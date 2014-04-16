/*
 * Copyright (C) 2012 Ondrej Perutka
 *
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public 
 * License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library. If not, see 
 * <http://www.gnu.org/licenses/>.
 */
package org.libav.audio;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.bridj.Pointer;
import org.libav.CopyTimestampGenerator;
import org.libav.IEncoder;
import org.libav.ITimestampGenerator;
import org.libav.LibavException;
import org.libav.avcodec.*;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avformat.IStreamWrapper;
import org.libav.avutil.MediaType;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;
import org.libav.data.IPacketConsumer;
import org.libav.util.Rational;

/**
 * Audio frame encoder.
 * 
 * @author Ondrej Perutka
 */
public class AudioFrameEncoder implements IEncoder {
    
    private static final AVUtilLibrary utilLib = LibraryManager.getInstance().getAVUtilLibrary();
    
    private final IStreamWrapper stream;
    private final ICodecContextWrapper cc;
    private boolean initialized;
    private boolean smallLastFrame;
    
    private IFrameWrapper tmpFrame;
    private Pointer<Byte> buffer;
    private int bufferSize;
    private int bufferSampleCapacity;
    private Pointer<Pointer<Byte>> planes;
    private int planeCount;
    private int frameSize;
    private int frameSampleCount;
    private long frameDuration;
    private Rational byteDuration;
    
    private IPacketWrapper packet;
    
    private long flushFramePts;
    private int offset;
    private Rational ptsTransformBase;
    private ITimestampGenerator timestampGenerator;
    
    private final Set<IPacketConsumer> consumers;

    /**
     * Create a new audio frame wncoder for the given audio stream.
     * 
     * @param stream an audio stream
     * @throws LibavException if the encoder cannot be created for some reason
     * (caused by the Libav)
     */
    public AudioFrameEncoder(IStreamWrapper stream) throws LibavException {
        this.stream = stream;
        
        cc = stream.getCodecContext();
        cc.clearWrapperCache();
        if (cc.getCodecType() != MediaType.AUDIO)
            throw new IllegalArgumentException("not an audio stream");
        
        initialized = false;
        smallLastFrame = false;
        
        tmpFrame = FrameWrapperFactory.getInstance().allocFrame();
        bufferSize = AVCodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE;
        buffer = malloc(bufferSize + AVCodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE);
        bufferSampleCapacity = 0;
        planes = null;
        planeCount = 0;
        frameSize = 0;
        frameSampleCount = 0;
        frameDuration = 0;
        byteDuration = null;
        
        packet = PacketWrapperFactory.getInstance().alloc();
        
        flushFramePts = 0;
        ptsTransformBase = null;
        timestampGenerator = new CopyTimestampGenerator();
        
        consumers = Collections.synchronizedSet(new HashSet<IPacketConsumer>());
    }
    
    @Override
    public ICodecContextWrapper getCodecContext() {
        return cc;
    }
    
    @Override
    public IStreamWrapper getStream() {
        return stream;
    }

    @Override
    public ITimestampGenerator getTimestampGenerator() {
        return timestampGenerator;
    }

    @Override
    public void setTimestampGenerator(ITimestampGenerator timestampGenerator) {
        this.timestampGenerator = timestampGenerator;
    }
    
    @Override
    public synchronized void close() {
        cc.close();
        if (packet != null)
            packet.free();
        if (buffer != null)
            utilLib.av_free(buffer);
        if (tmpFrame != null)
            tmpFrame.free();
        
        packet = null;
        buffer = null;
        tmpFrame = null;
        planes = null;
    }
    
    @Override
    public boolean isClosed() {
        return packet == null;
    }
    
    private Pointer<Byte> malloc(int size) {
        Pointer<Byte> ptr = utilLib.av_malloc(size).as(Byte.class);
        if (ptr == null)
            throw new OutOfMemoryError("not enough memory for the audio frame encoder");
        
        return ptr;
    }
    
    @Override
    public synchronized void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
        if (isClosed())
            return;
        
        initEncoder();
        
        long pts;
        while ((pts = timestampGenerator.nextFrame(frame.getPts())) >= 0)
            encodeFrame(frame, pts);
    }
    
    @Override
    public synchronized void flush() throws LibavException {
        if (isClosed())
            return;
        
        initEncoder();
        
        boolean flush = true;
        while (flush)
            flush = flushFrame();
    }
    
    private void initEncoder() throws LibavException {
        if (initialized)
            return;
        
        cc.clearWrapperCache();
        
        ICodecWrapper codec = CodecWrapperFactory.getInstance().findEncoder(cc.getCodecId());
        
        smallLastFrame = (codec.getCapabilities() & AVCodecLibrary.CODEC_CAP_SMALL_LAST_FRAME) == AVCodecLibrary.CODEC_CAP_SMALL_LAST_FRAME;
        frameSampleCount = cc.getFrameSize();
        if ((codec.getCapabilities() & AVCodecLibrary.CODEC_CAP_VARIABLE_FRAME_SIZE) == AVCodecLibrary.CODEC_CAP_VARIABLE_FRAME_SIZE)
            frameSampleCount = 8192;
        if (frameSampleCount <= 1) // keep compatibility with older PCM encoders
            frameSampleCount = 8192;
        
        SampleFormat sampleFormat = cc.getSampleFormat();
        int bytesPerSample = sampleFormat.getBytesPerSample();
        int channelCount = cc.getChannels();
        
        frameSize = frameSampleCount * bytesPerSample;
        if (sampleFormat.isPlanar())
            planeCount = channelCount;
        else {
            frameSize *= channelCount;
            planeCount = 1;
        }
        
        planes = Pointer.allocatePointers(Byte.class, planeCount);
        int lineSize = bufferSize / planeCount;
        lineSize -= lineSize % bytesPerSample;
        for (int i = 0; i < planeCount; i++)
            planes.set(i, buffer.offset(i * lineSize));
        
        bufferSampleCapacity = lineSize * planeCount / (channelCount * bytesPerSample);
        
        frameDuration = 1000 * frameSampleCount / cc.getSampleRate();
        byteDuration = new Rational(frameDuration, frameSize);
        offset = 0;
        
        // propper time base is set after avformat_write_header() call
        stream.clearWrapperCache();
        ptsTransformBase = stream.getTimeBase().mul(1000).invert();
        
        initialized = true;
    }
    
    private boolean flushFrame() throws LibavException {
        packet.init();
        packet.setData(null);
        packet.setSize(0);
        
        int sampleCount = offset * planeCount / (cc.getChannels() * cc.getSampleFormat().getBytesPerSample());
        if (sampleCount > 0) {
            if (sampleCount < frameSampleCount && !smallLastFrame) {
                sampleCount = frameSampleCount;
                for (int i = 0; i < planeCount; i++)
                    planes.get(i).clearBytesAtOffset(offset, frameSize - offset, (byte)0);
                offset = frameSize;
            }
            tmpFrame.fillAudioFrame(sampleCount, cc.getChannels(), cc.getSampleFormat(), buffer, bufferSize, bufferSampleCapacity);
        }
        offset = 0;

        boolean result;
        if (result = cc.encodeAudioFrame(sampleCount == 0 ? null : tmpFrame, packet)) {
            packet.clearWrapperCache();
            packet.setStreamIndex(stream.getIndex());
            packet.setPts(ptsTransformBase.mul(flushFramePts).longValue());
            packet.setDts(packet.getPts());
            sendPacket(packet);
            flushFramePts += frameDuration;
        }
        
        return result;
    }
    
    private void encodeFrame(IFrameWrapper frame, long pts) throws LibavException {
        int lineSize = frame.getLineSize().get(0);
        int size = lineSize;
        pts -= byteDuration.mul(offset).longValue();
        
        while (size > 0) {
            size -= appendSamples(frame, lineSize - size);
            
            if (offset == frameSize) {
                offset = 0;
                tmpFrame.fillAudioFrame(frameSampleCount, cc.getChannels(), cc.getSampleFormat(), buffer, bufferSize, bufferSampleCapacity);
                
                packet.init();
                packet.setData(null);
                packet.setSize(0);
                
                if (cc.encodeAudioFrame(tmpFrame, packet)) {
                    packet.clearWrapperCache();
                    //System.out.printf("encoding audio frame: pts = %d (pts_offset = %d, source_pts = %d)\n", pts, timestampGenerator.getOffset(), frame.getPts());
                    packet.setStreamIndex(stream.getIndex());
                    packet.setPts(ptsTransformBase.mul(pts).longValue());
                    packet.setDts(packet.getPts());
                    sendPacket(packet);
                    pts += frameDuration;
                    flushFramePts = pts;
                }
            }
        }
    }
    
    private int appendSamples(IFrameWrapper frame, int frameOffset) {
        Pointer<Pointer<Byte>> data;
        if (planeCount > frame.getDataLength())
            data = frame.getExtendedData();
        else
            data = frame.getData();
        
        int tmp = frameSize - offset;
        int lineSize = frame.getLineSize().get(0);
        int size = lineSize - frameOffset;
        
        if (size < tmp)
            tmp = size;
        
        Pointer<Byte> dataPlane;
        Pointer<Byte> plane;
        
        for (int i = 0; i < planeCount; i++) {
            dataPlane = data.get(i).offset(frameOffset);
            plane = planes.get(i).offset(offset);
            dataPlane.copyTo(plane, tmp);
        }
        
        offset += tmp;
        
        return tmp;
    }
    
    private void sendPacket(IPacketWrapper packet) throws LibavException {
        synchronized (consumers) {
            for (IPacketConsumer c : consumers)
                c.processPacket(this, packet);
        }
    }

    @Override
    public void addPacketConsumer(IPacketConsumer c) {
        consumers.add(c);
    }

    @Override
    public void removePacketConsumer(IPacketConsumer c) {
        consumers.remove(c);
    }
    
}

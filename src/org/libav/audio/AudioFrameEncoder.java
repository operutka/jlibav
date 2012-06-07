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
import org.libav.IEncoder;
import org.libav.LibavException;
import org.libav.avcodec.*;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avformat.IStreamWrapper;
import org.libav.avutil.bridge.AVMediaType;
import org.libav.avutil.bridge.AVSampleFormat;
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
    
    public static final int DEFAULT_OUTPUT_BUFFER_SIZE = 200000;
    
    private IStreamWrapper stream;
    private ICodecContextWrapper cc;
    
    private IFrameWrapper tmpFrame;
    private int offset;
    private int outputBufferSize;
    private Pointer<Byte> outputBuffer;
    private IPacketWrapper packet;
    private Rational ptsTransformBase;
    private long ptsOffset;
    
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
        if (cc.getCodecType() != AVMediaType.AVMEDIA_TYPE_AUDIO)
            throw new IllegalArgumentException("not an audio stream");
        
        tmpFrame = FrameWrapperFactory.getInstance().allocFrame();
        tmpFrame.getData().set(0, malloc(AVCodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE + AVCodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE));
        tmpFrame.getLineSize().set(0, 0);
        outputBufferSize = DEFAULT_OUTPUT_BUFFER_SIZE;
        outputBuffer = malloc(outputBufferSize);
        packet = PacketWrapperFactory.getInstance().alloc();
        stream.clearWrapperCache();
        ptsTransformBase = stream.getTimeBase().mul(1000).invert();
        ptsOffset = -1;
        
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
    public synchronized void close() {
        cc.close();
        if (outputBuffer != null)
            utilLib.av_free(outputBuffer);
        if (packet != null)
            packet.free();
        if (tmpFrame != null)
            utilLib.av_free(tmpFrame.getData().get(0));
        
        outputBuffer = null;
        packet = null;
        tmpFrame = null;
    }
    
    @Override
    public boolean isClosed() {
        return outputBuffer == null;
    }
    
    /**
     * Get size of the output buffer in bytes (it is the buffer passed to the 
     * encoding function).
     * 
     * @return size of the output buffer
     */
    public int getOutputBufferSize() {
        return outputBufferSize;
    }

    /**
     * Set size of the output buffer (it is the buffer passed to the encoding 
     * function). DO NOT USE this method until you know what you are doing.
     * 
     * @param outputBufferSize a size in bytes
     */
    public synchronized void setOutputBufferSize(int outputBufferSize) {
        if (isClosed())
            return;
            
        utilLib.av_free(outputBuffer);
        outputBuffer = malloc(outputBufferSize);
        
        this.outputBufferSize = outputBufferSize;
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
        if (cc.isClosed())
            openCodecContext();
        
        IPacketWrapper p = encodeFrame(frame);
        if (p != null)
            sendPacket(p);
    }
    
    @Override
    public synchronized void flush() throws LibavException {
        if (isClosed())
            return;
        if (cc.isClosed())
            openCodecContext();
        
        IPacketWrapper p;
        while ((p = flushFrame()) != null)
            sendPacket(p);
    }
    
    private void openCodecContext() throws LibavException {
        cc.clearWrapperCache();
        cc.open(CodecWrapperFactory.getInstance().findEncoder(cc.getCodecId()));
        cc.clearWrapperCache();
        
        tmpFrame.getLineSize().set(0, cc.getFrameSize() * cc.getChannels() * AVSampleFormat.getBitsPerSample(cc.getSampleFormat()) / 8);
        offset = 0;
    }
    
    private IPacketWrapper flushFrame() throws LibavException {
        IPacketWrapper result = packet;
        
        offset = 0;
        result.init();
        result.setData(outputBuffer);
        result.setSize(outputBufferSize);
        
        int tmp = tmpFrame.getLineSize().get(0);
        tmpFrame.getLineSize().set(0, offset);

        if (!cc.encodeAudioFrame(offset == 0 ? null : tmpFrame, result))
            result = null;
        else
            result.setStreamIndex(stream.getIndex());
        
        tmpFrame.getLineSize().set(0, tmp);

        return result;
    }
    
    private IPacketWrapper encodeFrame(IFrameWrapper frame) throws LibavException {
        Pointer<Byte> data = frame.getData().get(0);
        int tmp, size = frame.getLineSize().get(0);
        
        while (size > 0) {
            tmp = tmpFrame.getLineSize().get(0) - offset;
            if (size < tmp)
                tmp = size;
            data.copyTo(tmpFrame.getData().get(0).offset(offset), tmp);
            offset += tmp;
            size -= tmp;
            data = data.offset(tmp);
            
            if (offset == tmpFrame.getLineSize().get(0)) {
                offset = 0;
                packet.init();
                packet.setData(outputBuffer);
                packet.setSize(outputBufferSize);
                
                if (!cc.encodeAudioFrame(tmpFrame, packet))
                    return null;
                
                packet.setStreamIndex(stream.getIndex());
                if (frame.getPts() != AVUtilLibrary.AV_NOPTS_VALUE) {
                    if (ptsOffset == -1)
                        ptsOffset = frame.getPts();
                    // FIX: this does not allow to change frame rate neither respects the codec context time base
                    //System.out.printf("encoding audio frame: pts = %d (pts_offset = %d, source_pts = %d)\n", frame.getPts() - ptsOffset, ptsOffset, frame.getPts());
                    packet.setPts(ptsTransformBase.mul(frame.getPts() - ptsOffset).longValue());
                    packet.setDts(packet.getPts());
                }
                return packet;
            }
        }
        
        return null;
    }
    
    private void sendPacket(IPacketWrapper packet) throws LibavException {
        IPacketWrapper tmp;
        synchronized (consumers) {
            for (IPacketConsumer c : consumers) {
                tmp = packet.clone();
                c.processPacket(this, tmp);
                tmp.free();
            }
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

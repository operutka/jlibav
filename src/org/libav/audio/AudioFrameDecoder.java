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
import org.libav.IDecoder;
import org.libav.LibavException;
import org.libav.avcodec.*;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avformat.IStreamWrapper;
import org.libav.avutil.bridge.AVMediaType;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;
import org.libav.data.IFrameConsumer;
import org.libav.util.Rational;

/**
 * Audio frame decoder.
 * 
 * @author Ondrej Perutka
 */
public class AudioFrameDecoder implements IDecoder {
    
    private static final AVUtilLibrary utilLib = LibraryManager.getInstance().getAVUtilLibrary();
    
    private IStreamWrapper stream;
    private ICodecContextWrapper cc;
    
    private Rational sTimeBase;
    private long pts;
    
    private IFrameWrapper audioFrame;
    private Pointer<Byte> sampleBuffer;
    private int sampleBufferSize;
    
    private final Set<IFrameConsumer> consumers;

    /**
     * Create a new audio frame decoder for the given audio stream.
     * 
     * @param stream an audio stream
     * @throws LibavException if the decoder cannot be created for some reason
     * (caused by the Libav)
     */
    public AudioFrameDecoder(IStreamWrapper stream) throws LibavException {
        this.stream = stream;
        
        cc = stream.getCodecContext();
        cc.clearWrapperCache();
        if (cc.getCodecType() != AVMediaType.AVMEDIA_TYPE_AUDIO)
            throw new IllegalArgumentException("not an audio stream");
        
        cc.open(CodecWrapperFactory.getInstance().findDecoder(cc.getCodecId()));
        
        sTimeBase = stream.getTimeBase().mul(1000);
        pts = 0;
        
        audioFrame = FrameWrapperFactory.getInstance().allocFrame();
        sampleBufferSize = AVCodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE;
        sampleBuffer = utilLib.av_malloc(AVCodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE + AVCodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE).as(Byte.class);
        if (sampleBuffer == null)
            throw new OutOfMemoryError("unable to allocate memory to decode the audio stream");
        audioFrame.getData().set(0, sampleBuffer);
        audioFrame.getLineSize().set(0, sampleBufferSize);

        consumers = Collections.synchronizedSet(new HashSet<IFrameConsumer>());
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
        if (audioFrame != null)
            audioFrame.free();
        if (sampleBuffer != null)
            utilLib.av_free(sampleBuffer);
        cc.close();
        
        audioFrame = null;
        sampleBuffer = null;
    }
    
    @Override
    public boolean isClosed() {
        return cc.isClosed();
    }

    @Override
    public synchronized void processPacket(Object producer, IPacketWrapper packet) throws LibavException {
        if (isClosed() || packet.getStreamIndex() != stream.getIndex())
            return;
        
        //System.out.printf("AP: dts = %d\n", sTimeBase.mul(packet.getDts()).longValue());
        Pointer<Byte> tmp = packet.getData();
        while (packet.getSize() > 0) {
            audioFrame.getLineSize().set(0, sampleBufferSize);
            if (cc.decodeAudioFrame(packet, audioFrame))
                sendFrame(transformPts(audioFrame));
        }
        packet.setData(tmp);

        packet.free();
    }

    @Override
    public synchronized void flush() throws LibavException {
        IFrameWrapper fr;
        while ((fr = flushContext()) != null)
            sendFrame(transformPts(fr));
    }
    
    private synchronized IFrameWrapper flushContext() throws LibavException {
        if (isClosed())
            return null;
        
        IPacketWrapper packet = PacketWrapperFactory.getInstance().alloc();
        IFrameWrapper result = null;
        
        packet.setSize(0);
        packet.setData(null);
        audioFrame.getLineSize().set(0, sampleBufferSize);
        if (cc.decodeAudioFrame(packet, audioFrame))
            result = audioFrame;
        packet.free();
        
        return result;
    }
    
    protected void sendFrame(IFrameWrapper frame) throws LibavException {
        synchronized (consumers) {
            for (IFrameConsumer c : consumers)
                c.processFrame(this, frame);
        }
    }
    
    private IFrameWrapper transformPts(IFrameWrapper frame) {
        if (frame.getPacketDts() != AVUtilLibrary.AV_NOPTS_VALUE)
            frame.setPts(sTimeBase.mul(frame.getPacketDts()).longValue());
        else {
            frame.setPts(pts);
            pts += frame.getLineSize().get(0) * 8000 / (cc.getChannels() * cc.getSampleRate() * AVSampleFormat.getBitsPerSample(cc.getSampleFormat()));
        }
        
        return frame;
    }

    @Override
    public void addFrameConsumer(IFrameConsumer c) {
        consumers.add(c);
    }

    @Override
    public void removeFrameConsumer(IFrameConsumer c) {
        consumers.remove(c);
    }
    
}

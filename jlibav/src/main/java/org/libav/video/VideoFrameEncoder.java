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
package org.libav.video;

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
import org.libav.avformat.IFormatContextWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avutil.bridge.AVMediaType;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;
import org.libav.data.IPacketConsumer;
import org.libav.util.Rational;

/**
 * Video frame encoder.
 * 
 * @author Ondrej Perutka
 */
public class VideoFrameEncoder implements IEncoder {
    
    private static final AVUtilLibrary utilLib = LibraryManager.getInstance().getAVUtilLibrary();
    
    public static final int DEFAULT_OUTPUT_BUFFER_SIZE = 200000;
    
    private IStreamWrapper stream;
    private ICodecContextWrapper cc;
    
    private boolean rawFormat;
    
    private int outputBufferSize;
    private Pointer<Byte> outputBuffer;
    private IPacketWrapper packet;
    private Rational ptsTransformBase;
    private ITimestampGenerator timestampGenerator;
    
    private final Set<IPacketConsumer> consumers;
    
    /**
     * Create a new video frame encoder for the given video stream.
     * 
     * @param formatContext a format context
     * @param stream a video stream
     * @throws LibavException if the frame encoder cannot be crated (caused
     * by the Libav)
     */
    public VideoFrameEncoder(IFormatContextWrapper formatContext, IStreamWrapper stream) throws LibavException {
        this.stream = stream;
        
        cc = stream.getCodecContext();
        cc.clearWrapperCache();
        if (cc.getCodecType() != AVMediaType.AVMEDIA_TYPE_VIDEO)
            throw new IllegalArgumentException("not a video stream");
        
        rawFormat = (formatContext.getOutputFormat().getFlags() & AVFormatLibrary.AVFMT_RAWPICTURE) != 0;
        
        outputBufferSize = DEFAULT_OUTPUT_BUFFER_SIZE;
        outputBuffer = malloc(outputBufferSize);
        packet = PacketWrapperFactory.getInstance().alloc();
        ptsTransformBase = stream.getTimeBase().mul(1000).invert();
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
        if (outputBuffer != null)
            utilLib.av_free(outputBuffer);
        if (packet != null)
            packet.free();
        
        outputBuffer = null;
        packet = null;
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
            throw new OutOfMemoryError("not enough memory for the video frame encoder");
        
        return ptr;
    }
    
    @Override
    public synchronized void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
        if (isClosed())
            return;
        if (cc.isClosed())
            openCodecContext();
        
        IPacketWrapper p;
        long pts;
        
        while ((pts = timestampGenerator.nextFrame(frame.getPts())) >= 0) {
            p = encodeFrame(frame, pts);
            if (p != null)
                sendPacket(p);
        }
    }
    
    @Override
    public synchronized void flush() throws LibavException {
        if (isClosed())
            return;
        if (cc.isClosed())
            openCodecContext();
        
        IPacketWrapper p;
        while ((p = encodeFrame(null, timestampGenerator.getLastTimestamp())) != null)
            sendPacket(p);
    }
    
    private void openCodecContext() throws LibavException {
        cc.clearWrapperCache();
        cc.open(CodecWrapperFactory.getInstance().findEncoder(cc.getCodecId()));
        
    }
    
    private IPacketWrapper encodeFrame(IFrameWrapper frame, long pts) throws LibavException {
        packet.init();
        
        if (rawFormat) {
            if (frame == null)
                return null;
            packet.setFlags(packet.getFlags() | AVCodecLibrary.AV_PKT_FLAG_KEY);
            packet.setData(frame.getPointer().as(Byte.class));
            packet.setSize((int)FrameWrapperFactory.getInstance().getAVPictureSize());
            packet.setStreamIndex(stream.getIndex());
        } else {
            packet.setData(outputBuffer);
            packet.setSize(outputBufferSize);

            if (!cc.encodeVideoFrame(frame, packet))
                return null;

            packet.setStreamIndex(stream.getIndex());
            cc.clearWrapperCache();
            IFrameWrapper codedFrame = cc.getCodedFrame();
            if (codedFrame.isKeyFrame())
                packet.setFlags(packet.getFlags() | AVCodecLibrary.AV_PKT_FLAG_KEY);
             //System.out.printf("encoding video frame: pts = %d (pts_offset = %d, source_pts = %d)\n", pts, timestampGenerator.getOffset(), frame.getPts());
            packet.setPts(ptsTransformBase.mul(pts).longValue());
            packet.setDts(packet.getPts());
        }
        
        return packet;
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

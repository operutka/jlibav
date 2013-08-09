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
import org.libav.CopyTimestampGenerator;
import org.libav.IEncoder;
import org.libav.ITimestampGenerator;
import org.libav.LibavException;
import org.libav.avcodec.*;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avformat.IFormatContextWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avutil.MediaType;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.data.IPacketConsumer;
import org.libav.util.Rational;

/**
 * Video frame encoder.
 * 
 * @author Ondrej Perutka
 */
public class VideoFrameEncoder implements IEncoder {
    
    private IStreamWrapper stream;
    private ICodecContextWrapper cc;
    private boolean initialized;
    
    private boolean rawFormat;
    
    private IPacketWrapper packet;
    private Rational tsToCodecBase;
    private Rational tsToStreamBase;
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
        if (cc.getCodecType() != MediaType.VIDEO)
            throw new IllegalArgumentException("not a video stream");
        
        initialized = false;
        
        rawFormat = (formatContext.getOutputFormat().getFlags() & AVFormatLibrary.AVFMT_RAWPICTURE) != 0;
        
        packet = PacketWrapperFactory.getInstance().alloc();
        tsToCodecBase = null;
        tsToStreamBase = null;
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
        
        packet = null;
    }
    
    @Override
    public boolean isClosed() {
        return packet == null;
    }
    
    @Override
    public synchronized void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
        if (isClosed())
            return;
        
        initEncoder();
        
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
        
        initEncoder();
        
        IPacketWrapper p;
        while ((p = encodeFrame(null, timestampGenerator.getLastTimestamp())) != null)
            sendPacket(p);
    }
    
    private void initEncoder() throws LibavException {
        if (initialized)
            return;
        
        cc.clearWrapperCache();
        tsToCodecBase = cc.getTimeBase().mul(1000).invert();
        
        // propper time base is set after avformat_write_header() call
        stream.clearWrapperCache();
        tsToStreamBase = cc.getTimeBase().div(stream.getTimeBase());
        
        initialized = true;
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
            packet.setData(null);
            packet.setSize(0);

            boolean gotPacket;
            if (frame == null)
                gotPacket = cc.encodeVideoFrame(null, packet);
            else {
                long oldPts = frame.getPts();
                frame.setPts(tsToCodecBase.mul(pts).longValue());
                gotPacket = cc.encodeVideoFrame(frame, packet);
                frame.setPts(oldPts);
            }
            
            if (!gotPacket)
                return null;

            packet.clearWrapperCache();
            cc.clearWrapperCache();
            
            packet.setStreamIndex(stream.getIndex());
            IFrameWrapper codedFrame = cc.getCodedFrame();
            codedFrame.clearWrapperCache();
            if (codedFrame.isKeyFrame())
                packet.setFlags(packet.getFlags() | AVCodecLibrary.AV_PKT_FLAG_KEY);
            //System.out.printf("encoding video frame: pts = %d (pts_offset = %d, source_pts = %d)\n", pts, timestampGenerator.getOffset(), frame.getPts());
            if (packet.getPts() != AVUtilLibrary.AV_NOPTS_VALUE)
                packet.setPts(tsToStreamBase.mul(packet.getPts()).longValue());
            if (packet.getDts() != AVUtilLibrary.AV_NOPTS_VALUE)
                packet.setDts(tsToStreamBase.mul(packet.getDts()).longValue());
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

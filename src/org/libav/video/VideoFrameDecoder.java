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
import org.libav.IDecoder;
import org.libav.LibavException;
import org.libav.avcodec.*;
import org.libav.avformat.IStreamWrapper;
import org.libav.avutil.bridge.AVMediaType;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.data.IFrameConsumer;
import org.libav.util.Rational;

/**
 * Video frame decoder.
 * 
 * @author Ondrej Perutka
 */
public class VideoFrameDecoder implements IDecoder {
    
    private IStreamWrapper stream;
    private ICodecContextWrapper cc;
    
    private Rational sTimeBase;
    private long pts;
    private long frameDuration;
    
    private IFrameWrapper frame;
    
    private final Set<IFrameConsumer> consumers;

    /**
     * Create a new video frame decoder for the given video stream.
     * 
     * @param stream a video stream
     * @throws LibavException if the decoder cannot be created for some reason
     * (caused by the Libav)
     */
    public VideoFrameDecoder(IStreamWrapper stream) throws LibavException {
        this.stream = stream;
        
        cc = stream.getCodecContext();
        cc.clearWrapperCache();
        if (cc.getCodecType() != AVMediaType.AVMEDIA_TYPE_VIDEO)
            throw new IllegalArgumentException("not a video stream");
        
        cc.open(CodecWrapperFactory.getInstance().findDecoder(cc.getCodecId()));
        
        sTimeBase = stream.getTimeBase().mul(1000);
        pts = 0;
        frameDuration = cc.getTimeBase().mul(1000).longValue();

        frame = FrameWrapperFactory.getInstance().allocFrame();

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
        if (frame != null)
            frame.free();
        cc.close();
    }
    
    @Override
    public boolean isClosed() {
        return cc.isClosed();
    }

    @Override
    public synchronized void processPacket(Object producer, IPacketWrapper packet) throws LibavException {
        if (isClosed() || packet.getStreamIndex() != stream.getIndex())
            return;
        
        //System.out.printf("VP: dts = %d\n", sTimeBase.mul(packet.getDts()).longValue());
        Pointer<Byte> tmp = packet.getData();
        while (packet.getSize() > 0) {
            if (cc.decodeVideoFrame(packet, frame))
                sendFrame(transformPts(frame));
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
        if (cc.decodeVideoFrame(packet, frame))
            result = frame;
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
        //System.out.printf("decoded frame: pts = %d, packet_pts = %d, packet_dts = %d, sTimeBase = %s\n", frame.getPts(), frame.getPacketPts(), frame.getPacketDts(), sTimeBase.toString());
        if (frame.getPacketDts() != AVUtilLibrary.AV_NOPTS_VALUE)
            frame.setPts(sTimeBase.mul(frame.getPacketDts()).longValue());
        else {
            frame.setPts(pts);
            pts += frameDuration;
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

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
package org.libav.net.rtsp;

import java.io.IOException;
import java.net.InetAddress;
import org.libav.IEncoder;
import org.libav.IMediaEncoder;
import org.libav.LibavException;
import org.libav.avcodec.IFrameWrapper;
import org.libav.data.IFrameConsumer;

/**
 * Audio RTSP transcode stream.
 * 
 * @author Ondrej Perutka
 */
public class AudioTranscodeStream extends TranscodeStream implements IFrameConsumer {

    /**
     * Create a new audio transcode stream.
     * 
     * @param streamWriterFactory a stream writer factory
     * @throws IOException if the stream cannot be created
     */
    public AudioTranscodeStream(IStreamWriterFactory streamWriterFactory) throws IOException {
        super(streamWriterFactory);
    }

    @Override
    public synchronized UnicastConnectionInfo setupUnicast(String sessionId, InetAddress address, int rtpPort, int rtcpPort) throws IOException {
        UnicastConnectionInfo result = super.setupUnicast(sessionId, address, rtpPort, rtcpPort);
        
        if (!isStandalone()) {
            try {
                IMediaEncoder me = mediaEncoders.get(sessionId);
                IEncoder enc = me.getAudioStreamEncoder(0);
                IAggregateMediaStream ams = (IAggregateMediaStream)getParentStream();
                enc.setTimestampGenerator(ams.createTimestampGenerator(sessionId));
            } catch (Exception ex) {
                throw new IOException(ex);
            }
        }
        
        return result;
    }
    
    @Override
    public void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
        IMediaEncoder[] mes;
        
        synchronized (playbackSet) {
            mes = playbackSet.toArray(new IMediaEncoder[playbackSet.size()]);
        }
        
        for (IMediaEncoder me : mes)
            me.getAudioStreamEncoder(0).processFrame(this, frame);
    }
    
}

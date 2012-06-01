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
import org.libav.net.sdp.MediaDescription;

/**
 * Single media stream. The single media stream could be either standalone or
 * a part of an aggregate media stream.
 * 
 * @author Ondrej Perutka
 */
public interface ISingleMediaStream extends IMediaStream {
    
    /**
     * Prepare resources for unicast media transmission.
     * 
     * @param sessionId a session ID
     * @param address an address where to send media data
     * @param rtpPort a client RTP port
     * @param rtcpPort a client RTCP port
     * @return a description of the connection
     * @throws IOException if an IO error occurs
     */
    UnicastConnectionInfo setupUnicast(String sessionId, InetAddress address, int rtpPort, int rtcpPort) throws IOException;
    
    /**
     * Get media description (part of the SDP).
     * 
     * @param trackId a track ID for the media description
     * @return a media description which could be used to generate an SDP for
     * an aggregate media stream
     */
    MediaDescription getMediaDescription(int trackId);
    
    /**
     * Get parent (aggregate) media stream.
     * 
     * @return parent media stream or null if this stream is standalone
     */
    IMediaStream getParentStream();
    
    /**
     * Set parent media stream.
     * 
     * @param stream a media stream
     */
    void setParentStream(IMediaStream stream);
    
}

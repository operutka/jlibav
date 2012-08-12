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

import org.libav.*;

/**
 * Abstract media stream. Implements common media stream methods.
 * 
 * @author Ondrej Perutka
 */
public abstract class AbstractMediaStream implements IMediaStream {
    
    protected long id;

    public AbstractMediaStream() {
        id = 0;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Create a new RTP media encoder for the given connection description.
     * 
     * @param connectionInfo a connection description
     * @return an RTP media encoder
     * @throws LibavException if it is not possible to create a media encoder
     */
    protected IMediaEncoder createUnicastMediaEncoder(ISingleMediaStream.UnicastConnectionInfo connectionInfo) throws LibavException {
        return new DefaultMediaEncoder(createRtpUrl(connectionInfo), "rtp");
    }
    
    /**
     * Create a new RTP media writer for the given connection description.
     * 
     * @param connectionInfo a connection description
     * @return an RTP media writer
     * @throws LibavException if it is not possible to create a media writer
     */
    protected IMediaWriter createUnicastMediaWriter(ISingleMediaStream.UnicastConnectionInfo connectionInfo) throws LibavException {
        return new DefaultMediaWriter(createRtpUrl(connectionInfo), "rtp");
    }
    
    private String createRtpUrl(ISingleMediaStream.UnicastConnectionInfo connectionInfo) {
        StringBuilder url = new StringBuilder("rtp://");
        url.append(connectionInfo.getClientAddress().getHostAddress());
        url.append(":").append(connectionInfo.getClientRtpPort());
        url.append("?rtcpport=").append(connectionInfo.getClientRtcpPort());
        url.append("&localrtpport=").append(connectionInfo.getServerRtpPort());
        url.append("&localrtcpport=").append(connectionInfo.getServerRtcpPort());
        
        return url.toString();
    }
    
}

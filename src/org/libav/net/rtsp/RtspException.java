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

import org.libav.net.rtsp.message.RtspMessage;

/**
 * Exception which should be propagated to the remote client.
 * 
 * @author Ondrej Perutka
 */
public class RtspException extends Exception {

    private RtspMessage response;
    
    /**
     * Create a new RTSP exception and set the response code, response message
     * and message sequence number.
     * 
     * @param code a response code
     * @param message a response message
     * @param cseq a message sequence number
     */
    public RtspException(int code, String message, int cseq) {
        super(message);
        response = new RtspMessage(code, message, cseq);
    }
    
    /**
     * Create a new RTSP exception and set the response code, response message,
     * message sequence number and causing exception.
     * 
     * @param code a response code
     * @param message a response message
     * @param cseq a message sequence number
     * @param th an exception which caused this one
     */
    public RtspException(int code, String message, int cseq, Throwable th) {
        super(message, th);
        response = new RtspMessage(code, message, cseq);
    }

    /**
     * Get RTSP response message generated from this exception.
     * 
     * @return response message
     */
    public RtspMessage getResponse() {
        return response;
    }
    
}

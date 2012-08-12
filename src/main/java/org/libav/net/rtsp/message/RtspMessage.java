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
package org.libav.net.rtsp.message;

import java.nio.charset.Charset;

/**
 * RTSP message builder.
 * 
 * @author Ondrej Perutka
 */
public class RtspMessage {
    
    private RtspHeader header;
    private byte[] body;

    /**
     * Create a new RTSP message using the given header and body.
     * 
     * @param header RTSP message header
     * @param body 
     */
    public RtspMessage(RtspHeader header, byte[] body) {
        this.header = header;
        this.body = body;
    }
    
    /**
     * Create a new RTSP response message and set its ressponse code, 
     * message text and sequence number.
     * 
     * @param code
     * @param message
     * @param cseq message sequence number
     */
    public RtspMessage(int code, String message, int cseq) {
        this(new RtspResponseHeader(code, message, cseq), null);
    }
    
    /**
     * Create a new RTSP request message and set its method, URI and sequence
     * number.
     * 
     * @param method
     * @param uri
     * @param cseq message sequence number
     */
    public RtspMessage(String method, String uri, int cseq) {
        this(new RtspRequestHeader(method, uri, cseq), null);
    }
    
    /**
     * Get message header.
     * 
     * @return message header
     */
    public RtspHeader getHeader() {
        return header;
    }

    /**
     * Get message body.
     * 
     * @return message body
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * Set message body.
     * 
     * @param body 
     */
    public void setBody(byte[] body) {
        this.body = body;
    }
    
    /**
     * Get message body in its text form.
     * 
     * @param charset charset of the body
     * @return message body
     */
    public String getBodyText(Charset charset) {
        if (body == null)
            return null;
        
        return new String(body, charset);
    }
    
    /**
     * Set the given text as message body.
     * 
     * @param text 
     * @param encoding charset used to convert the text into a byte array
     */
    public void setBodyText(String text, Charset encoding) {
        body = text.getBytes(encoding);
    }
    
    /**
     * Get message as a byte array.
     * 
     * @return byte array
     */
    public byte[] getRawMessage() {
        if (body == null)
            header.removeField("content-length");
        else
            header.addField(new ContentLengthField(body.length));
        
        byte[] h = header.getBytes();
        int bodyLen = body == null ? 0 : body.length;
        byte[] result = new byte[h.length + bodyLen];
        System.arraycopy(h, 0, result, 0, h.length);
        if (body != null)
            System.arraycopy(body, 0, result, h.length, body.length);
        
        return result;
    }
    
}

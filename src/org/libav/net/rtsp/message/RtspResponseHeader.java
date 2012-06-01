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

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.libav.net.rtsp.RtspException;

/**
 * RTSP response header builder.
 * 
 * @author Ondrej Perutka
 */
public class RtspResponseHeader extends RtspHeader {
    
    private static final Pattern responsePattern = Pattern.compile("^([^\\s/]+)/([0-9\\.]+)[ \\t]+([0-9]+)[ \\t]+(.*)$");
    
    private int code;
    private String message;

    protected RtspResponseHeader(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    /**
     * Create a new RTSP response header and set its response code, response
     * message and sequence number.
     * 
     * @param code response code
     * @param message response message
     * @param cseq sequence number
     */
    public RtspResponseHeader(int code, String message, int cseq) {
        this(code, message);
        addField(new CSeqField(cseq));
    }

    /**
     * Get response code.
     * 
     * @return response code
     */
    public int getCode() {
        return code;
    }

    /**
     * Set response code.
     * 
     * @param code 
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Get response message.
     * 
     * @return response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set response message.
     * 
     * @param message 
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Make a text representation of this header.
     * 
     * @return a text representation of this header
     */
    @Override
    public String toString() {
        return "RTSP/1.0 " + code + " " + message + "\r\n" + super.toString();
    }
    
    /**
     * Parse RTSP response header from its text representation.
     * 
     * @param headerLines input lines (a response header split by "\r\n")
     * @return an RTSP response header
     * @throws ParseException if the given lines does not represent a valid
     * RTSP request header
     * @throws RtspException if the RTSP version is not supported
     */
    public static RtspResponseHeader parse(String[] headerLines) throws ParseException, RtspException {
        Matcher m = responsePattern.matcher(headerLines[0]);
        if (!m.find())
            throw new ParseException("not a valid request line", 0);
        if (!m.group(1).equalsIgnoreCase("RTSP"))
            throw new ParseException("unsupported protocol: " + m.group(3), 0);
        if (!m.group(2).matches("1.0"))
            throw new RtspException(505, "unsupported RTSP version: " + m.group(4), 0);
        
        RtspResponseHeader result = new RtspResponseHeader(Integer.parseInt(m.group(3)), m.group(4));
        
        for (int i = 1; i < headerLines.length; i++) {
            StringBuilder fieldLine = new StringBuilder(headerLines[i]);
            while (++i < headerLines.length) {
                if (headerLines[i].length() > 0 && !Character.isWhitespace(headerLines[i].charAt(0)))
                    break;
                fieldLine.append(headerLines[i].trim());
            }
            Field field = Field.parse(fieldLine.toString());
            if (field != null)
                result.addField(field);
            i--;
        }
        
        return result;
    }
    
}

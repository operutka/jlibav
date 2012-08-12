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
 * RTSP request header builder.
 * 
 * @author Ondrej Perutka
 */
public class RtspRequestHeader extends RtspHeader {
    
    private static final Pattern requestPattern = Pattern.compile("^([\\S]+)[ \\t]+([\\S]+)[ \\t]+([^\\s/]+)/([0-9\\.]+)$");
    
    public static final String METHOD_DESCRIBE = "DESCRIBE";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_PAUSE = "PAUSE";
    public static final String METHOD_PLAY = "PLAY";
    public static final String METHOD_SETUP = "SETUP";
    public static final String METHOD_TEARDOWN = "TEARDOWN";
    public static final String METHOD_GET_PARAMETER = "GET_PARAMETER";
    
    private String method;
    private String uri;
    
    protected RtspRequestHeader(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }
    
    /**
     * Create a new RTSP request header and set its method, URI and sequence
     * number.
     * 
     * @param method
     * @param uri
     * @param cseq sequence number
     */
    public RtspRequestHeader(String method, String uri, int cseq) {
        this(method, uri);
        addField(new CSeqField(cseq));
    }

    /**
     * Get method.
     * 
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Set method.
     * 
     * @param method 
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Get URI.
     * 
     * @return URI
     */
    public String getUri() {
        return uri;
    }

    /**
     * Set URI.
     * 
     * @param uri 
     */
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    /**
     * Make a text representation of this header.
     * 
     * @return a text representation of this header
     */
    @Override
    public String toString() {
        return method + " " + uri + " RTSP/1.0\r\n" + super.toString();
    }
    
    /**
     * Parse RTSP request header from its text representation.
     * 
     * @param headerLines input lines (a request header split by "\r\n")
     * @return an RTSP request header
     * @throws ParseException if the given lines does not represent a valid
     * RTSP request header
     * @throws RtspException if the RTSP version is not supported
     */
    public static RtspRequestHeader parse(String[] headerLines) throws ParseException, RtspException {
        Matcher m = requestPattern.matcher(headerLines[0]);
        if (!m.find())
            throw new ParseException("not a valid request line", 0);
        if (!m.group(3).equalsIgnoreCase("RTSP"))
            throw new ParseException("unsupported protocol: " + m.group(3), 0);
        if (!m.group(4).matches("1.0"))
            throw new RtspException(505, "unsupported RTSP version: " + m.group(4), 0);
        
        RtspRequestHeader result = new RtspRequestHeader(m.group(1), m.group(2));
        
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

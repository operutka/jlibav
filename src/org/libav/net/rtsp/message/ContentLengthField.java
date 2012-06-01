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

/**
 * Content-Length RTSP header field builder.
 * 
 * @author Ondrej Perutka
 */
public class ContentLengthField extends RtspHeader.Field {

    private long length;

    /**
     * Create a new Content-Length header field and set the length.
     * 
     * @param length 
     */
    public ContentLengthField(long length) {
        super("Content-Length");
        this.length = length;
    }

    /**
     * Get length.
     * 
     * @return length
     */
    public long getLength() {
        return length;
    }

    /**
     * Set length.
     * 
     * @param length 
     */
    public void setLength(long length) {
        this.length = length;
    }
    
    @Override
    public String getValueText() {
        return "" + length;
    }

    @Override
    public ContentLengthField clone() {
        return new ContentLengthField(length);
    }
    
    /**
     * Parse Content-Length RTSP header field.
     * 
     * @param fieldValue
     * @return a Content-Length RTSP header field
     * @throws ParseException if the given fieldValue is not an integral number
     */
    public static ContentLengthField parse(String fieldValue) throws ParseException {
        long length;
        
        try {
            length = Long.parseLong(fieldValue);
        } catch (Exception ex) {
            throw (ParseException) new ParseException("not a valid content-length field", 0).initCause(ex);
        }
        
        return new ContentLengthField(length);
    }
    
}

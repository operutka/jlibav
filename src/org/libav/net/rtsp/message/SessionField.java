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

/**
 * Session RTSP header field builder.
 * 
 * @author Ondrej Perutka
 */
public class SessionField extends RtspHeader.Field {
    
    private static final Pattern parsePattern = Pattern.compile("^([^;]+)(;timeout=([0-9]+))?$");

    private String id;
    private Long timeout;

    /**
     * Create a new Session field and set the session ID and timeout.
     * 
     * @param id session ID
     * @param timeout 
     */
    public SessionField(String id, Long timeout) {
        super("Session");
        this.id = id;
        this.timeout = timeout;
    }

    /**
     * Create a new Session field and set its session ID.
     * 
     * @param id session ID
     */
    public SessionField(String id) {
        this(id, null);
    }

    /**
     * Get session ID.
     * 
     * @return session ID
     */
    public String getId() {
        return id;
    }

    /**
     * Set session ID.
     * 
     * @param id session ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get timeout.
     * 
     * @return timeout in seconds or null if the timeout is not set
     */
    public Long getTimeout() {
        return timeout;
    }

    /**
     * Set timeout (pass null if you want to unset the timeout).
     * 
     * @param timeout 
     */
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
    
    @Override
    public String getValueText() {
        if (timeout == null)
            return id;
        else
            return id + ";timeout=" + timeout;
    }

    @Override
    public SessionField clone() {
        SessionField result = new SessionField(id);
        result.setTimeout(timeout);
        return result;
    }
    
    /**
     * Parse Session RTSP header field.
     * @param fieldValue
     * @return a Parse RTSP header field
     * @throws ParseException if the given value is not a valid session field
     * value text
     */
    public static SessionField parse(String fieldValue) throws ParseException {
        Matcher m = parsePattern.matcher(fieldValue);
        if (!m.find())
            throw new ParseException("not a valid session field", 0);
        
        SessionField result = new SessionField(m.group(1));
        String tmp = m.group(3);
        if (tmp != null && tmp.length() > 0) {
            try {
                result.setTimeout(Long.parseLong(tmp));
            } catch (Exception ex) {
                throw (ParseException) new ParseException("not a valid session field", 0).initCause(ex);
            }
        }
        
        return result;
    }
    
}

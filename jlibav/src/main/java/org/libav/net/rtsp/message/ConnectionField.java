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

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Connection RTSP header field builder.
 * 
 * @author Ondrej Perutka
 */
public class ConnectionField extends RtspHeader.Field {
    
    private static final Pattern parsePattern = Pattern.compile("[^,]+");

    private final Set<String> params;
    
    /**
     * Create a new empty Connection field.
     */
    public ConnectionField() {
        super("Connection");
        this.params = new HashSet<String>();
    }

    /**
     * Add a connection parameter.
     * 
     * @param param a connection parameter
     */
    public void addParam(String param) {
        params.add(param.toLowerCase());
    }
    
    /**
     * Checks whether this field contains the given parameter or not.
     * 
     * @param param a parameter
     * @return true if the parameter is already set in this field, false 
     * otherwise
     */
    public boolean containsParam(String param) {
        return params.contains(param.toLowerCase());
    }
    
    /**
     * Remove the given parameter from this field.
     * 
     * @param param a parameter
     */
    public void removeParam(String param) {
        params.remove(param.toLowerCase());
    }
    
    @Override
    public String getValueText() {
        StringBuilder bldr = new StringBuilder();
        for (String f : params)
            bldr.append(f).append(",");
        bldr.delete(bldr.length() - 1, bldr.length());
        return bldr.toString();
    }

    @Override
    public ConnectionField clone() {
        ConnectionField result = new ConnectionField();
        result.params.addAll(params);
        return result;
    }
    
    /**
     * Parse Connection RTSP header field.
     * 
     * @param fieldValue
     * @return a Connection RTSP header field
     */
    public static ConnectionField parse(String fieldValue) {
        ConnectionField result = new ConnectionField();
        Matcher m  = parsePattern.matcher(fieldValue);
        
        while (m.find())
            result.addParam(m.group());
        
        return result;
    }
    
}

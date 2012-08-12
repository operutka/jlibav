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
package org.libav.net.sdp;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Key field parser/builder.
 * 
 * @author Ondrej Perutka
 */
public class Key implements Cloneable {
    
    private static final Pattern parsePattern = Pattern.compile("^k=([^:\\s]+)(:(.*))?$");
    
    private String method;
    private String key;

    /**
     * Create a new instance of the key field, set its method and key.
     * 
     * @param method
     * @param key 
     */
    public Key(String method, String key) {
        this.method = method;
        this.key = key;
    }

    /**
     * Get key.
     * 
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Set key.
     * 
     * @param key 
     */
    public void setKey(String key) {
        this.key = key;
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
     * Create text representation of this field.
     * 
     * @return text representation of this field
     */
    @Override
    public String toString() {
        if ("prompt".equals(method))
            return "k=" + method + "\r\n";
        
        return "k=" + method + ":" + key + "\r\n";
    }

    /**
     * Clone this field.
     * 
     * @return cloned instance
     */
    @Override
    public Key clone() {
        return new Key(method, key);
    }
    
    /**
     * Parse a text representation of the key field.
     * 
     * @param line a text representation
     * @return key field
     * @throws ParseException if the given line is not a valid key field
     */
    public static Key parse(String line) throws ParseException {
        Matcher m = parsePattern.matcher(line);
        if (!m.find())
            throw new ParseException("not a valid key record", 0);
        
        return new Key(m.group(1), m.group(3));
    }
    
}

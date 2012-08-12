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
 * Attribute field parser/builder.
 * 
 * @author Ondrej Perutka
 */
public class Attribute implements Cloneable {
    
    private static final Pattern parsePattern = Pattern.compile("^a=([^:\\s]+)(:(.*))?$");
    
    private String name;
    private String value;

    /**
     * Create a new attribute field and set its name and value.
     * 
     * @param name
     * @param value 
     */
    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Create a new attribute field with no value and set its name.
     * 
     * @param name 
     */
    public Attribute(String name) {
        this(name, null);
    }

    /**
     * Get attribute name.
     * 
     * @return attribute name
     */
    public String getName() {
        return name;
    }

    /**
     * Set attribute name.
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get attribute value.
     * 
     * @return attribute value or null if it has no value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set attribute value.
     * 
     * @param value 
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Create text representation of this field.
     * 
     * @return text representation of this field
     */
    @Override
    public String toString() {
        if (value == null)
            return "a=" + name + "\r\n";
        
        return "a=" + name + ":" + value + "\r\n";
    }

    /**
     * Clone this field.
     * 
     * @return cloned instance
     */
    @Override
    public Attribute clone() {
        return new Attribute(name, value);
    }
    
    /**
     * Parse a text representation of the attribute field.
     * 
     * @param line a text representation
     * @return attribute field
     * @throws ParseException if the given line is not a valid attribute field
     */
    public static Attribute parse(String line) throws ParseException {
        Matcher m = parsePattern.matcher(line);
        if (!m.find())
            throw new ParseException("not a valid attribute record", 0);
        
        return new Attribute(m.group(1), m.group(3));
    }
    
}

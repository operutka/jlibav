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
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract RTSP message header.
 * 
 * @author Ondrej Perutka
 */
public abstract class RtspHeader {
    
    private final Map<String, Field> fields;

    protected RtspHeader() {
        fields = new HashMap<String, Field>();
    }
    
    /**
     * Get message sequence number.
     * 
     * @return message sequence number
     */
    public int getCSeq() {
        CSeqField csf = (CSeqField)getField("cseq");
        if (csf == null)
            return 0;
        
        return csf.getCseq();
    }
    
    /**
     * Add header field.
     * 
     * @param field header field
     */
    public void addField(Field field) {
        fields.put(field.getName().toLowerCase(), field);
    }
    
    /**
     * Get header field with the given name.
     * 
     * @param name a name of the header field
     * @return header field with the given name or null if there is no such
     * header field
     */
    public Field getField(String name) {
        return fields.get(name.toLowerCase());
    }
    
    /**
     * Remove header field with the given name.
     * 
     * @param name a name of the header field
     */
    public void removeField(String name) {
        fields.remove(name.toLowerCase());
    }
    
    /**
     * Encode header into a byte array using UTF-8 charset.
     * 
     * @return encoded header
     */
    public byte[] getBytes() {
        return toString().getBytes(Charset.forName("UTF-8"));
    }
    
    /**
     * Make a text representation of this header.
     * 
     * @return a text representation of this header
     */
    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder();
        for (Field field : fields.values())
            bldr.append(field).append("\r\n");
        bldr.append("\r\n");
        
        return bldr.toString();
    }
    
    /**
     * Abstract RTSP message header field.
     */
    public static abstract class Field implements Cloneable {
        private static final Pattern parsePattern = Pattern.compile("([^\\s:]+):[ \\t]*(.*)");
        
        private final String name;

        /**
         * Create a new header field and set its name.
         * 
         * @param name field name
         */
        public Field(String name) {
            this.name = name;
        }

        /**
         * Get field name.
         * 
         * @return field name
         */
        public String getName() {
            return name;
        }
        
        /**
         * Get field value in its text form.
         * 
         * @return field value
         */
        public abstract String getValueText();
        
        /**
         * Make a text representation of this field.
         * 
         * @return a text representation of this field
         */
        @Override
        public String toString() {
            return name + ":" + getValueText();
        }
        
        /**
         * Create a clonned instance of this field.
         * 
         * @return a clonned instance of this field
         */
        @Override
        public abstract Field clone();
        
        /**
         * Parse a header field from its text representation.
         * 
         * NOTE:
         * If the field type is recognized, the propper instance of the Field
         * subclass is returned, otherwise the GenericField instance is 
         * returned.
         * 
         * This method recognizes following header fields:
         * Transport, CSeq, Session, Date, Require, Proxy-Require, Unsupported,
         * Connection, Content-Length
         * 
         * @param fieldLine a header field text
         * @return a header field
         * @throws ParseException if the given text is not a valid header field
         */
        public static Field parse(String fieldLine) throws ParseException {
            Matcher m = parsePattern.matcher(fieldLine);
            if (!m.find())
                return null;
            
            String name = m.group(1);
            String value = m.group(2);
            if ("transport".equalsIgnoreCase(name))
                return TransportField.parse(value);
            else if ("cseq".equalsIgnoreCase(name))
                return CSeqField.parse(value);
            else if ("session".equalsIgnoreCase(name))
                return SessionField.parse(value);
            else if ("date".equalsIgnoreCase(name))
                return DateField.parse(value);
            else if ("require".equalsIgnoreCase(name))
                return RequireField.parse(value);
            else if ("proxy-require".equalsIgnoreCase(name))
                return ProxyRequireField.parse(value);
            else if ("unsupported".equalsIgnoreCase(name))
                return UnsupportedField.parse(value);
            else if ("connection".equalsIgnoreCase(name))
                return ConnectionField.parse(value);
            else if ("content-length".equalsIgnoreCase(name))
                return ContentLengthField.parse(value);
            
            return new GenericField(name, value);
        }
    }
    
}

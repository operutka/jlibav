/*
 * Copyright (C) 2013 Ondrej Perutka
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
package org.libav.avutil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.libav.avutil.bridge.AVMediaType;

/**
 * Media type enum.
 * 
 * @author Ondrej Perutka
 */
public enum MediaType {
    
    UNKNOWN("AVMEDIA_TYPE_UNKNOWN"),
    VIDEO("AVMEDIA_TYPE_VIDEO"),
    AUDIO("AVMEDIA_TYPE_AUDIO"),
    DATA("AVMEDIA_TYPE_DATA"),
    SUBTITLE("AVMEDIA_TYPE_SUBTITLE"),
    ATTACHMENT("AVMEDIA_TYPE_ATTACHMENT"),
    NB("AVMEDIA_TYPE_NB");
    
    private static final Map<Integer, MediaType> idMap;
    
    static {
        idMap = new HashMap<Integer, MediaType>();
        for (MediaType mediaType : MediaType.values())
            idMap.put(mediaType.value(), mediaType);
    }
    
    private int value;
    
    private MediaType(String fieldName) {
        value = MediaTypeMapper.getInstance().mediaTypeValue(fieldName);
    }
    
    public int value() {
        return value;
    }
    
    public static MediaType valueOf(int value) {
        return idMap.get(value);
    }
    
    private static class MediaTypeMapper {
        
        private static final MediaTypeMapper mapper = new MediaTypeMapper();
        
        private final Map<String, Integer> mediaTypeFieldMap;
        private final int UNKNOWN_VALUE;
        
        private MediaTypeMapper() {
            mediaTypeFieldMap = new HashMap<String, Integer>();
            UNKNOWN_VALUE = AVMediaType.AVMEDIA_TYPE_UNKNOWN;
            parseFields(AVMediaType.class.getDeclaredFields());
        }
        
        private void parseFields(Field[] fields) {
            Pattern codecIdPattern = Pattern.compile("(AVMEDIA_TYPE_[0-9A-Za-z_]+)");
            Matcher matcher;
            int mods;
            
            for (Field field : fields) {
                mods = field.getModifiers();
                if (Modifier.isStatic(mods) && Modifier.isFinal(mods)
                        && Modifier.isPublic(mods)) {
                    matcher = codecIdPattern.matcher(field.getName());
                    if (matcher.matches()) {
                        try {
                            mediaTypeFieldMap.put(matcher.group(1), field.getInt(null));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
        
        public int mediaTypeValue(String fieldName) {
            Integer val = mediaTypeFieldMap.get(fieldName);
            if (val == null)
                return UNKNOWN_VALUE;
            
            return val;
        }
        
        public static MediaTypeMapper getInstance() {
            return mapper;
        }
        
    }
    
}

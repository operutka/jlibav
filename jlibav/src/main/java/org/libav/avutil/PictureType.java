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
import org.libav.avutil.bridge.AVPictureType;

/**
 * Picture type enum.
 * 
 * @author Ondrej Perutka
 */
public enum PictureType {
    
    I("AV_PICTURE_TYPE_I"),
    P("AV_PICTURE_TYPE_P"),
    B("AV_PICTURE_TYPE_B"),
    S("AV_PICTURE_TYPE_S"),
    SI("AV_PICTURE_TYPE_SI"),
    SP("AV_PICTURE_TYPE_SP"),
    BI("AV_PICTURE_TYPE_BI");
    
    private static final Map<Integer, PictureType> idMap;
    
    static {
        idMap = new HashMap<Integer, PictureType>();
        for (PictureType pictureType : PictureType.values())
            idMap.put(pictureType.value(), pictureType);
    }
    
    private int value;
    
    private PictureType(String fieldName) {
        value = PictureTypeMapper.getInstance().pictureTypeValue(fieldName);
    }
    
    public int value() {
        return value;
    }
    
    public static PictureType valueOf(int value) {
        return idMap.get(value);
    }
    
    private static class PictureTypeMapper {
        
        private static final PictureTypeMapper mapper = new PictureTypeMapper();
        
        private final Map<String, Integer> pictureTypeFieldMap;
        
        private PictureTypeMapper() {
            pictureTypeFieldMap = new HashMap<String, Integer>();
            parseFields(AVPictureType.class.getDeclaredFields());
        }
        
        private void parseFields(Field[] fields) {
            Pattern codecIdPattern = Pattern.compile("(AV_PICTURE_TYPE_[0-9A-Za-z_]+)");
            Matcher matcher;
            int mods;
            
            for (Field field : fields) {
                mods = field.getModifiers();
                if (Modifier.isStatic(mods) && Modifier.isFinal(mods)
                        && Modifier.isPublic(mods)) {
                    matcher = codecIdPattern.matcher(field.getName());
                    if (matcher.matches()) {
                        try {
                            pictureTypeFieldMap.put(matcher.group(1), field.getInt(null));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
        
        public int pictureTypeValue(String fieldName) {
            Integer val = pictureTypeFieldMap.get(fieldName);
            if (val == null)
                return -1;
            
            return val;
        }
        
        public static PictureTypeMapper getInstance() {
            return mapper;
        }
        
    }
    
}

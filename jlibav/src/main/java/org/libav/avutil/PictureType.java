/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author daemonx
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

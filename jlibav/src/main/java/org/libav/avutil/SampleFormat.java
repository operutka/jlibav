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
import org.libav.avutil.bridge.AVSampleFormat;

/**
 * Sample format enum.
 * 
 * @author Ondrej Perutka
 */
public enum SampleFormat {
    
    NONE("AV_SAMPLE_FMT_NONE"),
    U8("AV_SAMPLE_FMT_U8"),
    S16("AV_SAMPLE_FMT_S16"),
    S32("AV_SAMPLE_FMT_S32"),
    FLT("AV_SAMPLE_FMT_FLT"),
    DBL("AV_SAMPLE_FMT_DBL"),
    
    U8P("AV_SAMPLE_FMT_U8P"),
    S16P("AV_SAMPLE_FMT_S16P"),
    S32P("AV_SAMPLE_FMT_S32P"),
    FLTP("AV_SAMPLE_FMT_FLTP"),
    DBLP("AV_SAMPLE_FMT_DBLP"),
    
    NB("AV_SAMPLE_FMT_NB");
    
    private static final Map<Integer, SampleFormat> idMap;
    private static final SampleFormatMapper sampleFormatMapper;
    
    static {
        idMap = new HashMap<Integer, SampleFormat>();
        for (SampleFormat sampleFmt : SampleFormat.values())
            idMap.put(sampleFmt.value(), sampleFmt);
        sampleFormatMapper = SampleFormatMapper.getInstance();
    }
    
    private int value;
    
    private SampleFormat(String fieldName) {
        value = SampleFormatMapper.getInstance().sampleFormatValue(fieldName);
    }
    
    public int value() {
        return value;
    }
    
    public int getBytesPerSample() {
        return sampleFormatMapper.getBytesPerSample(value);
    }
    
    public int getBitsPerSample() {
        return sampleFormatMapper.getBitsPerSample(value);
    }
    
    public boolean isPlanar() {
        return sampleFormatMapper.isPlanar(value);
    }
    
    public boolean isReal() {
        return sampleFormatMapper.isReal(value);
    }
    
    public boolean isSigned() {
        return sampleFormatMapper.isSigned(value);
    }
    
    public boolean isUnsigned() {
        return sampleFormatMapper.isUnsigned(value);
    }
    
    public static SampleFormat valueOf(int value) {
        return idMap.get(value);
    }
    
    private static class SampleFormatMapper {
        
        private static final SampleFormatMapper mapper = new SampleFormatMapper();
        
        private final Map<String, Integer> sampleFormatFieldMap;
        private final int NONE_VALUE;
        
        private SampleFormatMapper() {
            sampleFormatFieldMap = new HashMap<String, Integer>();
            NONE_VALUE = AVSampleFormat.AV_SAMPLE_FMT_NONE;
            parseFields(AVSampleFormat.class.getDeclaredFields());
        }
        
        private void parseFields(Field[] fields) {
            Pattern codecIdPattern = Pattern.compile("(AV_SAMPLE_FMT_[0-9A-Za-z_]+)");
            Matcher matcher;
            int mods;
            
            for (Field field : fields) {
                mods = field.getModifiers();
                if (Modifier.isStatic(mods) && Modifier.isFinal(mods)
                        && Modifier.isPublic(mods)) {
                    matcher = codecIdPattern.matcher(field.getName());
                    if (matcher.matches()) {
                        try {
                            sampleFormatFieldMap.put(matcher.group(1), field.getInt(null));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
        
        public int sampleFormatValue(String fieldName) {
            Integer val = sampleFormatFieldMap.get(fieldName);
            if (val == null)
                return NONE_VALUE;
            
            return val;
        }
        
        public int getBytesPerSample(int sampleFormat) {
            return AVSampleFormat.getBytesPerSample(sampleFormat);
        }
        
        public int getBitsPerSample(int sampleFormat) {
            return AVSampleFormat.getBitsPerSample(sampleFormat);
        }
        
        public boolean isPlanar(int sampleFormat) {
            return AVSampleFormat.isPlanar(sampleFormat);
        }
        
        public boolean isReal(int sampleFormat) {
            return AVSampleFormat.isReal(sampleFormat);
        }
        
        public boolean isSigned(int sampleFormat) {
            return AVSampleFormat.isSigned(sampleFormat);
        }
        
        public boolean isUnsigned(int sampleFormat) {
            return AVSampleFormat.isUnsigned(sampleFormat);
        }
        
        public static SampleFormatMapper getInstance() {
            return mapper;
        }
        
    }
    
}

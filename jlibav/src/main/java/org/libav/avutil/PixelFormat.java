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

/**
 * Pixel format enum.
 * 
 * @author Ondrej Perutka
 */
public enum PixelFormat {
    
    NONE("PIX_FMT_NONE"),
    YUV420P("PIX_FMT_YUV420P"),
    YUYV422("PIX_FMT_YUYV422"),
    RGB24("PIX_FMT_RGB24"),
    BGR24("PIX_FMT_BGR24"),
    YUV422P("PIX_FMT_YUV422P"),
    YUV444P("PIX_FMT_YUV444P"),
    YUV410P("PIX_FMT_YUV410P"),
    YUV411P("PIX_FMT_YUV411P"),
    GRAY8("PIX_FMT_GRAY8"),
    MONOWHITE("PIX_FMT_MONOWHITE"),
    MONOBLACK("PIX_FMT_MONOBLACK"),
    PAL8("PIX_FMT_PAL8"),
    YUVJ420P("PIX_FMT_YUVJ420P"),
    YUVJ422P("PIX_FMT_YUVJ422P"),
    YUVJ444P("PIX_FMT_YUVJ444P"),
    XVMC_MPEG2_MC("PIX_FMT_XVMC_MPEG2_MC"),
    XVMC_MPEG2_IDCT("PIX_FMT_XVMC_MPEG2_IDCT"),
    UYVY422("PIX_FMT_UYVY422"),
    UYYVYY411("PIX_FMT_UYYVYY411"),
    BGR8("PIX_FMT_BGR8"),
    BGR4("PIX_FMT_BGR4"),
    BGR4_BYTE("PIX_FMT_BGR4_BYTE"),
    RGB8("PIX_FMT_RGB8"),
    RGB4("PIX_FMT_RGB4"),
    RGB4_BYTE("PIX_FMT_RGB4_BYTE"),
    NV12("PIX_FMT_NV12"),
    NV21("PIX_FMT_NV21"),
    
    ARGB("PIX_FMT_ARGB"),
    RGBA("PIX_FMT_RGBA"),
    ABGR("PIX_FMT_ABGR"),
    BGRA("PIX_FMT_BGRA"),
    
    GRAY16BE("PIX_FMT_GRAY16BE"),
    GRAY16LE("PIX_FMT_GRAY16LE"),
    YUV440P("PIX_FMT_YUV440P"),
    YUVJ440P("PIX_FMT_YUVJ440P"),
    YUVA420P("PIX_FMT_YUVA420P"),
    VDPAU_H264("PIX_FMT_VDPAU_H264"),
    VDPAU_MPEG1("PIX_FMT_VDPAU_MPEG1"),
    VDPAU_MPEG2("PIX_FMT_VDPAU_MPEG2"),
    VDPAU_WMV3("PIX_FMT_VDPAU_WMV3"),
    VDPAU_VC1("PIX_FMT_VDPAU_VC1"),
    RGB48BE("PIX_FMT_RGB48BE"),
    RGB48LE("PIX_FMT_RGB48LE"),
    
    RGB565BE("PIX_FMT_RGB565BE"),
    RGB565LE("PIX_FMT_RGB565LE"),
    RGB555BE("PIX_FMT_RGB555BE"),
    RGB555LE("PIX_FMT_RGB555LE"),
    
    BGR565BE("PIX_FMT_BGR565BE"),
    BGR565LE("PIX_FMT_BGR565LE"),
    BGR555BE("PIX_FMT_BGR555BE"),
    BGR555LE("PIX_FMT_BGR555LE"),
    
    VAAPI_MOCO("PIX_FMT_VAAPI_MOCO"),
    VAAPI_IDCT("PIX_FMT_VAAPI_IDCT"),
    VAAPI_VLD("PIX_FMT_VAAPI_VLD"),
    
    YUV420P16LE("PIX_FMT_YUV420P16LE"),
    YUV420P16BE("PIX_FMT_YUV420P16BE"),
    YUV422P16LE("PIX_FMT_YUV422P16LE"),
    YUV422P16BE("PIX_FMT_YUV422P16BE"),
    YUV444P16LE("PIX_FMT_YUV444P16LE"),
    YUV444P16BE("PIX_FMT_YUV444P16BE"),
    VDPAU_MPEG4("PIX_FMT_VDPAU_MPEG4"),
    DXVA2_VLD("PIX_FMT_DXVA2_VLD"),
    
    RGB444LE("PIX_FMT_RGB444LE"),
    RGB444BE("PIX_FMT_RGB444BE"),
    BGR444LE("PIX_FMT_BGR444LE"),
    BGR444BE("PIX_FMT_BGR444BE"),
    Y400A("PIX_FMT_Y400A"),
    BGR48BE("PIX_FMT_BGR48BE"),
    BGR48LE("PIX_FMT_BGR48LE"),
    YUV420P9BE("PIX_FMT_YUV420P9BE"),
    YUV420P9LE("PIX_FMT_YUV420P9LE"),
    YUV420P10BE("PIX_FMT_YUV420P10BE"),
    YUV420P10LE("PIX_FMT_YUV420P10LE"),
    YUV422P10BE("PIX_FMT_YUV422P10BE"),
    YUV422P10LE("PIX_FMT_YUV422P10LE"),
    YUV444P9BE("PIX_FMT_YUV444P9BE"),
    YUV444P9LE("PIX_FMT_YUV444P9LE"),
    YUV444P10BE("PIX_FMT_YUV444P10BE"),
    YUV444P10LE("PIX_FMT_YUV444P10LE"),
    YUV422P9BE("PIX_FMT_YUV422P9BE"),
    YUV422P9LE("PIX_FMT_YUV422P9LE"),
    VDA_VLD("PIX_FMT_VDA_VLD"),
    GBRP("PIX_FMT_GBRP"),
    GBRP9BE("PIX_FMT_GBRP9BE"),
    GBRP9LE("PIX_FMT_GBRP9LE"),
    GBRP10BE("PIX_FMT_GBRP10BE"),
    GBRP10LE("PIX_FMT_GBRP10LE"),
    GBRP16BE("PIX_FMT_GBRP16BE"),
    GBRP16LE("PIX_FMT_GBRP16LE"),
    YUVA422P("PIX_FMT_YUVA422P"),
    YUVA444P("PIX_FMT_YUVA444P"),
    YUVA420P9BE("PIX_FMT_YUVA420P9BE"),
    YUVA420P9LE("PIX_FMT_YUVA420P9LE"),
    YUVA422P9BE("PIX_FMT_YUVA422P9BE"),
    YUVA422P9LE("PIX_FMT_YUVA422P9LE"),
    YUVA444P9BE("PIX_FMT_YUVA444P9BE"),
    YUVA444P9LE("PIX_FMT_YUVA444P9LE"),
    YUVA420P10BE("PIX_FMT_YUVA420P10BE"),
    YUVA420P10LE("PIX_FMT_YUVA420P10LE"),
    YUVA422P10BE("PIX_FMT_YUVA422P10BE"),
    YUVA422P10LE("PIX_FMT_YUVA422P10LE"),
    YUVA444P10BE("PIX_FMT_YUVA444P10BE"),
    YUVA444P10LE("PIX_FMT_YUVA444P10LE"),
    YUVA420P16BE("PIX_FMT_YUVA420P16BE"),
    YUVA420P16LE("PIX_FMT_YUVA420P16LE"),
    YUVA422P16BE("PIX_FMT_YUVA422P16BE"),
    YUVA422P16LE("PIX_FMT_YUVA422P16LE"),
    YUVA444P16BE("PIX_FMT_YUVA444P16BE"),
    YUVA444P16LE("PIX_FMT_YUVA444P16LE"),
    VDPAU("PIX_FMT_VDPAU"),
    XYZ12LE("PIX_FMT_XYZ12LE"),
    XYZ12BE("PIX_FMT_XYZ12BE"),
    NV16("PIX_FMT_NV16"),
    NV20LE("PIX_FMT_NV20LE"),
    NV20BE("PIX_FMT_NV20BE"),
    NB("PIX_FMT_NB");
    
    private static final Map<Integer, PixelFormat> idMap;
    
    static {
        idMap = new HashMap<Integer, PixelFormat>();
        for (PixelFormat pixFmt : PixelFormat.values())
            idMap.put(pixFmt.value(), pixFmt);
    }
    
    private int value;
    
    private PixelFormat(String fieldName) {
        value = PixelFormatMapper.getInstance().pixelFormatValue(fieldName);
    }
    
    public int value() {
        return value;
    }
    
    public static PixelFormat valueOf(int value) {
        return idMap.get(value);
    }
    
    private static class PixelFormatMapper {
        
        private static final PixelFormatMapper mapper = new PixelFormatMapper();
        
        private final Map<String, Integer> pixelFormatFieldMap;
        private final int NONE_VALUE;
        
        private PixelFormatMapper() {
            pixelFormatFieldMap = new HashMap<String, Integer>();
            NONE_VALUE = org.libav.avutil.bridge.PixelFormat.PIX_FMT_NONE;
            parseFields(org.libav.avutil.bridge.PixelFormat.class.getDeclaredFields());
        }
        
        private void parseFields(Field[] fields) {
            Pattern codecIdPattern = Pattern.compile("(PIX_FMT_[0-9A-Za-z_]+)");
            Matcher matcher;
            int mods;
            
            for (Field field : fields) {
                mods = field.getModifiers();
                if (Modifier.isStatic(mods) && Modifier.isFinal(mods)
                        && Modifier.isPublic(mods)) {
                    matcher = codecIdPattern.matcher(field.getName());
                    if (matcher.matches()) {
                        try {
                            pixelFormatFieldMap.put(matcher.group(1), field.getInt(null));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
        
        public int pixelFormatValue(String fieldName) {
            Integer val = pixelFormatFieldMap.get(fieldName);
            if (val == null)
                return NONE_VALUE;
            
            return val;
        }
        
        public static PixelFormatMapper getInstance() {
            return mapper;
        }
        
    }
    
}

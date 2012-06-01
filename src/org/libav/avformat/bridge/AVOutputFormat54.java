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
package org.libav.avformat.bridge;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * Mirror of the native AVOutputFormat struct for the libavformat v54.x.x. This 
 * class contains only the public API fields, the real AVOutputFormat struct is 
 * bigger. For details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVOutputFormat54 extends Structure {
    
    public Pointer name;
    public Pointer long_name;
    public Pointer mime_type;
    public Pointer extensions;
    public int audio_codec;
    public int video_codec;
    public int subtitle_codec;
    public int flags;
    public Pointer codec_tag;
    public Pointer priv_class;
    
    public AVOutputFormat54() {
        super();
        initFieldOrder();
    }
    
    public AVOutputFormat54(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new String[] { 
            "name", 
            "long_name", 
            "mime_type", 
            "extensions", 
            "audio_codec", 
            "video_codec", 
            "subtitle_codec", 
            "flags", 
            "codec_tag", 
            "priv_class"
        });
    }
    
    public static class ByReference extends AVOutputFormat54 implements Structure.ByReference { }
    public static class ByValue extends AVOutputFormat54 implements Structure.ByValue { }
        
}

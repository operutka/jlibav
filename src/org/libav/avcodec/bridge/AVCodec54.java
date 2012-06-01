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
package org.libav.avcodec.bridge;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * Mirror of the native AVCodec struct for the libavcodec v54.x.x. This class 
 * contains only the public API fields, the real AVCodec struct is bigger. For 
 * details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVCodec54 extends Structure {
    
    public Pointer name;
    public Pointer long_name;
    public int type;
    public int id;
    public int capabilities;
    public Pointer supported_framerates; 
    public Pointer pix_fmts;       
    public Pointer supported_samplerates;       
    public Pointer sample_fmts; 
    public Pointer channel_layouts;         
    public byte max_lowres;                     
    public Pointer priv_class;              
    public Pointer profiles;
    
    public AVCodec54() {
        super();
        initFieldOrder();
    }

    public AVCodec54(Pointer p) {
        super(p);
        initFieldOrder();
    }

    private void initFieldOrder() {
        setFieldOrder(new String[] {
            "name", 
            "long_name", 
            "type", 
            "id", 
            "capabilities", 
            "supported_framerates", 
            "pix_fmts", 
            "supported_samplerates", 
            "sample_fmts", 
            "channel_layouts", 
            "max_lowres", 
            "priv_class", 
            "profiles",
        });
    }
    
    public static class ByReference extends AVCodec54 implements Structure.ByReference { };
    public static class ByValue extends AVCodec54 implements Structure.ByValue { };
    
}

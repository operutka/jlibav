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
import org.libav.avcodec.bridge.AVPacket;
import org.libav.avutil.bridge.AVRational;

/**
 * Mirror of the native AVStream struct for the libavformat v54.x.x. This class 
 * contains only the public API fields, the real AVStream struct is bigger. For 
 * details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVStream54 extends Structure {
    
    public int index;
    public int id;
    public Pointer codec;
    public AVRational r_frame_rate;
    public Pointer priv_data;
    public AVFrac pts;
    public AVRational time_base;
    public long start_time;
    public long duration;
    public long nb_frames;
    public int disposition;
    public int discard;
    public AVRational sample_aspect_ratio;
    public Pointer metadata;
    public AVRational avg_frame_rate;
    public AVPacket attached_pic;
    
    public AVStream54() {
        super();
        initFieldOrder();
    }
    
    public AVStream54(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new String[] {
            "index", 
            "id", 
            "codec", 
            "r_frame_rate", 
            "priv_data", 
            "pts", 
            "time_base", 
            "start_time", 
            "duration", 
            "nb_frames", 
            "disposition", 
            "discard", 
            "sample_aspect_ratio", 
            "metadata", 
            "avg_frame_rate", 
            "attached_pic"
        });
    }
    
    public static class ByReference extends AVStream54 implements Structure.ByReference { }
    public static class ByValue extends AVStream54 implements Structure.ByValue { }
    
}

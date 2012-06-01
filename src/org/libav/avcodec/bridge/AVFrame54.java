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
import org.libav.avutil.bridge.AVRational;

/**
 * Mirror of the native AVFrame struct for the libavcodec v54.x.x. For details 
 * see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVFrame54 extends Structure {
    
    public Pointer[] data = new Pointer[8];
    public int[] linesize = new int[8];
    public Pointer extended_data;
    public int width;
    public int height;
    public int nb_samples;
    public int format;
    public int key_frame;
    public int pict_type;
    public Pointer[] base = new Pointer[8];
    public AVRational sample_aspect_ratio;
    public long pts;
    public long pkt_pts;
    public long pkt_dts;
    public int coded_picture_number;
    public int display_picture_number;
    public int quality;
    public int reference;
    public Pointer qscale_table;
    public int qstride;
    public int qscale_type;
    public Pointer mbskip_table;
    public Pointer[] motion_val = new Pointer[2];
    public Pointer mb_type;
    public Pointer dct_coeff;
    public Pointer[] ref_index = new Pointer[2];
    public Pointer opaque;
    public long[] error = new long[8];
    public int type;
    public int repeat_pict;
    public int interlaced_frame;
    public int top_field_first;
    public int palette_has_changed;
    public int buffer_hints;
    public Pointer pan_scan;
    public long reordered_opaque;
    public Pointer hwaccel_picture_private;
    public Pointer owner;
    public Pointer thread_opaque;
    public byte motion_subsample_log2;
    
    public AVFrame54() {
        super();
        initFieldOrder();
    }

    public AVFrame54(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new String[] {
            "data", 
            "linesize", 
            "extended_data", 
            "width", 
            "height", 
            "nb_samples", 
            "format", 
            "key_frame", 
            "pict_type", 
            "base", 
            "sample_aspect_ratio", 
            "pts", 
            "pkt_pts", 
            "pkt_dts", 
            "coded_picture_number", 
            "display_picture_number", 
            "quality", 
            "reference", 
            "qscale_table", 
            "qstride", 
            "qscale_type", 
            "mbskip_table", 
            "motion_val", 
            "mb_type", 
            "dct_coeff", 
            "ref_index", 
            "opaque", 
            "error", 
            "type", 
            "repeat_pict", 
            "interlaced_frame", 
            "top_field_first", 
            "palette_has_changed", 
            "buffer_hints", 
            "pan_scan", 
            "reordered_opaque", 
            "hwaccel_picture_private", 
            "owner", 
            "thread_opaque", 
            "motion_subsample_log2"
        });
    }
    
    public static class ByReference extends AVFrame54 implements Structure.ByReference { }
    public static class ByValue extends AVFrame54 implements Structure.ByValue { }
    
}

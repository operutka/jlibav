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
 * Mirror of the native AVFrame struct for the libavcodec v53.x.x. For details 
 * see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVFrame53 extends Structure {
    
    public Pointer[] data = new Pointer[4];
    public int[] linesize = new int[4];
    public Pointer[] base = new Pointer[4];
    public int key_frame;
    /**
     * @see AVPictureType
     */
    public int pict_type;
    public long pts;
    public int coded_picture_number;
    public int display_picture_number;
    public int quality;
    public int age;
    public int reference;
    public Pointer qscale_table;
    public int qstride;
    public Pointer mbskip_table;
    public Pointer[] motion_val = new Pointer[2];
    public Pointer mb_type;
    public byte motion_subsample_log2;
    public Pointer opaque;
    public long[] error = new long[4];
    public int type;
    public int repeat_pict;
    public int qscale_type;
    public int interlaced_frame;
    public int top_field_first;
    public Pointer pan_scan;
    public int palette_has_changed;
    public int buffer_hints;
    public Pointer dct_coeff;
    public Pointer[] ref_index = new Pointer[2];
    public long reordered_opaque;
    public Pointer hwaccel_picture_private;
    public long pkt_pts;
    public long pkt_dts;
    public Pointer owner;
    public Pointer thread_opaque;
    public int nb_samples;
    public Pointer extended_data;
    public AVRational sample_aspect_ratio;
    public int width;
    public int height;
    public int format;
    
    public AVFrame53() {
        super();
        initFieldOrder();
    }

    public AVFrame53(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[] {
            "data", 
            "linesize", 
            "base", 
            "key_frame", 
            "pict_type", 
            "pts", 
            "coded_picture_number", 
            "display_picture_number", 
            "quality", 
            "age", 
            "reference", 
            "qscale_table", 
            "qstride", 
            "mbskip_table", 
            "motion_val", 
            "mb_type", 
            "motion_subsample_log2", 
            "opaque", 
            "error", 
            "type", 
            "repeat_pict", 
            "qscale_type", 
            "interlaced_frame", 
            "top_field_first", 
            "pan_scan", 
            "palette_has_changed", 
            "buffer_hints", 
            "dct_coeff", 
            "ref_index", 
            "reordered_opaque", 
            "hwaccel_picture_private", 
            "pkt_pts", 
            "pkt_dts", 
            "owner", 
            "thread_opaque", 
            "nb_samples",
            "extended_data",
            "sample_aspect_ratio",
            "width",
            "height",
            "format"
        });
    }
    
    public static class ByReference extends AVFrame53 implements Structure.ByReference { };
    public static class ByValue extends AVFrame53 implements Structure.ByValue { };
    
}

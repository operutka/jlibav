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
 * Mirror of the native AVStream struct for the libavformat v53.x.x. For details
 * see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVStream53 extends Structure {
    
    public static final int MAX_STD_TIMEBASES = 60 * 12 + 5;
    public static final int MAX_REORDER_DELAY = 16;
    
    public int index;
    public int id;
    public Pointer codec;
    public AVRational r_frame_rate;
    public Pointer priv_data;
    public long first_dts;
    public AVFrac pts;
    public AVRational time_base;
    public int pts_wrap_bits;
    public int stream_copy;
    /**
     * @see AVDiscard
     */
    public int discard;
    public float quality;
    public long start_time;
    public long duration;
    /**
     * @see AVStreamParseType
     */
    public int need_parsing;
    public Pointer parser;
    public long cur_dts;
    public int last_IP_duration;
    public long last_IP_pts;
    public Pointer index_entries;
    public int nb_index_entries;
    public int index_entries_allocated_size;
    public long nb_frames;
    public int disposition;
    public AVProbeData probe_data;
    public long[] pts_buffer = new long[MAX_REORDER_DELAY + 1];
    public AVRational sample_aspect_ratio;
    public Pointer metadata;
    public Pointer cur_ptr;
    public int cur_len;
    public AVPacket cur_pkt;
    public long reference_dts;
    public int probe_packets;
    public Pointer last_in_packet_buffer;
    public AVRational avg_frame_rate;
    public int codec_info_nb_frames;
    public Info info;

    public AVStream53() {
        super();
        initFieldOrder();
    }

    public AVStream53(Pointer p) {
        super(p);
        initFieldOrder();
    }

    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"index", "id", "codec", "r_frame_rate", "priv_data", "first_dts", "pts", "time_base", "pts_wrap_bits", "stream_copy", "discard", "quality", "start_time", "duration", "need_parsing", "parser", "cur_dts", "last_IP_duration", "last_IP_pts", "index_entries", "nb_index_entries", "index_entries_allocated_size", "nb_frames", "disposition", "probe_data", "pts_buffer", "sample_aspect_ratio", "metadata", "cur_ptr", "cur_len", "cur_pkt", "reference_dts", "probe_packets", "last_in_packet_buffer", "avg_frame_rate", "codec_info_nb_frames", "info"});
    }

    public static class Info extends Structure {
        public long last_dts;
        public long duration_gcd;
        public int duration_count;
        public double[] duration_error = new double[MAX_STD_TIMEBASES];
        public long codec_info_duration;

        public Info() {
            super();
            initFieldOrder();
        }

        public Info(long last_dts, long duration_gcd, int duration_count, double[] duration_error, long codec_info_duration) {
            super();
            this.last_dts = last_dts;
            this.duration_gcd = duration_gcd;
            this.duration_count = duration_count;
            if (duration_error.length != this.duration_error.length) 
                    throw new IllegalArgumentException("Wrong array size !");
            this.duration_error = duration_error;
            this.codec_info_duration = codec_info_duration;
            initFieldOrder();
        }

        private void initFieldOrder() {
            setFieldOrder(new java.lang.String[]{"last_dts", "duration_gcd", "duration_count", "duration_error", "codec_info_duration"});
        }

        public static class ByReference extends Info implements Structure.ByReference { }
        public static class ByValue extends Info implements Structure.ByValue { }
    }

    public static class ByReference extends AVStream53 implements Structure.ByReference { }
    public static class ByValue extends AVStream53 implements Structure.ByValue { }
        
}

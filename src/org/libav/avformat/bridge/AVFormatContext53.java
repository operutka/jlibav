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
 * Mirror of the native AVFormatContext struct from the libavformat v53.x.x. 
 * For details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVFormatContext53 extends Structure {
    
    public static final int MAX_FILENAME_LENGTH = 1024;
    
    public Pointer av_class;
    public Pointer iformat;
    public Pointer oformat;
    public Pointer priv_data;
    public Pointer pb;
    public int nb_streams;
    public Pointer streams;
    public byte[] filename = new byte[MAX_FILENAME_LENGTH];
    public int ctx_flags;
    public Pointer packet_buffer;
    public long start_time;
    public long duration;
    public long file_size;
    public int bit_rate;
    public Pointer cur_st;
    public long data_offset;
    public int mux_rate;
    public int packet_size;
    public int preload;
    public int max_delay;
    public int flags;
    public int probesize;
    public int max_analyze_duration;
    public Pointer key;
    public int keylen;
    public int nb_programs;
    public Pointer programs;
    /**
     * @see CodecID
     */
    public int video_codec_id;
    /**
     * @see CodecID
     */
    public int audio_codec_id;
    /**
     * @see CodecID
     */
    public int subtitle_codec_id;
    public int max_index_size;
    public int max_picture_buffer;
    public int nb_chapters;
    public Pointer chapters;
    public int debug;
    public Pointer raw_packet_buffer;
    public Pointer raw_packet_buffer_end;
    public Pointer packet_buffer_end;
    public Pointer metadata;
    public int raw_packet_buffer_remaining_size;
    public long start_time_realtime;
    public int fps_probe_size;
    public int error_recognition;

    public AVFormatContext53() {
        super();
        initFieldOrder();
    }

    public AVFormatContext53(Pointer p) {
        super(p);
        initFieldOrder();
    }

    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"av_class", "iformat", "oformat", "priv_data", "pb", "nb_streams", "streams", "filename", "ctx_flags", "packet_buffer", "start_time", "duration", "file_size", "bit_rate", "cur_st", "data_offset", "mux_rate", "packet_size", "preload", "max_delay", "flags", "probesize", "max_analyze_duration", "key", "keylen", "nb_programs", "programs", "video_codec_id", "audio_codec_id", "subtitle_codec_id", "max_index_size", "max_picture_buffer", "nb_chapters", "chapters", "debug", "raw_packet_buffer", "raw_packet_buffer_end", "packet_buffer_end", "metadata", "raw_packet_buffer_remaining_size", "start_time_realtime", "fps_probe_size", "error_recognition"});
    }

    public static class ByReference extends AVFormatContext53 implements Structure.ByReference { };
    public static class ByValue extends AVFormatContext53 implements Structure.ByValue { };
    
}

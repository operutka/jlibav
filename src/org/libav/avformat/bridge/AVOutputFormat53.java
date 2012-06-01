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

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * Mirror of the native AVOutputFormat struct for the libavformat v53.x.x. For 
 * details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVOutputFormat53 extends Structure {
    
    public Pointer name;
    public Pointer long_name;
    public Pointer mime_type;
    public Pointer extensions;
    public int priv_data_size;
    /**
     * @see CodecID
     */
    public int audio_codec;
    /**
     * @see CodecID
     */
    public int video_codec;
    public WriteHeaderCallback write_header;
    public WritePacketCallback write_packet;
    public WriteTrailerCallback write_trailer;
    public int flags;
    public SetParametersCallback set_parameters;
    public InterleavePacketCallback interleave_packet;
    public Pointer codec_tag;
    /**
     * @see CodecID
     */
    public int subtitle_codec;
    public Pointer priv_class;
    public QueryCodecCallback query_codec;
    
    public Pointer next;
    
    public interface WriteHeaderCallback extends Callback {
        int apply(Pointer formatContext);
    };
    public interface WritePacketCallback extends Callback {
        int apply(Pointer formatContext, Pointer packet);
    };
    public interface WriteTrailerCallback extends Callback {
        int apply(Pointer formatContext);
    };
    public interface SetParametersCallback extends Callback {
        int apply(Pointer formatContext, Pointer formatParameters);
    };
    public interface InterleavePacketCallback extends Callback {
        int apply(Pointer formatContext, Pointer out, Pointer in, int flush);
    };
    public interface QueryCodecCallback extends Callback {
        /**
         * @param id codec ID @see CodecID
         * @param std_compliance
         * @return 
         */
        int apply(int id, int stdCompliance);
    };
    
    public AVOutputFormat53() {
        super();
        initFieldOrder();
    }
    
    public AVOutputFormat53(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"name", "long_name", "mime_type", "extensions", "priv_data_size", "audio_codec", "video_codec", "write_header", "write_packet", "write_trailer", "flags", "set_parameters", "interleave_packet", "codec_tag", "subtitle_codec", "priv_class", "query_codec", "next"});
    }
    
    public static class ByReference extends AVOutputFormat53 implements Structure.ByReference { };
    public static class ByValue extends AVOutputFormat53 implements Structure.ByValue { };
    
}

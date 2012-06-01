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

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

/**
 * Mirror of the native AVCodec struct for the libavcodec v53.x.x. For details 
 * see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVCodec53 extends Structure {
    
    public Pointer name;
    /**
     * @see AVMediaType
     */
    public int type;
    /**
     * @see CodecID
     */
    public int id;
    public int priv_data_size;
    public Pointer init;
    public Pointer encode;
    public Pointer close;
    public Pointer decode;
    public int capabilities;
    public Pointer next;
    public Pointer flush;
    public Pointer supported_framerates;
    /**
     * @see PixelFormat
     */
    public Pointer pix_fmts;
    public Pointer long_name;
    public Pointer supported_samplerates;
    /**
     * @see AVSampleFormat
     */
    public Pointer sample_fmts;
    public Pointer channel_layouts;
    public byte max_lowres;
    public Pointer priv_class;
    public Pointer profiles;
    public Pointer init_thread_copy;
    public Pointer update_thread_context;
    public Pointer defaults;

    public AVCodec53() {
        super();
        initFieldOrder();
    }

    public AVCodec53(Pointer p) {
        super(p);
        initFieldOrder();
    }

    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"name", "type", "id", "priv_data_size", "init", "encode", "close", "decode", "capabilities", "next", "flush", "supported_framerates", "pix_fmts", "long_name", "supported_samplerates", "sample_fmts", "channel_layouts", "max_lowres", "priv_class", "profiles", "init_thread_copy", "update_thread_context", "defaults"});
    }

    public interface AVCodecCallback extends Callback {
        int invoke(Pointer context);
    };

    public interface EncodeCallback extends Callback {
        int invoke(Pointer context, Pointer buf, int buf_size, Pointer data);
    };

    public interface DecodeCallback extends Callback {
        int invoke(Pointer context, Pointer outdata, IntByReference outdata_size, Pointer avpkt);
    };

    public interface UpdateThreadContextCallback extends Callback {
        int invoke(Pointer dst, Pointer src);
    };

    public static class ByReference extends AVCodec53 implements Structure.ByReference { };
    public static class ByValue extends AVCodec53 implements Structure.ByValue { };
    
}

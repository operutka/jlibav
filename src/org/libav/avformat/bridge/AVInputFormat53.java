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
import com.sun.jna.ptr.LongByReference;

/**
 * Mirror of the native AVInputFormat struct for the libavformat v53.x.x. For 
 * details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVInputFormat53 extends Structure {
    
    public Pointer name;
    public Pointer long_name;
    public int priv_data_size;
    public ReadProbeCallback read_probe;
    public ReadHeaderCallback read_header;
    public ReadPacketCallback read_packet;
    public ReadCloseCallback read_close;
    public ReadSeekCallback read_seek;
    public ReadTimestampCallback read_timestamp;
    public int flags;
    public Pointer extensions;
    public int value;
    public ReadPlayPauseCallback read_play;
    public ReadPlayPauseCallback read_pause;
    public Pointer codec_tag;
    public ReadSeek2Callback read_seek2;
    public Pointer metadata_conv;
    public Pointer priv_class;
    public AVInputFormat53.ByReference next;
    
    public AVInputFormat53() {
        super();
        initFieldOrder();
    }

    public AVInputFormat53(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"name", "long_name", "priv_data_size", "read_probe", "read_header", "read_packet", "read_close", "read_seek", "read_timestamp", "flags", "extensions", "value", "read_play", "read_pause", "codec_tag", "read_seek2", "metadata_conv", "priv_class", "next"});
    }
    
    public interface ReadProbeCallback extends Callback {
        int apply(Pointer probeData);
    }
    public interface ReadHeaderCallback extends Callback {
        int apply(Pointer formatContext, Pointer ap);
    }
    public interface ReadPacketCallback extends Callback {
        int apply(Pointer formatContext, Pointer pkt);
    }
    public interface ReadCloseCallback extends Callback {
        int apply(Pointer formatContext);
    }
    public interface ReadSeekCallback extends Callback {
	int apply(Pointer formatContext, int streamIndex, long timestamp, int flags);
    }
    public interface ReadTimestampCallback extends Callback {
        long apply(Pointer formatContext, int streamIndex, LongByReference pos, long posLimit);
    }
    public interface ReadPlayPauseCallback extends Callback {
        int apply(Pointer formatContext);
    }
    public interface ReadSeek2Callback extends Callback {
        int apply(Pointer formatContext, int streamIndex, long minTs, long ts, long maxTs, int flags);
    }
    
    public static class ByReference extends AVInputFormat53 implements Structure.ByReference { }
    public static class ByValue extends AVInputFormat53 implements Structure.ByValue { }
    
}

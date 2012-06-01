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
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * Mirror of the native AVIOContext struct from the libavformat v53.x.x and 
 * 54.x.x. For details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVIOContext extends Structure {
    
    public Pointer av_class;
    public Pointer buffer;
    public int buffer_size;
    public Pointer buf_ptr;
    public Pointer buf_end;
    public Pointer opaque;
    public PacketCallback read_packet;
    public PacketCallback write_packet;
    public SeekCallback seek;
    public long pos;
    public int must_flush;
    public int eof_reached;
    public int write_flag;
    public int max_packet_size;
    public NativeLong checksum;
    public Pointer checksum_ptr;
    public UpdateChecksumCallback update_checksum;
    public int error;
    public ReadPauseCallback read_pause;
    public ReadSeekCallback read_seek;
    public int seekable;
    
    public AVIOContext() {
        super();
        initFieldOrder();
    }
    
    public AVIOContext(Pointer ptr) {
        super(ptr);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"av_class", "buffer", "buffer_size", "buf_ptr", "buf_end", "opaque", "read_packet", "write_packet", "seek", "pos", "must_flush", "eof_reached", "write_flag", "max_packet_size", "checksum", "checksum_ptr", "update_checksum", "error", "read_pause", "read_seek", "seekable"});
    }
    
    public interface PacketCallback extends Callback {
        int apply(Pointer opaque, Pointer buf, int buf_size);
    }
    
    public interface SeekCallback extends Callback {
        long apply(Pointer opaque, long offset, int whence);
    }
    
    public interface UpdateChecksumCallback extends Callback {
        NativeLong apply(NativeLong checksum, Pointer buf, int size);
    }
    
    public interface ReadPauseCallback extends Callback {
        int apply(Pointer opaque, int pause);
    }
    
    public interface ReadSeekCallback extends Callback {
        long apply(Pointer opaque, int stream_index, long timestamp, int flags);
    }
    
    public static class ByReference extends AVIOContext implements Structure.ByReference { };
    public static class ByValue extends AVIOContext implements Structure.ByValue { };
}

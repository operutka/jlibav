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

/**
 * Mirror of the native AVPacket struct for the libavcodec v53.x.x and v54.x.x. 
 * For details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVPacket extends Structure {
    
    public long pts;
    public long dts;
    public Pointer data;
    public int size;
    public int stream_index;
    public int flags;
    public Pointer side_data;
    public int side_data_elems;
    public int duration;
    public Pointer destruct;
    public Pointer priv;
    public long pos;
    public long convergence_duration;
    
    public AVPacket() {
        super();
        initFieldOrder();
    }
    
    public AVPacket(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"pts", "dts", "data", "size", "stream_index", "flags", "side_data", "side_data_elems", "duration", "destruct", "priv", "pos", "convergence_duration"});
    }
    
    public static class SideData extends Structure {
        public Pointer data;
        public int size;
        /** 
         * @see AVPacketSideDataType
         */
        public int type;

        public SideData() {
            super();
            initFieldOrder();
        }
        
        public SideData(Pointer data, int size, int type) {
            super();
            this.data = data;
            this.size = size;
            this.type = type;
            initFieldOrder();
        }
        
        private void initFieldOrder() {
            setFieldOrder(new java.lang.String[]{"data", "size", "type"});
        }
        
        public static class ByReference extends SideData implements Structure.ByReference { }
        public static class ByValue extends SideData implements Structure.ByValue { }
    }
    
    public interface DestructCallback extends Callback {
        void invoke(Pointer avPacket);
    }
    
    public static class ByReference extends AVPacket implements Structure.ByReference { }
    public static class ByValue extends AVPacket implements Structure.ByValue { }
}

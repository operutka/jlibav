/*
 * Copyright (C) 2013 Ondrej Perutka
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

import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVPacket struct for the libavcodec v55.x.x. For details 
 * see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVPacket55 extends StructObject {

    public AVPacket55() {
        super();
    }

    public AVPacket55(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<?> buf() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVPacket55 buf(Pointer<?> buf) {
        this.io.setPointerField(this, 0, buf);
        return this;
    }

    @Field(1)
    public long pts() {
        return this.io.getLongField(this, 1);
    }

    @Field(1)
    public AVPacket55 pts(long pts) {
        this.io.setLongField(this, 1, pts);
        return this;
    }

    @Field(2)
    public long dts() {
        return this.io.getLongField(this, 2);
    }

    @Field(2)
    public AVPacket55 dts(long dts) {
        this.io.setLongField(this, 2, dts);
        return this;
    }

    @Field(3)
    public Pointer<Byte> data() {
        return this.io.getPointerField(this, 3);
    }

    @Field(3)
    public AVPacket55 data(Pointer<Byte> data) {
        this.io.setPointerField(this, 3, data);
        return this;
    }

    @Field(4)
    public int size() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public AVPacket55 size(int size) {
        this.io.setIntField(this, 4, size);
        return this;
    }

    @Field(5)
    public int stream_index() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public AVPacket55 stream_index(int stream_index) {
        this.io.setIntField(this, 5, stream_index);
        return this;
    }

    @Field(6)
    public int flags() {
        return this.io.getIntField(this, 6);
    }

    @Field(6)
    public AVPacket55 flags(int flags) {
        this.io.setIntField(this, 6, flags);
        return this;
    }

    @Field(7)
    public Pointer<AVPacket55.SideData> side_data() {
        return this.io.getPointerField(this, 7);
    }

    @Field(7)
    public AVPacket55 side_data(Pointer<AVPacket55.SideData> side_data) {
        this.io.setPointerField(this, 7, side_data);
        return this;
    }

    @Field(8)
    public int side_data_elems() {
        return this.io.getIntField(this, 8);
    }

    @Field(8)
    public AVPacket55 side_data_elems(int side_data_elems) {
        this.io.setIntField(this, 8, side_data_elems);
        return this;
    }

    @Field(9)
    public int duration() {
        return this.io.getIntField(this, 9);
    }

    @Field(9)
    public AVPacket55 duration(int duration) {
        this.io.setIntField(this, 9, duration);
        return this;
    }

    @Field(10)
    public Pointer<AVPacket55.DestructCallback> destruct() {
        return this.io.getPointerField(this, 10);
    }

    @Field(10)
    public AVPacket55 destruct(Pointer<AVPacket55.DestructCallback> destruct) {
        this.io.setPointerField(this, 10, destruct);
        return this;
    }

    @Field(11)
    public Pointer<?> priv() {
        return this.io.getPointerField(this, 11);
    }

    @Field(11)
    public AVPacket55 priv(Pointer<?> priv) {
        this.io.setPointerField(this, 11, priv);
        return this;
    }

    @Field(12)
    public long pos() {
        return this.io.getLongField(this, 12);
    }

    @Field(12)
    public AVPacket55 pos(long pos) {
        this.io.setLongField(this, 12, pos);
        return this;
    }

    @Field(13)
    public long convergence_duration() {
        return this.io.getLongField(this, 13);
    }

    @Field(13)
    public AVPacket55 convergence_duration(long convergence_duration) {
        this.io.setLongField(this, 13, convergence_duration);
        return this;
    }

    public static class SideData extends StructObject {
        public SideData() {
            super();
        }

        public SideData(Pointer pointer) {
            super(pointer);
        }

        @Field(0)
        public Pointer<Byte> data() {
            return this.io.getPointerField(this, 0);
        }

        @Field(0)
        public SideData data(Pointer<Byte> data) {
            this.io.setPointerField(this, 0, data);
            return this;
        }

        @Field(1)
        public int size() {
            return this.io.getIntField(this, 1);
        }

        @Field(1)
        public SideData size(int size) {
            this.io.setIntField(this, 1, size);
            return this;
        }

        @Field(2)
        public int type() {
            return this.io.getIntField(this, 2);
        }

        @Field(2)
        public SideData type(int type) {
            this.io.setIntField(this, 2, type);
            return this;
        }
    }

    public static abstract class DestructCallback extends Callback<DestructCallback> {
        abstract public void apply(Pointer<?> AVPacketPtr1);
    }
    
}

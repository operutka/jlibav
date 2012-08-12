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

import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVPacket struct for the libavcodec v53.x.x and v54.x.x.
 * For details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVPacket extends StructObject {

    public AVPacket() {
        super();
    }

    public AVPacket(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public long pts() {
        return this.io.getLongField(this, 0);
    }

    @Field(0)
    public AVPacket pts(long pts) {
        this.io.setLongField(this, 0, pts);
        return this;
    }

    @Field(1)
    public long dts() {
        return this.io.getLongField(this, 1);
    }

    @Field(1)
    public AVPacket dts(long dts) {
        this.io.setLongField(this, 1, dts);
        return this;
    }

    @Field(2)
    public Pointer<Byte> data() {
        return this.io.getPointerField(this, 2);
    }

    @Field(2)
    public AVPacket data(Pointer<Byte> data) {
        this.io.setPointerField(this, 2, data);
        return this;
    }

    @Field(3)
    public int size() {
        return this.io.getIntField(this, 3);
    }

    @Field(3)
    public AVPacket size(int size) {
        this.io.setIntField(this, 3, size);
        return this;
    }

    @Field(4)
    public int stream_index() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public AVPacket stream_index(int stream_index) {
        this.io.setIntField(this, 4, stream_index);
        return this;
    }

    @Field(5)
    public int flags() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public AVPacket flags(int flags) {
        this.io.setIntField(this, 5, flags);
        return this;
    }

    @Field(6)
    public Pointer<AVPacket.SideData> side_data() {
        return this.io.getPointerField(this, 6);
    }

    @Field(6)
    public AVPacket side_data(Pointer<AVPacket.SideData> side_data) {
        this.io.setPointerField(this, 6, side_data);
        return this;
    }

    @Field(7)
    public int side_data_elems() {
        return this.io.getIntField(this, 7);
    }

    @Field(7)
    public AVPacket side_data_elems(int side_data_elems) {
        this.io.setIntField(this, 7, side_data_elems);
        return this;
    }

    @Field(8)
    public int duration() {
        return this.io.getIntField(this, 8);
    }

    @Field(8)
    public AVPacket duration(int duration) {
        this.io.setIntField(this, 8, duration);
        return this;
    }

    @Field(9)
    public Pointer<AVPacket.DestructCallback> destruct() {
        return this.io.getPointerField(this, 9);
    }

    @Field(9)
    public AVPacket destruct(Pointer<AVPacket.DestructCallback> destruct) {
        this.io.setPointerField(this, 9, destruct);
        return this;
    }

    @Field(10)
    public Pointer<?> priv() {
        return this.io.getPointerField(this, 10);
    }

    @Field(10)
    public AVPacket priv(Pointer<?> priv) {
        this.io.setPointerField(this, 10, priv);
        return this;
    }

    @Field(11)
    public long pos() {
        return this.io.getLongField(this, 11);
    }

    @Field(11)
    public AVPacket pos(long pos) {
        this.io.setLongField(this, 11, pos);
        return this;
    }

    @Field(12)
    public long convergence_duration() {
        return this.io.getLongField(this, 12);
    }

    @Field(12)
    public AVPacket convergence_duration(long convergence_duration) {
        this.io.setLongField(this, 12, convergence_duration);
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
        public abstract void apply(Pointer<?> packet);
    }
    
}

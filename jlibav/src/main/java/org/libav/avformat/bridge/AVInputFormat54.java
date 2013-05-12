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

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVInputFormat struct from the libavformat v54.x.x and 
 * v55.x.x. This class contains only the public API fields, the real 
 * AVInputFormat struct is bigger. For details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVInputFormat54 extends StructObject {

    public AVInputFormat54() {
        super();
    }

    public AVInputFormat54(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<Byte> name() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVInputFormat54 name(Pointer<Byte> name) {
        this.io.setPointerField(this, 0, name);
        return this;
    }

    @Field(1)
    public Pointer<Byte> long_name() {
        return this.io.getPointerField(this, 1);
    }

    @Field(1)
    public AVInputFormat54 long_name(Pointer<Byte> long_name) {
        this.io.setPointerField(this, 1, long_name);
        return this;
    }

    @Field(2)
    public int flags() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public AVInputFormat54 flags(int flags) {
        this.io.setIntField(this, 2, flags);
        return this;
    }

    @Field(3)
    public Pointer<Byte> extensions() {
        return this.io.getPointerField(this, 3);
    }

    @Field(3)
    public AVInputFormat54 extensions(Pointer<Byte> extensions) {
        this.io.setPointerField(this, 3, extensions);
        return this;
    }

    @Field(4)
    public Pointer<Pointer<?>> codec_tag() {
        return this.io.getPointerField(this, 4);
    }

    @Field(4)
    public AVInputFormat54 codec_tag(Pointer<Pointer<?>> codec_tag) {
        this.io.setPointerField(this, 4, codec_tag);
        return this;
    }

    @Field(5)
    public Pointer<?> priv_class() {
        return this.io.getPointerField(this, 5);
    }

    @Field(5)
    public AVInputFormat54 priv_class(Pointer<?> priv_class) {
        this.io.setPointerField(this, 5, priv_class);
        return this;
    }
    
}

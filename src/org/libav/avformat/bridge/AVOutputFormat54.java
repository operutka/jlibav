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
 * Mirror of the native AVOutputFormat struct for the libavformat v54.x.x. This
 * class contains only the public API fields, the real AVOutputFormat struct is
 * bigger. For details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVOutputFormat54 extends StructObject {

    public AVOutputFormat54() {
        super();
    }

    public AVOutputFormat54(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<Byte> name() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVOutputFormat54 name(Pointer<Byte> name) {
        this.io.setPointerField(this, 0, name);
        return this;
    }

    @Field(1)
    public Pointer<Byte> long_name() {
        return this.io.getPointerField(this, 1);
    }

    @Field(1)
    public AVOutputFormat54 long_name(Pointer<Byte> long_name) {
        this.io.setPointerField(this, 1, long_name);
        return this;
    }

    @Field(2)
    public Pointer<Byte> mime_type() {
        return this.io.getPointerField(this, 2);
    }

    @Field(2)
    public AVOutputFormat54 mime_type(Pointer<Byte> mime_type) {
        this.io.setPointerField(this, 2, mime_type);
        return this;
    }

    @Field(3)
    public Pointer<Byte> extensions() {
        return this.io.getPointerField(this, 3);
    }

    @Field(3)
    public AVOutputFormat54 extensions(Pointer<Byte> extensions) {
        this.io.setPointerField(this, 3, extensions);
        return this;
    }

    @Field(4)
    public int audio_codec() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public AVOutputFormat54 audio_codec(int audio_codec) {
        this.io.setIntField(this, 4, audio_codec);
        return this;
    }

    @Field(5)
    public int video_codec() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public AVOutputFormat54 video_codec(int video_codec) {
        this.io.setIntField(this, 5, video_codec);
        return this;
    }

    @Field(6)
    public int subtitle_codec() {
        return this.io.getIntField(this, 6);
    }

    @Field(6)
    public AVOutputFormat54 subtitle_codec(int subtitle_codec) {
        this.io.setIntField(this, 6, subtitle_codec);
        return this;
    }

    @Field(7)
    public int flags() {
        return this.io.getIntField(this, 7);
    }

    @Field(7)
    public AVOutputFormat54 flags(int flags) {
        this.io.setIntField(this, 7, flags);
        return this;
    }

    @Field(8)
    public Pointer<Pointer<?>> codec_tag() {
        return this.io.getPointerField(this, 8);
    }

    @Field(8)
    public AVOutputFormat54 codec_tag(Pointer<Pointer<?>> codec_tag) {
        this.io.setPointerField(this, 8, codec_tag);
        return this;
    }

    @Field(9)
    public Pointer<?> priv_class() {
        return this.io.getPointerField(this, 9);
    }

    @Field(9)
    public AVOutputFormat54 priv_class(Pointer<?> priv_class) {
        this.io.setPointerField(this, 9, priv_class);
        return this;
    }
    
}

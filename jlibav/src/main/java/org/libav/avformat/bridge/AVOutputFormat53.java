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

import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVOutputFormat struct for the libavformat v53.x.x. For
 * details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVOutputFormat53 extends StructObject {

    public AVOutputFormat53() {
        super();
    }

    public AVOutputFormat53(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<Byte> name() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVOutputFormat53 name(Pointer<Byte> name) {
        this.io.setPointerField(this, 0, name);
        return this;
    }

    @Field(1)
    public Pointer<Byte> long_name() {
        return this.io.getPointerField(this, 1);
    }

    @Field(1)
    public AVOutputFormat53 long_name(Pointer<Byte> long_name) {
        this.io.setPointerField(this, 1, long_name);
        return this;
    }

    @Field(2)
    public Pointer<Byte> mime_type() {
        return this.io.getPointerField(this, 2);
    }

    @Field(2)
    public AVOutputFormat53 mime_type(Pointer<Byte> mime_type) {
        this.io.setPointerField(this, 2, mime_type);
        return this;
    }

    @Field(3)
    public Pointer<Byte> extensions() {
        return this.io.getPointerField(this, 3);
    }

    @Field(3)
    public AVOutputFormat53 extensions(Pointer<Byte> extensions) {
        this.io.setPointerField(this, 3, extensions);
        return this;
    }

    @Field(4)
    public int priv_data_size() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public AVOutputFormat53 priv_data_size(int priv_data_size) {
        this.io.setIntField(this, 4, priv_data_size);
        return this;
    }

    @Field(5)
    public int audio_codec() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public AVOutputFormat53 audio_codec(int audio_codec) {
        this.io.setIntField(this, 5, audio_codec);
        return this;
    }

    @Field(6)
    public int video_codec() {
        return this.io.getIntField(this, 6);
    }

    @Field(6)
    public AVOutputFormat53 video_codec(int video_codec) {
        this.io.setIntField(this, 6, video_codec);
        return this;
    }

    @Field(7)
    public Pointer<AVOutputFormat53.WriteHeaderCallback> write_header() {
        return this.io.getPointerField(this, 7);
    }

    @Field(7)
    public AVOutputFormat53 write_header(Pointer<AVOutputFormat53.WriteHeaderCallback> write_header) {
        this.io.setPointerField(this, 7, write_header);
        return this;
    }

    @Field(8)
    public Pointer<AVOutputFormat53.WritePacketCallback> write_packet() {
        return this.io.getPointerField(this, 8);
    }

    @Field(8)
    public AVOutputFormat53 write_packet(Pointer<AVOutputFormat53.WritePacketCallback> write_packet) {
        this.io.setPointerField(this, 8, write_packet);
        return this;
    }

    @Field(9)
    public Pointer<AVOutputFormat53.WriteTrailerCallback> write_trailer() {
        return this.io.getPointerField(this, 9);
    }

    @Field(9)
    public AVOutputFormat53 write_trailer(Pointer<AVOutputFormat53.WriteTrailerCallback> write_trailer) {
        this.io.setPointerField(this, 9, write_trailer);
        return this;
    }

    @Field(10)
    public int flags() {
        return this.io.getIntField(this, 10);
    }

    @Field(10)
    public AVOutputFormat53 flags(int flags) {
        this.io.setIntField(this, 10, flags);
        return this;
    }

    @Field(11)
    public Pointer<AVOutputFormat53.SetParametersCallback> set_parameters() {
        return this.io.getPointerField(this, 11);
    }

    @Field(11)
    public AVOutputFormat53 set_parameters(Pointer<AVOutputFormat53.SetParametersCallback> set_parameters) {
        this.io.setPointerField(this, 11, set_parameters);
        return this;
    }

    @Field(12)
    public Pointer<AVOutputFormat53.InterleavePacketCallback> interleave_packet() {
        return this.io.getPointerField(this, 12);
    }

    @Field(12)
    public AVOutputFormat53 interleave_packet(Pointer<AVOutputFormat53.InterleavePacketCallback> interleave_packet) {
        this.io.setPointerField(this, 12, interleave_packet);
        return this;
    }

    @Field(13)
    public Pointer<Pointer<?>> codec_tag() {
        return this.io.getPointerField(this, 13);
    }

    @Field(13)
    public AVOutputFormat53 codec_tag(Pointer<Pointer<?>> codec_tag) {
        this.io.setPointerField(this, 13, codec_tag);
        return this;
    }

    @Field(14)
    public int subtitle_codec() {
        return this.io.getIntField(this, 14);
    }

    @Field(14)
    public AVOutputFormat53 subtitle_codec(int subtitle_codec) {
        this.io.setIntField(this, 14, subtitle_codec);
        return this;
    }

    @Field(15)
    public Pointer<?> metadata_conv() {
        return this.io.getPointerField(this, 15);
    }

    @Field(15)
    public AVOutputFormat53 metadata_conv(Pointer<?> metadata_conv) {
        this.io.setPointerField(this, 15, metadata_conv);
        return this;
    }

    @Field(16)
    public Pointer<?> priv_class() {
        return this.io.getPointerField(this, 16);
    }

    @Field(16)
    public AVOutputFormat53 priv_class(Pointer<?> priv_class) {
        this.io.setPointerField(this, 16, priv_class);
        return this;
    }

    @Field(17)
    public Pointer<?> next() {
        return this.io.getPointerField(this, 17);
    }

    @Field(17)
    public AVOutputFormat53 next(Pointer<?> next) {
        this.io.setPointerField(this, 17, next);
        return this;
    }

    public static abstract class WriteHeaderCallback extends Callback<WriteHeaderCallback> {
        public abstract int apply(Pointer<?> formatContext);
    }

    public static abstract class WritePacketCallback extends Callback<WritePacketCallback> {
        public abstract int apply(Pointer<?> formatContext, Pointer<?> pkt);
    }

    public static abstract class WriteTrailerCallback extends Callback<WriteTrailerCallback> {
        public abstract int apply(Pointer<?> formatContext);
    }

    public static abstract class SetParametersCallback extends Callback<SetParametersCallback> {
        public abstract int apply(Pointer<?> formatContext, Pointer<?> formatParameters);
    }

    public static abstract class InterleavePacketCallback extends Callback<InterleavePacketCallback> {
        public abstract int apply(Pointer<?> formatContext, Pointer<?> out, Pointer<?> in, int flush);
    }
    
}

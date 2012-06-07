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
 * Mirror of the native AVCodec struct for the libavcodec v53.x.x. For details
 * see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVCodec53 extends StructObject {

    public AVCodec53() {
        super();
    }

    public AVCodec53(Pointer p) {
        super(p);
    }

    @Field(0)
    public Pointer<Byte> name() {
        return this.io.getPointerField(this, 0);
    }
    
    @Field(0)
    public AVCodec53 name(Pointer<Byte> name) {
        this.io.setPointerField(this, 0, name);
        return this;
    }

    @Field(1)
    public int type() {
        return this.io.getIntField(this, 1);
    }

    @Field(1)
    public AVCodec53 type(int type) {
        this.io.setIntField(this, 1, type);
        return this;
    }

    @Field(2)
    public int id() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public AVCodec53 id(int id) {
        this.io.setIntField(this, 2, id);
        return this;
    }

    @Field(3)
    public int priv_data_size() {
        return this.io.getIntField(this, 3);
    }

    @Field(3)
    public AVCodec53 priv_data_size(int priv_data_size) {
        this.io.setIntField(this, 3, priv_data_size);
        return this;
    }
    
    @Field(4)
    public Pointer<AVCodec53.AVCodecCallback> init() {
        return this.io.getPointerField(this, 4);
    }
    
    @Field(4)
    public AVCodec53 init(Pointer<AVCodec53.AVCodecCallback> init) {
        this.io.setPointerField(this, 4, init);
        return this;
    }
    
    @Field(5)
    public Pointer<AVCodec53.EncodeCallback> encode() {
        return this.io.getPointerField(this, 5);
    }
    
    @Field(5)
    public AVCodec53 encode(Pointer<AVCodec53.EncodeCallback> encode) {
        this.io.setPointerField(this, 5, encode);
        return this;
    }
    
    @Field(6)
    public Pointer<AVCodec53.AVCodecCallback> close() {
        return this.io.getPointerField(this, 6);
    }
    
    @Field(6)
    public AVCodec53 close(Pointer<AVCodec53.AVCodecCallback> close) {
        this.io.setPointerField(this, 6, close);
        return this;
    }
    
    @Field(7)
    public Pointer<AVCodec53.DecodeCallback> decode() {
        return this.io.getPointerField(this, 7);
    }
    
    @Field(7)
    public AVCodec53 decode(Pointer<AVCodec53.DecodeCallback> decode) {
        this.io.setPointerField(this, 7, decode);
        return this;
    }

    @Field(8)
    public int capabilities() {
        return this.io.getIntField(this, 8);
    }

    @Field(8)
    public AVCodec53 capabilities(int capabilities) {
        this.io.setIntField(this, 8, capabilities);
        return this;
    }
    
    @Field(9)
    public Pointer<?> next() {
        return this.io.getPointerField(this, 9);
    }
    
    @Field(9)
    public AVCodec53 next(Pointer<?> next) {
        this.io.setPointerField(this, 9, next);
        return this;
    }
    
    @Field(10)
    public Pointer<AVCodec53.AVCodecCallback> flush() {
        return this.io.getPointerField(this, 10);
    }
    
    @Field(10)
    public AVCodec53 flush(Pointer<AVCodec53.AVCodecCallback> flush) {
        this.io.setPointerField(this, 10, flush);
        return this;
    }
    
    @Field(11)
    public Pointer<?> supported_framerates() {
        return this.io.getPointerField(this, 11);
    }
    
    @Field(11)
    public AVCodec53 supported_framerates(Pointer<?> supported_framerates) {
        this.io.setPointerField(this, 11, supported_framerates);
        return this;
    }
    
    @Field(12)
    public Pointer<Integer> pix_fmts() {
        return this.io.getPointerField(this, 12);
    }
    
    @Field(12)
    public AVCodec53 pix_fmts(Pointer<Integer> pix_fmts) {
        this.io.setPointerField(this, 12, pix_fmts);
        return this;
    }
    
    @Field(13)
    public Pointer<Byte> long_name() {
        return this.io.getPointerField(this, 13);
    }
    
    @Field(13)
    public AVCodec53 long_name(Pointer<Byte> long_name) {
        this.io.setPointerField(this, 13, long_name);
        return this;
    }
    
    @Field(14)
    public Pointer<Integer> supported_samplerates() {
        return this.io.getPointerField(this, 14);
    }
    
    @Field(14)
    public AVCodec53 supported_samplerates(Pointer<Integer> supported_samplerates) {
        this.io.setPointerField(this, 14, supported_samplerates);
        return this;
    }
    
    @Field(15)
    public Pointer<Integer> sample_fmts() {
        return this.io.getPointerField(this, 15);
    }
    
    @Field(15)
    public AVCodec53 sample_fmts(Pointer<Integer> sample_fmts) {
        this.io.setPointerField(this, 15, sample_fmts);
        return this;
    }
    
    @Field(16)
    public Pointer<Long> channel_layouts() {
        return this.io.getPointerField(this, 16);
    }
    
    @Field(16)
    public AVCodec53 channel_layouts(Pointer<Long> channel_layouts) {
        this.io.setPointerField(this, 16, channel_layouts);
        return this;
    }

    @Field(17)
    public byte max_lowres() {
        return this.io.getByteField(this, 17);
    }

    @Field(17)
    public AVCodec53 max_lowres(byte max_lowres) {
        this.io.setByteField(this, 17, max_lowres);
        return this;
    }
    
    @Field(18)
    public Pointer<?> priv_class() {
        return this.io.getPointerField(this, 18);
    }
    
    @Field(18)
    public AVCodec53 priv_class(Pointer<?> priv_class) {
        this.io.setPointerField(this, 18, priv_class);
        return this;
    }
    
    @Field(19)
    public Pointer<?> profiles() {
        return this.io.getPointerField(this, 19);
    }
    
    @Field(19)
    public AVCodec53 profiles(Pointer<?> profiles) {
        this.io.setPointerField(this, 19, profiles);
        return this;
    }
    
    @Field(20)
    public Pointer<AVCodec53.AVCodecCallback> init_thread_copy() {
        return this.io.getPointerField(this, 20);
    }
    
    @Field(20)
    public AVCodec53 init_thread_copy(Pointer<AVCodec53.AVCodecCallback> init_thread_copy) {
        this.io.setPointerField(this, 20, init_thread_copy);
        return this;
    }
    
    @Field(21)
    public Pointer<AVCodec53.UpdateThreadContextCallback> update_thread_context() {
        return this.io.getPointerField(this, 21);
    }
    
    @Field(21)
    public AVCodec53 update_thread_context(Pointer<AVCodec53.UpdateThreadContextCallback> update_thread_context) {
        this.io.setPointerField(this, 21, update_thread_context);
        return this;
    }

    public static abstract class AVCodecCallback extends Callback<AVCodecCallback> {
        public abstract int apply(Pointer<?> context);
    }

    public static abstract class EncodeCallback extends Callback<EncodeCallback> {
        public abstract int apply(Pointer<?> context, Pointer<Byte> buf, int buf_size, Pointer<?> data);
    }

    public static abstract class DecodeCallback extends Callback<DecodeCallback> {
        public abstract int apply(Pointer<?> context, Pointer<?> outdata, Pointer<Integer> outdata_size, Pointer<?> avpkt);
    }

    public static abstract class UpdateThreadContextCallback extends Callback<UpdateThreadContextCallback> {
        public abstract int apply(Pointer<?> dst, Pointer<?> src);
    }

}

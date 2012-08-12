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

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVCodec struct for the libavcodec v54.x.x. This class 
 * contains only the public API fields, the real AVCodec struct is bigger. For 
 * details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVCodec54 extends StructObject {
    
    public AVCodec54() {
        super();
    }

    public AVCodec54(Pointer p) {
        super(p);
    }
    
    @Field(0) 
    public Pointer<Byte> name() {
        return this.io.getPointerField(this, 0);
    }
    
    @Field(0) 
    public AVCodec54 name(Pointer<Byte> name) {
        this.io.setPointerField(this, 0, name);
        return this;
    }
    
    @Field(1) 
    public Pointer<Byte> long_name() {
        return this.io.getPointerField(this, 1);
    }
    
    @Field(1) 
    public AVCodec54 long_name(Pointer<Byte> long_name) {
        this.io.setPointerField(this, 1, long_name);
        return this;
    }
    
    @Field(2) 
    public int type() {
        return this.io.getIntField(this, 2);
    }
    
    @Field(2) 
    public AVCodec54 type(int type) {
        this.io.setIntField(this, 2, type);
        return this;
    }
    
    @Field(3) 
    public int id() {
        return this.io.getIntField(this, 3);
    }
    
    @Field(3) 
    public AVCodec54 id(int id) {
        this.io.setIntField(this, 3, id);
        return this;
    }
    
    @Field(4) 
    public int capabilities() {
        return this.io.getIntField(this, 4);
    }
    
    @Field(4) 
    public AVCodec54 capabilities(int capabilities) {
        this.io.setIntField(this, 4, capabilities);
        return this;
    }
    
    @Field(5) 
    public Pointer<?> supported_framerates() {
        return this.io.getPointerField(this, 5);
    }
    
    @Field(5) 
    public AVCodec54 supported_framerates(Pointer<?> supported_framerates) {
        this.io.setPointerField(this, 5, supported_framerates);
        return this;
    }
    
    @Field(6) 
    public Pointer<Integer> pix_fmts() {
        return this.io.getPointerField(this, 6);
    }
    
    @Field(6) 
    public AVCodec54 pix_fmts(Pointer<Integer> pix_fmts) {
        this.io.setPointerField(this, 6, pix_fmts);
        return this;
    }
    
    @Field(7) 
    public Pointer<Integer> supported_samplerates() {
        return this.io.getPointerField(this, 7);
    }
    
    @Field(7) 
    public AVCodec54 supported_samplerates(Pointer<Integer> supported_samplerates) {
        this.io.setPointerField(this, 7, supported_samplerates);
        return this;
    }
    
    @Field(8) 
    public Pointer<Integer> sample_fmts() {
        return this.io.getPointerField(this, 8);
    }
    
    @Field(8) 
    public AVCodec54 sample_fmts(Pointer<Integer> sample_fmts) {
        this.io.setPointerField(this, 8, sample_fmts);
        return this;
    }
    
    @Field(9) 
    public Pointer<Long> channel_layouts() {
        return this.io.getPointerField(this, 9);
    }
    
    @Field(9) 
    public AVCodec54 channel_layouts(Pointer<Long> channel_layouts) {
        this.io.setPointerField(this, 9, channel_layouts);
        return this;
    }
    
    @Field(10) 
    public byte max_lowres() {
        return this.io.getByteField(this, 10);
    }
    
    @Field(10) 
    public AVCodec54 max_lowres(byte max_lowres) {
        this.io.setByteField(this, 10, max_lowres);
        return this;
    }
    
    @Field(11) 
    public Pointer<?> priv_class() {
        return this.io.getPointerField(this, 11);
    }
    
    @Field(11) 
    public AVCodec54 priv_class(Pointer<?> priv_class) {
        this.io.setPointerField(this, 11, priv_class);
        return this;
    }
    
    @Field(12) 
    public Pointer<?> profiles() {
        return this.io.getPointerField(this, 12);
    }
    
    @Field(12) 
    public AVCodec54 profiles(Pointer<?> profiles) {
        this.io.setPointerField(this, 12, profiles);
        return this;
    }
    
}

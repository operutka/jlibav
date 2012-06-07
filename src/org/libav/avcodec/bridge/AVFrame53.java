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
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.libav.avutil.bridge.AVRational;

/**
 * Mirror of the native AVFrame struct for the libavcodec v53.x.x. For details
 * see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVFrame53 extends StructObject {

    public AVFrame53() {
        super();
    }

    public AVFrame53(Pointer pointer) {
        super(pointer);
    }

    @Array({4})
    @Field(0)
    public Pointer<Pointer<Byte>> data() {
        return this.io.getPointerField(this, 0);
    }

    @Array({4})
    @Field(1)
    public Pointer<Integer> linesize() {
        return this.io.getPointerField(this, 1);
    }

    @Array({4})
    @Field(2)
    public Pointer<Pointer<Byte>> base() {
        return this.io.getPointerField(this, 2);
    }

    @Field(3)
    public int key_frame() {
        return this.io.getIntField(this, 3);
    }

    @Field(3)
    public AVFrame53 key_frame(int key_frame) {
        this.io.setIntField(this, 3, key_frame);
        return this;
    }

    @Field(4)
    public int pict_type() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public AVFrame53 pict_type(int pict_type) {
        this.io.setIntField(this, 4, pict_type);
        return this;
    }

    @Field(5)
    public long pts() {
        return this.io.getLongField(this, 5);
    }

    @Field(5)
    public AVFrame53 pts(long pts) {
        this.io.setLongField(this, 5, pts);
        return this;
    }

    @Field(6)
    public int coded_picture_number() {
        return this.io.getIntField(this, 6);
    }

    @Field(6)
    public AVFrame53 coded_picture_number(int coded_picture_number) {
        this.io.setIntField(this, 6, coded_picture_number);
        return this;
    }

    @Field(7)
    public int display_picture_number() {
        return this.io.getIntField(this, 7);
    }

    @Field(7)
    public AVFrame53 display_picture_number(int display_picture_number) {
        this.io.setIntField(this, 7, display_picture_number);
        return this;
    }

    @Field(8)
    public int quality() {
        return this.io.getIntField(this, 8);
    }

    @Field(8)
    public AVFrame53 quality(int quality) {
        this.io.setIntField(this, 8, quality);
        return this;
    }

    @Field(9)
    public int age() {
        return this.io.getIntField(this, 9);
    }

    @Field(9)
    public AVFrame53 age(int age) {
        this.io.setIntField(this, 9, age);
        return this;
    }

    @Field(10)
    public int reference() {
        return this.io.getIntField(this, 10);
    }

    @Field(10)
    public AVFrame53 reference(int reference) {
        this.io.setIntField(this, 10, reference);
        return this;
    }

    @Field(11)
    public Pointer<Byte> qscale_table() {
        return this.io.getPointerField(this, 11);
    }

    @Field(11)
    public AVFrame53 qscale_table(Pointer<Byte> qscale_table) {
        this.io.setPointerField(this, 11, qscale_table);
        return this;
    }

    @Field(12)
    public int qstride() {
        return this.io.getIntField(this, 12);
    }

    @Field(12)
    public AVFrame53 qstride(int qstride) {
        this.io.setIntField(this, 12, qstride);
        return this;
    }

    @Field(13)
    public Pointer<Byte> mbskip_table() {
        return this.io.getPointerField(this, 13);
    }

    @Field(13)
    public AVFrame53 mbskip_table(Pointer<Byte> mbskip_table) {
        this.io.setPointerField(this, 13, mbskip_table);
        return this;
    }

    @Array({2})
    @Field(14)
    public Pointer<Pointer<Pointer<Short>>> motion_val() {
        return this.io.getPointerField(this, 14);
    }

    @Field(15)
    public Pointer<Integer> mb_type() {
        return this.io.getPointerField(this, 15);
    }

    @Field(15)
    public AVFrame53 mb_type(Pointer<Integer> mb_type) {
        this.io.setPointerField(this, 15, mb_type);
        return this;
    }

    @Field(16)
    public byte motion_subsample_log2() {
        return this.io.getByteField(this, 16);
    }

    @Field(16)
    public AVFrame53 motion_subsample_log2(byte motion_subsample_log2) {
        this.io.setByteField(this, 16, motion_subsample_log2);
        return this;
    }

    @Field(17)
    public Pointer<?> opaque() {
        return this.io.getPointerField(this, 17);
    }

    @Field(17)
    public AVFrame53 opaque(Pointer<?> opaque) {
        this.io.setPointerField(this, 17, opaque);
        return this;
    }

    @Array({4})
    @Field(18)
    public Pointer<Long> error() {
        return this.io.getPointerField(this, 18);
    }

    @Field(19)
    public int type() {
        return this.io.getIntField(this, 19);
    }

    @Field(19)
    public AVFrame53 type(int type) {
        this.io.setIntField(this, 19, type);
        return this;
    }

    @Field(20)
    public int repeat_pict() {
        return this.io.getIntField(this, 20);
    }

    @Field(20)
    public AVFrame53 repeat_pict(int repeat_pict) {
        this.io.setIntField(this, 20, repeat_pict);
        return this;
    }

    @Field(21)
    public int qscale_type() {
        return this.io.getIntField(this, 21);
    }

    @Field(21)
    public AVFrame53 qscale_type(int qscale_type) {
        this.io.setIntField(this, 21, qscale_type);
        return this;
    }

    @Field(22)
    public int interlaced_frame() {
        return this.io.getIntField(this, 22);
    }

    @Field(22)
    public AVFrame53 interlaced_frame(int interlaced_frame) {
        this.io.setIntField(this, 22, interlaced_frame);
        return this;
    }

    @Field(23)
    public int top_field_first() {
        return this.io.getIntField(this, 23);
    }

    @Field(23)
    public AVFrame53 top_field_first(int top_field_first) {
        this.io.setIntField(this, 23, top_field_first);
        return this;
    }

    @Field(24)
    public Pointer<?> pan_scan() {
        return this.io.getPointerField(this, 24);
    }

    @Field(24)
    public AVFrame53 pan_scan(Pointer<?> pan_scan) {
        this.io.setPointerField(this, 24, pan_scan);
        return this;
    }

    @Field(25)
    public int palette_has_changed() {
        return this.io.getIntField(this, 25);
    }

    @Field(25)
    public AVFrame53 palette_has_changed(int palette_has_changed) {
        this.io.setIntField(this, 25, palette_has_changed);
        return this;
    }

    @Field(26)
    public int buffer_hints() {
        return this.io.getIntField(this, 26);
    }

    @Field(26)
    public AVFrame53 buffer_hints(int buffer_hints) {
        this.io.setIntField(this, 26, buffer_hints);
        return this;
    }

    @Field(27)
    public Pointer<Short> dct_coeff() {
        return this.io.getPointerField(this, 27);
    }

    @Field(27)
    public AVFrame53 dct_coeff(Pointer<Short> dct_coeff) {
        this.io.setPointerField(this, 27, dct_coeff);
        return this;
    }

    @Array({2})
    @Field(28)
    public Pointer<Pointer<Byte>> ref_index() {
        return this.io.getPointerField(this, 28);
    }

    @Field(29)
    public long reordered_opaque() {
        return this.io.getLongField(this, 29);
    }

    @Field(29)
    public AVFrame53 reordered_opaque(long reordered_opaque) {
        this.io.setLongField(this, 29, reordered_opaque);
        return this;
    }

    @Field(30)
    public Pointer<?> hwaccel_picture_private() {
        return this.io.getPointerField(this, 30);
    }

    @Field(30)
    public AVFrame53 hwaccel_picture_private(Pointer<?> hwaccel_picture_private) {
        this.io.setPointerField(this, 30, hwaccel_picture_private);
        return this;
    }

    @Field(31)
    public long pkt_pts() {
        return this.io.getLongField(this, 31);
    }

    @Field(31)
    public AVFrame53 pkt_pts(long pkt_pts) {
        this.io.setLongField(this, 31, pkt_pts);
        return this;
    }

    @Field(32)
    public long pkt_dts() {
        return this.io.getLongField(this, 32);
    }

    @Field(32)
    public AVFrame53 pkt_dts(long pkt_dts) {
        this.io.setLongField(this, 32, pkt_dts);
        return this;
    }

    @Field(33)
    public Pointer<?> owner() {
        return this.io.getPointerField(this, 33);
    }

    @Field(33)
    public AVFrame53 owner(Pointer<?> owner) {
        this.io.setPointerField(this, 33, owner);
        return this;
    }

    @Field(34)
    public Pointer<?> thread_opaque() {
        return this.io.getPointerField(this, 34);
    }

    @Field(34)
    public AVFrame53 thread_opaque(Pointer<?> thread_opaque) {
        this.io.setPointerField(this, 34, thread_opaque);
        return this;
    }
    
    @Field(35) 
    public int nb_samples() {
        return this.io.getIntField(this, 35);
    }
    
    @Field(35) 
    public AVFrame53 nb_samples(int nb_samples) {
        this.io.setIntField(this, 35, nb_samples);
        return this;
    }
    
    @Field(36) 
    public Pointer<Pointer<Byte>> extended_data() {
        return this.io.getPointerField(this, 36);
    }
    
    @Field(36) 
    public AVFrame53 extended_data(Pointer<Pointer<Byte>> extended_data) {
        this.io.setPointerField(this, 36, extended_data);
        return this;
    }
    
    @Field(37) 
    public AVRational sample_aspect_ratio() {
        return this.io.getNativeObjectField(this, 37);
    }
    
    @Field(37) 
    public AVFrame53 sample_aspect_ratio(AVRational sample_aspect_ratio) {
        this.io.setNativeObjectField(this, 37, sample_aspect_ratio);
        return this;
    }
    
    @Field(38) 
    public int width() {
        return this.io.getIntField(this, 38);
    }
    
    @Field(38) 
    public AVFrame53 width(int width) {
        this.io.setIntField(this, 38, width);
        return this;
    }
    
    @Field(39) 
    public int height() {
        return this.io.getIntField(this, 39);
    }
    
    @Field(39) 
    public AVFrame53 height(int height) {
        this.io.setIntField(this, 39, height);
        return this;
    }
    
    @Field(40) 
    public int format() {
        return this.io.getIntField(this, 40);
    }
    
    @Field(40) 
    public AVFrame53 format(int format) {
        this.io.setIntField(this, 40, format);
        return this;
    }
}

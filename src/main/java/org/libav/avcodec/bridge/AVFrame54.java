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
 * Mirror of the native AVFrame struct for the libavcodec v54.x.x. For details
 * see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVFrame54 extends StructObject {

    public AVFrame54() {
        super();
    }

    public AVFrame54(Pointer pointer) {
        super(pointer);
    }

    @Array({8})
    @Field(0)
    public Pointer<Pointer<Byte>> data() {
        return this.io.getPointerField(this, 0);
    }

    @Array({8})
    @Field(1)
    public Pointer<Integer> linesize() {
        return this.io.getPointerField(this, 1);
    }

    @Field(2)
    public Pointer<Pointer<Byte>> extended_data() {
        return this.io.getPointerField(this, 2);
    }

    @Field(2)
    public AVFrame54 extended_data(Pointer<Pointer<Byte>> extended_data) {
        this.io.setPointerField(this, 2, extended_data);
        return this;
    }

    @Field(3)
    public int width() {
        return this.io.getIntField(this, 3);
    }

    @Field(3)
    public AVFrame54 width(int width) {
        this.io.setIntField(this, 3, width);
        return this;
    }

    @Field(4)
    public int height() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public AVFrame54 height(int height) {
        this.io.setIntField(this, 4, height);
        return this;
    }

    @Field(5)
    public int nb_samples() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public AVFrame54 nb_samples(int nb_samples) {
        this.io.setIntField(this, 5, nb_samples);
        return this;
    }

    @Field(6)
    public int format() {
        return this.io.getIntField(this, 6);
    }

    @Field(6)
    public AVFrame54 format(int format) {
        this.io.setIntField(this, 6, format);
        return this;
    }

    @Field(7)
    public int key_frame() {
        return this.io.getIntField(this, 7);
    }

    @Field(7)
    public AVFrame54 key_frame(int key_frame) {
        this.io.setIntField(this, 7, key_frame);
        return this;
    }

    @Field(8)
    public int pict_type() {
        return this.io.getIntField(this, 8);
    }

    @Field(8)
    public AVFrame54 pict_type(int pict_type) {
        this.io.setIntField(this, 8, pict_type);
        return this;
    }

    @Array({8})
    @Field(9)
    public Pointer<Pointer<Byte>> base() {
        return this.io.getPointerField(this, 9);
    }

    @Field(10)
    public AVRational sample_aspect_ratio() {
        return this.io.getNativeObjectField(this, 10);
    }

    @Field(10)
    public AVFrame54 sample_aspect_ratio(AVRational sample_aspect_ratio) {
        this.io.setNativeObjectField(this, 10, sample_aspect_ratio);
        return this;
    }

    @Field(11)
    public long pts() {
        return this.io.getLongField(this, 11);
    }

    @Field(11)
    public AVFrame54 pts(long pts) {
        this.io.setLongField(this, 11, pts);
        return this;
    }

    @Field(12)
    public long pkt_pts() {
        return this.io.getLongField(this, 12);
    }

    @Field(12)
    public AVFrame54 pkt_pts(long pkt_pts) {
        this.io.setLongField(this, 12, pkt_pts);
        return this;
    }

    @Field(13)
    public long pkt_dts() {
        return this.io.getLongField(this, 13);
    }

    @Field(13)
    public AVFrame54 pkt_dts(long pkt_dts) {
        this.io.setLongField(this, 13, pkt_dts);
        return this;
    }

    @Field(14)
    public int coded_picture_number() {
        return this.io.getIntField(this, 14);
    }

    @Field(14)
    public AVFrame54 coded_picture_number(int coded_picture_number) {
        this.io.setIntField(this, 14, coded_picture_number);
        return this;
    }

    @Field(15)
    public int display_picture_number() {
        return this.io.getIntField(this, 15);
    }

    @Field(15)
    public AVFrame54 display_picture_number(int display_picture_number) {
        this.io.setIntField(this, 15, display_picture_number);
        return this;
    }

    @Field(16)
    public int quality() {
        return this.io.getIntField(this, 16);
    }

    @Field(16)
    public AVFrame54 quality(int quality) {
        this.io.setIntField(this, 16, quality);
        return this;
    }

    @Field(17)
    public int reference() {
        return this.io.getIntField(this, 17);
    }

    @Field(17)
    public AVFrame54 reference(int reference) {
        this.io.setIntField(this, 17, reference);
        return this;
    }

    @Field(18)
    public Pointer<Byte> qscale_table() {
        return this.io.getPointerField(this, 18);
    }

    @Field(18)
    public AVFrame54 qscale_table(Pointer<Byte> qscale_table) {
        this.io.setPointerField(this, 18, qscale_table);
        return this;
    }

    @Field(19)
    public int qstride() {
        return this.io.getIntField(this, 19);
    }

    @Field(19)
    public AVFrame54 qstride(int qstride) {
        this.io.setIntField(this, 19, qstride);
        return this;
    }

    @Field(20)
    public int qscale_type() {
        return this.io.getIntField(this, 20);
    }

    @Field(20)
    public AVFrame54 qscale_type(int qscale_type) {
        this.io.setIntField(this, 20, qscale_type);
        return this;
    }

    @Field(21)
    public Pointer<Byte> mbskip_table() {
        return this.io.getPointerField(this, 21);
    }

    @Field(21)
    public AVFrame54 mbskip_table(Pointer<Byte> mbskip_table) {
        this.io.setPointerField(this, 21, mbskip_table);
        return this;
    }

    @Array({2})
    @Field(22)
    public Pointer<Pointer<Pointer<Short>>> motion_val() {
        return this.io.getPointerField(this, 22);
    }

    @Field(23)
    public Pointer<Integer> mb_type() {
        return this.io.getPointerField(this, 23);
    }

    @Field(23)
    public AVFrame54 mb_type(Pointer<Integer> mb_type) {
        this.io.setPointerField(this, 23, mb_type);
        return this;
    }

    @Field(24)
    public Pointer<Short> dct_coeff() {
        return this.io.getPointerField(this, 24);
    }

    @Field(24)
    public AVFrame54 dct_coeff(Pointer<Short> dct_coeff) {
        this.io.setPointerField(this, 24, dct_coeff);
        return this;
    }

    @Array({2})
    @Field(25)
    public Pointer<Pointer<Byte>> ref_index() {
        return this.io.getPointerField(this, 25);
    }

    @Field(26)
    public Pointer<?> opaque() {
        return this.io.getPointerField(this, 26);
    }

    @Field(26)
    public AVFrame54 opaque(Pointer<?> opaque) {
        this.io.setPointerField(this, 26, opaque);
        return this;
    }

    @Array({8})
    @Field(27)
    public Pointer<Long> error() {
        return this.io.getPointerField(this, 27);
    }

    @Field(28)
    public int type() {
        return this.io.getIntField(this, 28);
    }

    @Field(28)
    public AVFrame54 type(int type) {
        this.io.setIntField(this, 28, type);
        return this;
    }

    @Field(29)
    public int repeat_pict() {
        return this.io.getIntField(this, 29);
    }

    @Field(29)
    public AVFrame54 repeat_pict(int repeat_pict) {
        this.io.setIntField(this, 29, repeat_pict);
        return this;
    }

    @Field(30)
    public int interlaced_frame() {
        return this.io.getIntField(this, 30);
    }

    @Field(30)
    public AVFrame54 interlaced_frame(int interlaced_frame) {
        this.io.setIntField(this, 30, interlaced_frame);
        return this;
    }

    @Field(31)
    public int top_field_first() {
        return this.io.getIntField(this, 31);
    }

    @Field(31)
    public AVFrame54 top_field_first(int top_field_first) {
        this.io.setIntField(this, 31, top_field_first);
        return this;
    }

    @Field(32)
    public int palette_has_changed() {
        return this.io.getIntField(this, 32);
    }

    @Field(32)
    public AVFrame54 palette_has_changed(int palette_has_changed) {
        this.io.setIntField(this, 32, palette_has_changed);
        return this;
    }

    @Field(33)
    public int buffer_hints() {
        return this.io.getIntField(this, 33);
    }

    @Field(33)
    public AVFrame54 buffer_hints(int buffer_hints) {
        this.io.setIntField(this, 33, buffer_hints);
        return this;
    }

    @Field(34)
    public Pointer<?> pan_scan() {
        return this.io.getPointerField(this, 34);
    }

    @Field(34)
    public AVFrame54 pan_scan(Pointer<?> pan_scan) {
        this.io.setPointerField(this, 34, pan_scan);
        return this;
    }

    @Field(35)
    public long reordered_opaque() {
        return this.io.getLongField(this, 35);
    }

    @Field(35)
    public AVFrame54 reordered_opaque(long reordered_opaque) {
        this.io.setLongField(this, 35, reordered_opaque);
        return this;
    }

    @Field(36)
    public Pointer<?> hwaccel_picture_private() {
        return this.io.getPointerField(this, 36);
    }

    @Field(36)
    public AVFrame54 hwaccel_picture_private(Pointer<?> hwaccel_picture_private) {
        this.io.setPointerField(this, 36, hwaccel_picture_private);
        return this;
    }

    @Field(37)
    public Pointer<?> owner() {
        return this.io.getPointerField(this, 37);
    }

    @Field(37)
    public AVFrame54 owner(Pointer<?> owner) {
        this.io.setPointerField(this, 37, owner);
        return this;
    }

    @Field(38)
    public Pointer<?> thread_opaque() {
        return this.io.getPointerField(this, 38);
    }

    @Field(38)
    public AVFrame54 thread_opaque(Pointer<?> thread_opaque) {
        this.io.setPointerField(this, 38, thread_opaque);
        return this;
    }

    @Field(39)
    public byte motion_subsample_log2() {
        return this.io.getByteField(this, 39);
    }

    @Field(39)
    public AVFrame54 motion_subsample_log2(byte motion_subsample_log2) {
        this.io.setByteField(this, 39, motion_subsample_log2);
        return this;
    }

    @Field(40)
    public int sample_rate() {
        return this.io.getIntField(this, 40);
    }

    @Field(40)
    public AVFrame54 sample_rate(int sample_rate) {
        this.io.setIntField(this, 40, sample_rate);
        return this;
    }

    @Field(41)
    public long channel_layout() {
        return this.io.getLongField(this, 41);
    }

    @Field(41)
    public AVFrame54 channel_layout(long channel_layout) {
        this.io.setLongField(this, 41, channel_layout);
        return this;
    }
    
}

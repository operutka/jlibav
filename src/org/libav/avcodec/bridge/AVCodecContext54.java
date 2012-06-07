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
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.libav.avutil.bridge.AVRational;

/**
 * Mirror of the native AVCodecContext struct for the libavcodec v54.x.x. For
 * details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVCodecContext54 extends StructObject {

    public AVCodecContext54() {
        super();
    }

    public AVCodecContext54(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<?> av_class() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVCodecContext54 av_class(Pointer<?> av_class) {
        this.io.setPointerField(this, 0, av_class);
        return this;
    }

    @Field(1)
    public int log_level_offset() {
        return this.io.getIntField(this, 1);
    }

    @Field(1)
    public AVCodecContext54 log_level_offset(int log_level_offset) {
        this.io.setIntField(this, 1, log_level_offset);
        return this;
    }

    @Field(2)
    public int codec_type() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public AVCodecContext54 codec_type(int codec_type) {
        this.io.setIntField(this, 2, codec_type);
        return this;
    }

    @Field(3)
    public Pointer<?> codec() {
        return this.io.getPointerField(this, 3);
    }

    @Field(3)
    public AVCodecContext54 codec(Pointer<?> codec) {
        this.io.setPointerField(this, 3, codec);
        return this;
    }

    @Array({32})
    @Field(4)
    public Pointer<Byte> codec_name() {
        return this.io.getPointerField(this, 4);
    }

    @Field(5)
    public int codec_id() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public AVCodecContext54 codec_id(int codec_id) {
        this.io.setIntField(this, 5, codec_id);
        return this;
    }

    @Field(6)
    public int codec_tag() {
        return this.io.getIntField(this, 6);
    }

    @Field(6)
    public AVCodecContext54 codec_tag(int codec_tag) {
        this.io.setIntField(this, 6, codec_tag);
        return this;
    }

    @Field(7)
    public int stream_codec_tag() {
        return this.io.getIntField(this, 7);
    }

    @Field(7)
    public AVCodecContext54 stream_codec_tag(int stream_codec_tag) {
        this.io.setIntField(this, 7, stream_codec_tag);
        return this;
    }

    @Field(8)
    public int sub_id() {
        return this.io.getIntField(this, 8);
    }

    @Field(8)
    public AVCodecContext54 sub_id(int sub_id) {
        this.io.setIntField(this, 8, sub_id);
        return this;
    }

    @Field(9)
    public Pointer<?> priv_data() {
        return this.io.getPointerField(this, 9);
    }

    @Field(9)
    public AVCodecContext54 priv_data(Pointer<?> priv_data) {
        this.io.setPointerField(this, 9, priv_data);
        return this;
    }

    @Field(10)
    public Pointer<?> internal() {
        return this.io.getPointerField(this, 10);
    }

    @Field(10)
    public AVCodecContext54 internal(Pointer<?> internal) {
        this.io.setPointerField(this, 10, internal);
        return this;
    }

    @Field(11)
    public Pointer<?> opaque() {
        return this.io.getPointerField(this, 11);
    }

    @Field(11)
    public AVCodecContext54 opaque(Pointer<?> opaque) {
        this.io.setPointerField(this, 11, opaque);
        return this;
    }

    @Field(12)
    public int bit_rate() {
        return this.io.getIntField(this, 12);
    }

    @Field(12)
    public AVCodecContext54 bit_rate(int bit_rate) {
        this.io.setIntField(this, 12, bit_rate);
        return this;
    }

    @Field(13)
    public int bit_rate_tolerance() {
        return this.io.getIntField(this, 13);
    }

    @Field(13)
    public AVCodecContext54 bit_rate_tolerance(int bit_rate_tolerance) {
        this.io.setIntField(this, 13, bit_rate_tolerance);
        return this;
    }

    @Field(14)
    public int global_quality() {
        return this.io.getIntField(this, 14);
    }

    @Field(14)
    public AVCodecContext54 global_quality(int global_quality) {
        this.io.setIntField(this, 14, global_quality);
        return this;
    }

    @Field(15)
    public int compression_level() {
        return this.io.getIntField(this, 15);
    }

    @Field(15)
    public AVCodecContext54 compression_level(int compression_level) {
        this.io.setIntField(this, 15, compression_level);
        return this;
    }

    @Field(16)
    public int flags() {
        return this.io.getIntField(this, 16);
    }

    @Field(16)
    public AVCodecContext54 flags(int flags) {
        this.io.setIntField(this, 16, flags);
        return this;
    }

    @Field(17)
    public int flags2() {
        return this.io.getIntField(this, 17);
    }

    @Field(17)
    public AVCodecContext54 flags2(int flags2) {
        this.io.setIntField(this, 17, flags2);
        return this;
    }

    @Field(18)
    public Pointer<Byte> extradata() {
        return this.io.getPointerField(this, 18);
    }

    @Field(18)
    public AVCodecContext54 extradata(Pointer<Byte> extradata) {
        this.io.setPointerField(this, 18, extradata);
        return this;
    }

    @Field(19)
    public int extradata_size() {
        return this.io.getIntField(this, 19);
    }

    @Field(19)
    public AVCodecContext54 extradata_size(int extradata_size) {
        this.io.setIntField(this, 19, extradata_size);
        return this;
    }

    @Field(20)
    public AVRational time_base() {
        return this.io.getNativeObjectField(this, 20);
    }

    @Field(20)
    public AVCodecContext54 time_base(AVRational time_base) {
        this.io.setNativeObjectField(this, 20, time_base);
        return this;
    }

    @Field(21)
    public int ticks_per_frame() {
        return this.io.getIntField(this, 21);
    }

    @Field(21)
    public AVCodecContext54 ticks_per_frame(int ticks_per_frame) {
        this.io.setIntField(this, 21, ticks_per_frame);
        return this;
    }

    @Field(22)
    public int delay() {
        return this.io.getIntField(this, 22);
    }

    @Field(22)
    public AVCodecContext54 delay(int delay) {
        this.io.setIntField(this, 22, delay);
        return this;
    }

    @Field(23)
    public int width() {
        return this.io.getIntField(this, 23);
    }

    @Field(23)
    public AVCodecContext54 width(int width) {
        this.io.setIntField(this, 23, width);
        return this;
    }

    @Field(24)
    public int height() {
        return this.io.getIntField(this, 24);
    }

    @Field(24)
    public AVCodecContext54 height(int height) {
        this.io.setIntField(this, 24, height);
        return this;
    }

    @Field(25)
    public int coded_width() {
        return this.io.getIntField(this, 25);
    }

    @Field(25)
    public AVCodecContext54 coded_width(int coded_width) {
        this.io.setIntField(this, 25, coded_width);
        return this;
    }

    @Field(26)
    public int coded_height() {
        return this.io.getIntField(this, 26);
    }

    @Field(26)
    public AVCodecContext54 coded_height(int coded_height) {
        this.io.setIntField(this, 26, coded_height);
        return this;
    }

    @Field(27)
    public int gop_size() {
        return this.io.getIntField(this, 27);
    }

    @Field(27)
    public AVCodecContext54 gop_size(int gop_size) {
        this.io.setIntField(this, 27, gop_size);
        return this;
    }

    @Field(28)
    public int pix_fmt() {
        return this.io.getIntField(this, 28);
    }

    @Field(28)
    public AVCodecContext54 pix_fmt(int pix_fmt) {
        this.io.setIntField(this, 28, pix_fmt);
        return this;
    }

    @Field(29)
    public int me_method() {
        return this.io.getIntField(this, 29);
    }

    @Field(29)
    public AVCodecContext54 me_method(int me_method) {
        this.io.setIntField(this, 29, me_method);
        return this;
    }

    @Field(30)
    public Pointer<AVCodecContext54.DrawHorizontalBandCallback> draw_horiz_band() {
        return this.io.getPointerField(this, 30);
    }

    @Field(30)
    public AVCodecContext54 draw_horiz_band(Pointer<AVCodecContext54.DrawHorizontalBandCallback> draw_horiz_band) {
        this.io.setPointerField(this, 30, draw_horiz_band);
        return this;
    }

    @Field(31)
    public Pointer<AVCodecContext54.GetFormatCallback> get_format() {
        return this.io.getPointerField(this, 31);
    }

    @Field(31)
    public AVCodecContext54 get_format(Pointer<AVCodecContext54.GetFormatCallback> get_format) {
        this.io.setPointerField(this, 31, get_format);
        return this;
    }

    @Field(32)
    public int max_b_frames() {
        return this.io.getIntField(this, 32);
    }

    @Field(32)
    public AVCodecContext54 max_b_frames(int max_b_frames) {
        this.io.setIntField(this, 32, max_b_frames);
        return this;
    }

    @Field(33)
    public float b_quant_factor() {
        return this.io.getFloatField(this, 33);
    }

    @Field(33)
    public AVCodecContext54 b_quant_factor(float b_quant_factor) {
        this.io.setFloatField(this, 33, b_quant_factor);
        return this;
    }

    @Field(34)
    public int rc_strategy() {
        return this.io.getIntField(this, 34);
    }

    @Field(34)
    public AVCodecContext54 rc_strategy(int rc_strategy) {
        this.io.setIntField(this, 34, rc_strategy);
        return this;
    }

    @Field(35)
    public int b_frame_strategy() {
        return this.io.getIntField(this, 35);
    }

    @Field(35)
    public AVCodecContext54 b_frame_strategy(int b_frame_strategy) {
        this.io.setIntField(this, 35, b_frame_strategy);
        return this;
    }

    @Field(36)
    public int luma_elim_threshold() {
        return this.io.getIntField(this, 36);
    }

    @Field(36)
    public AVCodecContext54 luma_elim_threshold(int luma_elim_threshold) {
        this.io.setIntField(this, 36, luma_elim_threshold);
        return this;
    }

    @Field(37)
    public int chroma_elim_threshold() {
        return this.io.getIntField(this, 37);
    }

    @Field(37)
    public AVCodecContext54 chroma_elim_threshold(int chroma_elim_threshold) {
        this.io.setIntField(this, 37, chroma_elim_threshold);
        return this;
    }

    @Field(38)
    public float b_quant_offset() {
        return this.io.getFloatField(this, 38);
    }

    @Field(38)
    public AVCodecContext54 b_quant_offset(float b_quant_offset) {
        this.io.setFloatField(this, 38, b_quant_offset);
        return this;
    }

    @Field(39)
    public int has_b_frames() {
        return this.io.getIntField(this, 39);
    }

    @Field(39)
    public AVCodecContext54 has_b_frames(int has_b_frames) {
        this.io.setIntField(this, 39, has_b_frames);
        return this;
    }

    @Field(40)
    public int mpeg_quant() {
        return this.io.getIntField(this, 40);
    }

    @Field(40)
    public AVCodecContext54 mpeg_quant(int mpeg_quant) {
        this.io.setIntField(this, 40, mpeg_quant);
        return this;
    }

    @Field(41)
    public float i_quant_factor() {
        return this.io.getFloatField(this, 41);
    }

    @Field(41)
    public AVCodecContext54 i_quant_factor(float i_quant_factor) {
        this.io.setFloatField(this, 41, i_quant_factor);
        return this;
    }

    @Field(42)
    public float i_quant_offset() {
        return this.io.getFloatField(this, 42);
    }

    @Field(42)
    public AVCodecContext54 i_quant_offset(float i_quant_offset) {
        this.io.setFloatField(this, 42, i_quant_offset);
        return this;
    }

    @Field(43)
    public float lumi_masking() {
        return this.io.getFloatField(this, 43);
    }

    @Field(43)
    public AVCodecContext54 lumi_masking(float lumi_masking) {
        this.io.setFloatField(this, 43, lumi_masking);
        return this;
    }

    @Field(44)
    public float temporal_cplx_masking() {
        return this.io.getFloatField(this, 44);
    }

    @Field(44)
    public AVCodecContext54 temporal_cplx_masking(float temporal_cplx_masking) {
        this.io.setFloatField(this, 44, temporal_cplx_masking);
        return this;
    }

    @Field(45)
    public float spatial_cplx_masking() {
        return this.io.getFloatField(this, 45);
    }

    @Field(45)
    public AVCodecContext54 spatial_cplx_masking(float spatial_cplx_masking) {
        this.io.setFloatField(this, 45, spatial_cplx_masking);
        return this;
    }

    @Field(46)
    public float p_masking() {
        return this.io.getFloatField(this, 46);
    }

    @Field(46)
    public AVCodecContext54 p_masking(float p_masking) {
        this.io.setFloatField(this, 46, p_masking);
        return this;
    }

    @Field(47)
    public float dark_masking() {
        return this.io.getFloatField(this, 47);
    }

    @Field(47)
    public AVCodecContext54 dark_masking(float dark_masking) {
        this.io.setFloatField(this, 47, dark_masking);
        return this;
    }

    @Field(48)
    public int slice_count() {
        return this.io.getIntField(this, 48);
    }

    @Field(48)
    public AVCodecContext54 slice_count(int slice_count) {
        this.io.setIntField(this, 48, slice_count);
        return this;
    }

    @Field(49)
    public int prediction_method() {
        return this.io.getIntField(this, 49);
    }

    @Field(49)
    public AVCodecContext54 prediction_method(int prediction_method) {
        this.io.setIntField(this, 49, prediction_method);
        return this;
    }

    @Field(50)
    public Pointer<Integer> slice_offset() {
        return this.io.getPointerField(this, 50);
    }

    @Field(50)
    public AVCodecContext54 slice_offset(Pointer<Integer> slice_offset) {
        this.io.setPointerField(this, 50, slice_offset);
        return this;
    }

    @Field(51)
    public AVRational sample_aspect_ratio() {
        return this.io.getNativeObjectField(this, 51);
    }

    @Field(51)
    public AVCodecContext54 sample_aspect_ratio(AVRational sample_aspect_ratio) {
        this.io.setNativeObjectField(this, 51, sample_aspect_ratio);
        return this;
    }

    @Field(52)
    public int me_cmp() {
        return this.io.getIntField(this, 52);
    }

    @Field(52)
    public AVCodecContext54 me_cmp(int me_cmp) {
        this.io.setIntField(this, 52, me_cmp);
        return this;
    }

    @Field(53)
    public int me_sub_cmp() {
        return this.io.getIntField(this, 53);
    }

    @Field(53)
    public AVCodecContext54 me_sub_cmp(int me_sub_cmp) {
        this.io.setIntField(this, 53, me_sub_cmp);
        return this;
    }

    @Field(54)
    public int mb_cmp() {
        return this.io.getIntField(this, 54);
    }

    @Field(54)
    public AVCodecContext54 mb_cmp(int mb_cmp) {
        this.io.setIntField(this, 54, mb_cmp);
        return this;
    }

    @Field(55)
    public int ildct_cmp() {
        return this.io.getIntField(this, 55);
    }

    @Field(55)
    public AVCodecContext54 ildct_cmp(int ildct_cmp) {
        this.io.setIntField(this, 55, ildct_cmp);
        return this;
    }

    @Field(56)
    public int dia_size() {
        return this.io.getIntField(this, 56);
    }

    @Field(56)
    public AVCodecContext54 dia_size(int dia_size) {
        this.io.setIntField(this, 56, dia_size);
        return this;
    }

    @Field(57)
    public int last_predictor_count() {
        return this.io.getIntField(this, 57);
    }

    @Field(57)
    public AVCodecContext54 last_predictor_count(int last_predictor_count) {
        this.io.setIntField(this, 57, last_predictor_count);
        return this;
    }

    @Field(58)
    public int pre_me() {
        return this.io.getIntField(this, 58);
    }

    @Field(58)
    public AVCodecContext54 pre_me(int pre_me) {
        this.io.setIntField(this, 58, pre_me);
        return this;
    }

    @Field(59)
    public int me_pre_cmp() {
        return this.io.getIntField(this, 59);
    }

    @Field(59)
    public AVCodecContext54 me_pre_cmp(int me_pre_cmp) {
        this.io.setIntField(this, 59, me_pre_cmp);
        return this;
    }

    @Field(60)
    public int pre_dia_size() {
        return this.io.getIntField(this, 60);
    }

    @Field(60)
    public AVCodecContext54 pre_dia_size(int pre_dia_size) {
        this.io.setIntField(this, 60, pre_dia_size);
        return this;
    }

    @Field(61)
    public int me_subpel_quality() {
        return this.io.getIntField(this, 61);
    }

    @Field(61)
    public AVCodecContext54 me_subpel_quality(int me_subpel_quality) {
        this.io.setIntField(this, 61, me_subpel_quality);
        return this;
    }

    @Field(62)
    public int dtg_active_format() {
        return this.io.getIntField(this, 62);
    }

    @Field(62)
    public AVCodecContext54 dtg_active_format(int dtg_active_format) {
        this.io.setIntField(this, 62, dtg_active_format);
        return this;
    }

    @Field(63)
    public int me_range() {
        return this.io.getIntField(this, 63);
    }

    @Field(63)
    public AVCodecContext54 me_range(int me_range) {
        this.io.setIntField(this, 63, me_range);
        return this;
    }

    @Field(64)
    public int intra_quant_bias() {
        return this.io.getIntField(this, 64);
    }

    @Field(64)
    public AVCodecContext54 intra_quant_bias(int intra_quant_bias) {
        this.io.setIntField(this, 64, intra_quant_bias);
        return this;
    }

    @Field(65)
    public int inter_quant_bias() {
        return this.io.getIntField(this, 65);
    }

    @Field(65)
    public AVCodecContext54 inter_quant_bias(int inter_quant_bias) {
        this.io.setIntField(this, 65, inter_quant_bias);
        return this;
    }

    @Field(66)
    public int color_table_id() {
        return this.io.getIntField(this, 66);
    }

    @Field(66)
    public AVCodecContext54 color_table_id(int color_table_id) {
        this.io.setIntField(this, 66, color_table_id);
        return this;
    }

    @Field(67)
    public int slice_flags() {
        return this.io.getIntField(this, 67);
    }

    @Field(67)
    public AVCodecContext54 slice_flags(int slice_flags) {
        this.io.setIntField(this, 67, slice_flags);
        return this;
    }

    @Field(68)
    public int xvmc_acceleration() {
        return this.io.getIntField(this, 68);
    }

    @Field(68)
    public AVCodecContext54 xvmc_acceleration(int xvmc_acceleration) {
        this.io.setIntField(this, 68, xvmc_acceleration);
        return this;
    }

    @Field(69)
    public int mb_decision() {
        return this.io.getIntField(this, 69);
    }

    @Field(69)
    public AVCodecContext54 mb_decision(int mb_decision) {
        this.io.setIntField(this, 69, mb_decision);
        return this;
    }

    @Field(70)
    public Pointer<Short> intra_matrix() {
        return this.io.getPointerField(this, 70);
    }

    @Field(70)
    public AVCodecContext54 intra_matrix(Pointer<Short> intra_matrix) {
        this.io.setPointerField(this, 70, intra_matrix);
        return this;
    }

    @Field(71)
    public Pointer<Short> inter_matrix() {
        return this.io.getPointerField(this, 71);
    }

    @Field(71)
    public AVCodecContext54 inter_matrix(Pointer<Short> inter_matrix) {
        this.io.setPointerField(this, 71, inter_matrix);
        return this;
    }

    @Field(72)
    public int scenechange_threshold() {
        return this.io.getIntField(this, 72);
    }

    @Field(72)
    public AVCodecContext54 scenechange_threshold(int scenechange_threshold) {
        this.io.setIntField(this, 72, scenechange_threshold);
        return this;
    }

    @Field(73)
    public int noise_reduction() {
        return this.io.getIntField(this, 73);
    }

    @Field(73)
    public AVCodecContext54 noise_reduction(int noise_reduction) {
        this.io.setIntField(this, 73, noise_reduction);
        return this;
    }

    @Field(74)
    public int inter_threshold() {
        return this.io.getIntField(this, 74);
    }

    @Field(74)
    public AVCodecContext54 inter_threshold(int inter_threshold) {
        this.io.setIntField(this, 74, inter_threshold);
        return this;
    }

    @Field(75)
    public int quantizer_noise_shaping() {
        return this.io.getIntField(this, 75);
    }

    @Field(75)
    public AVCodecContext54 quantizer_noise_shaping(int quantizer_noise_shaping) {
        this.io.setIntField(this, 75, quantizer_noise_shaping);
        return this;
    }

    @Field(76)
    public int me_threshold() {
        return this.io.getIntField(this, 76);
    }

    @Field(76)
    public AVCodecContext54 me_threshold(int me_threshold) {
        this.io.setIntField(this, 76, me_threshold);
        return this;
    }

    @Field(77)
    public int mb_threshold() {
        return this.io.getIntField(this, 77);
    }

    @Field(77)
    public AVCodecContext54 mb_threshold(int mb_threshold) {
        this.io.setIntField(this, 77, mb_threshold);
        return this;
    }

    @Field(78)
    public int intra_dc_precision() {
        return this.io.getIntField(this, 78);
    }

    @Field(78)
    public AVCodecContext54 intra_dc_precision(int intra_dc_precision) {
        this.io.setIntField(this, 78, intra_dc_precision);
        return this;
    }

    @Field(79)
    public int skip_top() {
        return this.io.getIntField(this, 79);
    }

    @Field(79)
    public AVCodecContext54 skip_top(int skip_top) {
        this.io.setIntField(this, 79, skip_top);
        return this;
    }

    @Field(80)
    public int skip_bottom() {
        return this.io.getIntField(this, 80);
    }

    @Field(80)
    public AVCodecContext54 skip_bottom(int skip_bottom) {
        this.io.setIntField(this, 80, skip_bottom);
        return this;
    }

    @Field(81)
    public float border_masking() {
        return this.io.getFloatField(this, 81);
    }

    @Field(81)
    public AVCodecContext54 border_masking(float border_masking) {
        this.io.setFloatField(this, 81, border_masking);
        return this;
    }

    @Field(82)
    public int mb_lmin() {
        return this.io.getIntField(this, 82);
    }

    @Field(82)
    public AVCodecContext54 mb_lmin(int mb_lmin) {
        this.io.setIntField(this, 82, mb_lmin);
        return this;
    }

    @Field(83)
    public int mb_lmax() {
        return this.io.getIntField(this, 83);
    }

    @Field(83)
    public AVCodecContext54 mb_lmax(int mb_lmax) {
        this.io.setIntField(this, 83, mb_lmax);
        return this;
    }

    @Field(84)
    public int me_penalty_compensation() {
        return this.io.getIntField(this, 84);
    }

    @Field(84)
    public AVCodecContext54 me_penalty_compensation(int me_penalty_compensation) {
        this.io.setIntField(this, 84, me_penalty_compensation);
        return this;
    }

    @Field(85)
    public int bidir_refine() {
        return this.io.getIntField(this, 85);
    }

    @Field(85)
    public AVCodecContext54 bidir_refine(int bidir_refine) {
        this.io.setIntField(this, 85, bidir_refine);
        return this;
    }

    @Field(86)
    public int brd_scale() {
        return this.io.getIntField(this, 86);
    }

    @Field(86)
    public AVCodecContext54 brd_scale(int brd_scale) {
        this.io.setIntField(this, 86, brd_scale);
        return this;
    }

    @Field(87)
    public int keyint_min() {
        return this.io.getIntField(this, 87);
    }

    @Field(87)
    public AVCodecContext54 keyint_min(int keyint_min) {
        this.io.setIntField(this, 87, keyint_min);
        return this;
    }

    @Field(88)
    public int refs() {
        return this.io.getIntField(this, 88);
    }

    @Field(88)
    public AVCodecContext54 refs(int refs) {
        this.io.setIntField(this, 88, refs);
        return this;
    }

    @Field(89)
    public int chromaoffset() {
        return this.io.getIntField(this, 89);
    }

    @Field(89)
    public AVCodecContext54 chromaoffset(int chromaoffset) {
        this.io.setIntField(this, 89, chromaoffset);
        return this;
    }

    @Field(90)
    public int scenechange_factor() {
        return this.io.getIntField(this, 90);
    }

    @Field(90)
    public AVCodecContext54 scenechange_factor(int scenechange_factor) {
        this.io.setIntField(this, 90, scenechange_factor);
        return this;
    }

    @Field(91)
    public int mv0_threshold() {
        return this.io.getIntField(this, 91);
    }

    @Field(91)
    public AVCodecContext54 mv0_threshold(int mv0_threshold) {
        this.io.setIntField(this, 91, mv0_threshold);
        return this;
    }

    @Field(92)
    public int b_sensitivity() {
        return this.io.getIntField(this, 92);
    }

    @Field(92)
    public AVCodecContext54 b_sensitivity(int b_sensitivity) {
        this.io.setIntField(this, 92, b_sensitivity);
        return this;
    }

    @Field(93)
    public int color_primaries() {
        return this.io.getIntField(this, 93);
    }

    @Field(93)
    public AVCodecContext54 color_primaries(int color_primaries) {
        this.io.setIntField(this, 93, color_primaries);
        return this;
    }

    @Field(94)
    public int color_trc() {
        return this.io.getIntField(this, 94);
    }

    @Field(94)
    public AVCodecContext54 color_trc(int color_trc) {
        this.io.setIntField(this, 94, color_trc);
        return this;
    }

    @Field(95)
    public int colorspace() {
        return this.io.getIntField(this, 95);
    }

    @Field(95)
    public AVCodecContext54 colorspace(int colorspace) {
        this.io.setIntField(this, 95, colorspace);
        return this;
    }

    @Field(96)
    public int color_range() {
        return this.io.getIntField(this, 96);
    }

    @Field(96)
    public AVCodecContext54 color_range(int color_range) {
        this.io.setIntField(this, 96, color_range);
        return this;
    }

    @Field(97)
    public int chroma_sample_location() {
        return this.io.getIntField(this, 97);
    }

    @Field(97)
    public AVCodecContext54 chroma_sample_location(int chroma_sample_location) {
        this.io.setIntField(this, 97, chroma_sample_location);
        return this;
    }

    @Field(98)
    public int slices() {
        return this.io.getIntField(this, 98);
    }

    @Field(98)
    public AVCodecContext54 slices(int slices) {
        this.io.setIntField(this, 98, slices);
        return this;
    }

    @Field(99)
    public int field_order() {
        return this.io.getIntField(this, 99);
    }

    @Field(99)
    public AVCodecContext54 field_order(int field_order) {
        this.io.setIntField(this, 99, field_order);
        return this;
    }

    @Field(100)
    public int sample_rate() {
        return this.io.getIntField(this, 100);
    }

    @Field(100)
    public AVCodecContext54 sample_rate(int sample_rate) {
        this.io.setIntField(this, 100, sample_rate);
        return this;
    }

    @Field(101)
    public int channels() {
        return this.io.getIntField(this, 101);
    }

    @Field(101)
    public AVCodecContext54 channels(int channels) {
        this.io.setIntField(this, 101, channels);
        return this;
    }

    @Field(102)
    public int sample_fmt() {
        return this.io.getIntField(this, 102);
    }

    @Field(102)
    public AVCodecContext54 sample_fmt(int sample_fmt) {
        this.io.setIntField(this, 102, sample_fmt);
        return this;
    }

    @Field(103)
    public int frame_size() {
        return this.io.getIntField(this, 103);
    }

    @Field(103)
    public AVCodecContext54 frame_size(int frame_size) {
        this.io.setIntField(this, 103, frame_size);
        return this;
    }

    @Field(104)
    public int frame_number() {
        return this.io.getIntField(this, 104);
    }

    @Field(104)
    public AVCodecContext54 frame_number(int frame_number) {
        this.io.setIntField(this, 104, frame_number);
        return this;
    }

    @Field(105)
    public int block_align() {
        return this.io.getIntField(this, 105);
    }

    @Field(105)
    public AVCodecContext54 block_align(int block_align) {
        this.io.setIntField(this, 105, block_align);
        return this;
    }

    @Field(106)
    public int cutoff() {
        return this.io.getIntField(this, 106);
    }

    @Field(106)
    public AVCodecContext54 cutoff(int cutoff) {
        this.io.setIntField(this, 106, cutoff);
        return this;
    }

    @Field(107)
    public int request_channels() {
        return this.io.getIntField(this, 107);
    }

    @Field(107)
    public AVCodecContext54 request_channels(int request_channels) {
        this.io.setIntField(this, 107, request_channels);
        return this;
    }

    @Field(108)
    public long channel_layout() {
        return this.io.getLongField(this, 108);
    }

    @Field(108)
    public AVCodecContext54 channel_layout(long channel_layout) {
        this.io.setLongField(this, 108, channel_layout);
        return this;
    }

    @Field(109)
    public long request_channel_layout() {
        return this.io.getLongField(this, 109);
    }

    @Field(109)
    public AVCodecContext54 request_channel_layout(long request_channel_layout) {
        this.io.setLongField(this, 109, request_channel_layout);
        return this;
    }

    @Field(110)
    public int audio_service_type() {
        return this.io.getIntField(this, 110);
    }

    @Field(110)
    public AVCodecContext54 audio_service_type(int audio_service_type) {
        this.io.setIntField(this, 110, audio_service_type);
        return this;
    }

    @Field(111)
    public int request_sample_fmt() {
        return this.io.getIntField(this, 111);
    }

    @Field(111)
    public AVCodecContext54 request_sample_fmt(int request_sample_fmt) {
        this.io.setIntField(this, 111, request_sample_fmt);
        return this;
    }

    @Field(112)
    public Pointer<AVCodecContext54.BufferCallback> get_buffer() {
        return this.io.getPointerField(this, 112);
    }

    @Field(112)
    public AVCodecContext54 get_buffer(Pointer<AVCodecContext54.BufferCallback> get_buffer) {
        this.io.setPointerField(this, 112, get_buffer);
        return this;
    }

    @Field(113)
    public Pointer<AVCodecContext54.BufferCallback> release_buffer() {
        return this.io.getPointerField(this, 113);
    }

    @Field(113)
    public AVCodecContext54 release_buffer(Pointer<AVCodecContext54.BufferCallback> release_buffer) {
        this.io.setPointerField(this, 113, release_buffer);
        return this;
    }

    @Field(114)
    public Pointer<AVCodecContext54.BufferCallback> reget_buffer() {
        return this.io.getPointerField(this, 114);
    }

    @Field(114)
    public AVCodecContext54 reget_buffer(Pointer<AVCodecContext54.BufferCallback> reget_buffer) {
        this.io.setPointerField(this, 114, reget_buffer);
        return this;
    }

    @Field(115)
    public float qcompress() {
        return this.io.getFloatField(this, 115);
    }

    @Field(115)
    public AVCodecContext54 qcompress(float qcompress) {
        this.io.setFloatField(this, 115, qcompress);
        return this;
    }

    @Field(116)
    public float qblur() {
        return this.io.getFloatField(this, 116);
    }

    @Field(116)
    public AVCodecContext54 qblur(float qblur) {
        this.io.setFloatField(this, 116, qblur);
        return this;
    }

    @Field(117)
    public int qmin() {
        return this.io.getIntField(this, 117);
    }

    @Field(117)
    public AVCodecContext54 qmin(int qmin) {
        this.io.setIntField(this, 117, qmin);
        return this;
    }

    @Field(118)
    public int qmax() {
        return this.io.getIntField(this, 118);
    }

    @Field(118)
    public AVCodecContext54 qmax(int qmax) {
        this.io.setIntField(this, 118, qmax);
        return this;
    }

    @Field(119)
    public int max_qdiff() {
        return this.io.getIntField(this, 119);
    }

    @Field(119)
    public AVCodecContext54 max_qdiff(int max_qdiff) {
        this.io.setIntField(this, 119, max_qdiff);
        return this;
    }

    @Field(120)
    public float rc_qsquish() {
        return this.io.getFloatField(this, 120);
    }

    @Field(120)
    public AVCodecContext54 rc_qsquish(float rc_qsquish) {
        this.io.setFloatField(this, 120, rc_qsquish);
        return this;
    }

    @Field(121)
    public float rc_qmod_amp() {
        return this.io.getFloatField(this, 121);
    }

    @Field(121)
    public AVCodecContext54 rc_qmod_amp(float rc_qmod_amp) {
        this.io.setFloatField(this, 121, rc_qmod_amp);
        return this;
    }

    @Field(122)
    public int rc_qmod_freq() {
        return this.io.getIntField(this, 122);
    }

    @Field(122)
    public AVCodecContext54 rc_qmod_freq(int rc_qmod_freq) {
        this.io.setIntField(this, 122, rc_qmod_freq);
        return this;
    }

    @Field(123)
    public int rc_buffer_size() {
        return this.io.getIntField(this, 123);
    }

    @Field(123)
    public AVCodecContext54 rc_buffer_size(int rc_buffer_size) {
        this.io.setIntField(this, 123, rc_buffer_size);
        return this;
    }

    @Field(124)
    public int rc_override_count() {
        return this.io.getIntField(this, 124);
    }

    @Field(124)
    public AVCodecContext54 rc_override_count(int rc_override_count) {
        this.io.setIntField(this, 124, rc_override_count);
        return this;
    }

    @Field(125)
    public Pointer<?> rc_override() {
        return this.io.getPointerField(this, 125);
    }

    @Field(125)
    public AVCodecContext54 rc_override(Pointer<?> rc_override) {
        this.io.setPointerField(this, 125, rc_override);
        return this;
    }

    @Field(126)
    public Pointer<Byte> rc_eq() {
        return this.io.getPointerField(this, 126);
    }

    @Field(126)
    public AVCodecContext54 rc_eq(Pointer<Byte> rc_eq) {
        this.io.setPointerField(this, 126, rc_eq);
        return this;
    }

    @Field(127)
    public int rc_max_rate() {
        return this.io.getIntField(this, 127);
    }

    @Field(127)
    public AVCodecContext54 rc_max_rate(int rc_max_rate) {
        this.io.setIntField(this, 127, rc_max_rate);
        return this;
    }

    @Field(128)
    public int rc_min_rate() {
        return this.io.getIntField(this, 128);
    }

    @Field(128)
    public AVCodecContext54 rc_min_rate(int rc_min_rate) {
        this.io.setIntField(this, 128, rc_min_rate);
        return this;
    }

    @Field(129)
    public float rc_buffer_aggressivity() {
        return this.io.getFloatField(this, 129);
    }

    @Field(129)
    public AVCodecContext54 rc_buffer_aggressivity(float rc_buffer_aggressivity) {
        this.io.setFloatField(this, 129, rc_buffer_aggressivity);
        return this;
    }

    @Field(130)
    public float rc_initial_cplx() {
        return this.io.getFloatField(this, 130);
    }

    @Field(130)
    public AVCodecContext54 rc_initial_cplx(float rc_initial_cplx) {
        this.io.setFloatField(this, 130, rc_initial_cplx);
        return this;
    }

    @Field(131)
    public float rc_max_available_vbv_use() {
        return this.io.getFloatField(this, 131);
    }

    @Field(131)
    public AVCodecContext54 rc_max_available_vbv_use(float rc_max_available_vbv_use) {
        this.io.setFloatField(this, 131, rc_max_available_vbv_use);
        return this;
    }

    @Field(132)
    public float rc_min_vbv_overflow_use() {
        return this.io.getFloatField(this, 132);
    }

    @Field(132)
    public AVCodecContext54 rc_min_vbv_overflow_use(float rc_min_vbv_overflow_use) {
        this.io.setFloatField(this, 132, rc_min_vbv_overflow_use);
        return this;
    }

    @Field(133)
    public int rc_initial_buffer_occupancy() {
        return this.io.getIntField(this, 133);
    }

    @Field(133)
    public AVCodecContext54 rc_initial_buffer_occupancy(int rc_initial_buffer_occupancy) {
        this.io.setIntField(this, 133, rc_initial_buffer_occupancy);
        return this;
    }

    @Field(134)
    public int coder_type() {
        return this.io.getIntField(this, 134);
    }

    @Field(134)
    public AVCodecContext54 coder_type(int coder_type) {
        this.io.setIntField(this, 134, coder_type);
        return this;
    }

    @Field(135)
    public int context_model() {
        return this.io.getIntField(this, 135);
    }

    @Field(135)
    public AVCodecContext54 context_model(int context_model) {
        this.io.setIntField(this, 135, context_model);
        return this;
    }

    @Field(136)
    public int lmin() {
        return this.io.getIntField(this, 136);
    }

    @Field(136)
    public AVCodecContext54 lmin(int lmin) {
        this.io.setIntField(this, 136, lmin);
        return this;
    }

    @Field(137)
    public int lmax() {
        return this.io.getIntField(this, 137);
    }

    @Field(137)
    public AVCodecContext54 lmax(int lmax) {
        this.io.setIntField(this, 137, lmax);
        return this;
    }

    @Field(138)
    public int frame_skip_threshold() {
        return this.io.getIntField(this, 138);
    }

    @Field(138)
    public AVCodecContext54 frame_skip_threshold(int frame_skip_threshold) {
        this.io.setIntField(this, 138, frame_skip_threshold);
        return this;
    }

    @Field(139)
    public int frame_skip_factor() {
        return this.io.getIntField(this, 139);
    }

    @Field(139)
    public AVCodecContext54 frame_skip_factor(int frame_skip_factor) {
        this.io.setIntField(this, 139, frame_skip_factor);
        return this;
    }

    @Field(140)
    public int frame_skip_exp() {
        return this.io.getIntField(this, 140);
    }

    @Field(140)
    public AVCodecContext54 frame_skip_exp(int frame_skip_exp) {
        this.io.setIntField(this, 140, frame_skip_exp);
        return this;
    }

    @Field(141)
    public int frame_skip_cmp() {
        return this.io.getIntField(this, 141);
    }

    @Field(141)
    public AVCodecContext54 frame_skip_cmp(int frame_skip_cmp) {
        this.io.setIntField(this, 141, frame_skip_cmp);
        return this;
    }

    @Field(142)
    public int trellis() {
        return this.io.getIntField(this, 142);
    }

    @Field(142)
    public AVCodecContext54 trellis(int trellis) {
        this.io.setIntField(this, 142, trellis);
        return this;
    }

    @Field(143)
    public int min_prediction_order() {
        return this.io.getIntField(this, 143);
    }

    @Field(143)
    public AVCodecContext54 min_prediction_order(int min_prediction_order) {
        this.io.setIntField(this, 143, min_prediction_order);
        return this;
    }

    @Field(144)
    public int max_prediction_order() {
        return this.io.getIntField(this, 144);
    }

    @Field(144)
    public AVCodecContext54 max_prediction_order(int max_prediction_order) {
        this.io.setIntField(this, 144, max_prediction_order);
        return this;
    }

    @Field(145)
    public long timecode_frame_start() {
        return this.io.getLongField(this, 145);
    }

    @Field(145)
    public AVCodecContext54 timecode_frame_start(long timecode_frame_start) {
        this.io.setLongField(this, 145, timecode_frame_start);
        return this;
    }

    @Field(146)
    public Pointer<AVCodecContext54.RtpCallback> rtp_callback() {
        return this.io.getPointerField(this, 146);
    }

    @Field(146)
    public AVCodecContext54 rtp_callback(Pointer<AVCodecContext54.RtpCallback> rtp_callback) {
        this.io.setPointerField(this, 146, rtp_callback);
        return this;
    }

    @Field(147)
    public int rtp_payload_size() {
        return this.io.getIntField(this, 147);
    }

    @Field(147)
    public AVCodecContext54 rtp_payload_size(int rtp_payload_size) {
        this.io.setIntField(this, 147, rtp_payload_size);
        return this;
    }

    @Field(148)
    public int mv_bits() {
        return this.io.getIntField(this, 148);
    }

    @Field(148)
    public AVCodecContext54 mv_bits(int mv_bits) {
        this.io.setIntField(this, 148, mv_bits);
        return this;
    }

    @Field(149)
    public int header_bits() {
        return this.io.getIntField(this, 149);
    }

    @Field(149)
    public AVCodecContext54 header_bits(int header_bits) {
        this.io.setIntField(this, 149, header_bits);
        return this;
    }

    @Field(150)
    public int i_tex_bits() {
        return this.io.getIntField(this, 150);
    }

    @Field(150)
    public AVCodecContext54 i_tex_bits(int i_tex_bits) {
        this.io.setIntField(this, 150, i_tex_bits);
        return this;
    }

    @Field(151)
    public int p_tex_bits() {
        return this.io.getIntField(this, 151);
    }

    @Field(151)
    public AVCodecContext54 p_tex_bits(int p_tex_bits) {
        this.io.setIntField(this, 151, p_tex_bits);
        return this;
    }

    @Field(152)
    public int i_count() {
        return this.io.getIntField(this, 152);
    }

    @Field(152)
    public AVCodecContext54 i_count(int i_count) {
        this.io.setIntField(this, 152, i_count);
        return this;
    }

    @Field(153)
    public int p_count() {
        return this.io.getIntField(this, 153);
    }

    @Field(153)
    public AVCodecContext54 p_count(int p_count) {
        this.io.setIntField(this, 153, p_count);
        return this;
    }

    @Field(154)
    public int skip_count() {
        return this.io.getIntField(this, 154);
    }

    @Field(154)
    public AVCodecContext54 skip_count(int skip_count) {
        this.io.setIntField(this, 154, skip_count);
        return this;
    }

    @Field(155)
    public int misc_bits() {
        return this.io.getIntField(this, 155);
    }

    @Field(155)
    public AVCodecContext54 misc_bits(int misc_bits) {
        this.io.setIntField(this, 155, misc_bits);
        return this;
    }

    @Field(156)
    public int frame_bits() {
        return this.io.getIntField(this, 156);
    }

    @Field(156)
    public AVCodecContext54 frame_bits(int frame_bits) {
        this.io.setIntField(this, 156, frame_bits);
        return this;
    }

    @Field(157)
    public Pointer<Byte> stats_out() {
        return this.io.getPointerField(this, 157);
    }

    @Field(157)
    public AVCodecContext54 stats_out(Pointer<Byte> stats_out) {
        this.io.setPointerField(this, 157, stats_out);
        return this;
    }

    @Field(158)
    public Pointer<Byte> stats_in() {
        return this.io.getPointerField(this, 158);
    }

    @Field(158)
    public AVCodecContext54 stats_in(Pointer<Byte> stats_in) {
        this.io.setPointerField(this, 158, stats_in);
        return this;
    }

    @Field(159)
    public int workaround_bugs() {
        return this.io.getIntField(this, 159);
    }

    @Field(159)
    public AVCodecContext54 workaround_bugs(int workaround_bugs) {
        this.io.setIntField(this, 159, workaround_bugs);
        return this;
    }

    @Field(160)
    public int strict_std_compliance() {
        return this.io.getIntField(this, 160);
    }

    @Field(160)
    public AVCodecContext54 strict_std_compliance(int strict_std_compliance) {
        this.io.setIntField(this, 160, strict_std_compliance);
        return this;
    }

    @Field(161)
    public int error_concealment() {
        return this.io.getIntField(this, 161);
    }

    @Field(161)
    public AVCodecContext54 error_concealment(int error_concealment) {
        this.io.setIntField(this, 161, error_concealment);
        return this;
    }

    @Field(162)
    public int debug() {
        return this.io.getIntField(this, 162);
    }

    @Field(162)
    public AVCodecContext54 debug(int debug) {
        this.io.setIntField(this, 162, debug);
        return this;
    }

    @Field(163)
    public int debug_mv() {
        return this.io.getIntField(this, 163);
    }

    @Field(163)
    public AVCodecContext54 debug_mv(int debug_mv) {
        this.io.setIntField(this, 163, debug_mv);
        return this;
    }

    @Field(164)
    public int err_recognition() {
        return this.io.getIntField(this, 164);
    }

    @Field(164)
    public AVCodecContext54 err_recognition(int err_recognition) {
        this.io.setIntField(this, 164, err_recognition);
        return this;
    }

    @Field(165)
    public long reordered_opaque() {
        return this.io.getLongField(this, 165);
    }

    @Field(165)
    public AVCodecContext54 reordered_opaque(long reordered_opaque) {
        this.io.setLongField(this, 165, reordered_opaque);
        return this;
    }

    @Field(166)
    public Pointer<?> hwaccel() {
        return this.io.getPointerField(this, 166);
    }

    @Field(166)
    public AVCodecContext54 hwaccel(Pointer<?> hwaccel) {
        this.io.setPointerField(this, 166, hwaccel);
        return this;
    }

    @Field(167)
    public Pointer<?> hwaccel_context() {
        return this.io.getPointerField(this, 167);
    }

    @Field(167)
    public AVCodecContext54 hwaccel_context(Pointer<?> hwaccel_context) {
        this.io.setPointerField(this, 167, hwaccel_context);
        return this;
    }

    @Array({8})
    @Field(168)
    public Pointer<Long> error() {
        return this.io.getPointerField(this, 168);
    }

    @Field(169)
    public int dct_algo() {
        return this.io.getIntField(this, 169);
    }

    @Field(169)
    public AVCodecContext54 dct_algo(int dct_algo) {
        this.io.setIntField(this, 169, dct_algo);
        return this;
    }

    @Field(170)
    public int idct_algo() {
        return this.io.getIntField(this, 170);
    }

    @Field(170)
    public AVCodecContext54 idct_algo(int idct_algo) {
        this.io.setIntField(this, 170, idct_algo);
        return this;
    }

    @Field(171)
    public int dsp_mask() {
        return this.io.getIntField(this, 171);
    }

    @Field(171)
    public AVCodecContext54 dsp_mask(int dsp_mask) {
        this.io.setIntField(this, 171, dsp_mask);
        return this;
    }

    @Field(172)
    public int bits_per_coded_sample() {
        return this.io.getIntField(this, 172);
    }

    @Field(172)
    public AVCodecContext54 bits_per_coded_sample(int bits_per_coded_sample) {
        this.io.setIntField(this, 172, bits_per_coded_sample);
        return this;
    }

    @Field(173)
    public int bits_per_raw_sample() {
        return this.io.getIntField(this, 173);
    }

    @Field(173)
    public AVCodecContext54 bits_per_raw_sample(int bits_per_raw_sample) {
        this.io.setIntField(this, 173, bits_per_raw_sample);
        return this;
    }

    @Field(174)
    public int lowres() {
        return this.io.getIntField(this, 174);
    }

    @Field(174)
    public AVCodecContext54 lowres(int lowres) {
        this.io.setIntField(this, 174, lowres);
        return this;
    }

    @Field(175)
    public Pointer<?> coded_frame() {
        return this.io.getPointerField(this, 175);
    }

    @Field(175)
    public AVCodecContext54 coded_frame(Pointer<?> coded_frame) {
        this.io.setPointerField(this, 175, coded_frame);
        return this;
    }

    @Field(176)
    public int thread_count() {
        return this.io.getIntField(this, 176);
    }

    @Field(176)
    public AVCodecContext54 thread_count(int thread_count) {
        this.io.setIntField(this, 176, thread_count);
        return this;
    }

    @Field(177)
    public int thread_type() {
        return this.io.getIntField(this, 177);
    }

    @Field(177)
    public AVCodecContext54 thread_type(int thread_type) {
        this.io.setIntField(this, 177, thread_type);
        return this;
    }

    @Field(178)
    public int active_thread_type() {
        return this.io.getIntField(this, 178);
    }

    @Field(178)
    public AVCodecContext54 active_thread_type(int active_thread_type) {
        this.io.setIntField(this, 178, active_thread_type);
        return this;
    }

    @Field(179)
    public int thread_safe_callbacks() {
        return this.io.getIntField(this, 179);
    }

    @Field(179)
    public AVCodecContext54 thread_safe_callbacks(int thread_safe_callbacks) {
        this.io.setIntField(this, 179, thread_safe_callbacks);
        return this;
    }

    @Field(180)
    public Pointer<AVCodecContext54.ExecuteCallback> execute() {
        return this.io.getPointerField(this, 180);
    }

    @Field(180)
    public AVCodecContext54 execute(Pointer<AVCodecContext54.ExecuteCallback> execute) {
        this.io.setPointerField(this, 180, execute);
        return this;
    }

    @Field(181)
    public Pointer<AVCodecContext54.Execute2Callback> execute2() {
        return this.io.getPointerField(this, 181);
    }

    @Field(181)
    public AVCodecContext54 execute2(Pointer<AVCodecContext54.Execute2Callback> execute2) {
        this.io.setPointerField(this, 181, execute2);
        return this;
    }

    @Field(182)
    public Pointer<?> thread_opaque() {
        return this.io.getPointerField(this, 182);
    }

    @Field(182)
    public AVCodecContext54 thread_opaque(Pointer<?> thread_opaque) {
        this.io.setPointerField(this, 182, thread_opaque);
        return this;
    }

    @Field(183)
    public int nsse_weight() {
        return this.io.getIntField(this, 183);
    }

    @Field(183)
    public AVCodecContext54 nsse_weight(int nsse_weight) {
        this.io.setIntField(this, 183, nsse_weight);
        return this;
    }

    @Field(184)
    public int profile() {
        return this.io.getIntField(this, 184);
    }

    @Field(184)
    public AVCodecContext54 profile(int profile) {
        this.io.setIntField(this, 184, profile);
        return this;
    }

    @Field(185)
    public int level() {
        return this.io.getIntField(this, 185);
    }

    @Field(185)
    public AVCodecContext54 level(int level) {
        this.io.setIntField(this, 185, level);
        return this;
    }

    @Field(186)
    public int skip_loop_filter() {
        return this.io.getIntField(this, 186);
    }

    @Field(186)
    public AVCodecContext54 skip_loop_filter(int skip_loop_filter) {
        this.io.setIntField(this, 186, skip_loop_filter);
        return this;
    }

    @Field(187)
    public int skip_idct() {
        return this.io.getIntField(this, 187);
    }

    @Field(187)
    public AVCodecContext54 skip_idct(int skip_idct) {
        this.io.setIntField(this, 187, skip_idct);
        return this;
    }

    @Field(188)
    public int skip_frame() {
        return this.io.getIntField(this, 188);
    }

    @Field(188)
    public AVCodecContext54 skip_frame(int skip_frame) {
        this.io.setIntField(this, 188, skip_frame);
        return this;
    }

    @Field(189)
    public Pointer<Byte> subtitle_header() {
        return this.io.getPointerField(this, 189);
    }

    @Field(189)
    public AVCodecContext54 subtitle_header(Pointer<Byte> subtitle_header) {
        this.io.setPointerField(this, 189, subtitle_header);
        return this;
    }

    @Field(190)
    public int subtitle_header_size() {
        return this.io.getIntField(this, 190);
    }

    @Field(190)
    public AVCodecContext54 subtitle_header_size(int subtitle_header_size) {
        this.io.setIntField(this, 190, subtitle_header_size);
        return this;
    }

    @Field(191)
    public int error_rate() {
        return this.io.getIntField(this, 191);
    }

    @Field(191)
    public AVCodecContext54 error_rate(int error_rate) {
        this.io.setIntField(this, 191, error_rate);
        return this;
    }

    @Field(192)
    public Pointer<?> pkt() {
        return this.io.getPointerField(this, 192);
    }

    @Field(192)
    public AVCodecContext54 pkt(Pointer<?> pkt) {
        this.io.setPointerField(this, 192, pkt);
        return this;
    }

    @Field(193)
    public long vbv_delay() {
        return this.io.getLongField(this, 193);
    }

    @Field(193)
    public AVCodecContext54 vbv_delay(long vbv_delay) {
        this.io.setLongField(this, 193, vbv_delay);
        return this;
    }

    public static abstract class DrawHorizontalBandCallback extends Callback<DrawHorizontalBandCallback> {
        public abstract void apply(Pointer<?> context, Pointer<?> src, Pointer<Integer> offset, int y, int type, int height);
    }

    public static abstract class GetFormatCallback extends Callback<GetFormatCallback> {
        public abstract int apply(Pointer<?> context, Pointer<Integer> fmt);
    }

    public static abstract class BufferCallback extends Callback<BufferCallback> {
        public abstract int apply(Pointer<?> context, Pointer<?> pic);
    }

    public static abstract class RtpCallback extends Callback<RtpCallback> {
        public abstract void apply(Pointer<?> context, Pointer<?> data, int size, int mb_nb);
    }

    public static abstract class ExecuteFunctionArgumentCallback extends Callback<ExecuteFunctionArgumentCallback> {
        public abstract int apply(Pointer<?> context, Pointer<?> arg);
    }

    public static abstract class ExecuteCallback extends Callback<ExecuteCallback> {
        public abstract int apply(Pointer<?> context, Pointer<AVCodecContext54.ExecuteFunctionArgumentCallback> arg1, Pointer<?> arg2, Pointer<Integer> ret, int count, int size);
    }

    public static abstract class Execute2FunctionArgumentCallback extends Callback<Execute2FunctionArgumentCallback> {
        public abstract int apply(Pointer<?> context, Pointer<?> arg, int jobnr, int threadnr);
    }

    public static abstract class Execute2Callback extends Callback<Execute2Callback> {
        public abstract int apply(Pointer<?> context, Pointer<AVCodecContext54.Execute2FunctionArgumentCallback> arg1, Pointer<?> arg2, Pointer<Integer> ret, int count);
    }
    
}

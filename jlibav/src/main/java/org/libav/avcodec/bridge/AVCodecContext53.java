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
 * Mirror of the native AVCodecContext struct for the libavcodec v53.x.x. For
 * details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVCodecContext53 extends StructObject {

    public static final int FF_ER_AGGRESSIVE = (int)3;
    public static final int FF_IDCT_SIMPLENEON = (int)22;
    public static final int FF_DEBUG_VIS_MV_B_BACK = (int)4;
    public static final int FF_IDCT_PS2 = (int)5;
    public static final int FF_LEVEL_UNKNOWN = (int)-99;
    public static final int FF_PROFILE_MPEG2_422 = (int)0;
    public static final int FF_ER_VERY_AGGRESSIVE = (int)4;
    public static final int FF_IDCT_SIMPLEVIS = (int)18;
    public static final int FF_DTG_AFD_14_9 = (int)11;
    public static final int FF_PROFILE_H264_EXTENDED = (int)88;
    public static final int FF_IDCT_LIBMPEG2MMX = (int)4;
    public static final int FF_COMPLIANCE_NORMAL = (int)0;
    public static final int FF_PROFILE_MPEG2_SIMPLE = (int)5;
    public static final int FF_IDCT_SH4 = (int)9;
    public static final int FF_IDCT_XVIDMMX = (int)14;
    public static final int FF_BUG_STD_QPEL = (int)128;
    public static final int FF_IDCT_ALTIVEC = (int)8;
    public static final int FF_PROFILE_DTS_ES = (int)30;
    public static final int FF_CMP_CHROMA = (int)256;
    public static final int FF_PROFILE_VC1_MAIN = (int)1;
    public static final int FF_IDCT_INT = (int)1;
    public static final int FF_PROFILE_MPEG2_SS = (int)2;
    public static final int FF_THREAD_FRAME = (int)1;
    public static final int FF_DTG_AFD_SP_4_3 = (int)15;
    public static final int FF_DTG_AFD_16_9 = (int)10;
    public static final int FF_DEBUG_BUFFERS = (int)32768;
    public static final int FF_PROFILE_H264_MAIN = (int)77;
    public static final int FF_ASPECT_EXTENDED = (int)15;
    public static final int FF_DEBUG_SKIP = (int)128;
    public static final int FF_PROFILE_H264_CONSTRAINED_BASELINE = (int)(66 | (1 << 9));
    public static final int FF_CMP_SATD = (int)2;
    public static final int FF_DEBUG_MMCO = (int)2048;
    public static final int FF_DEBUG_BUGS = (int)4096;
    public static final int FF_IDCT_AUTO = (int)0;
    public static final int X264_PART_I8X8 = (int)2;
    public static final int FF_AA_FLOAT = (int)3;
    public static final int FF_IDCT_WMV2 = (int)19;
    public static final int FF_AA_INT = (int)2;
    public static final int FF_DTG_AFD_4_3_SP_14_9 = (int)13;
    public static final int FF_DCT_ALTIVEC = (int)5;
    public static final int FF_BUG_AMV = (int)32;
    public static final int FF_AA_AUTO = (int)0;
    public static final int FF_CMP_RD = (int)6;
    public static final int FF_BUG_AUTODETECT = (int)1;
    public static final int FF_PROFILE_MPEG2_SNR_SCALABLE = (int)3;
    public static final int FF_COMPLIANCE_STRICT = (int)1;
    public static final int FF_DEBUG_ER = (int)1024;
    public static final int FF_CMP_BIT = (int)5;
    public static final int FF_BUG_AC_VLC = (int)0;
    public static final int FF_DEBUG_PTS = (int)512;
    public static final int FF_BUG_HPEL_CHROMA = (int)2048;
    public static final int FF_CODER_TYPE_RLE = (int)3;
    public static final int FF_DEBUG_MV = (int)32;
    public static final int FF_MB_DECISION_BITS = (int)1;
    public static final int FF_DTG_AFD_4_3 = (int)9;
    public static final int FF_DEBUG_VIS_QP = (int)8192;
    public static final int FF_CODER_TYPE_VLC = (int)0;
    public static final int FF_DEBUG_BITSTREAM = (int)4;
    public static final int FF_PROFILE_DTS_96_24 = (int)40;
    public static final int FF_CMP_DCT264 = (int)14;
    public static final int X264_PART_I4X4 = (int)1;
    public static final int FF_MB_DECISION_RD = (int)2;
    public static final int FF_BUG_NO_PADDING = (int)16;
    public static final int FF_DEFAULT_QUANT_BIAS = (int)999999;
    public static final int FF_EC_DEBLOCK = (int)2;
    public static final int FF_BUG_DC_CLIP = (int)4096;
    public static final int FF_PROFILE_VC1_COMPLEX = (int)2;
    public static final int FF_BUG_QPEL_CHROMA2 = (int)256;
    public static final int FF_PROFILE_MPEG2_MAIN = (int)4;
    public static final int FF_CMP_W97 = (int)12;
    public static final int FF_CMP_DCTMAX = (int)13;
    public static final int FF_IDCT_MLIB = (int)6;
    public static final int FF_IDCT_SIMPLEARMV6 = (int)17;
    public static final int FF_PROFILE_RESERVED = (int)-100;
    public static final int FF_DEBUG_DCT_COEFF = (int)64;
    public static final int FF_PROFILE_AAC_LOW = (int)1;
    public static final int FF_COMPLIANCE_EXPERIMENTAL = (int)-2;
    public static final int X264_PART_P4X4 = (int)32;
    public static final int FF_IDCT_SIMPLE = (int)2;
    public static final int FF_THREAD_SLICE = (int)2;
    public static final int FF_PROFILE_DTS_HD_MA = (int)60;
    public static final int FF_PROFILE_UNKNOWN = (int)-99;
    public static final int FF_DEBUG_VIS_MV_P_FOR = (int)1;
    public static final int FF_PROFILE_AAC_MAIN = (int)0;
    public static final int FF_CMP_VSAD = (int)8;
    public static final int FF_MB_DECISION_SIMPLE = (int)0;
    public static final int FF_CMP_DCT = (int)3;
    public static final int FF_IDCT_SIMPLEARM = (int)10;
    public static final int FF_ER_CAREFUL = (int)1;
    public static final int FF_BUG_EDGE = (int)1024;
    public static final int FF_PROFILE_H264_BASELINE = (int)66;
    public static final int FF_IDCT_SIMPLEMMX = (int)3;
    public static final int FF_IDCT_VP3 = (int)12;
    public static final int FF_CODER_TYPE_AC = (int)1;
    public static final int FF_PRED_PLANE = (int)1;
    public static final int FF_DTG_AFD_SAME = (int)8;
    public static final int FF_PROFILE_H264_CAVLC_444 = (int)44;
    public static final int FF_CMP_SAD = (int)0;
    public static final int FF_BUG_TRUNCATED = (int)16384;
    public static final int FF_DCT_MLIB = (int)4;
    public static final int FF_PROFILE_AAC_SSR = (int)2;
    public static final int SLICE_FLAG_CODED_ORDER = (int)1;
    public static final int FF_PROFILE_VC1_SIMPLE = (int)0;
    public static final int FF_DCT_FASTINT = (int)1;
    public static final int FF_IDCT_SIMPLEALPHA = (int)23;
    public static final int FF_DEBUG_VIS_MB_TYPE = (int)16384;
    public static final int FF_BUG_MS = (int)8192;
    public static final int FF_PROFILE_VC1_ADVANCED = (int)3;
    public static final int FF_COMPLIANCE_VERY_STRICT = (int)2;
    public static final int FF_ER_COMPLIANT = (int)2;
    public static final int FF_BUG_XVID_ILACE = (int)4;
    public static final int FF_IDCT_CAVS = (int)15;
    public static final int FF_IDCT_EA = (int)21;
    public static final int FF_PROFILE_H264_HIGH_10 = (int)110;
    public static final int FF_DTG_AFD_16_9_SP_14_9 = (int)14;
    public static final int FF_BUG_UMP4 = (int)8;
    public static final int X264_PART_B8X8 = (int)256;
    public static final int FF_CMP_VSSE = (int)9;
    public static final int FF_CMP_ZERO = (int)7;
    public static final int FF_IDCT_SIMPLEARMV5TE = (int)16;
    public static final int FF_IDCT_BINK = (int)24;
    public static final int FF_IDCT_FAAN = (int)20;
    public static final int FF_PROFILE_H264_HIGH_10_INTRA = (int)(110 | (1 << 11));
    public static final int FF_PRED_MEDIAN = (int)2;
    public static final int X264_PART_P8X8 = (int)16;
    public static final int FF_PROFILE_H264_HIGH_444 = (int)144;
    public static final int FF_CODER_TYPE_RAW = (int)2;
    public static final int FF_PROFILE_H264_HIGH = (int)100;
    public static final int SLICE_FLAG_ALLOW_FIELD = (int)2;
    public static final int FF_CMP_NSSE = (int)10;
    public static final int FF_PROFILE_H264_INTRA = (int)(1 << 11);
    public static final int FF_PROFILE_H264_CONSTRAINED = (int)(1 << 9);
    public static final int FF_DEBUG_THREADS = (int)65536;
    public static final int FF_DEBUG_QP = (int)16;
    public static final int FF_BUG_QPEL_CHROMA = (int)64;
    public static final int FF_COMPLIANCE_UNOFFICIAL = (int)-1;
    public static final int FF_BUG_DIRECT_BLOCKSIZE = (int)512;
    public static final int SLICE_FLAG_ALLOW_PLANE = (int)4;
    public static final int FF_IDCT_ARM = (int)7;
    public static final int FF_PROFILE_DTS_HD_HRA = (int)50;
    public static final int FF_DEBUG_RC = (int)2;
    public static final int FF_DEBUG_VIS_MV_B_FOR = (int)2;
    public static final int FF_AA_FASTINT = (int)1;
    public static final int FF_PRED_LEFT = (int)0;
    public static final int FF_CMP_W53 = (int)11;
    public static final int FF_IDCT_IPP = (int)13;
    public static final int FF_PROFILE_H264_HIGH_444_PREDICTIVE = (int)244;
    public static final int FF_CMP_SSE = (int)1;
    public static final int FF_EC_GUESS_MVS = (int)1;
    public static final int FF_BUG_OLD_MSMPEG4 = (int)2;
    public static final int FF_DCT_MMX = (int)3;
    public static final int FF_DEBUG_MB_TYPE = (int)8;
    public static final int FF_DEBUG_STARTCODE = (int)256;
    public static final int FF_DCT_FAAN = (int)6;
    public static final int FF_CMP_PSNR = (int)4;
    public static final int FF_DCT_AUTO = (int)0;
    public static final int FF_DEBUG_PICT_INFO = (int)1;
    public static final int FF_COMPRESSION_DEFAULT = (int)-1;
    public static final int FF_RC_STRATEGY_XVID = (int)1;
    public static final int FF_IDCT_H264 = (int)11;
    public static final int FF_PROFILE_DTS = (int)20;
    public static final int FF_PROFILE_H264_HIGH_422_INTRA = (int)(122 | (1 << 11));
    public static final int FF_PROFILE_MPEG2_HIGH = (int)1;
    public static final int FF_DCT_INT = (int)2;
    public static final int FF_PROFILE_H264_HIGH_444_INTRA = (int)(244 | (1 << 11));
    public static final int FF_PROFILE_H264_HIGH_422 = (int)122;
    public static final int FF_CODER_TYPE_DEFLATE = (int)4;
    public static final int FF_PROFILE_AAC_LTP = (int)3;
    
    public AVCodecContext53() {
        super();
    }

    public AVCodecContext53(Pointer p) {
        super(p);
    }

    @Field(0)
    public Pointer<?> av_class() {
        return this.io.getPointerField(this, 0);
    }
    
    @Field(0)
    public AVCodecContext53 av_class(Pointer<?> av_class) {
        this.io.setPointerField(this, 0, av_class);
        return this;
    }

    @Field(1)
    public int bit_rate() {
        return this.io.getIntField(this, 1);
    }

    @Field(1)
    public AVCodecContext53 bit_rate(int bit_rate) {
        this.io.setIntField(this, 1, bit_rate);
        return this;
    }

    @Field(2)
    public int bit_rate_tolerance() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public AVCodecContext53 bit_rate_tolerance(int bit_rate_tolerance) {
        this.io.setIntField(this, 2, bit_rate_tolerance);
        return this;
    }

    @Field(3)
    public int flags() {
        return this.io.getIntField(this, 3);
    }

    @Field(3)
    public AVCodecContext53 flags(int flags) {
        this.io.setIntField(this, 3, flags);
        return this;
    }

    @Field(4)
    public int sub_id() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public AVCodecContext53 sub_id(int sub_id) {
        this.io.setIntField(this, 4, sub_id);
        return this;
    }

    @Field(5)
    public int me_method() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public AVCodecContext53 me_method(int me_method) {
        this.io.setIntField(this, 5, me_method);
        return this;
    }
    
    @Field(6)
    public Pointer<Byte> extradata() {
        return this.io.getPointerField(this, 6);
    }
    
    @Field(6)
    public AVCodecContext53 extradata(Pointer<Byte> extradata) {
        this.io.setPointerField(this, 6, extradata);
        return this;
    }

    @Field(7)
    public int extradata_size() {
        return this.io.getIntField(this, 7);
    }

    @Field(7)
    public AVCodecContext53 extradata_size(int extradata_size) {
        this.io.setIntField(this, 7, extradata_size);
        return this;
    }
    
    @Field(8)
    public AVRational time_base() {
        return this.io.getNativeObjectField(this, 8);
    }
    
    @Field(8)
    public AVCodecContext53 time_base(AVRational time_base) {
        this.io.setNativeObjectField(this, 8, time_base);
        return this;
    }

    @Field(9)
    public int width() {
        return this.io.getIntField(this, 9);
    }

    @Field(9)
    public AVCodecContext53 width(int width) {
        this.io.setIntField(this, 9, width);
        return this;
    }

    @Field(10)
    public int height() {
        return this.io.getIntField(this, 10);
    }

    @Field(10)
    public AVCodecContext53 height(int height) {
        this.io.setIntField(this, 10, height);
        return this;
    }

    @Field(11)
    public int gop_size() {
        return this.io.getIntField(this, 11);
    }

    @Field(11)
    public AVCodecContext53 gop_size(int gop_size) {
        this.io.setIntField(this, 11, gop_size);
        return this;
    }

    @Field(12)
    public int pix_fmt() {
        return this.io.getIntField(this, 12);
    }

    @Field(12)
    public AVCodecContext53 pix_fmt(int pix_fmt) {
        this.io.setIntField(this, 12, pix_fmt);
        return this;
    }
    
    @Field(13)
    public Pointer<AVCodecContext53.DrawHorizontalBandCallback> draw_horiz_band() {
        return this.io.getPointerField(this, 13);
    }
    
    @Field(13)
    public AVCodecContext53 draw_horiz_band(Pointer<AVCodecContext53.DrawHorizontalBandCallback> draw_horiz_band) {
        this.io.setPointerField(this, 13, draw_horiz_band);
        return this;
    }

    @Field(14)
    public int sample_rate() {
        return this.io.getIntField(this, 14);
    }

    @Field(14)
    public AVCodecContext53 sample_rate(int sample_rate) {
        this.io.setIntField(this, 14, sample_rate);
        return this;
    }

    @Field(15)
    public int channels() {
        return this.io.getIntField(this, 15);
    }

    @Field(15)
    public AVCodecContext53 channels(int channels) {
        this.io.setIntField(this, 15, channels);
        return this;
    }

    @Field(16)
    public int sample_fmt() {
        return this.io.getIntField(this, 16);
    }

    @Field(16)
    public AVCodecContext53 sample_fmt(int sample_fmt) {
        this.io.setIntField(this, 16, sample_fmt);
        return this;
    }

    @Field(17)
    public int frame_size() {
        return this.io.getIntField(this, 17);
    }

    @Field(17)
    public AVCodecContext53 frame_size(int frame_size) {
        this.io.setIntField(this, 17, frame_size);
        return this;
    }

    @Field(18)
    public int frame_number() {
        return this.io.getIntField(this, 18);
    }

    @Field(18)
    public AVCodecContext53 frame_number(int frame_number) {
        this.io.setIntField(this, 18, frame_number);
        return this;
    }

    @Field(19)
    public int delay() {
        return this.io.getIntField(this, 19);
    }

    @Field(19)
    public AVCodecContext53 delay(int delay) {
        this.io.setIntField(this, 19, delay);
        return this;
    }

    @Field(20)
    public float qcompress() {
        return this.io.getFloatField(this, 20);
    }

    @Field(20)
    public AVCodecContext53 qcompress(float qcompress) {
        this.io.setFloatField(this, 20, qcompress);
        return this;
    }

    @Field(21)
    public float qblur() {
        return this.io.getFloatField(this, 21);
    }

    @Field(21)
    public AVCodecContext53 qblur(float qblur) {
        this.io.setFloatField(this, 21, qblur);
        return this;
    }

    @Field(22)
    public int qmin() {
        return this.io.getIntField(this, 22);
    }

    @Field(22)
    public AVCodecContext53 qmin(int qmin) {
        this.io.setIntField(this, 22, qmin);
        return this;
    }

    @Field(23)
    public int qmax() {
        return this.io.getIntField(this, 23);
    }

    @Field(23)
    public AVCodecContext53 qmax(int qmax) {
        this.io.setIntField(this, 23, qmax);
        return this;
    }

    @Field(24)
    public int max_qdiff() {
        return this.io.getIntField(this, 24);
    }

    @Field(24)
    public AVCodecContext53 max_qdiff(int max_qdiff) {
        this.io.setIntField(this, 24, max_qdiff);
        return this;
    }

    @Field(25)
    public int max_b_frames() {
        return this.io.getIntField(this, 25);
    }

    @Field(25)
    public AVCodecContext53 max_b_frames(int max_b_frames) {
        this.io.setIntField(this, 25, max_b_frames);
        return this;
    }

    @Field(26)
    public float b_quant_factor() {
        return this.io.getFloatField(this, 26);
    }

    @Field(26)
    public AVCodecContext53 b_quant_factor(float b_quant_factor) {
        this.io.setFloatField(this, 26, b_quant_factor);
        return this;
    }

    @Field(27)
    public int rc_strategy() {
        return this.io.getIntField(this, 27);
    }

    @Field(27)
    public AVCodecContext53 rc_strategy(int rc_strategy) {
        this.io.setIntField(this, 27, rc_strategy);
        return this;
    }

    @Field(28)
    public int b_frame_strategy() {
        return this.io.getIntField(this, 28);
    }

    @Field(28)
    public AVCodecContext53 b_frame_strategy(int b_frame_strategy) {
        this.io.setIntField(this, 28, b_frame_strategy);
        return this;
    }
    
    @Field(29)
    public Pointer<?> codec() {
        return this.io.getPointerField(this, 29);
    }
    
    @Field(29)
    public AVCodecContext53 codec(Pointer<?> codec) {
        this.io.setPointerField(this, 29, codec);
        return this;
    }
    
    @Field(30)
    public Pointer<?> priv_data() {
        return this.io.getPointerField(this, 30);
    }
    
    @Field(30)
    public AVCodecContext53 priv_data(Pointer<?> priv_data) {
        this.io.setPointerField(this, 30, priv_data);
        return this;
    }

    @Field(31)
    public int rtp_payload_size() {
        return this.io.getIntField(this, 31);
    }

    @Field(31)
    public AVCodecContext53 rtp_payload_size(int rtp_payload_size) {
        this.io.setIntField(this, 31, rtp_payload_size);
        return this;
    }
    
    @Field(32)
    public Pointer<AVCodecContext53.RtpCallback> rtp_callback() {
        return this.io.getPointerField(this, 32);
    }
    
    @Field(32)
    public AVCodecContext53 rtp_callback(Pointer<AVCodecContext53.RtpCallback> rtp_callback) {
        this.io.setPointerField(this, 32, rtp_callback);
        return this;
    }

    @Field(33)
    public int mv_bits() {
        return this.io.getIntField(this, 33);
    }

    @Field(33)
    public AVCodecContext53 mv_bits(int mv_bits) {
        this.io.setIntField(this, 33, mv_bits);
        return this;
    }

    @Field(34)
    public int header_bits() {
        return this.io.getIntField(this, 34);
    }

    @Field(34)
    public AVCodecContext53 header_bits(int header_bits) {
        this.io.setIntField(this, 34, header_bits);
        return this;
    }

    @Field(35)
    public int i_tex_bits() {
        return this.io.getIntField(this, 35);
    }

    @Field(35)
    public AVCodecContext53 i_tex_bits(int i_tex_bits) {
        this.io.setIntField(this, 35, i_tex_bits);
        return this;
    }

    @Field(36)
    public int p_tex_bits() {
        return this.io.getIntField(this, 36);
    }

    @Field(36)
    public AVCodecContext53 p_tex_bits(int p_tex_bits) {
        this.io.setIntField(this, 36, p_tex_bits);
        return this;
    }

    @Field(37)
    public int i_count() {
        return this.io.getIntField(this, 37);
    }

    @Field(37)
    public AVCodecContext53 i_count(int i_count) {
        this.io.setIntField(this, 37, i_count);
        return this;
    }

    @Field(38)
    public int p_count() {
        return this.io.getIntField(this, 38);
    }

    @Field(38)
    public AVCodecContext53 p_count(int p_count) {
        this.io.setIntField(this, 38, p_count);
        return this;
    }

    @Field(39)
    public int skip_count() {
        return this.io.getIntField(this, 39);
    }

    @Field(39)
    public AVCodecContext53 skip_count(int skip_count) {
        this.io.setIntField(this, 39, skip_count);
        return this;
    }

    @Field(40)
    public int misc_bits() {
        return this.io.getIntField(this, 40);
    }

    @Field(40)
    public AVCodecContext53 misc_bits(int misc_bits) {
        this.io.setIntField(this, 40, misc_bits);
        return this;
    }

    @Field(41)
    public int frame_bits() {
        return this.io.getIntField(this, 41);
    }

    @Field(41)
    public AVCodecContext53 frame_bits(int frame_bits) {
        this.io.setIntField(this, 41, frame_bits);
        return this;
    }
    
    @Field(42)
    public Pointer<?> opaque() {
        return this.io.getPointerField(this, 42);
    }
    
    @Field(42)
    public AVCodecContext53 opaque(Pointer<?> opaque) {
        this.io.setPointerField(this, 42, opaque);
        return this;
    }
    
    @Array({32})
    @Field(43)
    public Pointer<Byte> codec_name() {
        return this.io.getPointerField(this, 43);
    }

    @Field(44)
    public int codec_type() {
        return this.io.getIntField(this, 44);
    }

    @Field(44)
    public AVCodecContext53 codec_type(int codec_type) {
        this.io.setIntField(this, 44, codec_type);
        return this;
    }

    @Field(45)
    public int codec_id() {
        return this.io.getIntField(this, 45);
    }

    @Field(45)
    public AVCodecContext53 codec_id(int codec_id) {
        this.io.setIntField(this, 45, codec_id);
        return this;
    }

    @Field(46)
    public int codec_tag() {
        return this.io.getIntField(this, 46);
    }

    @Field(46)
    public AVCodecContext53 codec_tag(int codec_tag) {
        this.io.setIntField(this, 46, codec_tag);
        return this;
    }

    @Field(47)
    public int workaround_bugs() {
        return this.io.getIntField(this, 47);
    }

    @Field(47)
    public AVCodecContext53 workaround_bugs(int workaround_bugs) {
        this.io.setIntField(this, 47, workaround_bugs);
        return this;
    }

    @Field(48)
    public int luma_elim_threshold() {
        return this.io.getIntField(this, 48);
    }

    @Field(48)
    public AVCodecContext53 luma_elim_threshold(int luma_elim_threshold) {
        this.io.setIntField(this, 48, luma_elim_threshold);
        return this;
    }

    @Field(49)
    public int chroma_elim_threshold() {
        return this.io.getIntField(this, 49);
    }

    @Field(49)
    public AVCodecContext53 chroma_elim_threshold(int chroma_elim_threshold) {
        this.io.setIntField(this, 49, chroma_elim_threshold);
        return this;
    }

    @Field(50)
    public int strict_std_compliance() {
        return this.io.getIntField(this, 50);
    }

    @Field(50)
    public AVCodecContext53 strict_std_compliance(int strict_std_compliance) {
        this.io.setIntField(this, 50, strict_std_compliance);
        return this;
    }

    @Field(51)
    public float b_quant_offset() {
        return this.io.getFloatField(this, 51);
    }

    @Field(51)
    public AVCodecContext53 b_quant_offset(float b_quant_offset) {
        this.io.setFloatField(this, 51, b_quant_offset);
        return this;
    }

    @Field(52)
    public int error_recognition() {
        return this.io.getIntField(this, 52);
    }

    @Field(52)
    public AVCodecContext53 error_recognition(int error_recognition) {
        this.io.setIntField(this, 52, error_recognition);
        return this;
    }
    
    @Field(53)
    public Pointer<AVCodecContext53.BufferCallback> get_buffer() {
        return this.io.getPointerField(this, 53);
    }
    
    @Field(53)
    public AVCodecContext53 get_buffer(Pointer<AVCodecContext53.BufferCallback> get_buffer) {
        this.io.setPointerField(this, 53, get_buffer);
        return this;
    }
    
    @Field(54)
    public Pointer<AVCodecContext53.BufferCallback> release_buffer() {
        return this.io.getPointerField(this, 54);
    }
    
    @Field(54)
    public AVCodecContext53 release_buffer(Pointer<AVCodecContext53.BufferCallback> release_buffer) {
        this.io.setPointerField(this, 54, release_buffer);
        return this;
    }

    @Field(55)
    public int has_b_frames() {
        return this.io.getIntField(this, 55);
    }

    @Field(55)
    public AVCodecContext53 has_b_frames(int has_b_frames) {
        this.io.setIntField(this, 55, has_b_frames);
        return this;
    }

    @Field(56)
    public int block_align() {
        return this.io.getIntField(this, 56);
    }

    @Field(56)
    public AVCodecContext53 block_align(int block_align) {
        this.io.setIntField(this, 56, block_align);
        return this;
    }

    @Field(57)
    public int parse_only() {
        return this.io.getIntField(this, 57);
    }

    @Field(57)
    public AVCodecContext53 parse_only(int parse_only) {
        this.io.setIntField(this, 57, parse_only);
        return this;
    }

    @Field(58)
    public int mpeg_quant() {
        return this.io.getIntField(this, 58);
    }

    @Field(58)
    public AVCodecContext53 mpeg_quant(int mpeg_quant) {
        this.io.setIntField(this, 58, mpeg_quant);
        return this;
    }
    
    @Field(59)
    public Pointer<Byte> stats_out() {
        return this.io.getPointerField(this, 59);
    }
    
    @Field(59)
    public AVCodecContext53 stats_out(Pointer<Byte> stats_out) {
        this.io.setPointerField(this, 59, stats_out);
        return this;
    }
    
    @Field(60)
    public Pointer<Byte> stats_in() {
        return this.io.getPointerField(this, 60);
    }
    
    @Field(60)
    public AVCodecContext53 stats_in(Pointer<Byte> stats_in) {
        this.io.setPointerField(this, 60, stats_in);
        return this;
    }

    @Field(61)
    public float rc_qsquish() {
        return this.io.getFloatField(this, 61);
    }

    @Field(61)
    public AVCodecContext53 rc_qsquish(float rc_qsquish) {
        this.io.setFloatField(this, 61, rc_qsquish);
        return this;
    }

    @Field(62)
    public float rc_qmod_amp() {
        return this.io.getFloatField(this, 62);
    }

    @Field(62)
    public AVCodecContext53 rc_qmod_amp(float rc_qmod_amp) {
        this.io.setFloatField(this, 62, rc_qmod_amp);
        return this;
    }

    @Field(63)
    public int rc_qmod_freq() {
        return this.io.getIntField(this, 63);
    }

    @Field(63)
    public AVCodecContext53 rc_qmod_freq(int rc_qmod_freq) {
        this.io.setIntField(this, 63, rc_qmod_freq);
        return this;
    }
    
    @Field(64)
    public Pointer<?> rc_override() {
        return this.io.getPointerField(this, 64);
    }
    
    @Field(64)
    public AVCodecContext53 rc_override(Pointer<?> rc_override) {
        this.io.setPointerField(this, 64, rc_override);
        return this;
    }

    @Field(65)
    public int rc_override_count() {
        return this.io.getIntField(this, 65);
    }

    @Field(65)
    public AVCodecContext53 rc_override_count(int rc_override_count) {
        this.io.setIntField(this, 65, rc_override_count);
        return this;
    }
    
    @Field(66)
    public Pointer<Byte> rc_eq() {
        return this.io.getPointerField(this, 66);
    }
    
    @Field(66)
    public AVCodecContext53 rc_eq(Pointer<Byte> rc_eq) {
        this.io.setPointerField(this, 66, rc_eq);
        return this;
    }

    @Field(67)
    public int rc_max_rate() {
        return this.io.getIntField(this, 67);
    }

    @Field(67)
    public AVCodecContext53 rc_max_rate(int rc_max_rate) {
        this.io.setIntField(this, 67, rc_max_rate);
        return this;
    }

    @Field(68)
    public int rc_min_rate() {
        return this.io.getIntField(this, 68);
    }

    @Field(68)
    public AVCodecContext53 rc_min_rate(int rc_min_rate) {
        this.io.setIntField(this, 68, rc_min_rate);
        return this;
    }

    @Field(69)
    public int rc_buffer_size() {
        return this.io.getIntField(this, 69);
    }

    @Field(69)
    public AVCodecContext53 rc_buffer_size(int rc_buffer_size) {
        this.io.setIntField(this, 69, rc_buffer_size);
        return this;
    }

    @Field(70)
    public float rc_buffer_aggressivity() {
        return this.io.getFloatField(this, 70);
    }

    @Field(70)
    public AVCodecContext53 rc_buffer_aggressivity(float rc_buffer_aggressivity) {
        this.io.setFloatField(this, 70, rc_buffer_aggressivity);
        return this;
    }

    @Field(71)
    public float i_quant_factor() {
        return this.io.getFloatField(this, 71);
    }

    @Field(71)
    public AVCodecContext53 i_quant_factor(float i_quant_factor) {
        this.io.setFloatField(this, 71, i_quant_factor);
        return this;
    }

    @Field(72)
    public float i_quant_offset() {
        return this.io.getFloatField(this, 72);
    }

    @Field(72)
    public AVCodecContext53 i_quant_offset(float i_quant_offset) {
        this.io.setFloatField(this, 72, i_quant_offset);
        return this;
    }

    @Field(73)
    public float rc_initial_cplx() {
        return this.io.getFloatField(this, 73);
    }

    @Field(73)
    public AVCodecContext53 rc_initial_cplx(float rc_initial_cplx) {
        this.io.setFloatField(this, 73, rc_initial_cplx);
        return this;
    }

    @Field(74)
    public int dct_algo() {
        return this.io.getIntField(this, 74);
    }

    @Field(74)
    public AVCodecContext53 dct_algo(int dct_algo) {
        this.io.setIntField(this, 74, dct_algo);
        return this;
    }

    @Field(75)
    public float lumi_masking() {
        return this.io.getFloatField(this, 75);
    }

    @Field(75)
    public AVCodecContext53 lumi_masking(float lumi_masking) {
        this.io.setFloatField(this, 75, lumi_masking);
        return this;
    }

    @Field(76)
    public float temporal_cplx_masking() {
        return this.io.getFloatField(this, 76);
    }

    @Field(76)
    public AVCodecContext53 temporal_cplx_masking(float temporal_cplx_masking) {
        this.io.setFloatField(this, 76, temporal_cplx_masking);
        return this;
    }

    @Field(77)
    public float spatial_cplx_masking() {
        return this.io.getFloatField(this, 77);
    }

    @Field(77)
    public AVCodecContext53 spatial_cplx_masking(float spatial_cplx_masking) {
        this.io.setFloatField(this, 77, spatial_cplx_masking);
        return this;
    }

    @Field(78)
    public float p_masking() {
        return this.io.getFloatField(this, 78);
    }

    @Field(78)
    public AVCodecContext53 p_masking(float p_masking) {
        this.io.setFloatField(this, 78, p_masking);
        return this;
    }

    @Field(79)
    public float dark_masking() {
        return this.io.getFloatField(this, 79);
    }

    @Field(79)
    public AVCodecContext53 dark_masking(float dark_masking) {
        this.io.setFloatField(this, 79, dark_masking);
        return this;
    }

    @Field(80)
    public int idct_algo() {
        return this.io.getIntField(this, 80);
    }

    @Field(80)
    public AVCodecContext53 idct_algo(int idct_algo) {
        this.io.setIntField(this, 80, idct_algo);
        return this;
    }

    @Field(81)
    public int slice_count() {
        return this.io.getIntField(this, 81);
    }

    @Field(81)
    public AVCodecContext53 slice_count(int slice_count) {
        this.io.setIntField(this, 81, slice_count);
        return this;
    }
    
    @Field(82)
    public Pointer<Integer> slice_offset() {
        return this.io.getPointerField(this, 82);
    }
    
    @Field(82)
    public AVCodecContext53 slice_offset(Pointer<Integer> slice_offset) {
        this.io.setPointerField(this, 82, slice_offset);
        return this;
    }

    @Field(83)
    public int error_concealment() {
        return this.io.getIntField(this, 83);
    }

    @Field(83)
    public AVCodecContext53 error_concealment(int error_concealment) {
        this.io.setIntField(this, 83, error_concealment);
        return this;
    }

    @Field(84)
    public int dsp_mask() {
        return this.io.getIntField(this, 84);
    }

    @Field(84)
    public AVCodecContext53 dsp_mask(int dsp_mask) {
        this.io.setIntField(this, 84, dsp_mask);
        return this;
    }

    @Field(85)
    public int bits_per_coded_sample() {
        return this.io.getIntField(this, 85);
    }

    @Field(85)
    public AVCodecContext53 bits_per_coded_sample(int bits_per_coded_sample) {
        this.io.setIntField(this, 85, bits_per_coded_sample);
        return this;
    }

    @Field(86)
    public int prediction_method() {
        return this.io.getIntField(this, 86);
    }

    @Field(86)
    public AVCodecContext53 prediction_method(int prediction_method) {
        this.io.setIntField(this, 86, prediction_method);
        return this;
    }
    
    @Field(87)
    public AVRational sample_aspect_ratio() {
        return this.io.getNativeObjectField(this, 87);
    }
    
    @Field(87)
    public AVCodecContext53 sample_aspect_ratio(AVRational sample_aspect_ratio) {
        this.io.setNativeObjectField(this, 87, sample_aspect_ratio);
        return this;
    }
    
    @Field(88)
    public Pointer<?> coded_frame() {
        return this.io.getPointerField(this, 88);
    }
    
    @Field(88)
    public AVCodecContext53 coded_frame(Pointer<?> coded_frame) {
        this.io.setPointerField(this, 88, coded_frame);
        return this;
    }

    @Field(89)
    public int debug() {
        return this.io.getIntField(this, 89);
    }

    @Field(89)
    public AVCodecContext53 debug(int debug) {
        this.io.setIntField(this, 89, debug);
        return this;
    }

    @Field(90)
    public int debug_mv() {
        return this.io.getIntField(this, 90);
    }

    @Field(90)
    public AVCodecContext53 debug_mv(int debug_mv) {
        this.io.setIntField(this, 90, debug_mv);
        return this;
    }
    
    @Array({4})
    @Field(91)
    public Pointer<Long> error() {
        return this.io.getPointerField(this, 91);
    }

    @Field(92)
    public int me_cmp() {
        return this.io.getIntField(this, 92);
    }

    @Field(92)
    public AVCodecContext53 me_cmp(int me_cmp) {
        this.io.setIntField(this, 92, me_cmp);
        return this;
    }

    @Field(93)
    public int me_sub_cmp() {
        return this.io.getIntField(this, 93);
    }

    @Field(93)
    public AVCodecContext53 me_sub_cmp(int me_sub_cmp) {
        this.io.setIntField(this, 93, me_sub_cmp);
        return this;
    }

    @Field(94)
    public int mb_cmp() {
        return this.io.getIntField(this, 94);
    }

    @Field(94)
    public AVCodecContext53 mb_cmp(int mb_cmp) {
        this.io.setIntField(this, 94, mb_cmp);
        return this;
    }

    @Field(95)
    public int ildct_cmp() {
        return this.io.getIntField(this, 95);
    }

    @Field(95)
    public AVCodecContext53 ildct_cmp(int ildct_cmp) {
        this.io.setIntField(this, 95, ildct_cmp);
        return this;
    }

    @Field(96)
    public int dia_size() {
        return this.io.getIntField(this, 96);
    }

    @Field(96)
    public AVCodecContext53 dia_size(int dia_size) {
        this.io.setIntField(this, 96, dia_size);
        return this;
    }

    @Field(97)
    public int last_predictor_count() {
        return this.io.getIntField(this, 97);
    }

    @Field(97)
    public AVCodecContext53 last_predictor_count(int last_predictor_count) {
        this.io.setIntField(this, 97, last_predictor_count);
        return this;
    }

    @Field(98)
    public int pre_me() {
        return this.io.getIntField(this, 98);
    }

    @Field(98)
    public AVCodecContext53 pre_me(int pre_me) {
        this.io.setIntField(this, 98, pre_me);
        return this;
    }

    @Field(99)
    public int me_pre_cmp() {
        return this.io.getIntField(this, 99);
    }

    @Field(99)
    public AVCodecContext53 me_pre_cmp(int me_pre_cmp) {
        this.io.setIntField(this, 99, me_pre_cmp);
        return this;
    }

    @Field(100)
    public int pre_dia_size() {
        return this.io.getIntField(this, 100);
    }

    @Field(100)
    public AVCodecContext53 pre_dia_size(int pre_dia_size) {
        this.io.setIntField(this, 100, pre_dia_size);
        return this;
    }

    @Field(101)
    public int me_subpel_quality() {
        return this.io.getIntField(this, 101);
    }

    @Field(101)
    public AVCodecContext53 me_subpel_quality(int me_subpel_quality) {
        this.io.setIntField(this, 101, me_subpel_quality);
        return this;
    }
    
    @Field(102)
    public Pointer<AVCodecContext53.GetFormatCallback> get_format() {
        return this.io.getPointerField(this, 102);
    }
    
    @Field(102)
    public AVCodecContext53 get_format(Pointer<AVCodecContext53.GetFormatCallback> get_format) {
        this.io.setPointerField(this, 102, get_format);
        return this;
    }

    @Field(103)
    public int dtg_active_format() {
        return this.io.getIntField(this, 103);
    }

    @Field(103)
    public AVCodecContext53 dtg_active_format(int dtg_active_format) {
        this.io.setIntField(this, 103, dtg_active_format);
        return this;
    }

    @Field(104)
    public int me_range() {
        return this.io.getIntField(this, 104);
    }

    @Field(104)
    public AVCodecContext53 me_range(int me_range) {
        this.io.setIntField(this, 104, me_range);
        return this;
    }

    @Field(105)
    public int intra_quant_bias() {
        return this.io.getIntField(this, 105);
    }

    @Field(105)
    public AVCodecContext53 intra_quant_bias(int intra_quant_bias) {
        this.io.setIntField(this, 105, intra_quant_bias);
        return this;
    }

    @Field(106)
    public int inter_quant_bias() {
        return this.io.getIntField(this, 106);
    }

    @Field(106)
    public AVCodecContext53 inter_quant_bias(int inter_quant_bias) {
        this.io.setIntField(this, 106, inter_quant_bias);
        return this;
    }

    @Field(107)
    public int color_table_id() {
        return this.io.getIntField(this, 107);
    }

    @Field(107)
    public AVCodecContext53 color_table_id(int color_table_id) {
        this.io.setIntField(this, 107, color_table_id);
        return this;
    }

    @Field(108)
    public int internal_buffer_count() {
        return this.io.getIntField(this, 108);
    }

    @Field(108)
    public AVCodecContext53 internal_buffer_count(int internal_buffer_count) {
        this.io.setIntField(this, 108, internal_buffer_count);
        return this;
    }
    
    @Field(109)
    public Pointer<?> internal_buffer() {
        return this.io.getPointerField(this, 109);
    }
    
    @Field(109)
    public AVCodecContext53 internal_buffer(Pointer<?> internal_buffer) {
        this.io.setPointerField(this, 109, internal_buffer);
        return this;
    }

    @Field(110)
    public int global_quality() {
        return this.io.getIntField(this, 110);
    }

    @Field(110)
    public AVCodecContext53 global_quality(int global_quality) {
        this.io.setIntField(this, 110, global_quality);
        return this;
    }

    @Field(111)
    public int coder_type() {
        return this.io.getIntField(this, 111);
    }

    @Field(111)
    public AVCodecContext53 coder_type(int coder_type) {
        this.io.setIntField(this, 111, coder_type);
        return this;
    }

    @Field(112)
    public int context_model() {
        return this.io.getIntField(this, 112);
    }

    @Field(112)
    public AVCodecContext53 context_model(int context_model) {
        this.io.setIntField(this, 112, context_model);
        return this;
    }

    @Field(113)
    public int slice_flags() {
        return this.io.getIntField(this, 113);
    }

    @Field(113)
    public AVCodecContext53 slice_flags(int slice_flags) {
        this.io.setIntField(this, 113, slice_flags);
        return this;
    }

    @Field(114)
    public int xvmc_acceleration() {
        return this.io.getIntField(this, 114);
    }

    @Field(114)
    public AVCodecContext53 xvmc_acceleration(int xvmc_acceleration) {
        this.io.setIntField(this, 114, xvmc_acceleration);
        return this;
    }

    @Field(115)
    public int mb_decision() {
        return this.io.getIntField(this, 115);
    }

    @Field(115)
    public AVCodecContext53 mb_decision(int mb_decision) {
        this.io.setIntField(this, 115, mb_decision);
        return this;
    }
    
    @Field(116)
    public Pointer<Short> intra_matrix() {
        return this.io.getPointerField(this, 116);
    }
    
    @Field(116)
    public AVCodecContext53 intra_matrix(Pointer<Short> intra_matrix) {
        this.io.setPointerField(this, 116, intra_matrix);
        return this;
    }
    
    @Field(117)
    public Pointer<Short> inter_matrix() {
        return this.io.getPointerField(this, 117);
    }
    
    @Field(117)
    public AVCodecContext53 inter_matrix(Pointer<Short> inter_matrix) {
        this.io.setPointerField(this, 117, inter_matrix);
        return this;
    }

    @Field(118)
    public int stream_codec_tag() {
        return this.io.getIntField(this, 118);
    }

    @Field(118)
    public AVCodecContext53 stream_codec_tag(int stream_codec_tag) {
        this.io.setIntField(this, 118, stream_codec_tag);
        return this;
    }

    @Field(119)
    public int scenechange_threshold() {
        return this.io.getIntField(this, 119);
    }

    @Field(119)
    public AVCodecContext53 scenechange_threshold(int scenechange_threshold) {
        this.io.setIntField(this, 119, scenechange_threshold);
        return this;
    }

    @Field(120)
    public int lmin() {
        return this.io.getIntField(this, 120);
    }

    @Field(120)
    public AVCodecContext53 lmin(int lmin) {
        this.io.setIntField(this, 120, lmin);
        return this;
    }

    @Field(121)
    public int lmax() {
        return this.io.getIntField(this, 121);
    }

    @Field(121)
    public AVCodecContext53 lmax(int lmax) {
        this.io.setIntField(this, 121, lmax);
        return this;
    }
    
    @Field(122)
    public Pointer<?> palctrl() {
        return this.io.getPointerField(this, 122);
    }
    
    @Field(122)
    public AVCodecContext53 palctrl(Pointer<?> palctrl) {
        this.io.setPointerField(this, 122, palctrl);
        return this;
    }

    @Field(123)
    public int noise_reduction() {
        return this.io.getIntField(this, 123);
    }

    @Field(123)
    public AVCodecContext53 noise_reduction(int noise_reduction) {
        this.io.setIntField(this, 123, noise_reduction);
        return this;
    }
    
    @Field(124)
    public Pointer<AVCodecContext53.BufferCallback> reget_buffer() {
        return this.io.getPointerField(this, 124);
    }
    
    @Field(124)
    public AVCodecContext53 reget_buffer(Pointer<AVCodecContext53.BufferCallback> reget_buffer) {
        this.io.setPointerField(this, 124, reget_buffer);
        return this;
    }

    @Field(125)
    public int rc_initial_buffer_occupancy() {
        return this.io.getIntField(this, 125);
    }

    @Field(125)
    public AVCodecContext53 rc_initial_buffer_occupancy(int rc_initial_buffer_occupancy) {
        this.io.setIntField(this, 125, rc_initial_buffer_occupancy);
        return this;
    }

    @Field(126)
    public int inter_threshold() {
        return this.io.getIntField(this, 126);
    }

    @Field(126)
    public AVCodecContext53 inter_threshold(int inter_threshold) {
        this.io.setIntField(this, 126, inter_threshold);
        return this;
    }

    @Field(127)
    public int flags2() {
        return this.io.getIntField(this, 127);
    }

    @Field(127)
    public AVCodecContext53 flags2(int flags2) {
        this.io.setIntField(this, 127, flags2);
        return this;
    }

    @Field(128)
    public int error_rate() {
        return this.io.getIntField(this, 128);
    }

    @Field(128)
    public AVCodecContext53 error_rate(int error_rate) {
        this.io.setIntField(this, 128, error_rate);
        return this;
    }

    @Field(129)
    public int antialias_algo() {
        return this.io.getIntField(this, 129);
    }

    @Field(129)
    public AVCodecContext53 antialias_algo(int antialias_algo) {
        this.io.setIntField(this, 129, antialias_algo);
        return this;
    }

    @Field(130)
    public int quantizer_noise_shaping() {
        return this.io.getIntField(this, 130);
    }

    @Field(130)
    public AVCodecContext53 quantizer_noise_shaping(int quantizer_noise_shaping) {
        this.io.setIntField(this, 130, quantizer_noise_shaping);
        return this;
    }

    @Field(131)
    public int thread_count() {
        return this.io.getIntField(this, 131);
    }

    @Field(131)
    public AVCodecContext53 thread_count(int thread_count) {
        this.io.setIntField(this, 131, thread_count);
        return this;
    }
    
    @Field(132)
    public Pointer<AVCodecContext53.ExecuteCallback> execute() {
        return this.io.getPointerField(this, 132);
    }
    
    @Field(132)
    public AVCodecContext53 execute(Pointer<AVCodecContext53.ExecuteCallback> execute) {
        this.io.setPointerField(this, 132, execute);
        return this;
    }
    
    @Field(133)
    public Pointer<?> thread_opaque() {
        return this.io.getPointerField(this, 133);
    }
    
    @Field(133)
    public AVCodecContext53 thread_opaque(Pointer<?> thread_opaque) {
        this.io.setPointerField(this, 133, thread_opaque);
        return this;
    }

    @Field(134)
    public int me_threshold() {
        return this.io.getIntField(this, 134);
    }

    @Field(134)
    public AVCodecContext53 me_threshold(int me_threshold) {
        this.io.setIntField(this, 134, me_threshold);
        return this;
    }

    @Field(135)
    public int mb_threshold() {
        return this.io.getIntField(this, 135);
    }

    @Field(135)
    public AVCodecContext53 mb_threshold(int mb_threshold) {
        this.io.setIntField(this, 135, mb_threshold);
        return this;
    }

    @Field(136)
    public int intra_dc_precision() {
        return this.io.getIntField(this, 136);
    }

    @Field(136)
    public AVCodecContext53 intra_dc_precision(int intra_dc_precision) {
        this.io.setIntField(this, 136, intra_dc_precision);
        return this;
    }

    @Field(137)
    public int nsse_weight() {
        return this.io.getIntField(this, 137);
    }

    @Field(137)
    public AVCodecContext53 nsse_weight(int nsse_weight) {
        this.io.setIntField(this, 137, nsse_weight);
        return this;
    }

    @Field(138)
    public int skip_top() {
        return this.io.getIntField(this, 138);
    }

    @Field(138)
    public AVCodecContext53 skip_top(int skip_top) {
        this.io.setIntField(this, 138, skip_top);
        return this;
    }

    @Field(139)
    public int skip_bottom() {
        return this.io.getIntField(this, 139);
    }

    @Field(139)
    public AVCodecContext53 skip_bottom(int skip_bottom) {
        this.io.setIntField(this, 139, skip_bottom);
        return this;
    }

    @Field(140)
    public int profile() {
        return this.io.getIntField(this, 140);
    }

    @Field(140)
    public AVCodecContext53 profile(int profile) {
        this.io.setIntField(this, 140, profile);
        return this;
    }

    @Field(141)
    public int level() {
        return this.io.getIntField(this, 141);
    }

    @Field(141)
    public AVCodecContext53 level(int level) {
        this.io.setIntField(this, 141, level);
        return this;
    }

    @Field(142)
    public int lowres() {
        return this.io.getIntField(this, 142);
    }

    @Field(142)
    public AVCodecContext53 lowres(int lowres) {
        this.io.setIntField(this, 142, lowres);
        return this;
    }

    @Field(143)
    public int coded_width() {
        return this.io.getIntField(this, 143);
    }

    @Field(143)
    public AVCodecContext53 coded_width(int coded_width) {
        this.io.setIntField(this, 143, coded_width);
        return this;
    }

    @Field(144)
    public int coded_height() {
        return this.io.getIntField(this, 144);
    }

    @Field(144)
    public AVCodecContext53 coded_height(int coded_height) {
        this.io.setIntField(this, 144, coded_height);
        return this;
    }

    @Field(145)
    public int frame_skip_threshold() {
        return this.io.getIntField(this, 145);
    }

    @Field(145)
    public AVCodecContext53 frame_skip_threshold(int frame_skip_threshold) {
        this.io.setIntField(this, 145, frame_skip_threshold);
        return this;
    }

    @Field(146)
    public int frame_skip_factor() {
        return this.io.getIntField(this, 146);
    }

    @Field(146)
    public AVCodecContext53 frame_skip_factor(int frame_skip_factor) {
        this.io.setIntField(this, 146, frame_skip_factor);
        return this;
    }

    @Field(147)
    public int frame_skip_exp() {
        return this.io.getIntField(this, 147);
    }

    @Field(147)
    public AVCodecContext53 frame_skip_exp(int frame_skip_exp) {
        this.io.setIntField(this, 147, frame_skip_exp);
        return this;
    }

    @Field(148)
    public int frame_skip_cmp() {
        return this.io.getIntField(this, 148);
    }

    @Field(148)
    public AVCodecContext53 frame_skip_cmp(int frame_skip_cmp) {
        this.io.setIntField(this, 148, frame_skip_cmp);
        return this;
    }

    @Field(149)
    public float border_masking() {
        return this.io.getFloatField(this, 149);
    }

    @Field(149)
    public AVCodecContext53 border_masking(float border_masking) {
        this.io.setFloatField(this, 149, border_masking);
        return this;
    }

    @Field(150)
    public int mb_lmin() {
        return this.io.getIntField(this, 150);
    }

    @Field(150)
    public AVCodecContext53 mb_lmin(int mb_lmin) {
        this.io.setIntField(this, 150, mb_lmin);
        return this;
    }

    @Field(151)
    public int mb_lmax() {
        return this.io.getIntField(this, 151);
    }

    @Field(151)
    public AVCodecContext53 mb_lmax(int mb_lmax) {
        this.io.setIntField(this, 151, mb_lmax);
        return this;
    }

    @Field(152)
    public int me_penalty_compensation() {
        return this.io.getIntField(this, 152);
    }

    @Field(152)
    public AVCodecContext53 me_penalty_compensation(int me_penalty_compensation) {
        this.io.setIntField(this, 152, me_penalty_compensation);
        return this;
    }

    @Field(153)
    public int skip_loop_filter() {
        return this.io.getIntField(this, 153);
    }

    @Field(153)
    public AVCodecContext53 skip_loop_filter(int skip_loop_filter) {
        this.io.setIntField(this, 153, skip_loop_filter);
        return this;
    }

    @Field(154)
    public int skip_idct() {
        return this.io.getIntField(this, 154);
    }

    @Field(154)
    public AVCodecContext53 skip_idct(int skip_idct) {
        this.io.setIntField(this, 154, skip_idct);
        return this;
    }

    @Field(155)
    public int skip_frame() {
        return this.io.getIntField(this, 155);
    }

    @Field(155)
    public AVCodecContext53 skip_frame(int skip_frame) {
        this.io.setIntField(this, 155, skip_frame);
        return this;
    }

    @Field(156)
    public int bidir_refine() {
        return this.io.getIntField(this, 156);
    }

    @Field(156)
    public AVCodecContext53 bidir_refine(int bidir_refine) {
        this.io.setIntField(this, 156, bidir_refine);
        return this;
    }

    @Field(157)
    public int brd_scale() {
        return this.io.getIntField(this, 157);
    }

    @Field(157)
    public AVCodecContext53 brd_scale(int brd_scale) {
        this.io.setIntField(this, 157, brd_scale);
        return this;
    }

    @Field(158)
    public float crf() {
        return this.io.getFloatField(this, 158);
    }

    @Field(158)
    public AVCodecContext53 crf(float crf) {
        this.io.setFloatField(this, 158, crf);
        return this;
    }

    @Field(159)
    public int cqp() {
        return this.io.getIntField(this, 159);
    }

    @Field(159)
    public AVCodecContext53 cqp(int cqp) {
        this.io.setIntField(this, 159, cqp);
        return this;
    }

    @Field(160)
    public int keyint_min() {
        return this.io.getIntField(this, 160);
    }

    @Field(160)
    public AVCodecContext53 keyint_min(int keyint_min) {
        this.io.setIntField(this, 160, keyint_min);
        return this;
    }

    @Field(161)
    public int refs() {
        return this.io.getIntField(this, 161);
    }

    @Field(161)
    public AVCodecContext53 refs(int refs) {
        this.io.setIntField(this, 161, refs);
        return this;
    }

    @Field(162)
    public int chromaoffset() {
        return this.io.getIntField(this, 162);
    }

    @Field(162)
    public AVCodecContext53 chromaoffset(int chromaoffset) {
        this.io.setIntField(this, 162, chromaoffset);
        return this;
    }

    @Field(163)
    public int bframebias() {
        return this.io.getIntField(this, 163);
    }

    @Field(163)
    public AVCodecContext53 bframebias(int bframebias) {
        this.io.setIntField(this, 163, bframebias);
        return this;
    }

    @Field(164)
    public int trellis() {
        return this.io.getIntField(this, 164);
    }

    @Field(164)
    public AVCodecContext53 trellis(int trellis) {
        this.io.setIntField(this, 164, trellis);
        return this;
    }

    @Field(165)
    public float complexityblur() {
        return this.io.getFloatField(this, 165);
    }

    @Field(165)
    public AVCodecContext53 complexityblur(float complexityblur) {
        this.io.setFloatField(this, 165, complexityblur);
        return this;
    }

    @Field(166)
    public int deblockalpha() {
        return this.io.getIntField(this, 166);
    }

    @Field(166)
    public AVCodecContext53 deblockalpha(int deblockalpha) {
        this.io.setIntField(this, 166, deblockalpha);
        return this;
    }

    @Field(167)
    public int deblockbeta() {
        return this.io.getIntField(this, 167);
    }

    @Field(167)
    public AVCodecContext53 deblockbeta(int deblockbeta) {
        this.io.setIntField(this, 167, deblockbeta);
        return this;
    }

    @Field(168)
    public int partitions() {
        return this.io.getIntField(this, 168);
    }

    @Field(168)
    public AVCodecContext53 partitions(int partitions) {
        this.io.setIntField(this, 168, partitions);
        return this;
    }

    @Field(169)
    public int directpred() {
        return this.io.getIntField(this, 169);
    }

    @Field(169)
    public AVCodecContext53 directpred(int directpred) {
        this.io.setIntField(this, 169, directpred);
        return this;
    }

    @Field(170)
    public int cutoff() {
        return this.io.getIntField(this, 170);
    }

    @Field(170)
    public AVCodecContext53 cutoff(int cutoff) {
        this.io.setIntField(this, 170, cutoff);
        return this;
    }

    @Field(171)
    public int scenechange_factor() {
        return this.io.getIntField(this, 171);
    }

    @Field(171)
    public AVCodecContext53 scenechange_factor(int scenechange_factor) {
        this.io.setIntField(this, 171, scenechange_factor);
        return this;
    }

    @Field(172)
    public int mv0_threshold() {
        return this.io.getIntField(this, 172);
    }

    @Field(172)
    public AVCodecContext53 mv0_threshold(int mv0_threshold) {
        this.io.setIntField(this, 172, mv0_threshold);
        return this;
    }

    @Field(173)
    public int b_sensitivity() {
        return this.io.getIntField(this, 173);
    }

    @Field(173)
    public AVCodecContext53 b_sensitivity(int b_sensitivity) {
        this.io.setIntField(this, 173, b_sensitivity);
        return this;
    }

    @Field(174)
    public int compression_level() {
        return this.io.getIntField(this, 174);
    }

    @Field(174)
    public AVCodecContext53 compression_level(int compression_level) {
        this.io.setIntField(this, 174, compression_level);
        return this;
    }

    @Field(175)
    public int min_prediction_order() {
        return this.io.getIntField(this, 175);
    }

    @Field(175)
    public AVCodecContext53 min_prediction_order(int min_prediction_order) {
        this.io.setIntField(this, 175, min_prediction_order);
        return this;
    }

    @Field(176)
    public int max_prediction_order() {
        return this.io.getIntField(this, 176);
    }

    @Field(176)
    public AVCodecContext53 max_prediction_order(int max_prediction_order) {
        this.io.setIntField(this, 176, max_prediction_order);
        return this;
    }

    @Field(177)
    public int lpc_coeff_precision() {
        return this.io.getIntField(this, 177);
    }

    @Field(177)
    public AVCodecContext53 lpc_coeff_precision(int lpc_coeff_precision) {
        this.io.setIntField(this, 177, lpc_coeff_precision);
        return this;
    }

    @Field(178)
    public int prediction_order_method() {
        return this.io.getIntField(this, 178);
    }

    @Field(178)
    public AVCodecContext53 prediction_order_method(int prediction_order_method) {
        this.io.setIntField(this, 178, prediction_order_method);
        return this;
    }

    @Field(179)
    public int min_partition_order() {
        return this.io.getIntField(this, 179);
    }

    @Field(179)
    public AVCodecContext53 min_partition_order(int min_partition_order) {
        this.io.setIntField(this, 179, min_partition_order);
        return this;
    }

    @Field(180)
    public int max_partition_order() {
        return this.io.getIntField(this, 180);
    }

    @Field(180)
    public AVCodecContext53 max_partition_order(int max_partition_order) {
        this.io.setIntField(this, 180, max_partition_order);
        return this;
    }

    @Field(181)
    public long timecode_frame_start() {
        return this.io.getLongField(this, 181);
    }

    @Field(181)
    public AVCodecContext53 timecode_frame_start(long timecode_frame_start) {
        this.io.setLongField(this, 181, timecode_frame_start);
        return this;
    }

    @Field(182)
    public int request_channels() {
        return this.io.getIntField(this, 182);
    }

    @Field(182)
    public AVCodecContext53 request_channels(int request_channels) {
        this.io.setIntField(this, 182, request_channels);
        return this;
    }

    @Field(183)
    public float drc_scale() {
        return this.io.getFloatField(this, 183);
    }

    @Field(183)
    public AVCodecContext53 drc_scale(float drc_scale) {
        this.io.setFloatField(this, 183, drc_scale);
        return this;
    }

    @Field(184)
    public long reordered_opaque() {
        return this.io.getLongField(this, 184);
    }

    @Field(184)
    public AVCodecContext53 reordered_opaque(long reordered_opaque) {
        this.io.setLongField(this, 184, reordered_opaque);
        return this;
    }

    @Field(185)
    public int bits_per_raw_sample() {
        return this.io.getIntField(this, 185);
    }

    @Field(185)
    public AVCodecContext53 bits_per_raw_sample(int bits_per_raw_sample) {
        this.io.setIntField(this, 185, bits_per_raw_sample);
        return this;
    }

    @Field(186)
    public long channel_layout() {
        return this.io.getLongField(this, 186);
    }

    @Field(186)
    public AVCodecContext53 channel_layout(long channel_layout) {
        this.io.setLongField(this, 186, channel_layout);
        return this;
    }

    @Field(187)
    public long request_channel_layout() {
        return this.io.getLongField(this, 187);
    }

    @Field(187)
    public AVCodecContext53 request_channel_layout(long request_channel_layout) {
        this.io.setLongField(this, 187, request_channel_layout);
        return this;
    }

    @Field(188)
    public float rc_max_available_vbv_use() {
        return this.io.getFloatField(this, 188);
    }

    @Field(188)
    public AVCodecContext53 rc_max_available_vbv_use(float rc_max_available_vbv_use) {
        this.io.setFloatField(this, 188, rc_max_available_vbv_use);
        return this;
    }

    @Field(189)
    public float rc_min_vbv_overflow_use() {
        return this.io.getFloatField(this, 189);
    }

    @Field(189)
    public AVCodecContext53 rc_min_vbv_overflow_use(float rc_min_vbv_overflow_use) {
        this.io.setFloatField(this, 189, rc_min_vbv_overflow_use);
        return this;
    }
    
    @Field(190)
    public Pointer<?> hwaccel() {
        return this.io.getPointerField(this, 190);
    }
    
    @Field(190)
    public AVCodecContext53 hwaccel(Pointer<?> hwaccel) {
        this.io.setPointerField(this, 190, hwaccel);
        return this;
    }

    @Field(191)
    public int ticks_per_frame() {
        return this.io.getIntField(this, 191);
    }

    @Field(191)
    public AVCodecContext53 ticks_per_frame(int ticks_per_frame) {
        this.io.setIntField(this, 191, ticks_per_frame);
        return this;
    }
    
    @Field(192)
    public Pointer<?> hwaccel_context() {
        return this.io.getPointerField(this, 192);
    }
    
    @Field(192)
    public AVCodecContext53 hwaccel_context(Pointer<?> hwaccel_context) {
        this.io.setPointerField(this, 192, hwaccel_context);
        return this;
    }

    @Field(193)
    public int color_primaries() {
        return this.io.getIntField(this, 193);
    }

    @Field(193)
    public AVCodecContext53 color_primaries(int color_primaries) {
        this.io.setIntField(this, 193, color_primaries);
        return this;
    }

    @Field(194)
    public int color_trc() {
        return this.io.getIntField(this, 194);
    }

    @Field(194)
    public AVCodecContext53 color_trc(int color_trc) {
        this.io.setIntField(this, 194, color_trc);
        return this;
    }

    @Field(195)
    public int colorspace() {
        return this.io.getIntField(this, 195);
    }

    @Field(195)
    public AVCodecContext53 colorspace(int colorspace) {
        this.io.setIntField(this, 195, colorspace);
        return this;
    }

    @Field(196)
    public int color_range() {
        return this.io.getIntField(this, 196);
    }

    @Field(196)
    public AVCodecContext53 color_range(int color_range) {
        this.io.setIntField(this, 196, color_range);
        return this;
    }

    @Field(197)
    public int chroma_sample_location() {
        return this.io.getIntField(this, 197);
    }

    @Field(197)
    public AVCodecContext53 chroma_sample_location(int chroma_sample_location) {
        this.io.setIntField(this, 197, chroma_sample_location);
        return this;
    }
    
    @Field(198)
    public Pointer<AVCodecContext53.Execute2Callback> execute2() {
        return this.io.getPointerField(this, 198);
    }
    
    @Field(198)
    public AVCodecContext53 execute2(Pointer<AVCodecContext53.Execute2Callback> execute2) {
        this.io.setPointerField(this, 198, execute2);
        return this;
    }

    @Field(199)
    public int weighted_p_pred() {
        return this.io.getIntField(this, 199);
    }

    @Field(199)
    public AVCodecContext53 weighted_p_pred(int weighted_p_pred) {
        this.io.setIntField(this, 199, weighted_p_pred);
        return this;
    }

    @Field(200)
    public int aq_mode() {
        return this.io.getIntField(this, 200);
    }

    @Field(200)
    public AVCodecContext53 aq_mode(int aq_mode) {
        this.io.setIntField(this, 200, aq_mode);
        return this;
    }

    @Field(201)
    public float aq_strength() {
        return this.io.getFloatField(this, 201);
    }

    @Field(201)
    public AVCodecContext53 aq_strength(float aq_strength) {
        this.io.setFloatField(this, 201, aq_strength);
        return this;
    }

    @Field(202)
    public float psy_rd() {
        return this.io.getFloatField(this, 202);
    }

    @Field(202)
    public AVCodecContext53 psy_rd(float psy_rd) {
        this.io.setFloatField(this, 202, psy_rd);
        return this;
    }

    @Field(203)
    public float psy_trellis() {
        return this.io.getFloatField(this, 203);
    }

    @Field(203)
    public AVCodecContext53 psy_trellis(float psy_trellis) {
        this.io.setFloatField(this, 203, psy_trellis);
        return this;
    }

    @Field(204)
    public int rc_lookahead() {
        return this.io.getIntField(this, 204);
    }

    @Field(204)
    public AVCodecContext53 rc_lookahead(int rc_lookahead) {
        this.io.setIntField(this, 204, rc_lookahead);
        return this;
    }

    @Field(205)
    public float crf_max() {
        return this.io.getFloatField(this, 205);
    }

    @Field(205)
    public AVCodecContext53 crf_max(float crf_max) {
        this.io.setFloatField(this, 205, crf_max);
        return this;
    }

    @Field(206)
    public int log_level_offset() {
        return this.io.getIntField(this, 206);
    }

    @Field(206)
    public AVCodecContext53 log_level_offset(int log_level_offset) {
        this.io.setIntField(this, 206, log_level_offset);
        return this;
    }

    @Field(207)
    public int lpc_type() {
        return this.io.getIntField(this, 207);
    }

    @Field(207)
    public AVCodecContext53 lpc_type(int lpc_type) {
        this.io.setIntField(this, 207, lpc_type);
        return this;
    }

    @Field(208)
    public int lpc_passes() {
        return this.io.getIntField(this, 208);
    }

    @Field(208)
    public AVCodecContext53 lpc_passes(int lpc_passes) {
        this.io.setIntField(this, 208, lpc_passes);
        return this;
    }

    @Field(209)
    public int slices() {
        return this.io.getIntField(this, 209);
    }

    @Field(209)
    public AVCodecContext53 slices(int slices) {
        this.io.setIntField(this, 209, slices);
        return this;
    }
    
    @Field(210)
    public Pointer<Byte> subtitle_header() {
        return this.io.getPointerField(this, 210);
    }
    
    @Field(210)
    public AVCodecContext53 subtitle_header(Pointer<Byte> subtitle_header) {
        this.io.setPointerField(this, 210, subtitle_header);
        return this;
    }

    @Field(211)
    public int subtitle_header_size() {
        return this.io.getIntField(this, 211);
    }

    @Field(211)
    public AVCodecContext53 subtitle_header_size(int subtitle_header_size) {
        this.io.setIntField(this, 211, subtitle_header_size);
        return this;
    }
    
    @Field(212)
    public Pointer<?> pkt() {
        return this.io.getPointerField(this, 212);
    }
    
    @Field(212)
    public AVCodecContext53 pkt(Pointer<?> pkt) {
        this.io.setPointerField(this, 212, pkt);
        return this;
    }

    @Field(213)
    public int is_copy() {
        return this.io.getIntField(this, 213);
    }

    @Field(213)
    public AVCodecContext53 is_copy(int is_copy) {
        this.io.setIntField(this, 213, is_copy);
        return this;
    }

    @Field(214)
    public int thread_type() {
        return this.io.getIntField(this, 214);
    }

    @Field(214)
    public AVCodecContext53 thread_type(int thread_type) {
        this.io.setIntField(this, 214, thread_type);
        return this;
    }

    @Field(215)
    public int active_thread_type() {
        return this.io.getIntField(this, 215);
    }

    @Field(215)
    public AVCodecContext53 active_thread_type(int active_thread_type) {
        this.io.setIntField(this, 215, active_thread_type);
        return this;
    }

    @Field(216)
    public int thread_safe_callbacks() {
        return this.io.getIntField(this, 216);
    }

    @Field(216)
    public AVCodecContext53 thread_safe_callbacks(int thread_safe_callbacks) {
        this.io.setIntField(this, 216, thread_safe_callbacks);
        return this;
    }

    @Field(217)
    public long vbv_delay() {
        return this.io.getLongField(this, 217);
    }

    @Field(217)
    public AVCodecContext53 vbv_delay(long vbv_delay) {
        this.io.setLongField(this, 217, vbv_delay);
        return this;
    }

    @Field(218)
    public int audio_service_type() {
        return this.io.getIntField(this, 218);
    }

    @Field(218)
    public AVCodecContext53 audio_service_type(int audio_service_type) {
        this.io.setIntField(this, 218, audio_service_type);
        return this;
    }

    @Field(219)
    public int request_sample_fmt() {
        return this.io.getIntField(this, 219);
    }

    @Field(219)
    public AVCodecContext53 request_sample_fmt(int request_sample_fmt) {
        this.io.setIntField(this, 219, request_sample_fmt);
        return this;
    }

    public static abstract class DrawHorizontalBandCallback extends Callback<DrawHorizontalBandCallback> {
        public abstract void apply(Pointer<?> context, Pointer<?> frame, Pointer<Integer> offset, int y, int type, int height);
    }

    public static abstract class RtpCallback extends Callback<RtpCallback> {
        public abstract void apply(Pointer<?> context, Pointer<?> data, int size, int mb_nb);
    }

    public static abstract class BufferCallback extends Callback<BufferCallback> {
        public abstract int apply(Pointer<?> context, Pointer<?> frame);
    }

    public static abstract class GetFormatCallback extends Callback<GetFormatCallback> {
        public abstract int apply(Pointer<?> context, Pointer<Integer> fmt);
    }

    public static abstract class ExecuteFunctionArgumentCallback extends Callback<ExecuteFunctionArgumentCallback> {
        public abstract int apply(Pointer<?> context, Pointer<?> arg);
    }

    public static abstract class ExecuteCallback extends Callback<ExecuteCallback> {
        public abstract int apply(Pointer<?> context, Pointer<AVCodecContext53.ExecuteFunctionArgumentCallback> arg1, Pointer<?> arg2, Pointer<Integer> ret, int count, int size);
    }
    
    public static abstract class Execute2FunctionArgumentCallback extends Callback<Execute2FunctionArgumentCallback> {
        public abstract int apply(Pointer<?> context, Pointer<?> arg, int jobnr, int threadnr);
    }

    public static abstract class Execute2Callback extends Callback<Execute2Callback> {
        public abstract int apply(Pointer<?> context, Pointer<AVCodecContext53.Execute2FunctionArgumentCallback> arg1, Pointer<?> arg2, Pointer<Integer> ret, int count);
    }
    
}

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

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import org.libav.avutil.bridge.AVRational;

/**
 * Mirror of the native AVCodecContext struct for the libavcodec v54.x.x. For 
 * details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVCodecContext54 extends Structure {
    
    public static final int FF_IDCT_SIMPLENEON = 22;
    public static final int FF_DEBUG_VIS_MV_B_BACK = 0x00000004;
    public static final int FF_LEVEL_UNKNOWN = -99;
    public static final int FF_PROFILE_MPEG2_422 = 0;
    public static final int FF_IDCT_SIMPLEVIS = 18;
    public static final int FF_DTG_AFD_14_9 = 11;
    public static final int FF_PROFILE_H264_EXTENDED = 88;
    public static final int FF_IDCT_LIBMPEG2MMX = 4;
    public static final int FF_COMPLIANCE_NORMAL = 0;
    public static final int FF_PROFILE_MPEG2_SIMPLE = 5;
    public static final int FF_IDCT_XVIDMMX = 14;
    public static final int FF_IDCT_SH4 = 9;
    public static final int FF_IDCT_ALTIVEC = 8;
    public static final int AV_EF_BUFFER = 1 << 2;
    public static final int FF_BUG_STD_QPEL = 128;
    public static final int FF_PROFILE_DTS_ES = 30;
    public static final int FF_CMP_CHROMA = 256;
    public static final int FF_PROFILE_VC1_MAIN = 1;
    public static final int FF_IDCT_INT = 1;
    public static final int FF_PROFILE_MPEG2_SS = 2;
    public static final int FF_THREAD_FRAME = 1;
    public static final int FF_PROFILE_MPEG4_ADVANCED_REAL_TIME = 9;
    public static final int FF_DTG_AFD_SP_4_3 = 15;
    public static final int FF_DTG_AFD_16_9 = 10;
    public static final int AV_EF_BITSTREAM = 1 << 1;
    public static final int FF_DEBUG_BUFFERS = 0x00008000;
    public static final int FF_PROFILE_H264_MAIN = 77;
    public static final int FF_ASPECT_EXTENDED = 15;
    public static final int FF_DEBUG_SKIP = 0x00000080;
    public static final int FF_PROFILE_H264_CONSTRAINED_BASELINE = 66 | (1 << 9);
    public static final int FF_CMP_SATD = 2;
    public static final int FF_DEBUG_MMCO = 0x00000800;
    public static final int FF_PROFILE_MPEG4_SIMPLE_FACE_ANIMATION = 6;
    public static final int FF_DEBUG_BUGS = 0x00001000;
    public static final int FF_IDCT_AUTO = 0;
    public static final int FF_PROFILE_MPEG4_BASIC_ANIMATED_TEXTURE = 7;
    public static final int FF_PROFILE_MPEG4_SCALABLE_TEXTURE = 5;
    public static final int FF_IDCT_WMV2 = 19;
    public static final int FF_PROFILE_MPEG4_ADVANCED_SIMPLE = 15;
    public static final int FF_DTG_AFD_4_3_SP_14_9 = 13;
    public static final int FF_DCT_ALTIVEC = 5;
    public static final int FF_BUG_AMV = 32;
    public static final int AV_EF_EXPLODE = 1 << 3;
    public static final int FF_CMP_RD = 6;
    public static final int FF_BUG_AUTODETECT = 1;
    public static final int FF_PROFILE_MPEG2_SNR_SCALABLE = 3;
    public static final int FF_COMPLIANCE_STRICT = 1;
    public static final int FF_DEBUG_ER = 0x00000400;
    public static final int FF_CMP_BIT = 5;
    public static final int FF_BUG_AC_VLC = 0;
    public static final int FF_DEBUG_PTS = 0x00000200;
    public static final int FF_BUG_HPEL_CHROMA = 2048;
    public static final int FF_CODER_TYPE_RLE = 3;
    public static final int FF_DEBUG_MV = 32;
    public static final int FF_PROFILE_MPEG4_N_BIT = 4;
    public static final int FF_MB_DECISION_BITS = 1;
    public static final int FF_DTG_AFD_4_3 = 9;
    public static final int FF_DEBUG_VIS_QP = 0x00002000;
    public static final int FF_CODER_TYPE_VLC = 0;
    public static final int FF_DEBUG_BITSTREAM = 4;
    public static final int FF_PROFILE_DTS_96_24 = 40;
    public static final int FF_CMP_DCT264 = 14;
    public static final int FF_MB_DECISION_RD = 2;
    public static final int FF_BUG_NO_PADDING = 16;
    public static final int FF_DEFAULT_QUANT_BIAS = 999999;
    public static final int FF_EC_DEBLOCK = 2;
    public static final int FF_BUG_DC_CLIP = 4096;
    public static final int FF_PROFILE_VC1_COMPLEX = 2;
    public static final int FF_BUG_QPEL_CHROMA2 = 256;
    public static final int FF_PROFILE_MPEG2_MAIN = 4;
    public static final int FF_CMP_W97 = 12;
    public static final int FF_CMP_DCTMAX = 13;
    public static final int FF_IDCT_SIMPLEARMV6 = 17;
    public static final int FF_PROFILE_RESERVED = -100;
    public static final int FF_DEBUG_DCT_COEFF = 0x00000040;
    public static final int FF_PROFILE_AAC_LOW = 1;
    public static final int FF_COMPLIANCE_EXPERIMENTAL = -2;
    public static final int FF_IDCT_SIMPLE = 2;
    public static final int FF_PROFILE_DTS_HD_MA = 60;
    public static final int FF_THREAD_SLICE = 2;
    public static final int FF_PROFILE_UNKNOWN = -99;
    public static final int FF_DEBUG_VIS_MV_P_FOR = 0x00000001;
    public static final int FF_PROFILE_AAC_MAIN = 0;
    public static final int FF_CMP_VSAD = 8;
    public static final int FF_CMP_DCT = 3;
    public static final int FF_MB_DECISION_SIMPLE = 0;
    public static final int FF_IDCT_SIMPLEARM = 10;
    public static final int FF_PROFILE_MPEG4_SIMPLE = 0;
    public static final int FF_BUG_EDGE = 1024;
    public static final int FF_PROFILE_H264_BASELINE = 66;
    public static final int FF_IDCT_SIMPLEMMX = 3;
    public static final int FF_PROFILE_MPEG4_ADVANCED_SCALABLE_TEXTURE = 13;
    public static final int FF_IDCT_VP3 = 12;
    public static final int FF_PROFILE_MPEG4_CORE_SCALABLE = 10;
    public static final int FF_CODER_TYPE_AC = 1;
    public static final int FF_PROFILE_MPEG4_ADVANCED_CORE = 12;
    public static final int FF_PRED_PLANE = 1;
    public static final int FF_DTG_AFD_SAME = 8;
    public static final int FF_PROFILE_H264_CAVLC_444 = 44;
    public static final int FF_IDCT_MMI = 5;
    public static final int FF_CMP_SAD = 0;
    public static final int FF_PROFILE_MPEG4_CORE = 2;
    public static final int FF_BUG_TRUNCATED = 16384;
    public static final int FF_PROFILE_MPEG4_MAIN = 3;
    public static final int FF_PROFILE_AAC_SSR = 2;
    public static final int SLICE_FLAG_CODED_ORDER = 0x0001;
    public static final int FF_PROFILE_VC1_SIMPLE = 0;
    public static final int FF_IDCT_SIMPLEALPHA = 23;
    public static final int FF_DCT_FASTINT = 1;
    public static final int FF_DEBUG_VIS_MB_TYPE = 0x00004000;
    public static final int FF_BUG_MS = 8192;
    public static final int FF_PROFILE_VC1_ADVANCED = 3;
    public static final int FF_COMPLIANCE_VERY_STRICT = 2;
    public static final int FF_BUG_XVID_ILACE = 4;
    public static final int FF_IDCT_EA = 21;
    public static final int FF_IDCT_CAVS = 15;
    public static final int FF_PROFILE_MPEG4_SIMPLE_SCALABLE = 1;
    public static final int FF_PROFILE_H264_HIGH_10 = 110;
    public static final int FF_DTG_AFD_16_9_SP_14_9 = 14;
    public static final int FF_BUG_UMP4 = 8;
    public static final int FF_CMP_VSSE = 9;
    public static final int FF_CMP_ZERO = 7;
    public static final int FF_IDCT_SIMPLEARMV5TE = 16;
    public static final int FF_IDCT_BINK = 24;
    public static final int FF_IDCT_FAAN = 20;
    public static final int FF_PROFILE_H264_HIGH_10_INTRA = 110 | (1 << 11);
    public static final int FF_PRED_MEDIAN = 2;
    public static final int FF_PROFILE_H264_HIGH_444 = 144;
    public static final int FF_CODER_TYPE_RAW = 2;
    public static final int FF_PROFILE_H264_HIGH = 100;
    public static final int FF_CMP_NSSE = 10;
    public static final int SLICE_FLAG_ALLOW_FIELD = 0x0002;
    public static final int FF_PROFILE_H264_INTRA = 1 << 11;
    public static final int FF_PROFILE_H264_CONSTRAINED = 1 << 9;
    public static final int FF_DEBUG_THREADS = 0x00010000;
    public static final int FF_DEBUG_QP = 16;
    public static final int FF_PROFILE_MPEG4_ADVANCED_CODING = 11;
    public static final int FF_BUG_QPEL_CHROMA = 64;
    public static final int FF_BUG_DIRECT_BLOCKSIZE = 512;
    public static final int FF_COMPLIANCE_UNOFFICIAL = -1;
    public static final int FF_IDCT_ARM = 7;
    public static final int AV_EF_CRCCHECK = 1;
    public static final int SLICE_FLAG_ALLOW_PLANE = 0x0004;
    public static final int FF_PROFILE_DTS_HD_HRA = 50;
    public static final int FF_DEBUG_RC = 2;
    public static final int FF_DEBUG_VIS_MV_B_FOR = 0x00000002;
    public static final int FF_PRED_LEFT = 0;
    public static final int FF_IDCT_IPP = 13;
    public static final int FF_CMP_W53 = 11;
    public static final int FF_PROFILE_H264_HIGH_444_PREDICTIVE = 244;
    public static final int FF_CMP_SSE = 1;
    public static final int FF_EC_GUESS_MVS = 1;
    public static final int FF_DCT_MMX = 3;
    public static final int FF_BUG_OLD_MSMPEG4 = 2;
    public static final int FF_DEBUG_MB_TYPE = 8;
    public static final int FF_DEBUG_STARTCODE = 0x00000100;
    public static final int FF_DCT_FAAN = 6;
    public static final int FF_DCT_AUTO = 0;
    public static final int FF_CMP_PSNR = 4;
    public static final int FF_DEBUG_PICT_INFO = 1;
    public static final int FF_PROFILE_MPEG4_HYBRID = 8;
    public static final int FF_COMPRESSION_DEFAULT = -1;
    public static final int FF_IDCT_H264 = 11;
    public static final int FF_RC_STRATEGY_XVID = 1;
    public static final int FF_PROFILE_DTS = 20;
    public static final int FF_PROFILE_H264_HIGH_422_INTRA = 122 | (1 << 11);
    public static final int FF_PROFILE_MPEG2_HIGH = 1;
    public static final int FF_DCT_INT = 2;
    public static final int FF_PROFILE_H264_HIGH_444_INTRA = 244 | (1 << 11);
    public static final int FF_PROFILE_H264_HIGH_422 = 122;
    public static final int FF_CODER_TYPE_DEFLATE = 4;
    public static final int FF_PROFILE_AAC_LTP = 3;
    public static final int FF_PROFILE_MPEG4_SIMPLE_STUDIO = 14;
    
    public Pointer av_class;
    public int log_level_offset;
    public int codec_type;
    public Pointer codec;
    public byte[] codec_name = new byte[32];
    public int codec_id;
    public int codec_tag;
    public int stream_codec_tag;
    public int sub_id;
    public Pointer priv_data;
    public Pointer internal;
    public Pointer opaque;
    public int bit_rate;
    public int bit_rate_tolerance;
    public int global_quality;
    public int compression_level;
    public int flags;
    public int flags2;
    public Pointer extradata;
    public int extradata_size;
    public AVRational time_base;
    public int ticks_per_frame;
    public int delay;
    public int width;
    public int height;
    public int coded_width;
    public int coded_height;
    public int gop_size;
    public int pix_fmt;
    public int me_method;
    public Pointer draw_horiz_band;
    public Pointer get_format;
    public int max_b_frames;
    public float b_quant_factor;
    public int rc_strategy;
    public int b_frame_strategy;
    public int luma_elim_threshold;
    public int chroma_elim_threshold;
    public float b_quant_offset;
    public int has_b_frames;
    public int mpeg_quant;
    public float i_quant_factor;
    public float i_quant_offset;
    public float lumi_masking;
    public float temporal_cplx_masking;
    public float spatial_cplx_masking;
    public float p_masking;
    public float dark_masking;
    public int slice_count;
    public int prediction_method;
    public Pointer slice_offset;
    public AVRational sample_aspect_ratio;
    public int me_cmp;
    public int me_sub_cmp;
    public int mb_cmp;
    public int ildct_cmp;
    public int dia_size;
    public int last_predictor_count;
    public int pre_me;
    public int me_pre_cmp;
    public int pre_dia_size;
    public int me_subpel_quality;
    public int dtg_active_format;
    public int me_range;
    public int intra_quant_bias;
    public int inter_quant_bias;
    public int color_table_id;
    public int slice_flags;
    public int xvmc_acceleration;
    public int mb_decision;
    public Pointer intra_matrix;
    public Pointer inter_matrix;
    public int scenechange_threshold;
    public int noise_reduction;
    public int inter_threshold;
    public int quantizer_noise_shaping;
    public int me_threshold;
    public int mb_threshold;
    public int intra_dc_precision;
    public int skip_top;
    public int skip_bottom;
    public float border_masking;
    public int mb_lmin;
    public int mb_lmax;
    public int me_penalty_compensation;
    public int bidir_refine;
    public int brd_scale;
    public int keyint_min;
    public int refs;
    public int chromaoffset;
    public int scenechange_factor;
    public int mv0_threshold;
    public int b_sensitivity;
    public int color_primaries;
    public int color_trc;
    public int colorspace;
    public int color_range;
    public int chroma_sample_location;
    public int slices;
    public int field_order;
    public int sample_rate;
    public int channels;
    public int sample_fmt;
    public int frame_size;
    public int frame_number;
    public int block_align;
    public int cutoff;
    public int request_channels;
    public long channel_layout;
    public long request_channel_layout;
    public int audio_service_type;
    public int request_sample_fmt;
    public Pointer get_buffer;
    public Pointer release_buffer;
    public Pointer reget_buffer;
    public float qcompress;
    public float qblur;
    public int qmin;
    public int qmax;
    public int max_qdiff;
    public float rc_qsquish;
    public float rc_qmod_amp;
    public int rc_qmod_freq;
    public int rc_buffer_size;
    public int rc_override_count;
    public Pointer rc_override;
    public Pointer rc_eq;
    public int rc_max_rate;
    public int rc_min_rate;
    public float rc_buffer_aggressivity;
    public float rc_initial_cplx;
    public float rc_max_available_vbv_use;
    public float rc_min_vbv_overflow_use;
    public int rc_initial_buffer_occupancy;
    public int coder_type;
    public int context_model;
    public int lmin;
    public int lmax;
    public int frame_skip_threshold;
    public int frame_skip_factor;
    public int frame_skip_exp;
    public int frame_skip_cmp;
    public int trellis;
    public int min_prediction_order;
    public int max_prediction_order;
    public long timecode_frame_start;
    public Pointer rtp_callback;
    public int rtp_payload_size;
    public int mv_bits;
    public int header_bits;
    public int i_tex_bits;
    public int p_tex_bits;
    public int i_count;
    public int p_count;
    public int skip_count;
    public int misc_bits;
    public int frame_bits;
    public Pointer stats_out;
    public Pointer stats_in;
    public int workaround_bugs;
    public int strict_std_compliance;
    public int error_concealment;
    public int debug;
    public int debug_mv;
    public int err_recognition;
    public long reordered_opaque;
    public Pointer hwaccel;
    public Pointer hwaccel_context;
    public long[] error = new long[8];
    public int dct_algo;
    public int idct_algo;
    public int dsp_mask;
    public int bits_per_coded_sample;
    public int bits_per_raw_sample;
    public int lowres;
    public Pointer coded_frame;
    public int thread_count;
    public int thread_type;
    public int active_thread_type;
    public int thread_safe_callbacks;
    public Pointer execute;
    public Pointer execute2;
    public Pointer thread_opaque;
    public int nsse_weight;
    public int profile;
    public int level;
    public int skip_loop_filter;
    public int skip_idct;
    public int skip_frame;
    public Pointer subtitle_header;
    public int subtitle_header_size;
    public int error_rate;
    public Pointer pkt;
    public long vbv_delay;
    
    public AVCodecContext54() {
        super();
        initFieldOrder();
    }

    public AVCodecContext54(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new String[] {
            "av_class", 
            "log_level_offset", 
            "codec_type", 
            "codec", 
            "codec_name", 
            "codec_id", 
            "codec_tag", 
            "stream_codec_tag", 
            "sub_id", 
            "priv_data", 
            "internal", 
            "opaque", 
            "bit_rate", 
            "bit_rate_tolerance", 
            "global_quality", 
            "compression_level", 
            "flags", 
            "flags2", 
            "extradata", 
            "extradata_size", 
            "time_base", 
            "ticks_per_frame", 
            "delay", 
            "width", 
            "height", 
            "coded_width", 
            "coded_height", 
            "gop_size", 
            "pix_fmt", 
            "me_method", 
            "draw_horiz_band", 
            "get_format", 
            "max_b_frames", 
            "b_quant_factor", 
            "rc_strategy", 
            "b_frame_strategy", 
            "luma_elim_threshold", 
            "chroma_elim_threshold", 
            "b_quant_offset", 
            "has_b_frames", 
            "mpeg_quant", 
            "i_quant_factor", 
            "i_quant_offset", 
            "lumi_masking", 
            "temporal_cplx_masking", 
            "spatial_cplx_masking", 
            "p_masking", 
            "dark_masking", 
            "slice_count", 
            "prediction_method", 
            "slice_offset", 
            "sample_aspect_ratio", 
            "me_cmp", 
            "me_sub_cmp", 
            "mb_cmp", 
            "ildct_cmp", 
            "dia_size", 
            "last_predictor_count", 
            "pre_me", 
            "me_pre_cmp",
            "pre_dia_size", 
            "me_subpel_quality", 
            "dtg_active_format", 
            "me_range", 
            "intra_quant_bias", 
            "inter_quant_bias", 
            "color_table_id", 
            "slice_flags", 
            "xvmc_acceleration", 
            "mb_decision", 
            "intra_matrix", 
            "inter_matrix", 
            "scenechange_threshold", 
            "noise_reduction", 
            "inter_threshold", 
            "quantizer_noise_shaping", 
            "me_threshold", 
            "mb_threshold", 
            "intra_dc_precision", 
            "skip_top", 
            "skip_bottom", 
            "border_masking", "mb_lmin", 
            "mb_lmax", 
            "me_penalty_compensation", 
            "bidir_refine", 
            "brd_scale", 
            "keyint_min", 
            "refs", 
            "chromaoffset", 
            "scenechange_factor", 
            "mv0_threshold", 
            "b_sensitivity", 
            "color_primaries", 
            "color_trc", 
            "colorspace", 
            "color_range", 
            "chroma_sample_location", 
            "slices", 
            "field_order", 
            "sample_rate", 
            "channels", 
            "sample_fmt", 
            "frame_size", 
            "frame_number", 
            "block_align", 
            "cutoff", 
            "request_channels", 
            "channel_layout", 
            "request_channel_layout", 
            "audio_service_type", 
            "request_sample_fmt", 
            "get_buffer", 
            "release_buffer", 
            "reget_buffer", 
            "qcompress", 
            "qblur", 
            "qmin", 
            "qmax", 
            "max_qdiff", 
            "rc_qsquish", 
            "rc_qmod_amp", 
            "rc_qmod_freq", 
            "rc_buffer_size", 
            "rc_override_count", 
            "rc_override", 
            "rc_eq", 
            "rc_max_rate", 
            "rc_min_rate", 
            "rc_buffer_aggressivity", 
            "rc_initial_cplx", 
            "rc_max_available_vbv_use", 
            "rc_min_vbv_overflow_use", 
            "rc_initial_buffer_occupancy", 
            "coder_type", 
            "context_model", 
            "lmin", 
            "lmax", 
            "frame_skip_threshold", 
            "frame_skip_factor", 
            "frame_skip_exp", 
            "frame_skip_cmp", 
            "trellis", 
            "min_prediction_order", 
            "max_prediction_order", 
            "timecode_frame_start", 
            "rtp_callback", 
            "rtp_payload_size", 
            "mv_bits", 
            "header_bits", 
            "i_tex_bits", 
            "p_tex_bits", 
            "i_count", 
            "p_count", 
            "skip_count", 
            "misc_bits", 
            "frame_bits", 
            "stats_out", 
            "stats_in", 
            "workaround_bugs", 
            "strict_std_compliance", 
            "error_concealment", 
            "debug", "debug_mv", 
            "err_recognition", 
            "reordered_opaque", 
            "hwaccel", 
            "hwaccel_context", 
            "error", 
            "dct_algo", 
            "idct_algo", 
            "dsp_mask", 
            "bits_per_coded_sample", 
            "bits_per_raw_sample", 
            "lowres", 
            "coded_frame", 
            "thread_count", 
            "thread_type", 
            "active_thread_type", 
            "thread_safe_callbacks", 
            "execute", 
            "execute2", 
            "thread_opaque", 
            "nsse_weight", 
            "profile", 
            "level", 
            "skip_loop_filter", 
            "skip_idct", 
            "skip_frame", 
            "subtitle_header", 
            "subtitle_header_size", 
            "error_rate", 
            "pkt", 
            "vbv_delay",
        });
    }
    
    public interface DrawHorizontalBandCallback extends Callback {
        void apply(Pointer s, Pointer src, int[] offset, int y, int type, int height);
    }
    
    public interface RtpCallback extends Callback {
        void apply(Pointer avctx, Pointer data, int size, int mb_nb);
    }
    
    public interface GetFormatCallback extends Callback {
        int apply(Pointer s, IntByReference fmt);
    }
    
    public interface BufferCallback extends Callback {
        int apply(Pointer c, AVFrame53 pic);
    }
    
    public interface ExecuteFunctionArgumentCallback extends Callback {
        int apply(Pointer c2, Pointer arg);
    }
    
    public interface ExecuteCallback extends Callback {
        int apply(Pointer c, ExecuteFunctionArgumentCallback arg1, Pointer arg2, IntByReference ret, int count, int size);
    }
    
    public interface Execute2FunctionArgumentCallback extends Callback {
        int apply(Pointer c2, Pointer arg, int jobnr, int threadnr);
    }
    
    public interface Execute2Callback extends Callback {
        int apply(Pointer c, Execute2FunctionArgumentCallback arg1, Pointer arg2, IntByReference ret, int count);
    }
    
    public static class ByReference extends AVCodecContext54 implements Structure.ByReference { }
    public static class ByValue extends AVCodecContext54 implements Structure.ByValue { }
    
}

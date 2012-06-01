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

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import javax.security.auth.callback.Callback;
import org.libav.avutil.bridge.AVRational;

/**
 * Mirror of the native AVCodecContext struct for the libavcodec v53.x.x. For 
 * details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVCodecContext53 extends Structure {

    public Pointer av_class;
    public int bit_rate;
    public int bit_rate_tolerance;
    public int flags;
    public int sub_id;
    public int me_method;
    public Pointer extradata;
    public int extradata_size;
    public AVRational time_base;
    public int width;
    public int height;
    public int gop_size;
    /**
     * @see PixelFormat
     */
    public int pix_fmt;
    public Pointer draw_horiz_band;
    public int sample_rate;
    public int channels;
    /**
     * @see AVSampleFormat
     */
    public int sample_fmt;
    public int frame_size;
    public int frame_number;
    public int delay;
    public float qcompress;
    public float qblur;
    public int qmin;
    public int qmax;
    public int max_qdiff;
    public int max_b_frames;
    public float b_quant_factor;
    public int rc_strategy;
    public int b_frame_strategy;
    public Pointer codec;
    public Pointer priv_data;
    public int rtp_payload_size;
    public Pointer rtp_callback;
    public int mv_bits;
    public int header_bits;
    public int i_tex_bits;
    public int p_tex_bits;
    public int i_count;
    public int p_count;
    public int skip_count;
    public int misc_bits;
    public int frame_bits;
    public Pointer opaque;
    public byte[] codec_name = new byte[32];
    /**
     * @see AVMediaType
     */
    public int codec_type;
    /**
     * @see CodecID
     */
    public int codec_id;
    public int codec_tag;
    public int workaround_bugs;
    public int luma_elim_threshold;
    public int chroma_elim_threshold;
    public int strict_std_compliance;
    public float b_quant_offset;
    public int error_recognition;
    public Pointer get_buffer;
    public Pointer release_buffer;
    public int has_b_frames;
    public int block_align;
    public int parse_only;
    public int mpeg_quant;
    public Pointer stats_out;
    public Pointer stats_in;
    public float rc_qsquish;
    public float rc_qmod_amp;
    public int rc_qmod_freq;
    public Pointer rc_override;
    public int rc_override_count;
    public Pointer rc_eq;
    public int rc_max_rate;
    public int rc_min_rate;
    public int rc_buffer_size;
    public float rc_buffer_aggressivity;
    public float i_quant_factor;
    public float i_quant_offset;
    public float rc_initial_cplx;
    public int dct_algo;
    public float lumi_masking;
    public float temporal_cplx_masking;
    public float spatial_cplx_masking;
    public float p_masking;
    public float dark_masking;
    public int idct_algo;
    public int slice_count;
    public Pointer slice_offset;
    public int error_concealment;
    public int dsp_mask;
    public int bits_per_coded_sample;
    public int prediction_method;
    public AVRational sample_aspect_ratio;
    public Pointer coded_frame;
    public int debug;
    public int debug_mv;
    public long[] error = new long[4];
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
    public Pointer get_format;
    public int dtg_active_format;
    public int me_range;
    public int intra_quant_bias;
    public int inter_quant_bias;
    public int color_table_id;
    public int internal_buffer_count;
    public Pointer internal_buffer;
    public int global_quality;
    public int coder_type;
    public int context_model;
    public int slice_flags;
    public int xvmc_acceleration;
    public int mb_decision;
    public Pointer intra_matrix;
    public Pointer inter_matrix;
    public int stream_codec_tag;
    public int scenechange_threshold;
    public int lmin;
    public int lmax;
    public int noise_reduction;
    public Pointer reget_buffer;
    public int rc_initial_buffer_occupancy;
    public int inter_threshold;
    public int flags2;
    public int error_rate;
    public int quantizer_noise_shaping;
    public int thread_count;
    public Pointer execute;
    public Pointer thread_opaque;
    public int me_threshold;
    public int mb_threshold;
    public int intra_dc_precision;
    public int nsse_weight;
    public int skip_top;
    public int skip_bottom;
    public int profile;
    public int level;
    public int lowres;
    public int coded_width;
    public int coded_height;
    public int frame_skip_threshold;
    public int frame_skip_factor;
    public int frame_skip_exp;
    public int frame_skip_cmp;
    public float border_masking;
    public int mb_lmin;
    public int mb_lmax;
    public int me_penalty_compensation;
    /**
     * @see AVDiscard
     */
    public int skip_loop_filter;
    /**
     * @see AVDiscard
     */
    public int skip_idct;
    /**
     * @see AVDiscard
     */
    public int skip_frame;
    public int bidir_refine;
    public int brd_scale;
    public int keyint_min;
    public int refs;
    public int chromaoffset;
    public int trellis;
    public int cutoff;
    public int scenechange_factor;
    public int mv0_threshold;
    public int b_sensitivity;
    public int compression_level;
    public int min_prediction_order;
    public int max_prediction_order;
    public long timecode_frame_start;
    public long reordered_opaque;
    public int bits_per_raw_sample;
    public long channel_layout;
    public long request_channel_layout;
    public float rc_max_available_vbv_use;
    public float rc_min_vbv_overflow_use;
    public Pointer hwaccel;
    public int ticks_per_frame;
    public Pointer hwaccel_context;
    /**
     * @see AVColorPrimaries
     */
    public int color_primaries;
    /**
     * @see AVColorTransferCharacteristic
     */
    public int color_trc;
    /**
     * @see AVColorSpace
     */
    public int colorspace;
    /**
     * @see AVColorRange
     */
    public int color_range;
    /**
     * @see AVChromaLocation
     */
    public int chroma_sample_location;
    public Pointer execute2;
    public int log_level_offset;
    public int slices;
    public Pointer subtitle_header;
    public int subtitle_header_size;
    public Pointer pkt;
    public int is_copy;
    public int thread_type;
    public int active_thread_type;
    public int thread_safe_callbacks;
    public long vbv_delay;
    /**
     * @see AVAudioServiceType
     */
    public int audio_service_type;
    /**
     * @see AVSampleFormat
     */
    public int request_sample_fmt;

    public AVCodecContext53() {
        super();
        initFieldOrder();
    }

    public AVCodecContext53(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"av_class", "bit_rate", "bit_rate_tolerance", "flags", "sub_id", "me_method", "extradata", "extradata_size", "time_base", "width", "height", "gop_size", "pix_fmt", "draw_horiz_band", "sample_rate", "channels", "sample_fmt", "frame_size", "frame_number", "delay", "qcompress", "qblur", "qmin", "qmax", "max_qdiff", "max_b_frames", "b_quant_factor", "rc_strategy", "b_frame_strategy", "codec", "priv_data", "rtp_payload_size", "rtp_callback", "mv_bits", "header_bits", "i_tex_bits", "p_tex_bits", "i_count", "p_count", "skip_count", "misc_bits", "frame_bits", "opaque", "codec_name", "codec_type", "codec_id", "codec_tag", "workaround_bugs", "luma_elim_threshold", "chroma_elim_threshold", "strict_std_compliance", "b_quant_offset", "error_recognition", "get_buffer", "release_buffer", "has_b_frames", "block_align", "parse_only", "mpeg_quant", "stats_out", "stats_in", "rc_qsquish", "rc_qmod_amp", "rc_qmod_freq", "rc_override", "rc_override_count", "rc_eq", "rc_max_rate", "rc_min_rate", "rc_buffer_size", "rc_buffer_aggressivity", "i_quant_factor", "i_quant_offset", "rc_initial_cplx", "dct_algo", "lumi_masking", "temporal_cplx_masking", "spatial_cplx_masking", "p_masking", "dark_masking", "idct_algo", "slice_count", "slice_offset", "error_concealment", "dsp_mask", "bits_per_coded_sample", "prediction_method", "sample_aspect_ratio", "coded_frame", "debug", "debug_mv", "error", "me_cmp", "me_sub_cmp", "mb_cmp", "ildct_cmp", "dia_size", "last_predictor_count", "pre_me", "me_pre_cmp", "pre_dia_size", "me_subpel_quality", "get_format", "dtg_active_format", "me_range", "intra_quant_bias", "inter_quant_bias", "color_table_id", "internal_buffer_count", "internal_buffer", "global_quality", "coder_type", "context_model", "slice_flags", "xvmc_acceleration", "mb_decision", "intra_matrix", "inter_matrix", "stream_codec_tag", "scenechange_threshold", "lmin", "lmax", "noise_reduction", "reget_buffer", "rc_initial_buffer_occupancy", "inter_threshold", "flags2", "error_rate", "quantizer_noise_shaping", "thread_count", "execute", "thread_opaque", "me_threshold", "mb_threshold", "intra_dc_precision", "nsse_weight", "skip_top", "skip_bottom", "profile", "level", "lowres", "coded_width", "coded_height", "frame_skip_threshold", "frame_skip_factor", "frame_skip_exp", "frame_skip_cmp", "border_masking", "mb_lmin", "mb_lmax", "me_penalty_compensation", "skip_loop_filter", "skip_idct", "skip_frame", "bidir_refine", "brd_scale", "keyint_min", "refs", "chromaoffset", "trellis", "cutoff", "scenechange_factor", "mv0_threshold", "b_sensitivity", "compression_level", "min_prediction_order", "max_prediction_order", "timecode_frame_start", "reordered_opaque", "bits_per_raw_sample", "channel_layout", "request_channel_layout", "rc_max_available_vbv_use", "rc_min_vbv_overflow_use", "hwaccel", "ticks_per_frame", "hwaccel_context", "color_primaries", "color_trc", "colorspace", "color_range", "chroma_sample_location", "execute2", "log_level_offset", "slices", "subtitle_header", "subtitle_header_size", "pkt", "is_copy", "thread_type", "active_thread_type", "thread_safe_callbacks", "vbv_delay", "audio_service_type", "request_sample_fmt"});
    }

    public interface DrawHorizontalBandCallback extends Callback {
        void invoke(Pointer context, Pointer frame, int[] offset, int y, int type, int height);
    }
    
    public interface RtpCallback extends Callback {
        void invoke(Pointer context, Pointer data, int size, int mb_nb);
    }
    
    public interface BufferCallback extends Callback {
        int invoke(Pointer context, Pointer frame);
    }
    
    public interface GetFormatCallback extends Callback {
        /**
         * @see PixelFormat
         */
        int invoke(Pointer context, IntByReference fmt);
    }
    
    public interface ExecuteFunctionArgumentCallback extends Callback {
        int invoke(Pointer context, Pointer arg);
    }
    
    public interface ExecuteCallback extends Callback {
        int invoke(Pointer context, ExecuteFunctionArgumentCallback arg1, Pointer arg2, IntByReference ret, int count, int size);
    }
    
    public interface Execute2FunctionArgumentCallback extends Callback {
        int invoke(Pointer context, Pointer arg, int jobnr, int threadnr);
    }
    
    public interface Execute2Callback extends Callback {
        int invoke(Pointer context, Execute2FunctionArgumentCallback arg1, Pointer arg2, IntByReference ret, int count);
    }

    public static class ByReference extends AVCodecContext53 implements Structure.ByReference { };
    public static class ByValue extends AVCodecContext53 implements Structure.ByValue { };
}

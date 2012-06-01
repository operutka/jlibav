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
package org.libav.avcodec;

import com.sun.jna.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.bridge.*;
import org.libav.bridge.LibavLibraryWrapper;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for codec wrappers.
 * 
 * @author Ondrej Perutka
 */
public class CodecWrapperFactory {
    
    private static final LibavLibraryWrapper<IAVCodecLibrary> libWrapper;
    private static final CodecWrapperFactory instance;
    
    public static final int CODEC_ID_NONE;
    
    /// video codecs
    public static final int CODEC_ID_MPEG1VIDEO;
    public static final int CODEC_ID_MPEG2VIDEO;
    public static final int CODEC_ID_MPEG2VIDEO_XVMC;
    public static final int CODEC_ID_H261;
    public static final int CODEC_ID_H263;
    public static final int CODEC_ID_RV10;
    public static final int CODEC_ID_RV20;
    public static final int CODEC_ID_MJPEG;
    public static final int CODEC_ID_MJPEGB;
    public static final int CODEC_ID_LJPEG;
    public static final int CODEC_ID_SP5X;
    public static final int CODEC_ID_JPEGLS;
    public static final int CODEC_ID_MPEG4;
    public static final int CODEC_ID_RAWVIDEO;
    public static final int CODEC_ID_MSMPEG4V1;
    public static final int CODEC_ID_MSMPEG4V2;
    public static final int CODEC_ID_MSMPEG4V3;
    public static final int CODEC_ID_WMV1;
    public static final int CODEC_ID_WMV2;
    public static final int CODEC_ID_H263P;
    public static final int CODEC_ID_H263I;
    public static final int CODEC_ID_FLV1;
    public static final int CODEC_ID_SVQ1;
    public static final int CODEC_ID_SVQ3;
    public static final int CODEC_ID_DVVIDEO;
    public static final int CODEC_ID_HUFFYUV;
    public static final int CODEC_ID_CYUV;
    public static final int CODEC_ID_H264;
    public static final int CODEC_ID_INDEO3;
    public static final int CODEC_ID_VP3;
    public static final int CODEC_ID_THEORA;
    public static final int CODEC_ID_ASV1;
    public static final int CODEC_ID_ASV2;
    public static final int CODEC_ID_FFV1;
    public static final int CODEC_ID_4XM;
    public static final int CODEC_ID_VCR1;
    public static final int CODEC_ID_CLJR;
    public static final int CODEC_ID_MDEC;
    public static final int CODEC_ID_ROQ;
    public static final int CODEC_ID_INTERPLAY_VIDEO;
    public static final int CODEC_ID_XAN_WC3;
    public static final int CODEC_ID_XAN_WC4;
    public static final int CODEC_ID_RPZA;
    public static final int CODEC_ID_CINEPAK;
    public static final int CODEC_ID_WS_VQA;
    public static final int CODEC_ID_MSRLE;
    public static final int CODEC_ID_MSVIDEO1;
    public static final int CODEC_ID_IDCIN;
    public static final int CODEC_ID_8BPS;
    public static final int CODEC_ID_SMC;
    public static final int CODEC_ID_FLIC;
    public static final int CODEC_ID_TRUEMOTION1;
    public static final int CODEC_ID_VMDVIDEO;
    public static final int CODEC_ID_MSZH;
    public static final int CODEC_ID_ZLIB;
    public static final int CODEC_ID_QTRLE;
    public static final int CODEC_ID_SNOW;
    public static final int CODEC_ID_TSCC;
    public static final int CODEC_ID_ULTI;
    public static final int CODEC_ID_QDRAW;
    public static final int CODEC_ID_VIXL;
    public static final int CODEC_ID_QPEG;
    public static final int CODEC_ID_PNG;
    public static final int CODEC_ID_PPM;
    public static final int CODEC_ID_PBM;
    public static final int CODEC_ID_PGM;
    public static final int CODEC_ID_PGMYUV;
    public static final int CODEC_ID_PAM;
    public static final int CODEC_ID_FFVHUFF;
    public static final int CODEC_ID_RV30;
    public static final int CODEC_ID_RV40;
    public static final int CODEC_ID_VC1;
    public static final int CODEC_ID_WMV3;
    public static final int CODEC_ID_LOCO;
    public static final int CODEC_ID_WNV1;
    public static final int CODEC_ID_AASC;
    public static final int CODEC_ID_INDEO2;
    public static final int CODEC_ID_FRAPS;
    public static final int CODEC_ID_TRUEMOTION2;
    public static final int CODEC_ID_BMP;
    public static final int CODEC_ID_CSCD;
    public static final int CODEC_ID_MMVIDEO;
    public static final int CODEC_ID_ZMBV;
    public static final int CODEC_ID_AVS;
    public static final int CODEC_ID_SMACKVIDEO;
    public static final int CODEC_ID_NUV;
    public static final int CODEC_ID_KMVC;
    public static final int CODEC_ID_FLASHSV;
    public static final int CODEC_ID_CAVS;
    public static final int CODEC_ID_JPEG2000;
    public static final int CODEC_ID_VMNC;
    public static final int CODEC_ID_VP5;
    public static final int CODEC_ID_VP6;
    public static final int CODEC_ID_VP6F;
    public static final int CODEC_ID_TARGA;
    public static final int CODEC_ID_DSICINVIDEO;
    public static final int CODEC_ID_TIERTEXSEQVIDEO;
    public static final int CODEC_ID_TIFF;
    public static final int CODEC_ID_GIF;
    public static final int CODEC_ID_FFH264;
    public static final int CODEC_ID_DXA;
    public static final int CODEC_ID_DNXHD;
    public static final int CODEC_ID_THP;
    public static final int CODEC_ID_SGI;
    public static final int CODEC_ID_C93;
    public static final int CODEC_ID_BETHSOFTVID;
    public static final int CODEC_ID_PTX;
    public static final int CODEC_ID_TXD;
    public static final int CODEC_ID_VP6A;
    public static final int CODEC_ID_AMV;
    public static final int CODEC_ID_VB;
    public static final int CODEC_ID_PCX;
    public static final int CODEC_ID_SUNRAST;
    public static final int CODEC_ID_INDEO4;
    public static final int CODEC_ID_INDEO5;
    public static final int CODEC_ID_MIMIC;
    public static final int CODEC_ID_RL2;
    public static final int CODEC_ID_ESCAPE124;
    public static final int CODEC_ID_DIRAC;
    public static final int CODEC_ID_BFI;
    public static final int CODEC_ID_CMV;
    public static final int CODEC_ID_MOTIONPIXELS;
    public static final int CODEC_ID_TGV;
    public static final int CODEC_ID_TGQ;
    public static final int CODEC_ID_TQI;
    public static final int CODEC_ID_AURA;
    public static final int CODEC_ID_AURA2;
    public static final int CODEC_ID_V210X;
    public static final int CODEC_ID_TMV;
    public static final int CODEC_ID_V210;
    public static final int CODEC_ID_DPX;
    public static final int CODEC_ID_MAD;
    public static final int CODEC_ID_FRWU;
    public static final int CODEC_ID_FLASHSV2;
    public static final int CODEC_ID_CDGRAPHICS;
    public static final int CODEC_ID_R210;
    public static final int CODEC_ID_ANM;
    public static final int CODEC_ID_BINKVIDEO;
    public static final int CODEC_ID_IFF_ILBM;
    public static final int CODEC_ID_IFF_BYTERUN1;
    public static final int CODEC_ID_KGV1;
    public static final int CODEC_ID_YOP;
    public static final int CODEC_ID_VP8;
    public static final int CODEC_ID_PICTOR;
    public static final int CODEC_ID_ANSI;
    public static final int CODEC_ID_A64_MULTI;
    public static final int CODEC_ID_A64_MULTI5;
    public static final int CODEC_ID_R10K;
    public static final int CODEC_ID_MXPEG;
    public static final int CODEC_ID_LAGARITH;
    public static final int CODEC_ID_PRORES;
    public static final int CODEC_ID_JV;
    public static final int CODEC_ID_DFA;
    public static final int CODEC_ID_WMV3IMAGE;
    public static final int CODEC_ID_VC1IMAGE;
    public static final int CODEC_ID_UTVIDEO;
    public static final int CODEC_ID_BMV_VIDEO;
    public static final int CODEC_ID_VBLE;
    public static final int CODEC_ID_DXTORY;
    public static final int CODEC_ID_V410;
    public static final int CODEC_ID_XWD;
    public static final int CODEC_ID_CDXL;
    
    /// various PCM "codecs"
    public static final int CODEC_ID_FIRST_AUDIO;
    public static final int CODEC_ID_PCM_S16LE;
    public static final int CODEC_ID_PCM_S16BE;
    public static final int CODEC_ID_PCM_U16LE;
    public static final int CODEC_ID_PCM_U16BE;
    public static final int CODEC_ID_PCM_S8;
    public static final int CODEC_ID_PCM_U8;
    public static final int CODEC_ID_PCM_MULAW;
    public static final int CODEC_ID_PCM_ALAW;
    public static final int CODEC_ID_PCM_S32LE;
    public static final int CODEC_ID_PCM_S32BE;
    public static final int CODEC_ID_PCM_U32LE;
    public static final int CODEC_ID_PCM_U32BE;
    public static final int CODEC_ID_PCM_S24LE;
    public static final int CODEC_ID_PCM_S24BE;
    public static final int CODEC_ID_PCM_U24LE;
    public static final int CODEC_ID_PCM_U24BE;
    public static final int CODEC_ID_PCM_S24DAUD;
    public static final int CODEC_ID_PCM_ZORK;
    public static final int CODEC_ID_PCM_S16LE_PLANAR;
    public static final int CODEC_ID_PCM_DVD;
    public static final int CODEC_ID_PCM_F32BE;
    public static final int CODEC_ID_PCM_F32LE;
    public static final int CODEC_ID_PCM_F64BE;
    public static final int CODEC_ID_PCM_F64LE;
    public static final int CODEC_ID_PCM_BLURAY;
    public static final int CODEC_ID_PCM_LXF;
    public static final int CODEC_ID_S302M;
    public static final int CODEC_ID_PCM_S8_PLANAR;
    
    /// various ADPCM codecs
    public static final int CODEC_ID_ADPCM_IMA_QT;
    public static final int CODEC_ID_ADPCM_IMA_WAV;
    public static final int CODEC_ID_ADPCM_IMA_DK3;
    public static final int CODEC_ID_ADPCM_IMA_DK4;
    public static final int CODEC_ID_ADPCM_IMA_WS;
    public static final int CODEC_ID_ADPCM_IMA_SMJPEG;
    public static final int CODEC_ID_ADPCM_MS;
    public static final int CODEC_ID_ADPCM_4XM;
    public static final int CODEC_ID_ADPCM_XA;
    public static final int CODEC_ID_ADPCM_ADX;
    public static final int CODEC_ID_ADPCM_EA;
    public static final int CODEC_ID_ADPCM_G726;
    public static final int CODEC_ID_ADPCM_CT;
    public static final int CODEC_ID_ADPCM_SWF;
    public static final int CODEC_ID_ADPCM_YAMAHA;
    public static final int CODEC_ID_ADPCM_SBPRO_4;
    public static final int CODEC_ID_ADPCM_SBPRO_3;
    public static final int CODEC_ID_ADPCM_SBPRO_2;
    public static final int CODEC_ID_ADPCM_THP;
    public static final int CODEC_ID_ADPCM_IMA_AMV;
    public static final int CODEC_ID_ADPCM_EA_R1;
    public static final int CODEC_ID_ADPCM_EA_R3;
    public static final int CODEC_ID_ADPCM_EA_R2;
    public static final int CODEC_ID_ADPCM_IMA_EA_SEAD;
    public static final int CODEC_ID_ADPCM_IMA_EA_EACS;
    public static final int CODEC_ID_ADPCM_EA_XAS;
    public static final int CODEC_ID_ADPCM_EA_MAXIS_XA;
    public static final int CODEC_ID_ADPCM_IMA_ISS;
    public static final int CODEC_ID_ADPCM_G722;
    public static final int CODEC_ID_ADPCM_IMA_APC;
    
    /// AMR
    public static final int CODEC_ID_AMR_NB;
    public static final int CODEC_ID_AMR_WB;
    
    /// RealAudio codecs
    public static final int CODEC_ID_RA_144;
    public static final int CODEC_ID_RA_288;
    
    /// various DPCM codecs
    public static final int CODEC_ID_ROQ_DPCM;
    public static final int CODEC_ID_INTERPLAY_DPCM;
    public static final int CODEC_ID_XAN_DPCM;
    public static final int CODEC_ID_SOL_DPCM;
    
    /// audio codecs
    public static final int CODEC_ID_MP2;
    public static final int CODEC_ID_MP3;
    public static final int CODEC_ID_AAC;
    public static final int CODEC_ID_AC3;
    public static final int CODEC_ID_DTS;
    public static final int CODEC_ID_VORBIS;
    public static final int CODEC_ID_DVAUDIO;
    public static final int CODEC_ID_WMAV1;
    public static final int CODEC_ID_WMAV2;
    public static final int CODEC_ID_MACE3;
    public static final int CODEC_ID_MACE6;
    public static final int CODEC_ID_VMDAUDIO;
    public static final int CODEC_ID_SONIC;
    public static final int CODEC_ID_SONIC_LS;
    public static final int CODEC_ID_FLAC;
    public static final int CODEC_ID_MP3ADU;
    public static final int CODEC_ID_MP3ON4;
    public static final int CODEC_ID_SHORTEN;
    public static final int CODEC_ID_ALAC;
    public static final int CODEC_ID_WESTWOOD_SND1;
    public static final int CODEC_ID_GSM;
    public static final int CODEC_ID_QDM2;
    public static final int CODEC_ID_COOK;
    public static final int CODEC_ID_TRUESPEECH;
    public static final int CODEC_ID_TTA;
    public static final int CODEC_ID_SMACKAUDIO;
    public static final int CODEC_ID_QCELP;
    public static final int CODEC_ID_WAVPACK;
    public static final int CODEC_ID_DSICINAUDIO;
    public static final int CODEC_ID_IMC;
    public static final int CODEC_ID_MUSEPACK7;
    public static final int CODEC_ID_MLP;
    public static final int CODEC_ID_GSM_MS;
    public static final int CODEC_ID_ATRAC3;
    public static final int CODEC_ID_VOXWARE;
    public static final int CODEC_ID_APE;
    public static final int CODEC_ID_NELLYMOSER;
    public static final int CODEC_ID_MUSEPACK8;
    public static final int CODEC_ID_SPEEX;
    public static final int CODEC_ID_WMAVOICE;
    public static final int CODEC_ID_WMAPRO;
    public static final int CODEC_ID_WMALOSSLESS;
    public static final int CODEC_ID_ATRAC3P;
    public static final int CODEC_ID_EAC3;
    public static final int CODEC_ID_SIPR;
    public static final int CODEC_ID_MP1;
    public static final int CODEC_ID_TWINVQ;
    public static final int CODEC_ID_TRUEHD;
    public static final int CODEC_ID_MP4ALS;
    public static final int CODEC_ID_ATRAC1;
    public static final int CODEC_ID_BINKAUDIO_RDFT;
    public static final int CODEC_ID_BINKAUDIO_DCT;
    public static final int CODEC_ID_AAC_LATM;
    public static final int CODEC_ID_QDMC;
    public static final int CODEC_ID_CELT;
    public static final int CODEC_ID_G723_1;
    public static final int CODEC_ID_G729;
    public static final int CODEC_ID_8SVX_EXP;
    public static final int CODEC_ID_8SVX_FIB;
    public static final int CODEC_ID_BMV_AUDIO;
    
    /// subtitle codecs
    public static final int CODEC_ID_FIRST_SUBTITLE;
    public static final int CODEC_ID_DVD_SUBTITLE;
    public static final int CODEC_ID_DVB_SUBTITLE;
    public static final int CODEC_ID_TEXT;
    public static final int CODEC_ID_XSUB;
    public static final int CODEC_ID_SSA;
    public static final int CODEC_ID_MOV_TEXT;
    public static final int CODEC_ID_HDMV_PGS_SUBTITLE;
    public static final int CODEC_ID_DVB_TELETEXT;
    public static final int CODEC_ID_SRT;
    
    /// other specific kind of codecs (generally used for attachments)
    public static final int CODEC_ID_FIRST_UNKNOWN;
    public static final int CODEC_ID_TTF;
    public static final int CODEC_ID_PROBE;
    
    /// stream (only used by libavformat)
    public static final int CODEC_ID_MPEG2TS;
    
    // stream (only used by libavformat)
    public static final int CODEC_ID_MPEG4SYSTEMS;
    public static final int CODEC_ID_FFMETADATA;
    
    static {
        libWrapper = LibraryManager.getInstance().getAVCodecLibraryWrapper();
        instance = new CodecWrapperFactory();
        
        switch (libWrapper.getMajorVersion()) {
            case 53:
                CODEC_ID_NONE = CodecID53.CODEC_ID_NONE;
                /// video codecs
                CODEC_ID_MPEG1VIDEO = CodecID53.CODEC_ID_MPEG1VIDEO;
                CODEC_ID_MPEG2VIDEO = CodecID53.CODEC_ID_MPEG2VIDEO;
                CODEC_ID_MPEG2VIDEO_XVMC = CodecID53.CODEC_ID_MPEG2VIDEO_XVMC;
                CODEC_ID_H261 = CodecID53.CODEC_ID_H261;
                CODEC_ID_H263 = CodecID53.CODEC_ID_H263;
                CODEC_ID_RV10 = CodecID53.CODEC_ID_RV10;
                CODEC_ID_RV20 = CodecID53.CODEC_ID_RV20;
                CODEC_ID_MJPEG = CodecID53.CODEC_ID_MJPEG;
                CODEC_ID_MJPEGB = CodecID53.CODEC_ID_MJPEGB;
                CODEC_ID_LJPEG = CodecID53.CODEC_ID_LJPEG;
                CODEC_ID_SP5X = CodecID53.CODEC_ID_SP5X;
                CODEC_ID_JPEGLS = CodecID53.CODEC_ID_JPEGLS;
                CODEC_ID_MPEG4 = CodecID53.CODEC_ID_MPEG4;
                CODEC_ID_RAWVIDEO = CodecID53.CODEC_ID_RAWVIDEO;
                CODEC_ID_MSMPEG4V1 = CodecID53.CODEC_ID_MSMPEG4V1;
                CODEC_ID_MSMPEG4V2 = CodecID53.CODEC_ID_MSMPEG4V2;
                CODEC_ID_MSMPEG4V3 = CodecID53.CODEC_ID_MSMPEG4V3;
                CODEC_ID_WMV1 = CodecID53.CODEC_ID_WMV1;
                CODEC_ID_WMV2 = CodecID53.CODEC_ID_WMV2;
                CODEC_ID_H263P = CodecID53.CODEC_ID_H263P;
                CODEC_ID_H263I = CodecID53.CODEC_ID_H263I;
                CODEC_ID_FLV1 = CodecID53.CODEC_ID_FLV1;
                CODEC_ID_SVQ1 = CodecID53.CODEC_ID_SVQ1;
                CODEC_ID_SVQ3 = CodecID53.CODEC_ID_SVQ3;
                CODEC_ID_DVVIDEO = CodecID53.CODEC_ID_DVVIDEO;
                CODEC_ID_HUFFYUV = CodecID53.CODEC_ID_HUFFYUV;
                CODEC_ID_CYUV = CodecID53.CODEC_ID_CYUV;
                CODEC_ID_H264 = CodecID53.CODEC_ID_H264;
                CODEC_ID_INDEO3 = CodecID53.CODEC_ID_INDEO3;
                CODEC_ID_VP3 = CodecID53.CODEC_ID_VP3;
                CODEC_ID_THEORA = CodecID53.CODEC_ID_THEORA;
                CODEC_ID_ASV1 = CodecID53.CODEC_ID_ASV1;
                CODEC_ID_ASV2 = CodecID53.CODEC_ID_ASV2;
                CODEC_ID_FFV1 = CodecID53.CODEC_ID_FFV1;
                CODEC_ID_4XM = CodecID53.CODEC_ID_4XM;
                CODEC_ID_VCR1 = CodecID53.CODEC_ID_VCR1;
                CODEC_ID_CLJR = CodecID53.CODEC_ID_CLJR;
                CODEC_ID_MDEC = CodecID53.CODEC_ID_MDEC;
                CODEC_ID_ROQ = CodecID53.CODEC_ID_ROQ;
                CODEC_ID_INTERPLAY_VIDEO = CodecID53.CODEC_ID_INTERPLAY_VIDEO;
                CODEC_ID_XAN_WC3 = CodecID53.CODEC_ID_XAN_WC3;
                CODEC_ID_XAN_WC4 = CodecID53.CODEC_ID_XAN_WC4;
                CODEC_ID_RPZA = CodecID53.CODEC_ID_RPZA;
                CODEC_ID_CINEPAK = CodecID53.CODEC_ID_CINEPAK;
                CODEC_ID_WS_VQA = CodecID53.CODEC_ID_WS_VQA;
                CODEC_ID_MSRLE = CodecID53.CODEC_ID_MSRLE;
                CODEC_ID_MSVIDEO1 = CodecID53.CODEC_ID_MSVIDEO1;
                CODEC_ID_IDCIN = CodecID53.CODEC_ID_IDCIN;
                CODEC_ID_8BPS = CodecID53.CODEC_ID_8BPS;
                CODEC_ID_SMC = CodecID53.CODEC_ID_SMC;
                CODEC_ID_FLIC = CodecID53.CODEC_ID_FLIC;
                CODEC_ID_TRUEMOTION1 = CodecID53.CODEC_ID_TRUEMOTION1;
                CODEC_ID_VMDVIDEO = CodecID53.CODEC_ID_VMDVIDEO;
                CODEC_ID_MSZH = CodecID53.CODEC_ID_MSZH;
                CODEC_ID_ZLIB = CodecID53.CODEC_ID_ZLIB;
                CODEC_ID_QTRLE = CodecID53.CODEC_ID_QTRLE;
                CODEC_ID_SNOW = CodecID53.CODEC_ID_SNOW;
                CODEC_ID_TSCC = CodecID53.CODEC_ID_TSCC;
                CODEC_ID_ULTI = CodecID53.CODEC_ID_ULTI;
                CODEC_ID_QDRAW = CodecID53.CODEC_ID_QDRAW;
                CODEC_ID_VIXL = CodecID53.CODEC_ID_VIXL;
                CODEC_ID_QPEG = CodecID53.CODEC_ID_QPEG;
                CODEC_ID_PNG = CodecID53.CODEC_ID_PNG;
                CODEC_ID_PPM = CodecID53.CODEC_ID_PPM;
                CODEC_ID_PBM = CodecID53.CODEC_ID_PBM;
                CODEC_ID_PGM = CodecID53.CODEC_ID_PGM;
                CODEC_ID_PGMYUV = CodecID53.CODEC_ID_PGMYUV;
                CODEC_ID_PAM = CodecID53.CODEC_ID_PAM;
                CODEC_ID_FFVHUFF = CodecID53.CODEC_ID_FFVHUFF;
                CODEC_ID_RV30 = CodecID53.CODEC_ID_RV30;
                CODEC_ID_RV40 = CodecID53.CODEC_ID_RV40;
                CODEC_ID_VC1 = CodecID53.CODEC_ID_VC1;
                CODEC_ID_WMV3 = CodecID53.CODEC_ID_WMV3;
                CODEC_ID_LOCO = CodecID53.CODEC_ID_LOCO;
                CODEC_ID_WNV1 = CodecID53.CODEC_ID_WNV1;
                CODEC_ID_AASC = CodecID53.CODEC_ID_AASC;
                CODEC_ID_INDEO2 = CodecID53.CODEC_ID_INDEO2;
                CODEC_ID_FRAPS = CodecID53.CODEC_ID_FRAPS;
                CODEC_ID_TRUEMOTION2 = CodecID53.CODEC_ID_TRUEMOTION2;
                CODEC_ID_BMP = CodecID53.CODEC_ID_BMP;
                CODEC_ID_CSCD = CodecID53.CODEC_ID_CSCD;
                CODEC_ID_MMVIDEO = CodecID53.CODEC_ID_MMVIDEO;
                CODEC_ID_ZMBV = CodecID53.CODEC_ID_ZMBV;
                CODEC_ID_AVS = CodecID53.CODEC_ID_AVS;
                CODEC_ID_SMACKVIDEO = CodecID53.CODEC_ID_SMACKVIDEO;
                CODEC_ID_NUV = CodecID53.CODEC_ID_NUV;
                CODEC_ID_KMVC = CodecID53.CODEC_ID_KMVC;
                CODEC_ID_FLASHSV = CodecID53.CODEC_ID_FLASHSV;
                CODEC_ID_CAVS = CodecID53.CODEC_ID_CAVS;
                CODEC_ID_JPEG2000 = CodecID53.CODEC_ID_JPEG2000;
                CODEC_ID_VMNC = CodecID53.CODEC_ID_VMNC;
                CODEC_ID_VP5 = CodecID53.CODEC_ID_VP5;
                CODEC_ID_VP6 = CodecID53.CODEC_ID_VP6;
                CODEC_ID_VP6F = CodecID53.CODEC_ID_VP6F;
                CODEC_ID_TARGA = CodecID53.CODEC_ID_TARGA;
                CODEC_ID_DSICINVIDEO = CodecID53.CODEC_ID_DSICINVIDEO;
                CODEC_ID_TIERTEXSEQVIDEO = CodecID53.CODEC_ID_TIERTEXSEQVIDEO;
                CODEC_ID_TIFF = CodecID53.CODEC_ID_TIFF;
                CODEC_ID_GIF = CodecID53.CODEC_ID_GIF;
                CODEC_ID_FFH264 = CodecID53.CODEC_ID_FFH264;
                CODEC_ID_DXA = CodecID53.CODEC_ID_DXA;
                CODEC_ID_DNXHD = CodecID53.CODEC_ID_DNXHD;
                CODEC_ID_THP = CodecID53.CODEC_ID_THP;
                CODEC_ID_SGI = CodecID53.CODEC_ID_SGI;
                CODEC_ID_C93 = CodecID53.CODEC_ID_C93;
                CODEC_ID_BETHSOFTVID = CodecID53.CODEC_ID_BETHSOFTVID;
                CODEC_ID_PTX = CodecID53.CODEC_ID_PTX;
                CODEC_ID_TXD = CodecID53.CODEC_ID_TXD;
                CODEC_ID_VP6A = CodecID53.CODEC_ID_VP6A;
                CODEC_ID_AMV = CodecID53.CODEC_ID_AMV;
                CODEC_ID_VB = CodecID53.CODEC_ID_VB;
                CODEC_ID_PCX = CodecID53.CODEC_ID_PCX;
                CODEC_ID_SUNRAST = CodecID53.CODEC_ID_SUNRAST;
                CODEC_ID_INDEO4 = CodecID53.CODEC_ID_INDEO4;
                CODEC_ID_INDEO5 = CodecID53.CODEC_ID_INDEO5;
                CODEC_ID_MIMIC = CodecID53.CODEC_ID_MIMIC;
                CODEC_ID_RL2 = CodecID53.CODEC_ID_RL2;
                CODEC_ID_ESCAPE124 = CodecID53.CODEC_ID_ESCAPE124;
                CODEC_ID_DIRAC = CodecID53.CODEC_ID_DIRAC;
                CODEC_ID_BFI = CodecID53.CODEC_ID_BFI;
                CODEC_ID_CMV = CodecID53.CODEC_ID_CMV;
                CODEC_ID_MOTIONPIXELS = CodecID53.CODEC_ID_MOTIONPIXELS;
                CODEC_ID_TGV = CodecID53.CODEC_ID_TGV;
                CODEC_ID_TGQ = CodecID53.CODEC_ID_TGQ;
                CODEC_ID_TQI = CodecID53.CODEC_ID_TQI;
                CODEC_ID_AURA = CodecID53.CODEC_ID_AURA;
                CODEC_ID_AURA2 = CodecID53.CODEC_ID_AURA2;
                CODEC_ID_V210X = CodecID53.CODEC_ID_V210X;
                CODEC_ID_TMV = CodecID53.CODEC_ID_TMV;
                CODEC_ID_V210 = CodecID53.CODEC_ID_V210;
                CODEC_ID_DPX = CodecID53.CODEC_ID_DPX;
                CODEC_ID_MAD = CodecID53.CODEC_ID_MAD;
                CODEC_ID_FRWU = CodecID53.CODEC_ID_FRWU;
                CODEC_ID_FLASHSV2 = CodecID53.CODEC_ID_FLASHSV2;
                CODEC_ID_CDGRAPHICS = CodecID53.CODEC_ID_CDGRAPHICS;
                CODEC_ID_R210 = CodecID53.CODEC_ID_R210;
                CODEC_ID_ANM = CodecID53.CODEC_ID_ANM;
                CODEC_ID_BINKVIDEO = CodecID53.CODEC_ID_BINKVIDEO;
                CODEC_ID_IFF_ILBM = CodecID53.CODEC_ID_IFF_ILBM;
                CODEC_ID_IFF_BYTERUN1 = CodecID53.CODEC_ID_IFF_BYTERUN1;
                CODEC_ID_KGV1 = CodecID53.CODEC_ID_KGV1;
                CODEC_ID_YOP = CodecID53.CODEC_ID_YOP;
                CODEC_ID_VP8 = CodecID53.CODEC_ID_VP8;
                CODEC_ID_PICTOR = CodecID53.CODEC_ID_PICTOR;
                CODEC_ID_ANSI = CodecID53.CODEC_ID_ANSI;
                CODEC_ID_A64_MULTI = CodecID53.CODEC_ID_A64_MULTI;
                CODEC_ID_A64_MULTI5 = CodecID53.CODEC_ID_A64_MULTI5;
                CODEC_ID_R10K = CodecID53.CODEC_ID_R10K;
                CODEC_ID_MXPEG = CodecID53.CODEC_ID_MXPEG;
                CODEC_ID_LAGARITH = CodecID53.CODEC_ID_LAGARITH;
                CODEC_ID_PRORES = CodecID53.CODEC_ID_PRORES;
                CODEC_ID_JV = CodecID53.CODEC_ID_JV;
                CODEC_ID_DFA = CodecID53.CODEC_ID_DFA;
                CODEC_ID_WMV3IMAGE = CodecID53.CODEC_ID_WMV3IMAGE;
                CODEC_ID_VC1IMAGE = CodecID53.CODEC_ID_VC1IMAGE;
                CODEC_ID_UTVIDEO = CodecID53.CODEC_ID_NONE;
                CODEC_ID_BMV_VIDEO = CodecID53.CODEC_ID_NONE;
                CODEC_ID_VBLE = CodecID53.CODEC_ID_NONE;
                CODEC_ID_DXTORY = CodecID53.CODEC_ID_NONE;
                CODEC_ID_V410 = CodecID53.CODEC_ID_NONE;
                CODEC_ID_XWD = CodecID53.CODEC_ID_NONE;
                CODEC_ID_CDXL = CodecID53.CODEC_ID_NONE;
                /// various PCM "codecs"
                CODEC_ID_FIRST_AUDIO = CodecID53.CODEC_ID_FIRST_AUDIO;
                CODEC_ID_PCM_S16LE = CodecID53.CODEC_ID_PCM_S16LE;
                CODEC_ID_PCM_S16BE = CodecID53.CODEC_ID_PCM_S16BE;
                CODEC_ID_PCM_U16LE = CodecID53.CODEC_ID_PCM_U16LE;
                CODEC_ID_PCM_U16BE = CodecID53.CODEC_ID_PCM_U16BE;
                CODEC_ID_PCM_S8 = CodecID53.CODEC_ID_PCM_S8;
                CODEC_ID_PCM_U8 = CodecID53.CODEC_ID_PCM_U8;
                CODEC_ID_PCM_MULAW = CodecID53.CODEC_ID_PCM_MULAW;
                CODEC_ID_PCM_ALAW = CodecID53.CODEC_ID_PCM_ALAW;
                CODEC_ID_PCM_S32LE = CodecID53.CODEC_ID_PCM_S32LE;
                CODEC_ID_PCM_S32BE = CodecID53.CODEC_ID_PCM_S32BE;
                CODEC_ID_PCM_U32LE = CodecID53.CODEC_ID_PCM_U32LE;
                CODEC_ID_PCM_U32BE = CodecID53.CODEC_ID_PCM_U32BE;
                CODEC_ID_PCM_S24LE = CodecID53.CODEC_ID_PCM_S24LE;
                CODEC_ID_PCM_S24BE = CodecID53.CODEC_ID_PCM_S24BE;
                CODEC_ID_PCM_U24LE = CodecID53.CODEC_ID_PCM_U24LE;
                CODEC_ID_PCM_U24BE = CodecID53.CODEC_ID_PCM_U24BE;
                CODEC_ID_PCM_S24DAUD = CodecID53.CODEC_ID_PCM_S24DAUD;
                CODEC_ID_PCM_ZORK = CodecID53.CODEC_ID_PCM_ZORK;
                CODEC_ID_PCM_S16LE_PLANAR = CodecID53.CODEC_ID_PCM_S16LE_PLANAR;
                CODEC_ID_PCM_DVD = CodecID53.CODEC_ID_PCM_DVD;
                CODEC_ID_PCM_F32BE = CodecID53.CODEC_ID_PCM_F32BE;
                CODEC_ID_PCM_F32LE = CodecID53.CODEC_ID_PCM_F32LE;
                CODEC_ID_PCM_F64BE = CodecID53.CODEC_ID_PCM_F64BE;
                CODEC_ID_PCM_F64LE = CodecID53.CODEC_ID_PCM_F64LE;
                CODEC_ID_PCM_BLURAY = CodecID53.CODEC_ID_PCM_BLURAY;
                CODEC_ID_PCM_LXF = CodecID53.CODEC_ID_PCM_LXF;
                CODEC_ID_S302M = CodecID53.CODEC_ID_S302M;
                CODEC_ID_PCM_S8_PLANAR = CodecID53.CODEC_ID_NONE;
                /// various ADPCM codecs
                CODEC_ID_ADPCM_IMA_QT = CodecID53.CODEC_ID_ADPCM_IMA_QT;
                CODEC_ID_ADPCM_IMA_WAV = CodecID53.CODEC_ID_ADPCM_IMA_WAV;
                CODEC_ID_ADPCM_IMA_DK3 = CodecID53.CODEC_ID_ADPCM_IMA_DK3;
                CODEC_ID_ADPCM_IMA_DK4 = CodecID53.CODEC_ID_ADPCM_IMA_DK4;
                CODEC_ID_ADPCM_IMA_WS = CodecID53.CODEC_ID_ADPCM_IMA_WS;
                CODEC_ID_ADPCM_IMA_SMJPEG = CodecID53.CODEC_ID_ADPCM_IMA_SMJPEG;
                CODEC_ID_ADPCM_MS = CodecID53.CODEC_ID_ADPCM_MS;
                CODEC_ID_ADPCM_4XM = CodecID53.CODEC_ID_ADPCM_4XM;
                CODEC_ID_ADPCM_XA = CodecID53.CODEC_ID_ADPCM_XA;
                CODEC_ID_ADPCM_ADX = CodecID53.CODEC_ID_ADPCM_ADX;
                CODEC_ID_ADPCM_EA = CodecID53.CODEC_ID_ADPCM_EA;
                CODEC_ID_ADPCM_G726 = CodecID53.CODEC_ID_ADPCM_G726;
                CODEC_ID_ADPCM_CT = CodecID53.CODEC_ID_ADPCM_CT;
                CODEC_ID_ADPCM_SWF = CodecID53.CODEC_ID_ADPCM_SWF;
                CODEC_ID_ADPCM_YAMAHA = CodecID53.CODEC_ID_ADPCM_YAMAHA;
                CODEC_ID_ADPCM_SBPRO_4 = CodecID53.CODEC_ID_ADPCM_SBPRO_4;
                CODEC_ID_ADPCM_SBPRO_3 = CodecID53.CODEC_ID_ADPCM_SBPRO_3;
                CODEC_ID_ADPCM_SBPRO_2 = CodecID53.CODEC_ID_ADPCM_SBPRO_2;
                CODEC_ID_ADPCM_THP = CodecID53.CODEC_ID_ADPCM_THP;
                CODEC_ID_ADPCM_IMA_AMV = CodecID53.CODEC_ID_ADPCM_IMA_AMV;
                CODEC_ID_ADPCM_EA_R1 = CodecID53.CODEC_ID_ADPCM_EA_R1;
                CODEC_ID_ADPCM_EA_R3 = CodecID53.CODEC_ID_ADPCM_EA_R3;
                CODEC_ID_ADPCM_EA_R2 = CodecID53.CODEC_ID_ADPCM_EA_R2;
                CODEC_ID_ADPCM_IMA_EA_SEAD = CodecID53.CODEC_ID_ADPCM_IMA_EA_SEAD;
                CODEC_ID_ADPCM_IMA_EA_EACS = CodecID53.CODEC_ID_ADPCM_IMA_EA_EACS;
                CODEC_ID_ADPCM_EA_XAS = CodecID53.CODEC_ID_ADPCM_EA_XAS;
                CODEC_ID_ADPCM_EA_MAXIS_XA = CodecID53.CODEC_ID_ADPCM_EA_MAXIS_XA;
                CODEC_ID_ADPCM_IMA_ISS = CodecID53.CODEC_ID_ADPCM_IMA_ISS;
                CODEC_ID_ADPCM_G722 = CodecID53.CODEC_ID_ADPCM_G722;
                CODEC_ID_ADPCM_IMA_APC = CodecID53.CODEC_ID_NONE;
                /// AMR
                CODEC_ID_AMR_NB = CodecID53.CODEC_ID_AMR_NB;
                CODEC_ID_AMR_WB = CodecID53.CODEC_ID_AMR_WB;
                /// RealAudio codecs
                CODEC_ID_RA_144 = CodecID53.CODEC_ID_RA_144;
                CODEC_ID_RA_288 = CodecID53.CODEC_ID_RA_288;
                /// various DPCM codecs
                CODEC_ID_ROQ_DPCM = CodecID53.CODEC_ID_ROQ_DPCM;
                CODEC_ID_INTERPLAY_DPCM = CodecID53.CODEC_ID_INTERPLAY_DPCM;
                CODEC_ID_XAN_DPCM = CodecID53.CODEC_ID_XAN_DPCM;
                CODEC_ID_SOL_DPCM = CodecID53.CODEC_ID_SOL_DPCM;
                /// audio codecs
                CODEC_ID_MP2 = CodecID53.CODEC_ID_MP2;
                CODEC_ID_MP3 = CodecID53.CODEC_ID_MP3;
                CODEC_ID_AAC = CodecID53.CODEC_ID_AAC;
                CODEC_ID_AC3 = CodecID53.CODEC_ID_AC3;
                CODEC_ID_DTS = CodecID53.CODEC_ID_DTS;
                CODEC_ID_VORBIS = CodecID53.CODEC_ID_VORBIS;
                CODEC_ID_DVAUDIO = CodecID53.CODEC_ID_DVAUDIO;
                CODEC_ID_WMAV1 = CodecID53.CODEC_ID_WMAV1;
                CODEC_ID_WMAV2 = CodecID53.CODEC_ID_WMAV2;
                CODEC_ID_MACE3 = CodecID53.CODEC_ID_MACE3;
                CODEC_ID_MACE6 = CodecID53.CODEC_ID_MACE6;
                CODEC_ID_VMDAUDIO = CodecID53.CODEC_ID_VMDAUDIO;
                CODEC_ID_SONIC = CodecID53.CODEC_ID_SONIC;
                CODEC_ID_SONIC_LS = CodecID53.CODEC_ID_SONIC_LS;
                CODEC_ID_FLAC = CodecID53.CODEC_ID_FLAC;
                CODEC_ID_MP3ADU = CodecID53.CODEC_ID_MP3ADU;
                CODEC_ID_MP3ON4 = CodecID53.CODEC_ID_MP3ON4;
                CODEC_ID_SHORTEN = CodecID53.CODEC_ID_SHORTEN;
                CODEC_ID_ALAC = CodecID53.CODEC_ID_ALAC;
                CODEC_ID_WESTWOOD_SND1 = CodecID53.CODEC_ID_WESTWOOD_SND1;
                CODEC_ID_GSM = CodecID53.CODEC_ID_GSM;
                CODEC_ID_QDM2 = CodecID53.CODEC_ID_QDM2;
                CODEC_ID_COOK = CodecID53.CODEC_ID_COOK;
                CODEC_ID_TRUESPEECH = CodecID53.CODEC_ID_TRUESPEECH;
                CODEC_ID_TTA = CodecID53.CODEC_ID_TTA;
                CODEC_ID_SMACKAUDIO = CodecID53.CODEC_ID_SMACKAUDIO;
                CODEC_ID_QCELP = CodecID53.CODEC_ID_QCELP;
                CODEC_ID_WAVPACK = CodecID53.CODEC_ID_WAVPACK;
                CODEC_ID_DSICINAUDIO = CodecID53.CODEC_ID_DSICINAUDIO;
                CODEC_ID_IMC = CodecID53.CODEC_ID_IMC;
                CODEC_ID_MUSEPACK7 = CodecID53.CODEC_ID_MUSEPACK7;
                CODEC_ID_MLP = CodecID53.CODEC_ID_MLP;
                CODEC_ID_GSM_MS = CodecID53.CODEC_ID_GSM_MS;
                CODEC_ID_ATRAC3 = CodecID53.CODEC_ID_ATRAC3;
                CODEC_ID_VOXWARE = CodecID53.CODEC_ID_VOXWARE;
                CODEC_ID_APE = CodecID53.CODEC_ID_APE;
                CODEC_ID_NELLYMOSER = CodecID53.CODEC_ID_NELLYMOSER;
                CODEC_ID_MUSEPACK8 = CodecID53.CODEC_ID_MUSEPACK8;
                CODEC_ID_SPEEX = CodecID53.CODEC_ID_SPEEX;
                CODEC_ID_WMAVOICE = CodecID53.CODEC_ID_WMAVOICE;
                CODEC_ID_WMAPRO = CodecID53.CODEC_ID_WMAPRO;
                CODEC_ID_WMALOSSLESS = CodecID53.CODEC_ID_WMALOSSLESS;
                CODEC_ID_ATRAC3P = CodecID53.CODEC_ID_ATRAC3P;
                CODEC_ID_EAC3 = CodecID53.CODEC_ID_EAC3;
                CODEC_ID_SIPR = CodecID53.CODEC_ID_SIPR;
                CODEC_ID_MP1 = CodecID53.CODEC_ID_MP1;
                CODEC_ID_TWINVQ = CodecID53.CODEC_ID_TWINVQ;
                CODEC_ID_TRUEHD = CodecID53.CODEC_ID_TRUEHD;
                CODEC_ID_MP4ALS = CodecID53.CODEC_ID_MP4ALS;
                CODEC_ID_ATRAC1 = CodecID53.CODEC_ID_ATRAC1;
                CODEC_ID_BINKAUDIO_RDFT = CodecID53.CODEC_ID_BINKAUDIO_RDFT;
                CODEC_ID_BINKAUDIO_DCT = CodecID53.CODEC_ID_BINKAUDIO_DCT;
                CODEC_ID_AAC_LATM = CodecID53.CODEC_ID_AAC_LATM;
                CODEC_ID_QDMC = CodecID53.CODEC_ID_QDMC;
                CODEC_ID_CELT = CodecID53.CODEC_ID_CELT;
                CODEC_ID_G723_1 = CodecID53.CODEC_ID_NONE;
                CODEC_ID_G729 = CodecID53.CODEC_ID_NONE;
                CODEC_ID_8SVX_EXP = CodecID53.CODEC_ID_8SVX_EXP;
                CODEC_ID_8SVX_FIB = CodecID53.CODEC_ID_8SVX_FIB;
                CODEC_ID_BMV_AUDIO = CodecID53.CODEC_ID_NONE;
                /// subtitle codecs
                CODEC_ID_FIRST_SUBTITLE = CodecID53.CODEC_ID_FIRST_SUBTITLE;
                CODEC_ID_DVD_SUBTITLE = CodecID53.CODEC_ID_DVD_SUBTITLE;
                CODEC_ID_DVB_SUBTITLE = CodecID53.CODEC_ID_DVB_SUBTITLE;
                CODEC_ID_TEXT = CodecID53.CODEC_ID_TEXT;
                CODEC_ID_XSUB = CodecID53.CODEC_ID_XSUB;
                CODEC_ID_SSA = CodecID53.CODEC_ID_SSA;
                CODEC_ID_MOV_TEXT = CodecID53.CODEC_ID_MOV_TEXT;
                CODEC_ID_HDMV_PGS_SUBTITLE = CodecID53.CODEC_ID_HDMV_PGS_SUBTITLE;
                CODEC_ID_DVB_TELETEXT = CodecID53.CODEC_ID_DVB_TELETEXT;
                CODEC_ID_SRT = CodecID53.CODEC_ID_SRT;
                /// other specific kind of codecs (generally used for attachments)
                CODEC_ID_FIRST_UNKNOWN = CodecID53.CODEC_ID_FIRST_UNKNOWN;
                CODEC_ID_TTF = CodecID53.CODEC_ID_TTF;
                CODEC_ID_PROBE = CodecID53.CODEC_ID_PROBE;
                /// stream (only used by libavformat)
                CODEC_ID_MPEG2TS = CodecID53.CODEC_ID_MPEG2TS;
                // stream (only used by libavformat)
                CODEC_ID_MPEG4SYSTEMS = CodecID53.CODEC_ID_NONE;
                CODEC_ID_FFMETADATA = CodecID53.CODEC_ID_FFMETADATA;
                break;
            case 54:
                CODEC_ID_NONE = CodecID54.CODEC_ID_NONE;
                /// video codecs
                CODEC_ID_MPEG1VIDEO = CodecID54.CODEC_ID_MPEG1VIDEO;
                CODEC_ID_MPEG2VIDEO = CodecID54.CODEC_ID_MPEG2VIDEO;
                CODEC_ID_MPEG2VIDEO_XVMC = CodecID54.CODEC_ID_MPEG2VIDEO_XVMC;
                CODEC_ID_H261 = CodecID54.CODEC_ID_H261;
                CODEC_ID_H263 = CodecID54.CODEC_ID_H263;
                CODEC_ID_RV10 = CodecID54.CODEC_ID_RV10;
                CODEC_ID_RV20 = CodecID54.CODEC_ID_RV20;
                CODEC_ID_MJPEG = CodecID54.CODEC_ID_MJPEG;
                CODEC_ID_MJPEGB = CodecID54.CODEC_ID_MJPEGB;
                CODEC_ID_LJPEG = CodecID54.CODEC_ID_LJPEG;
                CODEC_ID_SP5X = CodecID54.CODEC_ID_SP5X;
                CODEC_ID_JPEGLS = CodecID54.CODEC_ID_JPEGLS;
                CODEC_ID_MPEG4 = CodecID54.CODEC_ID_MPEG4;
                CODEC_ID_RAWVIDEO = CodecID54.CODEC_ID_RAWVIDEO;
                CODEC_ID_MSMPEG4V1 = CodecID54.CODEC_ID_MSMPEG4V1;
                CODEC_ID_MSMPEG4V2 = CodecID54.CODEC_ID_MSMPEG4V2;
                CODEC_ID_MSMPEG4V3 = CodecID54.CODEC_ID_MSMPEG4V3;
                CODEC_ID_WMV1 = CodecID54.CODEC_ID_WMV1;
                CODEC_ID_WMV2 = CodecID54.CODEC_ID_WMV2;
                CODEC_ID_H263P = CodecID54.CODEC_ID_H263P;
                CODEC_ID_H263I = CodecID54.CODEC_ID_H263I;
                CODEC_ID_FLV1 = CodecID54.CODEC_ID_FLV1;
                CODEC_ID_SVQ1 = CodecID54.CODEC_ID_SVQ1;
                CODEC_ID_SVQ3 = CodecID54.CODEC_ID_SVQ3;
                CODEC_ID_DVVIDEO = CodecID54.CODEC_ID_DVVIDEO;
                CODEC_ID_HUFFYUV = CodecID54.CODEC_ID_HUFFYUV;
                CODEC_ID_CYUV = CodecID54.CODEC_ID_CYUV;
                CODEC_ID_H264 = CodecID54.CODEC_ID_H264;
                CODEC_ID_INDEO3 = CodecID54.CODEC_ID_INDEO3;
                CODEC_ID_VP3 = CodecID54.CODEC_ID_VP3;
                CODEC_ID_THEORA = CodecID54.CODEC_ID_THEORA;
                CODEC_ID_ASV1 = CodecID54.CODEC_ID_ASV1;
                CODEC_ID_ASV2 = CodecID54.CODEC_ID_ASV2;
                CODEC_ID_FFV1 = CodecID54.CODEC_ID_FFV1;
                CODEC_ID_4XM = CodecID54.CODEC_ID_4XM;
                CODEC_ID_VCR1 = CodecID54.CODEC_ID_VCR1;
                CODEC_ID_CLJR = CodecID54.CODEC_ID_CLJR;
                CODEC_ID_MDEC = CodecID54.CODEC_ID_MDEC;
                CODEC_ID_ROQ = CodecID54.CODEC_ID_ROQ;
                CODEC_ID_INTERPLAY_VIDEO = CodecID54.CODEC_ID_INTERPLAY_VIDEO;
                CODEC_ID_XAN_WC3 = CodecID54.CODEC_ID_XAN_WC3;
                CODEC_ID_XAN_WC4 = CodecID54.CODEC_ID_XAN_WC4;
                CODEC_ID_RPZA = CodecID54.CODEC_ID_RPZA;
                CODEC_ID_CINEPAK = CodecID54.CODEC_ID_CINEPAK;
                CODEC_ID_WS_VQA = CodecID54.CODEC_ID_WS_VQA;
                CODEC_ID_MSRLE = CodecID54.CODEC_ID_MSRLE;
                CODEC_ID_MSVIDEO1 = CodecID54.CODEC_ID_MSVIDEO1;
                CODEC_ID_IDCIN = CodecID54.CODEC_ID_IDCIN;
                CODEC_ID_8BPS = CodecID54.CODEC_ID_8BPS;
                CODEC_ID_SMC = CodecID54.CODEC_ID_SMC;
                CODEC_ID_FLIC = CodecID54.CODEC_ID_FLIC;
                CODEC_ID_TRUEMOTION1 = CodecID54.CODEC_ID_TRUEMOTION1;
                CODEC_ID_VMDVIDEO = CodecID54.CODEC_ID_VMDVIDEO;
                CODEC_ID_MSZH = CodecID54.CODEC_ID_MSZH;
                CODEC_ID_ZLIB = CodecID54.CODEC_ID_ZLIB;
                CODEC_ID_QTRLE = CodecID54.CODEC_ID_QTRLE;
                CODEC_ID_SNOW = CodecID54.CODEC_ID_SNOW;
                CODEC_ID_TSCC = CodecID54.CODEC_ID_TSCC;
                CODEC_ID_ULTI = CodecID54.CODEC_ID_ULTI;
                CODEC_ID_QDRAW = CodecID54.CODEC_ID_QDRAW;
                CODEC_ID_VIXL = CodecID54.CODEC_ID_VIXL;
                CODEC_ID_QPEG = CodecID54.CODEC_ID_QPEG;
                CODEC_ID_PNG = CodecID54.CODEC_ID_PNG;
                CODEC_ID_PPM = CodecID54.CODEC_ID_PPM;
                CODEC_ID_PBM = CodecID54.CODEC_ID_PBM;
                CODEC_ID_PGM = CodecID54.CODEC_ID_PGM;
                CODEC_ID_PGMYUV = CodecID54.CODEC_ID_PGMYUV;
                CODEC_ID_PAM = CodecID54.CODEC_ID_PAM;
                CODEC_ID_FFVHUFF = CodecID54.CODEC_ID_FFVHUFF;
                CODEC_ID_RV30 = CodecID54.CODEC_ID_RV30;
                CODEC_ID_RV40 = CodecID54.CODEC_ID_RV40;
                CODEC_ID_VC1 = CodecID54.CODEC_ID_VC1;
                CODEC_ID_WMV3 = CodecID54.CODEC_ID_WMV3;
                CODEC_ID_LOCO = CodecID54.CODEC_ID_LOCO;
                CODEC_ID_WNV1 = CodecID54.CODEC_ID_WNV1;
                CODEC_ID_AASC = CodecID54.CODEC_ID_AASC;
                CODEC_ID_INDEO2 = CodecID54.CODEC_ID_INDEO2;
                CODEC_ID_FRAPS = CodecID54.CODEC_ID_FRAPS;
                CODEC_ID_TRUEMOTION2 = CodecID54.CODEC_ID_TRUEMOTION2;
                CODEC_ID_BMP = CodecID54.CODEC_ID_BMP;
                CODEC_ID_CSCD = CodecID54.CODEC_ID_CSCD;
                CODEC_ID_MMVIDEO = CodecID54.CODEC_ID_MMVIDEO;
                CODEC_ID_ZMBV = CodecID54.CODEC_ID_ZMBV;
                CODEC_ID_AVS = CodecID54.CODEC_ID_AVS;
                CODEC_ID_SMACKVIDEO = CodecID54.CODEC_ID_SMACKVIDEO;
                CODEC_ID_NUV = CodecID54.CODEC_ID_NUV;
                CODEC_ID_KMVC = CodecID54.CODEC_ID_KMVC;
                CODEC_ID_FLASHSV = CodecID54.CODEC_ID_FLASHSV;
                CODEC_ID_CAVS = CodecID54.CODEC_ID_CAVS;
                CODEC_ID_JPEG2000 = CodecID54.CODEC_ID_JPEG2000;
                CODEC_ID_VMNC = CodecID54.CODEC_ID_VMNC;
                CODEC_ID_VP5 = CodecID54.CODEC_ID_VP5;
                CODEC_ID_VP6 = CodecID54.CODEC_ID_VP6;
                CODEC_ID_VP6F = CodecID54.CODEC_ID_VP6F;
                CODEC_ID_TARGA = CodecID54.CODEC_ID_TARGA;
                CODEC_ID_DSICINVIDEO = CodecID54.CODEC_ID_DSICINVIDEO;
                CODEC_ID_TIERTEXSEQVIDEO = CodecID54.CODEC_ID_TIERTEXSEQVIDEO;
                CODEC_ID_TIFF = CodecID54.CODEC_ID_TIFF;
                CODEC_ID_GIF = CodecID54.CODEC_ID_GIF;
                CODEC_ID_FFH264 = CodecID54.CODEC_ID_NONE;
                CODEC_ID_DXA = CodecID54.CODEC_ID_DXA;
                CODEC_ID_DNXHD = CodecID54.CODEC_ID_DNXHD;
                CODEC_ID_THP = CodecID54.CODEC_ID_THP;
                CODEC_ID_SGI = CodecID54.CODEC_ID_SGI;
                CODEC_ID_C93 = CodecID54.CODEC_ID_C93;
                CODEC_ID_BETHSOFTVID = CodecID54.CODEC_ID_BETHSOFTVID;
                CODEC_ID_PTX = CodecID54.CODEC_ID_PTX;
                CODEC_ID_TXD = CodecID54.CODEC_ID_TXD;
                CODEC_ID_VP6A = CodecID54.CODEC_ID_VP6A;
                CODEC_ID_AMV = CodecID54.CODEC_ID_AMV;
                CODEC_ID_VB = CodecID54.CODEC_ID_VB;
                CODEC_ID_PCX = CodecID54.CODEC_ID_PCX;
                CODEC_ID_SUNRAST = CodecID54.CODEC_ID_SUNRAST;
                CODEC_ID_INDEO4 = CodecID54.CODEC_ID_INDEO4;
                CODEC_ID_INDEO5 = CodecID54.CODEC_ID_INDEO5;
                CODEC_ID_MIMIC = CodecID54.CODEC_ID_MIMIC;
                CODEC_ID_RL2 = CodecID54.CODEC_ID_RL2;
                CODEC_ID_ESCAPE124 = CodecID54.CODEC_ID_ESCAPE124;
                CODEC_ID_DIRAC = CodecID54.CODEC_ID_DIRAC;
                CODEC_ID_BFI = CodecID54.CODEC_ID_BFI;
                CODEC_ID_CMV = CodecID54.CODEC_ID_CMV;
                CODEC_ID_MOTIONPIXELS = CodecID54.CODEC_ID_MOTIONPIXELS;
                CODEC_ID_TGV = CodecID54.CODEC_ID_TGV;
                CODEC_ID_TGQ = CodecID54.CODEC_ID_TGQ;
                CODEC_ID_TQI = CodecID54.CODEC_ID_TQI;
                CODEC_ID_AURA = CodecID54.CODEC_ID_AURA;
                CODEC_ID_AURA2 = CodecID54.CODEC_ID_AURA2;
                CODEC_ID_V210X = CodecID54.CODEC_ID_V210X;
                CODEC_ID_TMV = CodecID54.CODEC_ID_TMV;
                CODEC_ID_V210 = CodecID54.CODEC_ID_V210;
                CODEC_ID_DPX = CodecID54.CODEC_ID_DPX;
                CODEC_ID_MAD = CodecID54.CODEC_ID_MAD;
                CODEC_ID_FRWU = CodecID54.CODEC_ID_FRWU;
                CODEC_ID_FLASHSV2 = CodecID54.CODEC_ID_FLASHSV2;
                CODEC_ID_CDGRAPHICS = CodecID54.CODEC_ID_CDGRAPHICS;
                CODEC_ID_R210 = CodecID54.CODEC_ID_R210;
                CODEC_ID_ANM = CodecID54.CODEC_ID_ANM;
                CODEC_ID_BINKVIDEO = CodecID54.CODEC_ID_BINKVIDEO;
                CODEC_ID_IFF_ILBM = CodecID54.CODEC_ID_IFF_ILBM;
                CODEC_ID_IFF_BYTERUN1 = CodecID54.CODEC_ID_IFF_BYTERUN1;
                CODEC_ID_KGV1 = CodecID54.CODEC_ID_KGV1;
                CODEC_ID_YOP = CodecID54.CODEC_ID_YOP;
                CODEC_ID_VP8 = CodecID54.CODEC_ID_VP8;
                CODEC_ID_PICTOR = CodecID54.CODEC_ID_PICTOR;
                CODEC_ID_ANSI = CodecID54.CODEC_ID_ANSI;
                CODEC_ID_A64_MULTI = CodecID54.CODEC_ID_A64_MULTI;
                CODEC_ID_A64_MULTI5 = CodecID54.CODEC_ID_A64_MULTI5;
                CODEC_ID_R10K = CodecID54.CODEC_ID_R10K;
                CODEC_ID_MXPEG = CodecID54.CODEC_ID_MXPEG;
                CODEC_ID_LAGARITH = CodecID54.CODEC_ID_LAGARITH;
                CODEC_ID_PRORES = CodecID54.CODEC_ID_PRORES;
                CODEC_ID_JV = CodecID54.CODEC_ID_JV;
                CODEC_ID_DFA = CodecID54.CODEC_ID_DFA;
                CODEC_ID_WMV3IMAGE = CodecID54.CODEC_ID_WMV3IMAGE;
                CODEC_ID_VC1IMAGE = CodecID54.CODEC_ID_VC1IMAGE;
                CODEC_ID_UTVIDEO = CodecID54.CODEC_ID_UTVIDEO;
                CODEC_ID_BMV_VIDEO = CodecID54.CODEC_ID_BMV_VIDEO;
                CODEC_ID_VBLE = CodecID54.CODEC_ID_VBLE;
                CODEC_ID_DXTORY = CodecID54.CODEC_ID_DXTORY;
                CODEC_ID_V410 = CodecID54.CODEC_ID_V410;
                CODEC_ID_XWD = CodecID54.CODEC_ID_XWD;
                CODEC_ID_CDXL = CodecID54.CODEC_ID_CDXL;
                /// various PCM "codecs"
                CODEC_ID_FIRST_AUDIO = CodecID54.CODEC_ID_FIRST_AUDIO;
                CODEC_ID_PCM_S16LE = CodecID54.CODEC_ID_PCM_S16LE;
                CODEC_ID_PCM_S16BE = CodecID54.CODEC_ID_PCM_S16BE;
                CODEC_ID_PCM_U16LE = CodecID54.CODEC_ID_PCM_U16LE;
                CODEC_ID_PCM_U16BE = CodecID54.CODEC_ID_PCM_U16BE;
                CODEC_ID_PCM_S8 = CodecID54.CODEC_ID_PCM_S8;
                CODEC_ID_PCM_U8 = CodecID54.CODEC_ID_PCM_U8;
                CODEC_ID_PCM_MULAW = CodecID54.CODEC_ID_PCM_MULAW;
                CODEC_ID_PCM_ALAW = CodecID54.CODEC_ID_PCM_ALAW;
                CODEC_ID_PCM_S32LE = CodecID54.CODEC_ID_PCM_S32LE;
                CODEC_ID_PCM_S32BE = CodecID54.CODEC_ID_PCM_S32BE;
                CODEC_ID_PCM_U32LE = CodecID54.CODEC_ID_PCM_U32LE;
                CODEC_ID_PCM_U32BE = CodecID54.CODEC_ID_PCM_U32BE;
                CODEC_ID_PCM_S24LE = CodecID54.CODEC_ID_PCM_S24LE;
                CODEC_ID_PCM_S24BE = CodecID54.CODEC_ID_PCM_S24BE;
                CODEC_ID_PCM_U24LE = CodecID54.CODEC_ID_PCM_U24LE;
                CODEC_ID_PCM_U24BE = CodecID54.CODEC_ID_PCM_U24BE;
                CODEC_ID_PCM_S24DAUD = CodecID54.CODEC_ID_PCM_S24DAUD;
                CODEC_ID_PCM_ZORK = CodecID54.CODEC_ID_PCM_ZORK;
                CODEC_ID_PCM_S16LE_PLANAR = CodecID54.CODEC_ID_PCM_S16LE_PLANAR;
                CODEC_ID_PCM_DVD = CodecID54.CODEC_ID_PCM_DVD;
                CODEC_ID_PCM_F32BE = CodecID54.CODEC_ID_PCM_F32BE;
                CODEC_ID_PCM_F32LE = CodecID54.CODEC_ID_PCM_F32LE;
                CODEC_ID_PCM_F64BE = CodecID54.CODEC_ID_PCM_F64BE;
                CODEC_ID_PCM_F64LE = CodecID54.CODEC_ID_PCM_F64LE;
                CODEC_ID_PCM_BLURAY = CodecID54.CODEC_ID_PCM_BLURAY;
                CODEC_ID_PCM_LXF = CodecID54.CODEC_ID_PCM_LXF;
                CODEC_ID_S302M = CodecID54.CODEC_ID_S302M;
                CODEC_ID_PCM_S8_PLANAR = CodecID54.CODEC_ID_PCM_S8_PLANAR;
                /// various ADPCM codecs
                CODEC_ID_ADPCM_IMA_QT = CodecID54.CODEC_ID_ADPCM_IMA_QT;
                CODEC_ID_ADPCM_IMA_WAV = CodecID54.CODEC_ID_ADPCM_IMA_WAV;
                CODEC_ID_ADPCM_IMA_DK3 = CodecID54.CODEC_ID_ADPCM_IMA_DK3;
                CODEC_ID_ADPCM_IMA_DK4 = CodecID54.CODEC_ID_ADPCM_IMA_DK4;
                CODEC_ID_ADPCM_IMA_WS = CodecID54.CODEC_ID_ADPCM_IMA_WS;
                CODEC_ID_ADPCM_IMA_SMJPEG = CodecID54.CODEC_ID_ADPCM_IMA_SMJPEG;
                CODEC_ID_ADPCM_MS = CodecID54.CODEC_ID_ADPCM_MS;
                CODEC_ID_ADPCM_4XM = CodecID54.CODEC_ID_ADPCM_4XM;
                CODEC_ID_ADPCM_XA = CodecID54.CODEC_ID_ADPCM_XA;
                CODEC_ID_ADPCM_ADX = CodecID54.CODEC_ID_ADPCM_ADX;
                CODEC_ID_ADPCM_EA = CodecID54.CODEC_ID_ADPCM_EA;
                CODEC_ID_ADPCM_G726 = CodecID54.CODEC_ID_ADPCM_G726;
                CODEC_ID_ADPCM_CT = CodecID54.CODEC_ID_ADPCM_CT;
                CODEC_ID_ADPCM_SWF = CodecID54.CODEC_ID_ADPCM_SWF;
                CODEC_ID_ADPCM_YAMAHA = CodecID54.CODEC_ID_ADPCM_YAMAHA;
                CODEC_ID_ADPCM_SBPRO_4 = CodecID54.CODEC_ID_ADPCM_SBPRO_4;
                CODEC_ID_ADPCM_SBPRO_3 = CodecID54.CODEC_ID_ADPCM_SBPRO_3;
                CODEC_ID_ADPCM_SBPRO_2 = CodecID54.CODEC_ID_ADPCM_SBPRO_2;
                CODEC_ID_ADPCM_THP = CodecID54.CODEC_ID_ADPCM_THP;
                CODEC_ID_ADPCM_IMA_AMV = CodecID54.CODEC_ID_ADPCM_IMA_AMV;
                CODEC_ID_ADPCM_EA_R1 = CodecID54.CODEC_ID_ADPCM_EA_R1;
                CODEC_ID_ADPCM_EA_R3 = CodecID54.CODEC_ID_ADPCM_EA_R3;
                CODEC_ID_ADPCM_EA_R2 = CodecID54.CODEC_ID_ADPCM_EA_R2;
                CODEC_ID_ADPCM_IMA_EA_SEAD = CodecID54.CODEC_ID_ADPCM_IMA_EA_SEAD;
                CODEC_ID_ADPCM_IMA_EA_EACS = CodecID54.CODEC_ID_ADPCM_IMA_EA_EACS;
                CODEC_ID_ADPCM_EA_XAS = CodecID54.CODEC_ID_ADPCM_EA_XAS;
                CODEC_ID_ADPCM_EA_MAXIS_XA = CodecID54.CODEC_ID_ADPCM_EA_MAXIS_XA;
                CODEC_ID_ADPCM_IMA_ISS = CodecID54.CODEC_ID_ADPCM_IMA_ISS;
                CODEC_ID_ADPCM_G722 = CodecID54.CODEC_ID_ADPCM_G722;
                CODEC_ID_ADPCM_IMA_APC = CodecID54.CODEC_ID_ADPCM_IMA_APC;
                /// AMR
                CODEC_ID_AMR_NB = CodecID54.CODEC_ID_AMR_NB;
                CODEC_ID_AMR_WB = CodecID54.CODEC_ID_AMR_WB;
                /// RealAudio codecs
                CODEC_ID_RA_144 = CodecID54.CODEC_ID_RA_144;
                CODEC_ID_RA_288 = CodecID54.CODEC_ID_RA_288;
                /// various DPCM codecs
                CODEC_ID_ROQ_DPCM = CodecID54.CODEC_ID_ROQ_DPCM;
                CODEC_ID_INTERPLAY_DPCM = CodecID54.CODEC_ID_INTERPLAY_DPCM;
                CODEC_ID_XAN_DPCM = CodecID54.CODEC_ID_XAN_DPCM;
                CODEC_ID_SOL_DPCM = CodecID54.CODEC_ID_SOL_DPCM;
                /// audio codecs
                CODEC_ID_MP2 = CodecID54.CODEC_ID_MP2;
                CODEC_ID_MP3 = CodecID54.CODEC_ID_MP3;
                CODEC_ID_AAC = CodecID54.CODEC_ID_AAC;
                CODEC_ID_AC3 = CodecID54.CODEC_ID_AC3;
                CODEC_ID_DTS = CodecID54.CODEC_ID_DTS;
                CODEC_ID_VORBIS = CodecID54.CODEC_ID_VORBIS;
                CODEC_ID_DVAUDIO = CodecID54.CODEC_ID_DVAUDIO;
                CODEC_ID_WMAV1 = CodecID54.CODEC_ID_WMAV1;
                CODEC_ID_WMAV2 = CodecID54.CODEC_ID_WMAV2;
                CODEC_ID_MACE3 = CodecID54.CODEC_ID_MACE3;
                CODEC_ID_MACE6 = CodecID54.CODEC_ID_MACE6;
                CODEC_ID_VMDAUDIO = CodecID54.CODEC_ID_VMDAUDIO;
                CODEC_ID_SONIC = CodecID54.CODEC_ID_NONE;
                CODEC_ID_SONIC_LS = CodecID54.CODEC_ID_NONE;
                CODEC_ID_FLAC = CodecID54.CODEC_ID_FLAC;
                CODEC_ID_MP3ADU = CodecID54.CODEC_ID_MP3ADU;
                CODEC_ID_MP3ON4 = CodecID54.CODEC_ID_MP3ON4;
                CODEC_ID_SHORTEN = CodecID54.CODEC_ID_SHORTEN;
                CODEC_ID_ALAC = CodecID54.CODEC_ID_ALAC;
                CODEC_ID_WESTWOOD_SND1 = CodecID54.CODEC_ID_WESTWOOD_SND1;
                CODEC_ID_GSM = CodecID54.CODEC_ID_GSM;
                CODEC_ID_QDM2 = CodecID54.CODEC_ID_QDM2;
                CODEC_ID_COOK = CodecID54.CODEC_ID_COOK;
                CODEC_ID_TRUESPEECH = CodecID54.CODEC_ID_TRUESPEECH;
                CODEC_ID_TTA = CodecID54.CODEC_ID_TTA;
                CODEC_ID_SMACKAUDIO = CodecID54.CODEC_ID_SMACKAUDIO;
                CODEC_ID_QCELP = CodecID54.CODEC_ID_QCELP;
                CODEC_ID_WAVPACK = CodecID54.CODEC_ID_WAVPACK;
                CODEC_ID_DSICINAUDIO = CodecID54.CODEC_ID_DSICINAUDIO;
                CODEC_ID_IMC = CodecID54.CODEC_ID_IMC;
                CODEC_ID_MUSEPACK7 = CodecID54.CODEC_ID_MUSEPACK7;
                CODEC_ID_MLP = CodecID54.CODEC_ID_MLP;
                CODEC_ID_GSM_MS = CodecID54.CODEC_ID_GSM_MS;
                CODEC_ID_ATRAC3 = CodecID54.CODEC_ID_ATRAC3;
                CODEC_ID_VOXWARE = CodecID54.CODEC_ID_VOXWARE;
                CODEC_ID_APE = CodecID54.CODEC_ID_APE;
                CODEC_ID_NELLYMOSER = CodecID54.CODEC_ID_NELLYMOSER;
                CODEC_ID_MUSEPACK8 = CodecID54.CODEC_ID_MUSEPACK8;
                CODEC_ID_SPEEX = CodecID54.CODEC_ID_SPEEX;
                CODEC_ID_WMAVOICE = CodecID54.CODEC_ID_WMAVOICE;
                CODEC_ID_WMAPRO = CodecID54.CODEC_ID_WMAPRO;
                CODEC_ID_WMALOSSLESS = CodecID54.CODEC_ID_WMALOSSLESS;
                CODEC_ID_ATRAC3P = CodecID54.CODEC_ID_ATRAC3P;
                CODEC_ID_EAC3 = CodecID54.CODEC_ID_EAC3;
                CODEC_ID_SIPR = CodecID54.CODEC_ID_SIPR;
                CODEC_ID_MP1 = CodecID54.CODEC_ID_MP1;
                CODEC_ID_TWINVQ = CodecID54.CODEC_ID_TWINVQ;
                CODEC_ID_TRUEHD = CodecID54.CODEC_ID_TRUEHD;
                CODEC_ID_MP4ALS = CodecID54.CODEC_ID_MP4ALS;
                CODEC_ID_ATRAC1 = CodecID54.CODEC_ID_ATRAC1;
                CODEC_ID_BINKAUDIO_RDFT = CodecID54.CODEC_ID_BINKAUDIO_RDFT;
                CODEC_ID_BINKAUDIO_DCT = CodecID54.CODEC_ID_BINKAUDIO_DCT;
                CODEC_ID_AAC_LATM = CodecID54.CODEC_ID_AAC_LATM;
                CODEC_ID_QDMC = CodecID54.CODEC_ID_QDMC;
                CODEC_ID_CELT = CodecID54.CODEC_ID_CELT;
                CODEC_ID_G723_1 = CodecID54.CODEC_ID_G723_1;
                CODEC_ID_G729 = CodecID54.CODEC_ID_G729;
                CODEC_ID_8SVX_EXP = CodecID54.CODEC_ID_8SVX_EXP;
                CODEC_ID_8SVX_FIB = CodecID54.CODEC_ID_8SVX_FIB;
                CODEC_ID_BMV_AUDIO = CodecID54.CODEC_ID_BMV_AUDIO;
                /// subtitle codecs
                CODEC_ID_FIRST_SUBTITLE = CodecID54.CODEC_ID_FIRST_SUBTITLE;
                CODEC_ID_DVD_SUBTITLE = CodecID54.CODEC_ID_DVD_SUBTITLE;
                CODEC_ID_DVB_SUBTITLE = CodecID54.CODEC_ID_DVB_SUBTITLE;
                CODEC_ID_TEXT = CodecID54.CODEC_ID_TEXT;
                CODEC_ID_XSUB = CodecID54.CODEC_ID_XSUB;
                CODEC_ID_SSA = CodecID54.CODEC_ID_SSA;
                CODEC_ID_MOV_TEXT = CodecID54.CODEC_ID_MOV_TEXT;
                CODEC_ID_HDMV_PGS_SUBTITLE = CodecID54.CODEC_ID_HDMV_PGS_SUBTITLE;
                CODEC_ID_DVB_TELETEXT = CodecID54.CODEC_ID_DVB_TELETEXT;
                CODEC_ID_SRT = CodecID54.CODEC_ID_SRT;
                /// other specific kind of codecs (generally used for attachments)
                CODEC_ID_FIRST_UNKNOWN = CodecID54.CODEC_ID_FIRST_UNKNOWN;
                CODEC_ID_TTF = CodecID54.CODEC_ID_TTF;
                CODEC_ID_PROBE = CodecID54.CODEC_ID_PROBE;
                /// stream (only used by libavformat)
                CODEC_ID_MPEG2TS = CodecID54.CODEC_ID_MPEG2TS;
                // stream (only used by libavformat)
                CODEC_ID_MPEG4SYSTEMS = CodecID54.CODEC_ID_MPEG4SYSTEMS;
                CODEC_ID_FFMETADATA = CodecID54.CODEC_ID_FFMETADATA;
                break;
            default:
                CODEC_ID_NONE = 0;
                /// video codecs
                CODEC_ID_MPEG1VIDEO = 0;
                CODEC_ID_MPEG2VIDEO = 0;
                CODEC_ID_MPEG2VIDEO_XVMC = 0;
                CODEC_ID_H261 = 0;
                CODEC_ID_H263 = 0;
                CODEC_ID_RV10 = 0;
                CODEC_ID_RV20 = 0;
                CODEC_ID_MJPEG = 0;
                CODEC_ID_MJPEGB = 0;
                CODEC_ID_LJPEG = 0;
                CODEC_ID_SP5X = 0;
                CODEC_ID_JPEGLS = 0;
                CODEC_ID_MPEG4 = 0;
                CODEC_ID_RAWVIDEO = 0;
                CODEC_ID_MSMPEG4V1 = 0;
                CODEC_ID_MSMPEG4V2 = 0;
                CODEC_ID_MSMPEG4V3 = 0;
                CODEC_ID_WMV1 = 0;
                CODEC_ID_WMV2 = 0;
                CODEC_ID_H263P = 0;
                CODEC_ID_H263I = 0;
                CODEC_ID_FLV1 = 0;
                CODEC_ID_SVQ1 = 0;
                CODEC_ID_SVQ3 = 0;
                CODEC_ID_DVVIDEO = 0;
                CODEC_ID_HUFFYUV = 0;
                CODEC_ID_CYUV = 0;
                CODEC_ID_H264 = 0;
                CODEC_ID_INDEO3 = 0;
                CODEC_ID_VP3 = 0;
                CODEC_ID_THEORA = 0;
                CODEC_ID_ASV1 = 0;
                CODEC_ID_ASV2 = 0;
                CODEC_ID_FFV1 = 0;
                CODEC_ID_4XM = 0;
                CODEC_ID_VCR1 = 0;
                CODEC_ID_CLJR = 0;
                CODEC_ID_MDEC = 0;
                CODEC_ID_ROQ = 0;
                CODEC_ID_INTERPLAY_VIDEO = 0;
                CODEC_ID_XAN_WC3 = 0;
                CODEC_ID_XAN_WC4 = 0;
                CODEC_ID_RPZA = 0;
                CODEC_ID_CINEPAK = 0;
                CODEC_ID_WS_VQA = 0;
                CODEC_ID_MSRLE = 0;
                CODEC_ID_MSVIDEO1 = 0;
                CODEC_ID_IDCIN = 0;
                CODEC_ID_8BPS = 0;
                CODEC_ID_SMC = 0;
                CODEC_ID_FLIC = 0;
                CODEC_ID_TRUEMOTION1 = 0;
                CODEC_ID_VMDVIDEO = 0;
                CODEC_ID_MSZH = 0;
                CODEC_ID_ZLIB = 0;
                CODEC_ID_QTRLE = 0;
                CODEC_ID_SNOW = 0;
                CODEC_ID_TSCC = 0;
                CODEC_ID_ULTI = 0;
                CODEC_ID_QDRAW = 0;
                CODEC_ID_VIXL = 0;
                CODEC_ID_QPEG = 0;
                CODEC_ID_PNG = 0;
                CODEC_ID_PPM = 0;
                CODEC_ID_PBM = 0;
                CODEC_ID_PGM = 0;
                CODEC_ID_PGMYUV = 0;
                CODEC_ID_PAM = 0;
                CODEC_ID_FFVHUFF = 0;
                CODEC_ID_RV30 = 0;
                CODEC_ID_RV40 = 0;
                CODEC_ID_VC1 = 0;
                CODEC_ID_WMV3 = 0;
                CODEC_ID_LOCO = 0;
                CODEC_ID_WNV1 = 0;
                CODEC_ID_AASC = 0;
                CODEC_ID_INDEO2 = 0;
                CODEC_ID_FRAPS = 0;
                CODEC_ID_TRUEMOTION2 = 0;
                CODEC_ID_BMP = 0;
                CODEC_ID_CSCD = 0;
                CODEC_ID_MMVIDEO = 0;
                CODEC_ID_ZMBV = 0;
                CODEC_ID_AVS = 0;
                CODEC_ID_SMACKVIDEO = 0;
                CODEC_ID_NUV = 0;
                CODEC_ID_KMVC = 0;
                CODEC_ID_FLASHSV = 0;
                CODEC_ID_CAVS = 0;
                CODEC_ID_JPEG2000 = 0;
                CODEC_ID_VMNC = 0;
                CODEC_ID_VP5 = 0;
                CODEC_ID_VP6 = 0;
                CODEC_ID_VP6F = 0;
                CODEC_ID_TARGA = 0;
                CODEC_ID_DSICINVIDEO = 0;
                CODEC_ID_TIERTEXSEQVIDEO = 0;
                CODEC_ID_TIFF = 0;
                CODEC_ID_GIF = 0;
                CODEC_ID_FFH264 = 0;
                CODEC_ID_DXA = 0;
                CODEC_ID_DNXHD = 0;
                CODEC_ID_THP = 0;
                CODEC_ID_SGI = 0;
                CODEC_ID_C93 = 0;
                CODEC_ID_BETHSOFTVID = 0;
                CODEC_ID_PTX = 0;
                CODEC_ID_TXD = 0;
                CODEC_ID_VP6A = 0;
                CODEC_ID_AMV = 0;
                CODEC_ID_VB = 0;
                CODEC_ID_PCX = 0;
                CODEC_ID_SUNRAST = 0;
                CODEC_ID_INDEO4 = 0;
                CODEC_ID_INDEO5 = 0;
                CODEC_ID_MIMIC = 0;
                CODEC_ID_RL2 = 0;
                CODEC_ID_ESCAPE124 = 0;
                CODEC_ID_DIRAC = 0;
                CODEC_ID_BFI = 0;
                CODEC_ID_CMV = 0;
                CODEC_ID_MOTIONPIXELS = 0;
                CODEC_ID_TGV = 0;
                CODEC_ID_TGQ = 0;
                CODEC_ID_TQI = 0;
                CODEC_ID_AURA = 0;
                CODEC_ID_AURA2 = 0;
                CODEC_ID_V210X = 0;
                CODEC_ID_TMV = 0;
                CODEC_ID_V210 = 0;
                CODEC_ID_DPX = 0;
                CODEC_ID_MAD = 0;
                CODEC_ID_FRWU = 0;
                CODEC_ID_FLASHSV2 = 0;
                CODEC_ID_CDGRAPHICS = 0;
                CODEC_ID_R210 = 0;
                CODEC_ID_ANM = 0;
                CODEC_ID_BINKVIDEO = 0;
                CODEC_ID_IFF_ILBM = 0;
                CODEC_ID_IFF_BYTERUN1 = 0;
                CODEC_ID_KGV1 = 0;
                CODEC_ID_YOP = 0;
                CODEC_ID_VP8 = 0;
                CODEC_ID_PICTOR = 0;
                CODEC_ID_ANSI = 0;
                CODEC_ID_A64_MULTI = 0;
                CODEC_ID_A64_MULTI5 = 0;
                CODEC_ID_R10K = 0;
                CODEC_ID_MXPEG = 0;
                CODEC_ID_LAGARITH = 0;
                CODEC_ID_PRORES = 0;
                CODEC_ID_JV = 0;
                CODEC_ID_DFA = 0;
                CODEC_ID_WMV3IMAGE = 0;
                CODEC_ID_VC1IMAGE = 0;
                CODEC_ID_UTVIDEO = 0;
                CODEC_ID_BMV_VIDEO = 0;
                CODEC_ID_VBLE = 0;
                CODEC_ID_DXTORY = 0;
                CODEC_ID_V410 = 0;
                CODEC_ID_XWD = 0;
                CODEC_ID_CDXL = 0;
                /// various PCM "codecs"
                CODEC_ID_FIRST_AUDIO = 0;
                CODEC_ID_PCM_S16LE = 0;
                CODEC_ID_PCM_S16BE = 0;
                CODEC_ID_PCM_U16LE = 0;
                CODEC_ID_PCM_U16BE = 0;
                CODEC_ID_PCM_S8 = 0;
                CODEC_ID_PCM_U8 = 0;
                CODEC_ID_PCM_MULAW = 0;
                CODEC_ID_PCM_ALAW = 0;
                CODEC_ID_PCM_S32LE = 0;
                CODEC_ID_PCM_S32BE = 0;
                CODEC_ID_PCM_U32LE = 0;
                CODEC_ID_PCM_U32BE = 0;
                CODEC_ID_PCM_S24LE = 0;
                CODEC_ID_PCM_S24BE = 0;
                CODEC_ID_PCM_U24LE = 0;
                CODEC_ID_PCM_U24BE = 0;
                CODEC_ID_PCM_S24DAUD = 0;
                CODEC_ID_PCM_ZORK = 0;
                CODEC_ID_PCM_S16LE_PLANAR = 0;
                CODEC_ID_PCM_DVD = 0;
                CODEC_ID_PCM_F32BE = 0;
                CODEC_ID_PCM_F32LE = 0;
                CODEC_ID_PCM_F64BE = 0;
                CODEC_ID_PCM_F64LE = 0;
                CODEC_ID_PCM_BLURAY = 0;
                CODEC_ID_PCM_LXF = 0;
                CODEC_ID_S302M = 0;
                CODEC_ID_PCM_S8_PLANAR = 0;
                /// various ADPCM codecs
                CODEC_ID_ADPCM_IMA_QT = 0;
                CODEC_ID_ADPCM_IMA_WAV = 0;
                CODEC_ID_ADPCM_IMA_DK3 = 0;
                CODEC_ID_ADPCM_IMA_DK4 = 0;
                CODEC_ID_ADPCM_IMA_WS = 0;
                CODEC_ID_ADPCM_IMA_SMJPEG = 0;
                CODEC_ID_ADPCM_MS = 0;
                CODEC_ID_ADPCM_4XM = 0;
                CODEC_ID_ADPCM_XA = 0;
                CODEC_ID_ADPCM_ADX = 0;
                CODEC_ID_ADPCM_EA = 0;
                CODEC_ID_ADPCM_G726 = 0;
                CODEC_ID_ADPCM_CT = 0;
                CODEC_ID_ADPCM_SWF = 0;
                CODEC_ID_ADPCM_YAMAHA = 0;
                CODEC_ID_ADPCM_SBPRO_4 = 0;
                CODEC_ID_ADPCM_SBPRO_3 = 0;
                CODEC_ID_ADPCM_SBPRO_2 = 0;
                CODEC_ID_ADPCM_THP = 0;
                CODEC_ID_ADPCM_IMA_AMV = 0;
                CODEC_ID_ADPCM_EA_R1 = 0;
                CODEC_ID_ADPCM_EA_R3 = 0;
                CODEC_ID_ADPCM_EA_R2 = 0;
                CODEC_ID_ADPCM_IMA_EA_SEAD = 0;
                CODEC_ID_ADPCM_IMA_EA_EACS = 0;
                CODEC_ID_ADPCM_EA_XAS = 0;
                CODEC_ID_ADPCM_EA_MAXIS_XA = 0;
                CODEC_ID_ADPCM_IMA_ISS = 0;
                CODEC_ID_ADPCM_G722 = 0;
                CODEC_ID_ADPCM_IMA_APC = 0;
                /// AMR
                CODEC_ID_AMR_NB = 0;
                CODEC_ID_AMR_WB = 0;
                /// RealAudio codecs
                CODEC_ID_RA_144 = 0;
                CODEC_ID_RA_288 = 0;
                /// various DPCM codecs
                CODEC_ID_ROQ_DPCM = 0;
                CODEC_ID_INTERPLAY_DPCM = 0;
                CODEC_ID_XAN_DPCM = 0;
                CODEC_ID_SOL_DPCM = 0;
                /// audio codecs
                CODEC_ID_MP2 = 0;
                CODEC_ID_MP3 = 0;
                CODEC_ID_AAC = 0;
                CODEC_ID_AC3 = 0;
                CODEC_ID_DTS = 0;
                CODEC_ID_VORBIS = 0;
                CODEC_ID_DVAUDIO = 0;
                CODEC_ID_WMAV1 = 0;
                CODEC_ID_WMAV2 = 0;
                CODEC_ID_MACE3 = 0;
                CODEC_ID_MACE6 = 0;
                CODEC_ID_VMDAUDIO = 0;
                CODEC_ID_SONIC = 0;
                CODEC_ID_SONIC_LS = 0;
                CODEC_ID_FLAC = 0;
                CODEC_ID_MP3ADU = 0;
                CODEC_ID_MP3ON4 = 0;
                CODEC_ID_SHORTEN = 0;
                CODEC_ID_ALAC = 0;
                CODEC_ID_WESTWOOD_SND1 = 0;
                CODEC_ID_GSM = 0;
                CODEC_ID_QDM2 = 0;
                CODEC_ID_COOK = 0;
                CODEC_ID_TRUESPEECH = 0;
                CODEC_ID_TTA = 0;
                CODEC_ID_SMACKAUDIO = 0;
                CODEC_ID_QCELP = 0;
                CODEC_ID_WAVPACK = 0;
                CODEC_ID_DSICINAUDIO = 0;
                CODEC_ID_IMC = 0;
                CODEC_ID_MUSEPACK7 = 0;
                CODEC_ID_MLP = 0;
                CODEC_ID_GSM_MS = 0;
                CODEC_ID_ATRAC3 = 0;
                CODEC_ID_VOXWARE = 0;
                CODEC_ID_APE = 0;
                CODEC_ID_NELLYMOSER = 0;
                CODEC_ID_MUSEPACK8 = 0;
                CODEC_ID_SPEEX = 0;
                CODEC_ID_WMAVOICE = 0;
                CODEC_ID_WMAPRO = 0;
                CODEC_ID_WMALOSSLESS = 0;
                CODEC_ID_ATRAC3P = 0;
                CODEC_ID_EAC3 = 0;
                CODEC_ID_SIPR = 0;
                CODEC_ID_MP1 = 0;
                CODEC_ID_TWINVQ = 0;
                CODEC_ID_TRUEHD = 0;
                CODEC_ID_MP4ALS = 0;
                CODEC_ID_ATRAC1 = 0;
                CODEC_ID_BINKAUDIO_RDFT = 0;
                CODEC_ID_BINKAUDIO_DCT = 0;
                CODEC_ID_AAC_LATM = 0;
                CODEC_ID_QDMC = 0;
                CODEC_ID_CELT = 0;
                CODEC_ID_G723_1 = 0;
                CODEC_ID_G729 = 0;
                CODEC_ID_8SVX_EXP = 0;
                CODEC_ID_8SVX_FIB = 0;
                CODEC_ID_BMV_AUDIO = 0;
                /// subtitle codecs
                CODEC_ID_FIRST_SUBTITLE = 0;
                CODEC_ID_DVD_SUBTITLE = 0;
                CODEC_ID_DVB_SUBTITLE = 0;
                CODEC_ID_TEXT = 0;
                CODEC_ID_XSUB = 0;
                CODEC_ID_SSA = 0;
                CODEC_ID_MOV_TEXT = 0;
                CODEC_ID_HDMV_PGS_SUBTITLE = 0;
                CODEC_ID_DVB_TELETEXT = 0;
                CODEC_ID_SRT = 0;
                /// other specific kind of codecs (generally used for attachments)
                CODEC_ID_FIRST_UNKNOWN = 0;
                CODEC_ID_TTF = 0;
                CODEC_ID_PROBE = 0;
                /// stream (only used by libavformat)
                CODEC_ID_MPEG2TS = 0;
                // stream (only used by libavformat)
                CODEC_ID_MPEG4SYSTEMS = 0;
                CODEC_ID_FFMETADATA = 0;
                break;
        }
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param codec pointer to an AVCodec struct
     * @return codec wrapper
     */
    public ICodecWrapper wrap(Pointer codec) {
        switch (libWrapper.getMajorVersion()) {
            case 53: return wrap(new AVCodec53(codec));
            case 54: return wrap(new AVCodec54(codec));
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param codec AVCodec struct
     * @return codec wrapper
     */
    public ICodecWrapper wrap(AVCodec53 codec) {
        return new CodecWrapper53(codec);
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param codec AVCodec struct
     * @return codec wrapper
     */
    public ICodecWrapper wrap(AVCodec54 codec) {
        return new CodecWrapper54(codec);
    }
    
    /**
     * Find decoder by its ID.
     * 
     * @param codecId a codec ID
     * @return decoder
     * @throws LibavException if there is no decoder for the given codec ID
     */
    public ICodecWrapper findDecoder(int codecId) throws LibavException {
        switch (libWrapper.getMajorVersion()) {
            case 53: return CodecWrapper53.findDecoder(codecId);
            case 54: return CodecWrapper54.findDecoder(codecId);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Find encoder by its ID.
     * 
     * @param codecId a codec ID
     * @return encoder
     * @throws LibavException if there is no encoder for the given codec ID
     */
    public ICodecWrapper findEncoder(int codecId) throws LibavException {
        switch (libWrapper.getMajorVersion()) {
            case 53: return CodecWrapper53.findEncoder(codecId);
            case 54: return CodecWrapper54.findEncoder(codecId);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Find decoder by its name.
     * 
     * @param name a codec name
     * @return decoder
     * @throws LibavException if there is no decoder with the given name
     */
    public ICodecWrapper findDecoderByName(String name) throws LibavException {
        switch (libWrapper.getMajorVersion()) {
            case 53: return CodecWrapper53.findDecoderByName(name);
            case 54: return CodecWrapper54.findDecoderByName(name);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Find encoder by its name.
     * 
     * @param name a codec name
     * @return encoder
     * @throws LibavException if there is no encoder with the given name
     */
    public ICodecWrapper findEncoderByName(String name) throws LibavException {
        switch (libWrapper.getMajorVersion()) {
            case 53: return CodecWrapper53.findEncoderByName(name);
            case 54: return CodecWrapper54.findEncoderByName(name);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static CodecWrapperFactory getInstance() {
        return instance;
    }
    
}

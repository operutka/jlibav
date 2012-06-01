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

/**
 * Mirror of the native CodecID enum for the libavcodec v54.x.x. For details 
 * see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public interface CodecID54 {
    
    public static final int CODEC_ID_NONE = (int)0;
    
    /// video codecs
    public static final int CODEC_ID_MPEG1VIDEO = (int)1;
    public static final int CODEC_ID_MPEG2VIDEO = (int)2;
    public static final int CODEC_ID_MPEG2VIDEO_XVMC = (int)3;
    public static final int CODEC_ID_H261 = (int)4;
    public static final int CODEC_ID_H263 = (int)5;
    public static final int CODEC_ID_RV10 = (int)6;
    public static final int CODEC_ID_RV20 = (int)7;
    public static final int CODEC_ID_MJPEG = (int)8;
    public static final int CODEC_ID_MJPEGB = (int)9;
    public static final int CODEC_ID_LJPEG = (int)10;
    public static final int CODEC_ID_SP5X = (int)11;
    public static final int CODEC_ID_JPEGLS = (int)12;
    public static final int CODEC_ID_MPEG4 = (int)13;
    public static final int CODEC_ID_RAWVIDEO = (int)14;
    public static final int CODEC_ID_MSMPEG4V1 = (int)15;
    public static final int CODEC_ID_MSMPEG4V2 = (int)16;
    public static final int CODEC_ID_MSMPEG4V3 = (int)17;
    public static final int CODEC_ID_WMV1 = (int)18;
    public static final int CODEC_ID_WMV2 = (int)19;
    public static final int CODEC_ID_H263P = (int)20;
    public static final int CODEC_ID_H263I = (int)21;
    public static final int CODEC_ID_FLV1 = (int)22;
    public static final int CODEC_ID_SVQ1 = (int)23;
    public static final int CODEC_ID_SVQ3 = (int)24;
    public static final int CODEC_ID_DVVIDEO = (int)25;
    public static final int CODEC_ID_HUFFYUV = (int)26;
    public static final int CODEC_ID_CYUV = (int)27;
    public static final int CODEC_ID_H264 = (int)28;
    public static final int CODEC_ID_INDEO3 = (int)29;
    public static final int CODEC_ID_VP3 = (int)30;
    public static final int CODEC_ID_THEORA = (int)31;
    public static final int CODEC_ID_ASV1 = (int)32;
    public static final int CODEC_ID_ASV2 = (int)33;
    public static final int CODEC_ID_FFV1 = (int)34;
    public static final int CODEC_ID_4XM = (int)35;
    public static final int CODEC_ID_VCR1 = (int)36;
    public static final int CODEC_ID_CLJR = (int)37;
    public static final int CODEC_ID_MDEC = (int)38;
    public static final int CODEC_ID_ROQ = (int)39;
    public static final int CODEC_ID_INTERPLAY_VIDEO = (int)40;
    public static final int CODEC_ID_XAN_WC3 = (int)41;
    public static final int CODEC_ID_XAN_WC4 = (int)42;
    public static final int CODEC_ID_RPZA = (int)43;
    public static final int CODEC_ID_CINEPAK = (int)44;
    public static final int CODEC_ID_WS_VQA = (int)45;
    public static final int CODEC_ID_MSRLE = (int)46;
    public static final int CODEC_ID_MSVIDEO1 = (int)47;
    public static final int CODEC_ID_IDCIN = (int)48;
    public static final int CODEC_ID_8BPS = (int)49;
    public static final int CODEC_ID_SMC = (int)50;
    public static final int CODEC_ID_FLIC = (int)51;
    public static final int CODEC_ID_TRUEMOTION1 = (int)52;
    public static final int CODEC_ID_VMDVIDEO = (int)53;
    public static final int CODEC_ID_MSZH = (int)54;
    public static final int CODEC_ID_ZLIB = (int)55;
    public static final int CODEC_ID_QTRLE = (int)56;
    public static final int CODEC_ID_SNOW = (int)57;
    public static final int CODEC_ID_TSCC = (int)58;
    public static final int CODEC_ID_ULTI = (int)59;
    public static final int CODEC_ID_QDRAW = (int)60;
    public static final int CODEC_ID_VIXL = (int)61;
    public static final int CODEC_ID_QPEG = (int)62;
    public static final int CODEC_ID_PNG = (int)63;
    public static final int CODEC_ID_PPM = (int)64;
    public static final int CODEC_ID_PBM = (int)65;
    public static final int CODEC_ID_PGM = (int)66;
    public static final int CODEC_ID_PGMYUV = (int)67;
    public static final int CODEC_ID_PAM = (int)68;
    public static final int CODEC_ID_FFVHUFF = (int)69;
    public static final int CODEC_ID_RV30 = (int)70;
    public static final int CODEC_ID_RV40 = (int)71;
    public static final int CODEC_ID_VC1 = (int)72;
    public static final int CODEC_ID_WMV3 = (int)73;
    public static final int CODEC_ID_LOCO = (int)74;
    public static final int CODEC_ID_WNV1 = (int)75;
    public static final int CODEC_ID_AASC = (int)76;
    public static final int CODEC_ID_INDEO2 = (int)77;
    public static final int CODEC_ID_FRAPS = (int)78;
    public static final int CODEC_ID_TRUEMOTION2 = (int)79;
    public static final int CODEC_ID_BMP = (int)80;
    public static final int CODEC_ID_CSCD = (int)81;
    public static final int CODEC_ID_MMVIDEO = (int)82;
    public static final int CODEC_ID_ZMBV = (int)83;
    public static final int CODEC_ID_AVS = (int)84;
    public static final int CODEC_ID_SMACKVIDEO = (int)85;
    public static final int CODEC_ID_NUV = (int)86;
    public static final int CODEC_ID_KMVC = (int)87;
    public static final int CODEC_ID_FLASHSV = (int)88;
    public static final int CODEC_ID_CAVS = (int)89;
    public static final int CODEC_ID_JPEG2000 = (int)90;
    public static final int CODEC_ID_VMNC = (int)91;
    public static final int CODEC_ID_VP5 = (int)92;
    public static final int CODEC_ID_VP6 = (int)93;
    public static final int CODEC_ID_VP6F = (int)94;
    public static final int CODEC_ID_TARGA = (int)95;
    public static final int CODEC_ID_DSICINVIDEO = (int)96;
    public static final int CODEC_ID_TIERTEXSEQVIDEO = (int)97;
    public static final int CODEC_ID_TIFF = (int)98;
    public static final int CODEC_ID_GIF = (int)99;
    public static final int CODEC_ID_DXA = (int)100;
    public static final int CODEC_ID_DNXHD = (int)101;
    public static final int CODEC_ID_THP = (int)102;
    public static final int CODEC_ID_SGI = (int)103;
    public static final int CODEC_ID_C93 = (int)104;
    public static final int CODEC_ID_BETHSOFTVID = (int)105;
    public static final int CODEC_ID_PTX = (int)106;
    public static final int CODEC_ID_TXD = (int)107;
    public static final int CODEC_ID_VP6A = (int)108;
    public static final int CODEC_ID_AMV = (int)109;
    public static final int CODEC_ID_VB = (int)110;
    public static final int CODEC_ID_PCX = (int)111;
    public static final int CODEC_ID_SUNRAST = (int)112;
    public static final int CODEC_ID_INDEO4 = (int)113;
    public static final int CODEC_ID_INDEO5 = (int)114;
    public static final int CODEC_ID_MIMIC = (int)115;
    public static final int CODEC_ID_RL2 = (int)116;
    public static final int CODEC_ID_ESCAPE124 = (int)117;
    public static final int CODEC_ID_DIRAC = (int)118;
    public static final int CODEC_ID_BFI = (int)119;
    public static final int CODEC_ID_CMV = (int)120;
    public static final int CODEC_ID_MOTIONPIXELS = (int)121;
    public static final int CODEC_ID_TGV = (int)122;
    public static final int CODEC_ID_TGQ = (int)123;
    public static final int CODEC_ID_TQI = (int)124;
    public static final int CODEC_ID_AURA = (int)125;
    public static final int CODEC_ID_AURA2 = (int)126;
    public static final int CODEC_ID_V210X = (int)127;
    public static final int CODEC_ID_TMV = (int)128;
    public static final int CODEC_ID_V210 = (int)129;
    public static final int CODEC_ID_DPX = (int)130;
    public static final int CODEC_ID_MAD = (int)131;
    public static final int CODEC_ID_FRWU = (int)132;
    public static final int CODEC_ID_FLASHSV2 = (int)133;
    public static final int CODEC_ID_CDGRAPHICS = (int)134;
    public static final int CODEC_ID_R210 = (int)135;
    public static final int CODEC_ID_ANM = (int)136;
    public static final int CODEC_ID_BINKVIDEO = (int)137;
    public static final int CODEC_ID_IFF_ILBM = (int)138;
    public static final int CODEC_ID_IFF_BYTERUN1 = (int)139;
    public static final int CODEC_ID_KGV1 = (int)140;
    public static final int CODEC_ID_YOP = (int)141;
    public static final int CODEC_ID_VP8 = (int)142;
    public static final int CODEC_ID_PICTOR = (int)143;
    public static final int CODEC_ID_ANSI = (int)144;
    public static final int CODEC_ID_A64_MULTI = (int)145;
    public static final int CODEC_ID_A64_MULTI5 = (int)146;
    public static final int CODEC_ID_R10K = (int)147;
    public static final int CODEC_ID_MXPEG = (int)148;
    public static final int CODEC_ID_LAGARITH = (int)149;
    public static final int CODEC_ID_PRORES = (int)150;
    public static final int CODEC_ID_JV = (int)151;
    public static final int CODEC_ID_DFA = (int)152;
    public static final int CODEC_ID_WMV3IMAGE = (int)153;
    public static final int CODEC_ID_VC1IMAGE = (int)154;
    public static final int CODEC_ID_UTVIDEO = (int)155;
    public static final int CODEC_ID_BMV_VIDEO = (int)156;
    public static final int CODEC_ID_VBLE = (int)157;
    public static final int CODEC_ID_DXTORY = (int)158;
    public static final int CODEC_ID_V410 = (int)159;
    public static final int CODEC_ID_XWD = (int)160;
    public static final int CODEC_ID_CDXL = (int)161;
    
    /// various PCM "codecs"
    public static final int CODEC_ID_FIRST_AUDIO = (int)0x10000;
    public static final int CODEC_ID_PCM_S16LE = (int)0x10000;
    public static final int CODEC_ID_PCM_S16BE = (int)65537;
    public static final int CODEC_ID_PCM_U16LE = (int)65538;
    public static final int CODEC_ID_PCM_U16BE = (int)65539;
    public static final int CODEC_ID_PCM_S8 = (int)65540;
    public static final int CODEC_ID_PCM_U8 = (int)65541;
    public static final int CODEC_ID_PCM_MULAW = (int)65542;
    public static final int CODEC_ID_PCM_ALAW = (int)65543;
    public static final int CODEC_ID_PCM_S32LE = (int)65544;
    public static final int CODEC_ID_PCM_S32BE = (int)65545;
    public static final int CODEC_ID_PCM_U32LE = (int)65546;
    public static final int CODEC_ID_PCM_U32BE = (int)65547;
    public static final int CODEC_ID_PCM_S24LE = (int)65548;
    public static final int CODEC_ID_PCM_S24BE = (int)65549;
    public static final int CODEC_ID_PCM_U24LE = (int)65550;
    public static final int CODEC_ID_PCM_U24BE = (int)65551;
    public static final int CODEC_ID_PCM_S24DAUD = (int)65552;
    public static final int CODEC_ID_PCM_ZORK = (int)65553;
    public static final int CODEC_ID_PCM_S16LE_PLANAR = (int)65554;
    public static final int CODEC_ID_PCM_DVD = (int)65555;
    public static final int CODEC_ID_PCM_F32BE = (int)65556;
    public static final int CODEC_ID_PCM_F32LE = (int)65557;
    public static final int CODEC_ID_PCM_F64BE = (int)65558;
    public static final int CODEC_ID_PCM_F64LE = (int)65559;
    public static final int CODEC_ID_PCM_BLURAY = (int)65560;
    public static final int CODEC_ID_PCM_LXF = (int)65561;
    public static final int CODEC_ID_S302M = (int)65562;
    public static final int CODEC_ID_PCM_S8_PLANAR = (int)65563;
    
    /// various ADPCM codecs
    public static final int CODEC_ID_ADPCM_IMA_QT = (int)0x11000;
    public static final int CODEC_ID_ADPCM_IMA_WAV = (int)69633;
    public static final int CODEC_ID_ADPCM_IMA_DK3 = (int)69634;
    public static final int CODEC_ID_ADPCM_IMA_DK4 = (int)69635;
    public static final int CODEC_ID_ADPCM_IMA_WS = (int)69636;
    public static final int CODEC_ID_ADPCM_IMA_SMJPEG = (int)69637;
    public static final int CODEC_ID_ADPCM_MS = (int)69638;
    public static final int CODEC_ID_ADPCM_4XM = (int)69639;
    public static final int CODEC_ID_ADPCM_XA = (int)69640;
    public static final int CODEC_ID_ADPCM_ADX = (int)69641;
    public static final int CODEC_ID_ADPCM_EA = (int)69642;
    public static final int CODEC_ID_ADPCM_G726 = (int)69643;
    public static final int CODEC_ID_ADPCM_CT = (int)69644;
    public static final int CODEC_ID_ADPCM_SWF = (int)69645;
    public static final int CODEC_ID_ADPCM_YAMAHA = (int)69646;
    public static final int CODEC_ID_ADPCM_SBPRO_4 = (int)69647;
    public static final int CODEC_ID_ADPCM_SBPRO_3 = (int)69648;
    public static final int CODEC_ID_ADPCM_SBPRO_2 = (int)69649;
    public static final int CODEC_ID_ADPCM_THP = (int)69650;
    public static final int CODEC_ID_ADPCM_IMA_AMV = (int)69651;
    public static final int CODEC_ID_ADPCM_EA_R1 = (int)69652;
    public static final int CODEC_ID_ADPCM_EA_R3 = (int)69653;
    public static final int CODEC_ID_ADPCM_EA_R2 = (int)69654;
    public static final int CODEC_ID_ADPCM_IMA_EA_SEAD = (int)69655;
    public static final int CODEC_ID_ADPCM_IMA_EA_EACS = (int)69656;
    public static final int CODEC_ID_ADPCM_EA_XAS = (int)69657;
    public static final int CODEC_ID_ADPCM_EA_MAXIS_XA = (int)69658;
    public static final int CODEC_ID_ADPCM_IMA_ISS = (int)69659;
    public static final int CODEC_ID_ADPCM_G722 = (int)69660;
    public static final int CODEC_ID_ADPCM_IMA_APC = (int)69661;
    
    /// AMR
    public static final int CODEC_ID_AMR_NB = (int)0x12000;
    public static final int CODEC_ID_AMR_WB = (int)73729;
    
    /// RealAudio codecs
    public static final int CODEC_ID_RA_144 = (int)0x13000;
    public static final int CODEC_ID_RA_288 = (int)77825;
    
    /// various DPCM codecs
    public static final int CODEC_ID_ROQ_DPCM = (int)0x14000;
    public static final int CODEC_ID_INTERPLAY_DPCM = (int)81921;
    public static final int CODEC_ID_XAN_DPCM = (int)81922;
    public static final int CODEC_ID_SOL_DPCM = (int)81923;
    
    /// audio codecs
    public static final int CODEC_ID_MP2 = (int)0x15000;
    public static final int CODEC_ID_MP3 = (int)86017;
    public static final int CODEC_ID_AAC = (int)86018;
    public static final int CODEC_ID_AC3 = (int)86019;
    public static final int CODEC_ID_DTS = (int)86020;
    public static final int CODEC_ID_VORBIS = (int)86021;
    public static final int CODEC_ID_DVAUDIO = (int)86022;
    public static final int CODEC_ID_WMAV1 = (int)86023;
    public static final int CODEC_ID_WMAV2 = (int)86024;
    public static final int CODEC_ID_MACE3 = (int)86025;
    public static final int CODEC_ID_MACE6 = (int)86026;
    public static final int CODEC_ID_VMDAUDIO = (int)86027;
    public static final int CODEC_ID_FLAC = (int)86028;
    public static final int CODEC_ID_MP3ADU = (int)86029;
    public static final int CODEC_ID_MP3ON4 = (int)86030;
    public static final int CODEC_ID_SHORTEN = (int)86031;
    public static final int CODEC_ID_ALAC = (int)86032;
    public static final int CODEC_ID_WESTWOOD_SND1 = (int)86033;
    public static final int CODEC_ID_GSM = (int)86034;
    public static final int CODEC_ID_QDM2 = (int)86035;
    public static final int CODEC_ID_COOK = (int)86036;
    public static final int CODEC_ID_TRUESPEECH = (int)86037;
    public static final int CODEC_ID_TTA = (int)86038;
    public static final int CODEC_ID_SMACKAUDIO = (int)86039;
    public static final int CODEC_ID_QCELP = (int)86040;
    public static final int CODEC_ID_WAVPACK = (int)86041;
    public static final int CODEC_ID_DSICINAUDIO = (int)86042;
    public static final int CODEC_ID_IMC = (int)86043;
    public static final int CODEC_ID_MUSEPACK7 = (int)86044;
    public static final int CODEC_ID_MLP = (int)86045;
    public static final int CODEC_ID_GSM_MS = (int)86046;
    public static final int CODEC_ID_ATRAC3 = (int)86047;
    public static final int CODEC_ID_VOXWARE = (int)86048;
    public static final int CODEC_ID_APE = (int)86049;
    public static final int CODEC_ID_NELLYMOSER = (int)86050;
    public static final int CODEC_ID_MUSEPACK8 = (int)86051;
    public static final int CODEC_ID_SPEEX = (int)86052;
    public static final int CODEC_ID_WMAVOICE = (int)86053;
    public static final int CODEC_ID_WMAPRO = (int)86054;
    public static final int CODEC_ID_WMALOSSLESS = (int)86055;
    public static final int CODEC_ID_ATRAC3P = (int)86056;
    public static final int CODEC_ID_EAC3 = (int)86057;
    public static final int CODEC_ID_SIPR = (int)86058;
    public static final int CODEC_ID_MP1 = (int)86059;
    public static final int CODEC_ID_TWINVQ = (int)86060;
    public static final int CODEC_ID_TRUEHD = (int)86061;
    public static final int CODEC_ID_MP4ALS = (int)86062;
    public static final int CODEC_ID_ATRAC1 = (int)86063;
    public static final int CODEC_ID_BINKAUDIO_RDFT = (int)86064;
    public static final int CODEC_ID_BINKAUDIO_DCT = (int)86065;
    public static final int CODEC_ID_AAC_LATM = (int)86066;
    public static final int CODEC_ID_QDMC = (int)86067;
    public static final int CODEC_ID_CELT = (int)86068;
    public static final int CODEC_ID_G723_1 = (int)86069;
    public static final int CODEC_ID_G729 = (int)86070;
    public static final int CODEC_ID_8SVX_EXP = (int)86071;
    public static final int CODEC_ID_8SVX_FIB = (int)86072;
    public static final int CODEC_ID_BMV_AUDIO = (int)86073;
    
    /// subtitle codecs
    public static final int CODEC_ID_FIRST_SUBTITLE = (int)0x17000;
    public static final int CODEC_ID_DVD_SUBTITLE = (int)0x17000;
    public static final int CODEC_ID_DVB_SUBTITLE = (int)94209;
    public static final int CODEC_ID_TEXT = (int)94210;
    public static final int CODEC_ID_XSUB = (int)94211;
    public static final int CODEC_ID_SSA = (int)94212;
    public static final int CODEC_ID_MOV_TEXT = (int)94213;
    public static final int CODEC_ID_HDMV_PGS_SUBTITLE = (int)94214;
    public static final int CODEC_ID_DVB_TELETEXT = (int)94215;
    public static final int CODEC_ID_SRT = (int)94216;
    
    /// other specific kind of codecs (generally used for attachments)
    public static final int CODEC_ID_FIRST_UNKNOWN = (int)0x18000;
    public static final int CODEC_ID_TTF = (int)0x18000;
    public static final int CODEC_ID_PROBE = (int)0x19000;
    
    /// stream (only used by libavformat)
    public static final int CODEC_ID_MPEG2TS = (int)0x20000;
    
    // stream (only used by libavformat)
    public static final int CODEC_ID_MPEG4SYSTEMS = (int)0x20001;
    public static final int CODEC_ID_FFMETADATA = (int)0x21000;
    
}

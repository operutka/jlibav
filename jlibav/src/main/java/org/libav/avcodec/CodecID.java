/*
 * Copyright (C) 2013 Ondrej Perutka
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avcodec.bridge.CodecID53;
import org.libav.avcodec.bridge.CodecID54;
import org.libav.avcodec.bridge.CodecID55;
import org.libav.bridge.LibraryManager;

/**
 * Codec ID enum.
 * 
 * @author Ondrej Perutka
 */
public enum CodecID {
    
    NONE("CODEC_ID_NONE"),

    // video codecs
    MPEG1VIDEO("CODEC_ID_MPEG1VIDEO"),
    MPEG2VIDEO("CODEC_ID_MPEG2VIDEO"), // preferred ID for MPEG-1/2 video decoding
    MPEG2VIDEO_XVMC("CODEC_ID_MPEG2VIDEO_XVMC"),
    H261("CODEC_ID_H261"),
    H263("CODEC_ID_H263"),
    RV10("CODEC_ID_RV10"),
    RV20("CODEC_ID_RV20"),
    MJPEG("CODEC_ID_MJPEG"),
    MJPEGB("CODEC_ID_MJPEGB"),
    LJPEG("CODEC_ID_LJPEG"),
    SP5X("CODEC_ID_SP5X"),
    JPEGLS("CODEC_ID_JPEGLS"),
    MPEG4("CODEC_ID_MPEG4"),
    RAWVIDEO("CODEC_ID_RAWVIDEO"),
    MSMPEG4V1("CODEC_ID_MSMPEG4V1"),
    MSMPEG4V2("CODEC_ID_MSMPEG4V2"),
    MSMPEG4V3("CODEC_ID_MSMPEG4V3"),
    WMV1("CODEC_ID_WMV1"),
    WMV2("CODEC_ID_WMV2"),
    H263P("CODEC_ID_H263P"),
    H263I("CODEC_ID_H263I"),
    FLV1("CODEC_ID_FLV1"),
    SVQ1("CODEC_ID_SVQ1"),
    SVQ3("CODEC_ID_SVQ3"),
    DVVIDEO("CODEC_ID_DVVIDEO"),
    HUFFYUV("CODEC_ID_HUFFYUV"),
    CYUV("CODEC_ID_CYUV"),
    H264("CODEC_ID_H264"),
    INDEO3("CODEC_ID_INDEO3"),
    VP3("CODEC_ID_VP3"),
    THEORA("CODEC_ID_THEORA"),
    ASV1("CODEC_ID_ASV1"),
    ASV2("CODEC_ID_ASV2"),
    FFV1("CODEC_ID_FFV1"),
    _4XM("CODEC_ID_4XM"),
    VCR1("CODEC_ID_VCR1"),
    CLJR("CODEC_ID_CLJR"),
    MDEC("CODEC_ID_MDEC"),
    ROQ("CODEC_ID_ROQ"),
    INTERPLAY_VIDEO("CODEC_ID_INTERPLAY_VIDEO"),
    XAN_WC3("CODEC_ID_XAN_WC3"),
    XAN_WC4("CODEC_ID_XAN_WC4"),
    RPZA("CODEC_ID_RPZA"),
    CINEPAK("CODEC_ID_CINEPAK"),
    WS_VQA("CODEC_ID_WS_VQA"),
    MSRLE("CODEC_ID_MSRLE"),
    MSVIDEO1("CODEC_ID_MSVIDEO1"),
    IDCIN("CODEC_ID_IDCIN"),
    _8BPS("CODEC_ID_8BPS"),
    SMC("CODEC_ID_SMC"),
    FLIC("CODEC_ID_FLIC"),
    TRUEMOTION1("CODEC_ID_TRUEMOTION1"),
    VMDVIDEO("CODEC_ID_VMDVIDEO"),
    MSZH("CODEC_ID_MSZH"),
    ZLIB("CODEC_ID_ZLIB"),
    QTRLE("CODEC_ID_QTRLE"),
    TSCC("CODEC_ID_TSCC"),
    ULTI("CODEC_ID_ULTI"),
    QDRAW("CODEC_ID_QDRAW"),
    VIXL("CODEC_ID_VIXL"),
    QPEG("CODEC_ID_QPEG"),
    PNG("CODEC_ID_PNG"),
    PPM("CODEC_ID_PPM"),
    PBM("CODEC_ID_PBM"),
    PGM("CODEC_ID_PGM"),
    PGMYUV("CODEC_ID_PGMYUV"),
    PAM("CODEC_ID_PAM"),
    FFVHUFF("CODEC_ID_FFVHUFF"),
    RV30("CODEC_ID_RV30"),
    RV40("CODEC_ID_RV40"),
    VC1("CODEC_ID_VC1"),
    WMV3("CODEC_ID_WMV3"),
    LOCO("CODEC_ID_LOCO"),
    WNV1("CODEC_ID_WNV1"),
    AASC("CODEC_ID_AASC"),
    INDEO2("CODEC_ID_INDEO2"),
    FRAPS("CODEC_ID_FRAPS"),
    TRUEMOTION2("CODEC_ID_TRUEMOTION2"),
    BMP("CODEC_ID_BMP"),
    CSCD("CODEC_ID_CSCD"),
    MMVIDEO("CODEC_ID_MMVIDEO"),
    ZMBV("CODEC_ID_ZMBV"),
    AVS("CODEC_ID_AVS"),
    SMACKVIDEO("CODEC_ID_SMACKVIDEO"),
    NUV("CODEC_ID_NUV"),
    KMVC("CODEC_ID_KMVC"),
    FLASHSV("CODEC_ID_FLASHSV"),
    CAVS("CODEC_ID_CAVS"),
    JPEG2000("CODEC_ID_JPEG2000"),
    VMNC("CODEC_ID_VMNC"),
    VP5("CODEC_ID_VP5"),
    VP6("CODEC_ID_VP6"),
    VP6F("CODEC_ID_VP6F"),
    TARGA("CODEC_ID_TARGA"),
    DSICINVIDEO("CODEC_ID_DSICINVIDEO"),
    TIERTEXSEQVIDEO("CODEC_ID_TIERTEXSEQVIDEO"),
    TIFF("CODEC_ID_TIFF"),
    GIF("CODEC_ID_GIF"),
    DXA("CODEC_ID_DXA"),
    DNXHD("CODEC_ID_DNXHD"),
    THP("CODEC_ID_THP"),
    SGI("CODEC_ID_SGI"),
    C93("CODEC_ID_C93"),
    BETHSOFTVID("CODEC_ID_BETHSOFTVID"),
    PTX("CODEC_ID_PTX"),
    TXD("CODEC_ID_TXD"),
    VP6A("CODEC_ID_VP6A"),
    AMV("CODEC_ID_AMV"),
    VB("CODEC_ID_VB"),
    PCX("CODEC_ID_PCX"),
    SUNRAST("CODEC_ID_SUNRAST"),
    INDEO4("CODEC_ID_INDEO4"),
    INDEO5("CODEC_ID_INDEO5"),
    MIMIC("CODEC_ID_MIMIC"),
    RL2("CODEC_ID_RL2"),
    ESCAPE124("CODEC_ID_ESCAPE124"),
    DIRAC("CODEC_ID_DIRAC"),
    BFI("CODEC_ID_BFI"),
    CMV("CODEC_ID_CMV"),
    MOTIONPIXELS("CODEC_ID_MOTIONPIXELS"),
    TGV("CODEC_ID_TGV"),
    TGQ("CODEC_ID_TGQ"),
    TQI("CODEC_ID_TQI"),
    AURA("CODEC_ID_AURA"),
    AURA2("CODEC_ID_AURA2"),
    V210X("CODEC_ID_V210X"),
    TMV("CODEC_ID_TMV"),
    V210("CODEC_ID_V210"),
    DPX("CODEC_ID_DPX"),
    MAD("CODEC_ID_MAD"),
    FRWU("CODEC_ID_FRWU"),
    FLASHSV2("CODEC_ID_FLASHSV2"),
    CDGRAPHICS("CODEC_ID_CDGRAPHICS"),
    R210("CODEC_ID_R210"),
    ANM("CODEC_ID_ANM"),
    BINKVIDEO("CODEC_ID_BINKVIDEO"),
    IFF_ILBM("CODEC_ID_IFF_ILBM"),
    IFF_BYTERUN1("CODEC_ID_IFF_BYTERUN1"),
    KGV1("CODEC_ID_KGV1"),
    YOP("CODEC_ID_YOP"),
    VP8("CODEC_ID_VP8"),
    PICTOR("CODEC_ID_PICTOR"),
    ANSI("CODEC_ID_ANSI"),
    A64_MULTI("CODEC_ID_A64_MULTI"),
    A64_MULTI5("CODEC_ID_A64_MULTI5"),
    R10K("CODEC_ID_R10K"),
    MXPEG("CODEC_ID_MXPEG"),
    LAGARITH("CODEC_ID_LAGARITH"),
    PRORES("CODEC_ID_PRORES"),
    JV("CODEC_ID_JV"),
    DFA("CODEC_ID_DFA"),
    WMV3IMAGE("CODEC_ID_WMV3IMAGE"),
    VC1IMAGE("CODEC_ID_VC1IMAGE"),
    UTVIDEO("CODEC_ID_UTVIDEO"),
    BMV_VIDEO("CODEC_ID_BMV_VIDEO"),
    VBLE("CODEC_ID_VBLE"),
    DXTORY("CODEC_ID_DXTORY"),
    V410("CODEC_ID_V410"),
    XWD("CODEC_ID_XWD"),
    CDXL("CODEC_ID_CDXL"),
    XBM("CODEC_ID_XBM"),
    ZEROCODEC("CODEC_ID_ZEROCODEC"),
    MSS1("CODEC_ID_MSS1"),
    MSA1("CODEC_ID_MSA1"),
    TSCC2("CODEC_ID_TSCC2"),
    MTS2("CODEC_ID_MTS2"),
    CLLC("CODEC_ID_CLLC"),
    MSS2("CODEC_ID_MSS2"),
    VP9("CODEC_ID_VP9"),

    // various PCM "codecs"
    FIRST_AUDIO("CODEC_ID_FIRST_AUDIO"), // A dummy id pointing at the start of audio codecs
    PCM_S16LE("CODEC_ID_PCM_S16LE"),
    PCM_S16BE("CODEC_ID_PCM_S16BE"),
    PCM_U16LE("CODEC_ID_PCM_U16LE"),
    PCM_U16BE("CODEC_ID_PCM_U16BE"),
    PCM_S8("CODEC_ID_PCM_S8"),
    PCM_U8("CODEC_ID_PCM_U8"),
    PCM_MULAW("CODEC_ID_PCM_MULAW"),
    PCM_ALAW("CODEC_ID_PCM_ALAW"),
    PCM_S32LE("CODEC_ID_PCM_S32LE"),
    PCM_S32BE("CODEC_ID_PCM_S32BE"),
    PCM_U32LE("CODEC_ID_PCM_U32LE"),
    PCM_U32BE("CODEC_ID_PCM_U32BE"),
    PCM_S24LE("CODEC_ID_PCM_S24LE"),
    PCM_S24BE("CODEC_ID_PCM_S24BE"),
    PCM_U24LE("CODEC_ID_PCM_U24LE"),
    PCM_U24BE("CODEC_ID_PCM_U24BE"),
    PCM_S24DAUD("CODEC_ID_PCM_S24DAUD"),
    PCM_ZORK("CODEC_ID_PCM_ZORK"),
    PCM_S16LE_PLANAR("CODEC_ID_PCM_S16LE_PLANAR"),
    PCM_DVD("CODEC_ID_PCM_DVD"),
    PCM_F32BE("CODEC_ID_PCM_F32BE"),
    PCM_F32LE("CODEC_ID_PCM_F32LE"),
    PCM_F64BE("CODEC_ID_PCM_F64BE"),
    PCM_F64LE("CODEC_ID_PCM_F64LE"),
    PCM_BLURAY("CODEC_ID_PCM_BLURAY"),
    PCM_LXF("CODEC_ID_PCM_LXF"),
    S302M("CODEC_ID_S302M"),
    PCM_S8_PLANAR("CODEC_ID_PCM_S8_PLANAR"),

    // various ADPCM codecs
    ADPCM_IMA_QT("CODEC_ID_ADPCM_IMA_QT"),
    ADPCM_IMA_WAV("CODEC_ID_ADPCM_IMA_WAV"),
    ADPCM_IMA_DK3("CODEC_ID_ADPCM_IMA_DK3"),
    ADPCM_IMA_DK4("CODEC_ID_ADPCM_IMA_DK4"),
    ADPCM_IMA_WS("CODEC_ID_ADPCM_IMA_WS"),
    ADPCM_IMA_SMJPEG("CODEC_ID_ADPCM_IMA_SMJPEG"),
    ADPCM_MS("CODEC_ID_ADPCM_MS"),
    ADPCM_4XM("CODEC_ID_ADPCM_4XM"),
    ADPCM_XA("CODEC_ID_ADPCM_XA"),
    ADPCM_ADX("CODEC_ID_ADPCM_ADX"),
    ADPCM_EA("CODEC_ID_ADPCM_EA"),
    ADPCM_G726("CODEC_ID_ADPCM_G726"),
    ADPCM_CT("CODEC_ID_ADPCM_CT"),
    ADPCM_SWF("CODEC_ID_ADPCM_SWF"),
    ADPCM_YAMAHA("CODEC_ID_ADPCM_YAMAHA"),
    ADPCM_SBPRO_4("CODEC_ID_ADPCM_SBPRO_4"),
    ADPCM_SBPRO_3("CODEC_ID_ADPCM_SBPRO_3"),
    ADPCM_SBPRO_2("CODEC_ID_ADPCM_SBPRO_2"),
    ADPCM_THP("CODEC_ID_ADPCM_THP"),
    ADPCM_IMA_AMV("CODEC_ID_ADPCM_IMA_AMV"),
    ADPCM_EA_R1("CODEC_ID_ADPCM_EA_R1"),
    ADPCM_EA_R3("CODEC_ID_ADPCM_EA_R3"),
    ADPCM_EA_R2("CODEC_ID_ADPCM_EA_R2"),
    ADPCM_IMA_EA_SEAD("CODEC_ID_ADPCM_IMA_EA_SEAD"),
    ADPCM_IMA_EA_EACS("CODEC_ID_ADPCM_IMA_EA_EACS"),
    ADPCM_EA_XAS("CODEC_ID_ADPCM_EA_XAS"),
    ADPCM_EA_MAXIS_XA("CODEC_ID_ADPCM_EA_MAXIS_XA"),
    ADPCM_IMA_ISS("CODEC_ID_ADPCM_IMA_ISS"),
    ADPCM_G722("CODEC_ID_ADPCM_G722"),
    ADPCM_IMA_APC("CODEC_ID_ADPCM_IMA_APC"),

    // AMR
    AMR_NB("CODEC_ID_AMR_NB"),
    AMR_WB("CODEC_ID_AMR_WB"),

    // RealAudio codecs
    RA_144("CODEC_ID_RA_144"),
    RA_288("CODEC_ID_RA_288"),

    // various DPCM codecs
    ROQ_DPCM("CODEC_ID_ROQ_DPCM"),
    INTERPLAY_DPCM("CODEC_ID_INTERPLAY_DPCM"),
    XAN_DPCM("CODEC_ID_XAN_DPCM"),
    SOL_DPCM("CODEC_ID_SOL_DPCM"),

    // audio codecs
    MP2("CODEC_ID_MP2"),
    MP3("CODEC_ID_MP3"), // preferred ID for decoding MPEG audio layer 1, 2 or 3
    AAC("CODEC_ID_AAC"),
    AC3("CODEC_ID_AC3"),
    DTS("CODEC_ID_DTS"),
    VORBIS("CODEC_ID_VORBIS"),
    DVAUDIO("CODEC_ID_DVAUDIO"),
    WMAV1("CODEC_ID_WMAV1"),
    WMAV2("CODEC_ID_WMAV2"),
    MACE3("CODEC_ID_MACE3"),
    MACE6("CODEC_ID_MACE6"),
    VMDAUDIO("CODEC_ID_VMDAUDIO"),
    FLAC("CODEC_ID_FLAC"),
    MP3ADU("CODEC_ID_MP3ADU"),
    MP3ON4("CODEC_ID_MP3ON4"),
    SHORTEN("CODEC_ID_SHORTEN"),
    ALAC("CODEC_ID_ALAC"),
    WESTWOOD_SND1("CODEC_ID_WESTWOOD_SND1"),
    GSM("CODEC_ID_GSM"), // as in Berlin toast format
    QDM2("CODEC_ID_QDM2"),
    COOK("CODEC_ID_COOK"),
    TRUESPEECH("CODEC_ID_TRUESPEECH"),
    TTA("CODEC_ID_TTA"),
    SMACKAUDIO("CODEC_ID_SMACKAUDIO"),
    QCELP("CODEC_ID_QCELP"),
    WAVPACK("CODEC_ID_WAVPACK"),
    DSICINAUDIO("CODEC_ID_DSICINAUDIO"),
    IMC("CODEC_ID_IMC"),
    MUSEPACK7("CODEC_ID_MUSEPACK7"),
    MLP("CODEC_ID_MLP"),
    GSM_MS("CODEC_ID_GSM_MS"), // as found in WAV
    ATRAC3("CODEC_ID_ATRAC3"),
    VOXWARE("CODEC_ID_VOXWARE"),
    APE("CODEC_ID_APE"),
    NELLYMOSER("CODEC_ID_NELLYMOSER"),
    MUSEPACK8("CODEC_ID_MUSEPACK8"),
    SPEEX("CODEC_ID_SPEEX"),
    WMAVOICE("CODEC_ID_WMAVOICE"),
    WMAPRO("CODEC_ID_WMAPRO"),
    WMALOSSLESS("CODEC_ID_WMALOSSLESS"),
    ATRAC3P("CODEC_ID_ATRAC3P"),
    EAC3("CODEC_ID_EAC3"),
    SIPR("CODEC_ID_SIPR"),
    MP1("CODEC_ID_MP1"),
    TWINVQ("CODEC_ID_TWINVQ"),
    TRUEHD("CODEC_ID_TRUEHD"),
    MP4ALS("CODEC_ID_MP4ALS"),
    ATRAC1("CODEC_ID_ATRAC1"),
    BINKAUDIO_RDFT("CODEC_ID_BINKAUDIO_RDFT"),
    BINKAUDIO_DCT("CODEC_ID_BINKAUDIO_DCT"),
    AAC_LATM("CODEC_ID_AAC_LATM"),
    QDMC("CODEC_ID_QDMC"),
    CELT("CODEC_ID_CELT"),
    G723_1("CODEC_ID_G723_1"),
    G729("CODEC_ID_G729"),
    _8SVX_EXP("CODEC_ID_8SVX_EXP"),
    _8SVX_FIB("CODEC_ID_8SVX_FIB"),
    BMV_AUDIO("CODEC_ID_BMV_AUDIO"),
    RALF("CODEC_ID_RALF"),
    IAC("CODEC_ID_IAC"),
    ILBC("CODEC_ID_ILBC"),
    OPUS("CODEC_ID_OPUS"),
    COMFORT_NOISE("CODEC_ID_COMFORT_NOISE"),
    TAK("CODEC_ID_TAK"),

    // subtitle codecs
    FIRST_SUBTITLE("CODEC_ID_FIRST_SUBTITLE"), // A dummy ID pointing at the start of subtitle codecs.
    DVD_SUBTITLE("CODEC_ID_DVD_SUBTITLE"),
    DVB_SUBTITLE("CODEC_ID_DVB_SUBTITLE"),
    TEXT("CODEC_ID_TEXT"),  // raw UTF-8 text
    XSUB("CODEC_ID_XSUB"),
    SSA("CODEC_ID_SSA"),
    MOV_TEXT("CODEC_ID_MOV_TEXT"),
    HDMV_PGS_SUBTITLE("CODEC_ID_HDMV_PGS_SUBTITLE"),
    DVB_TELETEXT("CODEC_ID_DVB_TELETEXT"),
    SRT("CODEC_ID_SRT"),

    // other specific kind of codecs (generally used for attachments)
    FIRST_UNKNOWN("CODEC_ID_FIRST_UNKNOWN"), // A dummy ID pointing at the start of various fake codecs.
    TTF("CODEC_ID_TTF"),
    PROBE("CODEC_ID_PROBE"), // codec_id is not known (like AV_CODEC_ID_NONE) but lavf should attempt to identify it
    MPEG2TS("CODEC_ID_MPEG2TS"), // _FAKE_ codec to indicate a raw MPEG-2 TS stream (only used by libavformat)
    MPEG4SYSTEMS("CODEC_ID_MPEG4SYSTEMS"), // _FAKE_ codec to indicate a MPEG-4 Systems stream (only used by libavformat)
    FFMETADATA("CODEC_ID_FFMETADATA"); // Dummy codec for streams containing only metadata information.
    
    private static final Map<Integer, CodecID> idMap;
    
    static {
        idMap = new HashMap<Integer, CodecID>();
        for (CodecID codecId : CodecID.values())
            idMap.put(codecId.value(), codecId);
    }
    
    private final int value;

    private CodecID(String fieldName) {
        value = CodecIdMapper.getInstance().codecIdValue(fieldName);
    }
    
    public int value() {
        return value;
    }
    
    public static CodecID valueOf(int value) {
        return idMap.get(value);
    }
    
    private static class CodecIdMapper {
        
        private static final CodecIdMapper mapper = new CodecIdMapper();
        
        private final Map<String, Integer> codecIdFieldMap;
        private final int NONE_VALUE;
        
        private CodecIdMapper() {
            LibraryManager libManager = LibraryManager.getInstance();
            AVCodecLibrary codecLibrary = libManager.getAVCodecLibrary();
            
            codecIdFieldMap = new HashMap<String, Integer>();
            
            switch (codecLibrary.getMajorVersion()) {
                case 53:
                    NONE_VALUE = CodecID53.CODEC_ID_NONE;
                    parseFields(CodecID53.class.getDeclaredFields());
                    break;
                case 54:
                    NONE_VALUE = CodecID54.CODEC_ID_NONE;
                    parseFields(CodecID54.class.getDeclaredFields());
                    break;
                case 55:
                    NONE_VALUE = CodecID55.AV_CODEC_ID_NONE;
                    parseFields(CodecID55.class.getDeclaredFields());
                    break;
                default:
                    NONE_VALUE = 0;
                    break;
            }
        }
        
        private void parseFields(Field[] fields) {
            Pattern codecIdPattern = Pattern.compile("(AV_)?(CODEC_ID_[0-9A-Za-z_]+)");
            Matcher matcher;
            int mods;
            
            for (Field field : fields) {
                mods = field.getModifiers();
                if (Modifier.isStatic(mods) && Modifier.isFinal(mods)
                        && Modifier.isPublic(mods)) {
                    matcher = codecIdPattern.matcher(field.getName());
                    if (matcher.matches()) {
                        try {
                            codecIdFieldMap.put(matcher.group(2), field.getInt(null));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
        
        public int codecIdValue(String fieldName) {
            Integer val = codecIdFieldMap.get(fieldName);
            if (val == null)
                return NONE_VALUE;
            
            return val;
        }
        
        public static CodecIdMapper getInstance() {
            return mapper;
        }
        
    }
    
}

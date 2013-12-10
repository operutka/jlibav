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

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bridj.BridJ;
import org.bridj.Callback;
import org.bridj.NativeLibrary;
import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.bridj.ann.Optional;
import org.bridj.ann.Ptr;
import org.libav.bridge.ILibrary;

/**
 * Interface to provide access to the native avcodec library. The methods'
 * documentation has been taken from the original Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public final class AVCodecLibrary implements ILibrary {
    
    public static final int AVCODEC_MAX_AUDIO_FRAME_SIZE  = 192000;
    public static final int FF_INPUT_BUFFER_PADDING_SIZE = 8;
    public static final int FF_MIN_BUFFER_SIZE = 16384;
    public static final int FF_MAX_B_FRAMES = 16;
    
    public static final int AV_PKT_FLAG_KEY = 0x0001;
    public static final int AV_PKT_FLAG_CORRUPT = 0x0002;
    
    public static final int CODEC_CAP_DRAW_HORIZ_BAND = 0x0001;
    public static final int CODEC_CAP_DR1 = 0x0002;
    public static final int CODEC_CAP_PARSE_ONLY = 0x0004;
    public static final int CODEC_CAP_TRUNCATED = 0x0008;
    public static final int CODEC_CAP_HWACCEL = 0x0010;
    public static final int CODEC_CAP_DELAY = 0x0020;
    public static final int CODEC_CAP_SMALL_LAST_FRAME = 0x0040;
    public static final int CODEC_CAP_HWACCEL_VDPAU = 0x0080;
    public static final int CODEC_CAP_SUBFRAMES = 0x0100;
    public static final int CODEC_CAP_EXPERIMENTAL = 0x0200;
    public static final int CODEC_CAP_CHANNEL_CONF = 0x0400;
    public static final int CODEC_CAP_NEG_LINESIZES = 0x0800;
    public static final int CODEC_CAP_FRAME_THREADS = 0x1000;
    public static final int CODEC_CAP_SLICE_THREADS = 0x2000;
    public static final int CODEC_CAP_PARAM_CHANGE = 0x4000;
    public static final int CODEC_CAP_AUTO_THREADS = 0x8000;
    public static final int CODEC_CAP_VARIABLE_FRAME_SIZE = 0x10000;

    public static final int CODEC_FLAG_QSCALE = 0x0002;  
    public static final int CODEC_FLAG_4MV = 0x0004;  
    public static final int CODEC_FLAG_QPEL = 0x0010;  
    public static final int CODEC_FLAG_GMC = 0x0020;  
    public static final int CODEC_FLAG_MV0 = 0x0040;  

    public static final int CODEC_FLAG_INPUT_PRESERVED = 0x0100;
    public static final int CODEC_FLAG_PASS1 = 0x0200;   
    public static final int CODEC_FLAG_PASS2 = 0x0400;   
    public static final int CODEC_FLAG_GRAY = 0x2000;   
    public static final int CODEC_FLAG_EMU_EDGE = 0x4000;   
    public static final int CODEC_FLAG_PSNR = 0x8000;   
    public static final int CODEC_FLAG_TRUNCATED = 0x00010000; 
    public static final int CODEC_FLAG_NORMALIZE_AQP = 0x00020000; 
    public static final int CODEC_FLAG_INTERLACED_DCT = 0x00040000; 
    public static final int CODEC_FLAG_LOW_DELAY = 0x00080000; 
    public static final int CODEC_FLAG_GLOBAL_HEADER = 0x00400000; 
    public static final int CODEC_FLAG_BITEXACT = 0x00800000; 

    public static final int CODEC_FLAG_AC_PRED = 0x01000000; 
    public static final int CODEC_FLAG_CBP_RD = 0x04000000; 
    public static final int CODEC_FLAG_QP_RD = 0x08000000; 
    public static final int CODEC_FLAG_LOOP_FILTER = 0x00000800; 
    public static final int CODEC_FLAG_INTERLACED_ME = 0x20000000; 
    public static final int CODEC_FLAG_CLOSED_GOP = 0x80000000;
    public static final int CODEC_FLAG2_FAST = 0x00000001; 
    public static final int CODEC_FLAG2_STRICT_GOP = 0x00000002; 
    public static final int CODEC_FLAG2_NO_OUTPUT = 0x00000004; 
    public static final int CODEC_FLAG2_LOCAL_HEADER = 0x00000008; 
    public static final int CODEC_FLAG2_SKIP_RD = 0x00004000; 
    public static final int CODEC_FLAG2_CHUNKS = 0x00008000; 

    public static final int CODEC_FLAG_OBMC = 0x00000001; 
    public static final int CODEC_FLAG_H263P_AIV = 0x00000008; 
    public static final int CODEC_FLAG_PART = 0x0080;  
    public static final int CODEC_FLAG_ALT_SCAN = 0x00100000; 
    public static final int CODEC_FLAG_H263P_UMV = 0x02000000; 
    public static final int CODEC_FLAG_H263P_SLICE_STRUCT = 0x10000000;
    public static final int CODEC_FLAG_SVCD_SCAN_OFFSET = 0x40000000; 
    public static final int CODEC_FLAG2_INTRA_VLC = 0x00000800; 
    public static final int CODEC_FLAG2_DROP_FRAME_TIMECODE = 0x00002000; 
    public static final int CODEC_FLAG2_NON_LINEAR_QUANT = 0x00010000; 

    public static final int CODEC_FLAG_EXTERN_HUFF = 0x1000;   

    public static final int CODEC_FLAG2_BPYRAMID = 0x00000010; 
    public static final int CODEC_FLAG2_WPRED = 0x00000020; 
    public static final int CODEC_FLAG2_MIXED_REFS = 0x00000040; 
    public static final int CODEC_FLAG2_8X8DCT = 0x00000080; 
    public static final int CODEC_FLAG2_FASTPSKIP = 0x00000100; 
    public static final int CODEC_FLAG2_AUD = 0x00000200; 
    public static final int CODEC_FLAG2_BRDO = 0x00000400; 
    public static final int CODEC_FLAG2_MBTREE = 0x00040000; 
    public static final int CODEC_FLAG2_PSY = 0x00080000; 
    public static final int CODEC_FLAG2_SSIM = 0x00100000; 
    public static final int CODEC_FLAG2_INTRA_REFRESH = 0x00200000; 

    public static final int CODEC_FLAG2_MEMC_ONLY = 0x00001000; 

    public static final int CODEC_FLAG2_BIT_RESERVOIR = 0x00020000;

    public static final String LIB_NAME = BridJ.getNativeLibraryName(Lib.class);
    public static final int MIN_MAJOR_VERSION = 53;
    public static final int MAX_MAJOR_VERSION = 55;
    
    private int majorVersion;
    private int minorVersion;
    private int microVersion;
    
    private NativeLibrary lib;
    
    private LockManager lockManager;
    private Pointer<RegisterLockMgrCallback> lockMgrCallback;
    
    public AVCodecLibrary() throws IOException {
        lib = BridJ.getNativeLibrary(Lib.class);
        
        int libVersion = avcodec_version();
        
        majorVersion = (libVersion >> 16) & 0xff;
        minorVersion = (libVersion >> 8) & 0xff;
        microVersion = libVersion & 0xff;
        String version = String.format("%d.%d.%d", majorVersion, minorVersion, microVersion);
        
        File libFile = BridJ.getNativeLibraryFile(LIB_NAME);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Loading {0} library, version {1}...", new Object[] { libFile.getAbsolutePath(), version });
        
        if (majorVersion < MIN_MAJOR_VERSION || majorVersion > MAX_MAJOR_VERSION)
            throw new UnsatisfiedLinkError("Unsupported version of the " + LIB_NAME + " native library. (" + MIN_MAJOR_VERSION + ".x.x <= required <= " + MAX_MAJOR_VERSION + ".x.x, found " + version + ")");
        
        lockManager = new LockManager();
        lockMgrCallback = lockManager.toPointer();
        
        av_lockmgr_register(lockMgrCallback);
    }

    @Override
    public boolean functionExists(String functionName) {
        return lib.getSymbol(functionName) != null;
    }

    @Override
    public int getMajorVersion() {
        return majorVersion;
    }

    @Override
    public int getMicroVersion() {
        return microVersion;
    }

    @Override
    public int getMinorVersion() {
        return minorVersion;
    }
    
    /**
     * Get version of the avcodec library. Bits 23 to 16 represents major
     * version, bits 15 to 8 are for minor version and bits 7 to 0 are for
     * micro version.
     * 
     * @return version of the avcodec library
     */
    public int avcodec_version() {
        return Lib.avcodec_version();
    }
    
    /**
     * Register all the codecs, parsers and bitstream filters which were enabled 
     * at configuration time. 
     * 
     * If you do not call this function you can select exactly which formats you 
     * want to support, by using the individual registration functions.
     */
    public void avcodec_register_all() {
        Lib.avcodec_register_all();
    }
    
    /**
     * Finds a decoder for the specified codec.
     * 
     * @param codecId one of the CodecID constants
     * @return a pointer to a decoder or null, if no such decoder exists
     */
    public Pointer<?> avcodec_find_decoder(int id) {
        return Lib.avcodec_find_decoder(id);
    }
    
    /**
     * Find a registered decoder with the specified name.
     * 
     * @param name name of the requested decoder
     * @return a decoder if one was found, NULL otherwise
     */
    public Pointer<?> avcodec_find_decoder_by_name(Pointer<Byte> name) {
        return Lib.avcodec_find_decoder_by_name(name);
    }
    
    /**
     * Finds a encoder for the specified codec.
     * 
     * @param codecId one of the CodecID constants
     * @return  a pointer to an encoder or null, if no such encoder exists
     */
    public Pointer<?> avcodec_find_encoder(int id) {
        return Lib.avcodec_find_encoder(id);
    }
    
    /**
     * Find a registered encoder with the specified name.
     * 
     * @param name name of the requested encoder
     * @return an encoder if one was found, NULL otherwise
     */
    public Pointer<?> avcodec_find_encoder_by_name(Pointer<Byte> name) {
        return Lib.avcodec_find_encoder_by_name(name);
    }
    
    /**
     * Allocate an AVCodecContext and set its fields to default values. 
     * The resulting struct can be deallocated by simply calling av_free().
     * 
     * @param codec if non-NULL, allocate private data and initialize defaults 
     * for the given codec. It is illegal to then call avcodec_open() with 
     * a different codec
     * @return a pointer to an AVCodecContext or null on failure
     */
    public Pointer<?> avcodec_alloc_context3(Pointer<?> codec) {
        return Lib.avcodec_alloc_context3(codec);
    }
    
    /**
     * Allocate an AVFrame and set its fields to default values. 
     * The resulting struct can be deallocated by simply calling av_free().
     * 
     * @return An AVFrame filled with default values or NULL on failure.
     */
    public Pointer<?> avcodec_alloc_frame() {
        return Lib.avcodec_alloc_frame();
    }
    
    /**
     * Free the frame and any dynamically allocated objects in it,
     * e.g. extended_data.
     * 
     * WARNING: this function does NOT free the data buffers themselves
     * (it does not know how, since they might have been allocated with
     *  a custom get_buffer()).
     * 
     * @param frame frame to be freed. The pointer will be set to NULL.
     */
    public void avcodec_free_frame(Pointer<Pointer<?>> frame) {
        Lib.avcodec_free_frame(frame.getPeer());
    }
    
    /**
     * Set the fields of the given AVFrame to default values.
     * 
     * @param pic The AVFrame of which the fields should be set to default 
     * values.
     */
    public void avcodec_get_frame_defaults(Pointer<?> pic) {
        Lib.avcodec_get_frame_defaults(pic.getPeer());
    }
    
    /**
     * Initialize optional fields of a packet with default values.
     * 
     * @param pkt packet
     */
    public void av_init_packet(Pointer<?> pkt) {
        Lib.av_init_packet(pkt.getPeer());
    }
    
    /**
     * Allocate the payload of a packet and initialize its fields with default 
     * values.
     * 
     * @param packet
     * @param size wanted payload size
     * @return 0 if OK, AVERROR_xxx otherwise
     */
    public int av_new_packet(Pointer<?> pkt, int size) {
        return Lib.av_new_packet(pkt.getPeer(), size);
    }
    
    /**
     * Free a packet.
     * 
     * @param pkt packet to free
     */
    public void av_free_packet(Pointer<?> pkt) {
        Lib.av_free_packet(pkt.getPeer());
    }
    
    /**
     * Increase packet size, correctly zeroing padding.
     * 
     * @param pkt packet
     * @param grow_by number of bytes by which to increase the size of 
     * the packet
     * @return 0 on success
     */
    public int av_grow_packet(Pointer<?> pkt, int grow_by) {
        return Lib.av_grow_packet(pkt.getPeer(), grow_by);
    }
    
    /**
     * Reduce packet size, correctly zeroing padding.
     * 
     * @param pkt packet
     * @param size new size
     */
    public void av_shrink_packet(Pointer<?> pkt, int size) {
        Lib.av_shrink_packet(pkt.getPeer(), size);
    }
    
    /**
     * Set the fields of the given AVCodecContext to default values 
     * corresponding to the given codec (defaults may be codec-dependent).
     * 
     * Do not call this function if a non-NULL codec has been passed to 
     * avcodec_alloc_context3() that allocated this AVCodecContext. If codec 
     * is non-NULL, it is illegal to call avcodec_open2() with a different 
     * codec on this AVCodecContext.
     * 
     * @param s codec context
     * @param codec codec
     * @return 0 on success
     */
    public int avcodec_get_context_defaults3(Pointer<?> s, Pointer<?> codec) {
        return Lib.avcodec_get_context_defaults3(s.getPeer(), codec.getPeer());
    }
    
    /**
     * Initialize the AVCodecContext to use the given AVCodec. Prior to using this
     * function the context has to be allocated.
     *
     * The functions avcodec_find_decoder_by_name(), avcodec_find_encoder_by_name(),
     * avcodec_find_decoder() and avcodec_find_encoder() provide an easy way for
     * retrieving a codec.
     *
     * @warning This function is not thread safe!
     *
     * @deprecated since 53.6.0; use avcodec_open2()
     * @param avctx The context which will be set up to use the given codec.
     * @param codec The codec to use within the context.
     * @return zero on success, a negative value on error
     */
    public int avcodec_open(Pointer<?> avctx, Pointer<?> codec) {
        return Lib.avcodec_open(avctx.getPeer(), codec.getPeer());
    }
    
    /**
     * Initialize the AVCodecContext to use the given AVCodec.
     * Prior to using this function the context has to be allocated with 
     * avcodec_alloc_context(). 
     * 
     * The functions avcodec_find_decoder_by_name(), 
     * avcodec_find_encoder_by_name(), avcodec_find_decoder() and 
     * avcodec_find_encoder() provide an easy way for retrieving a codec.
     * 
     * WARNING:
     * This function is not thread safe!
     * 
     * @param avctx the context to initialize
     * @param codec a codec
     * @param options a dictionary filled with AVCodecContext and codec-private 
     * options. On return this object will be filled with options that were 
     * not found
     * @return zero on success, a negative value on error
     */
    public int avcodec_open2(Pointer<?> avctx, Pointer<?> codec, Pointer<Pointer<?>> options) {
        return Lib.avcodec_open2(avctx.getPeer(), codec.getPeer(), options == null ? 0 : options.getPeer());
    }
    
    public int avcodec_close(Pointer<?> avctx) {
        return Lib.avcodec_close(avctx.getPeer());
    }
    
    /**
     * Register a user provided lock manager supporting the operations
     * specified by AVLockOp. mutex points to a (void *) where the
     * lockmgr should store/get a pointer to a user allocated mutex. It's
     * NULL upon AV_LOCK_CREATE and != NULL for all other ops.
     *
     * @param cb User defined callback. Note: Libav may invoke calls to this
     *           callback during the call to av_lockmgr_register().
     *           Thus, the application must be prepared to handle that.
     *           If cb is set to NULL the lockmgr will be unregistered.
     *           Also note that during unregistration the previously registered
     *           lockmgr callback may also be invoked.
     */
    public int av_lockmgr_register(Pointer<RegisterLockMgrCallback> cb) {
        return Lib.av_lockmgr_register(Pointer.getPeer(cb));
    }
    
    /**
     * Decode the video frame of size avpkt->size from avpkt->data into picture. 
     * Some decoders may support multiple frames in a single AVPacket, such 
     * decoders would then just decode the first frame.
     * 
     * WARNING:
     * The input buffer must be FF_INPUT_BUFFER_PADDING_SIZE larger than the 
     * actual read bytes because some optimized bitstream readers read 32 or 64 
     * bits at once and could read over the end.
     * The end of the input buffer buf should be set to 0 to ensure that no 
     * overreading happens for damaged MPEG streams.
     * 
     * NOTE:
     * You might have to align the input buffer avpkt->data. The alignment 
     * requirements depend on the CPU: on some CPUs it isn't necessary at all, 
     * on others it won't work at all if not aligned and on others it will work 
     * but it will have an impact on performance.
     * In practice, avpkt->data should have 4 byte alignment at minimum.
     * 
     * NOTE:
     * Some codecs have a delay between input and output, these need to be fed 
     * with avpkt->data=NULL, avpkt->size=0 at the end to return the remaining 
     * frames.
     * 
     * @param avctx the codec context
     * @param picture The AVFrame in which the decoded video frame will be 
     * stored. Use avcodec_alloc_frame to get an AVFrame, the codec will 
     * allocate memory for the actual bitmap. with default get/release_buffer(), 
     * the decoder frees/reuses the bitmap as it sees fit. with overridden 
     * get/release_buffer() (needs CODEC_CAP_DR1) the user decides into what 
     * buffer the decoder decodes and the decoder tells the user once it does 
     * not need the data anymore, the user app can at this point free/reuse/keep 
     * the memory as it sees fit.
     * @param got_picture_ptr zero if no frame could be decompressed, otherwise, 
     * it is nonzero.
     * @param avpkt The input AVpacket containing the input buffer. You can 
     * create such packet with av_init_packet() and by then setting data and 
     * size, some decoders might in addition need other fields like 
     * flags&AV_PKT_FLAG_KEY. All decoders are designed to use the least 
     * fields possible.
     * @return On error a negative value is returned, otherwise the number of 
     * bytes used or zero if no frame could be decompressed.
     */
    public int avcodec_decode_video2(Pointer<?> avctx, Pointer<?> picture, Pointer<Integer> got_picture_ptr, Pointer<?> avpkt) {
        return Lib.avcodec_decode_video2(avctx.getPeer(), picture.getPeer(), got_picture_ptr.getPeer(), avpkt.getPeer());
    }
    
    /**
     * Encode a video frame from pict into buf. The input picture should be 
     * stored using a specific format, namely avctx.pix_fmt.
     * 
     * @deprecated since 54.01.0; use the avcodec_encode_video2() instead
     * 
     * @param avctx the codec context
     * @param buf the output buffer for the bitstream of encoded frame
     * @param buf_size the size of the output buffer in bytes
     * @param pict the input picture to encode
     * @return On error a negative value is returned, on success zero or the 
     * number of bytes used from the output buffer.
     */
    public int avcodec_encode_video(Pointer<?> avctx, Pointer<Byte> buf, int buf_size, Pointer<?> pict) {
        return Lib.avcodec_encode_video(avctx.getPeer(), buf.getPeer(), buf_size, pict == null ? 0 : pict.getPeer());
    }
    
    /**
     * Encode a frame of video. 
     * 
     * Takes input raw video data from frame and writes the next output packet, 
     * if available, to avpkt. The output packet does not necessarily contain 
     * data for the most recent frame, as encoders can delay and reorder input 
     * frames internally as needed.
     * 
     * NOTE:
     * If this function fails or produces no output, avpkt will be freed using 
     * av_free_packet() (i.e. avpkt->destruct will be called to free the user 
     * supplied buffer).
     * 
     * @param avctx codec context
     * @param avpkt output AVPacket. The user can supply an output buffer by 
     * setting avpkt->data and avpkt->size prior to calling the function, but 
     * if the size of the user-provided data is not large enough, encoding will 
     * fail. All other AVPacket fields will be reset by the encoder using 
     * av_init_packet(). If avpkt->data is NULL, the encoder will allocate it. 
     * The encoder will set avpkt->size to the size of the output packet. The 
     * returned data (if any) belongs to the caller, he is responsible for 
     * freeing it.
     * @param frame AVFrame containing the raw video data to be encoded. May be 
     * NULL when flushing an encoder that has the CODEC_CAP_DELAY capability 
     * set.
     * @param got_packet_ptr This field is set to 1 by libavcodec if the output 
     * packet is non-empty, and to 0 if it is empty. If the function returns an 
     * error, the packet can be assumed to be invalid, and the value of 
     * got_packet_ptr is undefined and should not be used.
     * @return 0 on success, negative error code on failure
     */
    public int avcodec_encode_video2(Pointer<?> avctx, Pointer<?> avpkt, Pointer<?> frame, Pointer<Integer> got_packet_ptr) {
        return Lib.avcodec_encode_video2(avctx.getPeer(), avpkt.getPeer(), frame == null ? 0 : frame.getPeer(), got_packet_ptr.getPeer());
    }
    
    /**
     * Decode the audio frame of size avpkt->size from avpkt->data into samples. 
     * 
     * Some decoders may support multiple frames in a single AVPacket, such 
     * decoders would then just decode the first frame. In this case, 
     * avcodec_decode_audio3 has to be called again with an AVPacket that 
     * contains the remaining data in order to decode the second frame etc. If 
     * no frame could be outputted, frame_size_ptr is zero. Otherwise, it is 
     * the decompressed frame size in bytes.
     * 
     * WARNING:
     * You must set frame_size_ptr to the allocated size of the output buffer 
     * before calling avcodec_decode_audio3().
     * The input buffer must be FF_INPUT_BUFFER_PADDING_SIZE larger than the 
     * actual read bytes because some optimized bitstream readers read 32 or 64 
     * bits at once and could read over the end.
     * The end of the input buffer avpkt->data should be set to 0 to ensure 
     * that no overreading happens for damaged MPEG streams.
     * 
     * NOTE:
     * You might have to align the input buffer avpkt->data and output buffer 
     * samples. The alignment requirements depend on the CPU: On some CPUs it 
     * isn't necessary at all, on others it won't work at all if not aligned 
     * and on others it will work but it will have an impact on performance.
     * 
     * In practice, avpkt->data should have 4 byte alignment at minimum and 
     * samples should be 16 byte aligned unless the CPU doesn't need it 
     * (AltiVec and SSE do).
     * 
     * NOTE:
     * Codecs which have the CODEC_CAP_DELAY capability set have a delay 
     * between input and output, these need to be fed with avpkt->data=NULL, 
     * avpkt->size=0 at the end to return the remaining frames.
     * 
     * @deprecated since 53.25.0; use the avcodec_decode_audio4() instead
     * 
     * @param avctx the codec context
     * @param samples the output buffer, sample type in avctx->sample_fmt
     * @param frameSizePtr the output buffer size in bytes
     * @param packet The input AVPacket containing the input buffer. You can 
     * create such packet with av_init_packet() and by then setting data and 
     * size, some decoders might in addition need other fields. All decoders 
     * are designed to use the least fields possible though.
     * @return On error a negative value is returned, otherwise the number of 
     * bytes used or zero if no frame data was decompressed (used) from the 
     * input AVPacket.
     */
    public int avcodec_decode_audio3(Pointer<?> avctx, Pointer<Short> samples, Pointer<Integer> frameSizePtr, Pointer<?> avpkt) {
        return Lib.avcodec_decode_audio3(avctx.getPeer(), samples.getPeer(), frameSizePtr.getPeer(), avpkt.getPeer());
    }
    
    /**
     * Decode the audio frame of size avpkt->size from avpkt->data into frame. 
     * 
     * Some decoders may support multiple frames in a single AVPacket. Such 
     * decoders would then just decode the first frame. In this case, 
     * avcodec_decode_audio4 has to be called again with an AVPacket containing 
     * the remaining data in order to decode the second frame, etc... Even if no
     * frames are returned, the packet needs to be fed to the decoder with 
     * remaining data until it is completely consumed or an error occurs.
     * 
     * WARNING:
     * The input buffer, avpkt->data must be FF_INPUT_BUFFER_PADDING_SIZE larger
     * than the actual read bytes because some optimized bitstream readers read 
     * 32 or 64 bits at once and could read over the end.
     * 
     * NOTE:
     * You might have to align the input buffer. The alignment requirements 
     * depend on the CPU and the decoder.
     * 
     * @param avctx the codec context
     * @param frame The AVFrame in which to store decoded audio samples. 
     * Decoders request a buffer of a particular size by setting 
     * AVFrame.nb_samples prior to calling get_buffer(). The decoder may, 
     * however, only utilize part of the buffer by setting AVFrame.nb_samples to
     * a smaller value in the output frame.
     * @param gotFramePtr Zero if no frame could be decoded, otherwise it is 
     * non-zero.
     * @param avpkt The input AVPacket containing the input buffer. At least 
     * avpkt->data and avpkt->size should be set. Some decoders might also 
     * require additional fields to be set.
     * @return A negative error code is returned if an error occurred during 
     * decoding, otherwise the number of bytes consumed from the input AVPacket 
     * is returned.
     */
    public int avcodec_decode_audio4(Pointer<?> avctx, Pointer<?> frame, Pointer<Integer> gotFramePtr, Pointer<?> avpkt) {
        return Lib.avcodec_decode_audio4(avctx.getPeer(), frame.getPeer(), gotFramePtr.getPeer(), avpkt.getPeer());
    }
    
    /**
     * Encode an audio frame from samples into buf.
     * 
     * NOTE:
     * The output buffer should be at least FF_MIN_BUFFER_SIZE bytes large. 
     * However, for PCM audio the user will know how much space is needed 
     * because it depends on the value passed in buf_size as described below. 
     * In that case a lower value can be used.
     * 
     * @deprecated since 53.34.0; use the avcodec_encode_audio2() instead
     * 
     * @param avctx the codec context
     * @param buf the output buffer
     * @param bufSize the output buffer size
     * @param samples the input buffer containing the samples The number of 
     * samples read from this buffer is frame_size*channels, both of which are 
     * defined in avctx. For PCM audio the number of samples read from samples 
     * is equal to buf_size * input_sample_size / output_sample_size.
     * @return On error a negative value is returned, on success zero or 
     * the number of bytes used to encode the data read from the input buffer.
     */
    public int avcodec_encode_audio(Pointer<?> avctx, Pointer<Byte> buf, int bufSize, Pointer<Short> samples) {
        return Lib.avcodec_encode_audio(avctx.getPeer(), buf.getPeer(), bufSize, samples == null ? 0 : samples.getPeer());
    }
    
    /**
     * Encode a frame of audio. 
     * 
     * Takes input samples from frame and writes the next output packet, if 
     * available, to avpkt. The output packet does not necessarily contain data 
     * for the most recent frame, as encoders can delay, split, and combine 
     * input frames internally as needed.
     * 
     * @param avctx codec context
     * @param avpkt output AVPacket. The user can supply an output buffer by 
     * setting avpkt->data and avpkt->size prior to calling the function, but 
     * if the size of the user-provided data is not large enough, encoding will 
     * fail. All other AVPacket fields will be reset by the encoder using 
     * av_init_packet(). If avpkt->data is NULL, the encoder will allocate it. 
     * The encoder will set avpkt->size to the size of the output packet.
     * @param frame 	AVFrame containing the raw audio data to be encoded. 
     * May be NULL when flushing an encoder that has the CODEC_CAP_DELAY 
     * capability set. There are 2 codec capabilities that affect the allowed 
     * values of frame->nb_samples. If CODEC_CAP_SMALL_LAST_FRAME is set, then 
     * only the final frame may be smaller than avctx->frame_size, and all other
     * frames must be equal to avctx->frame_size. If 
     * CODEC_CAP_VARIABLE_FRAME_SIZE is set, then each frame can have any number
     * of samples. If neither is set, frame->nb_samples must be equal to 
     * avctx->frame_size for all frames.
     * @param got_packet_ptr This field is set to 1 by libavcodec if the output 
     * packet is non-empty, and to 0 if it is empty. If the function returns an 
     * error, the packet can be assumed to be invalid, and the value of 
     * got_packet_ptr is undefined and should not be used.
     * @return 0 on success, negative error code on failure
     */
    public int avcodec_encode_audio2(Pointer<?> avctx, Pointer<?> avpkt, Pointer<?> frame, Pointer<Integer> got_packet_ptr) {
        return Lib.avcodec_encode_audio2(avctx.getPeer(), avpkt.getPeer(), frame == null ? 0 : frame.getPeer(), got_packet_ptr.getPeer());
    }
    
    /**
     * Calculate the size in bytes that a picture of the given width and height 
     * would occupy if stored in the given picture format. 
     * 
     * Note that this returns the size of a compact representation as generated 
     * by avpicture_layout(), which can be smaller than the size required for 
     * e.g. avpicture_fill().
     * 
     * @param pix_fmt the given picture format
     * @param width the width of the image
     * @param height the height of the image
     * @return Image data size in bytes or -1 on error (e.g. too large 
     * dimensions).
     */
    public int avpicture_get_size(int pix_fmt, int width, int height) {
        return Lib.avpicture_get_size(pix_fmt, width, height);
    }
    
    /**
     * Fill in the AVPicture fields. 
     * 
     * The fields of the given AVPicture are filled in by using the 'ptr' 
     * address which points to the image data buffer. Depending on the specified 
     * picture format, one or multiple image data pointers and line sizes will 
     * be set. If a planar format is specified, several pointers will be set 
     * pointing to the different picture planes and the line sizes of the 
     * different planes will be stored in the lines_sizes array. Call with 
     * ptr == NULL to get the required size for the ptr buffer.
     * 
     * To allocate the buffer and fill in the AVPicture fields in one call, use 
     * avpicture_alloc().
     * 
     * @param picture AVPicture whose fields are to be filled in
     * @param ptr buffer which will contain or contains the actual image data
     * @param pix_fmt the format in which the picture data is stored
     * @param width the width of the image in pixels
     * @param height the height of the image in pixels
     * @return size of the image data in bytes
     */
    public int avpicture_fill(Pointer<?> picture, Pointer<Byte> ptr, int pix_fmt, int width, int height) {
        return Lib.avpicture_fill(picture.getPeer(), ptr.getPeer(), pix_fmt, width, height);
    }
    
    /**
     * Copy pixel data from an AVPicture into a buffer. 
     * 
     * The data is stored compactly, without any gaps for alignment or padding 
     * which may be applied by avpicture_fill().
     * 
     * @param picture AVPicture containing image data
     * @param pix_fmt the format in which the picture data is stored
     * @param width the width of the image in pixels
     * @param height the height of the image in pixels
     * @param dest a buffer into which picture data will be copied
     * @param dest_size the size of dest
     * @return the number of bytes written to dest, or a negative value (error 
     * code) on error
     */
    public int avpicture_layout(Pointer<?> src, int pix_fmt, int width, int height, Pointer<Byte> dest, int dest_size) {
        return Lib.avpicture_layout(src.getPeer(), pix_fmt, width, height, dest.getPeer(), dest_size);
    }
    
    /**
     * Initialize audio resampling context.
     * 
     * @param outputChannels number of output channels
     * @param inputChannels number of input channels
     * @param outputRate output sample rate
     * @param inputRate input sample rate
     * @param sampleFmtOut requested output sample format
     * @param sampleFmtIn input sample format
     * @param filterLength length of each FIR filter in the filterbank relative 
     * to the cutoff frequency
     * @param log2PhaseCount log2 of the number of entries in the polyphase 
     * filterbank
     * @param linear if 1 then the used FIR filter will be linearly interpolated 
     * between the 2 closest, if 0 the closest will be used
     * @param cutoff cutoff frequency, 1.0 corresponds to half the output 
     * sampling rate
     * @return allocated ReSampleContext, NULL if error occured
     */
    public Pointer<?> av_audio_resample_init(int outputChannels, int inputChannels, int outputRate, int inputRate, int sampleFmtOut, int sampleFmtIn, int filterLength, int log2PhaseCount, int linear, double cutoff) {
        return Lib.av_audio_resample_init(outputChannels, inputChannels, outputRate, inputRate, sampleFmtOut, sampleFmtIn, filterLength, log2PhaseCount, linear, cutoff);
    }
    
    /**
     * Resample the input using given ReSampleContext.
     * 
     * @param s ReSampleContext
     * @param output
     * @param input
     * @param nbSamples
     * @return 0 if error
     */
    public int audio_resample(Pointer<?> s, Pointer<Byte> output, Pointer<Byte> input, int nbSamples) {
        return Lib.audio_resample(s.getPeer(), output.getPeer(), input.getPeer(), nbSamples);
    }
    
    /**
     * Free resample context.
     * 
     * @param s non-NULL pointer to a resample context previously created 
     * with av_audio_resample_init()
     */
    public void audio_resample_close(Pointer<?> s) {
        Lib.audio_resample_close(s.getPeer());
    }
    
    /**
     * Fill audio frame data and linesize.
     * 
     * AVFrame extended_data channel pointers are allocated if necessary for 
     * planar audio.
     * 
     * @param frame an AVFrame, frame->nb_samples must be set prior to calling 
     * the function. This function fills in frame->data, frame->extended_data, 
     * frame->linesize[0]
     * @param nb_channels channel count
     * @param sample_fmt sample format
     * @param buf buffer to use for frame data
     * @param buf_size size of buffer
     * @param align plane size sample alignment (0 = default)
     * @return 0 on success, negative error code on failure
     */
    public int avcodec_fill_audio_frame(Pointer<?> frame, int nb_channels, int sample_fmt, Pointer<Byte> buf, int buf_size, int align) {
        return Lib.avcodec_fill_audio_frame(frame.getPeer(), nb_channels, sample_fmt, buf.getPeer(), buf_size, align);
    }
    
    public static abstract class RegisterLockMgrCallback extends Callback<RegisterLockMgrCallback> {
        public abstract int apply(Pointer<Pointer<?>> mutex, int op);
    }
    
    @Library("avcodec")
    private static class Lib {
        static {
            BridJ.register();
        }

        public static native int avcodec_version();
        public static native void avcodec_register_all();
        public static native Pointer<?> avcodec_find_decoder(int id);
        public static native Pointer<?> avcodec_find_decoder_by_name(Pointer<Byte> name);
        public static native Pointer<?> avcodec_find_encoder(int id);
        public static native Pointer<?> avcodec_find_encoder_by_name(Pointer<Byte> name);
        public static native Pointer<?> avcodec_alloc_context3(Pointer<?> codec);
        public static native Pointer<?> avcodec_alloc_frame();
        @Optional
        public static native void avcodec_free_frame(@Ptr long frame);
        public static native void avcodec_get_frame_defaults(@Ptr long pic);
        public static native void av_init_packet(@Ptr long pkt);
        public static native int av_new_packet(@Ptr long pkt, int size);
        public static native void av_free_packet(@Ptr long pkt);
        public static native int av_grow_packet(@Ptr long pkt, int grow_by);
        public static native void av_shrink_packet(@Ptr long pkt, int size);
        public static native int avcodec_get_context_defaults3(@Ptr long s, @Ptr long codec);
        @Optional
	public static native int avcodec_open(@Ptr long avctx, @Ptr long codec);
        @Optional
	public static native int avcodec_open2(@Ptr long avctx, @Ptr long codec, @Ptr long options);
        public static native int avcodec_close(@Ptr long avctx);
        public static native int av_lockmgr_register(@Ptr long cb);
        public static native int avcodec_decode_video2(@Ptr long avctx, @Ptr long picture, @Ptr long got_picture_ptr, @Ptr long avpkt);
        @Optional
	public static native int avcodec_encode_video(@Ptr long avctx, @Ptr long buf, int buf_size, @Ptr long pict);
        @Optional
	public static native int avcodec_encode_video2(@Ptr long avctx, @Ptr long avpkt, @Ptr long frame, @Ptr long got_packet_ptr);
        @Optional
	public static native int avcodec_decode_audio3(@Ptr long avctx, @Ptr long samples, @Ptr long frame_size_ptr, @Ptr long avpkt);
        @Optional
	public static native int avcodec_decode_audio4(@Ptr long avctx, @Ptr long frame, @Ptr long got_frame_ptr, @Ptr long avpkt);
        @Optional
	public static native int avcodec_encode_audio(@Ptr long avctx, @Ptr long buf, int buf_size, @Ptr long samples);
        @Optional
	public static native int avcodec_encode_audio2(@Ptr long avctx, @Ptr long avpkt, @Ptr long frame, @Ptr long got_packet_ptr);
        public static native int avpicture_get_size(int pix_fmt, int width, int height);
        public static native int avpicture_fill(@Ptr long picture, @Ptr long ptr, int pix_fmt, int width, int height);
        public static native int avpicture_layout(@Ptr long src, int pix_fmt, int width, int height, @Ptr long dest, int dest_size);
        public static native Pointer<?> av_audio_resample_init(int output_channels, int input_channels, int output_rate, int input_rate, int sample_fmt_out, int sample_fmt_in, int filter_length, int log2_phase_count, int linear, double cutoff);
        public static native int audio_resample(@Ptr long s, @Ptr long output, @Ptr long input, int nb_samples);
        public static native void audio_resample_close(@Ptr long s);
        @Optional
        public static native int avcodec_fill_audio_frame(@Ptr long frame, int nb_channels, int sample_fmt, @Ptr long buf, int buf_size, int align);
    }
    
}

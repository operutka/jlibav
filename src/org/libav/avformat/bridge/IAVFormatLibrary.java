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
package org.libav.avformat.bridge;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * Interface to provide access to the native avformat library. The methods'
 * documentation has been taken from the original Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public interface IAVFormatLibrary extends Library {
    
    public static final int AVIO_FLAG_READ = 1;
    public static final int AVIO_FLAG_WRITE = 2;
    public static final int AVIO_FLAG_READ_WRITE = AVIO_FLAG_READ | AVIO_FLAG_WRITE;
    public static final int AVIO_FLAG_NONBLOCK = 8;
    
    public static final int AVFMT_NOFILE = 0x0001;
    public static final int AVFMT_NEEDNUMBER = 0x0002;
    public static final int AVFMT_SHOW_IDS = 0x0008;
    public static final int AVFMT_RAWPICTURE = 0x0020;
    public static final int AVFMT_GLOBALHEADER = 0x0040;
    public static final int AVFMT_NOTIMESTAMPS = 0x0080;
    public static final int AVFMT_GENERIC_INDEX = 0x0100;
    public static final int AVFMT_TS_DISCONT = 0x0200;
    public static final int AVFMT_VARIABLE_FPS = 0x0400;
    public static final int AVFMT_NODIMENSIONS = 0x0800;
    public static final int AVFMT_NOSTREAMS = 0x1000;
    public static final int AVFMT_NOBINSEARCH = 0x2000;
    public static final int AVFMT_NOGENSEARCH = 0x4000;
    public static final int AVFMT_NO_BYTE_SEEK = 0x8000;
    
    /**
     * Get version of the avformat library. Bits 23 to 16 represents major
     * version, bits 15 to 8 are for minor version and bits 7 to 0 are for
     * micro version.
     * 
     * @return version of the avformat library
     */
    int avformat_version();
    
    /**
     * Initialize libavformat and register all the muxers, demuxers and
     * protocols.
     * 
     * If you do not call this function, then you can select exactly which 
     * formats you want to support.
     */
    void av_register_all();
    
    /**
     * Do global initialization of network components. 
     * 
     * This is optional, but recommended, since it avoids the overhead of 
     * implicitly doing the setup for each session.
     * 
     * Calling this function will become mandatory if using network protocols 
     * at some major version bump.
     * 
     * NOTE: present since 53.13.0
     * 
     * @return version
     */
    int avformat_network_init();
    
    /**
     * Open a media file as input. 
     * The codecs are not opened. Only the file header (if present) is read.
     * 
     * @deprecated since 53.2.0; use avformat_open_input instead
     * @param ps the opened media file handle is put here
     * @param filename filename to open
     * @param fmt if non-NULL, force the file format to use
     * @param buf_size optional buffer size (zero if default is OK)
     * @param ap additional parameters needed when opening the file (NULL if 
     * default)
     * @return 0 if OK, AVERROR_xxx otherwise
     */
    int av_open_input_file(PointerByReference ps, Pointer filename, Pointer fmt, int buf_size, Pointer ap);
    
    /**
     * Open an input stream and read the header. 
     * The codecs are not opened. The stream must be closed with 
     * av_close_input_file().
     * 
     * NOTE:
     * If you want to use custom IO, preallocate the format context and set its 
     * pb field.
     * 
     * @param ps Pointer to user-supplied AVFormatContext (allocated by 
     * avformat_alloc_context). May be a pointer to NULL, in which case an 
     * AVFormatContext is allocated by this function and written into ps. 
     * Note that a user-supplied AVFormatContext will be freed on failure.
     * @param filename name of the stream to open
     * @param fmt If non-NULL, this parameter forces a specific input format. 
     * Otherwise the format is autodetected.
     * @param options A dictionary filled with AVFormatContext and 
     * demuxer-private options. On return this parameter will be destroyed and 
     * replaced with a dict containing options that were not found. May be NULL.
     * @return 0 on success, a negative AVERROR on failure
     */
    int avformat_open_input(PointerByReference ps, Pointer filename, Pointer fmt, PointerByReference options);
    
    /**
     * Close a media file (but not its codecs).
     * 
     * @param s media file handle
     */
    void av_close_input_file(Pointer s);
    
    /**
     * Close an opened input AVFormatContext. Free it and all its contents and 
     * set *s to NULL.
     * 
     * @param ps a double pointer to AVFormatContext
     */
    void avformat_close_input(PointerByReference ps);
    
    /**
     * Allocate an AVFormatContext.
     * 
     * @return a pointer to AVFormatContext, null otherwise
     */
    Pointer avformat_alloc_context();
    
    /**
     * Free the given AVFormatContext and all its streams.
     * 
     * @param s a context to free
     */
    void avformat_free_context(Pointer s);
    
    /**
     * Return the output format in the list of registered output formats which 
     * best matches the provided parameters, or return NULL if there is no 
     * match.
     * 
     * @param shortName if non-NULL checks if shortName matches with the names 
     * of the registered formats
     * @param filename if non-NULL checks if filename terminates with the 
     * extensions of the registered formats
     * @param mimeType if non-NULL checks if mime_type matches with the MIME 
     * type of the registered formats
     * @return 
     */
    Pointer av_guess_format(Pointer shortName, Pointer filename, Pointer mimeType);
    
    /**
     * Create and initialize a AVIOContext for accessing the resource indicated 
     * by url.
     * 
     * NOTE:
     * When the resource indicated by url has been opened in read+write mode, 
     * the AVIOContext can be used only for writing.
     * 
     * @param avioContext Used to return the pointer to the created AVIOContext. 
     * In case of failure the pointed to value is set to NULL.
     * @param url
     * @param flags flags which control how the resource indicated by url is to 
     * be opened
     * @return 0 in case of success, a negative value corresponding to an 
     * AVERROR code in case of failure
     */
    int avio_open(PointerByReference avioContext, Pointer url, int flags);
    
    /**
     * Close the resource accessed by the AVIOContext s and free it. 
     * 
     * This function can only be used if avioContext was opened by avio_open().
     * 
     * @param avioContext 
     * @return 0 on success, an AVERROR &lt; 0 on error
     */
    int avio_close(Pointer avioContext);
    
    /**
     * Read packets of a media file to get stream information.
     * 
     * This is useful for file formats with no headers such as MPEG. This 
     * function also computes the real framerate in case of MPEG-2 repeat frame 
     * mode. The logical file position is not changed by this function; examined 
     * packets may be buffered for later processing.
     * 
     * @deprecated since 53.3.0; use avformat_find_stream_info
     * @param ic media file handle
     * @return >=0 if OK, AVERROR_xxx on error
     */
    int av_find_stream_info(Pointer ic);
    
    /**
     * Read packets of a media file to get stream information. 
     * This is useful for file formats with no headers such as MPEG. This 
     * function also computes the real framerate in case of MPEG-2 repeat frame 
     * mode. The logical file position is not changed by this function; examined 
     * packets may be buffered for later processing.
     * 
     * NOTE:
     * This function isn't guaranteed to open all the codecs, so options being 
     * non-empty at return is a perfectly normal behavior.
     * 
     * @param ic media file handle
     * @param options If non-NULL, an ic.nb_streams long array of pointers to 
     * dictionaries, where i-th member contains options for codec corresponding 
     * to i-th stream. On return each dictionary will be filled with options 
     * that were not found.
     * @return >=0 if OK, AVERROR_xxx on error
     */
    int avformat_find_stream_info(Pointer ic, PointerByReference options);
    
    /**
     * Add a new stream to a media file.
     * 
     * When demuxing, it is called by the demuxer in read_header(). If the flag 
     * AVFMTCTX_NOHEADER is set in s.ctx_flags, then it may also be called in 
     * read_packet().
     * 
     * When muxing, should be called by the user before avformat_write_header().
     * 
     * @param s format context
     * @param c If non-NULL, the AVCodecContext corresponding to the new stream 
     * will be initialized to use this codec. This is needed for e.g. 
     * codec-specific defaults to be set, so codec should be provided if it is 
     * known.
     * @return newly created stream or NULL on error
     */
    Pointer avformat_new_stream(Pointer s, Pointer c);
    
    /**
     * Add a new stream to a media file. 
     * 
     * Can only be called in the read_header() function. If the flag 
     * AVFMTCTX_NOHEADER is in the format context, then new streams can be 
     * added in read_packet too.
     * 
     * @deprecated since 53.10.0; Use avformat_new_stream() instead.
     * 
     * @param s media file handle
     * @param id file-format-dependent stream ID
     * @return newly created stream or NULL on error
     */
    Pointer av_new_stream(Pointer s, int id);
    
    /**
     * Return the next frame of a stream. 
     * 
     * This function returns what is stored in the file, and does not validate 
     * that what is there are valid frames for the decoder. It will split what 
     * is stored in the file into frames and return one for each call. It will 
     * not omit invalid data between valid frames so as to give the decoder the 
     * maximum information possible for decoding.
     * 
     * The returned packet is valid until the next av_read_frame() or until 
     * av_close_input_file() and must be freed with av_free_packet. For video, 
     * the packet contains exactly one frame. For audio, it contains an integer 
     * number of frames if each frame has a known fixed size (e.g. PCM or ADPCM 
     * data). If the audio frames have a variable size (e.g. MPEG audio), then 
     * it contains one frame.
     * 
     * pkt->pts, pkt->dts and pkt->duration are always set to correct values in 
     * AVStream.time_base units (and guessed if the format cannot provide them). 
     * pkt->pts can be AV_NOPTS_VALUE if the video format has B-frames, so it is 
     * better to rely on pkt->dts if you do not decompress the payload.
     * 
     * @param s
     * @param pkt
     * @return 0 if OK, &lt; 0 on error or end of file
     */
    int av_read_frame(Pointer s, Pointer pkt);
    
    /**
     * Play a network-based stream (e.g. RTSP stream).
     * 
     * @param s format context
     * @return &lt; 0 if error
     */
    int av_read_play(Pointer s);
    
    /**
     * Pause a network-based stream (e.g. RTSP stream).
     * 
     * @param s format context
     * @return &lt; 0 if error
     */
    int av_read_pause(Pointer s);
    
    /**
     * Write a packet to an output media file. 
     * 
     * The packet shall contain one audio or video frame. The packet must be 
     * correctly interleaved according to the container specification, if not 
     * then av_interleaved_write_frame must be used.
     * 
     * @param s media file handle
     * @param pkt the packet, which contains the stream_index, buf/buf_size, 
     * dts/pts, ...
     * @return &lt; 0 on error, = 0 if OK, 1 if end of stream wanted
     */
    int av_write_frame(Pointer s, Pointer pkt);
    
    /**
     * Write a packet to an output media file ensuring correct interleaving.
     * 
     * The packet must contain one audio or video frame. If the packets are 
     * already correctly interleaved, the application should call 
     * av_write_frame() instead as it is slightly faster. It is also important 
     * to keep in mind that completely non-interleaved input will need huge 
     * amounts of memory to interleave with this, so it is preferable to 
     * interleave at the demuxer level.
     * 
     * @param s media file handle
     * @param pkt the packet, which contains the stream_index, buf/buf_size, 
     * dts/pts, ...
     * @return &lt; 0 on error, = 0 if OK, 1 if end of stream wanted
     */
    int av_interleaved_write_frame(Pointer s, Pointer pkt);
    
    /**
     * Allocate the stream private data and write the stream header to an output 
     * media file.
     * 
     * @param s Media file handle, must be allocated with 
     * avformat_alloc_context(). Its oformat field must be set to the desired 
     * output format; Its pb field must be set to an already openened 
     * AVIOContext.
     * @param options An AVDictionary filled with AVFormatContext and 
     * muxer-private options. On return this parameter will be destroyed and 
     * replaced with a dict containing options that were not found. May be NULL.
     * @return 0 on success, negative AVERROR on failure
     */
    int avformat_write_header(Pointer s, PointerByReference options);
    
    /**
     * Allocate the stream private data and write the stream header to an
     * output media file.
     * 
     * NOTE:
     * This sets stream time-bases, if possible to stream->codec->time_base
     * but for some formats it might also be some other time base.
     *
     * @param s media file handle
     * @return 0 if OK, AVERROR_xxx on error
     * 
     * @deprecated since 53.2.0; use avformat_write_header
     */
    int av_write_header(Pointer s);
    
    /**
     * Write the stream trailer to an output media file and free the file 
     * private data.
     * 
     * May only be called after a successful call to av_write_header.
     * 
     * @param s media file handle
     * @return 0 if OK, AVERROR_xxx on error
     */
    int av_write_trailer(Pointer s);
    
    /**
     * Generate an SDP for an RTP session.
     * 
     * @param ac array of AVFormatContexts describing the RTP streams. If the 
     * array is composed by only one context, such context can contain multiple 
     * AVStreams (one AVStream per RTP stream). Otherwise, all the contexts in 
     * the array (an AVCodecContext per RTP stream) must contain only one 
     * AVStream.
     * @param n_files number of AVCodecContexts contained in ac
     * @param buf buffer where the SDP will be stored (must be allocated by the 
     * caller)
     * @param size the size of the buffer
     * @return 0 if OK, AVERROR_xxx on error
     */
    int av_sdp_create(Pointer ac[], int n_files, Pointer buf, int size);
    
    /**
     * Seek to timestamp ts. 
     * 
     * Seeking will be done so that the point from which all active streams can 
     * be presented successfully will be closest to ts and within min/max_ts. 
     * Active streams are all streams that have 
     * AVStream.discard &lt; AVDISCARD_ALL.
     * 
     * If flags contain AVSEEK_FLAG_BYTE, then all timestamps are in bytes and 
     * are the file position (this may not be supported by all demuxers). If 
     * flags contain AVSEEK_FLAG_FRAME, then all timestamps are in frames in 
     * the stream with stream_index (this may not be supported by all demuxers). 
     * Otherwise all timestamps are in units of the stream selected by 
     * stream_index or if stream_index is -1, in AV_TIME_BASE units. If flags 
     * contain AVSEEK_FLAG_ANY, then non-keyframes are treated as keyframes 
     * (this may not be supported by all demuxers).
     * 
     * @param s format context
     * @param streamIndex index of the stream which is used as time base 
     * reference
     * @param minTs smallest acceptable timestamp
     * @param ts target timestamp
     * @param maxTs largest acceptable timestamp
     * @param flags flags
     * @return >= 0 on success, error code otherwise
     */
    int avformat_seek_file(Pointer s, int streamIndex, long minTs, long ts, long maxTs, int flags);
}

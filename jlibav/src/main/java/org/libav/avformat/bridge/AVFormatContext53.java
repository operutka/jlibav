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

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVFormatContext struct from the libavformat v53.x.x. For
 * details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVFormatContext53 extends StructObject {

    public static final int AVFMT_INFINITEOUTPUTLOOP = (int)0;
    public static final int AVFMT_FLAG_IGNIDX = (int)2;
    public static final int AVFMT_FLAG_NOFILLIN = (int)16;
    public static final int FF_FDEBUG_TS = (int)1;
    public static final int AVFMT_FLAG_GENPTS = (int)1;
    public static final int AVFMT_FLAG_IGNDTS = (int)8;
    public static final int AVFMT_FLAG_NONBLOCK = (int)4;
    public static final int AVFMT_FLAG_RTP_HINT = (int)64;
    public static final int AVFMT_FLAG_CUSTOM_IO = (int)128;
    public static final int RAW_PACKET_BUFFER_SIZE = (int)2500000;
    public static final int AVFMT_NOOUTPUTLOOP = (int)-1;
    public static final int AVFMT_FLAG_NOPARSE = (int)32;
    
    public AVFormatContext53() {
        super();
    }
    
    public AVFormatContext53(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<?> av_class() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVFormatContext53 av_class(Pointer<?> av_class) {
        this.io.setPointerField(this, 0, av_class);
        return this;
    }

    @Field(1)
    public Pointer<?> iformat() {
        return this.io.getPointerField(this, 1);
    }

    @Field(1)
    public AVFormatContext53 iformat(Pointer<?> iformat) {
        this.io.setPointerField(this, 1, iformat);
        return this;
    }

    @Field(2)
    public Pointer<?> oformat() {
        return this.io.getPointerField(this, 2);
    }

    @Field(2)
    public AVFormatContext53 oformat(Pointer<?> oformat) {
        this.io.setPointerField(this, 2, oformat);
        return this;
    }

    @Field(3)
    public Pointer<?> priv_data() {
        return this.io.getPointerField(this, 3);
    }

    @Field(3)
    public AVFormatContext53 priv_data(Pointer<?> priv_data) {
        this.io.setPointerField(this, 3, priv_data);
        return this;
    }

    @Field(4)
    public Pointer<?> pb() {
        return this.io.getPointerField(this, 4);
    }

    @Field(4)
    public AVFormatContext53 pb(Pointer<?> pb) {
        this.io.setPointerField(this, 4, pb);
        return this;
    }

    @Field(5)
    public int nb_streams() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public AVFormatContext53 nb_streams(int nb_streams) {
        this.io.setIntField(this, 5, nb_streams);
        return this;
    }

    @Field(6)
    public Pointer<Pointer<?>> streams() {
        return this.io.getPointerField(this, 6);
    }

    @Field(6)
    public AVFormatContext53 streams(Pointer<Pointer<?>> streams) {
        this.io.setPointerField(this, 6, streams);
        return this;
    }

    @Array({1024})
    @Field(7)
    public Pointer<Byte> filename() {
        return this.io.getPointerField(this, 7);
    }

    @Field(8)
    public long timestamp() {
        return this.io.getLongField(this, 8);
    }

    @Field(8)
    public AVFormatContext53 timestamp(long timestamp) {
        this.io.setLongField(this, 8, timestamp);
        return this;
    }

    @Field(9)
    public int ctx_flags() {
        return this.io.getIntField(this, 9);
    }

    @Field(9)
    public AVFormatContext53 ctx_flags(int ctx_flags) {
        this.io.setIntField(this, 9, ctx_flags);
        return this;
    }

    @Field(10)
    public Pointer<?> packet_buffer() {
        return this.io.getPointerField(this, 10);
    }

    @Field(10)
    public AVFormatContext53 packet_buffer(Pointer<?> packet_buffer) {
        this.io.setPointerField(this, 10, packet_buffer);
        return this;
    }

    @Field(11)
    public long start_time() {
        return this.io.getLongField(this, 11);
    }

    @Field(11)
    public AVFormatContext53 start_time(long start_time) {
        this.io.setLongField(this, 11, start_time);
        return this;
    }

    @Field(12)
    public long duration() {
        return this.io.getLongField(this, 12);
    }

    @Field(12)
    public AVFormatContext53 duration(long duration) {
        this.io.setLongField(this, 12, duration);
        return this;
    }

    @Field(13)
    public long file_size() {
        return this.io.getLongField(this, 13);
    }

    @Field(13)
    public AVFormatContext53 file_size(long file_size) {
        this.io.setLongField(this, 13, file_size);
        return this;
    }

    @Field(14)
    public int bit_rate() {
        return this.io.getIntField(this, 14);
    }

    @Field(14)
    public AVFormatContext53 bit_rate(int bit_rate) {
        this.io.setIntField(this, 14, bit_rate);
        return this;
    }

    @Field(15)
    public Pointer<?> cur_st() {
        return this.io.getPointerField(this, 15);
    }

    @Field(15)
    public AVFormatContext53 cur_st(Pointer<?> cur_st) {
        this.io.setPointerField(this, 15, cur_st);
        return this;
    }

    @Field(16)
    public long data_offset() {
        return this.io.getLongField(this, 16);
    }

    @Field(16)
    public AVFormatContext53 data_offset(long data_offset) {
        this.io.setLongField(this, 16, data_offset);
        return this;
    }

    @Field(17)
    public int mux_rate() {
        return this.io.getIntField(this, 17);
    }

    @Field(17)
    public AVFormatContext53 mux_rate(int mux_rate) {
        this.io.setIntField(this, 17, mux_rate);
        return this;
    }

    @Field(18)
    public int packet_size() {
        return this.io.getIntField(this, 18);
    }

    @Field(18)
    public AVFormatContext53 packet_size(int packet_size) {
        this.io.setIntField(this, 18, packet_size);
        return this;
    }

    @Field(19)
    public int preload() {
        return this.io.getIntField(this, 19);
    }

    @Field(19)
    public AVFormatContext53 preload(int preload) {
        this.io.setIntField(this, 19, preload);
        return this;
    }

    @Field(20)
    public int max_delay() {
        return this.io.getIntField(this, 20);
    }

    @Field(20)
    public AVFormatContext53 max_delay(int max_delay) {
        this.io.setIntField(this, 20, max_delay);
        return this;
    }

    @Field(21)
    public int loop_output() {
        return this.io.getIntField(this, 21);
    }

    @Field(21)
    public AVFormatContext53 loop_output(int loop_output) {
        this.io.setIntField(this, 21, loop_output);
        return this;
    }

    @Field(22)
    public int flags() {
        return this.io.getIntField(this, 22);
    }

    @Field(22)
    public AVFormatContext53 flags(int flags) {
        this.io.setIntField(this, 22, flags);
        return this;
    }

    @Field(23)
    public int loop_input() {
        return this.io.getIntField(this, 23);
    }

    @Field(23)
    public AVFormatContext53 loop_input(int loop_input) {
        this.io.setIntField(this, 23, loop_input);
        return this;
    }

    @Field(24)
    public int probesize() {
        return this.io.getIntField(this, 24);
    }

    @Field(24)
    public AVFormatContext53 probesize(int probesize) {
        this.io.setIntField(this, 24, probesize);
        return this;
    }

    @Field(25)
    public int max_analyze_duration() {
        return this.io.getIntField(this, 25);
    }

    @Field(25)
    public AVFormatContext53 max_analyze_duration(int max_analyze_duration) {
        this.io.setIntField(this, 25, max_analyze_duration);
        return this;
    }

    @Field(26)
    public Pointer<Byte> key() {
        return this.io.getPointerField(this, 26);
    }

    @Field(26)
    public AVFormatContext53 key(Pointer<Byte> key) {
        this.io.setPointerField(this, 26, key);
        return this;
    }

    @Field(27)
    public int keylen() {
        return this.io.getIntField(this, 27);
    }

    @Field(27)
    public AVFormatContext53 keylen(int keylen) {
        this.io.setIntField(this, 27, keylen);
        return this;
    }

    @Field(28)
    public int nb_programs() {
        return this.io.getIntField(this, 28);
    }

    @Field(28)
    public AVFormatContext53 nb_programs(int nb_programs) {
        this.io.setIntField(this, 28, nb_programs);
        return this;
    }

    @Field(29)
    public Pointer<Pointer<?>> programs() {
        return this.io.getPointerField(this, 29);
    }

    @Field(29)
    public AVFormatContext53 programs(Pointer<Pointer<?>> programs) {
        this.io.setPointerField(this, 29, programs);
        return this;
    }

    @Field(30)
    public int video_codec_id() {
        return this.io.getIntField(this, 30);
    }

    @Field(30)
    public AVFormatContext53 video_codec_id(int video_codec_id) {
        this.io.setIntField(this, 30, video_codec_id);
        return this;
    }

    @Field(31)
    public int audio_codec_id() {
        return this.io.getIntField(this, 31);
    }

    @Field(31)
    public AVFormatContext53 audio_codec_id(int audio_codec_id) {
        this.io.setIntField(this, 31, audio_codec_id);
        return this;
    }

    @Field(32)
    public int subtitle_codec_id() {
        return this.io.getIntField(this, 32);
    }

    @Field(32)
    public AVFormatContext53 subtitle_codec_id(int subtitle_codec_id) {
        this.io.setIntField(this, 32, subtitle_codec_id);
        return this;
    }

    @Field(33)
    public int max_index_size() {
        return this.io.getIntField(this, 33);
    }

    @Field(33)
    public AVFormatContext53 max_index_size(int max_index_size) {
        this.io.setIntField(this, 33, max_index_size);
        return this;
    }

    @Field(34)
    public int max_picture_buffer() {
        return this.io.getIntField(this, 34);
    }

    @Field(34)
    public AVFormatContext53 max_picture_buffer(int max_picture_buffer) {
        this.io.setIntField(this, 34, max_picture_buffer);
        return this;
    }

    @Field(35)
    public int nb_chapters() {
        return this.io.getIntField(this, 35);
    }

    @Field(35)
    public AVFormatContext53 nb_chapters(int nb_chapters) {
        this.io.setIntField(this, 35, nb_chapters);
        return this;
    }

    @Field(36)
    public Pointer<Pointer<?>> chapters() {
        return this.io.getPointerField(this, 36);
    }

    @Field(36)
    public AVFormatContext53 chapters(Pointer<Pointer<?>> chapters) {
        this.io.setPointerField(this, 36, chapters);
        return this;
    }

    @Field(37)
    public int debug() {
        return this.io.getIntField(this, 37);
    }

    @Field(37)
    public AVFormatContext53 debug(int debug) {
        this.io.setIntField(this, 37, debug);
        return this;
    }

    @Field(38)
    public Pointer<?> raw_packet_buffer() {
        return this.io.getPointerField(this, 38);
    }

    @Field(38)
    public AVFormatContext53 raw_packet_buffer(Pointer<?> raw_packet_buffer) {
        this.io.setPointerField(this, 38, raw_packet_buffer);
        return this;
    }

    @Field(39)
    public Pointer<?> raw_packet_buffer_end() {
        return this.io.getPointerField(this, 39);
    }

    @Field(39)
    public AVFormatContext53 raw_packet_buffer_end(Pointer<?> raw_packet_buffer_end) {
        this.io.setPointerField(this, 39, raw_packet_buffer_end);
        return this;
    }

    @Field(40)
    public Pointer<?> packet_buffer_end() {
        return this.io.getPointerField(this, 40);
    }

    @Field(40)
    public AVFormatContext53 packet_buffer_end(Pointer<?> packet_buffer_end) {
        this.io.setPointerField(this, 40, packet_buffer_end);
        return this;
    }

    @Field(41)
    public Pointer<?> metadata() {
        return this.io.getPointerField(this, 41);
    }

    @Field(41)
    public AVFormatContext53 metadata(Pointer<?> metadata) {
        this.io.setPointerField(this, 41, metadata);
        return this;
    }

    @Field(42)
    public int raw_packet_buffer_remaining_size() {
        return this.io.getIntField(this, 42);
    }

    @Field(42)
    public AVFormatContext53 raw_packet_buffer_remaining_size(int raw_packet_buffer_remaining_size) {
        this.io.setIntField(this, 42, raw_packet_buffer_remaining_size);
        return this;
    }

    @Field(43)
    public long start_time_realtime() {
        return this.io.getLongField(this, 43);
    }

    @Field(43)
    public AVFormatContext53 start_time_realtime(long start_time_realtime) {
        this.io.setLongField(this, 43, start_time_realtime);
        return this;
    }

    @Field(44)
    public int fps_probe_size() {
        return this.io.getIntField(this, 44);
    }

    @Field(44)
    public AVFormatContext53 fps_probe_size(int fps_probe_size) {
        this.io.setIntField(this, 44, fps_probe_size);
        return this;
    }

}

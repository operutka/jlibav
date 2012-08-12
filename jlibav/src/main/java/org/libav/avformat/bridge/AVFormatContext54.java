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
 * Mirror of the native AVFormatContext struct from the libavformat v54.x.x.
 * This class contains only the public API fields, the real AVFormatContext
 * struct is bigger. For details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVFormatContext54 extends StructObject {

    public static final int AVFMT_FLAG_IGNIDX = (int)2;
    public static final int AVFMT_FLAG_NOFILLIN = (int)16;
    public static final int FF_FDEBUG_TS = (int)1;
    public static final int AVFMT_FLAG_GENPTS = (int)1;
    public static final int AVFMT_FLAG_IGNDTS = (int)8;
    public static final int AVFMT_FLAG_NONBLOCK = (int)4;
    public static final int AVFMT_FLAG_DISCARD_CORRUPT = (int)256;
    public static final int AVFMT_FLAG_CUSTOM_IO = (int)128;
    public static final int AVFMT_FLAG_NOPARSE = (int)32;
        
    public AVFormatContext54() {
        super();
    }

    public AVFormatContext54(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<?> av_class() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVFormatContext54 av_class(Pointer<?> av_class) {
        this.io.setPointerField(this, 0, av_class);
        return this;
    }

    @Field(1)
    public Pointer<?> iformat() {
        return this.io.getPointerField(this, 1);
    }

    @Field(1)
    public AVFormatContext54 iformat(Pointer<?> iformat) {
        this.io.setPointerField(this, 1, iformat);
        return this;
    }

    @Field(2)
    public Pointer<?> oformat() {
        return this.io.getPointerField(this, 2);
    }

    @Field(2)
    public AVFormatContext54 oformat(Pointer<?> oformat) {
        this.io.setPointerField(this, 2, oformat);
        return this;
    }

    @Field(3)
    public Pointer<?> priv_data() {
        return this.io.getPointerField(this, 3);
    }

    @Field(3)
    public AVFormatContext54 priv_data(Pointer<?> priv_data) {
        this.io.setPointerField(this, 3, priv_data);
        return this;
    }

    @Field(4)
    public Pointer<?> pb() {
        return this.io.getPointerField(this, 4);
    }

    @Field(4)
    public AVFormatContext54 pb(Pointer<?> pb) {
        this.io.setPointerField(this, 4, pb);
        return this;
    }

    @Field(5)
    public int ctx_flags() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public AVFormatContext54 ctx_flags(int ctx_flags) {
        this.io.setIntField(this, 5, ctx_flags);
        return this;
    }

    @Field(6)
    public int nb_streams() {
        return this.io.getIntField(this, 6);
    }

    @Field(6)
    public AVFormatContext54 nb_streams(int nb_streams) {
        this.io.setIntField(this, 6, nb_streams);
        return this;
    }

    @Field(7)
    public Pointer<Pointer<?>> streams() {
        return this.io.getPointerField(this, 7);
    }

    @Field(7)
    public AVFormatContext54 streams(Pointer<Pointer<?>> streams) {
        this.io.setPointerField(this, 7, streams);
        return this;
    }

    @Array({1024})
    @Field(8)
    public Pointer<Byte> filename() {
        return this.io.getPointerField(this, 8);
    }

    @Field(9)
    public long start_time() {
        return this.io.getLongField(this, 9);
    }

    @Field(9)
    public AVFormatContext54 start_time(long start_time) {
        this.io.setLongField(this, 9, start_time);
        return this;
    }

    @Field(10)
    public long duration() {
        return this.io.getLongField(this, 10);
    }

    @Field(10)
    public AVFormatContext54 duration(long duration) {
        this.io.setLongField(this, 10, duration);
        return this;
    }

    @Field(11)
    public int bit_rate() {
        return this.io.getIntField(this, 11);
    }

    @Field(11)
    public AVFormatContext54 bit_rate(int bit_rate) {
        this.io.setIntField(this, 11, bit_rate);
        return this;
    }

    @Field(12)
    public int packet_size() {
        return this.io.getIntField(this, 12);
    }

    @Field(12)
    public AVFormatContext54 packet_size(int packet_size) {
        this.io.setIntField(this, 12, packet_size);
        return this;
    }

    @Field(13)
    public int max_delay() {
        return this.io.getIntField(this, 13);
    }

    @Field(13)
    public AVFormatContext54 max_delay(int max_delay) {
        this.io.setIntField(this, 13, max_delay);
        return this;
    }

    @Field(14)
    public int flags() {
        return this.io.getIntField(this, 14);
    }

    @Field(14)
    public AVFormatContext54 flags(int flags) {
        this.io.setIntField(this, 14, flags);
        return this;
    }

    @Field(15)
    public int probesize() {
        return this.io.getIntField(this, 15);
    }

    @Field(15)
    public AVFormatContext54 probesize(int probesize) {
        this.io.setIntField(this, 15, probesize);
        return this;
    }

    @Field(16)
    public int max_analyze_duration() {
        return this.io.getIntField(this, 16);
    }

    @Field(16)
    public AVFormatContext54 max_analyze_duration(int max_analyze_duration) {
        this.io.setIntField(this, 16, max_analyze_duration);
        return this;
    }

    @Field(17)
    public Pointer<Byte> key() {
        return this.io.getPointerField(this, 17);
    }

    @Field(17)
    public AVFormatContext54 key(Pointer<Byte> key) {
        this.io.setPointerField(this, 17, key);
        return this;
    }

    @Field(18)
    public int keylen() {
        return this.io.getIntField(this, 18);
    }

    @Field(18)
    public AVFormatContext54 keylen(int keylen) {
        this.io.setIntField(this, 18, keylen);
        return this;
    }

    @Field(19)
    public int nb_programs() {
        return this.io.getIntField(this, 19);
    }

    @Field(19)
    public AVFormatContext54 nb_programs(int nb_programs) {
        this.io.setIntField(this, 19, nb_programs);
        return this;
    }

    @Field(20)
    public Pointer<Pointer<?>> programs() {
        return this.io.getPointerField(this, 20);
    }

    @Field(20)
    public AVFormatContext54 programs(Pointer<Pointer<?>> programs) {
        this.io.setPointerField(this, 20, programs);
        return this;
    }

    @Field(21)
    public int video_codec_id() {
        return this.io.getIntField(this, 21);
    }

    @Field(21)
    public AVFormatContext54 video_codec_id(int video_codec_id) {
        this.io.setIntField(this, 21, video_codec_id);
        return this;
    }

    @Field(22)
    public int audio_codec_id() {
        return this.io.getIntField(this, 22);
    }

    @Field(22)
    public AVFormatContext54 audio_codec_id(int audio_codec_id) {
        this.io.setIntField(this, 22, audio_codec_id);
        return this;
    }

    @Field(23)
    public int subtitle_codec_id() {
        return this.io.getIntField(this, 23);
    }

    @Field(23)
    public AVFormatContext54 subtitle_codec_id(int subtitle_codec_id) {
        this.io.setIntField(this, 23, subtitle_codec_id);
        return this;
    }

    @Field(24)
    public int max_index_size() {
        return this.io.getIntField(this, 24);
    }

    @Field(24)
    public AVFormatContext54 max_index_size(int max_index_size) {
        this.io.setIntField(this, 24, max_index_size);
        return this;
    }

    @Field(25)
    public int max_picture_buffer() {
        return this.io.getIntField(this, 25);
    }

    @Field(25)
    public AVFormatContext54 max_picture_buffer(int max_picture_buffer) {
        this.io.setIntField(this, 25, max_picture_buffer);
        return this;
    }

    @Field(26)
    public int nb_chapters() {
        return this.io.getIntField(this, 26);
    }

    @Field(26)
    public AVFormatContext54 nb_chapters(int nb_chapters) {
        this.io.setIntField(this, 26, nb_chapters);
        return this;
    }

    @Field(27)
    public Pointer<Pointer<?>> chapters() {
        return this.io.getPointerField(this, 27);
    }

    @Field(27)
    public AVFormatContext54 chapters(Pointer<Pointer<?>> chapters) {
        this.io.setPointerField(this, 27, chapters);
        return this;
    }

    @Field(28)
    public Pointer<?> metadata() {
        return this.io.getPointerField(this, 28);
    }

    @Field(28)
    public AVFormatContext54 metadata(Pointer<?> metadata) {
        this.io.setPointerField(this, 28, metadata);
        return this;
    }

    @Field(29)
    public long start_time_realtime() {
        return this.io.getLongField(this, 29);
    }

    @Field(29)
    public AVFormatContext54 start_time_realtime(long start_time_realtime) {
        this.io.setLongField(this, 29, start_time_realtime);
        return this;
    }

    @Field(30)
    public int fps_probe_size() {
        return this.io.getIntField(this, 30);
    }

    @Field(30)
    public AVFormatContext54 fps_probe_size(int fps_probe_size) {
        this.io.setIntField(this, 30, fps_probe_size);
        return this;
    }

    @Field(31)
    public int error_recognition() {
        return this.io.getIntField(this, 31);
    }

    @Field(31)
    public AVFormatContext54 error_recognition(int error_recognition) {
        this.io.setIntField(this, 31, error_recognition);
        return this;
    }

    @Field(32)
    public AVIOInterruptCB interrupt_callback() {
        return this.io.getNativeObjectField(this, 32);
    }

    @Field(32)
    public AVFormatContext54 interrupt_callback(AVIOInterruptCB interrupt_callback) {
        this.io.setNativeObjectField(this, 32, interrupt_callback);
        return this;
    }

    @Field(33)
    public int debug() {
        return this.io.getIntField(this, 33);
    }

    @Field(33)
    public AVFormatContext54 debug(int debug) {
        this.io.setIntField(this, 33, debug);
        return this;
    }
    
}

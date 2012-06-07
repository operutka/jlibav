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
import org.libav.avcodec.bridge.AVPacket;
import org.libav.avutil.bridge.AVRational;

/**
 * Mirror of the native AVStream struct for the libavformat v53.x.x. For details
 * see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVStream53 extends StructObject {

    public static final int MAX_PROBE_PACKETS = (int)2500;
    public static final int MAX_REORDER_DELAY = (int)16;
    public static final int MAX_STD_TIMEBASES = (int)(60 * 12 + 5);
    
    public AVStream53() {
        super();
    }

    public AVStream53(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public int index() {
        return this.io.getIntField(this, 0);
    }

    @Field(0)
    public AVStream53 index(int index) {
        this.io.setIntField(this, 0, index);
        return this;
    }

    @Field(1)
    public int id() {
        return this.io.getIntField(this, 1);
    }

    @Field(1)
    public AVStream53 id(int id) {
        this.io.setIntField(this, 1, id);
        return this;
    }

    @Field(2)
    public Pointer<?> codec() {
        return this.io.getPointerField(this, 2);
    }

    @Field(2)
    public AVStream53 codec(Pointer<?> codec) {
        this.io.setPointerField(this, 2, codec);
        return this;
    }

    @Field(3)
    public AVRational r_frame_rate() {
        return this.io.getNativeObjectField(this, 3);
    }

    @Field(3)
    public AVStream53 r_frame_rate(AVRational r_frame_rate) {
        this.io.setNativeObjectField(this, 3, r_frame_rate);
        return this;
    }

    @Field(4)
    public Pointer<?> priv_data() {
        return this.io.getPointerField(this, 4);
    }

    @Field(4)
    public AVStream53 priv_data(Pointer<?> priv_data) {
        this.io.setPointerField(this, 4, priv_data);
        return this;
    }

    @Field(5)
    public long first_dts() {
        return this.io.getLongField(this, 5);
    }

    @Field(5)
    public AVStream53 first_dts(long first_dts) {
        this.io.setLongField(this, 5, first_dts);
        return this;
    }

    @Field(6)
    public AVFrac pts() {
        return this.io.getNativeObjectField(this, 6);
    }

    @Field(6)
    public AVStream53 pts(AVFrac pts) {
        this.io.setNativeObjectField(this, 6, pts);
        return this;
    }

    @Field(7)
    public AVRational time_base() {
        return this.io.getNativeObjectField(this, 7);
    }

    @Field(7)
    public AVStream53 time_base(AVRational time_base) {
        this.io.setNativeObjectField(this, 7, time_base);
        return this;
    }

    @Field(8)
    public int pts_wrap_bits() {
        return this.io.getIntField(this, 8);
    }

    @Field(8)
    public AVStream53 pts_wrap_bits(int pts_wrap_bits) {
        this.io.setIntField(this, 8, pts_wrap_bits);
        return this;
    }

    @Field(9)
    public int stream_copy() {
        return this.io.getIntField(this, 9);
    }

    @Field(9)
    public AVStream53 stream_copy(int stream_copy) {
        this.io.setIntField(this, 9, stream_copy);
        return this;
    }

    @Field(10)
    public int discard() {
        return this.io.getIntField(this, 10);
    }

    @Field(10)
    public AVStream53 discard(int discard) {
        this.io.setIntField(this, 10, discard);
        return this;
    }

    @Field(11)
    public float quality() {
        return this.io.getFloatField(this, 11);
    }

    @Field(11)
    public AVStream53 quality(float quality) {
        this.io.setFloatField(this, 11, quality);
        return this;
    }

    @Field(12)
    public long start_time() {
        return this.io.getLongField(this, 12);
    }

    @Field(12)
    public AVStream53 start_time(long start_time) {
        this.io.setLongField(this, 12, start_time);
        return this;
    }

    @Field(13)
    public long duration() {
        return this.io.getLongField(this, 13);
    }

    @Field(13)
    public AVStream53 duration(long duration) {
        this.io.setLongField(this, 13, duration);
        return this;
    }

    @Field(14)
    public int need_parsing() {
        return this.io.getIntField(this, 14);
    }

    @Field(14)
    public AVStream53 need_parsing(int need_parsing) {
        this.io.setIntField(this, 14, need_parsing);
        return this;
    }

    @Field(15)
    public Pointer<?> parser() {
        return this.io.getPointerField(this, 15);
    }

    @Field(15)
    public AVStream53 parser(Pointer<?> parser) {
        this.io.setPointerField(this, 15, parser);
        return this;
    }

    @Field(16)
    public long cur_dts() {
        return this.io.getLongField(this, 16);
    }

    @Field(16)
    public AVStream53 cur_dts(long cur_dts) {
        this.io.setLongField(this, 16, cur_dts);
        return this;
    }

    @Field(17)
    public int last_IP_duration() {
        return this.io.getIntField(this, 17);
    }

    @Field(17)
    public AVStream53 last_IP_duration(int last_IP_duration) {
        this.io.setIntField(this, 17, last_IP_duration);
        return this;
    }

    @Field(18)
    public long last_IP_pts() {
        return this.io.getLongField(this, 18);
    }

    @Field(18)
    public AVStream53 last_IP_pts(long last_IP_pts) {
        this.io.setLongField(this, 18, last_IP_pts);
        return this;
    }

    @Field(19)
    public Pointer<?> index_entries() {
        return this.io.getPointerField(this, 19);
    }

    @Field(19)
    public AVStream53 index_entries(Pointer<?> index_entries) {
        this.io.setPointerField(this, 19, index_entries);
        return this;
    }

    @Field(20)
    public int nb_index_entries() {
        return this.io.getIntField(this, 20);
    }

    @Field(20)
    public AVStream53 nb_index_entries(int nb_index_entries) {
        this.io.setIntField(this, 20, nb_index_entries);
        return this;
    }

    @Field(21)
    public int index_entries_allocated_size() {
        return this.io.getIntField(this, 21);
    }

    @Field(21)
    public AVStream53 index_entries_allocated_size(int index_entries_allocated_size) {
        this.io.setIntField(this, 21, index_entries_allocated_size);
        return this;
    }

    @Field(22)
    public long nb_frames() {
        return this.io.getLongField(this, 22);
    }

    @Field(22)
    public AVStream53 nb_frames(long nb_frames) {
        this.io.setLongField(this, 22, nb_frames);
        return this;
    }

    @Field(23)
    public int disposition() {
        return this.io.getIntField(this, 23);
    }

    @Field(23)
    public AVStream53 disposition(int disposition) {
        this.io.setIntField(this, 23, disposition);
        return this;
    }

    @Field(24)
    public AVProbeData probe_data() {
        return this.io.getNativeObjectField(this, 24);
    }

    @Field(24)
    public AVStream53 probe_data(AVProbeData probe_data) {
        this.io.setNativeObjectField(this, 24, probe_data);
        return this;
    }

    @Array({16 + 1})
    @Field(25)
    public Pointer<Long> pts_buffer() {
        return this.io.getPointerField(this, 25);
    }

    @Field(26)
    public AVRational sample_aspect_ratio() {
        return this.io.getNativeObjectField(this, 26);
    }

    @Field(26)
    public AVStream53 sample_aspect_ratio(AVRational sample_aspect_ratio) {
        this.io.setNativeObjectField(this, 26, sample_aspect_ratio);
        return this;
    }

    @Field(27)
    public Pointer<?> metadata() {
        return this.io.getPointerField(this, 27);
    }

    @Field(27)
    public AVStream53 metadata(Pointer<?> metadata) {
        this.io.setPointerField(this, 27, metadata);
        return this;
    }

    @Field(28)
    public Pointer<Byte> cur_ptr() {
        return this.io.getPointerField(this, 28);
    }

    @Field(28)
    public AVStream53 cur_ptr(Pointer<Byte> cur_ptr) {
        this.io.setPointerField(this, 28, cur_ptr);
        return this;
    }

    @Field(29)
    public int cur_len() {
        return this.io.getIntField(this, 29);
    }

    @Field(29)
    public AVStream53 cur_len(int cur_len) {
        this.io.setIntField(this, 29, cur_len);
        return this;
    }

    @Field(30)
    public AVPacket cur_pkt() {
        return this.io.getNativeObjectField(this, 30);
    }

    @Field(30)
    public AVStream53 cur_pkt(AVPacket cur_pkt) {
        this.io.setNativeObjectField(this, 30, cur_pkt);
        return this;
    }

    @Field(31)
    public long reference_dts() {
        return this.io.getLongField(this, 31);
    }

    @Field(31)
    public AVStream53 reference_dts(long reference_dts) {
        this.io.setLongField(this, 31, reference_dts);
        return this;
    }

    @Field(32)
    public int probe_packets() {
        return this.io.getIntField(this, 32);
    }

    @Field(32)
    public AVStream53 probe_packets(int probe_packets) {
        this.io.setIntField(this, 32, probe_packets);
        return this;
    }

    @Field(33)
    public Pointer<?> last_in_packet_buffer() {
        return this.io.getPointerField(this, 33);
    }

    @Field(33)
    public AVStream53 last_in_packet_buffer(Pointer<?> last_in_packet_buffer) {
        this.io.setPointerField(this, 33, last_in_packet_buffer);
        return this;
    }

    @Field(34)
    public AVRational avg_frame_rate() {
        return this.io.getNativeObjectField(this, 34);
    }

    @Field(34)
    public AVStream53 avg_frame_rate(AVRational avg_frame_rate) {
        this.io.setNativeObjectField(this, 34, avg_frame_rate);
        return this;
    }

    @Field(35)
    public int codec_info_nb_frames() {
        return this.io.getIntField(this, 35);
    }

    @Field(35)
    public AVStream53 codec_info_nb_frames(int codec_info_nb_frames) {
        this.io.setIntField(this, 35, codec_info_nb_frames);
        return this;
    }

    @Field(36)
    public Pointer<AVStream53.Info> info() {
        return this.io.getPointerField(this, 36);
    }

    @Field(36)
    public AVStream53 info(Pointer<AVStream53.Info> info) {
        this.io.setPointerField(this, 36, info);
        return this;
    }

    public static class Info extends StructObject {
        public Info() {
            super();
        }

        public Info(Pointer pointer) {
            super(pointer);
        }

        @Field(0)
        public long last_dts() {
            return this.io.getLongField(this, 0);
        }

        @Field(0)
        public Info last_dts(long last_dts) {
            this.io.setLongField(this, 0, last_dts);
            return this;
        }

        @Field(1)
        public long duration_gcd() {
            return this.io.getLongField(this, 1);
        }

        @Field(1)
        public Info duration_gcd(long duration_gcd) {
            this.io.setLongField(this, 1, duration_gcd);
            return this;
        }

        @Field(2)
        public int duration_count() {
            return this.io.getIntField(this, 2);
        }

        @Field(2)
        public Info duration_count(int duration_count) {
            this.io.setIntField(this, 2, duration_count);
            return this;
        }

        @Array({60 * 12 + 5})
        @Field(3)
        public Pointer<Double> duration_error() {
            return this.io.getPointerField(this, 3);
        }

        @Field(4)
        public long codec_info_duration() {
            return this.io.getLongField(this, 4);
        }

        @Field(4)
        public Info codec_info_duration(long codec_info_duration) {
            this.io.setLongField(this, 4, codec_info_duration);
            return this;
        }
    }
    
}

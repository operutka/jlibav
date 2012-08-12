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

import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVInputFormat struct for the libavformat v53.x.x. For
 * details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVInputFormat53 extends StructObject {

    public AVInputFormat53() {
        super();
    }

    public AVInputFormat53(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<Byte> name() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVInputFormat53 name(Pointer<Byte> name) {
        this.io.setPointerField(this, 0, name);
        return this;
    }

    @Field(1)
    public Pointer<Byte> long_name() {
        return this.io.getPointerField(this, 1);
    }

    @Field(1)
    public AVInputFormat53 long_name(Pointer<Byte> long_name) {
        this.io.setPointerField(this, 1, long_name);
        return this;
    }

    @Field(2)
    public int priv_data_size() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public AVInputFormat53 priv_data_size(int priv_data_size) {
        this.io.setIntField(this, 2, priv_data_size);
        return this;
    }

    @Field(3)
    public Pointer<AVInputFormat53.ReadProbeCallback> read_probe() {
        return this.io.getPointerField(this, 3);
    }

    @Field(3)
    public AVInputFormat53 read_probe(Pointer<AVInputFormat53.ReadProbeCallback> read_probe) {
        this.io.setPointerField(this, 3, read_probe);
        return this;
    }

    @Field(4)
    public Pointer<AVInputFormat53.ReadHeaderCallback> read_header() {
        return this.io.getPointerField(this, 4);
    }

    @Field(4)
    public AVInputFormat53 read_header(Pointer<AVInputFormat53.ReadHeaderCallback> read_header) {
        this.io.setPointerField(this, 4, read_header);
        return this;
    }

    @Field(5)
    public Pointer<AVInputFormat53.ReadPacketCallback> read_packet() {
        return this.io.getPointerField(this, 5);
    }

    @Field(5)
    public AVInputFormat53 read_packet(Pointer<AVInputFormat53.ReadPacketCallback> read_packet) {
        this.io.setPointerField(this, 5, read_packet);
        return this;
    }

    @Field(6)
    public Pointer<AVInputFormat53.ReadCloseCallback> read_close() {
        return this.io.getPointerField(this, 6);
    }

    @Field(6)
    public AVInputFormat53 read_close(Pointer<AVInputFormat53.ReadCloseCallback> read_close) {
        this.io.setPointerField(this, 6, read_close);
        return this;
    }

    @Field(7)
    public Pointer<AVInputFormat53.ReadSeekCallback> read_seek() {
        return this.io.getPointerField(this, 7);
    }

    @Field(7)
    public AVInputFormat53 read_seek(Pointer<AVInputFormat53.ReadSeekCallback> read_seek) {
        this.io.setPointerField(this, 7, read_seek);
        return this;
    }

    @Field(8)
    public Pointer<AVInputFormat53.ReadTimestampCallback> read_timestamp() {
        return this.io.getPointerField(this, 8);
    }

    @Field(8)
    public AVInputFormat53 read_timestamp(Pointer<AVInputFormat53.ReadTimestampCallback> read_timestamp) {
        this.io.setPointerField(this, 8, read_timestamp);
        return this;
    }

    @Field(9)
    public int flags() {
        return this.io.getIntField(this, 9);
    }

    @Field(9)
    public AVInputFormat53 flags(int flags) {
        this.io.setIntField(this, 9, flags);
        return this;
    }

    @Field(10)
    public Pointer<Byte> extensions() {
        return this.io.getPointerField(this, 10);
    }

    @Field(10)
    public AVInputFormat53 extensions(Pointer<Byte> extensions) {
        this.io.setPointerField(this, 10, extensions);
        return this;
    }

    @Field(11)
    public int value() {
        return this.io.getIntField(this, 11);
    }

    @Field(11)
    public AVInputFormat53 value(int value) {
        this.io.setIntField(this, 11, value);
        return this;
    }

    @Field(12)
    public Pointer<AVInputFormat53.ReadPlayPauseCallback> read_play() {
        return this.io.getPointerField(this, 12);
    }

    @Field(12)
    public AVInputFormat53 read_play(Pointer<AVInputFormat53.ReadPlayPauseCallback> read_play) {
        this.io.setPointerField(this, 12, read_play);
        return this;
    }

    @Field(13)
    public Pointer<AVInputFormat53.ReadPlayPauseCallback> read_pause() {
        return this.io.getPointerField(this, 13);
    }

    @Field(13)
    public AVInputFormat53 read_pause(Pointer<AVInputFormat53.ReadPlayPauseCallback> read_pause) {
        this.io.setPointerField(this, 13, read_pause);
        return this;
    }

    @Field(14)
    public Pointer<Pointer<?>> codec_tag() {
        return this.io.getPointerField(this, 14);
    }

    @Field(14)
    public AVInputFormat53 codec_tag(Pointer<Pointer<?>> codec_tag) {
        this.io.setPointerField(this, 14, codec_tag);
        return this;
    }

    @Field(15)
    public Pointer<AVInputFormat53.ReadSeek2Callback> read_seek2() {
        return this.io.getPointerField(this, 15);
    }

    @Field(15)
    public AVInputFormat53 read_seek2(Pointer<AVInputFormat53.ReadSeek2Callback> read_seek2) {
        this.io.setPointerField(this, 15, read_seek2);
        return this;
    }

    @Field(16)
    public Pointer<?> metadata_conv() {
        return this.io.getPointerField(this, 16);
    }

    @Field(16)
    public AVInputFormat53 metadata_conv(Pointer<?> metadata_conv) {
        this.io.setPointerField(this, 16, metadata_conv);
        return this;
    }

    @Field(17)
    public Pointer<?> priv_class() {
        return this.io.getPointerField(this, 17);
    }

    @Field(17)
    public AVInputFormat53 priv_class(Pointer<?> priv_class) {
        this.io.setPointerField(this, 17, priv_class);
        return this;
    }

    @Field(18)
    public Pointer<?> next() {
        return this.io.getPointerField(this, 18);
    }

    @Field(18)
    public AVInputFormat53 next(Pointer<?> next) {
        this.io.setPointerField(this, 18, next);
        return this;
    }

    public static abstract class ReadProbeCallback extends Callback<ReadProbeCallback> {
        public abstract int apply(Pointer<?> probeData);
    }

    public static abstract class ReadHeaderCallback extends Callback<ReadHeaderCallback> {
        public abstract int apply(Pointer<?> formatContext, Pointer<?> ap);
    }

    public static abstract class ReadPacketCallback extends Callback<ReadPacketCallback> {
        public abstract int apply(Pointer<?> formatContext, Pointer<?> pkt);
    }

    public static abstract class ReadCloseCallback extends Callback<ReadCloseCallback> {
        public abstract int apply(Pointer<?> formatContext);
    }

    public static abstract class ReadSeekCallback extends Callback<ReadSeekCallback> {
        public abstract int apply(Pointer<?> formatContext, int stream_index, long timestamp, int flags);
    }

    public static abstract class ReadTimestampCallback extends Callback<ReadTimestampCallback> {
        public abstract long apply(Pointer<?> formatContext, int stream_index, Pointer<Long> pos, long pos_limit);
    }

    public static abstract class ReadPlayPauseCallback extends Callback<ReadPlayPauseCallback> {
        public abstract int apply(Pointer<?> formatContext);
    }

    public static abstract class ReadSeek2Callback extends Callback<ReadSeek2Callback> {
        public abstract int apply(Pointer<?> formatContext, int stream_index, long min_ts, long ts, long max_ts, int flags);
    }
    
}

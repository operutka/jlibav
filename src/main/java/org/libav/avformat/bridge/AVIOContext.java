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
import org.bridj.ann.CLong;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVIOContext struct from the libavformat v53.x.x and
 * 54.x.x. For details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVIOContext extends StructObject {

    public AVIOContext() {
        super();
    }

    public AVIOContext(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<?> av_class() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVIOContext av_class(Pointer<?> av_class) {
        this.io.setPointerField(this, 0, av_class);
        return this;
    }

    @Field(1)
    public Pointer<Byte> buffer() {
        return this.io.getPointerField(this, 1);
    }

    @Field(1)
    public AVIOContext buffer(Pointer<Byte> buffer) {
        this.io.setPointerField(this, 1, buffer);
        return this;
    }

    @Field(2)
    public int buffer_size() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public AVIOContext buffer_size(int buffer_size) {
        this.io.setIntField(this, 2, buffer_size);
        return this;
    }

    @Field(3)
    public Pointer<Byte> buf_ptr() {
        return this.io.getPointerField(this, 3);
    }

    @Field(3)
    public AVIOContext buf_ptr(Pointer<Byte> buf_ptr) {
        this.io.setPointerField(this, 3, buf_ptr);
        return this;
    }

    @Field(4)
    public Pointer<Byte> buf_end() {
        return this.io.getPointerField(this, 4);
    }

    @Field(4)
    public AVIOContext buf_end(Pointer<Byte> buf_end) {
        this.io.setPointerField(this, 4, buf_end);
        return this;
    }

    @Field(5)
    public Pointer<?> opaque() {
        return this.io.getPointerField(this, 5);
    }

    @Field(5)
    public AVIOContext opaque(Pointer<?> opaque) {
        this.io.setPointerField(this, 5, opaque);
        return this;
    }

    @Field(6)
    public Pointer<AVIOContext.PacketCallback> read_packet() {
        return this.io.getPointerField(this, 6);
    }

    @Field(6)
    public AVIOContext read_packet(Pointer<AVIOContext.PacketCallback> read_packet) {
        this.io.setPointerField(this, 6, read_packet);
        return this;
    }

    @Field(7)
    public Pointer<AVIOContext.PacketCallback> write_packet() {
        return this.io.getPointerField(this, 7);
    }

    @Field(7)
    public AVIOContext write_packet(Pointer<AVIOContext.PacketCallback> write_packet) {
        this.io.setPointerField(this, 7, write_packet);
        return this;
    }

    @Field(8)
    public Pointer<AVIOContext.SeekCallback> seek() {
        return this.io.getPointerField(this, 8);
    }

    @Field(8)
    public AVIOContext seek(Pointer<AVIOContext.SeekCallback> seek) {
        this.io.setPointerField(this, 8, seek);
        return this;
    }

    @Field(9)
    public long pos() {
        return this.io.getLongField(this, 9);
    }

    @Field(9)
    public AVIOContext pos(long pos) {
        this.io.setLongField(this, 9, pos);
        return this;
    }

    @Field(10)
    public int must_flush() {
        return this.io.getIntField(this, 10);
    }

    @Field(10)
    public AVIOContext must_flush(int must_flush) {
        this.io.setIntField(this, 10, must_flush);
        return this;
    }

    @Field(11)
    public int eof_reached() {
        return this.io.getIntField(this, 11);
    }

    @Field(11)
    public AVIOContext eof_reached(int eof_reached) {
        this.io.setIntField(this, 11, eof_reached);
        return this;
    }

    @Field(12)
    public int write_flag() {
        return this.io.getIntField(this, 12);
    }

    @Field(12)
    public AVIOContext write_flag(int write_flag) {
        this.io.setIntField(this, 12, write_flag);
        return this;
    }

    @Field(13)
    public int max_packet_size() {
        return this.io.getIntField(this, 13);
    }

    @Field(13)
    public AVIOContext max_packet_size(int max_packet_size) {
        this.io.setIntField(this, 13, max_packet_size);
        return this;
    }

    @CLong
    @Field(14)
    public long checksum() {
        return this.io.getCLongField(this, 14);
    }

    @CLong
    @Field(14)
    public AVIOContext checksum(long checksum) {
        this.io.setCLongField(this, 14, checksum);
        return this;
    }

    @Field(15)
    public Pointer<Byte> checksum_ptr() {
        return this.io.getPointerField(this, 15);
    }

    @Field(15)
    public AVIOContext checksum_ptr(Pointer<Byte> checksum_ptr) {
        this.io.setPointerField(this, 15, checksum_ptr);
        return this;
    }

    @Field(16)
    public Pointer<AVIOContext.UpdateChecksumCallback> update_checksum() {
        return this.io.getPointerField(this, 16);
    }

    @Field(16)
    public AVIOContext update_checksum(Pointer<AVIOContext.UpdateChecksumCallback> update_checksum) {
        this.io.setPointerField(this, 16, update_checksum);
        return this;
    }

    @Field(17)
    public int error() {
        return this.io.getIntField(this, 17);
    }

    @Field(17)
    public AVIOContext error(int error) {
        this.io.setIntField(this, 17, error);
        return this;
    }

    @Field(18)
    public Pointer<AVIOContext.ReadPauseCallback> read_pause() {
        return this.io.getPointerField(this, 18);
    }

    @Field(18)
    public AVIOContext read_pause(Pointer<AVIOContext.ReadPauseCallback> read_pause) {
        this.io.setPointerField(this, 18, read_pause);
        return this;
    }

    @Field(19)
    public Pointer<AVIOContext.ReadSeekCallback> read_seek() {
        return this.io.getPointerField(this, 19);
    }

    @Field(19)
    public AVIOContext read_seek(Pointer<AVIOContext.ReadSeekCallback> read_seek) {
        this.io.setPointerField(this, 19, read_seek);
        return this;
    }

    @Field(20)
    public int seekable() {
        return this.io.getIntField(this, 20);
    }

    @Field(20)
    public AVIOContext seekable(int seekable) {
        this.io.setIntField(this, 20, seekable);
        return this;
    }

    public static abstract class PacketCallback extends Callback<PacketCallback> {
        public abstract int apply(Pointer<?> opaque, Pointer<Byte> buf, int buf_size);
    }

    public static abstract class SeekCallback extends Callback<SeekCallback> {
        public abstract long apply(Pointer<?> opaque, long offset, int whence);
    }

    public static abstract class UpdateChecksumCallback extends Callback<UpdateChecksumCallback> {
        @CLong
        public abstract long apply(@CLong long checksum, Pointer<Byte> buf, int size);
    }

    public static abstract class ReadPauseCallback extends Callback<ReadPauseCallback> {
        public abstract int apply(Pointer<?> opaque, int pause);
    }

    public static abstract class ReadSeekCallback extends Callback<ReadSeekCallback> {
        public abstract long apply(Pointer<?> opaque, int stream_index, long timestamp, int flags);
    }
    
}

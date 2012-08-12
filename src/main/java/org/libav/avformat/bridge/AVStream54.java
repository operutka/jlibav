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
import org.bridj.ann.Field;
import org.libav.avcodec.bridge.AVPacket;
import org.libav.avutil.bridge.AVRational;

/**
 * Mirror of the native AVStream struct for the libavformat v54.x.x. This class
 * contains only the public API fields, the real AVStream struct is bigger. For
 * details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVStream54 extends StructObject {

    public AVStream54() {
        super();
    }

    public AVStream54(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public int index() {
        return this.io.getIntField(this, 0);
    }

    @Field(0)
    public AVStream54 index(int index) {
        this.io.setIntField(this, 0, index);
        return this;
    }

    @Field(1)
    public int id() {
        return this.io.getIntField(this, 1);
    }

    @Field(1)
    public AVStream54 id(int id) {
        this.io.setIntField(this, 1, id);
        return this;
    }

    @Field(2)
    public Pointer<?> codec() {
        return this.io.getPointerField(this, 2);
    }

    @Field(2)
    public AVStream54 codec(Pointer<?> codec) {
        this.io.setPointerField(this, 2, codec);
        return this;
    }

    @Field(3)
    public AVRational r_frame_rate() {
        return this.io.getNativeObjectField(this, 3);
    }

    @Field(3)
    public AVStream54 r_frame_rate(AVRational r_frame_rate) {
        this.io.setNativeObjectField(this, 3, r_frame_rate);
        return this;
    }

    @Field(4)
    public Pointer<?> priv_data() {
        return this.io.getPointerField(this, 4);
    }

    @Field(4)
    public AVStream54 priv_data(Pointer<?> priv_data) {
        this.io.setPointerField(this, 4, priv_data);
        return this;
    }

    @Field(5)
    public AVFrac pts() {
        return this.io.getNativeObjectField(this, 5);
    }

    @Field(5)
    public AVStream54 pts(AVFrac pts) {
        this.io.setNativeObjectField(this, 5, pts);
        return this;
    }

    @Field(6)
    public AVRational time_base() {
        return this.io.getNativeObjectField(this, 6);
    }

    @Field(6)
    public AVStream54 time_base(AVRational time_base) {
        this.io.setNativeObjectField(this, 6, time_base);
        return this;
    }

    @Field(7)
    public long start_time() {
        return this.io.getLongField(this, 7);
    }

    @Field(7)
    public AVStream54 start_time(long start_time) {
        this.io.setLongField(this, 7, start_time);
        return this;
    }

    @Field(8)
    public long duration() {
        return this.io.getLongField(this, 8);
    }

    @Field(8)
    public AVStream54 duration(long duration) {
        this.io.setLongField(this, 8, duration);
        return this;
    }

    @Field(9)
    public long nb_frames() {
        return this.io.getLongField(this, 9);
    }

    @Field(9)
    public AVStream54 nb_frames(long nb_frames) {
        this.io.setLongField(this, 9, nb_frames);
        return this;
    }

    @Field(10)
    public int disposition() {
        return this.io.getIntField(this, 10);
    }

    @Field(10)
    public AVStream54 disposition(int disposition) {
        this.io.setIntField(this, 10, disposition);
        return this;
    }

    @Field(11)
    public int discard() {
        return this.io.getIntField(this, 11);
    }

    @Field(11)
    public AVStream54 discard(int discard) {
        this.io.setIntField(this, 11, discard);
        return this;
    }

    @Field(12)
    public AVRational sample_aspect_ratio() {
        return this.io.getNativeObjectField(this, 12);
    }

    @Field(12)
    public AVStream54 sample_aspect_ratio(AVRational sample_aspect_ratio) {
        this.io.setNativeObjectField(this, 12, sample_aspect_ratio);
        return this;
    }

    @Field(13)
    public Pointer<?> metadata() {
        return this.io.getPointerField(this, 13);
    }

    @Field(13)
    public AVStream54 metadata(Pointer<?> metadata) {
        this.io.setPointerField(this, 13, metadata);
        return this;
    }

    @Field(14)
    public AVRational avg_frame_rate() {
        return this.io.getNativeObjectField(this, 14);
    }

    @Field(14)
    public AVStream54 avg_frame_rate(AVRational avg_frame_rate) {
        this.io.setNativeObjectField(this, 14, avg_frame_rate);
        return this;
    }

    @Field(15)
    public AVPacket attached_pic() {
        return this.io.getNativeObjectField(this, 15);
    }

    @Field(15)
    public AVStream54 attached_pic(AVPacket attached_pic) {
        this.io.setNativeObjectField(this, 15, attached_pic);
        return this;
    }
    
}

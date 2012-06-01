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

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 * Implements necessary synchronization for the IAVCodecLibrary.
 * 
 * @author Ondrej Perutka
 */
public class SynchronizedAVCodecLibrary implements IAVCodecLibrary {

    private IAVCodecLibrary lib;

    public SynchronizedAVCodecLibrary(IAVCodecLibrary lib) {
        this.lib = lib;
    }
    
    @Override
    public int avcodec_version() {
        return lib.avcodec_version();
    }

    @Override
    public void avcodec_register_all() {
        lib.avcodec_register_all();
    }

    @Override
    public Pointer avcodec_find_decoder(int codecId) {
        return lib.avcodec_find_decoder(codecId);
    }

    @Override
    public Pointer avcodec_find_decoder_by_name(Pointer name) {
        return lib.avcodec_find_decoder_by_name(name);
    }

    @Override
    public Pointer avcodec_find_encoder(int codecId) {
        return lib.avcodec_find_encoder(codecId);
    }

    @Override
    public Pointer avcodec_find_encoder_by_name(Pointer name) {
        return lib.avcodec_find_encoder_by_name(name);
    }

    @Override
    public Pointer avcodec_alloc_context3(Pointer codec) {
        return lib.avcodec_alloc_context3(codec);
    }

    @Override
    public Pointer avcodec_alloc_frame() {
        return lib.avcodec_alloc_frame();
    }

    @Override
    public void avcodec_get_frame_defaults(Pointer pic) {
        lib.avcodec_get_frame_defaults(pic);
    }

    @Override
    public void av_init_packet(Pointer packet) {
        lib.av_init_packet(packet);
    }

    @Override
    public int av_new_packet(Pointer packet, int size) {
        return lib.av_new_packet(packet, size);
    }

    @Override
    public void av_free_packet(Pointer packet) {
        lib.av_free_packet(packet);
    }

    @Override
    public synchronized int avcodec_open(Pointer context, Pointer codec) {
        return lib.avcodec_open(context, codec);
    }

    @Override
    public synchronized int avcodec_open2(Pointer context, Pointer codec, PointerByReference options) {
        return lib.avcodec_open2(context, codec, options);
    }

    @Override
    public synchronized int avcodec_close(Pointer context) {
        return lib.avcodec_close(context);
    }

    @Override
    public int avcodec_decode_video2(Pointer context, Pointer picture, IntByReference got_picture_ptr, Pointer packet) {
        return lib.avcodec_decode_video2(context, picture, got_picture_ptr, packet);
    }

    @Override
    public int avcodec_encode_video(Pointer context, Pointer buf, int buf_size, Pointer picture) {
        return lib.avcodec_encode_video(context, buf, buf_size, picture);
    }

    @Override
    public int avcodec_encode_video2(Pointer avctx, Pointer avpkt, Pointer frame, IntByReference got_packet_ptr) {
        return lib.avcodec_encode_video2(avctx, avpkt, frame, got_packet_ptr);
    }

    @Override
    public int avcodec_decode_audio3(Pointer avctx, Pointer samples, IntByReference frameSizePtr, Pointer packet) {
        return lib.avcodec_decode_audio3(avctx, samples, frameSizePtr, packet);
    }

    @Override
    public int avcodec_decode_audio4(Pointer avctx, Pointer frame, IntByReference gotFramePtr, Pointer avpkt) {
        return lib.avcodec_decode_audio4(avctx, frame, gotFramePtr, avpkt);
    }

    @Override
    public int avcodec_encode_audio(Pointer avctx, Pointer buf, int bufSize, Pointer samples) {
        return lib.avcodec_encode_audio(avctx, buf, bufSize, samples);
    }

    @Override
    public int avcodec_encode_audio2(Pointer avctx, Pointer avpkt, Pointer frame, IntByReference got_packet_ptr) {
        return lib.avcodec_encode_audio2(avctx, avpkt, frame, got_packet_ptr);
    }

    @Override
    public int avpicture_get_size(int pix_fmt, int width, int height) {
        return lib.avpicture_get_size(pix_fmt, width, height);
    }

    @Override
    public int avpicture_fill(Pointer picture, Pointer ptr, int pix_fmt, int width, int height) {
        return lib.avpicture_fill(picture, ptr, pix_fmt, width, height);
    }

    @Override
    public int avpicture_layout(Pointer picture, int pix_fmt, int width, int height, Pointer dest, int dest_size) {
        return lib.avpicture_layout(picture, pix_fmt, width, height, dest, dest_size);
    }

    @Override
    public Pointer av_audio_resample_init(int outputChannels, int inputChannels, int outputRate, int inputRate, int sampleFmtOut, int sampleFmtIn, int filterLength, int log2PhaseCount, int linear, double cutoff) {
        return lib.av_audio_resample_init(outputChannels, inputChannels, outputRate, inputRate, sampleFmtOut, sampleFmtIn, filterLength, log2PhaseCount, linear, cutoff);
    }

    @Override
    public int audio_resample(Pointer s, Pointer output, Pointer input, int nbSamples) {
        return lib.audio_resample(s, output, input, nbSamples);
    }

    @Override
    public void audio_resample_close(Pointer s) {
        lib.audio_resample_close(s);
    }
    
}

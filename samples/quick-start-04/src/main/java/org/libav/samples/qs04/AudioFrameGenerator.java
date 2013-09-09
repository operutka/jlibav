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
package org.libav.samples.qs04;

import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.audio.AudioFrameResampler;
import org.libav.avcodec.FrameWrapperFactory;
import org.libav.avcodec.IFrameWrapper;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVChannelLayout;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;
import org.libav.data.IFrameConsumer;
import org.libav.data.IFrameProducer;

/**
 * Generator for audio frames. It generates sine wave of frequency given by
 * the TONE_FREQUENCY constant.
 * 
 * @author Ondrej Perutka
 */
public class AudioFrameGenerator implements IFrameProducer {
    
    private static final AVUtilLibrary utilLib = LibraryManager.getInstance().getAVUtilLibrary();
    
    private static final int TONE_FREQUENCY = 1000;
    
    private AudioFrameResampler resampler;
    private IFrameWrapper audioFrame;
    private int sampleRate;
    
    private Pointer<Byte> frameData;
    private int frameDataSize;
    private short[] samples;
    
    private long encodedSamples;
    private long nextFrameTime;

    /**
     * Create a new audio frame generator.
     * 
     * @param sampleRate output sample rate
     * @param sampleFormat output sample format
     * @param channelLayout output channel layout
     * @throws LibavException if it is not possible to initialize generator
     */
    public AudioFrameGenerator(int sampleRate, SampleFormat sampleFormat, long channelLayout) throws LibavException {
        this.sampleRate = sampleRate;
        
        // create a new audio resampler from internal format to the given output format
        resampler = new AudioFrameResampler(AVChannelLayout.AV_CH_LAYOUT_MONO, channelLayout, 
                sampleRate, sampleRate, SampleFormat.S16, sampleFormat);
        
        FrameWrapperFactory frameFactory = FrameWrapperFactory.getInstance();
        
        // create array for audio samples
        samples = new short[AVCodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE / 2];
        // create a new Libav frame for audio samples
        audioFrame = frameFactory.allocFrame();
        // allocate data buffer for the Libav frame
        frameDataSize = AVCodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE + AVCodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE;
        frameData = utilLib.av_malloc(frameDataSize).as(Byte.class);
        if (frameData == null)
            throw new OutOfMemoryError("not enough memory to encode audio");
        
        // fill the Libav frame with the data buffer
        audioFrame.fillAudioFrame(samples.length, 1, SampleFormat.S16, frameData, frameDataSize);
        
        // number of encoded audio samples
        encodedSamples = 0;
        // presentation time stamp of the next audio frame
        nextFrameTime = 0;
    }
    
    /**
     * Release all resources.
     */
    public void dispose() {
        resampler.dispose();
        audioFrame.free();
        
        if (frameData != null)
            utilLib.av_free(frameData);
        
        frameData = null;
    }
    
    /**
     * Add frame consumer.
     * 
     * @param c frame consumer
     */
    public void addFrameConsumer(IFrameConsumer c) {
        resampler.addFrameConsumer(c);
    }

    /**
     * Remove frame consumer.
     * 
     * @param c frame consumer
     */
    public void removeFrameConsumer(IFrameConsumer c) {
        resampler.removeFrameConsumer(c);
    }
    
    /**
     * Create a new audio frame of the given duration and pass it to all 
     * frame consumers.
     * 
     * @param duration frame duration in miliseconds
     * @throws LibavException if it is not possible to create and send an audio
     * frame
     */
    public void nextFrame(int duration) throws LibavException {
        // get number of samples from the duration
        duration = sampleRate * duration / 1000;
        double sample;
        
        // fill the array of audio samples
        for (int i = 0; i < duration; i++) {
            // get exact time of the audio sample
            sample = (double)(encodedSamples + i) / sampleRate;
            // get the audio sample
            sample = Math.sin(2 * Math.PI * TONE_FREQUENCY * sample);
            // put the sample into the array
            samples[i] = (short)(Short.MAX_VALUE / 2 * sample);
        }
        // add number of current samples to the number of all encoded samples
        encodedSamples += duration;
        
        // get pointer to the first plane of the Libav frame
        Pointer<Pointer<Byte>> framePlanes = audioFrame.getData();
        Pointer<Byte> frameSamples = framePlanes.get(0);
        // fill the plane with the array of samples
        frameSamples.setShorts(samples);
        
        // set correct size of the first plane
        Pointer<Integer> frameLineSizes = audioFrame.getLineSize();
        frameLineSizes.set(0, duration * SampleFormat.S16.getBytesPerSample());
        
        // set frame's presentation time stamp
        audioFrame.setPts(nextFrameTime);
        // pass the frame to the resampler (and all its audio frame consumers)
        resampler.processFrame(this, audioFrame);
        
        // update pts of the next audio frame
        nextFrameTime = encodedSamples * 1000 / sampleRate;
    }
    
    /**
     * Get presentation time stamp of the next audio frame.
     * 
     * @return presentation time stamp in miliseconds
     */
    public long getNextFrameTime() {
        return nextFrameTime;
    }
    
}

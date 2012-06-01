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
package org.libav.audio;

/**
 * Audio frame. Holds an array of audio samples and its size in bytes.
 * 
 * NOTE:
 * The audio frame may contain more samples than the number of channels.
 * 
 * @author Ondrej Perutka
 */
public class AudioFrame {
    
    private byte[] samples;
    private int frameSize;
    private int channelCount;
    private int sampleFormat;
    private int sampleRate;

    /**
     * Create a new audio frame and set the array of audio samples. The size
     * of the new frame is equal to the samples.length.
     * 
     * @param samples audio samples
     * @param channelCount number of audio channels
     * @param sampleFormat a sample format
     * @param sampleRate sample rate
     */
    public AudioFrame(byte[] samples, int channelCount, int sampleFormat, int sampleRate) {
        this(samples, samples.length, channelCount, sampleFormat, sampleRate);
    }

    /**
     * Create a new audio frame and set the array of audio samples and the
     * frame size.
     * 
     * @param samples audio samples
     * @param frameSize a frame size
     * @param channelCount number of audio channels
     * @param sampleFormat a sample format
     * @param sampleRate sample rate
     */
    public AudioFrame(byte[] samples, int frameSize, int channelCount, int sampleFormat, int sampleRate) {
        this.samples = samples;
        this.frameSize = frameSize;
        this.channelCount = channelCount;
        this.sampleFormat = sampleFormat;
        this.sampleRate = sampleRate;
    }

    /**
     * Get audio samples.
     * 
     * @return audio samples
     */
    public byte[] getSamples() {
        return samples;
    }

    /**
     * Get frame size.
     * 
     * @return frame size
     */
    public int getFrameSize() {
        return frameSize;
    }

    /**
     * Get number of audio channels.
     * 
     * @return number of audio channels
     */
    public int getChannelCount() {
        return channelCount;
    }

    /**
     * Get sample format.
     * 
     * @return sample format
     */
    public int getSampleFormat() {
        return sampleFormat;
    }

    /**
     * Get sample rate.
     * 
     * @return sample rate
     */
    public int getSampleRate() {
        return sampleRate;
    }
    
}

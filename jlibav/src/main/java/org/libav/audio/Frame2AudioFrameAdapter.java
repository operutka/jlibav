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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.IFrameWrapper;
import org.libav.data.IFrameConsumer;

/**
 * Frame to AudioFrame adapter. It translates frame wrappers to audio frames.
 * 
 * @author Ondrej Perutka
 */
public class Frame2AudioFrameAdapter implements IFrameConsumer, IAudioFrameProducer {

    private AudioFrameResampler resampler;
    private final Set<IAudioFrameConsumer> consumers;

    /**
     * Create a new frame to audio frame adapter and set resampling parameters.
     * 
     * @param inputChannelLayout an input channel layout
     * @param outputChannelLayout an output channel layout
     * @param inputSampleRate a sample rate of the input frames
     * @param outputSampleRate a sample rate of the output frames
     * @param inputSampleFormat a sample format of the input frames
     * @param outputSampleFormat a sample format of the output frames
     * @throws LibavException if an error occurs
     */
    public Frame2AudioFrameAdapter(long inputChannelLayout, long outputChannelLayout, int inputSampleRate, int outputSampleRate, int inputSampleFormat, int outputSampleFormat) throws LibavException {
        resampler = new AudioFrameResampler(inputChannelLayout, outputChannelLayout, inputSampleRate, outputSampleRate, inputSampleFormat, outputSampleFormat);
        consumers = Collections.synchronizedSet(new HashSet<IAudioFrameConsumer>());
        
        resampler.addFrameConsumer(new ResampledFrameConsumer());
    }
    
    /**
     * Set format of input samples.
     * 
     * @param channelLayout a channel layout
     * @param sampleRate a sample rate
     * @param sampleFormat a sample format
     * @throws LibavException if an error occurs
     */
    public synchronized void setInputFormat(long channelLayout, int sampleRate, int sampleFormat) throws LibavException {
        resampler.setInputFormat(channelLayout, sampleRate, sampleFormat);
    }

    /**
     * Get expected input channel layout.
     * 
     * @return expected input channel layout
     */
    public long getInputChannelLayout() {
        return resampler.getInputChannelLayout();
    }

    /**
     * Get expected sample format of the input frames.
     * 
     * @return expected sample format of the input frames
     */
    public int getInputSampleFormat() {
        return resampler.getInputSampleFormat();
    }

    /**
     * Get expected sample rate of the input frames.
     * 
     * @return expected sample rate of the input frames
     */
    public int getInputSampleRate() {
        return resampler.getInputSampleRate();
    }
    
    /**
     * Set format of output samples.
     * 
     * @param channelLayout a channel layout
     * @param sampleRate a sample rate
     * @param sampleFormat a sample format
     * @throws LibavException if an error occurs
     */
    public synchronized void setOutputFormat(long channelLayout, int sampleRate, int sampleFormat) throws LibavException {
        resampler.setOutputFormat(channelLayout, sampleRate, sampleFormat);
    }

    /**
     * Get output channel layout.
     * 
     * @return output channel layout
     */
    public long getOutputChannelLayout() {
        return resampler.getOutputChannelLayout();
    }

    /**
     * Get sample format of the output frames.
     * 
     * @return sample format of the output frames
     */
    public int getOutputSampleFormat() {
        return resampler.getOutputSampleFormat();
    }

    /**
     * Get sample rate of the output frames.
     * 
     * @return sample rate of the output frames
     */
    public int getOutputSampleRate() {
        return resampler.getOutputSampleRate();
    }
    
    /**
     * Release all native resources.
     */
    public synchronized void dispose() {
        resampler.dispose();
    }

    @Override
    public synchronized void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
        resampler.processFrame(producer, frame);
    }

    @Override
    public void addAudioFrameConsumer(IAudioFrameConsumer consumer) {
        consumers.add(consumer);
    }

    @Override
    public void removeAudioFrameConsumer(IAudioFrameConsumer consumer) {
        consumers.remove(consumer);
    }
    
    public int getConsumerCount() {
        return consumers.size();
    }
    
    private class ResampledFrameConsumer implements IFrameConsumer {
        @Override
        public void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
            Pointer<Byte> data = frame.getData().get();
            int length = frame.getLineSize().get();
            
            AudioFrame af = new AudioFrame(data.getBytes(length), 
                    resampler.getOutputChannelCount(), 
                    resampler.getOutputSampleFormat(), 
                    resampler.getOutputSampleRate());
        
            synchronized (consumers) {
                for (IAudioFrameConsumer c : consumers)
                    c.processFrame(this, af);
            }
        }
    }
    
}

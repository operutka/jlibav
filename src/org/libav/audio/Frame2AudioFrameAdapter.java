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
import org.libav.avcodec.IResampleContextWrapper;
import org.libav.avcodec.ResampleContextWrapperFactory;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;
import org.libav.data.IFrameConsumer;

/**
 * Frame to AudioFrame adapter. It translates frame wrappers to audio frames.
 * 
 * @author Ondrej Perutka
 */
public class Frame2AudioFrameAdapter implements IFrameConsumer, IAudioFrameProducer {

    private static final AVUtilLibrary utilLib = LibraryManager.getInstance().getAVUtilLibrary();
    
    private int inputChannelCount;
    private int inputSampleRate;
    private int inputSampleFormat;
    private int inputBitsPerSample;
    
    private int outputChannelCount;
    private int outputSampleRate;
    private int outputSampleFormat;
    private int outputBitsPerSample;
    
    private IResampleContextWrapper resampleContext;
    private Pointer<Byte> resampleBuffer;
    
    private final Set<IAudioFrameConsumer> consumers;

    /**
     * Create a new frame to audio frame adapter and set resampling parameters.
     * 
     * @param inputChanelCount a number of channels of the input frames
     * @param outputChanelCount a number of channels of the output frames
     * @param inputSampleRate a sample rate of the input frames
     * @param outputSampleRate a sample rate of the output frames
     * @param inputSampleFormat a sample format of the input frames
     * @param outputSampleFormat a sample format of the output frames
     * @throws LibavException if an error occurs
     */
    public Frame2AudioFrameAdapter(int inputChanelCount, int outputChanelCount, int inputSampleRate, int outputSampleRate, int inputSampleFormat, int outputSampleFormat) throws LibavException {
        this.inputChannelCount = inputChanelCount;
        this.inputSampleRate = inputSampleRate;
        this.inputSampleFormat = inputSampleFormat;
        this.inputBitsPerSample = AVSampleFormat.getBitsPerSample(inputSampleFormat);
        
        this.outputChannelCount = outputChanelCount;
        this.outputSampleRate = outputSampleRate;
        this.outputSampleFormat = outputSampleFormat;
        this.outputBitsPerSample = AVSampleFormat.getBitsPerSample(outputSampleFormat);
        
        resampleContext = null;
        resampleBuffer = null;
        
        init();
        
        consumers = Collections.synchronizedSet(new HashSet<IAudioFrameConsumer>());
    }
    
    private void init() throws LibavException {
        if (resampleContext != null)
            resampleContext.close();
        if (resampleBuffer != null)
            utilLib.av_free(resampleBuffer);
        
        resampleContext = null;
        resampleBuffer = null;
        
        if (inputChannelCount != outputChannelCount || inputSampleFormat != outputSampleFormat || inputSampleRate != outputSampleRate) {
            resampleContext = ResampleContextWrapperFactory.getInstance().alloc(inputChannelCount, outputChannelCount, inputSampleRate, outputSampleRate, inputSampleFormat, outputSampleFormat, 16, 10, 0, 0.8);
            resampleBuffer = utilLib.av_malloc(AVCodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE + AVCodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE).as(Byte.class);
        }
    }
    
    /**
     * Set format of input samples.
     * 
     * @param chanelCount a number of channels
     * @param sampleRate a sample rate
     * @param sampleFormat a sample format
     * @throws LibavException if an error occurs
     */
    public synchronized void setInputFormat(int chanelCount, int sampleRate, int sampleFormat) throws LibavException {
        if (inputChannelCount != chanelCount || inputSampleRate != sampleRate || inputSampleFormat != sampleFormat) {
            inputChannelCount = chanelCount;
            inputSampleRate = sampleRate;
            inputSampleFormat = sampleFormat;
            inputBitsPerSample = AVSampleFormat.getBitsPerSample(sampleFormat);
            init();
        }
    }

    /**
     * Get expected number of channels of the input frames.
     * 
     * @return expected number of channels of the input frames
     */
    public int getInputChannelCount() {
        return inputChannelCount;
    }

    /**
     * Get expected sample format of the input frames.
     * 
     * @return expected sample format of the input frames
     */
    public int getInputSampleFormat() {
        return inputSampleFormat;
    }

    /**
     * Get expected sample rate of the input frames.
     * 
     * @return expected sample rate of the input frames
     */
    public int getInputSampleRate() {
        return inputSampleRate;
    }
    
    /**
     * Set format of output samples.
     * 
     * @param chanelCount a number of channels
     * @param sampleRate a sample rate
     * @param sampleFormat a sample format
     * @throws LibavException if an error occurs
     */
    public synchronized void setOutputFormat(int chanelCount, int sampleRate, int sampleFormat) throws LibavException {
        if (outputChannelCount != chanelCount || outputSampleRate != sampleRate || outputSampleFormat != sampleFormat) {
            outputChannelCount = chanelCount;
            outputSampleRate = sampleRate;
            outputSampleFormat = sampleFormat;
            outputBitsPerSample = AVSampleFormat.getBitsPerSample(sampleFormat);
            init();
        }
    }

    /**
     * Get number of channels of the output frames.
     * 
     * @return number of channels of the output frames
     */
    public int getOutputChannelCount() {
        return outputChannelCount;
    }

    /**
     * Get sample format of the output frames.
     * 
     * @return sample format of the output frames
     */
    public int getOutputSampleFormat() {
        return outputSampleFormat;
    }

    /**
     * Get sample rate of the output frames.
     * 
     * @return sample rate of the output frames
     */
    public int getOutputSampleRate() {
        return outputSampleRate;
    }
    
    /**
     * Release all native resources.
     */
    public synchronized void dispose() {
        if (resampleContext != null)
            resampleContext.close();
        
        resampleContext = null;
    }

    @Override
    public synchronized void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
        if (resampleContext == null)
            sendAudioFrame(frame.getData().get(0), frame.getLineSize().get(0));
        else {
            int fc = frame.getLineSize().get(0) / (inputChannelCount * inputBitsPerSample / 8);
            fc = resampleContext.resample(frame.getData().get(0), resampleBuffer, fc);
            sendAudioFrame(resampleBuffer, fc * outputChannelCount * outputBitsPerSample / 8);
        }
    }
    
    private void sendAudioFrame(Pointer<Byte> data, int len) throws LibavException {
        AudioFrame af = new AudioFrame(data.getBytes(len), outputChannelCount, outputSampleFormat, outputSampleRate);
        
        synchronized (consumers) {
            for (IAudioFrameConsumer c : consumers)
                c.processFrame(this, af);
        }
    }

    @Override
    public void addAudioFrameConsumer(IAudioFrameConsumer consumer) {
        consumers.add(consumer);
    }

    @Override
    public void removeAudioFrameConsumer(IAudioFrameConsumer consumer) {
        consumers.remove(consumer);
    }
    
    public int getConsumetCount() {
        return consumers.size();
    }
    
}

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
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avresample.AudioResampleContextWrapperFactory;
import org.libav.avresample.IAudioResampleContextWrapper;
import org.libav.avutil.bridge.AVChannelLayout;
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
    
    private long inputChannelLayout;
    private int inputChannelCount;
    private int inputSampleRate;
    private int inputSampleFormat;
    private int inputBytesPerSample;
    
    private long outputChannelLayout;
    private int outputChannelCount;
    private int outputSampleRate;
    private int outputSampleFormat;
    private int outputBytesPerSample;
    
    private IAudioResampleContextWrapper resampleContext;
    private Pointer<Pointer<?>> resampleBuffer;
    private int bufferSize;
    
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
        this.inputChannelLayout = inputChannelLayout;
        this.inputChannelCount = AVChannelLayout.getChannelCount(inputChannelLayout);
        this.inputSampleRate = inputSampleRate;
        this.inputSampleFormat = inputSampleFormat;
        this.inputBytesPerSample = AVSampleFormat.getBytesPerSample(inputSampleFormat);
        
        this.outputChannelLayout = outputChannelLayout;
        this.outputChannelCount = AVChannelLayout.getChannelCount(outputChannelLayout);
        this.outputSampleRate = outputSampleRate;
        this.outputSampleFormat = outputSampleFormat;
        this.outputBytesPerSample = AVSampleFormat.getBytesPerSample(outputSampleFormat);
        
        resampleContext = null;
        resampleBuffer = null;
        bufferSize = AVCodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE + AVCodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE;
        
        init();
        
        consumers = Collections.synchronizedSet(new HashSet<IAudioFrameConsumer>());
    }
    
    private void init() throws LibavException {
        if (resampleContext != null)
            resampleContext.free();
        if (resampleBuffer != null)
            utilLib.av_free(resampleBuffer);
        
        resampleContext = null;
        resampleBuffer = null;
        
        if (inputChannelLayout != outputChannelLayout || inputSampleFormat != outputSampleFormat || inputSampleRate != outputSampleRate) {
            resampleContext = AudioResampleContextWrapperFactory.getInstance().allocate();
            resampleContext.setInputChannelLayout(inputChannelLayout);
            resampleContext.setInputSampleFormat(inputSampleFormat);
            resampleContext.setInputSampleRate(inputSampleRate);
            resampleContext.setOutputChannelLayout(outputChannelLayout);
            resampleContext.setOutputSampleFormat(outputSampleFormat);
            resampleContext.setOutputSampleRate(outputSampleRate);
            resampleContext.open();
            resampleBuffer = Pointer.allocatePointer();
            resampleBuffer.set(utilLib.av_malloc(bufferSize));
        }
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
        if (inputChannelLayout != channelLayout || inputSampleRate != sampleRate || inputSampleFormat != sampleFormat) {
            inputChannelLayout = channelLayout;
            inputChannelCount = AVChannelLayout.getChannelCount(channelLayout);
            inputSampleRate = sampleRate;
            inputSampleFormat = sampleFormat;
            inputBytesPerSample = AVSampleFormat.getBytesPerSample(sampleFormat);
            init();
        }
    }

    /**
     * Get expected input channel layout.
     * 
     * @return expected input channel layout
     */
    public long getInputChannelLayout() {
        return inputChannelLayout;
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
     * @param channelLayout a channel layout
     * @param sampleRate a sample rate
     * @param sampleFormat a sample format
     * @throws LibavException if an error occurs
     */
    public synchronized void setOutputFormat(long channelLayout, int sampleRate, int sampleFormat) throws LibavException {
        if (outputChannelLayout != channelLayout || outputSampleRate != sampleRate || outputSampleFormat != sampleFormat) {
            outputChannelLayout = channelLayout;
            outputChannelCount = AVChannelLayout.getChannelCount(channelLayout);
            outputSampleRate = sampleRate;
            outputSampleFormat = sampleFormat;
            outputBytesPerSample = AVSampleFormat.getBitsPerSample(sampleFormat);
            init();
        }
    }

    /**
     * Get output channel layout.
     * 
     * @return output channel layout
     */
    public long getOutputChannelLayout() {
        return outputChannelLayout;
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
            resampleContext.free();
        if (resampleBuffer != null)
            utilLib.av_free(resampleBuffer);
        
        resampleContext = null;
        resampleBuffer = null;
    }

    @Override
    public synchronized void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
        if (resampleContext == null)
            sendAudioFrame(frame.getData().get(0), frame.getLineSize().get(0));
        else {
            int inPlaneSize = frame.getLineSize().get(0);
            int inSampleCount = inPlaneSize / (inputChannelCount * inputBytesPerSample);
            int outSampleCount = bufferSize / (outputChannelCount * outputBytesPerSample);
            outSampleCount = resampleContext.convert(resampleBuffer, bufferSize, outSampleCount, 
                    (Pointer)frame.getData(), inPlaneSize, inSampleCount);
            
            sendAudioFrame(resampleBuffer.get().as(Byte.class), outSampleCount * outputChannelCount * outputBytesPerSample);
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

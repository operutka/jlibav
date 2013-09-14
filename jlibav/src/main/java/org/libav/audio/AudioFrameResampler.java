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
import org.libav.avcodec.FrameWrapperFactory;
import org.libav.avcodec.IFrameWrapper;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avresample.AudioResampleContextWrapperFactory;
import org.libav.avresample.IAudioResampleContextWrapper;
import org.libav.avutil.bridge.AVChannelLayout;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;
import org.libav.data.IFrameConsumer;
import org.libav.data.IFrameProducer;

/**
 * Resampler for audio frames.
 * 
 * @author Ondrej Perutka
 */
public class AudioFrameResampler implements IFrameConsumer, IFrameProducer {

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
    private ResampleBuffer resampleBuffer;
    private IFrameWrapper outputFrame;
    
    private final Set<IFrameConsumer> consumers;

    /**
     * Create a new audio frame resampler and set resampling parameters.
     * 
     * @param inputChannelLayout an input channel layout
     * @param outputChannelLayout an output channel layout
     * @param inputSampleRate a sample rate of the input frames
     * @param outputSampleRate a sample rate of the output frames
     * @param inputSampleFormat a sample format of the input frames
     * @param outputSampleFormat a sample format of the output frames
     * @throws LibavException if an error occurs
     */
    public AudioFrameResampler(long inputChannelLayout, long outputChannelLayout, int inputSampleRate, int outputSampleRate, int inputSampleFormat, int outputSampleFormat) throws LibavException {
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
        outputFrame = null;
        
        init();
        
        consumers = Collections.synchronizedSet(new HashSet<IFrameConsumer>());
    }
    
    private void init() throws LibavException {
        if (resampleContext != null)
            resampleContext.free();
        if (resampleBuffer != null)
            resampleBuffer.free();
        if (outputFrame != null)
            outputFrame.free();
        
        resampleContext = null;
        resampleBuffer = null;
        outputFrame = null;
        
        if (inputChannelLayout != outputChannelLayout || inputSampleFormat != outputSampleFormat || inputSampleRate != outputSampleRate) {
            resampleContext = AudioResampleContextWrapperFactory.getInstance().allocate();
            resampleContext.setInputChannelLayout(inputChannelLayout);
            resampleContext.setInputSampleFormat(inputSampleFormat);
            resampleContext.setInputSampleRate(inputSampleRate);
            resampleContext.setOutputChannelLayout(outputChannelLayout);
            resampleContext.setOutputSampleFormat(outputSampleFormat);
            resampleContext.setOutputSampleRate(outputSampleRate);
            resampleContext.open();
            resampleBuffer = new ResampleBuffer(outputSampleFormat, outputChannelCount);
            outputFrame = FrameWrapperFactory.getInstance().allocFrame();
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
     * Get number of input channels.
     * 
     * @return number of input channels
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
            outputBytesPerSample = AVSampleFormat.getBytesPerSample(sampleFormat);
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
     * Get number of output channels.
     * 
     * @return number of output channels
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
            resampleContext.free();
        if (resampleBuffer != null)
            resampleBuffer.free();
        if (outputFrame != null)
            outputFrame.free();
        
        resampleContext = null;
        resampleBuffer = null;
        outputFrame = null;
    }
    
    @Override
    public void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
        if (resampleContext == null)
            sendFrame(frame);
        else {
            Pointer inputData = frame.getData();
            int inPlaneSize = frame.getLineSize().get(0);
            int inSampleCount = inPlaneSize / inputBytesPerSample;
            if (AVSampleFormat.isPlanar(inputSampleFormat) && inputChannelCount > frame.getDataLength())
                throw new LibavException("unsupported channel count for planar audio format");
            else if (!AVSampleFormat.isPlanar(inputSampleFormat))
                inSampleCount /= inputChannelCount;
            
            int outSampleCount = resampleContext.convert(resampleBuffer.getData(), 
                    resampleBuffer.getLineSize(), resampleBuffer.getMaxSampleCount(), 
                    inputData, inPlaneSize, inSampleCount);
            
            outputFrame.fillAudioFrame(resampleBuffer.getMaxSampleCount(), 
                    outputChannelCount, outputSampleFormat, 
                    resampleBuffer.getBuffer(), resampleBuffer.getBufferSize());
            
            int lineSize = outSampleCount * outputBytesPerSample;
            if (!AVSampleFormat.isPlanar(outputSampleFormat))
                lineSize *= outputChannelCount;
            
            outputFrame.getLineSize().set(0, lineSize);
            // there must not be any API breaking change, so we need this dirty hack
            try {
                outputFrame.setNbSamples(outSampleCount);
            } catch (UnsatisfiedLinkError ex) { }
            
            outputFrame.setKeyFrame(frame.isKeyFrame());
            outputFrame.setPacketDts(frame.getPacketDts());
            outputFrame.setPacketPts(frame.getPacketPts());
            outputFrame.setPts(frame.getPts());
            sendFrame(outputFrame);
        }
    }
    
    private void sendFrame(IFrameWrapper frame) throws LibavException {
        synchronized (consumers) {
            for (IFrameConsumer c : consumers)
                c.processFrame(this, frame);
        }
    }

    @Override
    public void addFrameConsumer(IFrameConsumer consumer) {
        consumers.add(consumer);
    }

    @Override
    public void removeFrameConsumer(IFrameConsumer consumer) {
        consumers.remove(consumer);
    }
    
    public int getConsumerCount() {
        return consumers.size();
    }
    
    private static class ResampleBuffer {
        private Pointer<Pointer<?>> data;
        private Pointer<Byte> buffer;
        private int bufferSize;
        private int lineSize;
        private int maxSampleCount;

        public ResampleBuffer(int sampleFormat, int channelCount) {
            int planes = AVSampleFormat.isPlanar(sampleFormat) ? channelCount : 1;
            
            bufferSize = AVCodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE;
            buffer = utilLib.av_malloc(bufferSize + AVCodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE).as(Byte.class);
            if (buffer == null)
                throw new OutOfMemoryError("not enough memory for the audio frame resampler");
            
            int bytesPerSample = AVSampleFormat.getBytesPerSample(sampleFormat);
            lineSize = bufferSize / planes;
            lineSize -= lineSize % bytesPerSample;
            
            data = Pointer.allocatePointers(planes);
            for (int i = 0; i < planes; i++)
                data.set(i, buffer.offset(i * lineSize));
            
            maxSampleCount = lineSize * planes / (channelCount * bytesPerSample);
        }
        
        public void free() {
            if (buffer == null)
                return;
            
            utilLib.av_free(buffer);
            
            buffer = null;
            data = null;
        }

        public Pointer<Pointer<?>> getData() {
            return data;
        }

        public int getLineSize() {
            return lineSize;
        }

        public Pointer<Byte> getBuffer() {
            return buffer;
        }
        
        public int getBufferSize() {
            return bufferSize;
        }
        
        public int getMaxSampleCount() {
            return maxSampleCount;
        }
    }
    
}

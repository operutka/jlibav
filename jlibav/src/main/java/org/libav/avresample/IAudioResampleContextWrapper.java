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
package org.libav.avresample;

import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avutil.SampleFormat;
import org.libav.bridge.IWrapper;

/**
 * Wrapper for the AVAudioResampleContextWrapper. It provides access to some of 
 * the AVAudioResampleContextWrapper properties and also it allows to work with 
 * the resample context using the Libav functions.
 * 
 * @author Ondrej Perutka
 */
public interface IAudioResampleContextWrapper extends IWrapper {
    
    /**
     * Open resample context.
     * 
     * @throws LibavException
     */
    void open() throws LibavException;
    
    /**
     * Close resample context. It also clears the internal output FIFO, delay 
     * buffer and custom mixing matrix.
     */
    void close();
    
    /**
     * Free this context and all associated resources. It also closes opened
     * context.
     */
    void free();
    
    /**
     * Get input channel layout.
     * 
     * @return channel layout
     */
    long getInputChannelLayout();
    
    /**
     * Set input channel layout.
     * 
     * @param channelLayout channel layout
     */
    void setInputChannelLayout(long channelLayout);
    
    /**
     * Get input sample format.
     * 
     * @return sample format
     */
    SampleFormat getInputSampleFormat();
    
    /**
     * Set input sample format.
     * 
     * @param sampleFormat sample format
     */
    void setInputSampleFormat(SampleFormat sampleFormat);
    
    /**
     * Get input sample rate.
     * 
     * @return sample rate
     */
    int getInputSampleRate();
    
    /**
     * Set input sample rate.
     * 
     * @param sampleRate sample rate
     */
    void setInputSampleRate(int sampleRate);
    
    /**
     * Get output channel layout.
     * 
     * @return channel layout
     */
    long getOutputChannelLayout();
    
    /**
     * Set output channel layout.
     * 
     * @param channelLayout channel layout
     */
    void setOutputChannelLayout(long channelLayout);
    
    /**
     * Get output sample format.
     * 
     * @return sample format
     */
    SampleFormat getOutputSampleFormat();
    
    /**
     * Set output sample format.
     * 
     * @param sampleFormat sample format
     */
    void setOutputSampleFormat(SampleFormat sampleFormat);
    
    /**
     * Get output sample rate.
     * 
     * @return sample rate
     */
    int getOutputSampleRate();
    
    /**
     * Set output sample rate.
     * 
     * @param sampleRate sample rate
     */
    void setOutputSampleRate(int sampleRate);
    
    /**
     * Get current mixing matrix.<br/><br/>
     * 
     * NOTE: The method may not be supported if the avresample library is not
     * present.
     * 
     * @param stride distance between adjacent input channels in the matrix 
     * array
     * @return mixing coefficients; matrix[i + stride * o] is the weight of 
     * input channel i in output channel o
     * @throws LibavException
     */
    double[] getMatrix(int stride) throws LibavException;
    
    /**
     * Set custom mixing matrix. It overrides the default mixing matrix.<br/><br/>
     * 
     * NOTE: The method may not be supported if the avresample library is not
     * present.
     * 
     * @param matrix mixing coefficients; matrix[i + stride * o] is the weight 
     * of input channel i in output channel o
     * @param stride distance between adjacent input channels in the matrix 
     * array
     * @throws LibavException
     */
    void setMatrix(double[] matrix, int stride) throws LibavException;
    
    /**
     * Convert input samples and write them to the output FIFO. The output data 
     * can be null or have fewer allocated samples than required. In this case, 
     * any remaining samples not written to the output will be added to an 
     * internal FIFO buffer, to be returned at the next call to this method.
     * 
     * At the end of the conversion process, there may be data remaining in 
     * the internal FIFO buffer. Call this method with null input to get this 
     * data as output.
     * 
     * @param output output data pointers
     * @param outPlaneSize output plane size
     * @param outSampleCount maximum number of samples that the output buffer 
     * can hold
     * @param input input data pointers
     * @param inPlaneSize input plane size
     * @param inSampleCount number of input samples to convert
     * @return number of samples written to the output buffer, not including 
     * converted samples added to the internal output FIFO
     * @throws LibavException
     */
    int convert(Pointer<Pointer<?>> output, int outPlaneSize, int outSampleCount, Pointer<Pointer<?>> input, int inPlaneSize, int inSampleCount) throws LibavException;
    
}

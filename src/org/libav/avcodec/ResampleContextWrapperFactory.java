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
package org.libav.avcodec;

import org.bridj.Pointer;
import org.libav.LibavException;

/**
 * Factory class for resample contexts.
 * 
 * @author Ondrej Perutka
 */
public class ResampleContextWrapperFactory {
    
    private static final ResampleContextWrapperFactory instance;
    
    static {
        instance = new ResampleContextWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param resampleContext pointer to an AVResampleContext struct
     * @return resample context wrapper
     */
    public IResampleContextWrapper wrap(Pointer<?> resampleContext) {
        return new ResampleContextWrapper(resampleContext);
    }
    
    /**
     * Allocate a new audio resample context.
     * 
     * @param inputChanelCount a number of input channels
     * @param outputChanelCount a number of output channels
     * @param inputSampleRate an input sample rate
     * @param outputSampleRate an output sample rate
     * @param inputSampleFormat an input sample format
     * @param outputSampleFormat an output sample format
     * @param filterLength length of each FIR filter in the filterbank relative 
     * to the cutoff frequency
     * @param log2PhaseCount log2 of the number of entries in the polyphase 
     * filterbank
     * @param linear if 1 then the used FIR filter will be linearly interpolated 
     * between the 2 closest, if 0 the closest will be used
     * @param cutoff cutoff frequency, 1.0 corresponds to half the output 
     * sampling rate
     * @return resample context wrapper
     * @throws LibavException if the resample context cannot be crated
     */
    public IResampleContextWrapper alloc(int inputChanelCount, int outputChanelCount, int inputSampleRate, int outputSampleRate, int inputSampleFormat, int outputSampleFormat, int filterLength, int log2PhaseCount, int linear, double cutoff) throws LibavException {
        return ResampleContextWrapper.allocateResampleContext(inputChanelCount, outputChanelCount, inputSampleRate, outputSampleRate, inputSampleFormat, outputSampleFormat, filterLength, log2PhaseCount, linear, cutoff);
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static ResampleContextWrapperFactory getInstance() {
        return instance;
    }
    
}

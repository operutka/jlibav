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
import org.libav.bridge.IWrapper;

/**
 * Wrapper for the AVResampleContext. It allows to work with the resample
 * context using the Libav functions.
 * 
 * @author Ondrej Perutka
 */
public interface IResampleContextWrapper extends IWrapper {
    
    /**
     * Close the resample context.
     */
    void close();
    
    /**
     * Resample the given audio buffer.
     * 
     * @param inputBuffer input buffer
     * @param outputBuffer output buffer
     * @param frameCount number of frames in the input buffer
     * @return number of frames in the output buffer
     * @throws LibavException if the input cannot be resampled
     */
    int resample(Pointer<Byte> inputBuffer, Pointer<Byte> outputBuffer, int frameCount) throws LibavException;
    
}

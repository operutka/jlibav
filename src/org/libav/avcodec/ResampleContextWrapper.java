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

import com.sun.jna.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.bridge.IAVCodecLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVResampleContext.
 * 
 * @author Ondrej Perutka
 */
public class ResampleContextWrapper implements IResampleContextWrapper {

    private static final IAVCodecLibrary codecLib = LibraryManager.getInstance().getAVCodecLibraryWrapper().getLibrary();
    
    private Pointer rc;

    public ResampleContextWrapper(Pointer resampleContext) {
        this.rc = resampleContext;
    }
    
    @Override
    public void close() {
        if (rc == null)
            return;
        
        codecLib.audio_resample_close(rc);
        rc = null;
    }
    
    @Override
    public void clearWrapperCache() {
    }

    @Override
    public Pointer getPointer() {
        return rc;
    }
    
    @Override
    public int resample(Pointer inputBuffer, Pointer outputBuffer, int frameCount) throws LibavException {
        if (rc == null)
            return 0;
        
        int len = codecLib.audio_resample(rc, outputBuffer, inputBuffer, frameCount);
        if (len == 0)
            throw new LibavException("audio resample error");
        
        return len;
    }
    
    public static ResampleContextWrapper allocateResampleContext(int inputChanelCount, int outputChanelCount, int inputSampleRate, int outputSampleRate, int inputSampleFormat, int outputSampleFormat, int filterLength, int log2PhaseCount, int linear, double cutoff) throws LibavException {
        Pointer ptr = codecLib.av_audio_resample_init(outputChanelCount, inputChanelCount, outputSampleRate, inputSampleRate, outputSampleFormat, inputSampleFormat, filterLength, log2PhaseCount, linear, cutoff);
        if (ptr == null)
            throw new LibavException("unable to create audio resample context");
        
        return new ResampleContextWrapper(ptr);
    }
    
}

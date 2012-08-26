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
import org.libav.avresample.bridge.AVResampleLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for AudioResampleContextWrapper.
 * 
 * @author Ondrej Perutka
 */
public class AudioResampleContextWrapperFactory {
    
    private static final AVResampleLibrary resLib = LibraryManager.getInstance().getAVResampleLibrary();
    
    private static final AudioResampleContextWrapperFactory instance;
    
    static {
        instance = new AudioResampleContextWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param audioResampleContext pointer to an AVAudioResampleContext struct
     * @return audio resample context wrapper
     */
    public IAudioResampleContextWrapper wrap(Pointer<?> audioResampleContext) {
        if (resLib == null)
            return new AudioResampleContextWrapperLAVC(audioResampleContext);
        
        return new AudioResampleContextWrapperLAVR(audioResampleContext);
    }
    
    /**
     * Allocate a new audio resample context.
     * 
     * @return audio resample context wrapper
     */
    public IAudioResampleContextWrapper allocate() {
        if (resLib == null)
            return AudioResampleContextWrapperLAVC.allocate();
        
        return AudioResampleContextWrapperLAVR.allocate();
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static AudioResampleContextWrapperFactory getInstance() {
        return instance;
    }
    
}

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
package org.libav.avformat;

import com.sun.jna.Pointer;
import org.libav.avformat.bridge.AVInputFormat53;
import org.libav.avformat.bridge.AVInputFormat54;
import org.libav.avformat.bridge.IAVFormatLibrary;
import org.libav.bridge.LibavLibraryWrapper;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for input format wrappers.
 * 
 * @author Ondrej Perutka
 */
public class InputFormatWrapperFactory {
    
    private static final LibavLibraryWrapper<IAVFormatLibrary> libWrapper;
    private static final InputFormatWrapperFactory instance;
    
    static {
        libWrapper = LibraryManager.getInstance().getAVFormatLibraryWrapper();
        instance = new InputFormatWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param format pointer to an AVInputFormat struct
     * @return input format wrapper
     */
    public IInputFormatWrapper wrap(Pointer format) {
        switch (libWrapper.getMajorVersion()) {
            case 53: return wrap(new AVInputFormat53(format));
            case 54: return wrap(new AVInputFormat54(format));
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavformat");
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param format AVInputFormat struct
     * @return input format wrapper
     */
    public IInputFormatWrapper wrap(AVInputFormat53 format) {
        return new InputFormatWrapper53(format);
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param format AVInputFormat struct
     * @return input format wrapper
     */
    public IInputFormatWrapper wrap(AVInputFormat54 format) {
        return new InputFormatWrapper54(format);
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static InputFormatWrapperFactory getInstance() {
        return instance;
    }
    
}

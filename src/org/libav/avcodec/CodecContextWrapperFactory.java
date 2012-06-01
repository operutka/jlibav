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
import org.libav.avcodec.bridge.AVCodecContext53;
import org.libav.avcodec.bridge.AVCodecContext54;
import org.libav.avcodec.bridge.IAVCodecLibrary;
import org.libav.bridge.LibavLibraryWrapper;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for codec context wrappers.
 * 
 * @author Ondrej Perutka
 */
public class CodecContextWrapperFactory {
    
    private static final LibavLibraryWrapper<IAVCodecLibrary> libWrapper;
    private static final CodecContextWrapperFactory instance;
    
    static {
        libWrapper = LibraryManager.getInstance().getAVCodecLibraryWrapper();
        instance = new CodecContextWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param codecContext pointer to an AVCodecContext struct
     * @return codec context wrapper
     */
    public ICodecContextWrapper wrap(Pointer codecContext) {
        switch (libWrapper.getMajorVersion()) {
            case 53: return wrap(new AVCodecContext53(codecContext));
            case 54: return wrap(new AVCodecContext54(codecContext));
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param codecContext AVCodecContext struct
     * @return codec context wrapper
     */
    public ICodecContextWrapper wrap(AVCodecContext53 codecContext) {
        return new CodecContextWrapper53(codecContext);
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param codecContext AVCodecContext struct
     * @return codec context wrapper
     */
    public ICodecContextWrapper wrap(AVCodecContext54 codecContext) {
        return new CodecContextWrapper54(codecContext);
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static CodecContextWrapperFactory getInstance() {
        return instance;
    }
    
}

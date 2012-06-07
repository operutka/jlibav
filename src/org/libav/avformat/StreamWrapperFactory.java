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

import org.bridj.Pointer;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avformat.bridge.AVStream53;
import org.libav.avformat.bridge.AVStream54;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for stream wrappers.
 * 
 * @author Ondrej Perutka
 */
public class StreamWrapperFactory {
    
    private static final AVFormatLibrary formatLib;
    private static final StreamWrapperFactory instance;
    
    static {
        formatLib = LibraryManager.getInstance().getAVFormatLibrary();
        instance = new StreamWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param stream pointer to an AVStream struct
     * @return stream wrapper
     */
    public IStreamWrapper wrap(Pointer<?> stream) {
        switch (formatLib.getMajorVersion()) {
            case 53: return wrap(new AVStream53(stream));
            case 54: return wrap(new AVStream54(stream));
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavformat");
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param stream AVStream struct
     * @return stream wrapper
     */
    public IStreamWrapper wrap(AVStream53 stream) {
        return new StreamWrapper53(stream);
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param stream AVStream struct
     * @return stream wrapper
     */
    public IStreamWrapper wrap(AVStream54 stream) {
        return new StreamWrapper54(stream);
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static StreamWrapperFactory getInstance() {
        return instance;
    }
    
}

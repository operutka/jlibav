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
import org.libav.avformat.bridge.AVIOContext;

/**
 * Factory class for IO context wrappers.
 * 
 * @author Ondrej Perutka
 */
public class IOContextWrapperFactory {
    
    private static final IOContextWrapperFactory instance;
    
    static {
        instance = new IOContextWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param ioContext pointer to an AVIOContext struct
     * @return IO context wrapper
     */
    public IIOContextWrapper wrap(Pointer<?> ioContext) {
        return wrap(new AVIOContext(ioContext));
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param ioContext AVIOContext struct
     * @return IO context wrapper
     */
    public IIOContextWrapper wrap(AVIOContext ioContext) {
        return new IOContextWrapper(ioContext);
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static IOContextWrapperFactory getInstance() {
        return instance;
    }
    
}

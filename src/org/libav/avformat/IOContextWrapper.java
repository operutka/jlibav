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
 * Wrapper class for the AVIOContext.
 * 
 * @author Ondrej Perutka
 */
public class IOContextWrapper extends AbstractIOContextWrapper {

    private AVIOContext context;

    /**
     * Create a new wrapper for the given AVIOContext.
     * 
     * @param context an AVIOContext structure
     */
    public IOContextWrapper(AVIOContext context) {
        this.context = context;
    }

    @Override
    public Pointer<?> getPointer() {
        return Pointer.pointerTo(context);
    }
    
    @Override
    public boolean isSeekable() {
        if (seekable == null) {
            int s = context.seekable();
            seekable = s == 0 ? false : true;
        }
        
        return seekable;
    }
    
}

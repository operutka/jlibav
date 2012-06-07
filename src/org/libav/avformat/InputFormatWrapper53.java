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
import org.libav.avformat.bridge.AVInputFormat53;

/**
 * Wrapper class for the AVInputFormat53.
 * 
 * @author Ondrej Perutka
 */
public class InputFormatWrapper53 extends AbstractInputFormatWrapper {

    private AVInputFormat53 format;

    /**
     * Create a new wrapper for the given AVInputFormat.
     * 
     * @param format an input format structure
     */
    public InputFormatWrapper53(AVInputFormat53 format) {
        this.format = format;
        this.flags = null;
    }
    
    @Override
    public Pointer<?> getPointer() {
        return Pointer.pointerTo(format);
    }

    @Override
    public String getName() {
        if (name == null) {
            Pointer<Byte> ptr = format.name();
            name = ptr == null ? null : ptr.getCString();
        }
        
        return name;
    }
    
    @Override
    public int getFlags() {
        if (flags == null)
            flags = format.flags();
        
        return flags;
    }

    @Override
    public void setFlags(int flags) {
        format.flags(flags);
        this.flags = flags;
    }
    
}

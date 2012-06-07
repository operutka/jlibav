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
import org.libav.avformat.bridge.AVOutputFormat54;

/**
 * Wrapper class for the AVOutputFormat54.
 * 
 * @author Ondrej Perutka
 */
public class OutputFormatWrapper54 extends AbstractOutputFormatWrapper {
    
    private AVOutputFormat54 format;
    
    /**
     * Create a new wrapper for the given AVOutputFormat.
     * 
     * @param format an output format structure
     */
    public OutputFormatWrapper54(AVOutputFormat54 format) {
        this.format = format;
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
        this.flags = flags;
        format.flags(flags);
    }
    
}

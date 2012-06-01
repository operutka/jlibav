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
package org.libav.c.bridge;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

/**
 * Interface to provide access to the native C library.
 * 
 * @author Ondrej Perutka
 */
public interface ICLibrary extends Library {
    
    /**
     * Copies the values of num bytes from the location pointed by source 
     * directly to the memory block pointed by destination.
     * 
     * @param dest pointer to the destination array where the content is to be 
     * copied
     * @param src pointer to the source of data to be copied
     * @param size number of bytes to copy
     * @return destination is returned
     */
    Pointer memcpy(Pointer dest, Pointer src, int size);
    
}

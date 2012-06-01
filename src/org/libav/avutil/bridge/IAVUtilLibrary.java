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
package org.libav.avutil.bridge;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

/**
 * Interface to provide access to the native avutil library. The methods'
 * documentation has been taken from the original Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public interface IAVUtilLibrary extends Library {
    
    public static final long AV_NOPTS_VALUE = 0x8000000000000000l;
    public static final int AV_TIME_BASE = 1000000;
    
    /**
     * Get version of the avutil library. Bits 23 to 16 represents major
     * version, bits 15 to 8 are for minor version and bits 7 to 0 are for
     * micro version.
     * 
     * @return version of the avutil library
     */
    int avutil_version();
    
    /**
     * Rescale a 64-bit integer by 2 rational numbers.
     * 
     * @param a
     * @param bq
     * @param cq
     * @return 
     */
    long av_rescale_q(long a, AVRational.ByValue bq, AVRational.ByValue cq);
    
    /**
     * Allocate a block of size bytes with alignment suitable for all memory 
     * accesses (including vectors if available on the CPU).
     * 
     * @param size size in bytes for the memory block to be allocated
     * @return pointer to the allocated block, NULL if the block cannot be 
     * allocated
     */
    Pointer av_malloc(int size);
    
    /**
     * Free a memory block which has been allocated with av_malloc(z)() 
     * or av_realloc().
     * 
     * @param ptr ptr pointer to the memory block which should be freed
     */
    void av_free(Pointer ptr);
    
    /**
     * Free a memory block which has been allocated with av_malloc(z)() or 
     * av_realloc() and set the pointer pointing to it to NULL.
     * 
     * @param ptr pointer to the pointer to the memory block which should be 
     * freed
     */
    void av_freep(Pointer ptr);
    
    /**
     * Put a description of the AVERROR code errnum in errbuf.
     * 
     * In case of failure the global variable errno is set to indicate the 
     * error. Even in case of failure av_strerror() will print a generic error 
     * message indicating the errnum provided to errbuf.
     * 
     * @param errNum error code to describe
     * @param errBuf buffer to which description is written
     * @param errbufSize the size in bytes of errbuf
     * @return 0 on success, a negative value if a description for errnum 
     * cannot be found
     */
    int av_strerror(int errNum, Pointer errBuf, int errbufSize);
    
}

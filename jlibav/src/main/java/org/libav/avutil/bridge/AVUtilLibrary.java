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

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bridj.BridJ;
import org.bridj.NativeLibrary;
import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.bridj.ann.Ptr;
import org.libav.bridge.ILibrary;

/**
 * Interface to provide access to the native avutil library. The methods'
 * documentation has been taken from the original Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public final class AVUtilLibrary implements ILibrary {
    
    public static final long AV_NOPTS_VALUE = 0x8000000000000000l;
    public static final int AV_TIME_BASE = 1000000;
    
    public static final int AV_DICT_MATCH_CASE = 1;
    public static final int AV_DICT_IGNORE_SUFFIX = 2;
    public static final int AV_DICT_DONT_STRDUP_KEY = 4;
    public static final int AV_DICT_DONT_STRDUP_VAL = 8;
    public static final int AV_DICT_DONT_OVERWRITE = 16;
    public static final int AV_DICT_APPEND = 32;

    public static final String LIB_NAME = BridJ.getNativeLibraryName(Lib.class);
    public static final int MIN_MAJOR_VERSION = 51;
    public static final int MAX_MAJOR_VERSION = 51;
    
    private int majorVersion;
    private int minorVersion;
    private int microVersion;
    
    private NativeLibrary lib;
    
    public AVUtilLibrary() throws IOException {
        lib = BridJ.getNativeLibrary(Lib.class);
        
        int libVersion = avutil_version();
        
        majorVersion = (libVersion >> 16) & 0xff;
        minorVersion = (libVersion >> 8) & 0xff;
        microVersion = libVersion & 0xff;
        String version = String.format("%d.%d.%d", majorVersion, minorVersion, microVersion);
        
        File libFile = BridJ.getNativeLibraryFile(LIB_NAME);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Loading {0} library, version {1}...", new Object[] { libFile.getAbsolutePath(), version });
        
        if (majorVersion < MIN_MAJOR_VERSION || majorVersion > MAX_MAJOR_VERSION)
            throw new UnsatisfiedLinkError("Unsupported version of the " + LIB_NAME + " native library. (" + MIN_MAJOR_VERSION + ".x.x <= required <= " + MAX_MAJOR_VERSION + ".x.x, found " + version + ")");
    }

    @Override
    public boolean functionExists(String functionName) {
        return lib.getSymbol(functionName) != null;
    }

    @Override
    public int getMajorVersion() {
        return majorVersion;
    }

    @Override
    public int getMicroVersion() {
        return microVersion;
    }

    @Override
    public int getMinorVersion() {
        return minorVersion;
    }
    
    /**
     * Get version of the avutil library. Bits 23 to 16 represents major
     * version, bits 15 to 8 are for minor version and bits 7 to 0 are for
     * micro version.
     * 
     * @return version of the avutil library
     */
    public int avutil_version() {
        return Lib.avutil_version();
    }
    
    /**
     * Rescale a 64-bit integer by 2 rational numbers.
     * 
     * FIX: wait for BridJ's "by value" support
     * 
     * @param a
     * @param bq
     * @param cq
     * @return 
     */
    public long av_rescale_q(long a, AVRational bq, AVRational cq) {
        long b = bq.num() * (long)cq.den();
        long c = cq.num() * (long)bq.den();
        return av_rescale(a, b, c);
    }
    
    /**
     * Rescale a 64-bit integer with rounding to nearest. A simple a*b/c isn't 
     * possible as it can overflow.
     * 
     * @param a
     * @param b
     * @param c
     * @return 
     */
    public long av_rescale(long a, long b, long c) {
        return Lib.av_rescale(a, b, c);
    }
    
    /**
     * Allocate a block of size bytes with alignment suitable for all memory 
     * accesses (including vectors if available on the CPU).
     * 
     * @param size size in bytes for the memory block to be allocated
     * @return pointer to the allocated block, NULL if the block cannot be 
     * allocated
     */
    public Pointer<?> av_malloc(long size) {
        return Lib.av_malloc(size);
    }
    
    /**
     * Free a memory block which has been allocated with av_malloc(z)() 
     * or av_realloc().
     * 
     * @param ptr ptr pointer to the memory block which should be freed
     */
    public void av_free(Pointer<?> ptr) {
        Lib.av_free(ptr);
    }
    
    /**
     * Free a memory block which has been allocated with av_malloc(z)() or 
     * av_realloc() and set the pointer pointing to it to NULL.
     * 
     * @param ptr pointer to the pointer to the memory block which should be 
     * freed
     */
    public void av_freep(Pointer<?> ptr) {
        Lib.av_freep(ptr);
    }
    
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
    public int av_strerror(int errNum, Pointer<Byte> errBuf, long errbufSize) {
        return Lib.av_strerror(errNum, errBuf, errbufSize);
    }
    
    /**
     * Get a dictionary entry with matching key.
     * 
     * @param m
     * @param key
     * @param prev Set to the previous matching element to find the next. If 
     * set to null the first matching element is returned.
     * @param flags Allows case as well as suffix-insensitive comparisons.
     * @return Found entry or null, changing key or value leads to undefined 
     * behavior.
     */
    public Pointer<?> av_dict_get(Pointer<?> m, Pointer<Byte> key, Pointer<?> prev, int flags) {
        return Lib.av_dict_get(m, key, prev, flags);
    }
    
    /**
     * Set the given entry in *pm, overwriting an existing entry.
     * 
     * @param pm pointer to a pointer to a dictionary struct. If pm is null 
     * a dictionary struct is allocated and put in pm.
     * @param key entry key to add to pm (will be av_strduped depending 
     * on flags)
     * @param value entry value to add to pm (will be av_strduped depending 
     * on flags). Passing a NULL value will cause an existing entry to be 
     * deleted.
     * @param flags
     * @return &gt;= 0 on success otherwise an error code &lt;0
     */
    public int av_dict_set(Pointer<Pointer<?>> pm, Pointer<Byte> key, Pointer<Byte> value, int flags) {
        return Lib.av_dict_set(pm, key, value, flags);
    }
    
    /**
     * Copy entries from one AVDictionary struct into another.
     * 
     * NOTE:
     * Metadata is read using the AV_DICT_IGNORE_SUFFIX flag.
     * 
     * @param dst pointer to a pointer to a AVDictionary struct. If dst is null, 
     * this function will allocate a struct for you and put it in dst.
     * @param src pointer to source AVDictionary struct
     * @param flags flags to use when setting entries in dst
     */
    public void av_dict_copy(Pointer<Pointer<?>> dst, Pointer<?> src, int flags) {
        Lib.av_dict_copy(dst, src, flags);
    }
    
    /**
     * Free all the memory allocated for an AVDictionary struct and all keys 
     * and values.
     * 
     * @param m 
     */
    public void av_dict_free(Pointer<Pointer<?>> m) {
        Lib.av_dict_free(m);
    }
    
    @Library("avutil")
    private static class Lib {
        static {
            BridJ.register();
	}
        
	public static native int avutil_version();
	// FIX: wait for BridJ's "by value" support
        //public static native long av_rescale_q(long a, AVRational bq, AVRational cq);
        public static native long av_rescale(long a, long b, long c);
	public static native Pointer<?> av_malloc(@Ptr long size);
	public static native void av_free(Pointer<?> ptr);
	public static native void av_freep(Pointer<?> ptr);
	public static native int av_strerror(int errnum, Pointer<Byte> errbuf, @Ptr long errbuf_size);
        public static native Pointer<?> av_dict_get(Pointer<?> m, Pointer<Byte> key, Pointer<?> prev, int flags);
        public static native int av_dict_set(Pointer<Pointer<?>> pm, Pointer<Byte> key, Pointer<Byte> value, int flags);
        public static native void av_dict_copy(Pointer<Pointer<?>> dst, Pointer<?> src, int flags);
        public static native void av_dict_free(Pointer<Pointer<?>> m);
    }
    
}

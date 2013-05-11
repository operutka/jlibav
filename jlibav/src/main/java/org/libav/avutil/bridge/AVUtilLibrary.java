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
import org.bridj.ann.Optional;
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
    
    public static final int AV_LOG_QUIET = -8;
    public static final int AV_LOG_PANIC = 0;
    public static final int AV_LOG_FATAL = 8;
    public static final int AV_LOG_ERROR = 16;
    public static final int AV_LOG_WARNING = 24;
    public static final int AV_LOG_INFO = 32;
    public static final int AV_LOG_VERBOSE = 40;
    public static final int AV_LOG_DEBUG = 48;

    public static final String LIB_NAME = BridJ.getNativeLibraryName(Lib.class);
    public static final int MIN_MAJOR_VERSION = 51;
    public static final int MAX_MAJOR_VERSION = 52;
    
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
     * Get Libav log level.
     * 
     * @return log level
     */
    public int av_log_get_level() {
        return Lib.av_log_get_level();
    }
    
    /**
     * Set Libav log level.
     * 
     * @param logLevel log level
     */
    public void av_log_set_level(int logLevel) {
        Lib.av_log_set_level(logLevel);
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
    
    /**
     * Return number of bytes per sample.
     * 
     * @param sample_fmt a smaple format
     * @return number of bytes per sample or zero if unknown for the given sample format
     */
    public int av_get_bytes_per_sample(int sample_fmt) {
        return Lib.av_get_bytes_per_sample(sample_fmt);
    }
    
    /**
     * Get a value of the option with the given name from an object.
     * 
     * @param obj an object
     * @param name option name
     * @param o_out found AVOption
     * @param buf a buffer which is used for returning non string values as 
     * strings, can be NULL
     * @param buf_len allocated length in bytes of buf
     * @return buf or null if there is no such option or the option type is 
     * unknown
     */
    public Pointer<Byte> av_get_string(Pointer<?> obj, Pointer<Byte> name, Pointer<Pointer<?>> o_out, Pointer<Byte> buf, int buf_len) {
        return Lib.av_get_string(obj.getPeer(), name.getPeer(), o_out.getPeer(), buf.getPeer(), buf_len);
    }
    
    /**
     * Get a value of the option with the given name from an object.
     * 
     * @param obj an object
     * @param name option name
     * @param o_out found AVOption
     * @return long value
     */
    public long av_get_int(Pointer<?> obj, Pointer<Byte> name, Pointer<Pointer<?>> o_out) {
        return Lib.av_get_int(obj.getPeer(), name.getPeer(), o_out.getPeer());
    }
    
    /**
     * Get a value of the option with the given name from an object.
     * 
     * @param obj an object
     * @param name option name
     * @param o_out found AVOption
     * @return double value
     */
    public double av_get_double(Pointer<?> obj, Pointer<Byte> name, Pointer<Pointer<?>> o_out) {
        return Lib.av_get_double(obj.getPeer(), name.getPeer(), o_out.getPeer());
    }
    
    /**
     * Get a value of the option with the given name from an object.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name name of the option to get
     * @param search_flags flags passed to av_opt_find2. I.e. if 
     * AV_OPT_SEARCH_CHILDREN is passed here, then the option may be found in 
     * a child of obj
     * @param out_val value of the option will be written here
     * @return 0 on success, a negative error code otherwise
     */
    public int av_opt_get(Pointer<?> obj, Pointer<Byte> name, int search_flags, Pointer<Pointer<?>> out_val) {
        return Lib.av_opt_get(obj.getPeer(), name.getPeer(), search_flags, out_val.getPeer());
    }
    
    /**
     * Get a value of the option with the given name from an object.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name name of the option to get
     * @param search_flags flags passed to av_opt_find2. I.e. if 
     * AV_OPT_SEARCH_CHILDREN is passed here, then the option may be found in 
     * a child of obj
     * @param out_val value of the option will be written here
     * @return 0 on success, a negative error code otherwise
     */
    public int av_opt_get_int(Pointer<?> obj, Pointer<Byte> name, int search_flags, Pointer<Long> out_val) {
        return Lib.av_opt_get_int(obj.getPeer(), name.getPeer(), search_flags, out_val.getPeer());
    }
    
    /**
     * Get a value of the option with the given name from an object.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name name of the option to get
     * @param search_flags flags passed to av_opt_find2. I.e. if 
     * AV_OPT_SEARCH_CHILDREN is passed here, then the option may be found in 
     * a child of obj
     * @param out_val value of the option will be written here
     * @return 0 on success, a negative error code otherwise
     */
    public int av_opt_get_double(Pointer<?> obj, Pointer<Byte> name, int search_flags, Pointer<Double> out_val) {
        return Lib.av_opt_get_double(obj.getPeer(), name.getPeer(), search_flags, out_val.getPeer());
    }
    
    /**
     * Get a value of the option with the given name from an object.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name name of the option to get
     * @param search_flags flags passed to av_opt_find2. I.e. if 
     * AV_OPT_SEARCH_CHILDREN is passed here, then the option may be found in 
     * a child of obj
     * @param out_val value of the option will be written here
     * @return 0 on success, a negative error code otherwise
     */
    public int av_opt_get_q(Pointer<?> obj, Pointer<Byte> name, int search_flags, Pointer<?> out_val) {
        return Lib.av_opt_get_q(obj.getPeer(), name.getPeer(), search_flags, out_val.getPeer());
    }
    
    /**
     * Set the field of obj with the given name to value.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name the name of the field to set
     * @param val The value to set. If the field is not of a string type, then 
     * the given string is parsed. SI postfixes and some named scalars are 
     * supported. If the field is of a numeric type, it has to be a numeric or 
     * named scalar. Behavior with more than one scalar and +- infix operators 
     * is undefined. If the field is of a flags type, it has to be a sequence 
     * of numeric scalars or named flags separated by '+' or '-'. Prefixing 
     * a flag with '+' causes it to be set without affecting the other flags; 
     * similarly, '-' unsets a flag.
     * @param alloc this parameter is currently ignored
     * @param o_out if non-NULL put here a pointer to the AVOption found
     * @return 0 if the value has been set, or an AVERROR code in case of error: 
     * AVERROR_OPTION_NOT_FOUND if no matching option exists AVERROR(ERANGE) 
     * if the value is out of range AVERROR(EINVAL) if the value is not valid
     */
    public int av_set_string3(Pointer<?> obj, Pointer<Byte> name, Pointer<Byte> val, int alloc, Pointer<Pointer<?>> o_out) {
        return Lib.av_set_string3(obj.getPeer(), name.getPeer(), val.getPeer(), alloc, o_out.getPeer());
    }
    
    /**
     * Set the field of obj with the given name to value.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name the name of the field to set
     * @param n a value
     * @return modified AVOption or null if the option has not been set
     */
    public Pointer<?> av_set_int(Pointer<?> obj, Pointer<Byte> name, long n) {
        return Lib.av_set_int(obj.getPeer(), name.getPeer(), n);
    }
    
    /**
     * Set the field of obj with the given name to value.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name the name of the field to set
     * @param n a value
     * @return modified AVOption or null if the option has not been set
     */
    public Pointer<?> av_set_double(Pointer<?> obj, Pointer<Byte> name, double n) {
        return Lib.av_set_double(obj.getPeer(), name.getPeer(), n);
    }

    /**
     * Set the field of obj with the given name to value.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name the name of the field to set
     * @param val The value to set. In case of av_opt_set() if the field is not 
     * of a string type, then the given string is parsed. SI postfixes and some 
     * named scalars are supported. If the field is of a numeric type, it has to 
     * be a numeric or named scalar. Behavior with more than one scalar and +- 
     * infix operators is undefined. If the field is of a flags type, it has to 
     * be a sequence of numeric scalars or named flags separated by '+' or '-'. 
     * Prefixing a flag with '+' causes it to be set without affecting the other 
     * flags; similarly, '-' unsets a flag.
     * @param search_flags flags passed to av_opt_find2. I.e. if 
     * AV_OPT_SEARCH_CHILDREN is passed here, then the option may be set on 
     * a child of obj
     * @return 0 if the value has been set, or an AVERROR code in case of 
     * error: AVERROR_OPTION_NOT_FOUND if no matching option exists 
     * AVERROR(ERANGE) if the value is out of range AVERROR(EINVAL) if 
     * the value is not valid
     */
    public int av_opt_set(Pointer<?> obj, Pointer<Byte> name, Pointer<Byte> val, int search_flags) {
        return Lib.av_opt_set(obj.getPeer(), name.getPeer(), val.getPeer(), search_flags);
    }
    
    /**
     * Set the field of obj with the given name to value.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name the name of the field to set
     * @param val The value to set. In case of av_opt_set() if the field is not 
     * of a string type, then the given string is parsed. SI postfixes and some 
     * named scalars are supported. If the field is of a numeric type, it has to 
     * be a numeric or named scalar. Behavior with more than one scalar and +- 
     * infix operators is undefined. If the field is of a flags type, it has to 
     * be a sequence of numeric scalars or named flags separated by '+' or '-'. 
     * Prefixing a flag with '+' causes it to be set without affecting the other 
     * flags; similarly, '-' unsets a flag.
     * @param search_flags flags passed to av_opt_find2. I.e. if 
     * AV_OPT_SEARCH_CHILDREN is passed here, then the option may be set on 
     * a child of obj
     * @return 0 if the value has been set, or an AVERROR code in case of 
     * error: AVERROR_OPTION_NOT_FOUND if no matching option exists 
     * AVERROR(ERANGE) if the value is out of range AVERROR(EINVAL) if 
     * the value is not valid
     */
    public int av_opt_set_int(Pointer<?> obj, Pointer<Byte> name, long val, int search_flags) {
        return Lib.av_opt_set_int(obj.getPeer(), name.getPeer(), val, search_flags);
    }
    
    /**
     * Set the field of obj with the given name to value.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name the name of the field to set
     * @param val The value to set. In case of av_opt_set() if the field is not 
     * of a string type, then the given string is parsed. SI postfixes and some 
     * named scalars are supported. If the field is of a numeric type, it has to 
     * be a numeric or named scalar. Behavior with more than one scalar and +- 
     * infix operators is undefined. If the field is of a flags type, it has to 
     * be a sequence of numeric scalars or named flags separated by '+' or '-'. 
     * Prefixing a flag with '+' causes it to be set without affecting the other 
     * flags; similarly, '-' unsets a flag.
     * @param search_flags flags passed to av_opt_find2. I.e. if 
     * AV_OPT_SEARCH_CHILDREN is passed here, then the option may be set on 
     * a child of obj
     * @return 0 if the value has been set, or an AVERROR code in case of 
     * error: AVERROR_OPTION_NOT_FOUND if no matching option exists 
     * AVERROR(ERANGE) if the value is out of range AVERROR(EINVAL) if 
     * the value is not valid
     */
    public int av_opt_set_double(Pointer<?> obj, Pointer<Byte> name, double val, int search_flags) {
        return Lib.av_opt_set_double(obj.getPeer(), name.getPeer(), val, search_flags);
    }
    
    /**
     * Set the field of obj with the given name to value.
     * 
     * @param obj a struct whose first element is a pointer to an AVClass
     * @param name the name of the field to set
     * @param val The value to set. In case of av_opt_set() if the field is not 
     * of a string type, then the given string is parsed. SI postfixes and some 
     * named scalars are supported. If the field is of a numeric type, it has to 
     * be a numeric or named scalar. Behavior with more than one scalar and +- 
     * infix operators is undefined. If the field is of a flags type, it has to 
     * be a sequence of numeric scalars or named flags separated by '+' or '-'. 
     * Prefixing a flag with '+' causes it to be set without affecting the other 
     * flags; similarly, '-' unsets a flag.
     * @param search_flags flags passed to av_opt_find2. I.e. if 
     * AV_OPT_SEARCH_CHILDREN is passed here, then the option may be set on 
     * a child of obj
     * @return 0 if the value has been set, or an AVERROR code in case of 
     * error: AVERROR_OPTION_NOT_FOUND if no matching option exists 
     * AVERROR(ERANGE) if the value is out of range AVERROR(EINVAL) if 
     * the value is not valid
     */
    public int av_opt_set_bin(Pointer<?> obj, Pointer<Byte> name, Pointer<Byte> val, int size, int search_flags) {
        return Lib.av_opt_set_bin(obj.getPeer(), name.getPeer(), val.getPeer(), size, search_flags);
    }
    
    /**
     * Return a channel layout id that matches name, or 0 if no match is found. 
     * Name can be one or several of the following notations, separated by '+' 
     * or '|':<br/><br/>
     *  - the name of an usual channel layout (mono, stereo, 4.0, quad, 5.0, 
     * 5.0(side), 5.1, 5.1(side), 7.1, 7.1(wide), downmix)<br/>
     *  - the name of a single channel (FL, FR, FC, LFE, BL, BR, FLC, FRC, BC, 
     * SL, SR, TC, TFL, TFC, TFR, TBL, TBC, TBR, DL, DR)<br/>
     *  - a number of channels, in decimal, optionally followed by 'c', yielding 
     * the default channel layout for that number of channels<br/>
     *  - a channel layout mask, in hexadecimal starting with "0x" (see the 
     * AV_CH_* macros)<br/><br/>
     * 
     * Example: "stereo+FC" = "2+FC" = "2c+1c" = "0x7"
     * 
     * @param name layout name
     * @return channel layout
     */
    public long av_get_channel_layout(Pointer<Byte> name) {
        return Lib.av_get_channel_layout(name.getPeer());
    }
    
    /**
     * Return default channel layout for a given number of channels.
     * 
     * @param nb_channels number of channels
     * @return channel layout
     */
    public long av_get_default_channel_layout(int nb_channels) {
        return Lib.av_get_default_channel_layout(nb_channels);
    }
    
    /**
     * Return the number of channels in the channel layout.
     * 
     * @param channel_layout a channel layout
     * @return number of channels
     */
    public int av_get_channel_layout_nb_channels(long channel_layout) {
        return Lib.av_get_channel_layout_nb_channels(channel_layout);
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
        
        public static native int av_log_get_level();
        public static native void av_log_set_level(int logLevel);
        
        public static native Pointer<?> av_dict_get(Pointer<?> m, Pointer<Byte> key, Pointer<?> prev, int flags);
        public static native int av_dict_set(Pointer<Pointer<?>> pm, Pointer<Byte> key, Pointer<Byte> value, int flags);
        public static native void av_dict_copy(Pointer<Pointer<?>> dst, Pointer<?> src, int flags);
        public static native void av_dict_free(Pointer<Pointer<?>> m);
        
        public static native int av_get_bytes_per_sample(int sample_fmt);
        
        @Optional
        public static native Pointer<Byte> av_get_string(@Ptr long obj, @Ptr long name, @Ptr long o_out, @Ptr long buf, int buf_len);
        @Optional
        public static native long av_get_int(@Ptr long obj, @Ptr long name, @Ptr long o_out);
        @Optional
        public static native double av_get_double(@Ptr long obj, @Ptr long name, @Ptr long o_out);
	// FIX: wait for BridJ's "by value" support
        //@Optional
        //public static native AVRational av_get_q(Pointer<?> obj, Pointer<Byte> name, Pointer<Pointer<?>> o_out);
        @Optional
        public static native int av_opt_get(@Ptr long obj, @Ptr long name, int search_flags, @Ptr long out_val);
        @Optional
        public static native int av_opt_get_int(@Ptr long obj, @Ptr long name, int search_flags, @Ptr long out_val);
        @Optional
        public static native int av_opt_get_double(@Ptr long obj, @Ptr long name, int search_flags, @Ptr long out_val);
        @Optional
        public static native int av_opt_get_q(@Ptr long obj, @Ptr long name, int search_flags, @Ptr long out_val);
        
        @Optional
        public static native int av_set_string3(@Ptr long obj, @Ptr long name, @Ptr long val, int alloc, @Ptr long o_out);
        @Optional
        public static native Pointer<?> av_set_int(@Ptr long obj, @Ptr long name, long n);
        @Optional
        public static native Pointer<?> av_set_double(@Ptr long obj, @Ptr long name, double n);
        // FIX: wait for BridJ's "by value" support
        //@Optional
        //public static native Pointer<?> av_set_q(Pointer<?> obj, Pointer<Byte> name, AVRational n);
        @Optional
        public static native int av_opt_set(@Ptr long obj, @Ptr long name, @Ptr long val, int search_flags);
        @Optional
        public static native int av_opt_set_int(@Ptr long obj, @Ptr long name, long val, int search_flags);
        @Optional
        public static native int av_opt_set_double(@Ptr long obj, @Ptr long name, double val, int search_flags);
	// FIX: wait for BridJ's "by value" support
        //@Optional
        //public static native int av_opt_set_q(@Ptr long obj, @Ptr long name, AVRational val, int search_flags);
        @Optional
        public static native int av_opt_set_bin(@Ptr long obj, @Ptr long name, @Ptr long val, int size, int search_flags);
        
        public static native long av_get_channel_layout(@Ptr long name);
        @Optional
        public static native long av_get_default_channel_layout(int nb_channels);
        public static native int av_get_channel_layout_nb_channels(long channel_layout);
    }
    
}

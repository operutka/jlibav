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
package org.libav.avresample.bridge;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bridj.BridJ;
import org.bridj.NativeLibrary;
import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.libav.bridge.ILibrary;

/**
 * It provides access to the native avformat library. The methods'
 * documentation has been taken from the original Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public final class AVResampleLibrary implements ILibrary {

    public static final String LIB_NAME = BridJ.getNativeLibraryName(Lib.class);
    public static final int MIN_MAJOR_VERSION = 0;
    public static final int MAX_MAJOR_VERSION = 0;
    
    private int majorVersion;
    private int minorVersion;
    private int microVersion;
    
    private NativeLibrary lib;

    public AVResampleLibrary() throws IOException {
        lib = BridJ.getNativeLibrary(Lib.class);
        
        int libVersion = avresample_version();
        
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
     * Get version of the avresample library. Bits 23 to 16 represents major
     * version, bits 15 to 8 are for minor version and bits 7 to 0 are for
     * micro version.
     * 
     * @return version of the avresample library
     */
    public int avresample_version() {
        return Lib.avresample_version();
    }
    
    /**
     * Allocate AVAudioResampleContext and set options.
     * 
     * @return allocated audio resample context, or null on failure
     */
    public Pointer<?> avresample_alloc_context() {
        return Lib.avresample_alloc_context();
    }
    
    /**
     * Initialize AVAudioResampleContext.
     * 
     * @param avr audio resample context
     * @return 0 on success, negative AVERROR code on failure
     */
    public int avresample_open(Pointer<?> avr) {
        return Lib.avresample_open(avr);
    }
    
    /**
     * Close AVAudioResampleContext.
     * 
     * This closes the context, but it does not change the parameters. 
     * The context can be reopened with avresample_open(). It does, however, 
     * clear the output FIFO and any remaining leftover samples in 
     * the resampling delay buffer. If there was a custom matrix being used, 
     * that is also cleared.
     * 
     * @param avr audio resample context
     */
    public void avresample_close(Pointer<?> avr) {
        Lib.avresample_close(avr);
    }
    
    /**
     * Free AVAudioResampleContext and associated AVOption values. 
     * 
     * This also calls avresample_close() before freeing.
     * 
     * @param avr audio resample context
     */
    public void avresample_free(Pointer<Pointer<?>> avr) {
        Lib.avresample_free(avr);
    }
    
    /**
     * Get the current channel mixing matrix.
     * 
     * @param avr audio resample context
     * @param matrix mixing coefficients; matrix[i + stride * o] is the weight 
     * of input channel i in output channel o.
     * @param stride distance between adjacent input channels in the matrix 
     * array
     * @return 0 on success, negative AVERROR code on failure
     */
    public int avresample_get_matrix(Pointer<?> avr, Pointer<Double> matrix, int stride) {
        return Lib.avresample_get_matrix(avr, matrix, stride);
    }
    
    /**
     * Set channel mixing matrix. 
     * 
     * Allows for setting a custom mixing matrix, overriding the default matrix 
     * generated internally during avresample_open(). This function can be 
     * called anytime on an allocated context, either before or after calling 
     * avresample_open(). avresample_convert() always uses the current matrix. 
     * Calling avresample_close() on the context will clear the current matrix.
     * 
     * @param avr audio resample context
     * @param matrix mixing coefficients; matrix[i + stride * o] is the weight 
     * of input channel i in output channel o.
     * @param stride distance between adjacent input channels in the matrix 
     * array
     * @return 0 on success, negative AVERROR code on failure
     */
    public int avresample_set_matrix(Pointer<?> avr, Pointer<Double> matrix, int stride) {
        return Lib.avresample_set_matrix(avr, matrix, stride);
    }
    
    /**
     * Convert input samples and write them to the output FIFO. 
     * 
     * The output data can be NULL or have fewer allocated samples than 
     * required. In this case, any remaining samples not written to the output 
     * will be added to an internal FIFO buffer, to be returned at the next call 
     * to this function or to avresample_read().
     * 
     * If converting sample rate, there may be data remaining in the internal 
     * resampling delay buffer. avresample_get_delay() tells the number of 
     * remaining samples. To get this data as output, call avresample_convert() 
     * with NULL input.
     * 
     * At the end of the conversion process, there may be data remaining in 
     * the internal FIFO buffer. avresample_available() tells the number of 
     * remaining samples. To get this data as output, either call 
     * avresample_convert() with NULL input or call avresample_read().
     * 
     * @param avr audio resample context
     * @param output output data pointers
     * @param out_plane_size output plane size, in bytes. This can be 0 if 
     * unknown, but that will lead to optimized functions not being used 
     * directly on the output, which could slow down some conversions.
     * @param out_samples maximum number of samples that the output buffer can 
     * hold
     * @param input input data pointers
     * @param in_plane_size input plane size, in bytes This can be 0 if unknown, 
     * but that will lead to optimized functions not being used directly on 
     * the input, which could slow down some conversions.
     * @param in_samples number of input samples to convert
     * @return number of samples written to the output buffer, not including 
     * converted samples added to the internal output FIFO
     */
    public int avresample_convert(Pointer<?> avr, Pointer<Pointer<?>> output, int out_plane_size, int out_samples, Pointer<Pointer<?>> input, int in_plane_size, int in_samples) {
        return Lib.avresample_convert(avr, output, out_plane_size, out_samples, input, in_plane_size, in_samples);
    }
    
    @Library("avresample")
    private static class Lib {
        static {
            BridJ.register();
	}
        
        public static native int avresample_version();
        public static native Pointer<?> avresample_alloc_context();
        public static native int avresample_open(Pointer<?> avr);
        public static native void avresample_close(Pointer<?> avr);
        public static native void avresample_free(Pointer<Pointer<?>> avr);
        public static native int avresample_get_matrix(Pointer<?> avr, Pointer<Double> matrix, int stride);
        public static native int avresample_set_matrix(Pointer<?> avr, Pointer<Double> matrix, int stride);
        public static native int avresample_convert(Pointer<?> avr, Pointer<Pointer<?>> output, int out_plane_size, int out_samples, Pointer<Pointer<?>> input, int in_plane_size, int in_samples);
    }
    
}

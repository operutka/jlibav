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
package org.libav.swscale.bridge;

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
 * Interface to provide access to the native swscale library. The methods'
 * documentation has been taken from the original Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public final class SWScaleLibrary implements ILibrary {
    
    public static final int SWS_FAST_BILINEAR = 1;
    public static final int SWS_BILINEAR = 2;
    public static final int SWS_BICUBIC = 4;
    public static final int SWS_X = 8;
    public static final int SWS_POINT = 0x10;
    public static final int SWS_AREA = 0x20;
    public static final int SWS_BICUBLIN = 0x40;
    public static final int SWS_GAUSS = 0x80;
    public static final int SWS_SINC = 0x100;
    public static final int SWS_LANCZOS = 0x200;
    public static final int SWS_SPLINE = 0x400;

    public static final String LIB_NAME = BridJ.getNativeLibraryName(Lib.class);
    public static final int MIN_MAJOR_VERSION = 2;
    public static final int MAX_MAJOR_VERSION = 2;
    
    private final int majorVersion;
    private final int minorVersion;
    private final int microVersion;
    
    private final NativeLibrary lib;
    
    public SWScaleLibrary() throws IOException {
        lib = BridJ.getNativeLibrary(Lib.class);
        
        int libVersion = swscale_version();
        
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
     * Get version of the swscale library. Bits 23 to 16 represents major
     * version, bits 15 to 8 are for minor version and bits 7 to 0 are for
     * micro version.
     * 
     * @return version of the swscale library
     */
    public int swscale_version() {
        return Lib.swscale_version();
    }
    
    /**
     * Checks if context can be reused, otherwise reallocates a new one. 
     * If context is NULL, just calls sws_getContext() to get a new context. 
     * 
     * Otherwise, checks if the parameters are the ones already saved in 
     * context. If that is the case, returns the current context. Otherwise, 
     * frees context and gets a new context with the new parameters.
     * 
     * Be warned that srcFilter and dstFilter are not checked, they are assumed 
     * to remain the same.
     * 
     * @param context existing context or null
     * @param srcW the width of the source image
     * @param srcH the height of the source image
     * @param srcFormat the source image PixelFormat
     * @param dstW the width of the destination image
     * @param dstH the height of the destination image
     * @param dstFormat the destination image PixelFormat
     * @param flags specify which algorithm and options to use for rescaling
     * @param srcFilter 
     * @param dstFilter 
     * @param param 
     * @return a pointer to an allocated context, or NULL in case of error
     */
    public Pointer<?> sws_getCachedContext(Pointer<?> context, int srcW, int srcH, int srcFormat, int dstW, int dstH, int dstFormat, int flags, Pointer<?> srcFilter, Pointer<?> dstFilter, Pointer<Double> param) {
        return Lib.sws_getCachedContext(context, srcW, srcH, srcFormat, dstW, dstH, dstFormat, flags, srcFilter, dstFilter, param);
    }
    
    /**
     * Frees the swscaler context swsContext. 
     * 
     * If swsContext is NULL, then does nothing.
     * 
     * @param swsContext 
     */
    public void sws_freeContext(Pointer<?> swsContext) {
        Lib.sws_freeContext(swsContext);
    }
    
    /**
     * Scales the image slice in srcSlice and puts the resulting scaled slice in 
     * the image in dst. 
     * 
     * A slice is a sequence of consecutive rows in an image.
     * 
     * Slices have to be provided in sequential order, either in top-bottom or 
     * bottom-top order. If slices are provided in non-sequential order the 
     * behavior of the function is undefined.
     * 
     * Assumes planar YUV to be in YUV order instead of YVU.
     * 
     * @param c the scaling context previously created with sws_getContext()
     * @param srcSlice the array containing the pointers to the planes of the 
     * source slice
     * @param srcStride the array containing the strides for each plane of the 
     * source image
     * @param srcSliceY the position in the source image of the slice to 
     * process, that is the number (counted starting from zero) in the image of 
     * the first row of the slice
     * @param srcSliceH the height of the source slice, that is the number of 
     * rows in the slice
     * @param dst the array containing the pointers to the planes of the 
     * destination image
     * @param dstStride the array containing the strides for each plane of the 
     * destination image
     * @return the height of the output slice
     */
    public int sws_scale(Pointer<?> c, Pointer<Pointer<Byte>> srcSlice, Pointer<Integer> srcStride, int srcSliceY, int srcSliceH, Pointer<Pointer<Byte>> dst, Pointer<Integer> dstStride) {
        return Lib.sws_scale(c, srcSlice, srcStride, srcSliceY, srcSliceH, dst, dstStride);
    }
    
    @Library("swscale")
    private static class Lib {
        static {
            BridJ.register();
	}
        
	public static native int swscale_version();
	public static native Pointer<?> sws_getCachedContext(Pointer<?> context, int srcW, int srcH, int srcFormat, int dstW, int dstH, int dstFormat, int flags, Pointer<?> srcFilter, Pointer<?> dstFilter, Pointer<Double> param);
	public static native void sws_freeContext(Pointer<?> swsContext);
	public static native int sws_scale(Pointer<?> c, Pointer<Pointer<Byte>> srcSlice, Pointer<Integer> srcStride, int srcSliceY, int srcSliceH, Pointer<Pointer<Byte>> dst, Pointer<Integer> dstStride);
    }
    
}

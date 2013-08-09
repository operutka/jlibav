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
package org.libav.swscale;

import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.IFrameWrapper;
import org.libav.avutil.PixelFormat;
import org.libav.bridge.LibraryManager;
import org.libav.swscale.bridge.SWScaleLibrary;

/**
 * Wrapper for the SWScaleContext.
 * 
 * @author Ondrej Perutka
 */
public class ScaleContextWrapper implements IScaleContextWrapper {
    
    private static final SWScaleLibrary scaleLib = LibraryManager.getInstance().getSWScaleLibrary();

    private Pointer<?> scaleContext;
    
    /**
     * Wrap existing SWScaleContext.
     * 
     * @param scaleContext pointer to the valid SWScaleContext
     */
    private ScaleContextWrapper(Pointer<?> scaleContext) {
        this.scaleContext = scaleContext;
    }
    
    @Override
    public void free() {
        if (scaleContext == null)
            return;
        
        scaleLib.sws_freeContext(scaleContext);
        scaleContext = null;
    }
    
    @Override
    public void clearWrapperCache() {
    }
    
    @Override
    public Pointer<?> getPointer() {
        return scaleContext;
    }

    @Override
    public void rebind(Pointer<?> pointer) {
        scaleContext = pointer;
    }
    
    @Override
    public int scale(IFrameWrapper src, IFrameWrapper dst, int srcSliceY, int srcSliceHeight) throws LibavException {
        if (scaleContext == null)
            throw new LibavException("current context has been freed");
        
        return scaleLib.sws_scale(scaleContext, src.getData(), src.getLineSize(), srcSliceY, srcSliceHeight, dst.getData(), dst.getLineSize());
    }
    
    /**
     * Create SWScale context using given params.
     * 
     * @see ISWScaleLibrary
     */
    public static ScaleContextWrapper createContext(int srcWidth, int srcHeight, PixelFormat srcFormat, int dstWidth, int dstHeight, PixelFormat dstFormat, int flags) throws LibavException {
        return new ScaleContextWrapper(allocateContext(srcWidth, srcHeight, srcFormat, dstWidth, dstHeight, dstFormat, flags, null, null, null));
    }
    
    /**
     * Create SWScale context using given params.
     * 
     * @see ISWScaleLibrary
     */
    public static ScaleContextWrapper createContext(int srcWidth, int srcHeight, PixelFormat srcFormat, int dstWidth, int dstHeight, PixelFormat dstFormat, int flags, Pointer srcFilter, Pointer dstFilter) throws LibavException {
        return new ScaleContextWrapper(allocateContext(srcWidth, srcHeight, srcFormat, dstWidth, dstHeight, dstFormat, flags, srcFilter, dstFilter, null));
    }
    
    /**
     * Create SWScale context using given params.
     * 
     * @see ISWScaleLibrary
     */
    public static ScaleContextWrapper createContext(int srcWidth, int srcHeight, PixelFormat srcFormat, int dstWidth, int dstHeight, PixelFormat dstFormat, int flags, Pointer srcFilter, Pointer dstFilter, Pointer param) throws LibavException {
        return new ScaleContextWrapper(allocateContext(srcWidth, srcHeight, srcFormat, dstWidth, dstHeight, dstFormat, flags, srcFilter, dstFilter, param));
    }
    
    private static Pointer<?> allocateContext(int srcWidth, int srcHeight, PixelFormat srcFormat, int dstWidth, int dstHeight, PixelFormat dstFormat, int flags, Pointer srcFilter, Pointer dstFilter, Pointer param) throws LibavException {
        Pointer<?> result = scaleLib.sws_getCachedContext(null, srcWidth, srcHeight, srcFormat.value(), dstWidth, dstHeight, dstFormat.value(), flags, srcFilter, dstFilter, param);
        if (result == null)
            throw new LibavException("unable to create scale context");
        
        return result;
    }
    
}

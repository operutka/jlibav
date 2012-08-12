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

import org.libav.LibavException;
import org.libav.avcodec.IFrameWrapper;
import org.libav.bridge.IWrapper;

/**
 * Wrapper for the SWScaleContext.
 * 
 * @author Ondrej Perutka
 */
public interface IScaleContextWrapper extends IWrapper {
    
    /**
     * Free context.
     */
    void free();
    
    /**
     * Scale the given slice of the source frame.
     * 
     * @param src source frame
     * @param dst allocated destination frame
     * @param srcSliceY Y position of the slice in the source frame
     * @param srcSliceHeight height of the slice from the source frame
     * @return the height of the output slice
     * @throws LibavException if the scale context has been freed
     */
    int scale(IFrameWrapper src, IFrameWrapper dst, int srcSliceY, int srcSliceHeight) throws LibavException;
    
}

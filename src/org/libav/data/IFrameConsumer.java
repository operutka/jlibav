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
package org.libav.data;

import org.libav.LibavException;
import org.libav.avcodec.IFrameWrapper;

/**
 * Frame consumer interface.
 * 
 * @author Ondrej Perutka
 */
public interface IFrameConsumer {
    
    /**
     * This method will be called by a frame decoder. It delivers audio/video 
     * frames.
     * 
     * @param producer a video frame producer responsible for calling this
     * method and producing the video frame
     * @param frame a video frame
     * @throws LibavException you may want to throw this type of exception
     * if you call some Libav function inside this method
     */
    void processFrame(Object producer, IFrameWrapper frame) throws LibavException;
    
}

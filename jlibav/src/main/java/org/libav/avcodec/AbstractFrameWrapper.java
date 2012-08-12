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
package org.libav.avcodec;

import org.bridj.Pointer;

/**
 * Abstract wrapper for the AVFrame.
 * 
 * @author Ondrej Perutka
 */
public abstract class AbstractFrameWrapper implements IFrameWrapper {
    
    protected Pointer<Pointer<Byte>> data;
    protected Pointer<Integer> lineSize;
    protected Boolean keyFrame;
    protected Long pts;
    protected Integer repeatPicture;
    protected Long packetDts;
    protected Long packetPts;
    protected Integer nbSamples;

    public AbstractFrameWrapper() {
        data = null;
        lineSize = null;
        keyFrame = null;
        pts = null;
        repeatPicture = null;
        packetPts = null;
        packetDts = null;
    }

    @Override
    public void clearWrapperCache() {
        data = null;
        lineSize = null;
        keyFrame = null;
        pts = null;
        repeatPicture = null;
        packetPts = null;
        packetDts = null;
    }
    
}

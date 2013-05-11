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

import org.libav.util.Rational;

/**
 * Abstract wrapper for the AVCodecContext.
 * 
 * @author Ondrej Perutka
 */
public abstract class AbstractCodecContextWrapper implements ICodecContextWrapper {

    protected IFrameWrapper codedFrame;
    protected Integer codecType;
    protected Integer codecId;
    protected Integer flags;
    protected Integer width;
    protected Integer height;
    protected Rational sampleAspectRatio;
    protected Integer chromaSampleLocation;
    protected Integer pixelFormat;
    protected Integer bitRate;
    protected Rational timeBase;
    protected Integer gopSize;
    protected Integer maxBFrames;
    protected Integer mbDecision;
    protected Integer channels;
    protected Long channelLayout;
    protected Integer sampleRate;
    protected Integer sampleFormat;
    protected Integer frameSize;
    
    public AbstractCodecContextWrapper() {
        codedFrame = null;
        codecType = null;
        codecId = null;
        flags = null;
        width = null;
        height = null;
        sampleAspectRatio = null;
        chromaSampleLocation = null;
        pixelFormat = null;
        bitRate = null;
        timeBase = null;
        gopSize = null;
        maxBFrames = null;
        mbDecision = null;
        channels = null;
        channelLayout = null;
        sampleRate = null;
        sampleFormat = null;
        frameSize = null;
    }
    
    @Override
    public void clearWrapperCache() {
        codecType = null;
        codecId = null;
        flags = null;
        width = null;
        height = null;
        sampleAspectRatio = null;
        chromaSampleLocation = null;
        pixelFormat = null;
        bitRate = null;
        timeBase = null;
        gopSize = null;
        maxBFrames = null;
        mbDecision = null;
        channels = null;
        channelLayout = null;
        sampleRate = null;
        sampleFormat = null;
        frameSize = null;
    }
    
}

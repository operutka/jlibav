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
package org.libav.avformat;

import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avutil.IDictionaryWrapper;
import org.libav.util.Rational;

/**
 * Abstract wrapper for the AVStream.
 * 
 * @author Ondrej Perutka
 */
public abstract class AbstractStreamWrapper implements IStreamWrapper {
    
    protected ICodecContextWrapper codecContext;
    protected Integer index;
    protected Rational sampleAspectRatio;
    protected Rational timeBase;
    protected Rational frameRate;
    protected Long frameCount;
    protected Long duration;
    protected IDictionaryWrapper metadata;
    protected Integer disposition;

    public AbstractStreamWrapper() {
        codecContext = null;
        index = null;
        sampleAspectRatio = null;
        timeBase = null;
        frameRate = null;
        frameCount = null;
        duration = null;
        metadata = null;
        disposition = null;
    }

    @Override
    public void clearWrapperCache() {
        index = null;
        sampleAspectRatio = null;
        timeBase = null;
        frameRate = null;
        frameCount = null;
        duration = null;
        disposition = null;
    }
    
}

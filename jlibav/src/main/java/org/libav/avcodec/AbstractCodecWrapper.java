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

import org.libav.avutil.MediaType;
import org.libav.avutil.PixelFormat;
import org.libav.avutil.SampleFormat;
import org.libav.util.Rational;

/**
 * Abstract wrapper for the AVCodec.
 * 
 * @author Ondrej Perutka
 */
public abstract class AbstractCodecWrapper implements ICodecWrapper {
    
    protected CodecID id;
    protected MediaType type;
    protected String name;
    protected Integer capabilities;
    protected Rational[] supportedFrameRates;
    protected PixelFormat[] supportedPixelFormats;
    protected int[] supportedSampleRates;
    protected SampleFormat[] supportedSampleFormats;
    protected long[] supportedChannelLayouts;

    public AbstractCodecWrapper() {
        id = null;
        type = null;
        name = null;
        capabilities = null;
        supportedFrameRates = null;
        supportedPixelFormats = null;
        supportedSampleRates = null;
        supportedSampleFormats = null;
        supportedChannelLayouts = null;
    }

    @Override
    public void clearWrapperCache() {
        id = null;
        type = null;
        name = null;
        capabilities = null;
        supportedFrameRates = null;
        supportedPixelFormats = null;
        supportedSampleRates = null;
        supportedSampleFormats = null;
        supportedChannelLayouts = null;
    }
    
}

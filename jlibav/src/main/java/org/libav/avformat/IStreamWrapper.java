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
import org.libav.bridge.IWrapper;
import org.libav.util.Rational;

/**
 * Wrapper for the AVStream. It provides access to some of the
 * AVStream properties.
 * 
 * @author Ondrej Perutka
 */
public interface IStreamWrapper extends IWrapper {
    
    /**
     * Free the allocated resources.
     * 
     * WARNING:
     * DO NOT call this if you have not allocated the stream (using the 
     * FormatContextWrapper newStream() method). If you call this method, you 
     * must null the corresponding field of the streams array in the format 
     * context.
     */
    void free();
    
    /**
     * Get the codec property from the AVStream.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return codec context
     */
    ICodecContextWrapper getCodecContext();
    
    /**
     * Set the codec property of the AVStream. The value may be 
     * cached.
     * 
     * @param codecContext a codec context
     */
    void setCodecContext(ICodecContextWrapper codecContext);
    
    /**
     * Get the index property from the AVStream.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return stream index
     */
    int getIndex();

    /**
     * Get the r_frame_rate property from the AVStream.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return real base frame rate
     */
    Rational getRFrameRate();

    /**
     * Get the time_base property from the AVStream.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return time base
     */
    Rational getTimeBase();
    
    /**
     * Set the time_base property of the AVStream. The value may be 
     * cached.
     * 
     * @param timeBase a time base
     */
    void setTimeBase(Rational timeBase);

    /**
     * Get the nb_frames property from the AVStream.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return number of frames
     */
    long getFrameCount();
    
    /**
     * Get the duration property from the AVStream.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return stream duration
     */
    long getDuration();
    
    /**
     * Get the disposition property from the AVStream.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return stream disposition
     */
    int getDisposition();
    
    /**
     * Get the metadata property from the AVStream.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return stream metadata
     */
    IDictionaryWrapper getMetadata();
    
}

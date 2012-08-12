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
package org.libav;

import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.data.IFrameProducer;
import org.libav.data.IPacketConsumer;

/**
 * Decoder interface.
 * 
 * @author Ondrej Perutka
 */
public interface IDecoder extends IFrameProducer, IPacketConsumer {
    
    /**
     * Get decoding codec context.
     * 
     * @return decoding codec context
     */
    ICodecContextWrapper getCodecContext();

    /**
     * Get underlaying stream.
     * 
     * @return underlaying stream
     */
    IStreamWrapper getStream();

    /**
     * Close this decoder and release all its resources.
     */
    void close();
    
    /**
     * Check whether the decoder has been closed.
     * 
     * @return true if the decoder has been closed, false otherwise
     */
    boolean isClosed();
    
    /**
     * Flush the decoder. (Call this at the end of decoding process.)
     * 
     * @throws LibavException if an error occurs
     */
    void flush() throws LibavException;
    
}

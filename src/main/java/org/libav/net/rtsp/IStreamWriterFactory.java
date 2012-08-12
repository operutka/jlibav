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
package org.libav.net.rtsp;

import org.libav.IMediaWriter;
import org.libav.LibavException;

/**
 * Stream writer factory interface.
 * 
 * @author Ondrej Perutka
 */
public interface IStreamWriterFactory {
    
    /**
     * Create a new audio or video stream at the given media writer and return
     * its index.
     * 
     * @param mediaWriter
     * @return index of the created stream writer
     * @throws LibavException if the stream writer cannot be created
     */
    int createWriter(IMediaWriter mediaWriter)throws LibavException;
    
}

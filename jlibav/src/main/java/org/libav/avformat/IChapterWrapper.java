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

import org.libav.avutil.IDictionaryWrapper;
import org.libav.bridge.IWrapper;

/**
 * Wrapper for the AVChapter.
 * 
 * @author Ondrej Perutka
 */
public interface IChapterWrapper extends IWrapper {
    
    /**
     * Free the chapter. Do NOT use this method for chapters you have not 
     * allocated or chapters accessible from a format context. Be really
     * carefull if you have more than one chapter wrapper instances pointing
     * at the same native memory.
     */
    void free();
    
    /**
     * Get chapter unique ID.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return chapter unique ID
     */
    int getId();
    
    /**
     * Set chapter unique ID. The value may be cached.
     * 
     * @param id a chapter ID
     */
    void setId(int id);
    
    /**
     * Get chapter start time in miliseconds since the begining.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return chapter start time
     */
    long getStart();
    
    /**
     * Set chapter start time in miliseconds since the begining. The value 
     * may be cached.
     * 
     * @param start a chapter start time
     */
    void setStart(long start);
    
    /**
     * Get chapter end time in miliseconds since the begining.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return chapter end time
     */
    long getEnd();
    
    /**
     * Set chapter end time in miliseconds since the begining. The value 
     * may be cached.
     * 
     * @param end a chapter end time
     */
    void setEnd(long end);
    
    /**
     * Get chapter metadata.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return chapter metadata
     */
    IDictionaryWrapper getMetadata();
    
}

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

/**
 * Interface for timestamp generator classes.
 * 
 * @author Ondrej Perutka
 */
public interface ITimestampGenerator {
    
    /**
     * Get timestamp for the next frame. It is a value between the last 
     * generated timestamp and the input timestamp - offset or -1 if 
     * the input timestamp - offset is less or equal to the last generated 
     * timestamp.
     * 
     * NOTE:
     * Call this metod multiple times until the returned value is &lt; 0.
     * 
     * @param inputTimestamp an input timestamp
     * @return timestamp for the next frame
     */
    long nextFrame(long inputTimestamp);
    
    /**
     * Get last generated timestamp.
     * 
     * @return last timestamp
     */
    long getLastTimestamp();
    
    /**
     * Set offset for input timestamps.
     * 
     * @param offset an offset
     */
    void setInputOffset(long offset);
    
    /**
     * Get offset for input timestamps.
     * 
     * @return offset
     */
    long getInputOffset();
    
    /**
     * Reset the generated sequence.
     */
    void reset();
    
}

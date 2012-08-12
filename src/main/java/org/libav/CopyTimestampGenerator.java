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
 * Copy timestamp generator. New timestamp is the input timestamp - offset. 
 * The first input timestamp is set as offset after the new object creation
 * or reset call.
 * 
 * @author Ondrej Perutka
 */
public class CopyTimestampGenerator implements ITimestampGenerator {
    
    private boolean inputAsOffset;
    private long offset;
    private long lastTimestamp;

    public CopyTimestampGenerator() {
        inputAsOffset = true;
        offset = 0;
        lastTimestamp = -1;
    }
    
    @Override
    public long nextFrame(long inputTimestamp) {
        if (inputAsOffset) {
            offset = inputTimestamp;
            lastTimestamp = -1;
            inputAsOffset = false;
        }
        
        if ((inputTimestamp - offset) <= lastTimestamp)
            return -1;
        
        lastTimestamp = inputTimestamp - offset;
        return lastTimestamp;
    }

    @Override
    public long getLastTimestamp() {
        return lastTimestamp;
    }

    @Override
    public void setInputOffset(long offset) {
        this.offset = offset;
        lastTimestamp = -1;
    }

    @Override
    public long getInputOffset() {
        return offset;
    }

    @Override
    public void reset() {
        inputAsOffset = true;
    }
    
}

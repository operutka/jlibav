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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.libav.ITimestampGenerator;

/**
 * Timestamp generator factory for aggregate media streams.
 * 
 * @author Ondrej Perutka
 */
public class TimestampGeneratorFactory {
    
    private final Map<String, Long> sessionOffsets;
    private final Map<String, Set<Generator>> sessionGenerators;

    public TimestampGeneratorFactory() {
        sessionOffsets = new HashMap<String, Long>();
        sessionGenerators = new HashMap<String, Set<Generator>>();
    }
    
    public synchronized ITimestampGenerator createTimestampGenerator(String sessionId) {
        if (!sessionGenerators.containsKey(sessionId))
            sessionGenerators.put(sessionId, new HashSet<Generator>());
        
        Generator result = new Generator(sessionId);
        sessionGenerators.get(sessionId).add(result);
        return result;
    }
    
    public synchronized void dropSession(String sessionId) {
        sessionOffsets.remove(sessionId);
        sessionGenerators.remove(sessionId);
    }
    
    private synchronized long getSessionOffset(String sessionId, long defaultValue) {
        if (!sessionOffsets.containsKey(sessionId))
            sessionOffsets.put(sessionId, defaultValue);
        
        return sessionOffsets.get(sessionId);
    }
    
    private synchronized void setSessionOffset(String sessionId, long offset) {
        Set<Generator> generators = sessionGenerators.get(sessionId);
        if (generators == null)
            return;
        
        sessionOffsets.put(sessionId, offset);
        for (Generator gen : generators)
            gen.updateOffset();
    }
    
    private synchronized void resetSessionOffset(String sessionId) {
        Set<Generator> generators = sessionGenerators.get(sessionId);
        if (generators == null)
            return;
        
        sessionOffsets.remove(sessionId);
        for (Generator gen : generators)
            gen.updateOffset();
    }
    
    private class Generator implements ITimestampGenerator {
        private final String sessionId;
        private long lastTimestamp;
        private long offset;
        private boolean updateOffset;

        public Generator(String sessionId) {
            this.sessionId = sessionId;
            lastTimestamp = -1;
            offset = 0;
            updateOffset = true;
        }
        
        @Override
        public long nextFrame(long inputTimestamp) {
            if (updateOffset) {
                offset = getSessionOffset(sessionId, inputTimestamp);
                lastTimestamp = -1;
                updateOffset = false;
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
            setSessionOffset(sessionId, offset);
            updateOffset = true;
        }

        @Override
        public long getInputOffset() {
            return offset;
        }

        @Override
        public void reset() {
            resetSessionOffset(sessionId);
        }
        
        private void updateOffset() {
            updateOffset = true;
        }
        
    }
    
}

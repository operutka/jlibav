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
package org.libav.avutil;

import java.util.List;
import org.libav.LibavException;
import org.libav.bridge.IWrapper;

/**
 * Wrapper for the AVDictionary.
 * 
 * @author Ondrej Perutka
 */
public interface IDictionaryWrapper extends IWrapper, Iterable<IDictionaryWrapper.Pair> {
    
    /**
     * Get value for the given key.
     * 
     * @param key a key
     * @param flags see AV_DICT_ flags
     * @return a value or null if there is no entry for the given key
     */
    String get(String key, int flags);
    
    /**
     * Set value for the given key. If the value is null the entry will be 
     * removed from the dictionary.
     * 
     * @param key a key
     * @param value a value
     * @param flags see AV_DICT_ flags
     * @throws LibavException if an error occured
     */
    void set(String key, String value, int flags) throws LibavException;
    
    /**
     * Free the dictionary and all its resources.
     */
    void free();
    
    /**
     * Copy content of this dictionary into another dictionary.
     * 
     * @param dictionary a dictionary
     * @param flags see AV_DICT_ flags
     */
    void copyTo(IDictionaryWrapper dictionary, int flags);
    
    /**
     * Convert this dictionary into a list of key-value pairs.
     * 
     * @return list of key-value pairs
     */
    List<Pair> toList();
    
    public static class Pair {
        private final String key;
        private final String value;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }
        
        public String getValue() {
            return value;
        }
    }
    
}

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

import org.bridj.Pointer;

/**
 * Factory class for dictionary wrappers.
 * 
 * @author Ondrej Perutka
 */
public class DictionaryWrapperFactory {
    
    private static final DictionaryWrapperFactory instance;
    
    static {
        instance = new DictionaryWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param dictionary pointer to an AVDictionary struct
     * @return dictionary wrapper
     */
    public IDictionaryWrapper wrap(Pointer<?> dictionary) {
        return new DictionaryWrapper(dictionary);
    }
    
    /**
     * Allocate a new dictionary.
     * 
     * @return dictionary wrapper
     */
    public IDictionaryWrapper allocate() {
        return DictionaryWrapper.allocate();
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static DictionaryWrapperFactory getInstance() {
        return instance;
    }
    
}

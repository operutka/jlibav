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

import org.libav.bridge.IWrapper;

/**
 * Wrapper for the AVInputFormat. It provides access to some of the
 * AVInputFormat properties.
 * 
 * @author Ondrej Perutka
 */
public interface IInputFormatWrapper extends IWrapper {
    
    /**
     * Get the name property from the AVInputFormat.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return name
     */
    String getName();
    
    /**
     * Get the flags property from the AVInputFormat.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return flags
     */
    int getFlags();
    
    /**
     * Set the flags property of the AVInputFormat. The value may be 
     * cached.
     * 
     * @param flags 
     */
    void setFlags(int flags);
    
}

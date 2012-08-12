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
package org.libav.bridge;

/**
 *
 * @author Ondrej Perutka
 */
public interface ILibrary {
    
    /**
     * Get library major version.
     * 
     * @return major version
     */
    public int getMajorVersion();

    /**
     * Get library micro version.
     * 
     * @return micro version
     */
    public int getMicroVersion();

    /**
     * Get library minor version.
     * 
     * @return minor version
     */
    public int getMinorVersion();
    
    /**
     * Check whether the given function exists in this version of the library.
     * 
     * @param functionName name of the function
     * @return true if the function exists, false otherwise
     */
    public boolean functionExists(String functionName);
    
}

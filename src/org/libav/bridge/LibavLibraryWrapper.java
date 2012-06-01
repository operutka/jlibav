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

import com.sun.jna.Library;
import com.sun.jna.NativeLibrary;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wrapper for Libav libraries.
 * 
 * @author Ondrej Perutka
 */
public class LibavLibraryWrapper<T extends Library> {
    
    private int majorVersion;
    private int minorVersion;
    private int microVersion;
    
    private T lib;
    private NativeLibrary nativeLib;

    /**
     * Create a new Libav library wrapper.
     * 
     * @param nativeLib native library
     * @param lib library
     * @param libVersion library version
     * @param minMajorVersion min supported major version
     * @param maxMajorVersion max supported major version
     */
    public LibavLibraryWrapper(NativeLibrary nativeLib, T lib, int libVersion, int minMajorVersion, int maxMajorVersion) {
        this.nativeLib = nativeLib;
        this.lib = lib;
        
        majorVersion = (libVersion >> 16) & 0xff;
        minorVersion = (libVersion >> 8) & 0xff;
        microVersion = libVersion & 0xff;
        String version = String.format("%d.%d.%d", majorVersion, minorVersion, microVersion);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Loading {0} library, version {1}...", new Object[] { nativeLib.getName(), version });
        
        if (majorVersion < minMajorVersion || majorVersion > maxMajorVersion)
            throw new UnsatisfiedLinkError("Unsupported version of the " + nativeLib.getName() + " native library. (" + minMajorVersion + ".x.x <= required <= " + maxMajorVersion + ".x.x, found " + version + ")");
    }
    /**
     * Get library major version.
     * 
     * @return major version
     */
    public int getMajorVersion() {
        return majorVersion;
    }

    /**
     * Get library micro version.
     * 
     * @return micro version
     */
    public int getMicroVersion() {
        return microVersion;
    }

    /**
     * Get library minor version.
     * 
     * @return minor version
     */
    public int getMinorVersion() {
        return minorVersion;
    }
    
    /**
     * Get library.
     * 
     * @return library
     */
    public T getLibrary() {
        return lib;
    }
    
    /**
     * Check whether the given function exists in this version of the library.
     * 
     * @param functionName name of the function
     * @return true if the function exists, false otherwise
     */
    public boolean functionExists(String functionName) {
        try {
            nativeLib.getFunction(functionName);
        } catch (UnsatisfiedLinkError err) {
            return false;
        }
        
        return true;
    }
    
}

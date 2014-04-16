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
package org.libav.avdevice.bridge;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bridj.BridJ;
import org.bridj.NativeLibrary;
import org.bridj.ann.Library;
import org.libav.bridge.ILibrary;

/**
 * This class provides acces to the native avdevice library. The methods'
 * documentation has been taken from the original Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public final class AVDeviceLibrary implements ILibrary {
    
    public static final String LIB_NAME = BridJ.getNativeLibraryName(Lib.class);
    public static final int MIN_MAJOR_VERSION = 53;
    public static final int MAX_MAJOR_VERSION = 54;
    
    private final int majorVersion;
    private final int minorVersion;
    private final int microVersion;
    
    private final NativeLibrary lib;

    public AVDeviceLibrary() throws IOException {
        lib = BridJ.getNativeLibrary(Lib.class);
        
        int libVersion = avdevice_version();
        
        majorVersion = (libVersion >> 16) & 0xff;
        minorVersion = (libVersion >> 8) & 0xff;
        microVersion = libVersion & 0xff;
        String version = String.format("%d.%d.%d", majorVersion, minorVersion, microVersion);
        
        File libFile = BridJ.getNativeLibraryFile(LIB_NAME);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Loading {0} library, version {1}...", new Object[] { libFile.getAbsolutePath(), version });
        
        if (majorVersion < MIN_MAJOR_VERSION || majorVersion > MAX_MAJOR_VERSION)
            throw new UnsatisfiedLinkError("Unsupported version of the " + LIB_NAME + " native library. (" + MIN_MAJOR_VERSION + ".x.x <= required <= " + MAX_MAJOR_VERSION + ".x.x, found " + version + ")");
    }

    @Override
    public boolean functionExists(String functionName) {
        return lib.getSymbol(functionName) != null;
    }
    
    @Override
    public int getMajorVersion() {
        return majorVersion;
    }

    @Override
    public int getMicroVersion() {
        return microVersion;
    }

    @Override
    public int getMinorVersion() {
        return minorVersion;
    }
    
    /**
     * Get version of the avdevice library. Bits 23 to 16 represents major
     * version, bits 15 to 8 are for minor version and bits 7 to 0 are for
     * micro version.
     * 
     * @return version of the avdevice library
     */
    public int avdevice_version() {
        return Lib.avdevice_version();
    }
    
    /**
     * Initialize libavdevice and register all the input and output devices.
     */
    public synchronized void avdevice_register_all() {
        Lib.avdevice_register_all();
    }
    
    @Library("avdevice")
    private static class Lib {
        static {
            BridJ.register();
	}
        
        public static native int avdevice_version();
	public static native void avdevice_register_all();
    }
    
}

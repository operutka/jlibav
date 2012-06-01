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

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.nio.charset.Charset;

/**
 * Native string representation.
 * 
 * @author Ondrej Perutka
 */
public class CustomNativeString {
    private Pointer memory;
    private int size;
    private String str;
    private boolean gcEnabled;

    /**
     * Create native string representation.
     * 
     * @param str custom string
     * @param encoding native string encoding
     * @param nullTerminatorLength length of the null terminator in bytes
     */
    public CustomNativeString(String str, String encoding, int nullTerminatorLength) {
        this(str, Charset.forName(encoding), nullTerminatorLength);
    }
    
    /**
     * Create native string representation.
     * 
     * @param str custom string
     * @param encoding native string encoding
     * @param nullTerminatorLength length of the null terminator in bytes
     */
    public CustomNativeString(String str, Charset encoding, int nullTerminatorLength) {
        this.str = str;
        this.gcEnabled = true;
        byte[] bytes = str.getBytes(encoding);
        size = bytes.length + nullTerminatorLength;
        
        long peer = Native.malloc(size);
        if (peer == 0)
            throw new OutOfMemoryError();
            
        this.memory = new Pointer(peer);
        this.memory.write(0, bytes, 0, bytes.length);
        
        if (nullTerminatorLength > 0)
            this.memory.setMemory(bytes.length, nullTerminatorLength, (byte)0);
    }
    
    /**
     * Return pointer to the native string.
     * 
     * @return pointer to the native string or null if the native memory was 
     * freed
     */
    public Pointer getPointer() {
        return memory;
    }
    
    /**
     * Return size of the allocated native memory region (including the length 
     * of the null terminator).
     * 
     * @return the size in bytes or 0 if the native memory was freed
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Free the allocated native memory.
     */
    public synchronized void free() {
        if (memory == null)
            return;
        
        Native.free(Pointer.nativeValue(memory));
        size = 0;
        str = null;
        memory = null;
    }

    /**
     * Check whether the garbage collection of the native memory is enabled. 
     * The garbage collection is enabled by default.
     * 
     * @return true if the GC is enabled, false otherwise
     */
    public boolean isGcEnabled() {
        return gcEnabled;
    }

    /**
     * Enable or disable the garbage collection of the native memory. The 
     * garbage collection is enabled by default. Disabling the garbage 
     * collection may be useful if the native string is to be freed by a native
     * function and the reference to this object is not to be held.
     * 
     * @param gcEnabled true to enable the GC, false to disable it
     */
    public void setGcEnabled(boolean gcEnabled) {
        this.gcEnabled = gcEnabled;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void finalize() throws Throwable {
        try {
            if (gcEnabled)
                free();
        } finally {
            super.finalize();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final CustomNativeString other = (CustomNativeString) obj;
        if ((this.str == null) ? (other.str != null) : !this.str.equals(other.str))
            return false;
            
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.str != null ? this.str.hashCode() : 0);
        return hash;
    }

    /**
     * Return the string.
     * 
     * @return the string
     */
    @Override
    public String toString() {
        return str;
    }
}

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

import org.bridj.Pointer;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Exception thrown if there is an error in the Libav native space.
 * 
 * @author Ondrej Perutka
 */
public class LibavException extends Exception {

    private static final AVUtilLibrary lib = LibraryManager.getInstance().getAVUtilLibrary();
    
    public LibavException(int errorCode, Throwable thrwbl) {
        super(getLibavErrorDescription(errorCode) + "(" + errorCode + ")", thrwbl);
    }

    public LibavException(int errorCode) {
        super(getLibavErrorDescription(errorCode) + "(" + errorCode + ")");
    }

    public LibavException(Throwable cause) {
        super(cause);
    }

    public LibavException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibavException(String message) {
        super(message);
    }

    public LibavException() {
    }
    
    public static String getLibavErrorDescription(int errorCode) {
        Pointer<Byte> ptr = Pointer.allocateBytes(2048);
        if (ptr == null)
            return "memory allocation error, unable to get the Libav error description";
        
        lib.av_strerror(errorCode, ptr, 2048);
        
        return ptr.getCString();
    }
}

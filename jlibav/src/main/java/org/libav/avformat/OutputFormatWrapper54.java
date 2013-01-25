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

import java.nio.charset.Charset;
import org.bridj.Pointer;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avformat.bridge.AVOutputFormat54;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVOutputFormat54.
 * 
 * @author Ondrej Perutka
 */
public class OutputFormatWrapper54 extends AbstractOutputFormatWrapper {
    
    private static final AVFormatLibrary formatLib = LibraryManager.getInstance().getAVFormatLibrary();
    
    private AVOutputFormat54 format;
    
    /**
     * Create a new wrapper for the given AVOutputFormat.
     * 
     * @param format an output format structure
     */
    public OutputFormatWrapper54(AVOutputFormat54 format) {
        this.format = format;
    }
    
    @Override
    public Pointer<?> getPointer() {
        return Pointer.pointerTo(format);
    }

    @Override
    public String getName() {
        if (name == null) {
            Pointer<Byte> ptr = format.name();
            name = ptr == null ? null : ptr.getCString();
        }
        
        return name;
    }
    
    @Override
    public int getFlags() {
        if (flags == null)
            flags = format.flags();
        
        return flags;
    }
    
    @Override
    public void setFlags(int flags) {
        this.flags = flags;
        format.flags(flags);
    }
    
    public static OutputFormatWrapper54 guessFormat(String shortName, String fileName, String mimeType) {
        Charset utf8 = Charset.forName("UTF-8");
        
        Pointer<Byte> pShortName = null;
        Pointer<Byte> pFileName = null;
        Pointer<Byte> pMimeType = null;
        
        if (shortName != null)
            pShortName = Pointer.pointerToString(shortName, Pointer.StringType.C, utf8).as(Byte.class);
        if (fileName != null)
            pFileName = Pointer.pointerToString(fileName, Pointer.StringType.C, utf8).as(Byte.class);
        if (mimeType != null)
            pMimeType = Pointer.pointerToString(mimeType, Pointer.StringType.C, utf8).as(Byte.class);
        
        Pointer result = formatLib.av_guess_format(pShortName, pFileName, pMimeType);
        if (result == null)
            return null;
        
        return new OutputFormatWrapper54(new AVOutputFormat54(result));
    }
    
}

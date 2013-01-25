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

import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avformat.bridge.AVFormatContext53;
import org.libav.avformat.bridge.AVFormatContext54;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for format context wrappers.
 * 
 * @author Ondrej Perutka
 */
public class FormatContextWrapperFactory {
    
    private static final AVFormatLibrary formatLib;
    private static final FormatContextWrapperFactory instance;
    
    static {
        formatLib = LibraryManager.getInstance().getAVFormatLibrary();
        instance = new FormatContextWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param formatContext pointer to an AVFormatContext struct
     * @return format context wrapper
     */
    public IFormatContextWrapper wrap(Pointer<?> formatContext) {
        switch (formatLib.getMajorVersion()) {
            case 53: return wrap(new AVFormatContext53(formatContext));
            case 54: return wrap(new AVFormatContext54(formatContext));
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavformat");
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param formatContext AVFormatContext struct
     * @return format context wrapper
     */
    public IFormatContextWrapper wrap(AVFormatContext53 formatContext) {
        return new FormatContextWrapper53(formatContext);
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param formatContext AVFormatContext struct
     * @return format context wrapper
     */
    public IFormatContextWrapper wrap(AVFormatContext54 formatContext) {
        return new FormatContextWrapper54(formatContext);
    }
    
    /**
     * Open media stream.
     * 
     * @param url a media URL
     * @return format context wrapper
     * @throws LibavException if an error occurs while opening media
     */
    public IFormatContextWrapper openMedia(String url) throws LibavException {
        switch (formatLib.getMajorVersion()) {
            case 53: return FormatContextWrapper53.openMedia(url);
            case 54: return FormatContextWrapper54.openMedia(url);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavformat");
    }
    
    /**
     * Open media stream using the given input format.
     * 
     * @param url a media URL
     * @param inputFormat input format short name
     * @return format context wrapper
     * @throws LibavException if an error occurs while opening media
     */
    public IFormatContextWrapper openMedia(String url, String inputFormat) throws LibavException {
        switch (formatLib.getMajorVersion()) {
            case 53: return FormatContextWrapper53.openMedia(url, inputFormat);
            case 54: return FormatContextWrapper54.openMedia(url, inputFormat);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavformat");
    }
    
    /**
     * Open media stream using the given input format.
     * 
     * @param url a media URL
     * @param inputFormat input format
     * @return format context wrapper
     * @throws LibavException if an error occurs while opening media
     */
    public IFormatContextWrapper openMedia(String url, IInputFormatWrapper inputFormat) throws LibavException {
        switch (formatLib.getMajorVersion()) {
            case 53: return FormatContextWrapper53.openMedia(url, inputFormat);
            case 54: return FormatContextWrapper54.openMedia(url, inputFormat);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavformat");
    }
    
    /**
     * Create a new media stream.
     * 
     * @param url a media URL
     * @param outputFormatName a name of the output format
     * @return format context wrapper
     * @throws LibavException if an error occurs while creating media
     */
    public IFormatContextWrapper createMedia(String url, String outputFormatName) throws LibavException {
        switch (formatLib.getMajorVersion()) {
            case 53: return FormatContextWrapper53.createMedia(url, outputFormatName);
            case 54: return FormatContextWrapper54.createMedia(url, outputFormatName);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavformat");
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static FormatContextWrapperFactory getInstance() {
        return instance;
    }
    
}

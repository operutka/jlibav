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

import java.util.Iterator;
import org.bridj.Pointer;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avformat.bridge.AVOutputFormat53;
import org.libav.avformat.bridge.AVOutputFormat54;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for output format wrappers.
 * 
 * @author Ondrej Perutka
 */
public class OutputFormatWrapperFactory implements Iterable<IOutputFormatWrapper> {
    
    private static final AVFormatLibrary formatLib;
    private static final OutputFormatWrapperFactory instance;
    
    static {
        formatLib = LibraryManager.getInstance().getAVFormatLibrary();
        instance = new OutputFormatWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param format pointer to an AVOutputFormat struct
     * @return output format wrapper
     */
    public IOutputFormatWrapper wrap(Pointer<?> format) {
        switch (formatLib.getMajorVersion()) {
            case 53: return wrap(new AVOutputFormat53(format));
            case 54:
            case 55: return wrap(new AVOutputFormat54(format));
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavformat");
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param format AVOutputFormat struct
     * @return output format wrapper
     */
    public IOutputFormatWrapper wrap(AVOutputFormat53 format) {
        return new OutputFormatWrapper53(format);
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param format AVOutputFormat struct
     * @return output format wrapper
     */
    public IOutputFormatWrapper wrap(AVOutputFormat54 format) {
        return new OutputFormatWrapper54(format);
    }
    
    /**
     * Try to guess output format according to the given format short name,
     * file name and/or mime type.
     * 
     * @param shortName format short name
     * @param fileName file name
     * @param mimeType mime type
     * @return output format or null
     */
    public IOutputFormatWrapper guessFormat(String shortName, String fileName, String mimeType) {
        switch (formatLib.getMajorVersion()) {
            case 53: return OutputFormatWrapper53.guessFormat(shortName, fileName, mimeType);
            case 54:
            case 55: return OutputFormatWrapper54.guessFormat(shortName, fileName, mimeType);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavformat");
    }
    
    @Override
    public Iterator<IOutputFormatWrapper> iterator() {
        return new OutputFormatIterator();
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static OutputFormatWrapperFactory getInstance() {
        return instance;
    }
    
    /**
     * Iterator to probe all available output formats.
     */
    private class OutputFormatIterator implements Iterator<IOutputFormatWrapper> {
        private Pointer<?> current;
        private boolean last;

        public OutputFormatIterator() {
            current = null;
            last = false;
        }
        
        @Override
        public boolean hasNext() {
            return !last && formatLib.av_iformat_next(current) != null;
        }

        @Override
        public IOutputFormatWrapper next() {
            current = formatLib.av_iformat_next(current);
            if (current != null)
                return wrap(current);
            
            last = true;
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("unsupported method");
        }
    }
    
}

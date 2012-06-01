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

import com.sun.jna.Pointer;

/**
 * Abstract wrapper for the AVFormatContext.
 * 
 * @author Ondrej Perutka
 */
public abstract class AbstractFormatContextWrapper implements IFormatContextWrapper {
    
    protected IStreamWrapper[] streams;
    protected Integer streamCount;
    protected String fileName;
    protected IIOContextWrapper ioContext;
    protected IOutputFormatWrapper outputFormat;
    protected IInputFormatWrapper inputFormat;
    protected Long duration;
    protected Pointer privateData;

    public AbstractFormatContextWrapper() {
        streams = null;
        streamCount = null;
        fileName = null;
        ioContext = null;
        outputFormat = null;
        inputFormat = null;
        duration = null;
        privateData = null;
    }

    @Override
    public void clearWrapperCache() {
        streams = null;
        streamCount = null;
        fileName = null;
        ioContext = null;
        outputFormat = null;
        inputFormat = null;
        duration = null;
        privateData = null;
    }
    
}

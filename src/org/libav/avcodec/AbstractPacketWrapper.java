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
package org.libav.avcodec;

import org.bridj.Pointer;

/**
 * Abstract wrapper for the AVPacket.
 * 
 * @author Ondrej Perutka
 */
public abstract class AbstractPacketWrapper implements IPacketWrapper {

    protected Integer streamIndex;
    protected Integer size;
    protected Pointer<Byte> data;
    protected Integer flags;
    protected Long pts;
    protected Long dts;
    protected Integer duration;
    protected Long convergenceDuration;
    protected Long position;
    
    public AbstractPacketWrapper() {
        streamIndex = null;
        size = null;
        data = null;
        flags = null;
        pts = null;
        dts = null;
        duration = null;
        convergenceDuration = null;
        position = null;
    }

    @Override
    public void clearWrapperCache() {
        streamIndex = null;
        size = null;
        data = null;
        flags = null;
        pts = null;
        dts = null;
        duration = null;
        convergenceDuration = null;
        position = null;
    }
    
    @Override
    public abstract IPacketWrapper clone();
    
}

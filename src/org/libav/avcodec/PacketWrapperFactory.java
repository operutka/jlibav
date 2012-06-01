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

import com.sun.jna.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.bridge.AVPacket;

/**
 * Factory class for packet wrappers.
 * 
 * @author Ondrej Perutka
 */
public class PacketWrapperFactory {
    
    private static final PacketWrapperFactory instance;
    
    static {
        instance = new PacketWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param packet pointer to an AVPacket struct
     * @return packet wrapper
     */
    public IPacketWrapper wrap(Pointer packet) {
        return wrap(new AVPacket(packet));
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param packet AVPacket struct
     * @return packet wrapper
     */
    public IPacketWrapper wrap(AVPacket packet) {
        return new PacketWrapper(packet);
    }
    
    /**
     * Allocate a new empty packet.
     * 
     * @return packet wrapper
     */
    public IPacketWrapper alloc() {
        return PacketWrapper.allocatePacket();
    }
    
    /**
     * Allocate a new packet of the given size.
     * 
     * @param size packet size
     * @return packet wrapper
     */
    public IPacketWrapper alloc(int size) throws LibavException {
        return PacketWrapper.allocatePacket(size);
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static PacketWrapperFactory getInstance() {
        return instance;
    }
    
}

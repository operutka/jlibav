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
import org.libav.LibavException;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avcodec.bridge.AVPacket;
import org.libav.avcodec.bridge.AVPacket55;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for packet wrappers.
 * 
 * @author Ondrej Perutka
 */
public class PacketWrapperFactory {
    
    private static final AVCodecLibrary codecLib;
    private static final PacketWrapperFactory instance;
    
    static {
        codecLib = LibraryManager.getInstance().getAVCodecLibrary();
        instance = new PacketWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param packet pointer to an AVPacket struct
     * @return packet wrapper
     */
    public IPacketWrapper wrap(Pointer<?> packet) {
        switch (codecLib.getMajorVersion()) {
            case 53:
            case 54: return wrap(new AVPacket(packet));
            case 55: return wrap(new AVPacket55(packet));
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
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
     * Wrap the given struct.
     * 
     * @param packet AVPacket struct
     * @return packet wrapper
     */
    public IPacketWrapper wrap(AVPacket55 packet) {
        return new PacketWrapper55(packet);
    }
    
    /**
     * Allocate a new empty packet.
     * 
     * @return packet wrapper
     */
    public IPacketWrapper alloc() {
        switch (codecLib.getMajorVersion()) {
            case 53:
            case 54: return PacketWrapper.allocatePacket();
            case 55: return PacketWrapper55.allocatePacket();
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Allocate a new packet of the given size.
     * 
     * @param size packet size
     * @return packet wrapper
     */
    public IPacketWrapper alloc(int size) throws LibavException {
        switch (codecLib.getMajorVersion()) {
            case 53:
            case 54: return PacketWrapper.allocatePacket(size);
            case 55: return PacketWrapper55.allocatePacket(size);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
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

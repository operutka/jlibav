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
import org.libav.avcodec.bridge.AVCodec54;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVCodec54.
 * 
 * @author Ondrej Perutka
 */
public class CodecWrapper54 extends AbstractCodecWrapper {
    
    private static final AVCodecLibrary lib;
    
    static {
        lib = LibraryManager.getInstance().getAVCodecLibrary();
    }
    
    private AVCodec54 codec;
    
    private String longName;

    /**
     * Create a new wrapper for the given AVCodec.
     * 
     * @param codec an AVCodec structure
     */
    public CodecWrapper54(AVCodec54 codec) {
        this.codec = codec;
        this.longName = null;
    }

    @Override
    public void clearWrapperCache() {
        super.clearWrapperCache();
        longName = null;
    }
    
    @Override
    public Pointer<?> getPointer() {
        return Pointer.pointerTo(codec);
    }

    @Override
    public void rebind(Pointer<?> pointer) {
        codec = new AVCodec54(pointer);
    }

    @Override
    public int getId() {
        if (id == null)
            id = codec.id();
        
        return id;
    }

    @Override
    public int getType() {
        if (type == null)
            type = codec.type();
        
        return type;
    }

    @Override
    public String getName() {
        if (name == null) {
            Pointer<Byte> p = codec.name();
            name = p == null ? null : p.getCString();
        }
        
        return name;
    }
    
    public String getLongName() {
        if (longName == null) {
            Pointer<Byte> p = codec.long_name();
            longName = p == null ? null : p.getCString();
        }
        
        return longName;
    }
    
    @Override
    public int getCapabilities() {
        if (capabilities == null)
            capabilities = codec.capabilities();
        
        return capabilities;
    }
    
    public static CodecWrapper54 findDecoder(int codecId) throws LibavException {
        Pointer ptr = lib.avcodec_find_decoder(codecId);
        if (ptr == null)
            throw new LibavException("unable to find decoder");
        
        return new CodecWrapper54(new AVCodec54(ptr));
    }
    
    public static CodecWrapper54 findEncoder(int codecId) throws LibavException {
        Pointer ptr = lib.avcodec_find_encoder(codecId);
        if (ptr == null)
            throw new LibavException("unable to find encoder");
        
        return new CodecWrapper54(new AVCodec54(ptr));
    }
    
    public static CodecWrapper54 findDecoderByName(String name) throws LibavException {
        Pointer<Byte> nm = Pointer.pointerToCString(name);
        Pointer ptr = lib.avcodec_find_decoder_by_name(nm);
        if (ptr == null)
            throw new LibavException("unable to find decoder");
        
        return new CodecWrapper54(new AVCodec54(ptr));
    }
    
    public static CodecWrapper54 findEncoderByName(String name) throws LibavException {
        Pointer<Byte> nm = Pointer.pointerToCString(name);
        Pointer ptr = lib.avcodec_find_encoder_by_name(nm);
        if (ptr == null)
            throw new LibavException("unable to find encoder");
        
        return new CodecWrapper54(new AVCodec54(ptr));
    }
    
}

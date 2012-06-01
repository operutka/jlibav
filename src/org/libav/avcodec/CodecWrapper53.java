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
import org.libav.avcodec.bridge.AVCodec53;
import org.libav.avcodec.bridge.IAVCodecLibrary;
import org.libav.bridge.CustomNativeString;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVCodec53.
 * 
 * @author Ondrej Perutka
 */
public class CodecWrapper53 extends AbstractCodecWrapper {
    
    private static final IAVCodecLibrary lib;
    
    static {
        LibraryManager lm = LibraryManager.getInstance();
        lib = lm.getAVCodecLibraryWrapper().getLibrary();
    }
    
    private AVCodec53 codec;

    /**
     * Create a new wrapper for the given AVCodec.
     * 
     * @param codec an AVCodec structure
     */
    public CodecWrapper53(AVCodec53 codec) {
        this.codec = codec;
    }
    
    @Override
    public Pointer getPointer() {
        return codec.getPointer();
    }

    @Override
    public int getId() {
        if (id == null)
            id = (Integer)codec.readField("id");
        
        return id;
    }

    @Override
    public int getType() {
        if (type == null)
            type = (Integer)codec.readField("type");
        
        return type;
    }

    @Override
    public String getName() {
        if (name == null) {
            Pointer p = (Pointer)codec.readField("name");
            name = p == null ? null : p.getString(0);
        }
        
        return name;
    }
    
    @Override
    public int getCapabilities() {
        if (capabilities == null)
            capabilities = (Integer)codec.readField("capabilities");
        
        return capabilities;
    }
    
    public static CodecWrapper53 findDecoder(int codecId) throws LibavException {
        Pointer ptr = lib.avcodec_find_decoder(codecId);
        if (ptr == null)
            throw new LibavException("unable to find decoder");
        
        return new CodecWrapper53(new AVCodec53(ptr));
    }
    
    public static CodecWrapper53 findEncoder(int codecId) throws LibavException {
        Pointer ptr = lib.avcodec_find_encoder(codecId);
        if (ptr == null)
            throw new LibavException("unable to find encoder");
        
        return new CodecWrapper53(new AVCodec53(ptr));
    }
    
    public static CodecWrapper53 findDecoderByName(String name) throws LibavException {
        CustomNativeString cns = new CustomNativeString(name, "UTF-8", 1);
        Pointer ptr = lib.avcodec_find_decoder_by_name(cns.getPointer());
        if (ptr == null)
            throw new LibavException("unable to find decoder");
        
        return new CodecWrapper53(new AVCodec53(ptr));
    }
    
    public static CodecWrapper53 findEncoderByName(String name) throws LibavException {
        CustomNativeString cns = new CustomNativeString(name, "UTF-8", 1);
        Pointer ptr = lib.avcodec_find_encoder_by_name(cns.getPointer());
        if (ptr == null)
            throw new LibavException("unable to find encoder");
        
        return new CodecWrapper53(new AVCodec53(ptr));
    }
    
}

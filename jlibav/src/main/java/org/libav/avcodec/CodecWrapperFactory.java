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
import org.libav.avcodec.bridge.*;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for codec wrappers.
 * 
 * @author Ondrej Perutka
 */
public class CodecWrapperFactory {
    
    private static final AVCodecLibrary codecLib;
    private static final CodecWrapperFactory instance;
    
    static {
        codecLib = LibraryManager.getInstance().getAVCodecLibrary();
        instance = new CodecWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param codec pointer to an AVCodec struct
     * @return codec wrapper
     */
    public ICodecWrapper wrap(Pointer<?> codec) {
        switch (codecLib.getMajorVersion()) {
            case 53: return wrap(new AVCodec53(codec));
            case 54: return wrap(new AVCodec54(codec));
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param codec AVCodec struct
     * @return codec wrapper
     */
    public ICodecWrapper wrap(AVCodec53 codec) {
        return new CodecWrapper53(codec);
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param codec AVCodec struct
     * @return codec wrapper
     */
    public ICodecWrapper wrap(AVCodec54 codec) {
        return new CodecWrapper54(codec);
    }
    
    /**
     * Find decoder by its ID.
     * 
     * @param codecId a codec ID
     * @return decoder
     * @throws LibavException if there is no decoder for the given codec ID
     */
    public ICodecWrapper findDecoder(CodecID codecId) throws LibavException {
        switch (codecLib.getMajorVersion()) {
            case 53: return CodecWrapper53.findDecoder(codecId);
            case 54: return CodecWrapper54.findDecoder(codecId);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Find encoder by its ID.
     * 
     * @param codecId a codec ID
     * @return encoder
     * @throws LibavException if there is no encoder for the given codec ID
     */
    public ICodecWrapper findEncoder(CodecID codecId) throws LibavException {
        switch (codecLib.getMajorVersion()) {
            case 53: return CodecWrapper53.findEncoder(codecId);
            case 54: return CodecWrapper54.findEncoder(codecId);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Find decoder by its name.
     * 
     * @param name a codec name
     * @return decoder
     * @throws LibavException if there is no decoder with the given name
     */
    public ICodecWrapper findDecoderByName(String name) throws LibavException {
        switch (codecLib.getMajorVersion()) {
            case 53: return CodecWrapper53.findDecoderByName(name);
            case 54: return CodecWrapper54.findDecoderByName(name);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Find encoder by its name.
     * 
     * @param name a codec name
     * @return encoder
     * @throws LibavException if there is no encoder with the given name
     */
    public ICodecWrapper findEncoderByName(String name) throws LibavException {
        switch (codecLib.getMajorVersion()) {
            case 53: return CodecWrapper53.findEncoderByName(name);
            case 54: return CodecWrapper54.findEncoderByName(name);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static CodecWrapperFactory getInstance() {
        return instance;
    }
    
}

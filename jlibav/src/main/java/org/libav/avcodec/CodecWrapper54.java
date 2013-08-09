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

import java.util.ArrayList;
import java.util.List;
import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.bridge.AVCodec54;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avutil.PixelFormat;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVRational;
import org.libav.bridge.LibraryManager;
import org.libav.util.Rational;

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
    public CodecID getId() {
        if (id == null)
            id = CodecID.valueOf(codec.id());
        
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
    
    @Override
    public Rational[] getSupportedFrameRates() {
        if (supportedFrameRates == null && codec.supported_framerates() != null) {
            Pointer<AVRational> pFrameRates = codec.supported_framerates().as(AVRational.class);
            List<Rational> result = new ArrayList<Rational>();
            int i = 0;
            
            AVRational rational = pFrameRates.get(i++);
            while (!(rational.num() == 0 && rational.den() == 0)) {
                result.add(new Rational(rational));
                rational = pFrameRates.get(i++);
            }
            
            supportedFrameRates = result.toArray(new Rational[result.size()]);
        }
        
        return supportedFrameRates;
    }

    @Override
    public PixelFormat[] getSupportedPixelFormats() {
        if (supportedPixelFormats == null && codec.pix_fmts() != null) {
            Pointer<Integer> pPixelFormats = codec.pix_fmts();
            List<PixelFormat> result = new ArrayList<PixelFormat>();
            int i = 0;
            
            int pixelFormat = pPixelFormats.get(i++);
            while (pixelFormat != -1) {
                result.add(PixelFormat.valueOf(pixelFormat));
                pixelFormat = pPixelFormats.get(i++);
            }
            
            supportedPixelFormats = result.toArray(new PixelFormat[result.size()]);
        }
        
        return supportedPixelFormats;
    }

    @Override
    public int[] getSupportedSampleRates() {
        if (supportedSampleRates == null && codec.supported_samplerates() != null) {
            Pointer<Integer> pSampleRates = codec.supported_samplerates();
            List<Integer> result = new ArrayList<Integer>();
            int i = 0;
            
            int sampleRate = pSampleRates.get(i++);
            while (sampleRate != 0) {
                result.add(sampleRate);
                sampleRate = pSampleRates.get(i++);
            }
            
            supportedSampleRates = new int[result.size()];
            for (i = 0; i < result.size(); i++)
                supportedSampleRates[i] = result.get(i);
        }
        
        return supportedSampleRates;
    }

    @Override
    public SampleFormat[] getSupportedSampleFormats() {
        if (supportedSampleFormats == null && codec.sample_fmts() != null) {
            Pointer<Integer> pSampleFormats = codec.sample_fmts();
            List<SampleFormat> result = new ArrayList<SampleFormat>();
            int i = 0;
            
            int sampleFormat = pSampleFormats.get(i++);
            while (sampleFormat != -1) {
                result.add(SampleFormat.valueOf(sampleFormat));
                sampleFormat = pSampleFormats.get(i++);
            }
            
            supportedSampleFormats = result.toArray(new SampleFormat[result.size()]);
        }
        
        return supportedSampleFormats;
    }

    @Override
    public long[] getSupportedChannelLayouts() {
        if (supportedChannelLayouts == null && codec.channel_layouts() != null) {
            Pointer<Long> pChannelLayouts = codec.channel_layouts();
            List<Long> result = new ArrayList<Long>();
            int i = 0;
            
            long channelLayout = pChannelLayouts.get(i++);
            while (channelLayout != 0) {
                result.add(channelLayout);
                channelLayout = pChannelLayouts.get(i++);
            }
            
            supportedChannelLayouts = new long[result.size()];
            for (i = 0; i < result.size(); i++)
                supportedChannelLayouts[i] = result.get(i);
        }
        
        return supportedChannelLayouts;
    }
    
    public static CodecWrapper54 findDecoder(CodecID codecId) throws LibavException {
        Pointer ptr = lib.avcodec_find_decoder(codecId.value());
        if (ptr == null)
            throw new LibavException("unable to find decoder");
        
        return new CodecWrapper54(new AVCodec54(ptr));
    }
    
    public static CodecWrapper54 findEncoder(CodecID codecId) throws LibavException {
        Pointer ptr = lib.avcodec_find_encoder(codecId.value());
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

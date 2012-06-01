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
import com.sun.jna.ptr.IntByReference;
import org.libav.LibavException;
import org.libav.avcodec.bridge.AVCodecContext53;
import org.libav.avcodec.bridge.IAVCodecLibrary;
import org.libav.avutil.bridge.AVRational;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.avutil.bridge.IAVUtilLibrary;
import org.libav.bridge.LibavLibraryWrapper;
import org.libav.bridge.LibraryManager;
import org.libav.util.Rational;

/**
 * Wrapper class for the AVCodecContext53.
 * 
 * @author Ondrej Perutka
 */
public class CodecContextWrapper53 extends AbstractCodecContextWrapper {
    
    private static final IAVCodecLibrary codecLib;
    
    private static final boolean avcOpen2;
    private static final boolean avcDecodeAudio4;
    private static final boolean avcEncodeAudio2;
    
    static {
        LibavLibraryWrapper<IAVCodecLibrary> avc = LibraryManager.getInstance().getAVCodecLibraryWrapper();
        codecLib = avc.getLibrary();
        
        avcOpen2 = avc.functionExists("avcodec_open2");
        avcDecodeAudio4 = avc.functionExists("avcodec_decode_audio4");
        avcEncodeAudio2 = avc.functionExists("avcodec_encode_audio2");
    }
    
    private AVCodecContext53 context;
    
    private IntByReference intByRef;
    private IDecodeAudioFunction decodeAudioFunction;
    private IEncodeAudioFunction encodeAudioFunction;
    
    /**
     * Create a new wrapper for the given AVCodecContext.
     * 
     * @param context a codec context structure
     */
    public CodecContextWrapper53(AVCodecContext53 context) {
        this.context = context;
        
        this.intByRef = new IntByReference(0);
        
        if (avcDecodeAudio4)
            this.decodeAudioFunction = new DecodeAudio4();
        else
            this.decodeAudioFunction = new DecodeAudio3();
        
        if (avcEncodeAudio2)
            this.encodeAudioFunction = new EncodeAudio2();
        else
            this.encodeAudioFunction = new EncodeAudio();
    }
    
    @Override
    public Pointer getPointer() {
        if (context == null)
            return null;
        
        return context.getPointer();
    }
    
    @Override
    public void open(ICodecWrapper codec) throws LibavException {
        if (this.codec != null)
            close();
        
        int result;
        if (avcOpen2)
            result = codecLib.avcodec_open2(context.getPointer(), codec.getPointer(), null);
        else
            result = codecLib.avcodec_open(context.getPointer(), codec.getPointer());
        
        if(result < 0)
            throw new LibavException(result);
        
        this.codec = codec;
    }
    
    @Override
    public void close() {
        if (isClosed())
            return;
        
        codecLib.avcodec_close(context.getPointer());
        codec = null;
    }
    
    @Override
    public void free() {
        close();
        
        if (context == null)
            return;
        
        IAVUtilLibrary lib = LibraryManager.getInstance().getAVUtilLibraryWrapper().getLibrary();
        lib.av_free(context.getPointer());
        context = null;
    }
    
    @Override
    public boolean isClosed() {
        return codec == null;
    }
    
    @Override
    public IFrameWrapper getCodedFrame() {
        if (context == null)
            return null;
        
        if (codedFrame == null) {
            Pointer p = (Pointer)context.readField("coded_frame");
            codedFrame = p == null ? null : FrameWrapperFactory.getInstance().wrap(p);
        }
        
        return codedFrame;
    }
    
    @Override
    public int getCodecType() {
        if (context == null)
            return 0;
        
        if (codecType == null)
            codecType = (Integer)context.readField("codec_type");
        
        return codecType;
    }
    
    @Override
    public void setCodecType(int codecType) {
        if (context == null)
            return;
        
        context.writeField("codec_type", codecType);
        this.codecType = codecType;
    }
    
    @Override
    public int getCodecId() {
        if (context == null)
            return 0;
        
        if (codecId == null)
            codecId = (Integer)context.readField("codec_id");
        
        return codecId;
    }
    
    @Override
    public void setCodecId(int codecId) {
        if (context == null)
            return;
        
        context.writeField("codec_id", codecId);
        this.codecId = codecId;
    }
    
    @Override
    public int getFlags() {
        if (context == null)
            return 0;
        
        if (flags == null)
            flags = (Integer)context.readField("flags");
        
        return flags;
    }
    
    @Override
    public void setFlags(int flags) {
        if (context == null)
            return;
        
        this.flags = flags;
        context.writeField("flags", flags);
    }
    
    @Override
    public int getWidth() {
        if (context == null)
            return 0;
        
        if (width == null)
            width = (Integer)context.readField("width");
        
        return width;
    }
    
    @Override
    public void setWidth(int width) {
        if (context == null)
            return;
        
        context.writeField("width", width);
        this.width = width;
    }
    
    @Override
    public int getHeight() {
        if (context == null)
            return 0;
        
        if (height == null)
            height = (Integer)context.readField("height");
        
        return height;
    }
    
    @Override
    public void setHeight(int height) {
        if (context == null)
            return;
        
        context.writeField("height", height);
        this.height = height;
    }
    
    @Override
    public int getPixelFormat() {
        if (context == null)
            return 0;
        
        if (pixelFormat == null)
            pixelFormat = (Integer)context.readField("pix_fmt");
        
        return pixelFormat;
    }
    
    @Override
    public void setPixelFormat(int pixelFormat) {
        if (context == null)
            return;
        
        context.writeField("pix_fmt", pixelFormat);
        this.pixelFormat = pixelFormat;
    }

    @Override
    public int getBitRate() {
        if (context == null)
            return 0;
        
        if (bitRate == null)
            bitRate = (Integer)context.readField("bit_rate");
        
        return bitRate;
    }

    @Override
    public void setBitRate(int bitRate) {
        if (context == null)
            return;
        
        context.writeField("bit_rate", bitRate);
        this.bitRate = bitRate;
    }

    @Override
    public Rational getTimeBase() {
        if (context == null)
            return null;
        
        if (timeBase == null)
            timeBase = new Rational((AVRational)context.readField("time_base"));
        
        return timeBase;
    }

    @Override
    public void setTimeBase(Rational timeBase) {
        if (context == null)
            return;
        
        getTimeBase();
        context.time_base.writeField("num", (int)timeBase.getNumerator());
        context.time_base.writeField("den", (int)timeBase.getDenominator());
        this.timeBase = timeBase;
    }

    @Override
    public int getGopSize() {
        if (context == null)
            return 0;
        
        if (gopSize == null)
            gopSize = (Integer)context.readField("gop_size");
        
        return gopSize;
    }

    @Override
    public void setGopSize(int gopSize) {
        if (context == null)
            return;
        
        context.writeField("gop_size", gopSize);
        this.gopSize = gopSize;
    }

    @Override
    public int getMaxBFrames() {
        if (context == null)
            return 0;
        
        if (maxBFrames == null)
            maxBFrames = (Integer)context.readField("max_b_frames");
        
        return maxBFrames;
    }

    @Override
    public void setMaxBFrames(int maxBFrames) {
        if (context == null)
            return;
        
        context.writeField("max_b_frames", maxBFrames);
        this.maxBFrames = maxBFrames;
    }

    @Override
    public int getMbDecision() {
        if (context == null)
            return 0;
        
        if (mbDecision == null)
            mbDecision = (Integer)context.readField("mb_decision");
        
        return mbDecision;
    }

    @Override
    public void setMbDecision(int mbDecision) {
        if (context == null)
            return;
        
        context.writeField("mb_decision", mbDecision);
        this.mbDecision = mbDecision;
    }

    @Override
    public int getChannels() {
        if (context == null)
            return 0;
        
        if (channels == null)
            channels = (Integer)context.readField("channels");
        
        return channels;
    }

    @Override
    public void setChannels(int chanels) {
        if (context == null)
            return;
        
        context.writeField("channels", chanels);
        this.channels = chanels;
    }

    @Override
    public int getSampleFormat() {
        if (context == null)
            return 0;
        
        if (sampleFormat == null)
            sampleFormat = (Integer)context.readField("sample_fmt");
        
        return sampleFormat;
    }

    @Override
    public void setSampleFormat(int sampleFormat) {
        if (context == null)
            return;
        
        context.writeField("sample_fmt", sampleFormat);
        this.sampleFormat = sampleFormat;
    }

    @Override
    public int getSampleRate() {
        if (context == null)
            return 0;
        
        if (sampleRate == null)
            sampleRate = (Integer)context.readField("sample_rate");
        
        return sampleRate;
    }

    @Override
    public void setSampleRate(int sampleRate) {
        if (context == null)
            return;
        
        context.writeField("sample_rate", sampleRate);
        this.sampleRate = sampleRate;
    }
    
    @Override
    public int getFrameSize() {
        if (context == null)
            return 0;
        
        if (frameSize == null)
            frameSize = (Integer)context.readField("frame_size");
        
        return frameSize;
    }
    
    @Override
    public boolean decodeVideoFrame(IPacketWrapper packet, IFrameWrapper frame) throws LibavException {
        if (isClosed())
            return false;
        
        intByRef.setValue(0);
        
        int packetSize = packet.getSize();
        int len = codecLib.avcodec_decode_video2(context.getPointer(), frame.getPointer(), intByRef, packet.getPointer());
        if (len < 0)
            throw new LibavException(len);
        
        packetSize -= len;
        packet.setSize(packetSize);
        packet.setData(packetSize <= 0 ? null : packet.getData().share(len));
        if (intByRef.getValue() != 0) {
            frame.clearWrapperCache();
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean encodeVideoFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException {
        if (isClosed())
            return false;
        
        int size = codecLib.avcodec_encode_video(context.getPointer(), packet.getData(), packet.getSize(), frame == null ? null : frame.getPointer());
        if (size < 0)
            throw new LibavException(size);
        else if (size == 0)
            return false;
        
        packet.setSize(size);
        
        return true;
    }
    
    @Override
    public boolean decodeAudioFrame(IPacketWrapper packet, IFrameWrapper frame) throws LibavException {
        if (isClosed())
            return false;
        
        return decodeAudioFunction.decodeAudioFrame(packet, frame);
    }
    
    @Override
    public boolean encodeAudioFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException {
        if (isClosed())
            return false;
        
        return encodeAudioFunction.encodeAudioFrame(frame, packet);
    }
    
    private static interface IDecodeAudioFunction {
        boolean decodeAudioFrame(IPacketWrapper packet, IFrameWrapper frame) throws LibavException;
    }
    
    private static interface IEncodeAudioFunction {
        boolean encodeAudioFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException;
    }
    
    private class DecodeAudio3 implements IDecodeAudioFunction {
        @Override
        public boolean decodeAudioFrame(IPacketWrapper packet, IFrameWrapper frame) throws LibavException {
            intByRef.setValue(frame.getLineSize()[0]);

            int packetSize = packet.getSize();
            int len = codecLib.avcodec_decode_audio3(context.getPointer(), frame.getData()[0], intByRef, packet.getPointer());
            if (len < 0)
                throw new LibavException(len);

            packetSize -= len;
            packet.setSize(packetSize);
            packet.setData(packetSize <= 0 ? null : packet.getData().share(len));
            if (intByRef.getValue() > 0) {
                frame.clearWrapperCache();
                frame.setLineSize(0, intByRef.getValue());
                frame.setPacketPts(packet.getPts());
                frame.setPacketDts(packet.getDts());
                return true;
            }

            return false;
        }
    }
    
    private class DecodeAudio4 implements IDecodeAudioFunction {
        @Override
        public boolean decodeAudioFrame(IPacketWrapper packet, IFrameWrapper frame) throws LibavException {
            intByRef.setValue(0);
            
            frame.getDefaults();
            int packetSize = packet.getSize();
            int len = codecLib.avcodec_decode_audio4(context.getPointer(), frame.getPointer(), intByRef, packet.getPointer());
            if (len < 0)
                throw new LibavException(len);
            
            packetSize -= len;
            packet.setSize(packetSize);
            packet.setData(packetSize <= 0 ? null : packet.getData().share(len));
            if (intByRef.getValue() != 0) {
                frame.setLineSize(0, frame.getNbSamples() * getChannels() * AVSampleFormat.getBitsPerSample(getSampleFormat()) / 8);
                return true;
            }

            return false;
        }
    }
    
    private class EncodeAudio implements IEncodeAudioFunction {
        @Override
        public boolean encodeAudioFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException {
            int len = codecLib.avcodec_encode_audio(context.getPointer(), packet.getData(), packet.getSize(), frame == null ? null : frame.getData()[0]);
            if (len < 0)
                throw new LibavException(len);
            else if (len == 0)
                return false;

            packet.setSize(len);

            return true;
        }
    }
    
    private class EncodeAudio2 implements IEncodeAudioFunction {
        @Override
        public boolean encodeAudioFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException {
            intByRef.setValue(0);
            
            if (frame != null)
                frame.setNbSamples(frame.getLineSize()[0] / (getChannels() * AVSampleFormat.getBitsPerSample(getSampleFormat()) / 8));
            int len = codecLib.avcodec_encode_audio2(context.getPointer(), packet.getPointer(), frame == null ? null : frame.getPointer(), intByRef);
            if (len < 0)
                throw new LibavException(len);
            
            if (intByRef.getValue() != 0)
                return true;
            
            return false;
        }
    }
    
}

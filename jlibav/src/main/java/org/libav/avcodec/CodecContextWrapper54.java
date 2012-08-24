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
import org.libav.avcodec.bridge.AVCodecContext54;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;
import org.libav.util.Rational;

/**
 * Wrapper class for the AVCodecContext54.
 * 
 * @author Ondrej Perutka
 */
public class CodecContextWrapper54 extends AbstractCodecContextWrapper {
    
    private static final AVCodecLibrary codecLib;
    
    private static final boolean avcEncodeVideo2;
    
    static {
        codecLib = LibraryManager.getInstance().getAVCodecLibrary();
        
        avcEncodeVideo2 = codecLib.functionExists("avcodec_encode_video2");
    }
    
    private AVCodecContext54 context;
    
    private Pointer<Integer> intByRef;
    private IEncodeVideoFunction encodeVideoFunction;
    
    /**
     * Create a new wrapper for the given AVCodecContext.
     * 
     * @param context a codec context structure
     */
    public CodecContextWrapper54(AVCodecContext54 context) {
        this.context = context;
        
        this.intByRef = Pointer.allocateInt();
        if (avcEncodeVideo2)
            this.encodeVideoFunction = new EncodeVideo2();
        else
            this.encodeVideoFunction = new EncodeVideo();
    }
    
    @Override
    public Pointer getPointer() {
        if (context == null)
            return null;
        
        return Pointer.pointerTo(context);
    }
    
    @Override
    public void open(ICodecWrapper codec) throws LibavException {
        if (this.codec != null)
            close();
        
        int result = codecLib.avcodec_open2(getPointer(), codec.getPointer(), null);
        if(result < 0)
            throw new LibavException(result);
        
        this.codec = codec;
    }
    
    @Override
    public void close() {
        if (isClosed())
            return;
        
        codecLib.avcodec_close(getPointer());
        codec = null;
    }
    
    @Override
    public void free() {
        close();
        
        if (context == null)
            return;
        
        AVUtilLibrary lib = LibraryManager.getInstance().getAVUtilLibrary();
        lib.av_free(getPointer());
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
            Pointer p = context.coded_frame();
            codedFrame = p == null ? null : FrameWrapperFactory.getInstance().wrap(p);
        }
        
        return codedFrame;
    }
    
    @Override
    public int getCodecType() {
        if (context == null)
            return 0;
        
        if (codecType == null)
            codecType = context.codec_type();
        
        return codecType;
    }
    
    @Override
    public void setCodecType(int codecType) {
        if (context == null)
            return;
        
        context.codec_type(codecType);
        this.codecType = codecType;
    }
    
    @Override
    public int getCodecId() {
        if (context == null)
            return 0;
        
        if (codecId == null)
            codecId = context.codec_id();
        
        return codecId;
    }
    
    @Override
    public void setCodecId(int codecId) {
        if (context == null)
            return;
        
        context.codec_id(codecId);
        this.codecId = codecId;
    }
    
    @Override
    public int getFlags() {
        if (context == null)
            return 0;
        
        if (flags == null)
            flags = context.flags();
        
        return flags;
    }
    
    @Override
    public void setFlags(int flags) {
        if (context == null)
            return;
        
        context.flags(flags);
        this.flags = flags;
    }
    
    @Override
    public int getWidth() {
        if (context == null)
            return 0;
        
        if (width == null)
            width = context.width();
        
        return width;
    }
    
    @Override
    public void setWidth(int width) {
        if (context == null)
            return;
        
        context.width(width);
        this.width = width;
    }
    
    @Override
    public int getHeight() {
        if (context == null)
            return 0;
        
        if (height == null)
            height = context.height();
        
        return height;
    }
    
    @Override
    public void setHeight(int height) {
        if (context == null)
            return;
        
        context.height(height);
        this.height = height;
    }
    
    @Override
    public int getPixelFormat() {
        if (context == null)
            return 0;
        
        if (pixelFormat == null)
            pixelFormat = context.pix_fmt();
        
        return pixelFormat;
    }
    
    @Override
    public void setPixelFormat(int pixelFormat) {
        if (context == null)
            return;
        
        context.pix_fmt(pixelFormat);
        this.pixelFormat = pixelFormat;
    }

    @Override
    public int getBitRate() {
        if (context == null)
            return 0;
        
        if (bitRate == null)
            bitRate = context.bit_rate();
        
        return bitRate;
    }

    @Override
    public void setBitRate(int bitRate) {
        if (context == null)
            return;
        
        context.bit_rate(bitRate);
        this.bitRate = bitRate;
    }

    @Override
    public Rational getTimeBase() {
        if (context == null)
            return null;
        
        if (timeBase == null)
            timeBase = new Rational(context.time_base());
        
        return timeBase;
    }

    @Override
    public void setTimeBase(Rational timeBase) {
        if (context == null)
            return;
        
        context.time_base().num((int)timeBase.getNumerator());
        context.time_base().den((int)timeBase.getDenominator());
        this.timeBase = timeBase;
    }

    @Override
    public int getGopSize() {
        if (context == null)
            return 0;
        
        if (gopSize == null)
            gopSize = context.gop_size();
        
        return gopSize;
    }

    @Override
    public void setGopSize(int gopSize) {
        if (context == null)
            return;
        
        context.gop_size(gopSize);
        this.gopSize = gopSize;
    }

    @Override
    public int getMaxBFrames() {
        if (context == null)
            return 0;
        
        if (maxBFrames == null)
            maxBFrames = context.max_b_frames();
        
        return maxBFrames;
    }

    @Override
    public void setMaxBFrames(int maxBFrames) {
        if (context == null)
            return;
        
        context.max_b_frames(maxBFrames);
        this.maxBFrames = maxBFrames;
    }

    @Override
    public int getMbDecision() {
        if (context == null)
            return 0;
        
        if (mbDecision == null)
            mbDecision = context.mb_decision();
        
        return mbDecision;
    }

    @Override
    public void setMbDecision(int mbDecision) {
        if (context == null)
            return;
        
        context.mb_decision(mbDecision);
        this.mbDecision = mbDecision;
    }

    @Override
    public int getChannels() {
        if (context == null)
            return 0;
        
        if (channels == null)
            channels = context.channels();
        
        return channels;
    }

    @Override
    public void setChannels(int channels) {
        if (context == null)
            return;
        
        context.channels(channels);
        this.channels = channels;
    }

    @Override
    public int getSampleFormat() {
        if (context == null)
            return 0;
        
        if (sampleFormat == null)
            sampleFormat = context.sample_fmt();
        
        return sampleFormat;
    }

    @Override
    public void setSampleFormat(int sampleFormat) {
        if (context == null)
            return;
        
        context.sample_fmt(sampleFormat);
        this.sampleFormat = sampleFormat;
    }

    @Override
    public int getSampleRate() {
        if (context == null)
            return 0;
        
        if (sampleRate == null)
            sampleRate = context.sample_rate();
        
        return sampleRate;
    }

    @Override
    public void setSampleRate(int sampleRate) {
        if (context == null)
            return;
        
        context.sample_rate(sampleRate);
        this.sampleRate = sampleRate;
    }
    
    @Override
    public int getFrameSize() {
        if (context == null)
            return 0;
        
        if (frameSize == null)
            frameSize = context.frame_size();
        
        return frameSize;
    }

    @Override
    public boolean decodeVideoFrame(IPacketWrapper packet, IFrameWrapper frame) throws LibavException {
        if (isClosed())
            return false;
        
        intByRef.setInt(0);
        
        int packetSize = packet.getSize();
        int len = codecLib.avcodec_decode_video2(getPointer(), frame.getPointer(), intByRef, packet.getPointer());
        if (len < 0)
            throw new LibavException(len);
        
        packetSize -= len;
        packet.setSize(packetSize);
        packet.setData(packetSize <= 0 ? null : packet.getData().offset(len));
        if (intByRef.getInt() != 0) {
            frame.clearWrapperCache();
            return true;
        }
        
        return false;
    }

    @Override
    public boolean encodeVideoFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException {
        if (isClosed())
            return false;
        
        return encodeVideoFunction.encodeVideoFrame(frame, packet);
    }

    @Override
    public boolean decodeAudioFrame(IPacketWrapper packet, IFrameWrapper frame) throws LibavException {
        if (isClosed())
            return false;
        
        intByRef.setInt(0);

        frame.getDefaults();
        int packetSize = packet.getSize();
        int len = codecLib.avcodec_decode_audio4(getPointer(), frame.getPointer(), intByRef, packet.getPointer());
        if (len < 0)
            throw new LibavException(len);

        packetSize -= len;
        packet.setSize(packetSize);
        packet.setData(packetSize <= 0 ? null : packet.getData().offset(len));
        if (intByRef.getInt() != 0) {
            frame.getLineSize().set(0, frame.getNbSamples() * getChannels() * AVSampleFormat.getBytesPerSample(getSampleFormat()));
            return true;
        }

        return false;
    }

    @Override
    public boolean encodeAudioFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException {
        if (isClosed())
            return false;
        
        intByRef.setInt(0);

        int len = codecLib.avcodec_encode_audio2(getPointer(), packet.getPointer(), frame == null ? null : frame.getPointer(), intByRef);
        if (len < 0)
            throw new LibavException(len);

        if (intByRef.getInt() != 0)
            return true;

        return false;
    }
    
    private static interface IEncodeVideoFunction {
        boolean encodeVideoFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException;
    }
    
    private class EncodeVideo implements IEncodeVideoFunction {
        @Override
        public boolean encodeVideoFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException {
            int size = codecLib.avcodec_encode_video(getPointer(), packet.getData(), packet.getSize(), frame == null ? null : frame.getPointer());
            if (size < 0)
                throw new LibavException(size);
            else if (size == 0)
                return false;

            packet.setSize(size);

            return true;
        }
    }
    
    private class EncodeVideo2 implements IEncodeVideoFunction {
        @Override
        public boolean encodeVideoFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException {
            intByRef.setInt(0);

            int len = codecLib.avcodec_encode_video2(getPointer(), packet.getPointer(), frame == null ? null : frame.getPointer(), intByRef);
            if (len < 0)
                throw new LibavException(len);

            if (intByRef.getInt() != 0)
                return true;

            return false;
        }
    }
    
}

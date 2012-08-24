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
import org.libav.avcodec.bridge.AVFrame53;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVFrame53.
 * 
 * @author Ondrej Perutka
 */
public class FrameWrapper53 extends AbstractFrameWrapper {
    
    private static final AVCodecLibrary codecLib;
    private static final AVUtilLibrary utilLib;

    private static final boolean hasNbSamples;
    private static final boolean hasAvcodecFillAudioFrame;
    
    static {
        codecLib = LibraryManager.getInstance().getAVCodecLibrary();
        utilLib = LibraryManager.getInstance().getAVUtilLibrary();
        
        hasNbSamples = codecLib.getMajorVersion() > 53 || (codecLib.getMajorVersion() == 53 && codecLib.getMinorVersion() >= 25);
        hasAvcodecFillAudioFrame = codecLib.functionExists("avcodec_fill_audio_frame");
    }
    
    private AVFrame53 frame;
    private IFillAudioFrameFunction fillAudioFrameFunction;
    
    private Pointer[] toBeFreed;
    
    /**
     * Create a new wrapper for the given AVFrame.
     * 
     * @param frame an AVFrame structure
     */
    public FrameWrapper53(AVFrame53 frame) {
        this.frame = frame;
        
        if (hasAvcodecFillAudioFrame)
            fillAudioFrameFunction = new FillAudioFrameFunctionLibav();
        else
            fillAudioFrameFunction = new FillAudioFrameFunctionManual();
        
        this.toBeFreed = null;
    }
    
    @Override
    public Pointer<?> getPointer() {
        if (frame == null)
            return null;
        
        return Pointer.pointerTo(frame);
    }
    
    @Override
    public void free() {
        if (frame == null)
            return;
        
        if (toBeFreed != null) {
            for (Pointer p : toBeFreed)
                utilLib.av_free(p);
        }
        utilLib.av_free(getPointer());
        frame = null;
        toBeFreed = null;
    }
    
    @Override
    public void getDefaults() {
        if (frame == null)
            return;
        
        codecLib.avcodec_get_frame_defaults(getPointer());
        clearWrapperCache();
    }

    @Override
    public void fillAudioFrame(int sampleCount, int channelCount, int sampleFormat, Pointer<Byte> buffer, int bufferSize) throws LibavException {
        fillAudioFrameFunction.fillAudioFrame(sampleCount, channelCount, sampleFormat, buffer, bufferSize);
    }
    
    @Override
    public Pointer<Pointer<Byte>> getData() {
        if (frame == null)
            return null;
        
        if (data == null)
            data = frame.data();
        
        return data;
    }

    @Override
    public int getDataLength() {
        return 4;
    }
    
    @Override
    public Pointer<Integer> getLineSize() {
        if (frame == null)
            return null;
        
        if (lineSize == null)
            lineSize = frame.linesize();
        
        return lineSize;
    }

    @Override
    public int getLineSizeLength() {
        return 4;
    }
    
    @Override
    public boolean isKeyFrame() {
        if (frame == null)
            return false;
        
        if (keyFrame == null)
            keyFrame = frame.key_frame() == 0 ? false : true;
        
        return keyFrame;
    }

    @Override
    public void setKeyFrame(boolean keyFrame) {
        if (frame == null)
            return;
        
        frame.key_frame(keyFrame ? 1 : 0);
        this.keyFrame = keyFrame;
    }
    
    @Override
    public long getPts() {
        if (frame == null)
            return 0;
        
        if (pts == null)
            pts = frame.pts();
        
        return pts;
    }

    @Override
    public void setPts(long pts) {
        if (frame == null)
            return;
        
        frame.pts(pts);
        this.pts = pts;
    }
    
    @Override
    public int getRepeatPicture() {
        if (frame == null)
            return 0;
        
        if (repeatPicture == null)
            repeatPicture = frame.repeat_pict();
        
        return repeatPicture;
    }

    @Override
    public void setRepeatPicture(int repeatPicture) {
        if (frame == null)
            return;
        
        frame.repeat_pict(repeatPicture);
        this.repeatPicture = repeatPicture;
    }
    
    @Override
    public long getPacketDts() {
        if (frame == null)
            return 0;
        
        if (packetDts == null)
            packetDts = frame.pkt_dts();
        
        return packetDts;
    }

    @Override
    public void setPacketDts(long packetDts) {
        if (frame == null)
            return;
        
        frame.pkt_dts(packetDts);
        this.packetDts = packetDts;
    }
    
    @Override
    public long getPacketPts() {
        if (frame == null)
            return 0;
        
        if (packetPts == null)
            packetPts = frame.pkt_pts();
        
        return packetPts;
    }

    @Override
    public void setPacketPts(long packetPts) {
        if (frame == null)
            return;
        
        frame.pkt_pts(packetPts);
        this.packetPts = packetPts;
    }

    @Override
    public int getNbSamples() {
        if (!hasNbSamples)
            throw new UnsatisfiedLinkError("the property is not supported in this version of the libavcodec");
        if (frame == null)
            return 0;
        
        if (nbSamples == null)
            nbSamples = frame.nb_samples();
        
        return nbSamples;
    }

    @Override
    public void setNbSamples(int nbSamples) {
        if (!hasNbSamples)
            throw new UnsatisfiedLinkError("the property is not supported in this version of the libavcodec");
        if (frame == null)
            return;
        
        frame.nb_samples(nbSamples);
        this.nbSamples = nbSamples;
    }
    
    public static FrameWrapper53 allocatePicture(int pixelFormat, int width, int height) throws LibavException {
        FrameWrapper53 result = allocateFrame();
        Pointer data;

        try {
            data = allocatePictureBuffer(pixelFormat, width, height);
        } catch (LibavException ex) {
            utilLib.av_free(result.getPointer());
            throw ex;
        }

        result.toBeFreed = new Pointer[] { data };
        codecLib.avpicture_fill(result.getPointer(), data, pixelFormat, width, height);

        return result;
    }
    
    public static FrameWrapper53 allocateFrame() throws LibavException {
        Pointer<?> ptr = codecLib.avcodec_alloc_frame();
        if (ptr == null)
            throw new LibavException("unable to allocate a new frame");
        
        return new FrameWrapper53(new AVFrame53(ptr));
    }
    
    private static Pointer<?> allocatePictureBuffer(int pixelFormat, int width, int height) throws LibavException {
        int size = codecLib.avpicture_get_size(pixelFormat, width, height);
        Pointer<?> result = utilLib.av_malloc(size);
        if (result == null)
            throw new LibavException("unable to allocate a new picture");

        return result;
    }
    
    private static interface IFillAudioFrameFunction {
        void fillAudioFrame(int sampleCount, int channelCount, int sampleFormat, Pointer<Byte> buffer, int bufferSize) throws LibavException;
    }
    
    private class FillAudioFrameFunctionManual implements IFillAudioFrameFunction {
        @Override
        public void fillAudioFrame(int sampleCount, int channelCount, int sampleFormat, Pointer<Byte> buffer, int bufferSize) throws LibavException {
            if (frame == null)
                return;
            
            if (hasNbSamples)
                setNbSamples(sampleCount);
            
            int lineSize = sampleCount * channelCount * AVSampleFormat.getBytesPerSample(sampleFormat);
            if (lineSize > bufferSize)
                throw new LibavException("not enough audio data");
            
            getLineSize().set(0, lineSize);
            getData().set(0, buffer);
        }
    }
    
    private class FillAudioFrameFunctionLibav implements IFillAudioFrameFunction {
        @Override
        public void fillAudioFrame(int sampleCount, int channelCount, int sampleFormat, Pointer<Byte> buffer, int bufferSize) throws LibavException {
            if (frame == null)
                return;

            setNbSamples(sampleCount);
            int res = codecLib.avcodec_fill_audio_frame(getPointer(), channelCount, sampleFormat, buffer, bufferSize, 1);
            if (res != 0)
                throw new LibavException(res);
        }
    }
    
}

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
import org.libav.avcodec.bridge.AVFrame54;
import org.libav.avutil.PixelFormat;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVFrame54.
 * 
 * @author Ondrej Perutka
 */
public class FrameWrapper54 extends AbstractFrameWrapper {
    
    private static final AVCodecLibrary codecLib;
    private static final AVUtilLibrary utilLib;
    
    private static final IFreeFrame freeFrame;
    
    static {
        codecLib = LibraryManager.getInstance().getAVCodecLibrary();
        utilLib = LibraryManager.getInstance().getAVUtilLibrary();
        
        if (codecLib.functionExists("avcodec_free_frame"))
            freeFrame = new FreeFrame2();
        else
            freeFrame = new FreeFrame();
    }
    
    private AVFrame54 frame;
    
    private Pointer[] toBeFreed;
    
    /**
     * Create a new wrapper for the given AVFrame.
     * 
     * @param frame an AVFrame structure
     */
    public FrameWrapper54(AVFrame54 frame) {
        this.frame = frame;
        
        this.toBeFreed = null;
    }
    
    @Override
    public Pointer<?> getPointer() {
        if (frame == null)
            return null;
        
        return Pointer.pointerTo(frame);
    }

    @Override
    public void rebind(Pointer<?> pointer) {
        frame = new AVFrame54(pointer);
    }
    
    @Override
    public void free() {
        freeFrame.free(this);
    }
    
    @Override
    public void getDefaults() {
        if (frame == null)
            return;
        
        codecLib.avcodec_get_frame_defaults(getPointer());
        clearWrapperCache();
    }

    @Override
    public void fillAudioFrame(int sampleCount, int channelCount, SampleFormat sampleFormat, Pointer<Byte> buffer, int bufferSize) throws LibavException {
        if (frame == null)
            return;
        
        setNbSamples(sampleCount);
        int res = codecLib.avcodec_fill_audio_frame(getPointer(), channelCount, sampleFormat.value(), buffer, bufferSize, 1);
        if (res != 0)
            throw new LibavException(res);
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
        return 8;
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
        return 8;
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
        if (frame == null)
            return 0;
        
        if (nbSamples == null)
            nbSamples = frame.nb_samples();
        
        return nbSamples;
    }

    @Override
    public void setNbSamples(int nbSamples) {
        if (frame == null)
            return;
        
        frame.nb_samples(nbSamples);
        this.nbSamples = nbSamples;
    }
    
    private static interface IFreeFrame {
        void free(FrameWrapper54 frame);
    }
    
    private static class FreeFrame implements IFreeFrame {
        @Override
        public void free(FrameWrapper54 frame) {
            if (frame.frame == null)
                return;

            if (frame.toBeFreed != null) {
                for (Pointer p : frame.toBeFreed)
                    utilLib.av_free(p);
            }
            utilLib.av_free(frame.getPointer());
            
            frame.frame = null;
            frame.toBeFreed = null;
        }
    }
    
    private static class FreeFrame2 implements IFreeFrame {
        @Override
        public void free(FrameWrapper54 frame) {
            if (frame.frame == null)
                return;

            if (frame.toBeFreed != null) {
                for (Pointer p : frame.toBeFreed)
                    utilLib.av_free(p);
            }
            Pointer<Pointer<?>> tmp = Pointer.allocatePointer();
            tmp.set(frame.getPointer());
            codecLib.avcodec_free_frame(tmp);
            
            frame.frame = null;
            frame.toBeFreed = null;
        }
    }
    
    public static FrameWrapper54 allocatePicture(PixelFormat pixelFormat, int width, int height) throws LibavException {
        FrameWrapper54 result = allocateFrame();
        Pointer data;

        try {
            data = allocatePictureBuffer(pixelFormat, width, height);
        } catch (LibavException ex) {
            utilLib.av_free(result.getPointer());
            throw ex;
        }

        result.toBeFreed = new Pointer[] { data };
        codecLib.avpicture_fill(result.getPointer(), data, pixelFormat.value(), width, height);

        return result;
    }
    
    public static FrameWrapper54 allocateFrame() throws LibavException {
        Pointer<?> ptr = codecLib.avcodec_alloc_frame();
        if (ptr == null)
            throw new LibavException("unable to allocate a new frame");
        
        return new FrameWrapper54(new AVFrame54(ptr));
    }
    
    private static Pointer<?> allocatePictureBuffer(PixelFormat pixelFormat, int width, int height) throws LibavException {
        int size = codecLib.avpicture_get_size(pixelFormat.value(), width, height);
        if (size <= 0)
            throw new LibavException("invalid picture size");
        
        Pointer<?> result = utilLib.av_malloc(size);
        if (result == null)
            throw new LibavException("unable to allocate a new picture");

        return result;
    }
    
}

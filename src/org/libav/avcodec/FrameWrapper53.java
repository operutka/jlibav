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
import org.libav.avcodec.bridge.AVFrame53;
import org.libav.avcodec.bridge.IAVCodecLibrary;
import org.libav.avutil.bridge.IAVUtilLibrary;
import org.libav.bridge.LibavLibraryWrapper;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVFrame53.
 * 
 * @author Ondrej Perutka
 */
public class FrameWrapper53 extends AbstractFrameWrapper {
    
    private static final IAVCodecLibrary codecLib;
    private static final IAVUtilLibrary utilLib;

    private static final boolean hasNbSamples;
    
    static {
        LibraryManager lm = LibraryManager.getInstance();
        LibavLibraryWrapper<IAVCodecLibrary> avc = lm.getAVCodecLibraryWrapper();
        codecLib = avc.getLibrary();
        utilLib = lm.getAVUtilLibraryWrapper().getLibrary();
        
        hasNbSamples = avc.getMajorVersion() > 53 || (avc.getMajorVersion() == 53 && avc.getMinorVersion() >= 25);
    }
    
    private AVFrame53 frame;
    
    private Pointer[] toBeFreed;
    
    /**
     * Create a new wrapper for the given AVFrame.
     * 
     * @param frame an AVFrame structure
     */
    public FrameWrapper53(AVFrame53 frame) {
        this.frame = frame;
        
        this.toBeFreed = null;
    }
    
    @Override
    public Pointer getPointer() {
        if (frame == null)
            return null;
        
        return frame.getPointer();
    }
    
    @Override
    public void free() {
        if (frame == null)
            return;
        
        if (toBeFreed != null) {
            for (Pointer p : toBeFreed)
                utilLib.av_free(p);
        }
        utilLib.av_free(frame.getPointer());
        frame = null;
        toBeFreed = null;
    }
    
    @Override
    public void getDefaults() {
        if (frame == null)
            return;
        
        codecLib.avcodec_get_frame_defaults(frame.getPointer());
        clearWrapperCache();
    }
    
    @Override
    public Pointer[] getData() {
        if (frame == null)
            return null;
        
        if (data == null)
            data = (Pointer[])frame.readField("data");
        
        return data;
    }
    
    @Override
    public void setData(int index, Pointer ptr) {
        if (frame == null)
            return;
        
        getData();
        data[index] = ptr;
        frame.writeField("data", data);
    }

    @Override
    public int getDataLength() {
        return 4;
    }
    
    @Override
    public int[] getLineSize() {
        if (frame == null)
            return null;
        
        if (lineSize == null)
            lineSize = (int[])frame.readField("linesize");
        
        return lineSize;
    }
    
    @Override
    public void setLineSize(int index, int size) {
        if (frame == null)
            return;
        
        getLineSize();
        lineSize[index] = size;
        frame.writeField("linesize", lineSize);
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
            keyFrame = (Integer)frame.readField("key_frame") == 0 ? false : true;
        
        return keyFrame;
    }

    @Override
    public void setKeyFrame(boolean keyFrame) {
        if (frame == null)
            return;
        
        frame.writeField("key_frame", keyFrame);
        this.keyFrame = keyFrame;
    }
    
    @Override
    public long getPts() {
        if (frame == null)
            return 0;
        
        if (pts == null)
            pts = (Long)frame.readField("pts");
        
        return pts;
    }

    @Override
    public void setPts(long pts) {
        if (frame == null)
            return;
        
        frame.writeField("pts", pts);
        this.pts = pts;
    }
    
    @Override
    public int getRepeatPicture() {
        if (frame == null)
            return 0;
        
        if (repeatPicture == null)
            repeatPicture = (Integer)frame.readField("repeat_pict");
        
        return repeatPicture;
    }

    @Override
    public void setRepeatPicture(int repeatPicture) {
        if (frame == null)
            return;
        
        frame.writeField("repeat_pict", repeatPicture);
        this.repeatPicture = repeatPicture;
    }
    
    @Override
    public long getPacketDts() {
        if (frame == null)
            return 0;
        
        if (packetDts == null)
            packetDts = (Long)frame.readField("pkt_dts");
        
        return packetDts;
    }

    @Override
    public void setPacketDts(long packetDts) {
        if (frame == null)
            return;
        
        frame.writeField("pkt_dts", packetDts);
        this.packetDts = packetDts;
    }
    
    @Override
    public long getPacketPts() {
        if (frame == null)
            return 0;
        
        if (packetPts == null)
            packetPts = (Long)frame.readField("pkt_pts");
        
        return packetPts;
    }

    @Override
    public void setPacketPts(long packetPts) {
        if (frame == null)
            return;
        
        frame.writeField("pkt_pts", packetPts);
        this.packetPts = packetPts;
    }

    @Override
    public int getNbSamples() {
        if (!hasNbSamples)
            throw new UnsatisfiedLinkError("the property is not supported in this version of the libavcodec");
        if (frame == null)
            return 0;
        
        if (nbSamples == null)
            nbSamples = (Integer)frame.readField("nb_samples");
        
        return nbSamples;
    }

    @Override
    public void setNbSamples(int nbSamples) {
        if (!hasNbSamples)
            throw new UnsatisfiedLinkError("the property is not supported in this version of the libavcodec");
        if (frame == null)
            return;
        
        frame.writeField("nb_samples", nbSamples);
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
        Pointer ptr = codecLib.avcodec_alloc_frame();
        if (ptr == null)
            throw new LibavException("unable to allocate a new frame");
        
        return new FrameWrapper53(new AVFrame53(ptr));
    }
    
    private static Pointer allocatePictureBuffer(int pixelFormat, int width, int height) throws LibavException {
        int size = codecLib.avpicture_get_size(pixelFormat, width, height);
        Pointer result = utilLib.av_malloc(size);
        if (result == null)
            throw new LibavException("unable to allocate a new picture");

        return result;
    }
    
}

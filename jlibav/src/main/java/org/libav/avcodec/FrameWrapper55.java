/*
 * Copyright (C) 2013 Ondrej Perutka
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
import org.libav.avcodec.bridge.AVFrame55;
import org.libav.avutil.PixelFormat;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVFrame55.
 * 
 * @author Ondrej Perutka
 */
public class FrameWrapper55 extends AbstractFrameWrapper {
    
    private static final AVCodecLibrary codecLib;
    private static final AVUtilLibrary utilLib;
    
    static {
        codecLib = LibraryManager.getInstance().getAVCodecLibrary();
        utilLib = LibraryManager.getInstance().getAVUtilLibrary();
    }
    
    private AVFrame55 frame;
    private Integer width;
    private Integer height;
    private Integer format;
    
    /**
     * Create a new wrapper for the given AVFrame.
     * 
     * @param frame an AVFrame structure
     */
    public FrameWrapper55(AVFrame55 frame) {
        this.frame = frame;
        this.width = null;
        this.height = null;
        this.format = null;
    }

    @Override
    public void clearWrapperCache() {
        super.clearWrapperCache();
        
        this.width = null;
        this.height = null;
        this.format = null;
    }
    
    @Override
    public Pointer<?> getPointer() {
        if (frame == null)
            return null;
        
        return Pointer.pointerTo(frame);
    }

    @Override
    public void rebind(Pointer<?> pointer) {
        frame = new AVFrame55(pointer);
    }
    
    @Override
    public void free() {
        if (frame == null)
            return;

        Pointer<Pointer<?>> tmp = Pointer.allocatePointer();
        tmp.set(getPointer());
        utilLib.av_frame_free(tmp);

        frame = null;
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
        fillAudioFrame(sampleCount, channelCount, sampleFormat, buffer, bufferSize, sampleCount);
    }
    
    @Override
    public void fillAudioFrame(int sampleCount, int channelCount, SampleFormat sampleFormat, Pointer<Byte> buffer, int bufferSize, int bufferSampleCapacity) throws LibavException {
        if (frame == null)
            return;
        
        setNbSamples(bufferSampleCapacity);
        int res = codecLib.avcodec_fill_audio_frame(getPointer(), channelCount, sampleFormat.value(), buffer, bufferSize, 1);
        if (res != 0)
            throw new LibavException(res);
        
        int ls = sampleCount * sampleFormat.getBytesPerSample();
        if (!sampleFormat.isPlanar())
            ls *= channelCount;
        
        getLineSize().set(0, ls);
        setNbSamples(sampleCount);
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
    public Pointer<Pointer<Byte>> getExtendedData() {
        if (frame == null)
            return null;
        
        if (extendedData == null)
            extendedData = frame.extended_data();
        
        return extendedData;
    }

    @Override
    public void setExtendedData(Pointer<Pointer<Byte>> extendedData) {
        if (frame == null)
            return;
        
        frame.extended_data(extendedData);
        this.extendedData = extendedData;
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
            keyFrame = frame.key_frame() != 0;
        
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
    
    public int getWidth() {
        if (frame == null)
            return 0;
        
        if (width == null)
            width = frame.width();
        
        return width;
    }
    
    public void setWidth(int width) {
        if (frame == null)
            return;
        
        frame.width(width);
        this.width = width;
    }
    
    public int getHeight() {
        if (frame == null)
            return 0;
        
        if (height == null)
            height = frame.height();
        
        return height;
    }
    
    public void setHeight(int height) {
        if (frame == null)
            return;
        
        frame.height(height);
        this.height = height;
    }
    
    public int getFormat() {
        if (frame == null)
            return 0;
        
        if (format == null)
            format = frame.format();
        
        return format;
    }
    
    public void setFormat(int format) {
        if (frame == null)
            return;
        
        frame.format(format);
        this.format = format;
    }
    
    public static FrameWrapper55 allocatePicture(PixelFormat pixelFormat, int width, int height) throws LibavException {
        FrameWrapper55 result = allocateFrame();
        result.setWidth(width);
        result.setHeight(height);
        result.setFormat(pixelFormat.value());
        
        Pointer<?> framePtr = result.getPointer();
        
        int err = utilLib.av_frame_get_buffer(framePtr, 32);
        if (err != 0)
            throw new LibavException(err);
        
        return result;
    }
    
    public static FrameWrapper55 allocateFrame() throws LibavException {
        Pointer<?> ptr = utilLib.av_frame_alloc();
        if (ptr == null)
            throw new LibavException("unable to allocate a new frame");
        
        return new FrameWrapper55(new AVFrame55(ptr));
    }
    
}

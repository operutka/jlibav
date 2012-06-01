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
package org.libav.video;

import java.nio.ByteOrder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.libav.LibavException;
import org.libav.avcodec.FrameWrapperFactory;
import org.libav.avcodec.IFrameWrapper;
import org.libav.avutil.bridge.PixelFormat;
import org.libav.data.IFrameConsumer;
import org.libav.swscale.ScaleContextWrapper;
import org.libav.swscale.bridge.ISWScaleLibrary;

/**
 * Frame to VideoFrame adapter. It translates frame wrappers to video frames.
 * 
 * @author Ondrej Perutka
 */
public class Frame2VideoFrameAdapter implements IFrameConsumer, IVideoFrameProducer {
    
    private int srcWidth;
    private int srcHeight;
    private int srcFormat;
    
    private int dstWidth;
    private int dstHeight;
    private int dstFormat;
    
    private ScaleContextWrapper scaleContext;
    private int scalingAlg;
    
    private IFrameWrapper rgbPicture;
    
    private final Set<IVideoFrameConsumer> consumers;

    /**
     * Create a new frame to video frame adapter and set scaling parameters.
     * 
     * @param srcWidth a width of source images
     * @param srcHeight a height of source images
     * @param srcFormat a pixel format of source images
     * @param dstWidth a width of produced images
     * @param dstHeight a height of produced images
     * @throws LibavException if an error occurs
     */
    public Frame2VideoFrameAdapter(int srcWidth, int srcHeight, int srcFormat, int dstWidth, int dstHeight) throws LibavException {
        this.srcWidth = srcWidth;
        this.srcHeight = srcHeight;
        this.srcFormat = srcFormat;
        this.dstWidth = dstWidth;
        this.dstHeight = dstHeight;
        
        dstFormat = PixelFormat.PIX_FMT_BGRA;
        if (ByteOrder.BIG_ENDIAN.equals(ByteOrder.nativeOrder()))
            dstFormat = PixelFormat.PIX_FMT_ARGB;
        scalingAlg = ISWScaleLibrary.SWS_BICUBIC;
        
        init();
        
        consumers = Collections.synchronizedSet(new HashSet<IVideoFrameConsumer>());
    }
    
    private void init() throws LibavException {
        if (scaleContext != null)
            scaleContext.free();
        if (rgbPicture != null)
            rgbPicture.free();
        scaleContext = ScaleContextWrapper.createContext(srcWidth, srcHeight, srcFormat, dstWidth, dstHeight, dstFormat, scalingAlg);
        rgbPicture = FrameWrapperFactory.getInstance().allocPicture(dstFormat, dstWidth, dstHeight);
    }
    
    /**
     * Set expected format of source images.
     * 
     * @param width a width
     * @param height a height
     * @param pixelFormat a pixel format
     * @throws LibavException if an error occurs
     */
    public synchronized void setSourceImageFormat(int width, int height, int pixelFormat) throws LibavException {
        if (width != srcWidth || height != srcHeight || srcFormat != pixelFormat) {
            srcWidth = width;
            srcHeight = height;
            srcFormat = pixelFormat;
            init();
        }
    }
    
    /**
     * Get expected width of source images.
     * 
     * @return expected width of source images
     */
    public int getSourceImageWidth() {
        return srcWidth;
    }
    
    /**
     * Get expected height of source images.
     * 
     * @return expected height of source images
     */
    public int getSourceImageHeight() {
        return srcHeight;
    }
    
    /**
     * Get expected pixel format of source images.
     * 
     * @return expected pixel format of source images
     */
    public int getSourceImagePixelFormat() {
        return srcFormat;
    }
    
    /**
     * Set format of produced images.
     * 
     * @param width a width
     * @param height a height
     * @throws LibavException if an error occurs
     */
    public synchronized void setDestinationImageFormat(int width, int height) throws LibavException {
        if (width != dstWidth || height != dstHeight) {
            dstWidth = width;
            dstHeight = height;
            init();
        }
    }
    
    /**
     * Get width of produced images.
     * 
     * @return width of produced images
     */
    public int getDestinationImageWidth() {
        return dstWidth;
    }
    
    /**
     * Get height of produced images.
     * 
     * @return height of produced images
     */
    public int getDestinationImageHeight() {
        return dstHeight;
    }
    
    /**
     * Set scaling algorithm.
     * 
     * @param scalingAlgorithm a scaling algorithm
     * @throws LibavException if an error occurs
     */
    public synchronized void setScalingAlgorithm(int scalingAlgorithm) throws LibavException {
        if (scalingAlgorithm != scalingAlg) {
            scalingAlg = scalingAlgorithm;
            init();
        }
    }
    
    /**
     * Get scaling algorithm.
     * 
     * @return scaling algorithm
     */
    public int getScalingAlgorithm() {
        return scalingAlg;
    }
    
    /**
     * Release all native sources.
     */
    public synchronized void dispose() {
        if (scaleContext != null)
            scaleContext.free();
        if (rgbPicture != null)
            rgbPicture.free();
        
        scaleContext = null;
        rgbPicture = null;
    }

    @Override
    public synchronized void processFrame(Object producer, IFrameWrapper frame) throws LibavException {
        if (scaleContext == null)
            return;
        
        scaleContext.scale(frame, rgbPicture, 0, srcHeight);
        int[] pixels = rgbPicture.getData()[0].getIntArray(0, dstWidth * dstHeight);
        sendVideoFrame(new VideoFrame(pixels, dstWidth, dstHeight, frame.getPts()));
    }
    
    private void sendVideoFrame(VideoFrame vf) throws LibavException {
        synchronized (consumers) {
            for (IVideoFrameConsumer c : consumers)
                c.processFrame(this, vf);
        }
    }

    @Override
    public void addVideoFrameConsumer(IVideoFrameConsumer consumer) {
        consumers.add(consumer);
    }

    @Override
    public void removeVideoFrameConsumer(IVideoFrameConsumer consumer) {
        consumers.remove(consumer);
    }
    
    public int getConsumerCount() {
        return consumers.size();
    }
    
}

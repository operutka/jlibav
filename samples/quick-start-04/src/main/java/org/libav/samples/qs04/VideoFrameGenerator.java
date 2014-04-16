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
package org.libav.samples.qs04;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.nio.ByteOrder;
import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.FrameWrapperFactory;
import org.libav.avcodec.IFrameWrapper;
import org.libav.avutil.PixelFormat;
import org.libav.data.IFrameConsumer;
import org.libav.data.IFrameProducer;
import org.libav.video.FrameScaler;

/**
 * Generator for video frames. It creates video frames filled with solid color
 * which periodicaly changes its shades of red, green and blue. The period
 * for each color component is given by RED_PERIOD, GREEN_PERIOD and BLUE_PERIOD
 * constants. These constants are in miliseconds.
 * 
 * @author Ondrej Perutka
 */
public class VideoFrameGenerator implements IFrameProducer {
    
    private static final int RED_PERIOD = 7000;
    private static final int GREEN_PERIOD = 11000;
    private static final int BLUE_PERIOD = 17000;
    
    private final FrameScaler frameScaler;
    private final IFrameWrapper videoFrame;
    
    private final BufferedImage image;
    private final int[] pixels;
    private final Graphics2D imageGraphics;
    
    private Font font;
    private final FontRenderContext fontRenderContext;
    
    private final int frameDuration;
    private long nextFrameTime;
    
    /**
     * Create a new video frame generator.
     * 
     * @param frameDuration duration of each video frame
     * @param width video width
     * @param height video height
     * @param pixelFormat output pixel format
     * @throws LibavException if it is not possible to initialize the generator
     */
    public VideoFrameGenerator(int frameDuration, int width, int height, PixelFormat pixelFormat) throws LibavException {
        this.frameDuration = frameDuration;
        
        // get the right internal pixel format according to the platform's endianity
        PixelFormat internalPixelFormat = PixelFormat.BGRA;
        if (ByteOrder.BIG_ENDIAN.equals(ByteOrder.nativeOrder()))
            internalPixelFormat = PixelFormat.ARGB;
        
        // create a new video frame scaler from internal format to the given output format
        frameScaler = new FrameScaler(width, height, internalPixelFormat, width, height, pixelFormat);
        
        FrameWrapperFactory frameFactory = FrameWrapperFactory.getInstance();
        
        // create a new Libav frame for video frames
        videoFrame = frameFactory.allocPicture(internalPixelFormat, width, height);
        // create pixel array
        pixels = new int[width * height];
        // create a new BufferedImage on the top of the pixel array (it is much
        // faster than retrieving pixel array using the getRGB() method)
        DataBuffer db = new DataBufferInt(pixels, pixels.length);
        int[] masks = new int[] { 0x00ff0000, 0x0000ff00, 0x000000ff };
        SampleModel sm = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, width, height, masks);
        WritableRaster wr = Raster.createWritableRaster(sm, db, new Point());
        image = new BufferedImage(new DirectColorModel(24, 0x00ff0000, 0x0000ff00, 0x000000ff), wr, false, null);
        // create graphics object for the buffered image
        imageGraphics = image.createGraphics();
        
        // prepare font and font rendering context to put some info into the frames
        font = imageGraphics.getFont();
        font = font.deriveFont(14f);
        fontRenderContext = new FontRenderContext(null, true, true);
        
        // presentation time stamp of the next video frame
        nextFrameTime = 0;
    }
    
    /**
     * Release all resources.
     */
    public void dispose() {
        frameScaler.dispose();
        videoFrame.free();
    }

    /**
     * Add frame consumer.
     * 
     * @param c frame consumer
     */
    public void addFrameConsumer(IFrameConsumer c) {
        frameScaler.addFrameConsumer(c);
    }

    /**
     * Remove frame consumer.
     * 
     * @param c frame consumer
     */
    public void removeFrameConsumer(IFrameConsumer c) {
        frameScaler.removeFrameConsumer(c);
    }
    
    /**
     * Repaint the BufferedImage.
     */
    private void updateImage() {
        // get values for each color component depending on the frame's
        // presentation time stamp
        float red = Math.abs((float)Math.sin(2 * Math.PI * nextFrameTime / RED_PERIOD));
        float green = Math.abs((float)Math.sin(2 * Math.PI * nextFrameTime / GREEN_PERIOD));
        float blue = Math.abs((float)Math.sin(2 * Math.PI * nextFrameTime / BLUE_PERIOD));
        
        // fill the image with solid color
        imageGraphics.setColor(new Color(red, green, blue));
        imageGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        // draw some info into the bottom left corner of the buffered image
        String text = String.format("%g, R = %g, G = %g, B = %g", nextFrameTime / 1000.0, red, green, blue);
        TextLayout tl = new TextLayout(text, font, fontRenderContext);
        Rectangle2D tlb = tl.getBounds();
        
        imageGraphics.setColor(Color.BLACK);
        imageGraphics.fillRect(0, image.getHeight() - 10 - (int)tlb.getHeight(), 
                (int)tlb.getWidth() + 10, (int)tlb.getHeight() + 10);
        imageGraphics.setColor(Color.GRAY);
        tl.draw(imageGraphics, 5, image.getHeight() - 5);
    }
    
    /**
     * Create a new video frame and pass it to all frame consumers.
     * 
     * @throws LibavException 
     */
    public void nextFrame() throws LibavException {
        // repaint buffered image
        updateImage();
        
        // get pointer to the first plane of the Libav frame
        Pointer<Pointer<Byte>> framePlanes = videoFrame.getData();
        Pointer<Byte> framePixels = framePlanes.get(0);
        // fill the plane with the pixel array
        framePixels.setInts(pixels);
        
        // set frame's presentation time stamp
        videoFrame.setPts(nextFrameTime);
        // pass the frame to the scaler (and all its video frame consumers)
        frameScaler.processFrame(this, videoFrame);
        
        // update pts of the next video frame
        nextFrameTime += frameDuration;
    }
    
    /**
     * Get presentation time stamp of the next video frame.
     * 
     * @return presentation time stamp in miliseconds
     */
    public long getNextFrameTime() {
        return nextFrameTime;
    }
    
}

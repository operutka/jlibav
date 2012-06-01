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

import java.util.Arrays;

/**
 * Video frame. It holds image data in (A)RGB format, image size and video frame 
 * presentation timestamp.
 * 
 * @author Ondrej Perutka
 */
public class VideoFrame implements Cloneable {
    
    private int[] data;
    private int width;
    private int height;
    private long presentationTimeStamp;

    /**
     * Create a new video frame and set its image data, size and presentation
     * timestamp.
     * 
     * @param data image data in the (A)RGB format; Each item of this array
     * represents exactly one pixel. The most significant byte may be alpha,
     * the next byte is red channel, the next one is green channel and the
     * last one is blue.
     * @param width width of the image
     * @param height height of the image
     * @param presentationTimeStamp presentation timestamp of the vide frame
     * (in miliseconds from the begining of the video stream)
     */
    public VideoFrame(int[] data, int width, int height, long presentationTimeStamp) {
        this.data = data;
        this.width = width;
        this.height = height;
        this.presentationTimeStamp = presentationTimeStamp;
    }

    /**
     * Get image data in the (A)RGB format. Each item of the returned array
     * represents exactly one pixel. The most significant byte may be alpha,
     * the next byte is red channel, the next one is green channel and the
     * last one is blue.
     * 
     * This method DOES NOT create a copy of the inner image data. If you
     * change the returned array, you also change this video frame.
     * 
     * @return image data in the (A)RGB format
     */
    public int[] getData() {
        return data;
    }

    /**
     * Get image height.
     * 
     * @return image height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get presentation timestamp (in miliseconds from the begining of the 
     * video stream).
     * 
     * @return presentation timestamp
     */
    public long getPresentationTimeStamp() {
        return presentationTimeStamp;
    }

    /**
     * Get image width.
     * 
     * @return image width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Make a clonned instance of this video frame.
     * 
     * @return a clonned instance of this video frame
     */
    @Override
    public VideoFrame clone() {
        int[] dataCopy = Arrays.copyOf(data, data.length);
        return new VideoFrame(dataCopy, width, height, presentationTimeStamp);
    }
    
}

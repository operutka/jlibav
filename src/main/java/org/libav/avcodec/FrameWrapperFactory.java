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

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.libav.LibavException;
import org.libav.avcodec.bridge.*;
import org.libav.bridge.LibraryManager;

/**
 * Factory class for frame wrappers.
 * 
 * @author Ondrej Perutka
 */
public class FrameWrapperFactory {
    
    private static final AVCodecLibrary codecLib;
    private static final FrameWrapperFactory instance;
    
    static {
        codecLib = LibraryManager.getInstance().getAVCodecLibrary();
        instance = new FrameWrapperFactory();
    }
    
    /**
     * Wrap the given pointer.
     * 
     * @param frame pointer to an AVFrame struct
     * @return frame wrapper
     */
    public IFrameWrapper wrap(Pointer<?> frame) {
        switch (codecLib.getMajorVersion()) {
            case 53: return wrap(new AVFrame53(frame));
            case 54: return wrap(new AVFrame54(frame));
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param frame AVFrame struct
     * @return frame wrapper
     */
    public IFrameWrapper wrap(AVFrame53 frame) {
        return new FrameWrapper53(frame);
    }
    
    /**
     * Wrap the given struct.
     * 
     * @param frame AVFrame struct
     * @return frame wrapper
     */
    public IFrameWrapper wrap(AVFrame54 frame) {
        return new FrameWrapper54(frame);
    }
    
    /**
     * Allocate a new frame.
     * 
     * @return frame wrapper
     * @throws LibavException if the frame cannot be allocated
     */
    public IFrameWrapper allocFrame() throws LibavException {
        switch (codecLib.getMajorVersion()) {
            case 53: return FrameWrapper53.allocateFrame();
            case 54: return FrameWrapper54.allocateFrame();
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Allocate a new frame and its data fields to be able to store a picture of
     * the given size and format.
     * 
     * @param pixelFormat a pixel format
     * @param width frame width
     * @param height frame height
     * @return frame wrapper
     * @throws LibavException if the frame cannot be allocated
     */
    public IFrameWrapper allocPicture(int pixelFormat, int width, int height) throws LibavException {
        switch (codecLib.getMajorVersion()) {
            case 53: return FrameWrapper53.allocatePicture(pixelFormat, width, height);
            case 54: return FrameWrapper54.allocatePicture(pixelFormat, width, height);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Get size of the native AVPicture structure.
     * 
     * @return size of the native AVPicture structure
     */
    public long getAVPictureSize() {
        switch (codecLib.getMajorVersion()) {
            case 53: return BridJ.sizeOf(AVPicture53.class);
            case 54: return BridJ.sizeOf(AVPicture54.class);
        }
        
        throw new UnsatisfiedLinkError("unsupported version of the libavcodec");
    }
    
    /**
     * Get instance of this factory.
     * 
     * @return instance of this factory
     */
    public static FrameWrapperFactory getInstance() {
        return instance;
    }
    
}

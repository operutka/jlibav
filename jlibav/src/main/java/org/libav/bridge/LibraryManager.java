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
package org.libav.bridge;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bridj.BridJ;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avresample.bridge.AVResampleLibrary;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.swscale.bridge.SWScaleLibrary;

/**
 * This singleton class loads the dynamic libraries and holds refrences to them.
 * 
 * @author Ondrej Perutka
 */
public class LibraryManager {
    
    private static final String PKEY_LIBPATH = "org.libav.libpath";
    private static final String DEFAULT_LIBPATH = "libav";
    
    private static LibraryManager instance = null;

    private final AVUtilLibrary avUtil;
    private final AVCodecLibrary avCodec;
    private final AVFormatLibrary avFormat;
    private final SWScaleLibrary swScale;
    private AVResampleLibrary avResample;
    
    private LibraryManager() throws IOException {
        BridJ.addLibraryPath(System.getProperty(PKEY_LIBPATH, DEFAULT_LIBPATH));
        
        BridJ.addNativeLibraryAlias(AVCodecLibrary.LIB_NAME, AVCodecLibrary.LIB_NAME);
        for (int i = AVCodecLibrary.MIN_MAJOR_VERSION; i <= AVCodecLibrary.MAX_MAJOR_VERSION; i++)
            BridJ.addNativeLibraryAlias(AVCodecLibrary.LIB_NAME, AVCodecLibrary.LIB_NAME + "-" + i);
        
        BridJ.addNativeLibraryAlias(AVFormatLibrary.LIB_NAME, AVFormatLibrary.LIB_NAME);
        for (int i = AVFormatLibrary.MIN_MAJOR_VERSION; i <= AVFormatLibrary.MAX_MAJOR_VERSION; i++)
            BridJ.addNativeLibraryAlias(AVFormatLibrary.LIB_NAME, AVFormatLibrary.LIB_NAME + "-" + i);
        
        BridJ.addNativeLibraryAlias(AVUtilLibrary.LIB_NAME, AVUtilLibrary.LIB_NAME);
        for (int i = AVUtilLibrary.MIN_MAJOR_VERSION; i <= AVUtilLibrary.MAX_MAJOR_VERSION; i++)
            BridJ.addNativeLibraryAlias(AVUtilLibrary.LIB_NAME, AVUtilLibrary.LIB_NAME + "-" + i);
        
        BridJ.addNativeLibraryAlias(SWScaleLibrary.LIB_NAME, SWScaleLibrary.LIB_NAME);
        for (int i = SWScaleLibrary.MIN_MAJOR_VERSION; i <= SWScaleLibrary.MAX_MAJOR_VERSION; i++)
            BridJ.addNativeLibraryAlias(SWScaleLibrary.LIB_NAME, SWScaleLibrary.LIB_NAME + "-" + i);
        
        BridJ.addNativeLibraryAlias(AVResampleLibrary.LIB_NAME, AVResampleLibrary.LIB_NAME);
        for (int i = AVResampleLibrary.MIN_MAJOR_VERSION; i <= AVResampleLibrary.MAX_MAJOR_VERSION; i++)
            BridJ.addNativeLibraryAlias(AVResampleLibrary.LIB_NAME, AVResampleLibrary.LIB_NAME + "-" + i);
        
        avUtil = new AVUtilLibrary();
        avCodec = new AVCodecLibrary();
        avFormat = new AVFormatLibrary();
        swScale = new SWScaleLibrary();
        
        try {
            avResample = new AVResampleLibrary();
        } catch (IOException ex) {
            avResample = null;
        }
        
        avFormat.av_register_all();
        if (avFormat.functionExists("avformat_network_init"))
            avFormat.avformat_network_init();
        avCodec.avcodec_register_all();
    }
    
    /**
     * Get avutil library wrapper.
     * 
     * @return avutil library wrapper
     */
    public AVUtilLibrary getAVUtilLibrary() {
        return avUtil;
    }
    
    /**
     * Get avcodec library wrapper.
     * 
     * @return avcodec library wrapper
     */
    public AVCodecLibrary getAVCodecLibrary() {
        return avCodec;
    }
    
    /**
     * Get avformat library wrapper.
     * 
     * @return avformat library wrapper
     */
    public AVFormatLibrary getAVFormatLibrary() {
        return avFormat;
    }
    
    /**
     * Get swscale library wrapper.
     * 
     * @return swscale library wrapper
     */
    public SWScaleLibrary getSWScaleLibrary() {
        return swScale;
    }
    
    /**
     * Get avresample library wrapper.
     * 
     * @return avresample library wrapper
     */
    public AVResampleLibrary getAVResampleLibrary() {
        return avResample;
    }
    
    /**
     * Return instance of the LibraryManager.
     * 
     * @return instance of the LibraryManager
     */
    public static LibraryManager getInstance() {
        try {
            if (instance == null)
                instance = new LibraryManager();
        } catch (IOException ex) {
            Logger.getLogger(LibraryManager.class.getName()).log(Level.SEVERE, "unable to load native libraries", ex);
        }
        
        return instance;
    }
    
}

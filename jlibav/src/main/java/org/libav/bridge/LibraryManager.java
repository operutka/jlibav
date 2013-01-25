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
import org.libav.avdevice.bridge.AVDeviceLibrary;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avresample.bridge.AVResampleLibrary;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.swscale.bridge.SWScaleLibrary;

/**
 * This singleton class loads the Libav dynamic libraries and holds refrences 
 * to them.
 * 
 * It searches for the native libraries in default system locations and inside
 * a folder specified by the "org.libav.libpath" system property. Default value
 * of this property is "./libav".
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
    private AVDeviceLibrary avDevice;
    private final SWScaleLibrary swScale;
    private AVResampleLibrary avResample;
    
    private LibraryManager() throws IOException {
        BridJ.addLibraryPath(System.getProperty(PKEY_LIBPATH, DEFAULT_LIBPATH));
        
        addNativeLibraryAliases(AVCodecLibrary.LIB_NAME, AVCodecLibrary.MIN_MAJOR_VERSION, AVCodecLibrary.MAX_MAJOR_VERSION);
        addNativeLibraryAliases(AVFormatLibrary.LIB_NAME, AVFormatLibrary.MIN_MAJOR_VERSION, AVFormatLibrary.MAX_MAJOR_VERSION);
        addNativeLibraryAliases(AVUtilLibrary.LIB_NAME, AVUtilLibrary.MIN_MAJOR_VERSION, AVUtilLibrary.MAX_MAJOR_VERSION);
        addNativeLibraryAliases(AVDeviceLibrary.LIB_NAME, AVDeviceLibrary.MIN_MAJOR_VERSION, AVDeviceLibrary.MAX_MAJOR_VERSION);
        addNativeLibraryAliases(SWScaleLibrary.LIB_NAME, SWScaleLibrary.MIN_MAJOR_VERSION, SWScaleLibrary.MAX_MAJOR_VERSION);
        addNativeLibraryAliases(AVResampleLibrary.LIB_NAME, AVResampleLibrary.MIN_MAJOR_VERSION, AVResampleLibrary.MAX_MAJOR_VERSION);
        
        avUtil = new AVUtilLibrary();
        avCodec = new AVCodecLibrary();
        avFormat = new AVFormatLibrary();
        swScale = new SWScaleLibrary();
        
        try {
            avDevice = new AVDeviceLibrary();
        } catch (IOException ex) {
            avDevice = null;
        }
        
        try {
            avResample = new AVResampleLibrary();
        } catch (IOException ex) {
            avResample = null;
        }
        
        avFormat.av_register_all();
        if (avFormat.functionExists("avformat_network_init"))
            avFormat.avformat_network_init();
        avCodec.avcodec_register_all();
        if (avDevice != null)
            avDevice.avdevice_register_all();
    }
    
    /**
     * Add aliases for the given native library name. (It is required on some
     * platforms.)
     * 
     * @param libName standard library name
     * @param minMajorVersio min major version
     * @param maxMajorVersion max major version
     */
    private void addNativeLibraryAliases(String libName, int minMajorVersio, int maxMajorVersion) {
        BridJ.addNativeLibraryAlias(libName, libName);
        for (int i = minMajorVersio; i <= maxMajorVersion; i++)
            BridJ.addNativeLibraryAlias(libName, libName + "-" + i);
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
     * Get avdevice library wrapper.
     * 
     * @return avdevice library wrapper
     */
    public AVDeviceLibrary getAVDeviceLibrary() {
        return avDevice;
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

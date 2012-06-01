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

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.libav.avcodec.bridge.IAVCodecLibrary;
import org.libav.avcodec.bridge.SynchronizedAVCodecLibrary;
import org.libav.avformat.bridge.IAVFormatLibrary;
import org.libav.avutil.bridge.IAVUtilLibrary;
import org.libav.c.bridge.ICLibrary;
import org.libav.swscale.bridge.ISWScaleLibrary;

/**
 * This singleton class loads the dynamic libraries and holds refrences to them.
 * 
 * @author Ondrej Perutka
 */
public class LibraryManager {
    
    private static LibraryManager instance = null;

    private final LibavLibraryWrapper<IAVUtilLibrary> avUtil;
    private final LibavLibraryWrapper<IAVCodecLibrary> avCodec;
    private final LibavLibraryWrapper<IAVFormatLibrary> avFormat;
    private final LibavLibraryWrapper<ISWScaleLibrary> swScale;
    private final ICLibrary cLib;
    
    private LibraryManager() {
        System.setProperty("jna.library.path", "libav");
        System.setProperty("jna.nosys", "true");
        String avUtilLibPath = getLibPath("avutil");
        String avCodecLibPath = getLibPath("avcodec");
        String avFormatLibPath = getLibPath("avformat");
        String swScaleLibPath = getLibPath("swscale");
        
        IAVUtilLibrary avUtilLib = (IAVUtilLibrary)Native.loadLibrary(avUtilLibPath, IAVUtilLibrary.class);
        IAVCodecLibrary avCodecLib = (IAVCodecLibrary)Native.loadLibrary(avCodecLibPath, IAVCodecLibrary.class);
        avCodecLib = new SynchronizedAVCodecLibrary(avCodecLib);
        IAVFormatLibrary avFormatLib = (IAVFormatLibrary)Native.loadLibrary(avFormatLibPath, IAVFormatLibrary.class);
        ISWScaleLibrary swScaleLib = (ISWScaleLibrary)Native.loadLibrary(swScaleLibPath, ISWScaleLibrary.class);
        
        avUtil = new LibavLibraryWrapper<IAVUtilLibrary>(NativeLibrary.getInstance(avUtilLibPath), avUtilLib, avUtilLib.avutil_version(), 51, 51);
        avCodec = new LibavLibraryWrapper<IAVCodecLibrary>(NativeLibrary.getInstance(avCodecLibPath), avCodecLib, avCodecLib.avcodec_version(), 53, 54);
        avFormat = new LibavLibraryWrapper<IAVFormatLibrary>(NativeLibrary.getInstance(avFormatLibPath), avFormatLib, avFormatLib.avformat_version(), 53, 54);
        swScale = new LibavLibraryWrapper<ISWScaleLibrary>(NativeLibrary.getInstance(swScaleLibPath), swScaleLib, swScaleLib.swscale_version(), 2, 2);
        cLib = (ICLibrary)Native.loadLibrary((Platform.C_LIBRARY_NAME), ICLibrary.class);
        
        avFormatLib.av_register_all();
        if (avFormat.functionExists("avformat_network_init"))
            avFormatLib.avformat_network_init();
        avCodecLib.avcodec_register_all();
    }
    
    private String getLibPath(String libName) {
        File result = findLibInDir(new File("."), libName);
        if (result != null)
            return result.getAbsolutePath();
        
        String jnaLibPath = System.getProperty("jna.library.path");
        if (jnaLibPath != null)
            result = findLibInDir(new File(jnaLibPath), libName);
        if (result != null)
            return result.getAbsolutePath();
        
        return libName;
    }
    
    private File findLibInDir(File dir, String libName) {
        if (!dir.isDirectory())
            return null;
        
        Pattern p = Pattern.compile("(lib)?" + libName + "(-\\d+)?\\.(dll|so)(\\.\\d+)*", Pattern.CASE_INSENSITIVE);
        
        File[] files = dir.listFiles();
        for (File file : files) {
            if (!file.isFile())
                continue;
            Matcher m = p.matcher(file.getName());
            if (m.matches())
                return file;
        }
        
        return null;
    }
    
    /**
     * Get avutil library wrapper.
     * 
     * @return avutil library wrapper
     */
    public LibavLibraryWrapper<IAVUtilLibrary> getAVUtilLibraryWrapper() {
        return avUtil;
    }
    
    /**
     * Get avcodec library wrapper.
     * 
     * @return avcodec library wrapper
     */
    public LibavLibraryWrapper<IAVCodecLibrary> getAVCodecLibraryWrapper() {
        return avCodec;
    }
    
    /**
     * Get avformat library wrapper.
     * 
     * @return avformat library wrapper
     */
    public LibavLibraryWrapper<IAVFormatLibrary> getAVFormatLibraryWrapper() {
        return avFormat;
    }
    
    /**
     * Get swscale library wrapper.
     * 
     * @return swscale library wrapper
     */
    public LibavLibraryWrapper<ISWScaleLibrary> getSWScaleLibraryWrapper() {
        return swScale;
    }
    
    /**
     * Get C library.
     * 
     * @return C library
     */
    public ICLibrary getCLibrary() {
        return cLib;
    }
    
    /**
     * Return instance of the LibraryManager.
     * 
     * @return instance of the LibraryManager
     */
    public static LibraryManager getInstance() {
        if (instance == null)
            instance = new LibraryManager();
        
        return instance;
    }
    
}

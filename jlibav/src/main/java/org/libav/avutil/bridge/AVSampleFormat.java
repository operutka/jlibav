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
package org.libav.avutil.bridge;

import org.libav.bridge.LibraryManager;

/**
 * Mirror of the native AVSampleFormat enum. For details see the Libav 
 * documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVSampleFormat {
    
    public static final AVUtilLibrary utilLib = LibraryManager.getInstance().getAVUtilLibrary();
    
    public static final int AV_SAMPLE_FMT_NONE = -1;
    public static final int AV_SAMPLE_FMT_U8 = 0;
    public static final int AV_SAMPLE_FMT_S16 = 1;
    public static final int AV_SAMPLE_FMT_S32 = 2;
    public static final int AV_SAMPLE_FMT_FLT = 3;
    public static final int AV_SAMPLE_FMT_DBL = 4;
    
    public static final int AV_SAMPLE_FMT_U8P = 5;
    public static final int AV_SAMPLE_FMT_S16P = 6;
    public static final int AV_SAMPLE_FMT_S32P = 7;
    public static final int AV_SAMPLE_FMT_FLTP = 8;
    public static final int AV_SAMPLE_FMT_DBLP = 9;
    
    public static final int AV_SAMPLE_FMT_NB = 10;
    
    public static int getBytesPerSample(int sampleFormat) {
        return utilLib.av_get_bytes_per_sample(sampleFormat);
    }
    
    public static int getBitsPerSample(int sampleFormat) {
        return utilLib.av_get_bytes_per_sample(sampleFormat) * 8;
    }
    
    public static boolean isSigned(int sampleFormat) {
        switch (sampleFormat) {
            case AV_SAMPLE_FMT_DBL:
            case AV_SAMPLE_FMT_FLT:
            case AV_SAMPLE_FMT_S16:
            case AV_SAMPLE_FMT_S32:
            case AV_SAMPLE_FMT_DBLP:
            case AV_SAMPLE_FMT_FLTP:
            case AV_SAMPLE_FMT_S16P:
            case AV_SAMPLE_FMT_S32P: return true;
            default: return false;
        }
    }
    
    public static boolean isUnsigned(int sampleFormat) {
        switch (sampleFormat) {
            case AV_SAMPLE_FMT_U8:
            case AV_SAMPLE_FMT_U8P: return true;
            default: return false;
        }
    }
    
    public static boolean isReal(int sampleFormat) {
        switch (sampleFormat) {
            case AV_SAMPLE_FMT_DBL:
            case AV_SAMPLE_FMT_FLT:
            case AV_SAMPLE_FMT_DBLP:
            case AV_SAMPLE_FMT_FLTP: return true;
            default: return false;
        }
    }
    
    public static boolean isPlanar(int sampleFormat) {
        switch (sampleFormat) {
            case AV_SAMPLE_FMT_DBLP:
            case AV_SAMPLE_FMT_FLTP:
            case AV_SAMPLE_FMT_U8P:
            case AV_SAMPLE_FMT_S16P:
            case AV_SAMPLE_FMT_S32P: return true;
            default: return false;
        }
    }
    
}

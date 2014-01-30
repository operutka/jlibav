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

/**
 * Mirror of the native PixelFormat enum. For details see the Libav 
 * documentation.
 * 
 * @author Ondrej Perutka
 */
public interface PixelFormat {
    
    public static final int PIX_FMT_NONE = -1;
    public static final int PIX_FMT_YUV420P = 0;
    public static final int PIX_FMT_YUYV422 = 1;
    public static final int PIX_FMT_RGB24 = 2;
    public static final int PIX_FMT_BGR24 = 3;
    public static final int PIX_FMT_YUV422P = 4;
    public static final int PIX_FMT_YUV444P = 5;
    public static final int PIX_FMT_YUV410P = 6;
    public static final int PIX_FMT_YUV411P = 7;
    public static final int PIX_FMT_GRAY8 = 8;
    public static final int PIX_FMT_MONOWHITE = 9;
    public static final int PIX_FMT_MONOBLACK = 10;
    public static final int PIX_FMT_PAL8 = 11;
    public static final int PIX_FMT_YUVJ420P = 12;
    public static final int PIX_FMT_YUVJ422P = 13;
    public static final int PIX_FMT_YUVJ444P = 14;
    public static final int PIX_FMT_XVMC_MPEG2_MC = 15;
    public static final int PIX_FMT_XVMC_MPEG2_IDCT = 16;
    public static final int PIX_FMT_UYVY422 = 17;
    public static final int PIX_FMT_UYYVYY411 = 18;
    public static final int PIX_FMT_BGR8 = 19;
    public static final int PIX_FMT_BGR4 = 20;
    public static final int PIX_FMT_BGR4_BYTE = 21;
    public static final int PIX_FMT_RGB8 = 22;
    public static final int PIX_FMT_RGB4 = 23;
    public static final int PIX_FMT_RGB4_BYTE = 24;
    public static final int PIX_FMT_NV12 = 25;
    public static final int PIX_FMT_NV21 = 26;
    
    public static final int PIX_FMT_ARGB = 27;
    public static final int PIX_FMT_RGBA = 28;
    public static final int PIX_FMT_ABGR = 29;
    public static final int PIX_FMT_BGRA = 30;
    
    public static final int PIX_FMT_GRAY16BE = 31;
    public static final int PIX_FMT_GRAY16LE = 32;
    public static final int PIX_FMT_YUV440P = 33;
    public static final int PIX_FMT_YUVJ440P = 34;
    public static final int PIX_FMT_YUVA420P = 35;
    public static final int PIX_FMT_VDPAU_H264 = 36;
    public static final int PIX_FMT_VDPAU_MPEG1 = 37;
    public static final int PIX_FMT_VDPAU_MPEG2 = 38;
    public static final int PIX_FMT_VDPAU_WMV3 = 39;
    public static final int PIX_FMT_VDPAU_VC1 = 40;
    public static final int PIX_FMT_RGB48BE = 41;
    public static final int PIX_FMT_RGB48LE = 42;
    
    public static final int PIX_FMT_RGB565BE = 43;
    public static final int PIX_FMT_RGB565LE = 44;
    public static final int PIX_FMT_RGB555BE = 45;
    public static final int PIX_FMT_RGB555LE = 46;
    
    public static final int PIX_FMT_BGR565BE = 47;
    public static final int PIX_FMT_BGR565LE = 48;
    public static final int PIX_FMT_BGR555BE = 49;
    public static final int PIX_FMT_BGR555LE = 50;
    
    public static final int PIX_FMT_VAAPI_MOCO = 51;
    public static final int PIX_FMT_VAAPI_IDCT = 52;
    public static final int PIX_FMT_VAAPI_VLD = 53;
    
    public static final int PIX_FMT_YUV420P16LE = 54;
    public static final int PIX_FMT_YUV420P16BE = 55;
    public static final int PIX_FMT_YUV422P16LE = 56;
    public static final int PIX_FMT_YUV422P16BE = 57;
    public static final int PIX_FMT_YUV444P16LE = 58;
    public static final int PIX_FMT_YUV444P16BE = 59;
    public static final int PIX_FMT_VDPAU_MPEG4 = 60;
    public static final int PIX_FMT_DXVA2_VLD = 61;
    
    public static final int PIX_FMT_RGB444LE = 62;
    public static final int PIX_FMT_RGB444BE = 63;
    public static final int PIX_FMT_BGR444LE = 64;
    public static final int PIX_FMT_BGR444BE = 65;
    public static final int PIX_FMT_Y400A = 66;
    public static final int PIX_FMT_BGR48BE = 67;
    public static final int PIX_FMT_BGR48LE = 68;
    public static final int PIX_FMT_YUV420P9BE = 69;
    public static final int PIX_FMT_YUV420P9LE = 70;
    public static final int PIX_FMT_YUV420P10BE = 71;
    public static final int PIX_FMT_YUV420P10LE = 72;
    public static final int PIX_FMT_YUV422P10BE = 73;
    public static final int PIX_FMT_YUV422P10LE = 74;
    public static final int PIX_FMT_YUV444P9BE = 75;
    public static final int PIX_FMT_YUV444P9LE = 76;
    public static final int PIX_FMT_YUV444P10BE = 77;
    public static final int PIX_FMT_YUV444P10LE = 78;
    public static final int PIX_FMT_YUV422P9BE = 79;
    public static final int PIX_FMT_YUV422P9LE = 80;
    public static final int PIX_FMT_VDA_VLD = 81;
    public static final int PIX_FMT_GBRP = 82;
    public static final int PIX_FMT_GBRP9BE = 83;
    public static final int PIX_FMT_GBRP9LE = 84;
    public static final int PIX_FMT_GBRP10BE = 85;
    public static final int PIX_FMT_GBRP10LE = 86;
    public static final int PIX_FMT_GBRP16BE = 87;
    public static final int PIX_FMT_GBRP16LE = 88;
    public static final int PIX_FMT_YUVA422P = 89;
    public static final int PIX_FMT_YUVA444P = 90;
    public static final int PIX_FMT_YUVA420P9BE = 91;
    public static final int PIX_FMT_YUVA420P9LE = 92;
    public static final int PIX_FMT_YUVA422P9BE = 93;
    public static final int PIX_FMT_YUVA422P9LE = 94;
    public static final int PIX_FMT_YUVA444P9BE = 95;
    public static final int PIX_FMT_YUVA444P9LE = 96;
    public static final int PIX_FMT_YUVA420P10BE = 97;
    public static final int PIX_FMT_YUVA420P10LE = 98;
    public static final int PIX_FMT_YUVA422P10BE = 99;
    public static final int PIX_FMT_YUVA422P10LE = 100;
    public static final int PIX_FMT_YUVA444P10BE = 101;
    public static final int PIX_FMT_YUVA444P10LE = 102;
    public static final int PIX_FMT_YUVA420P16BE = 103;
    public static final int PIX_FMT_YUVA420P16LE = 104;
    public static final int PIX_FMT_YUVA422P16BE = 105;
    public static final int PIX_FMT_YUVA422P16LE = 106;
    public static final int PIX_FMT_YUVA444P16BE = 107;
    public static final int PIX_FMT_YUVA444P16LE = 108;
    public static final int PIX_FMT_VDPAU = 109;
    public static final int PIX_FMT_XYZ12LE = 110;
    public static final int PIX_FMT_XYZ12BE = 111;
    public static final int PIX_FMT_NV16 = 112;
    public static final int PIX_FMT_NV20LE = 113;
    public static final int PIX_FMT_NV20BE = 114;
    public static final int PIX_FMT_NB = 115;
    
}

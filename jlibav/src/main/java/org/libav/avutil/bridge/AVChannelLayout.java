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

import java.nio.charset.Charset;
import org.bridj.Pointer;
import org.libav.bridge.LibraryManager;

/**
 * Mirror for the native channel layout macros and convenience functions. For 
 * details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVChannelLayout {
    
    private static final AVUtilLibrary utilLib;
    private static final boolean hasGetDefaultChannelLayout;
    
    static {
        utilLib = LibraryManager.getInstance().getAVUtilLibrary();
        hasGetDefaultChannelLayout = utilLib.functionExists("av_get_default_channel_layout");
    }
    
    public static final int AV_CH_FRONT_LEFT = 0x00000001;
    public static final int AV_CH_FRONT_RIGHT = 0x00000002;
    public static final int AV_CH_FRONT_CENTER = 0x00000004;
    public static final int AV_CH_LOW_FREQUENCY = 0x00000008;
    public static final int AV_CH_BACK_LEFT = 0x00000010;
    public static final int AV_CH_BACK_RIGHT = 0x00000020;
    public static final int AV_CH_FRONT_LEFT_OF_CENTER = 0x00000040;
    public static final int AV_CH_FRONT_RIGHT_OF_CENTER = 0x00000080;
    public static final int AV_CH_BACK_CENTER = 0x00000100;
    public static final int AV_CH_SIDE_LEFT = 0x00000200;
    public static final int AV_CH_SIDE_RIGHT = 0x00000400;
    public static final int AV_CH_TOP_CENTER = 0x00000800;
    public static final int AV_CH_TOP_FRONT_LEFT = 0x00001000;
    public static final int AV_CH_TOP_FRONT_CENTER = 0x00002000;
    public static final int AV_CH_TOP_FRONT_RIGHT = 0x00004000;
    public static final int AV_CH_TOP_BACK_LEFT = 0x00008000;
    public static final int AV_CH_TOP_BACK_CENTER = 0x00010000;
    public static final int AV_CH_TOP_BACK_RIGHT = 0x00020000;
    public static final int AV_CH_STEREO_LEFT = 0x20000000;  ///< Stereo downmix.
    public static final int AV_CH_STEREO_RIGHT = 0x40000000;  ///< See AV_CH_STEREO_LEFT.
    public static final long AV_CH_WIDE_LEFT = 0x0000000080000000;
    public static final long AV_CH_WIDE_RIGHT = 0x0000000100000000l;
    public static final long AV_CH_SURROUND_DIRECT_LEFT = 0x0000000200000000l;
    public static final long AV_CH_SURROUND_DIRECT_RIGHT = 0x0000000400000000l;

    public static final long AV_CH_LAYOUT_NATIVE = 0x8000000000000000l;

    public static final int AV_CH_LAYOUT_MONO = (AV_CH_FRONT_CENTER);
    public static final int AV_CH_LAYOUT_STEREO = (AV_CH_FRONT_LEFT|AV_CH_FRONT_RIGHT);
    public static final int AV_CH_LAYOUT_2POINT1 = (AV_CH_LAYOUT_STEREO|AV_CH_LOW_FREQUENCY);
    public static final int AV_CH_LAYOUT_2_1 = (AV_CH_LAYOUT_STEREO|AV_CH_BACK_CENTER);
    public static final int AV_CH_LAYOUT_SURROUND = (AV_CH_LAYOUT_STEREO|AV_CH_FRONT_CENTER);
    public static final int AV_CH_LAYOUT_3POINT1 = (AV_CH_LAYOUT_SURROUND|AV_CH_LOW_FREQUENCY);
    public static final int AV_CH_LAYOUT_4POINT0 = (AV_CH_LAYOUT_SURROUND|AV_CH_BACK_CENTER);
    public static final int AV_CH_LAYOUT_4POINT1 = (AV_CH_LAYOUT_4POINT0|AV_CH_LOW_FREQUENCY);
    public static final int AV_CH_LAYOUT_2_2 = (AV_CH_LAYOUT_STEREO|AV_CH_SIDE_LEFT|AV_CH_SIDE_RIGHT);
    public static final int AV_CH_LAYOUT_QUAD = (AV_CH_LAYOUT_STEREO|AV_CH_BACK_LEFT|AV_CH_BACK_RIGHT);
    public static final int AV_CH_LAYOUT_5POINT0 = (AV_CH_LAYOUT_SURROUND|AV_CH_SIDE_LEFT|AV_CH_SIDE_RIGHT);
    public static final int AV_CH_LAYOUT_5POINT1 = (AV_CH_LAYOUT_5POINT0|AV_CH_LOW_FREQUENCY);
    public static final int AV_CH_LAYOUT_5POINT0_BACK = (AV_CH_LAYOUT_SURROUND|AV_CH_BACK_LEFT|AV_CH_BACK_RIGHT);
    public static final int AV_CH_LAYOUT_5POINT1_BACK = (AV_CH_LAYOUT_5POINT0_BACK|AV_CH_LOW_FREQUENCY);
    public static final int AV_CH_LAYOUT_6POINT0 = (AV_CH_LAYOUT_5POINT0|AV_CH_BACK_CENTER);
    public static final int AV_CH_LAYOUT_6POINT0_FRONT = (AV_CH_LAYOUT_2_2|AV_CH_FRONT_LEFT_OF_CENTER|AV_CH_FRONT_RIGHT_OF_CENTER);
    public static final int AV_CH_LAYOUT_HEXAGONAL = (AV_CH_LAYOUT_5POINT0_BACK|AV_CH_BACK_CENTER);
    public static final int AV_CH_LAYOUT_6POINT1 = (AV_CH_LAYOUT_5POINT1|AV_CH_BACK_CENTER);
    public static final int AV_CH_LAYOUT_6POINT1_BACK = (AV_CH_LAYOUT_5POINT1_BACK|AV_CH_BACK_CENTER);
    public static final int AV_CH_LAYOUT_6POINT1_FRONT = (AV_CH_LAYOUT_6POINT0_FRONT|AV_CH_LOW_FREQUENCY);
    public static final int AV_CH_LAYOUT_7POINT0 = (AV_CH_LAYOUT_5POINT0|AV_CH_BACK_LEFT|AV_CH_BACK_RIGHT);
    public static final int AV_CH_LAYOUT_7POINT0_FRONT = (AV_CH_LAYOUT_5POINT0|AV_CH_FRONT_LEFT_OF_CENTER|AV_CH_FRONT_RIGHT_OF_CENTER);
    public static final int AV_CH_LAYOUT_7POINT1 = (AV_CH_LAYOUT_5POINT1|AV_CH_BACK_LEFT|AV_CH_BACK_RIGHT);
    public static final int AV_CH_LAYOUT_7POINT1_WIDE = (AV_CH_LAYOUT_5POINT1|AV_CH_FRONT_LEFT_OF_CENTER|AV_CH_FRONT_RIGHT_OF_CENTER);
    public static final int AV_CH_LAYOUT_7POINT1_WIDE_BACK = (AV_CH_LAYOUT_5POINT1_BACK|AV_CH_FRONT_LEFT_OF_CENTER|AV_CH_FRONT_RIGHT_OF_CENTER);
    public static final int AV_CH_LAYOUT_OCTAGONAL = (AV_CH_LAYOUT_5POINT0|AV_CH_BACK_LEFT|AV_CH_BACK_CENTER|AV_CH_BACK_RIGHT);
    public static final int AV_CH_LAYOUT_STEREO_DOWNMIX = (AV_CH_STEREO_LEFT|AV_CH_STEREO_RIGHT);
    
    /**
     * Return a channel layout id that matches name, or 0 if no match is found.
     * 
     * @param name layout name
     * @return channel layout or 0 if there is no such layout
     */
    public static long getChannelLayout(String name) {
        Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, Charset.forName("UTF-8"));
        return utilLib.av_get_channel_layout(tmp.as(Byte.class));
    }
    
    /**
     * Return default channel layout for a given number of channels.
     * 
     * @param channelCount number of channels
     * @return channel layout
     */
    public static long getDefaultChannelLayout(int channelCount) {
        if (hasGetDefaultChannelLayout)
            return utilLib.av_get_default_channel_layout(channelCount);
        
        switch (channelCount) {
            case 1: return AV_CH_LAYOUT_MONO;
            case 2: return AV_CH_LAYOUT_STEREO;
            case 3: return AV_CH_LAYOUT_SURROUND;
            case 4: return AV_CH_LAYOUT_QUAD;
            case 5: return AV_CH_LAYOUT_5POINT0;
            case 6: return AV_CH_LAYOUT_5POINT1;
            case 7: return AV_CH_LAYOUT_6POINT1;
            case 8: return AV_CH_LAYOUT_7POINT1;
            default: return 0;
        }
    }
    
    /**
     * Return the number of channels in the channel layout.
     * 
     * @param channelLayout a channel layout
     * @return number of channels
     */
    public static int getChannelCount(long channelLayout) {
        return utilLib.av_get_channel_layout_nb_channels(channelLayout);
    }
    
}

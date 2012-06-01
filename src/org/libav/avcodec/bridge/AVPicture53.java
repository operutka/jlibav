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
package org.libav.avcodec.bridge;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * Mirror of the native AVPicture struct for the libavcodec v 53.x.x. For 
 * details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVPicture53 extends Structure {
    
    public Pointer[] data = new Pointer[4];
    public int[] linesize = new int[4];

    public AVPicture53() {
        super();
        initFieldOrder();
    }
    
    public AVPicture53(Pointer p) {
        super(p);
        initFieldOrder();
    }

    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"data", "linesize"});
    }

    public static class ByReference extends AVPicture53 implements Structure.ByReference { };
    public static class ByValue extends AVPicture53 implements Structure.ByValue { };
}

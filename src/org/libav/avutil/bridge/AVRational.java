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

import com.sun.jna.Structure;

/**
 * Mirror of the native AVRational struct. For details see the Libav 
 * documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVRational extends Structure {
    
    public int num;
    public int den;
    
    public AVRational() {
        super();
        initFieldOrder();
    }
    
    public AVRational(int num, int den) {
        super();
        this.num = num;
        this.den = den;
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new java.lang.String[]{"num", "den"});
    }
    
    public static class ByReference extends AVRational implements Structure.ByReference { };
    public static class ByValue extends AVRational implements Structure.ByValue { };
    
}

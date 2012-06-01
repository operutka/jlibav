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
package org.libav.avformat.bridge;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * Mirror of the native AVIOInterruptCB struct from the libavformat v54.x.x. 
 * For details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVIOInterruptCB extends Structure {
    
    public InterruptCallback callback;
    public Pointer opaque;
    
    public interface InterruptCallback extends Callback {
        int apply(Pointer voidPtr1);
    }
    
    public AVIOInterruptCB() {
        super();
        initFieldOrder();
    }
    
    public AVIOInterruptCB(Pointer p) {
        super(p);
        initFieldOrder();
    }
    
    public AVIOInterruptCB(InterruptCallback callback, Pointer opaque) {
        super();
        this.callback = callback;
        this.opaque = opaque;
        initFieldOrder();
    }
    
    private void initFieldOrder() {
        setFieldOrder(new String[] { 
            "callback", 
            "opaque",
        });
    }
    
    public static class ByReference extends AVIOInterruptCB implements Structure.ByReference { }
    public static class ByValue extends AVIOInterruptCB implements Structure.ByValue { }
    
}

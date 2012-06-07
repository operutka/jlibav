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

import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVIOInterruptCB struct from the libavformat v54.x.x. For
 * details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVIOInterruptCB extends StructObject {

    public AVIOInterruptCB() {
        super();
    }

    public AVIOInterruptCB(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<AVIOInterruptCB.InterruptCallback> callback() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVIOInterruptCB callback(Pointer<AVIOInterruptCB.InterruptCallback> callback) {
        this.io.setPointerField(this, 0, callback);
        return this;
    }

    @Field(1)
    public Pointer<?> opaque() {
        return this.io.getPointerField(this, 1);
    }

    @Field(1)
    public AVIOInterruptCB opaque(Pointer<?> opaque) {
        this.io.setPointerField(this, 1, opaque);
        return this;
    }

    public static abstract class InterruptCallback extends Callback<InterruptCallback> {
        public abstract int apply(Pointer<?> voidPtr1);
    }
    
}

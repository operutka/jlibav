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

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVPicture struct for the libavcodec v54.x.x and 55.x.x. 
 * For details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVPicture54 extends StructObject {

    public AVPicture54() {
        super();
    }

    public AVPicture54(Pointer pointer) {
        super(pointer);
    }

    @Array({8})
    @Field(0)
    public Pointer<Pointer<Byte>> data() {
        return this.io.getPointerField(this, 0);
    }

    @Array({8})
    @Field(1)
    public Pointer<Integer> linesize() {
        return this.io.getPointerField(this, 1);
    }
    
}

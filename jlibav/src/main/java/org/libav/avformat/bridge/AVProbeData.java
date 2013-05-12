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

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;

/**
 * Mirror of the native AVProbeData struct for the libavformat v53.x.x, 
 * v54.x.x and v55.x.x. For details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVProbeData extends StructObject {

    public AVProbeData() {
        super();
    }

    public AVProbeData(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public Pointer<Byte> filename() {
        return this.io.getPointerField(this, 0);
    }

    @Field(0)
    public AVProbeData filename(Pointer<Byte> filename) {
        this.io.setPointerField(this, 0, filename);
        return this;
    }

    @Field(1)
    public Pointer<Byte> buf() {
        return this.io.getPointerField(this, 1);
    }

    @Field(1)
    public AVProbeData buf(Pointer<Byte> buf) {
        this.io.setPointerField(this, 1, buf);
        return this;
    }

    @Field(2)
    public int buf_size() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public AVProbeData buf_size(int buf_size) {
        this.io.setIntField(this, 2, buf_size);
        return this;
    }
    
}

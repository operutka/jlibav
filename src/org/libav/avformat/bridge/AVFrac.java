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
 * Mirror of the native AVFrac struct from the libavformat v 53.x.x and v54.x.x.
 * For details see the Libav documentation.
 *
 * @author Ondrej Perutka
 */
public class AVFrac extends StructObject {

    public AVFrac() {
        super();
    }

    public AVFrac(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public long val() {
        return this.io.getLongField(this, 0);
    }

    @Field(0)
    public AVFrac val(long val) {
        this.io.setLongField(this, 0, val);
        return this;
    }

    @Field(1)
    public long num() {
        return this.io.getLongField(this, 1);
    }

    @Field(1)
    public AVFrac num(long num) {
        this.io.setLongField(this, 1, num);
        return this;
    }

    @Field(2)
    public long den() {
        return this.io.getLongField(this, 2);
    }

    @Field(2)
    public AVFrac den(long den) {
        this.io.setLongField(this, 2, den);
        return this;
    }
    
}

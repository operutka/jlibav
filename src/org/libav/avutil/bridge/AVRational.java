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

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;


/**
 * Mirror of the native AVRational struct. For details see the Libav
 * documentation.
 *
 * @author Ondrej Perutka
 */
public class AVRational extends StructObject {

    public AVRational() {
        super();
    }
    
    public AVRational(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public int num() {
        return this.io.getIntField(this, 0);
    }

    @Field(0)
    public AVRational num(int num) {
        this.io.setIntField(this, 0, num);
        return this;
    }

    @Field(1)
    public int den() {
        return this.io.getIntField(this, 1);
    }

    @Field(1)
    public AVRational den(int den) {
        this.io.setIntField(this, 1, den);
        return this;
    }

}

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
 * Mirror of the native AVFormatContext struct. For details see the Libav 
 * documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVDictionaryEntry extends StructObject {

    public AVDictionaryEntry(Pointer peer) {
        super(peer);
    }

    public AVDictionaryEntry() {
        super();
    }
    
    @Field(0)
    public Pointer<Byte> key() {
        return this.io.getPointerField(this, 0);
    }
    
    @Field(0)
    public AVDictionaryEntry key(Pointer<Byte> key) {
        this.io.setPointerField(this, 0, key);
        return this;
    }
    
    @Field(1)
    public Pointer<Byte> value() {
        return this.io.getPointerField(this, 1);
    }
    
    @Field(1)
    public AVDictionaryEntry value(Pointer<Byte> key) {
        this.io.setPointerField(this, 1, key);
        return this;
    }
    
}

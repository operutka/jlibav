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
import org.libav.avutil.bridge.AVRational;

/**
 * Mirror of the native AVChapter struct for the libavformat v53.x.x, 54.x.x
 * and 55.x.x. For details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public class AVChapter extends StructObject {

    public AVChapter(Pointer pointer) {
        super(pointer);
    }

    public AVChapter() {
    }
    
    @Field(0)
    public int id() {
        return this.io.getIntField(this, 0);
    }
    
    @Field(0)
    public AVChapter id(int id) {
        this.io.setIntField(this, 0, id);
        return this;
    }
    
    @Field(1)
    public AVRational time_base() {
        return this.io.getNativeObjectField(this, 1);
    }
    
    @Field(1)
    public AVChapter time_base(AVRational time_base) {
        this.io.setNativeObjectField(this, 1, time_base);
        return this;
    }
    
    @Field(2)
    public long start() {
        return this.io.getLongField(this, 2);
    }
    
    @Field(2)
    public AVChapter start(long start) {
        this.io.setLongField(this, 2, start);
        return this;
    }
    
    @Field(3)
    public long end() {
        return this.io.getLongField(this, 3);
    }
    
    @Field(3)
    public AVChapter end(long end) {
        this.io.setLongField(this, 3, end);
        return this;
    }
    
    @Field(4)
    public Pointer<?> metadata() {
        return this.io.getPointerField(this, 4);
    }
    
    @Field(4)
    public AVChapter metadata(Pointer<?> metadata) {
        this.io.setPointerField(this, 4, metadata);
        return this;
    }
    
}

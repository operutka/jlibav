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

/**
 * Mirror of the native AVPictureType enum. For details see the Libav 
 * documentation.
 * 
 * @author Ondrej Perutka
 */
public interface AVPictureType {
    
    public static final int AV_PICTURE_TYPE_I = 1;
    public static final int AV_PICTURE_TYPE_P = 2;
    public static final int AV_PICTURE_TYPE_B = 3;
    public static final int AV_PICTURE_TYPE_S = 4;
    public static final int AV_PICTURE_TYPE_SI = 5;
    public static final int AV_PICTURE_TYPE_SP = 6;
    public static final int AV_PICTURE_TYPE_BI = 7;
    
}

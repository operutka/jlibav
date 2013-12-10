/*
 * Copyright (C) 2013 Ondrej Perutka
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

/**
 * Mirror of the native AVLockOp enum for the libavcodec v53.x.x, v54.x.x and
 * v55.x.x. For details see the Libav documentation.
 * 
 * @author Ondrej Perutka
 */
public interface AVLockOp {

    public static final int AV_LOCK_CREATE = 0;
    public static final int AV_LOCK_OBTAIN = 1;
    public static final int AV_LOCK_RELEASE = 2;
    public static final int AV_LOCK_DESTROY = 3;
    
}

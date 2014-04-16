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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import org.bridj.Pointer;

/**
 * Lock Manager for the libavcodec library.
 * 
 * @author Ondrej Perutka
 */
public class LockManager extends AVCodecLibrary.RegisterLockMgrCallback {

    private final MutexMap mutexMap;

    public LockManager() {
        this.mutexMap = new MutexMap();
    }
    
    @Override
    public int apply(Pointer<Pointer<?>> mutex, int op) {
        switch (op) {
            case AVLockOp.AV_LOCK_CREATE: return mutexMap.create(mutex);
            case AVLockOp.AV_LOCK_DESTROY: return mutexMap.destroy(mutex);
            case AVLockOp.AV_LOCK_OBTAIN: return mutexMap.obtain(mutex);
            case AVLockOp.AV_LOCK_RELEASE: return mutexMap.release(mutex);
            default: return -1;
        }
    }
    
    private static class MutexMap {
        private final Map<Pointer<?>, Semaphore> mutexMap;

        public MutexMap() {
            mutexMap = Collections.synchronizedMap(new HashMap<Pointer<?>, Semaphore>());
        }

        private int create(Pointer<Pointer<?>> mutex) {
            // it doesn't matter what's inside
            Pointer<?> mid = Pointer.allocateInt();
            mutexMap.put(mid, new Semaphore(1));
            mutex.set(mid);

            return 0;
        }

        private int destroy(Pointer<Pointer<?>> mutex) {
            Pointer<?> mid = mutex.get();
            if (mid == null)
                return -1;

            mutexMap.remove(mid);

            return 0;
        }

        private Semaphore getMutex(Pointer<Pointer<?>> mutex) {
            Pointer<?> mid = mutex.get();
            if (mid == null)
                return null;

            return mutexMap.get(mid);
        }

        private int obtain(Pointer<Pointer<?>> mutex) {
            Semaphore s = getMutex(mutex);
            if (s == null)
                return -1;

            try {
                s.acquire();
            } catch (InterruptedException ex) {
                return -2;
            }

            return 0;
        }

        private int release(Pointer<Pointer<?>> mutex) {
            Semaphore s = getMutex(mutex);
            if (s == null)
                return -1;

            s.release();

            return 0;
        }
    }
    
}

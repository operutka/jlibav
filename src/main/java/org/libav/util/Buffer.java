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
package org.libav.util;

/**
 * Generic synchronized buffer of fixed size.
 * 
 * @author Ondrej Perutka
 */
public class Buffer<T> {
    
    protected Object[] buffer;
    protected int front;
    protected int end;
    protected int count;
    
    /**
     * Create a new buffer with the given capacity.
     * 
     * @param bufferCapacity a buffer capacity
     */
    public Buffer(int bufferCapacity) {
        this.buffer = new Object[bufferCapacity];
        this.front = 0;
        this.end = 0;
        this.count = 0;
    }
    
    /**
     * Get number of objects inside this buffer.
     * 
     * @return number of objects inside this buffer
     */
    public int getItemCount() {
        return count;
    }
    
    /**
     * Get buffer capacity.
     * 
     * @return buffer capacity
     */
    public int getCapacity() {
        return buffer.length;
    }
    
    /**
     * Drop all elements stored in the buffer.
     */
    public synchronized void clear() {
        front = 0;
        end = 0;
        count = 0;
        notifyAll();
    }
    
    /**
     * Put a new element into this buffer. Remove the least recently inserted 
     * element if the buffer is full.
     * 
     * @param elem an element
     */
    public synchronized void shiftPut(T elem) {
        if (count == buffer.length)
            front = (front + 1) % buffer.length;
        else
            count++;
        buffer[end] = elem;
        end = (end + 1) % buffer.length;
        notifyAll();
    }
    
    /**
     * Put a new element into this buffer. Do nothing if the buffer is full.
     * 
     * @param elem an element
     */
    public synchronized void dropPut(T elem) {
        if (count == buffer.length)
            return;
        
        count++;
        buffer[end] = elem;
        end = (end + 1) % buffer.length;
        notifyAll();
    }
    
    /**
     * Put a new element into this buffer. Wait until there is a free space if 
     * the buffer is full.
     * 
     * @param elem an element
     */
    public synchronized void waitPut(T elem) throws InterruptedException {
        while (count == buffer.length)
            wait();
        
        count++;
        buffer[end] = elem;
        end = (end + 1) % buffer.length;
        notifyAll();
    }
    
    /**
     * Put a new element into this buffer. Doubles the buffer capacity if there
     * is no space to store the given element.
     * 
     * @param elem an element
     */
    public synchronized void put(T elem) {
        if (count == buffer.length) {
            Object[] newBuffer = new Object[buffer.length * 2];
            for (int i = 0; i < buffer.length; i++)
                newBuffer[i] = buffer[(front + i) % buffer.length];
            
            front = 0;
            end = buffer.length;
            buffer = newBuffer;
        }
        
        count++;
        buffer[end] = elem;
        end = (end + 1) % buffer.length;
        notifyAll();
    }
    
    /**
     * Get the least recently inserted element from this buffer.
     * 
     * @return the least recently inserted element or null if the buffer is 
     * empty
     */
    public synchronized T get() {
        if (count == 0)
            return null;
        
        count--;
        Object result = buffer[front];
        front = (front + 1) % buffer.length;
        notifyAll();
        return (T)result;
    }
    
    /**
     * Get the least recently inserted element from this buffer. This method
     * waits until there is an element available if the buffer is empty.
     * 
     * @return the least recently inserted element or null if the buffer is 
     * empty
     */
    public synchronized T waitGet() throws InterruptedException {
        while (count == 0)
            wait();
        
        count--;
        Object result = buffer[front];
        front = (front + 1) % buffer.length;
        notifyAll();
        return (T)result;
    }
    
    /**
     * Get the least recently inserted element but do not remove it.
     * 
     * @return the least recently inserted element or null if the buffer is 
     * empty
     */
    public synchronized T front() {
        if (count == 0)
            return null;
        
        return (T)buffer[front];
    }
    
}

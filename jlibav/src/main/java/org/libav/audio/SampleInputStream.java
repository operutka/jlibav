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
package org.libav.audio;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.libav.LibavException;

/**
 * Sample input stream. It is an adapter between an audio frame consumer and
 * audio input stream.
 * 
 * @author Ondrej Perutka
 */
public class SampleInputStream extends InputStream implements IAudioFrameConsumer {

    private final byte[] buffer;
    private int front;
    private int end;
    private int size;
    private boolean blockingFrameProcessing;
    private Semaphore emptySpace;
    
    private boolean eof;
    
    /**
     * Create a new sample input stream and set its buffer size. The buffer size
     * must be greather than the biggest frame size of all frames passed to
     * this object via the processFrame method.
     * 
     * @param bufferSize a buffer size
     */
    public SampleInputStream(int bufferSize) {
        this(bufferSize, true);
    }
    
    /**
     * Create a new sample input stream, set its buffer size and the blocking 
     * frame processing flag. The buffer size must be greather than the biggest 
     * frame size of all frames passed to this object via the processFrame 
     * method.
     * 
     * If the blocking frame processing is true, the processFrame method waits
     * until there is a space to store the whole frame inside the buffer. If the
     * blocking frame processing is false, the processFrame method may drop
     * data from the begining of the buffer to get enough space to store the
     * audio frame.
     * 
     * @param bufferSize a buffer size
     * @param blockingFrameProcessing a blocking frame processing flag
     */
    public SampleInputStream(int bufferSize, boolean blockingFrameProcessing) {
        this.buffer = new byte[bufferSize];
        this.front = 0;
        this.end = 0;
        this.size = 0;
        this.blockingFrameProcessing = blockingFrameProcessing;
        this.emptySpace = new Semaphore(bufferSize, true);
        
        this.eof = false;
    }

    /**
     * Get blocking frame processing flag.
     * 
     * If the blocking frame processing is true, the processFrame method waits
     * until there is a space to store the whole frame inside the buffer. If the
     * blocking frame processing is false, the processFrame method may drop
     * data from the begining of the buffer to get enough space to store the
     * audio frame.
     * 
     * @return blocking frame processing flag
     */
    public boolean isFrameProcessingBlocking() {
        return blockingFrameProcessing;
    }

    /**
     * Set blocking frame processing flag.
     * 
     * If the blocking frame processing is true, the processFrame method waits
     * until there is a space to store the whole frame inside the buffer. If the
     * blocking frame processing is false, the processFrame method may drop
     * data from the begining of the buffer to get enough space to store the
     * audio frame.
     * 
     * @param blockingFrameProcessing a blocking frame processing flag
     */
    public void setBlockingFrameProcessing(boolean blockingFrameProcessing) {
        this.blockingFrameProcessing = blockingFrameProcessing;
    }
    
    private int dropBufferData(int len) {
        synchronized (buffer) {
            if (len <= 0)
                return 0;
            len = len < size ? len : size;
            front = (front + len) % buffer.length;
            size -= len;
            if (blockingFrameProcessing)
                emptySpace.release(len);
            return len;
        }
    }
    
    /**
     * Drop all data stored inside the buffer.
     */
    public void flushBuffer() {
        synchronized (buffer) {
            dropBufferData(size);
        }
    }

    @Override
    public void processFrame(Object producer, AudioFrame frame) throws LibavException {
        int tmp, len = frame.getFrameSize();
        byte[] data = frame.getSamples();
        
        if (len > buffer.length)
            throw new LibavException("sample stream buffer is smaller than frame");
        if (blockingFrameProcessing) {
            try {
                emptySpace.acquire(len);
            } catch (InterruptedException ex) {
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "interrupted while waiting for empty space");
                return;
            }
        }
        
        synchronized (buffer) {
            if (len > (buffer.length - size) && !blockingFrameProcessing)
                dropBufferData(len - buffer.length + size);
            
            tmp = buffer.length - end;
            if (tmp < len) {
                System.arraycopy(data, 0, buffer, end, tmp);
                System.arraycopy(data, tmp, buffer, 0, len - tmp);
            } else
                System.arraycopy(data, 0, buffer, end, len);
            
            end = (end + len) % buffer.length;
            size += len;
            
            buffer.notifyAll();
        }
    }
    
    @Override
    public int available() throws IOException {
        return size;
    }

    @Override
    public void close() throws IOException {
        synchronized (buffer) {
            eof = true;
            flushBuffer();
            buffer.notifyAll();
        }
    }

    @Override
    public synchronized void mark(int i) {
    }

    @Override
    public synchronized void reset() throws IOException {
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        synchronized (buffer) {
            while (true) {
                if (eof && size < 1)
                    return -1;
                else if (size < 1) {
                    try {
                        buffer.wait();
                    } catch (InterruptedException ex) {
                        throw new IOException("interrupted while waiting for data", ex);
                    }
                } else {
                    byte result = buffer[front];
                    front = (front + 1) % buffer.length;
                    size--;
                    if (blockingFrameProcessing)
                        emptySpace.release();
                    return result;
                }
            }
        }
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return read(bytes, 0, bytes.length);
    }

    @Override
    public int read(byte[] bytes, int off, int len) throws IOException {
        if (bytes == null)
            throw new NullPointerException();
        if (off < 0 || len < 0 || (off + len) > bytes.length)
            throw new IndexOutOfBoundsException();
        if (len == 0)
            return 0;
        
        synchronized (buffer) {
            int rest = len;
            while (rest > 0) {
                if (eof && size < 1 && rest == len)
                    return -1;
                else if (eof && size < 1)
                    return len - rest;
                else if (size < 1) {
                    try {
                        buffer.wait();
                    } catch (InterruptedException ex) {
                        throw new IOException("interrupted while waiting for data", ex);
                    }
                } else {
                    int readLen = rest < size ? rest : size;
                    for (int i = 0; i < readLen; i++)
                        bytes[off + i] = buffer[(front + i) % buffer.length];
                    off += readLen;
                    rest -= readLen;
                    front = (front + readLen) % buffer.length;
                    size -= readLen;
                    if (blockingFrameProcessing)
                        emptySpace.release(readLen);
                }
            }
            
            return len;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        return dropBufferData((int)l);
    }
    
}

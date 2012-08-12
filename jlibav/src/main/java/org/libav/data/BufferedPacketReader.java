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
package org.libav.data;

import java.util.concurrent.Semaphore;
import org.libav.avcodec.IPacketWrapper;
import org.libav.avcodec.PacketWrapperFactory;
import org.libav.avformat.IFormatContextWrapper;
import org.libav.util.Buffer;

/**
 * Buffered packet reader.
 * 
 * @author Ondrej Perutka
 */
public class BufferedPacketReader {
    
    private IFormatContextWrapper formatContext;
    private IPacketWrapper packet;
    
    private final Buffer<IPacketWrapper> buffer;
    private boolean eof;
    
    private ReaderThread readerThread;
    private Thread t;
    private Semaphore threadControlMutex;

    /**
     * Create a new pcket reader.
     * 
     * @param formatContext a format context
     * @param bufferSize size of the buffer
     */
    public BufferedPacketReader(IFormatContextWrapper formatContext, int bufferSize) {
        this.formatContext = formatContext;
        packet = PacketWrapperFactory.getInstance().alloc();
        
        buffer = new Buffer<IPacketWrapper>(bufferSize);
        eof = false;
        
        readerThread = null;
        t = null;
        threadControlMutex = new Semaphore(1);
    }
    
    private void start() {
        if (t != null)
            return;
        
        readerThread = new ReaderThread();
        t = new Thread(readerThread);
        t.setDaemon(true);
        t.start();
    }
    
    private void stop() throws InterruptedException {
        if (t == null)
            return;
        
        readerThread.stop();
        t.interrupt();
        t.join();
        
        t = null;
    }
    
    /**
     * Drop buffered data.
     */
    public void dropBuffer() {
        try {
            threadControlMutex.acquire();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        
        try {
            stop();
        } catch (InterruptedException ex) {
            threadControlMutex.release();
            throw new RuntimeException(ex);
        }
        
        IPacketWrapper pw;
        synchronized (buffer) {
            while (buffer.getItemCount() > 0) {
                pw = buffer.get();
                if (pw != null)
                    pw.free();
            }
        }
        
        threadControlMutex.release();
    }
    
    /**
     * Reset the EOF flag. (It is usefull when seeking.)
     */
    public void resetEof() {
        eof = false;
    }
    
    /**
     * Release all associated resources.
     */
    public void close() {
        dropBuffer();
        
        try {
            threadControlMutex.acquire();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        
        if (packet != null)
            packet.free();
        
        packet = null;
        
        threadControlMutex.release();
    }
    
    /**
     * Check whether the packet reader is closed or not.
     * 
     * @return true if it is close, false otherwise
     */
    public boolean isClosed() {
        return packet == null;
    }
    
    /**
     * Get next packet.
     * 
     * @return packet wrapper or null in case of EOF
     */
    public IPacketWrapper nextPacket() {
        IPacketWrapper pw;
        try {
            threadControlMutex.acquire();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        
        synchronized (buffer) {
            if ((eof && buffer.getItemCount() == 0) || isClosed()) {
                threadControlMutex.release();
                return null;
            }
            if (t == null)
                start();
            
            try {
                pw = buffer.waitGet();
            } catch (InterruptedException ex) {
                threadControlMutex.release();
                throw new RuntimeException(ex);
            }
        }
        
        threadControlMutex.release();
        return pw;
    }
    
    private boolean putPacket(IPacketWrapper pw) {
        if (pw == null)
            eof = true;
        
        try {
            buffer.waitPut(pw);
        } catch (InterruptedException ex) {
            return false;
        }

        return true;
    }
    
    private class ReaderThread implements Runnable {
        private boolean stop;

        public ReaderThread() {
            stop = false;
        }
        
        public void stop() {
            stop = true;
        }
        
        @Override
        public void run() {
            IPacketWrapper pw;
            boolean put;
            
            while (!stop) {
                if (formatContext.readNextPacket(packet))
                    pw = packet.clone();
                else
                    pw = null;
                packet.free();
                
                do {
                    //System.out.printf("got next packet: pts = %d, dts = %d, stream_index = %d, pos = %d\n", pw.getPts(), pw.getDts(), pw.getStreamIndex(), pw.getPosition());
                    put = putPacket(pw);
                } while (!put && !stop);
                
                if (pw == null)
                    stop = true;
                else if (!put)
                    pw.free();
            }
        }
    }
    
}

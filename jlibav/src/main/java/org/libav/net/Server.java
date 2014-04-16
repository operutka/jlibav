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
package org.libav.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Universal ServerSocket listener.
 * 
 * @author Ondrej Perutka
 */
public class Server {
    
    public static final int DEFAULT_THREAD_POOL_SIZE = 50;
    
    private final IConnectionHandlerFactory chFactory;
    private ConnectionListener cl;
    
    /**
     * Create a new Server.
     * 
     * @param connectionHandlerFactory connection handler factory
     */
    public Server(IConnectionHandlerFactory connectionHandlerFactory) {
        this.chFactory = connectionHandlerFactory;
        this.cl = null;
    }
    
    /**
     * Start listening on the given server socket and use the default thread
     * pool size (number of threads to server client requests).
     * 
     * @param ss a server socket
     */
    public synchronized void startListening(ServerSocket ss) {
        startListening(ss, DEFAULT_THREAD_POOL_SIZE);
    }
    
    /**
     * Start listening on the given server socket and use the given thread
     * pool size (number of threads to server client requests).
     * 
     * @param ss a server socket
     * @param threadPoolSize a number of threads to server client requests
     */
    public synchronized void startListening(ServerSocket ss, int threadPoolSize) {
        if (cl != null)
            throw new IllegalStateException("already listening");
        
        cl = new ConnectionListener(ss, threadPoolSize);
        Thread t = new Thread(cl);
        t.start();
    }
    
    /**
     * Stop listening and close the underlaying server socket.
     * 
     * @throws IOException if an I/O error occurs when closing underlaying
     * server socket
     */
    public synchronized void stopListening() throws IOException {
        if (cl == null)
            return;
        
        cl.stop();
        cl = null;
    }
    
    /**
     * Stop listening, close the underlaying server socket and interrupt all
     * client request handlers.
     * 
     * @throws IOException if an I/O error occurs when closing underlaying
     * server socket
     */
    public synchronized void shutdown() throws IOException {
        if (cl == null)
            return;
        
        cl.shutdown();
        cl = null;
    }
    
    private class ConnectionListener implements Runnable {
        private final ServerSocket ss;
        private final ExecutorService threadPool;

        public ConnectionListener(ServerSocket ss, int threadPoolSize) {
            this.ss = ss;
            this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
        }
        
        @Override
        public void run() {
            try {
                while (true)
                    threadPool.execute(chFactory.createConnectionHandler(ss.accept()));
            } catch (SocketException ex) {
            } catch (IOException ex) {
                throw new RuntimeException("accept failed", ex);
            }
        }
        
        public void stop() throws IOException {
            ss.close();
            threadPool.shutdown();
        }
        
        public void shutdown() throws IOException {
            ss.close();
            threadPool.shutdownNow();
        }
    }
    
}

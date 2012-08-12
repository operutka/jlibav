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
package org.libav.net.rtsp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;
import org.libav.net.sdp.SessionDescription;

/**
 * Media stream interface.
 * 
 * @author Ondrej Perutka
 */
public interface IMediaStream {
    
    /**
     * Get stream ID.
     * 
     * @return stream ID
     */
    long getId();
    
    /**
     * Set stream ID.
     * 
     * @param id stream ID
     */
    void setId(long id);
    
    /**
     * Start media playback for the given session.
     * 
     * @param sessionId a session ID
     * @return true on success, false if the session was not set up for this
     * media stream
     * @throws IOException if an IO error occurs
     */
    boolean play(String sessionId) throws IOException;
    
    /**
     * Pause media playback for the given session.
     * 
     * @param sessionId a session ID
     * @return true on success, false if the session was not set up for this
     * media stream
     * @throws IOException if an IO error occurs
     */
    boolean pause(String sessionId) throws IOException;
    
    /**
     * Release all resources associated with the given session ID.
     * 
     * @param sessionId a session ID
     */
    void teardown(String sessionId);
    
    /**
     * Release all resources associated with this media stream.
     */
    void free();
    
    /**
     * Check whether this stream is standalone or a part of an aggregate media
     * stream.
     * 
     * @return true if this stream is standalone, false if it is a part of an
     * aggregate media stream
     */
    boolean isStandalone();
    
    /**
     * Get session description (SDP) for this media stream.
     * 
     * @param url an URL where this stream is located
     * @param charset an encoding for the session description
     * @return a session description
     */
    SessionDescription getSessionDescription(String url, Charset charset);
    
    /**
     * Description for unicast connection.
     */
    public static class UnicastConnectionInfo {
        private InetAddress clientAddress;
        private int clientRtpPort;
        private int clientRtcpPort;
        private int serverRtpPort;
        private int serverRtcpPort;

        /**
         * Create a new unicast connection info, set its client address, client
         * RTP port, client RTCP port and find free RTP-RTCP pair for the 
         * server.
         * 
         * @param clientAddress a client address
         * @param clientRtpPort a client RTP port
         * @param clientRtcpPort a client RTCP port
         * @throws IOException if all of the three attempts to find the server 
         * port pair failed
         */
        public UnicastConnectionInfo(InetAddress clientAddress, int clientRtpPort, int clientRtcpPort) throws IOException {
            this.clientAddress = clientAddress;
            this.clientRtpPort = clientRtpPort;
            this.clientRtcpPort = clientRtcpPort;
            
            boolean found = false;
            for (int i = 0; i < 3 && !found; i++)
                found = findFreeServerPortPair();
            
            if (!found)
                throw new IOException("unable to find a free server port pair");
        }
    
        private boolean findFreeServerPortPair() {
            DatagramSocket ds1 = null;
            DatagramSocket ds2 = null;

            try {
                ds1 = new DatagramSocket();
                ds2 = new DatagramSocket(ds1.getLocalPort() + 1);
                if ((ds2.getLocalPort() % 2) == 0) {
                    ds1.close();
                    ds1 = ds2;
                    ds2 = new DatagramSocket(ds1.getLocalPort() + 1);
                }
                serverRtpPort = ds1.getLocalPort();
                serverRtcpPort = ds2.getLocalPort();
                return true;
            } catch (IOException ex) {
            } finally {
                if (ds1 != null)
                    ds1.close();
                if (ds2 != null)
                    ds2.close();
            }

            return false;
        }

        /**
         * Get client address.
         * 
         * @return client address
         */
        public InetAddress getClientAddress() {
            return clientAddress;
        }

        /**
         * Get client RTCP port.
         * 
         * @return client RTCP port
         */
        public int getClientRtcpPort() {
            return clientRtcpPort;
        }

        /**
         * Get client RTP port.
         * 
         * @return client RTP port
         */
        public int getClientRtpPort() {
            return clientRtpPort;
        }

        /**
         * Get server RTCP port.
         * 
         * @return server RTCP port
         */
        public int getServerRtcpPort() {
            return serverRtcpPort;
        }

        /**
         * Get server RTP port.
         * 
         * @return server RTP port
         */
        public int getServerRtpPort() {
            return serverRtpPort;
        }
    }
    
}

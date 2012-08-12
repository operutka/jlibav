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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * RTSP session. It holds resources for a remote RTSP client.
 * 
 * @author Ondrej Perutka
 */
public class RtspSession {
    
    public static final long DEFAULT_TIMEOUT = 60000;
    
    private String id;
    private long timeout;
    private long lastAccessTime;
    
    private Set<IMediaStream> resources;

    /**
     * Create a new RTSP session.
     */
    public RtspSession() {
        id = UUID.randomUUID().toString();
        timeout = DEFAULT_TIMEOUT;
        lastAccessTime = System.currentTimeMillis();
        
        resources = new HashSet<IMediaStream>();
    }
    
    /**
     * Add a new media resource to the session.
     * 
     * @param stream a media resource
     */
    public synchronized void addResource(IMediaStream stream) {
        resources.add(stream);
    }
    
    /**
     * Remove a media resource from this session.
     * 
     * NOTE:
     * This method is not responsible for calling teardown() on the given media
     * stream.
     * 
     * @param stream a media resource to be removed
     */
    public synchronized void removeResource(IMediaStream stream) {
        resources.remove(stream);
    }

    /**
     * Get session ID.
     * 
     * @return session ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Get session timeout in miliseconds.
     * 
     * @return session timeout
     */
    public long getTimeout() {
        return timeout;
    }
    
    /**
     * Set session timeout in miliseconds.
     * 
     * @param timeout session timeout
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * Get last access time. It is a timestamp returned by the
     * System.currentTimeMillis() method.
     * 
     * @return last access time
     */
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    /**
     * Set last access time. It must be a timestamp returned by the
     * System.currentTimeMillis() method.
     * 
     * @param lastAccessTime last access time
     */
    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }
    
    /**
     * Check whether this session is still valid.
     * 
     * @return true if the session is valid, false if the session has expired
     */
    public boolean isValid() {
        return (lastAccessTime + timeout) >= System.currentTimeMillis();
    }
    
    /**
     * Teardown and remove all session resources.
     */
    public synchronized void free() {
        for (IMediaStream res : resources)
            res.teardown(id);
        resources.clear();
    }
    
}

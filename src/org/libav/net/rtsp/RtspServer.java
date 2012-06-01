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

import java.net.Socket;
import java.util.*;
import org.libav.net.IConnectionHandlerFactory;

/**
 * Implementation of the IConnectionHandlerFactory which allows to serve RTSP 
 * clients.
 * 
 * @author Ondrej Perutka
 */
public class RtspServer implements IConnectionHandlerFactory {

    private static final int SESSION_CHECKER_PERIOD = 60000;
    
    private final Map<String, IMediaStream> mediaStreams;
    private final Map<IMediaStream, Set<String>> mediaStreamAliases;
    private final Map<String, RtspSession> sessions;
    private AggregateChangeListener aggregateChangeListener;
    
    private Timer sessionChecker;

    /**
     * Create a new RTSP server.
     */
    public RtspServer() {
        mediaStreams = new HashMap<String, IMediaStream>();
        mediaStreamAliases = new HashMap<IMediaStream, Set<String>>();
        sessions = new HashMap<String, RtspSession>();
        aggregateChangeListener = new AggregateChangeListener();
        
        sessionChecker = new Timer("RTSP session checker", true);
        sessionChecker.schedule(new SessionChecker(), SESSION_CHECKER_PERIOD, SESSION_CHECKER_PERIOD);
    }
    
    private void addAlias(IMediaStream mediaStream, String alias) {
        if (!mediaStreamAliases.containsKey(mediaStream))
            mediaStreamAliases.put(mediaStream, new HashSet<String>());
        Set<String> aliases = mediaStreamAliases.get(mediaStream);
        aliases.add(alias);
    }
    
    private void removeAlias(IMediaStream mediaStream, String alias) {
        Set<String> aliases = mediaStreamAliases.get(mediaStream);
        if (aliases != null)
            aliases.remove(alias);
        if (aliases.isEmpty())
            mediaStreamAliases.remove(mediaStream);
    }
    
    /**
     * Add a media stream which will be published within this RTSP server.
     * 
     * @param path a path where the stream will be available
     * @param mediaStream a media stream to be published
     * @throws IllegalArgumentException if there is already a stream at the
     * given path or the given path ends with "/trackId=[0-9]+"
     */
    public void addMediaStream(String path, ISingleMediaStream mediaStream) {
        synchronized (mediaStreams) {
            if (path.matches(".*/trackId=[0-9]+"))
                throw new IllegalArgumentException("illegal path format (uses reserved construction \"trackId=#\")");
            if (mediaStreams.containsKey(path))
                throw new IllegalArgumentException("there is already a media stream for the given url");

            mediaStreams.put(path, mediaStream);
            addAlias(mediaStream, path);
        }
    }
    
    /**
     * Add an aggregate media stream which will be published within this RTSP 
     * server.
     * 
     * @param path a path where the aggregate stream will be available
     * @param mediaStream an aggregate media stream to be published
     * @throws IllegalArgumentException if there is already a stream at the
     * given path or the given path ends with "/trackId=[0-9]+"
     */
    public void addMediaStream(String path, IAggregateMediaStream mediaStream) {
        synchronized (mediaStreams) {
            if (path.matches(".*/trackId=[0-9]+"))
                throw new IllegalArgumentException("illegal path format (uses reserved construction \"trackId=#\")");
            if (mediaStreams.containsKey(path))
                throw new IllegalArgumentException("there is already a media stream for the given url");

            mediaStreams.put(path, mediaStream);
            addAlias(mediaStream, path);
            ISingleMediaStream sms;
            for (int i = 0; i < mediaStream.count(); i++) {
                sms = mediaStream.get(i);
                mediaStreams.put(path + "/trackId=" + sms.getId(), sms);
            }
            mediaStream.addStreamChangeListener(aggregateChangeListener);
        }
    }
    
    /**
     * Remove media stream at the given path and release all its resources.
     * 
     * @param path a path where to remove a media stream
     * @throws IllegalArgumentException if the media stream at the given path
     * is a part of an aggregate media stream
     */
    public void removeMediaStream(String path) {
        synchronized (mediaStreams) {
            IMediaStream ms = mediaStreams.get(path);
            if (ms == null)
                return;
            if (!ms.isStandalone())
                throw new IllegalArgumentException("the stream at the given url cannot be removed because it is a part of an aggregate stream");

            removeAlias(ms, path);
            if (!mediaStreamAliases.containsKey(ms))
                ms.free();
            if (ms instanceof IAggregateMediaStream) {
                IAggregateMediaStream ams = (IAggregateMediaStream)ms;
                ams.removeStreamChangeListener(aggregateChangeListener);
                for (int i = 0; i < ams.count(); i++)
                    mediaStreams.remove(path + "/trackId=" + ams.get(i).getId());
            }
            mediaStreams.remove(path);
        }
    }
    
    /**
     * Get media stream at the given path.
     * 
     * @param path a path to the media stream
     * @return a media stream or null if there is no media stream at the given
     * path
     */
    public IMediaStream getMediaStream(String path) {
        synchronized (mediaStreams) {
            return mediaStreams.get(path);
        }
    }
    
    /**
     * Create a new RTSP session.
     * 
     * @return a new RTSP session
     */
    public RtspSession createSession() {
        synchronized (sessions) {
            RtspSession sess = new RtspSession();
            sessions.put(sess.getId(), sess);
            return sess;
        }
    }
    
    /**
     * Get RTSP with the given session ID.
     * 
     * @param id a session ID
     * @return RTSP session or null if there is no session with the given ID
     * or the existing session expired
     */
    public RtspSession getSession(String id) {
        synchronized (sessions) {
            RtspSession result = sessions.get(id);
            if (result != null && !result.isValid()) {
                dropSession(id);
                result = null;
            }

            if (result != null)
                result.setLastAccessTime(System.currentTimeMillis());
            
            return result;
        }
    }
    
    /**
     * Drop RTSP session with the given ID and release all its resources.
     * 
     * @param id a session ID
     */
    public void dropSession(String id) {
        synchronized (sessions) {
            RtspSession sess = sessions.get(id);
            if (sess == null)
                return;
            sess.free();
            sessions.remove(id);
        }
    }
    
    @Override
    public Runnable createConnectionHandler(Socket socket) {
        return new RtspConnectionHandler(this, socket);
    }
    
    /**
     * Release all resources associated with server and terminate all sessions.
     */
    public void dispose() {
        synchronized (mediaStreams) {
            for (IMediaStream ms : mediaStreams.values()) {
                if (ms.isStandalone())
                    ms.free();
            }
            mediaStreams.clear();
            mediaStreamAliases.clear();
        }
        synchronized (sessions) {
            for (RtspSession session : sessions.values())
                session.free();
            sessions.clear();
        }
    }
    
    private class SessionChecker extends TimerTask {
        @Override
        public void run() {
            List<String> dropIds = new ArrayList<String>();
            synchronized (sessions) {
                for (RtspSession sess : sessions.values()) {
                    if (!sess.isValid())
                        dropIds.add(sess.getId());
                }
                for (String id : dropIds)
                    dropSession(id);
            }
        }
    }
    
    private class AggregateChangeListener implements IAggregateMediaStream.IStreamChangeListener {
        @Override
        public void streamAdded(IAggregateMediaStream sender, ISingleMediaStream stream) {
            synchronized (mediaStreams) {
                Set<String> aliases = mediaStreamAliases.get(sender);
                if (aliases == null)
                    return;
                
                for (String alias : aliases)
                    mediaStreams.put(alias + "/trackId=" + stream.getId(), stream);
            }
        }

        @Override
        public void streamRemoved(IAggregateMediaStream sender, ISingleMediaStream stream) {
            synchronized (mediaStreams) {
                Set<String> aliases = mediaStreamAliases.get(sender);
                if (aliases == null)
                    return;
                
                stream.free();
                for (String alias : aliases)
                    mediaStreams.remove(alias + "/trackId=" + stream.getId());
            }
        }
    }
    
}

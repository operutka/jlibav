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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.*;
import org.libav.ITimestampGenerator;
import org.libav.net.sdp.Attribute;
import org.libav.net.sdp.Connection;
import org.libav.net.sdp.SessionDescription;

/**
 * Simple aggregate media stream. It provides methods to handle multiple single 
 * media streams.
 * 
 * @author Ondrej Perutka
 */
public class SimpleAggregateMediaStream extends AbstractMediaStream implements IAggregateMediaStream {
    
    private final List<ISingleMediaStream> mediaStreams;
    private final Set<IStreamChangeListener> streamChangeListeners;
    private TimestampGeneratorFactory tsGeneratorFactory;
    private long maxId;

    /**
     * Create a new aggregate media stream.
     */
    public SimpleAggregateMediaStream() {
        this.mediaStreams = Collections.synchronizedList(new ArrayList<ISingleMediaStream>());
        this.streamChangeListeners = Collections.synchronizedSet(new HashSet<IStreamChangeListener>());
        this.tsGeneratorFactory = new TimestampGeneratorFactory();
        this.maxId = 0;
    }

    @Override
    public void addStreamChangeListener(IStreamChangeListener l) {
        streamChangeListeners.add(l);
    }

    @Override
    public void removeStreamChangeListener(IStreamChangeListener l) {
        streamChangeListeners.remove(l);
    }

    @Override
    public boolean play(String sessionId) throws IOException {
        boolean result = false;
        
        synchronized (mediaStreams) {
            for (ISingleMediaStream ms : mediaStreams)
                result |= ms.play(sessionId);
        }
        
        return result;
    }

    @Override
    public boolean pause(String sessionId) throws IOException {
        boolean result = false;
        
        synchronized (mediaStreams) {
            for (ISingleMediaStream ms : mediaStreams)
                result |= ms.pause(sessionId);
        }
        
        return result;
    }

    @Override
    public void teardown(String sessionId) {
        synchronized (mediaStreams) {
            for (ISingleMediaStream ms : mediaStreams)
                ms.teardown(sessionId);
        }
        tsGeneratorFactory.dropSession(sessionId);
    }
    
    /**
     * Add a new single media stream.
     * 
     * @param stream a single media stream
     * @return ID of the added stream
     */
    public long add(ISingleMediaStream stream) {
        stream.setParentStream(this);
        synchronized (mediaStreams) {
            stream.setId(maxId++);
            mediaStreams.add(stream);
        }
        
        for (IStreamChangeListener l : streamChangeListeners)
            l.streamAdded(this, stream);
        
        return stream.getId();
    }
    
    @Override
    public ISingleMediaStream get(int streamIndex) {
        return mediaStreams.get(streamIndex);
    }
    
    @Override
    public ISingleMediaStream getById(long id) {
        for (ISingleMediaStream sms : mediaStreams) {
            if (sms.getId() == id)
                return sms;
        }
        
        return null;
    }
    
    @Override
    public int count() {
        return mediaStreams.size();
    }
    
    /**
     * Remove single media stream managed by this aggregate media stream at the
     * given index.
     * 
     * @param streamIndex
     * @return removed single media stream
     */
    public ISingleMediaStream remove(int streamIndex) {
        ISingleMediaStream result = mediaStreams.remove(streamIndex);
        result.setParentStream(null);
        
        for (IStreamChangeListener l : streamChangeListeners)
            l.streamRemoved(this, result);
        
        return result;
    }
    
    /**
     * Remove single media stream with the given ID.
     * 
     * @param id stream ID
     * @return single media stream or null if there is no such stream
     */
    public ISingleMediaStream removeById(long id) {
        for (int i = 0; i < mediaStreams.size(); i++) {
            if (mediaStreams.get(i).getId() == id)
                return remove(i);
        }
        
        return null;
    }

    @Override
    public void free() {
        synchronized (mediaStreams) {
            for (ISingleMediaStream ms : mediaStreams)
                ms.free();
        }
    }

    @Override
    public boolean isStandalone() {
        return true;
    }

    @Override
    public ITimestampGenerator createTimestampGenerator(String sessionId) {
        return tsGeneratorFactory.createTimestampGenerator(sessionId);
    }
    
    @Override
    public SessionDescription getSessionDescription(String url, Charset charset) {
        InetAddress blank = null;
        try {
            blank = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException ex) { }
        
        SessionDescription sd = new SessionDescription(blank);
        sd.setConnectionData(new Connection(blank));
        sd.addAttribute(new Attribute("tool", "jlibav"));
        sd.addAttribute(new Attribute("recvonly"));
        sd.addAttribute(new Attribute("type", "broadcast"));
        sd.addAttribute(new Attribute("charset", charset.name()));
        sd.addAttribute(new Attribute("control", url));
        
        synchronized (mediaStreams) {
            for (ISingleMediaStream sms : mediaStreams)
                sd.getMediaDescriptions().add(sms.getMediaDescription((int)sms.getId()));
        }
        
        return sd;
    }
    
}

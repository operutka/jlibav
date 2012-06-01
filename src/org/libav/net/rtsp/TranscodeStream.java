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
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.libav.IMediaEncoder;
import org.libav.IMediaWriter;
import org.libav.LibavException;
import org.libav.net.sdp.*;

/**
 * General RTSP transcode stream class.
 * 
 * @author Ondrej Perutka
 */
public class TranscodeStream extends AbstractSingleMediaStream implements ISingleMediaStream {
    
    protected final Map<String, IMediaEncoder> mediaEncoders;
    protected final Set<IMediaEncoder> playbackSet;
    protected IStreamWriterFactory streamWriterFactory;
    
    protected SessionDescription sdp;
    
    /**
     * Create a new transcode RTSP stream.
     * 
     * @param streamWriterFactory a stream writer factory
     * @throws IOException if the stream cannot be created
     */
    protected TranscodeStream(IStreamWriterFactory streamWriterFactory) throws IOException {
        this.mediaEncoders = new HashMap<String, IMediaEncoder>();
        this.playbackSet = Collections.synchronizedSet(new HashSet<IMediaEncoder>());
        this.streamWriterFactory = streamWriterFactory;
        
        // prepare the stream session description
        InetAddress blank = null;
        try {
            blank = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException ex) { }
        
        this.sdp = getLibavSdp();
        this.sdp.setOrigin(new Origin(blank));
        this.sdp.setConnectionData(new Connection(blank));
        this.sdp.addAttribute(new Attribute("tool", "jlibav"));
        this.sdp.addAttribute(new Attribute("recvonly"));
        this.sdp.addAttribute(new Attribute("type", "broadcast"));
        for (MediaDescription md : this.sdp.getMediaDescriptions()) {
            md.setConnection(null);
            md.setPort(0);
            md.setNumOfPorts(1);
        }
    }
    
    @Override
    public synchronized UnicastConnectionInfo setupUnicast(String sessionId, InetAddress address, int rtpPort, int rtcpPort) throws IOException {
        if (mediaEncoders.containsKey(sessionId))
            return null;
        
        UnicastConnectionInfo result;
        try {
            result = new UnicastConnectionInfo(address, rtpPort, rtcpPort);
            IMediaEncoder me = createUnicastMediaEncoder(result);
            IMediaWriter mw = me.getMediaWriter();
            mw.setInterleave(false);
            streamWriterFactory.createWriter(mw);
            mw.writeHeader();
            mediaEncoders.put(sessionId, me);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
        
        return result;
    }
    
    @Override
    public synchronized boolean play(String sessionId) {
        IMediaEncoder me = mediaEncoders.get(sessionId);
        if (me == null)
            return false;
        
        playbackSet.add(me);
        
        return true;
    }

    @Override
    public synchronized boolean pause(String sessionId) {
        IMediaEncoder me = mediaEncoders.get(sessionId);
        if (me == null)
            return false;
        
        playbackSet.remove(me);
        
        return true;
    }

    @Override
    public synchronized void teardown(String sessionId) {
        IMediaEncoder me = mediaEncoders.get(sessionId);
        if (me == null)
            return;
        
        teardown(me);
        mediaEncoders.remove(sessionId);
    }
    
    private void teardown(IMediaEncoder me) {
        try {
            playbackSet.remove(me);
            me.close();
        } catch (LibavException ex) {
            Logger.getLogger(TranscodeStream.class.getName()).log(Level.WARNING, "unable to close a media writer", ex);
        }
    }

    @Override
    public synchronized void free() {
        for (IMediaEncoder me : mediaEncoders.values())
            teardown(me);
        mediaEncoders.clear();
        playbackSet.clear();
    }

    @Override
    public SessionDescription getSessionDescription(String url, Charset charset) {
        SessionDescription result = sdp.clone();
        result.addAttribute(new Attribute("charset", charset.name()));
        result.addAttribute(new Attribute("control", url));
        
        return result;
    }

    @Override
    public MediaDescription getMediaDescription(int trackId) {
        MediaDescription md = sdp.getMediaDescriptions().get(0);
        md = md.clone();
        if (trackId >= 0)
            md.addAttribute(new Attribute("control", "trackId=" + trackId));
        else
            md.removeAttribute("control");
        
        return md;
    }
    
    /**
     * Get session description provided by the Libav.
     * 
     * @return a session description
     * @throws IOException if the SDP cannot be created for some reason
     */
    private SessionDescription getLibavSdp() throws IOException {
        int port = 43000 + (int)(10000 * Math.random());
        if ((port % 2) != 0)
            port++;
        IMediaEncoder me = null;
        
        try {
            UnicastConnectionInfo ci = new UnicastConnectionInfo(InetAddress.getLocalHost(), port, port + 1);
            me = createUnicastMediaEncoder(ci);
            IMediaWriter mw = me.getMediaWriter();
            streamWriterFactory.createWriter(mw);
            mw.writeHeader();
            return SessionDescription.parse(mw.getSdp());
        } catch (LibavException ex) {
            throw new IOException(ex);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (me != null)
                    me.close();
            } catch (LibavException ex) {
                throw new IOException(ex);
            }
        }
    }
    
}

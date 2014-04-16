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
import org.libav.IMediaWriter;
import org.libav.LibavException;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avcodec.IPacketWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.avutil.MediaType;
import org.libav.data.IPacketConsumer;
import org.libav.net.sdp.*;

/**
 * FIX: It is not working (bad image size on the client side).
 * 
 * @author Ondrej Perutka
 */
public class CopyStream extends AbstractSingleMediaStream implements ISingleMediaStream, IPacketConsumer {
    
    private final Map<String, IMediaWriter> mediaWriters;
    private final Set<IMediaWriter> playbackSet;
    private final IStreamWrapper inputStream;
    
    private final SessionDescription sdp;
    
    private long ptsOffset;
    
    /**
     * Create a new transcode RTSP stream.
     * 
     * @param inputStream an input stream
     * @throws IOException if the stream cannot be created
     */
    public CopyStream(IStreamWrapper inputStream) throws IOException {
        mediaWriters = new HashMap<String, IMediaWriter>();
        playbackSet = Collections.synchronizedSet(new HashSet<IMediaWriter>());
        this.inputStream = inputStream;
        
        ptsOffset = -1;
        
        // prepare the stream session description
        InetAddress blank = null;
        try {
            blank = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException ex) { }
        
        sdp = getLibavSdp();
        sdp.setOrigin(new Origin(blank));
        sdp.setConnectionData(new Connection(blank));
        sdp.addAttribute(new Attribute("tool", "jlibav"));
        sdp.addAttribute(new Attribute("recvonly"));
        sdp.addAttribute(new Attribute("type", "broadcast"));
        for (MediaDescription md : sdp.getMediaDescriptions()) {
            md.setConnection(null);
            md.setPort(0);
            md.setNumOfPorts(1);
        }
    }
    
    @Override
    public synchronized UnicastConnectionInfo setupUnicast(String sessionId, InetAddress address, int rtpPort, int rtcpPort) throws IOException {
        if (mediaWriters.containsKey(sessionId))
            return null;
        
        UnicastConnectionInfo result;
        try {
            result = new UnicastConnectionInfo(address, rtpPort, rtcpPort);
            IMediaWriter mw = createUnicastMediaWriter(result);
            mw.setInterleave(false);
            createStream(mw);
            mw.writeHeader();
            mediaWriters.put(sessionId, mw);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
        
        return result;
    }
    
    private void createStream(IMediaWriter writer) throws LibavException {
        ICodecContextWrapper cc = inputStream.getCodecContext();
        if (cc.getCodecType() == MediaType.AUDIO)
            writer.addAudioStream(cc.getCodecId(), cc.getSampleRate(), cc.getSampleFormat(), cc.getChannels());
        else if (cc.getCodecType() == MediaType.VIDEO)
            writer.addVideoStream(cc.getCodecId(), cc.getWidth(), cc.getHeight());
        else
            throw new LibavException("unsupported media type");
    }
    
    @Override
    public synchronized boolean play(String sessionId) {
        IMediaWriter mw = mediaWriters.get(sessionId);
        if (mw == null)
            return false;
        
        playbackSet.add(mw);
        
        return true;
    }

    @Override
    public synchronized boolean pause(String sessionId) {
        IMediaWriter mw = mediaWriters.get(sessionId);
        if (mw == null)
            return false;
        
        playbackSet.remove(mw);
        
        return true;
    }

    @Override
    public synchronized void teardown(String sessionId) {
        IMediaWriter mw = mediaWriters.get(sessionId);
        if (mw == null)
            return;
        
        teardown(mw);
        mediaWriters.remove(sessionId);
    }
    
    private void teardown(IMediaWriter mw) {
        try {
            playbackSet.remove(mw);
            mw.close();
        } catch (LibavException ex) {
            Logger.getLogger(TranscodeStream.class.getName()).log(Level.WARNING, "unable to close a media writer", ex);
        }
    }

    @Override
    public synchronized void free() {
        for (IMediaWriter mw : mediaWriters.values())
            teardown(mw);
        mediaWriters.clear();
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

    @Override
    public void processPacket(Object producer, IPacketWrapper packet) throws LibavException {
        if (packet.getStreamIndex() != inputStream.getIndex())
            return;
        
        IPacketWrapper pw = packet.clone();
        pw.setStreamIndex(0);
        if (ptsOffset == -1)
            ptsOffset = packet.getDts();
        pw.setDts(packet.getDts() - ptsOffset);
        pw.setPts(pw.getDts());
        
        IMediaWriter[] mws;
        synchronized (playbackSet) {
            mws = playbackSet.toArray(new IMediaWriter[playbackSet.size()]);
        }
        
        IPacketWrapper tmp;
        for (IMediaWriter mw : mws) {
            tmp = pw.clone();
            mw.processPacket(this, tmp);
            tmp.free();
        }
        pw.free();
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
        IMediaWriter mw = null;
        
        try {
            UnicastConnectionInfo ci = new UnicastConnectionInfo(InetAddress.getLocalHost(), port, port + 1);
            mw = createUnicastMediaWriter(ci);
            createStream(mw);
            mw.writeHeader();
            return SessionDescription.parse(mw.getSdp());
        } catch (LibavException ex) {
            throw new IOException(ex);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (mw != null)
                    mw.close();
            } catch (LibavException ex) {
                throw new IOException(ex);
            }
        }
    }
    
}

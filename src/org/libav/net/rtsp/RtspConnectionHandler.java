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
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.libav.net.NetworkStreamReader;
import org.libav.net.NetworkStreamWriter;
import org.libav.net.rtsp.message.*;

/**
 * RTSP connection handler. It is responsible for a communication with a remote
 * client.
 * 
 * @author Ondrej Perutka
 */
public class RtspConnectionHandler implements Runnable {

    private static final Pattern uriParsePattern = Pattern.compile("rtsp://([^\\s:/]+)(:([0-9]+))?(/[^\\s?]*)?.*");
    private static final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    
    private Charset charset;
    private RtspServer server;
    private Socket socket;

    /**
     * Create a new RTSP connection handler.
     * 
     * @param server parent RTSP server
     * @param socket a client socket
     */
    public RtspConnectionHandler(RtspServer server, Socket socket) {
        this.charset = Charset.forName("UTF-8");
        this.server = server;
        this.socket = socket;
    }
    
    @Override
    public void run() {
        NetworkStreamWriter nsw;
        NetworkStreamReader nsr;
        List<String> lines = new ArrayList<String>();
        RtspRequestHeader header;
        ContentLengthField cl;
        String line;
        byte[] body;
        
        try {
            nsw = new NetworkStreamWriter(socket.getOutputStream(), charset);
            nsr = new NetworkStreamReader(socket.getInputStream(), 2048, charset);
            Logger.getLogger(getClass().getName()).log(Level.INFO, "opened connection with client: {0}", socket.getInetAddress().getHostAddress());
            
            do {
                line = nsr.readLine();
                if (line == null)
                    return;
                
                lines.clear();
                while (line != null && !"".equals(line)) {
                    lines.add(line);
                    line = nsr.readLine();
                }
                
                header = null;
                try {
                    header = RtspRequestHeader.parse(lines.toArray(new String[lines.size()]));
                    cl = (ContentLengthField)header.getField("content-length");
                    body = null;
                    if (cl != null)
                        nsr.read(body, 0, (int)cl.getLength());
                    sendResponse(nsw, processRequest(new RtspMessage(header, body)));
                } catch (ParseException ex) {
                    sendResponse(nsw, new RtspMessage(400, "Bad Request: " + ex.getMessage(), 0));
                } catch (RtspException ex) {
                    sendResponse(nsw, ex.getResponse());
                }
            } while (header != null && !shouldCloseConnection(header));
        } catch (SocketException ex) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "client {0} closed connection", socket.getInetAddress().getHostAddress());
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "error while processing RTSP request", ex);
        } finally {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "closed connection with client: {0}", socket.getInetAddress().getHostAddress());
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "unable to close a socket", ex);
            }
        }
    }
    
    /**
     * Send the given response to the remote client.
     * 
     * @param nsw stream writer
     * @param response a response
     * @throws IOException an exception thrown by the stream writer
     */
    private void sendResponse(NetworkStreamWriter nsw, RtspMessage response) throws IOException {
        StringBuilder log = new StringBuilder("\n--------------------------------------------------------------------------------\n");
        log.append("Sending response to the client: ").append(socket.getInetAddress().getHostAddress()).append("\n");
        log.append(response.getHeader().toString().replace("\r\n", "\n"));
        if (response.getBody() != null)
            log.append(response.getBodyText(charset));
        log.append("--------------------------------------------------------------------------------");
        Logger.getLogger(getClass().getName()).log(Level.INFO, log.toString());
        
        nsw.write(response.getRawMessage());
        nsw.flush();
    }
    
    /**
     * Check whether there is a "Connection: close" in the request header.
     * 
     * @param header a request header
     * @return true if the connection should be closed after sending a response
     */
    private boolean shouldCloseConnection(RtspHeader header) {
        ConnectionField cf = (ConnectionField)header.getField("connection");
        if (cf == null)
            return false;
        
        return cf.containsParam("close");
    }
    
    /**
     * Process the given request and create a response.
     * 
     * @param request a request to be processed
     * @return a response to the given request
     */
    private RtspMessage processRequest(RtspMessage request) {
        StringBuilder log = new StringBuilder("\n--------------------------------------------------------------------------------\n");
        log.append("Received request from client: ").append(socket.getInetAddress().getHostAddress()).append("\n");
        log.append(request.getHeader().toString().replace("\r\n", "\n"));
        if (request.getBody() != null)
            log.append(request.getBodyText(charset));
        log.append("--------------------------------------------------------------------------------");
        Logger.getLogger(getClass().getName()).log(Level.INFO, log.toString());
        
        RtspRequestHeader requestHeader = (RtspRequestHeader)request.getHeader();
        
        UnsupportedField uf = new UnsupportedField();
        RequireField rf = (RequireField)requestHeader.getField("require");
        if (rf != null)
            uf.addFeatures(rf.getFeatures());
        rf = (RequireField)requestHeader.getField("proxy-require");
        if (rf != null)
            uf.addFeatures(rf.getFeatures());
        if (uf.getFeatureCount() > 0) {
            RtspResponseHeader responseHeader = new RtspResponseHeader(551, "Option not supported", requestHeader.getCSeq());
            responseHeader.addField(uf);
            return new RtspMessage(responseHeader, null);
        }
        
        try {
            if (RtspRequestHeader.METHOD_OPTIONS.equals(requestHeader.getMethod()))
                return options(request);
            else if (RtspRequestHeader.METHOD_DESCRIBE.equals(requestHeader.getMethod()))
                return describe(request);
            else if (RtspRequestHeader.METHOD_SETUP.equals(requestHeader.getMethod()))
                return setup(request);
            else if (RtspRequestHeader.METHOD_PLAY.equals(requestHeader.getMethod()))
                return play(request);
            else if (RtspRequestHeader.METHOD_PAUSE.equals(requestHeader.getMethod()))
                return pause(request);
            else if (RtspRequestHeader.METHOD_GET_PARAMETER.equals(requestHeader.getMethod()))
                return getParameter(request);
            else if (RtspRequestHeader.METHOD_TEARDOWN.equals(requestHeader.getMethod()))
                return teardown(request);
        } catch (RtspException ex) {
            return ex.getResponse();
        }
        
        return new RtspMessage(501, "Not Implemented: " + requestHeader.getMethod(), requestHeader.getCSeq());
    }
    
    /**
     * RTSP options method. See RTSP specification for details.
     * 
     * @param request a request message
     * @return a response
     */
    private RtspMessage options(RtspMessage request) {
        RtspRequestHeader requestHeader = (RtspRequestHeader)request.getHeader();
        RtspResponseHeader responseHeader = new RtspResponseHeader(200, "OK", requestHeader.getCSeq());
        responseHeader.addField(new GenericField("Public", "OPTIONS,DESCRIBE,SETUP,TEARDOWN,PLAY,PAUSE,GET_PARAMETER"));
        return new RtspMessage(responseHeader, null);
    }
    
    /**
     * RTSP describe method. See RTSP specification for details.
     * 
     * @param request a request message
     * @return a response
     * @throws RtspException if there is no media stream at the URL specified
     * in the given request
     */
    private RtspMessage describe(RtspMessage request) throws RtspException {
        RtspRequestHeader requestHeader = (RtspRequestHeader)request.getHeader();
        IMediaStream ms = getMediaStream(requestHeader);
        
        RtspResponseHeader responseHeader = new RtspResponseHeader(200, "OK", requestHeader.getCSeq());
        responseHeader.addField(new DateField(calendar.getTime()));
        responseHeader.addField(new GenericField("Content-Type", "application/sdp"));
        
        RtspMessage result = new RtspMessage(responseHeader, null);
        result.setBodyText(ms.getSessionDescription(requestHeader.getUri(), charset).toString(), charset);
        
        return result;
    }
    
    /**
     * RTSP setup method. See RTSP specification for details.
     * 
     * @param request a request message
     * @return a response
     * @throws RtspException if the setup command cannot be executed (several 
     * reasons could cause that)
     */
    private RtspMessage setup(RtspMessage request) throws RtspException {
        RtspRequestHeader requestHeader = (RtspRequestHeader)request.getHeader();
        ISingleMediaStream sms = getSingleMediaStream(requestHeader);
        SessionField sf = (SessionField)requestHeader.getField("session");
        RtspSession session;
        if (sf == null)
            session = server.createSession();
        else
            session = server.getSession(sf.getId());
        if (session == null)
            throw new RtspException(454, "Session Not Found", requestHeader.getCSeq());
        
        TransportField tp = (TransportField)requestHeader.getField("transport");
        if (tp == null)
            throw new RtspException(400, "Bad Request (missing transport header field)", requestHeader.getCSeq());
        if (tp.getMulticast())
            throw new RtspException(461, "Unsupported Transport (multicast is not supported)", requestHeader.getCSeq());
        if (!"RTP".equalsIgnoreCase(tp.getProtocol()))
            throw new RtspException(461, "Unsupported Transport (protocol \"" + tp.getProtocol() + "\" is not supported)", requestHeader.getCSeq());
        if (!"AVP".equalsIgnoreCase(tp.getProfile()))
            throw new RtspException(461, "Unsupported Transport (RTP profile \"" + tp.getProfile() + "\" is not supported)", requestHeader.getCSeq());
        if (tp.getLowerTransport() != null && !"UDP".equalsIgnoreCase(tp.getLowerTransport()))
            throw new RtspException(461, "Unsupported Transport (lower transport \"" + tp.getLowerTransport() + "\" is not supported)", requestHeader.getCSeq());
        if (tp.getClientPortFrom() == null)
            throw new RtspException(451, "Invalid parameter (missing client port in the transport header field)", requestHeader.getCSeq());
        
        int rtpPort = tp.getClientPortFrom();
        int rtcpPort = tp.getClientPortTo() == null ? rtpPort + 1 : tp.getClientPortTo();
        ISingleMediaStream.UnicastConnectionInfo ci;
        try {
            ci = sms.setupUnicast(session.getId(), socket.getInetAddress(), rtpPort, rtcpPort);
            if (ci == null)
                throw new RtspException(459, "Aggregate Operation Not Allowed (media stream is already set up for this session)", requestHeader.getCSeq());
        } catch (IOException ex) {
            throw new RtspException(500, "Internal Server Error", requestHeader.getCSeq(), ex);
        }
        session.addResource(sms.isStandalone() ? sms : sms.getParentStream());
        
        RtspResponseHeader responseHeader = new RtspResponseHeader(200, "OK", requestHeader.getCSeq());
        responseHeader.addField(new DateField(calendar.getTime()));
        responseHeader.addField(new SessionField(session.getId(), session.getTimeout() / 1000));
        tp = new TransportField();
        tp.setClientPortFrom(rtpPort);
        tp.setClientPortTo(rtcpPort);
        tp.setServerPortFrom(ci.getServerRtpPort());
        tp.setServerPortTo(ci.getServerRtcpPort());
        responseHeader.addField(tp);
        
        return new RtspMessage(responseHeader, null);
    }
    
    /**
     * RTSP play method. See RTSP specification for details.
     * 
     * @param request a request message
     * @return a response
     * @throws RtspException if the play command cannot be executed (several 
     * reasons could cause that)
     */
    private RtspMessage play(RtspMessage request) throws RtspException {
        RtspRequestHeader requestHeader = (RtspRequestHeader)request.getHeader();
        IMediaStream ms = getMediaStream(requestHeader);
        RtspSession session = getSession(requestHeader);
        // TODO: process range field (not necessary for live streaming)
        try {
            if (!ms.play(session.getId()))
                throw new RtspException(455, "Method Not Valid In This State (call setup at first)", requestHeader.getCSeq());
        } catch (IOException ex) {
            throw new RtspException(500, "Internal Server Error", requestHeader.getCSeq(), ex);
        }
        
        RtspResponseHeader responseHeader = new RtspResponseHeader(200, "OK", requestHeader.getCSeq());
        responseHeader.addField(new DateField(calendar.getTime()));
        responseHeader.addField(new SessionField(session.getId()));
        // TODO: add range field
        return new RtspMessage(responseHeader, null);
    }
    
    /**
     * RTSP pause method. See RTSP specification for details.
     * 
     * @param request a request message
     * @return a response
     * @throws RtspException if the pause command cannot be executed (several 
     * reasons could cause that)
     */
    private RtspMessage pause(RtspMessage request) throws RtspException {
        RtspRequestHeader requestHeader = (RtspRequestHeader)request.getHeader();
        IMediaStream ms = getMediaStream(requestHeader);
        RtspSession session = getSession(requestHeader);
        try {
            if (!ms.pause(session.getId()))
                throw new RtspException(455, "Method Not Valid In This State (call setup at first)", requestHeader.getCSeq());
        } catch (IOException ex) {
            throw new RtspException(500, "Internal Server Error", requestHeader.getCSeq(), ex);
        }
        
        RtspResponseHeader responseHeader = new RtspResponseHeader(200, "OK", requestHeader.getCSeq());
        responseHeader.addField(new DateField(calendar.getTime()));
        responseHeader.addField(new SessionField(session.getId()));
        return new RtspMessage(responseHeader, null);
    }
    
    /**
     * RTSP teardown method. See RTSP specification for details.
     * 
     * @param request a request message
     * @return a response
     * @throws RtspException if the teardown command cannot be executed (several 
     * reasons could cause that)
     */
    private RtspMessage teardown(RtspMessage request) throws RtspException {
        RtspRequestHeader requestHeader = (RtspRequestHeader)request.getHeader();
        IMediaStream ms = getMediaStream(requestHeader);
        RtspSession session = getSession(requestHeader);
        if (ms instanceof IAggregateMediaStream) {
            IAggregateMediaStream ams = (IAggregateMediaStream)ms;
            for (int i = 0; i < ams.count(); i++)
                session.removeResource(ams.get(i));
        } else
            session.removeResource((ISingleMediaStream)ms);
        ms.teardown(session.getId());
        
        return new RtspMessage(200, "OK", requestHeader.getCSeq());
    }
    
    /**
     * RTSP GET_PARAMETER method. See RTSP specification for details.
     * 
     * NOTE:
     * This method does nothing it is used only to keep sessions alive.
     * 
     * @param request a request message
     * @return a response
     * @throws RtspException if the teardown command cannot be executed (several 
     * reasons could cause that)
     */
    private RtspMessage getParameter(RtspMessage request) throws RtspException {
        RtspRequestHeader requestHeader = (RtspRequestHeader)request.getHeader();
        getMediaStream(requestHeader);
        RtspSession session = getSession(requestHeader);
        
        RtspResponseHeader responseHeader = new RtspResponseHeader(200, "OK", requestHeader.getCSeq());
        responseHeader.addField(new DateField(calendar.getTime()));
        responseHeader.addField(new SessionField(session.getId()));
        return new RtspMessage(responseHeader, null);
    }
    
    /**
     * Get RTSP session for the given request.
     * 
     * @param requestHeader a request header
     * @return an RTSP session
     * @throws RtspException if there is no such session or the Session field
     * is missing in the given header
     */
    private RtspSession getSession(RtspRequestHeader requestHeader) throws RtspException {
        SessionField sf = (SessionField)requestHeader.getField("session");
        if (sf == null)
            throw new RtspException(400, "Bad Request (missing session field)", requestHeader.getCSeq());
        RtspSession result = server.getSession(sf.getId());
        if (result == null)
            throw new RtspException(454, "Session Not Found", requestHeader.getCSeq());
        
        return result;
    }
    
    /**
     * Get a media stream for the given request.
     * 
     * @param requestHeader a request header
     * @return a media stream
     * @throws RtspException if there is no stream at the URL specified in the
     * given request header
     */
    private IMediaStream getMediaStream(RtspRequestHeader requestHeader) throws RtspException {
        Matcher m = uriParsePattern.matcher(requestHeader.getUri());
        if (!m.find())
            throw new RtspException(404, "Not found", requestHeader.getCSeq());
        IMediaStream result = server.getMediaStream(m.group(4));
        if (result == null)
            throw new RtspException(404, "Not found", requestHeader.getCSeq());
        
        return result;
    }
    
    /**
     * Get single media stream. This method calls the getMediaStream() method
     * and checks whether the returned stream is an AggregateMediaStream.
     * 
     * @param requestHeader a request header
     * @return a single media stream
     * @throws RtspException if there is no such stream or the stream is an
     * AggregateMediaStream
     */
    private ISingleMediaStream getSingleMediaStream(RtspRequestHeader requestHeader) throws RtspException {
        IMediaStream result = getMediaStream(requestHeader);
        if (result instanceof IAggregateMediaStream)
            throw new RtspException(459, "Aggregate Operation Not Allowed", requestHeader.getCSeq());
        
        return (ISingleMediaStream)result;
    }
    
}

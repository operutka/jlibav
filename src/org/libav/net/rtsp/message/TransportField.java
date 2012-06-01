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
package org.libav.net.rtsp.message;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Transport RTSP header field builder.
 * 
 * @author Ondrej Perutka
 */
public class TransportField extends RtspHeader.Field {
    
    private static final Pattern parsePattern = Pattern.compile("^([^\\s/;]+)/([^\\s/;]+)(/([^\\s;]+))?((;[^\\s;]*)*)$");
    private static final Pattern paramPattern = Pattern.compile("[^\\s;]+");
    
    public static final String PROTOCOL_RTP = "RTP";
    public static final String PROFILE_AVP = "AVP";
    public static final String L_TRANSPORT_TCP = "TCP";
    public static final String L_TRANSPORT_UDP = "UDP";
    
    private String protocol;
    private String profile;
    private String lowerTransport;
    private boolean multicast;
    private String destination;
    private Integer interleavedFrom;
    private Integer interleavedTo;
    private boolean append;
    private Integer ttl;
    private Integer layers;
    private Integer portFrom;
    private Integer portTo;
    private Integer clientPortFrom;
    private Integer clientPortTo;
    private Integer serverPortFrom;
    private Integer serverPortTo;
    private String ssrc;
    private String mode;

    /**
     * Create a new Transport field and set the RTP protocol, AVP profile and
     * unicast as defaults.
     */
    public TransportField() {
        this(PROTOCOL_RTP, PROFILE_AVP, false);
    }
    
    /**
     * Create a new Transport field and set the protocol, profile and multicast
     * flag.
     * 
     * @param protocol
     * @param profile
     * @param multicast 
     */
    public TransportField(String protocol, String profile, boolean multicast) {
        super("Transport");
        this.protocol = protocol;
        this.profile = profile;
        this.lowerTransport = null;
        this.multicast = multicast;
        this.destination = null;
        this.interleavedFrom = null;
        this.interleavedTo = null;
        this.append = false;
        this.ttl = null;
        this.layers = null;
        this.portFrom = null;
        this.portTo = null;
        this.clientPortFrom = null;
        this.clientPortTo = null;
        this.serverPortFrom = null;
        this.serverPortTo = null;
        this.ssrc = null;
        this.mode = null;
    }

    /**
     * Get append flag.
     * 
     * @return true if the media data should be appended to the existing 
     * resource, false otherwise (used in combination with the RECORD method)
     */
    public boolean getAppend() {
        return append;
    }

    /**
     * Set append flag. This is valid only in combination with the RECOR method.
     * Set true to append media data to the existing resource, false to
     * overwrite it.
     * 
     * @param append 
     */
    public void setAppend(boolean append) {
        this.append = append;
    }

    /**
     * Get client port (first from the range). See RFC specification for 
     * details.
     * 
     * @return client port (first from the range) or null, if the client
     * port is not specified
     */
    public Integer getClientPortFrom() {
        return clientPortFrom;
    }

    /**
     * Set client port (first from the range). See RFC specification for
     * details.
     * 
     * @param clientPortFrom client port (first from the range)
     */
    public void setClientPortFrom(Integer clientPortFrom) {
        this.clientPortFrom = clientPortFrom;
    }

    /**
     * Get client port (last from the range). See RFC specification for 
     * details.
     * 
     * @return client port (last from the range) or null, if the client
     * port is not specified
     */
    public Integer getClientPortTo() {
        return clientPortTo;
    }

    /**
     * Set client port (last from the range). See RFC specification for
     * details.
     * 
     * @param clientPortTo client port (last from the range)
     */
    public void setClientPortTo(Integer clientPortTo) {
        this.clientPortTo = clientPortTo;
    }

    /**
     * Get address where the stream shoul be sent. See RFC specification for
     * details.
     * 
     * @return address where the stream shoul be sent
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Set address where the stream shoul be sent. See RFC specification for
     * details.
     * 
     * @param destination an address where the stream should be sent
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Get interleaved channel number (first from the range). See RFC
     * specification for details.
     * 
     * @return interleaved channel number (first from the range) or null if it
     * is not specified
     */
    public Integer getInterleavedFrom() {
        return interleavedFrom;
    }

    /**
     * Set interleaved channel number (first from the range). See RFC
     * specification for details.
     * 
     * @param interleavedFrom interleaved channel number (first from the range)
     */
    public void setInterleavedFrom(Integer interleavedFrom) {
        this.interleavedFrom = interleavedFrom;
    }

    /**
     * Get interleaved channel number (last from the range). See RFC
     * specification for details.
     * 
     * @return interleaved channel number (last from the range) or null if it
     * is not specified
     */
    public Integer getInterleavedTo() {
        return interleavedTo;
    }

    /**
     * Set interleaved channel number (last from the range). See RFC
     * specification for details.
     * 
     * @param interleavedTo interleaved channel number (last from the range)
     */
    public void setInterleavedTo(Integer interleavedTo) {
        this.interleavedTo = interleavedTo;
    }

    /**
     * Get number of multicast layers. See RFC specification for details.
     * 
     * @return number of multicast layers or null if it is not specified
     */
    public Integer getLayers() {
        return layers;
    }

    /**
     * Set number of multicast layers. See RFC specification for details.
     * 
     * @param layers number of multicast layers
     */
    public void setLayers(Integer layers) {
        this.layers = layers;
    }

    /**
     * Get lower transport.
     * 
     * @return lower transport
     */
    public String getLowerTransport() {
        return lowerTransport;
    }

    /**
     * Set lower transport.
     * 
     * @param lowerTransport 
     */
    public void setLowerTransport(String lowerTransport) {
        this.lowerTransport = lowerTransport;
    }

    /**
     * Get mode (PLAY/RECORD).
     * 
     * @return mode or null if it is not specified
     */
    public String getMode() {
        return mode;
    }

    /**
     * Set mode (valid values are "PLAY" and "RECORD").
     * 
     * @param mode 
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Get multicast flag.
     * 
     * @return true if the stream delivery will be using multicast, false
     * otherwise
     */
    public boolean getMulticast() {
        return multicast;
    }

    /**
     * Set multicast flag. True for multicast, false for unicast.
     * 
     * @param multicast 
     */
    public void setMulticast(boolean multicast) {
        this.multicast = multicast;
    }

    /**
     * Get port (first from the range). See RFC specification for details.
     * 
     * @return port (first from the range) or null, if the port is not specified
     */
    public Integer getPortFrom() {
        return portFrom;
    }

    /**
     * Set port (first from the range). See RFC specification for details.
     * 
     * @param portFrom port (first from the range)
     */
    public void setPortFrom(Integer portFrom) {
        this.portFrom = portFrom;
    }

    /**
     * Get port (last from the range). See RFC specification for details.
     * 
     * @return port (last from the range) or null, if the port is not specified
     */
    public Integer getPortTo() {
        return portTo;
    }

    /**
     * Set port (last from the range). See RFC specification for details.
     * 
     * @param portTo port (last from the range)
     */
    public void setPortTo(Integer portTo) {
        this.portTo = portTo;
    }

    /**
     * Get profile.
     * 
     * @return profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * Set profile.
     * 
     * @param profile 
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * Get transport protocol.
     * 
     * @return transport protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Set transport protocol.
     * 
     * @param protocol transport protocol
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Get server port (first from the range). See RFC specification for 
     * details.
     * 
     * @return server port (first from the range) or null, if the port is not 
     * specified
     */
    public Integer getServerPortFrom() {
        return serverPortFrom;
    }

    /**
     * Set server port (first from the range). See RFC specification for details.
     * 
     * @param serverPortFrom server port (first from the range)
     */
    public void setServerPortFrom(Integer serverPortFrom) {
        this.serverPortFrom = serverPortFrom;
    }

    /**
     * Get server port (last from the range). See RFC specification for 
     * details.
     * 
     * @return server port (last from the range) or null, if the port is not 
     * specified
     */
    public Integer getServerPortTo() {
        return serverPortTo;
    }

    /**
     * Set server port (last from the range). See RFC specification for details.
     * 
     * @param serverPortTo server port (last from the range)
     */
    public void setServerPortTo(Integer serverPortTo) {
        this.serverPortTo = serverPortTo;
    }

    /**
     * Get SSRC. See RFC specification for details.
     * 
     * @return ssrc
     */
    public String getSsrc() {
        return ssrc;
    }

    /**
     * Set SSRC. See RFC specification for details.
     * 
     * @param ssrc 
     */
    public void setSsrc(String ssrc) {
        this.ssrc = ssrc;
    }

    /**
     * Get TTL.
     * 
     * @return TTL or null if it is not specified
     */
    public Integer getTtl() {
        return ttl;
    }

    /**
     * Set TTL.
     * 
     * @param ttl 
     */
    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    @Override
    public TransportField clone() {
        TransportField result = new TransportField();
        result.append = append;
        result.clientPortFrom = clientPortFrom;
        result.clientPortTo = clientPortTo;
        result.destination = destination;
        result.interleavedFrom = interleavedFrom;
        result.interleavedTo = interleavedTo;
        result.layers = layers;
        result.lowerTransport = lowerTransport;
        result.mode = mode;
        result.multicast = multicast;
        result.portFrom = portFrom;
        result.portTo = portTo;
        result.profile = profile;
        result.protocol = protocol;
        result.serverPortFrom = serverPortFrom;
        result.serverPortTo = serverPortTo;
        result.ssrc = ssrc;
        result.ttl = ttl;
        
        return result;
    }

    @Override
    public String getValueText() {
        StringBuilder bldr = new StringBuilder();
        bldr.append(protocol).append("/").append(profile);
        if (lowerTransport != null)
            bldr.append("/").append(lowerTransport);
        bldr.append(";");
        
        if (multicast)
            bldr.append("multicast");
        else
            bldr.append("unicast");
        
        if (destination != null)
            bldr.append(";destination=").append(destination);
        if (interleavedFrom != null) {
            bldr.append(";interleaved=").append(interleavedFrom);
            if (interleavedTo != null)
                bldr.append("-").append(interleavedTo);
        }
        if (append)
            bldr.append(";append");
        if (multicast) {
            if (ttl == null)
                throw new IllegalStateException("multicast is used, ttl must be set");
            else
                bldr.append(";ttl=").append(ttl);
            if (layers != null)
                bldr.append(";layers=").append(layers);
            if (portFrom != null) {
                bldr.append(";port=").append(portFrom);
                if (portTo != null)
                    bldr.append("-").append(portTo);
            }
        }
        if (clientPortFrom != null) {
            bldr.append(";client_port=").append(clientPortFrom);
            if (clientPortTo != null)
                bldr.append("-").append(clientPortTo);
        }
        if (serverPortFrom != null) {
            bldr.append(";server_port=").append(serverPortFrom);
            if (serverPortTo != null)
                bldr.append("-").append(serverPortTo);
        }
        if (!multicast && ssrc != null)
            bldr.append(";ssrc=").append(ssrc);
        if (mode != null)
            bldr.append(";mode=\"").append(mode).append("\"");
        
        return bldr.toString();
    }
    
    /**
     * Parse Transport RTSP header field.
     * 
     * @param fieldValue
     * @return a Transport RTSP header field
     * @throws ParseException if the given fieldValue is not a valid Transport
     * header field
     */
    public static TransportField parse(String fieldValue) throws ParseException {
        TransportField result = new TransportField();
        Matcher m = parsePattern.matcher(fieldValue);
        if (!m.find())
            throw new ParseException("not a valid transport field", 0);
        
        result.setProtocol(m.group(1));
        result.setProfile(m.group(2));
        result.setLowerTransport(m.group(4));
        
        m = paramPattern.matcher(m.group(5));
        String tmp, tmp2;
        try {
            while (m.find()) {
                tmp2 = m.group();
                tmp = tmp2.toLowerCase();
                if (tmp.equals("multicast"))
                    result.setMulticast(true);
                else if (tmp.startsWith("destination="))
                    result.setDestination(tmp2.substring(12));
                else if (tmp.startsWith("interleaved=")) {
                    String[] ch = tmp2.substring(12).split("-");
                    result.setInterleavedFrom(Integer.parseInt(ch[0]));
                    if (ch.length > 1)
                        result.setInterleavedTo(Integer.parseInt(ch[1]));
                } else if (tmp.equals("append"))
                    result.setAppend(true);
                else if (tmp.startsWith("ttl="))
                    result.setTtl(Integer.parseInt(tmp2.substring(4)));
                else if (tmp.startsWith("layers="))
                    result.setLayers(Integer.parseInt(tmp2.substring(7)));
                else if (tmp.startsWith("port=")) {
                    String[] p = tmp2.substring(5).split("-");
                    result.setPortFrom(Integer.parseInt(p[0]));
                    if (p.length > 1)
                        result.setPortTo(Integer.parseInt(p[1]));
                } else if (tmp.startsWith("client_port=")) {
                    String[] p = tmp2.substring(12).split("-");
                    result.setClientPortFrom(Integer.parseInt(p[0]));
                    if (p.length > 1)
                        result.setClientPortTo(Integer.parseInt(p[1]));
                } else if (tmp.startsWith("server_port=")) {
                    String[] p = tmp2.substring(12).split("-");
                    result.setServerPortFrom(Integer.parseInt(p[0]));
                    if (p.length > 1)
                        result.setServerPortTo(Integer.parseInt(p[1]));
                } else if (tmp.startsWith("ssrc="))
                    result.setSsrc(tmp2.substring(5));
                else if (tmp.startsWith("mode="))
                    result.setMode(tmp2.substring(5).replace("\"", ""));
            }
        } catch (Exception ex) {
            throw (ParseException) new ParseException("not a valid transport field", 0).initCause(ex);
        }
        
        return result;
    }
    
}

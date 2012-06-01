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
package org.libav.net.sdp;

import java.net.InetAddress;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Session description parser/builder. See RFC 4566.
 * 
 * @author Ondrej Perutka
 */
public class SessionDescription implements Cloneable {
    
    public static final String BANDWIDTH_CONFERENCE_TOTAL  = "CT";
    public static final String BANDWIDTH_APPLICATION_SPECIFIC = "AS";
    
    private String protocolVersion;
    private Origin origin;
    private String sessionName;
    private String sessionDescription;
    private String uri;
    private List<String> phones;
    private List<String> emails;
    private Connection connectionData;
    private Map<String, Long> bandwidth;
    private List<TimeDescription> times;
    private TimeZoneAdjustment timeZoneAdjustment;
    private Key key;
    private Map<String, Attribute> attributes;
    private List<MediaDescription> mediaDescriptions;

    /**
     * Create empty session description.
     */
    private SessionDescription() {
        this.protocolVersion = "0";
        this.origin = null;
        this.sessionName = "No Name";
        this.sessionDescription = "No Description";
        this.uri = null;
        this.phones = new ArrayList<String>();
        this.emails = new ArrayList<String>();
        this.connectionData = null;
        this.bandwidth = new HashMap<String, Long>();
        this.times = new ArrayList<TimeDescription>();
        this.timeZoneAdjustment = null;
        this.key = null;
        this.attributes = new HashMap<String, Attribute>();
        this.mediaDescriptions = new ArrayList<MediaDescription>();
    }
    
    /**
     * Create session description and set its origin.
     * 
     * @param origin the origin of the session description
     */
    public SessionDescription(InetAddress origin) {
        this();
        if (origin != null)
            this.origin = new Origin(origin);
    }

    /**
     * Get list of email addresses. The list is empty by default.
     * 
     * @return list of email addresses
     */
    public List<String> getEmails() {
        return emails;
    }

    /**
     * Get list of media descriptions. The list is empty by default.
     * 
     * @return list of media descriptions
     */
    public List<MediaDescription> getMediaDescriptions() {
        return mediaDescriptions;
    }

    /**
     * Get list of phone numbers. The list is empty by default.
     * 
     * @return list of phone numbers
     */
    public List<String> getPhones() {
        return phones;
    }

    /**
     * Get list of the session time description fields. The list is empty by 
     * default.
     * 
     * @return list of the session time descriptions
     */
    public List<TimeDescription> getTimes() {
        return times;
    }
    
    /**
     * Add bandwidth field.
     * 
     * @param name
     * @param value 
     */
    public void addBandwidth(String name, long value) {
        bandwidth.put(name, value);
    }
    
    /**
     * Remove bandwidth field.
     * 
     * @param name 
     */
    public void removeBandwidth(String name) {
        bandwidth.remove(name);
    }
    
    /**
     * Get bandwidth field value.
     * 
     * @param name
     * @return value of the specified bandwidth or null if there is no such 
     * bandwidth
     */
    public Long getBandwidth(String name) {
        return bandwidth.get(name);
    }
    
    /**
     * Add attribute field.
     * 
     * @param attribute 
     */
    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getName(), attribute);
    }
    
    /**
     * Remove attribute field.
     * 
     * @param name 
     */
    public void removeAttribute(String name) {
        attributes.remove(name);
    }
    
    /**
     * Get attribute field.
     * 
     * @param name
     * @return specified attribute or null if there is no such attribute
     */
    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Get connection data field. It is null by default.
     * 
     * @return connection data or null if the connection field was not set
     */
    public Connection getConnectionData() {
        return connectionData;
    }

    /**
     * Set connection data field.
     * 
     * @param connectionData connection data
     */
    public void setConnectionData(Connection connectionData) {
        this.connectionData = connectionData;
    }

    /**
     * Get key field. It is null by default.
     * 
     * @return key or null if the key was not set
     */
    public Key getKey() {
        return key;
    }

    /**
     * Set key field.
     * 
     * @param key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Get origin field. It is null by default if it is not set by the 
     * constructor.
     * 
     * @return origin or null if the origin was not set
     */
    public Origin getOrigin() {
        return origin;
    }

    /**
     * Set origin field.
     * 
     * @param origin 
     */
    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    /**
     * Get version of the SDP.
     * 
     * @return version of the SDP
     */
    public String getProtocolVersion() {
        return protocolVersion;
    }

    /**
     * Set version of the SDP.
     * 
     * @param protocolVersion 
     */
    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    /**
     * Get session description text.
     * 
     * @return session description text
     */
    public String getSessionDescription() {
        return sessionDescription;
    }

    /**
     * Set session description text.
     * 
     * @param sessionDescription 
     */
    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    /**
     * Get session name.
     * 
     * @return session name
     */
    public String getSessionName() {
        return sessionName;
    }

    /**
     * Set session name.
     * 
     * @param sessionName 
     */
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    /**
     * Get time zone adjustment field. It is null by default.
     * 
     * @return time zone adjustment field or null if it's not set
     */
    public TimeZoneAdjustment getTimeZoneAdjustment() {
        return timeZoneAdjustment;
    }

    /**
     * Set time zone adjustment field.
     * 
     * @param timeZoneAdjustment 
     */
    public void setTimeZoneAdjustment(TimeZoneAdjustment timeZoneAdjustment) {
        this.timeZoneAdjustment = timeZoneAdjustment;
    }

    /**
     * Get URI to additional information about this session. It is null by 
     * default.
     * 
     * @return URI
     */
    public String getUri() {
        return uri;
    }

    /**
     * Set URI to additional information about this session.
     * 
     * @param uri 
     */
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    /**
     * Transform this session description to its text representation.
     * 
     * NOTE:
     * All new lines are represented as CRLF because of the requirement of
     * the protocol.
     * 
     * @return text representation of this session description
     */
    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder();
        bldr.append("v=").append(protocolVersion).append("\r\n");
        bldr.append(origin);
        bldr.append("s=").append(sessionName).append("\r\n");
        if (sessionDescription != null)
            bldr.append("i=").append(sessionDescription).append("\r\n");
        if (uri != null)
            bldr.append("u=").append(uri).append("\r\n");
        for (String phone : phones)
            bldr.append("p=").append(phone).append("\r\n");
        for (String email : emails)
            bldr.append("e=").append(email).append("\r\n");
        if (connectionData != null)
            bldr.append(connectionData);
        for (String bw : bandwidth.keySet())
            bldr.append("b=").append(bw).append(":").append(bandwidth.get(bw)).append("\r\n");
        if (times.isEmpty())
            bldr.append(new TimeDescription().toString());
        else {
            for (TimeDescription time : times)
                bldr.append(time.toString());
        }
        if (timeZoneAdjustment != null)
            bldr.append(timeZoneAdjustment);
        if (key != null)
            bldr.append(key);
        for (Attribute attr : attributes.values())
            bldr.append(attr);
        for (MediaDescription md : mediaDescriptions)
            bldr.append(md);
        
        return bldr.toString();
    }

    /**
     * Clone this session description.
     * 
     * @return cloned instance
     */
    @Override
    public SessionDescription clone() {
        SessionDescription result = new SessionDescription();
        result.setProtocolVersion(protocolVersion);
        if (origin != null)
            result.setOrigin(origin.clone());
        result.setSessionName(sessionName);
        result.setSessionDescription(sessionDescription);
        result.setUri(uri);
        result.getPhones().addAll(phones);
        result.getEmails().addAll(emails);
        if (connectionData != null)
            result.setConnectionData(connectionData.clone());
        result.bandwidth.putAll(bandwidth);
        for (TimeDescription td : times)
            result.getTimes().add(td.clone());
        if (timeZoneAdjustment != null)
            result.setTimeZoneAdjustment(timeZoneAdjustment.clone());
        if (key != null)
            result.setKey(key.clone());
        for (Attribute attr : attributes.values())
            result.addAttribute(attr.clone());
        for (MediaDescription md : mediaDescriptions)
            result.getMediaDescriptions().add(md.clone());
        
        return result;
    }
    
    /**
     * Parse session description from its text representation.
     * 
     * @param sdp text representation of an SDP
     * @return parsed session description
     * @throws ParseException if the given text is not a valid SDP
     */
    public static SessionDescription parse(String sdp) throws ParseException {
        String[] lines = sdp.split("\r\n");
        int i = 0;
        SessionDescription result = new SessionDescription();
        
        try {
            for (; i < lines.length; i++) {
                if (lines[i].startsWith("v="))
                    result.setProtocolVersion(lines[i].substring(2));
                else if (lines[i].startsWith("o="))
                    result.setOrigin(Origin.parse(lines[i]));
                else if (lines[i].startsWith("s="))
                    result.setSessionName(lines[i].substring(2));
                else if (lines[i].startsWith("i="))
                    result.setSessionDescription(lines[i].substring(2));
                else if (lines[i].startsWith("u="))
                    result.setUri(lines[i].substring(2));
                else if (lines[i].startsWith("p="))
                    result.getPhones().add(lines[i].substring(2));
                else if (lines[i].startsWith("e="))
                    result.getEmails().add(lines[i].substring(2));
                else if (lines[i].startsWith("c="))
                    result.setConnectionData(Connection.parse(lines[i]));
                else if (lines[i].startsWith("b=")) {
                    if (!lines[i].matches("b=[^:\\s]+:.*"))
                        throw new ParseException("not a valid bandwidth record", i);
                    String[] bw = lines[i].substring(2).split(":");
                    result.addBandwidth(bw[0], Long.parseLong(bw[1]));
                } else if (lines[i].startsWith("t="))
                    i += TimeDescription.parse(result, lines, i) - 1;
                else if (lines[i].startsWith("z="))
                    result.setTimeZoneAdjustment(TimeZoneAdjustment.parse(lines[i]));
                else if (lines[i].startsWith("k="))
                    result.setKey(Key.parse(lines[i]));
                else if (lines[i].startsWith("a="))
                    result.addAttribute(Attribute.parse(lines[i]));
                else if (lines[i].startsWith("m="))
                    i += MediaDescription.parse(result, lines, i) - 1;
            }
        } catch (Exception ex) {
            throw (ParseException) new ParseException("not a valid SDP format", i).initCause(ex);
        }
        
        return result;
    }
    
}

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Origin field parser/builder.
 * 
 * @author Ondrej Perutka
 */
public class Origin implements Cloneable {
    
    private static final Pattern parsePattern = Pattern.compile("^o=([\\S]+)[ \\t]+([0-9]+)[ \\t]+([0-9]+)[ \\t]+([\\S]+)[ \\t]+([\\S]+)[ \\t]+([\\S]+)$");
    
    public static final String NETWORK_TYPE_INTERNET = "IN";
    
    private String username;
    private long sessionId;
    private long version;
    private Connection connectionData;

    private Origin(String username, long sessionId, long version) {
        this.username = username;
        this.sessionId = sessionId;
        this.version = version;
        this.connectionData = null;
    }
    
    /**
     * Create a new origin field and set the user name, session ID, origin
     * version and address.
     * 
     * @param username
     * @param sessionId
     * @param version 
     */
    public Origin(String username, long sessionId, long version, InetAddress address) {
        this(username, sessionId, version);
        if (address == null)
            throw new NullPointerException("given address cannot be null");
        this.connectionData = new Connection(address);
    }

    /**
     * Create a new origin field and set its address.
     * 
     * @param address 
     */
    public Origin(InetAddress address) {
        this("-", TimeDescription.getNtpTimestamp(), TimeDescription.getNtpTimestamp(), address);
    }

    /**
     * Get address.
     * 
     * @return address
     */
    public InetAddress getAddress() {
        return connectionData.getAddress();
    }
    
    /**
     * Get address type. The returned value depends on the type of the 
     * used address. This method returns "IP6" in case the address is 
     * an IPv6 address otherwise this method returns "IP4".
     * 
     * @return type of the used address
     */
    public String getAddressType() {
        return connectionData.getAddressType();
    }

    /**
     * Set address.
     * 
     * @param address 
     */
    public void setAddress(InetAddress address) {
        connectionData.setAddress(address);
    }

    /**
     * Get network type. By default it is "IN" (Internet).
     * 
     * @return network type
     */
    public String getNetworkType() {
        return connectionData.getNetworkType();
    }

    /**
     * Set network type.
     * 
     * @param networkType 
     */
    public void setNetworkType(String networkType) {
        this.connectionData.setNetworkType(networkType);
    }

    /**
     * Get session ID.
     * 
     * @return session ID
     */
    public long getSessionId() {
        return sessionId;
    }

    /**
     * Set session ID.
     * 
     * @param sessionId 
     */
    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Get user name. It is "-" by default.
     * @return 
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set user name.
     * 
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get version.
     * 
     * @return version
     */
    public long getVersion() {
        return version;
    }

    /**
     * Set version.
     * 
     * @param version 
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * Create text representation of this field.
     * 
     * @return text representation of this field
     */
    @Override
    public String toString() {
        return "o=" + username + " " +
                      sessionId + " " +
                      version + " " +
                      connectionData.getNetworkType() + " " +
                      connectionData.getAddressType() + " " +
                      connectionData.getAddress().getHostAddress() + "\r\n";
    }

    /**
     * Clone this field.
     * 
     * @return cloned instance
     */
    @Override
    public Origin clone() {
        Origin result = new Origin(username, sessionId, version);
        result.connectionData = connectionData.clone();
        
        return result;
    }
    
    /**
     * Parse a text representation of the origin field.
     * 
     * @param line a text representation
     * @return origin field
     * @throws ParseException if the given line is not a valid origin field
     */
    public static Origin parse(String line) throws ParseException {
        Matcher m = parsePattern.matcher(line);
        if (!m.find())
            throw new ParseException("not a valid origin record", 0);
        
        Origin result = null;
        
        try {
            long sessionId = Long.parseLong(m.group(2));
            long version = Long.parseLong(m.group(3));
            InetAddress addr = InetAddress.getByName(m.group(6));
            result = new Origin(m.group(1), sessionId, version, addr);
        } catch (Exception ex) {
            throw (ParseException) new ParseException("not a valid origin record", 0).initCause(ex);
        }
        
        result.setNetworkType(m.group(4));
        return result;
    }
    
}

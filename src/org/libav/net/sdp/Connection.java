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

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Connection field parser/builder.
 * 
 * @author Ondrej Perutka
 */
public class Connection implements Cloneable {
    
    private static final Pattern parsePattern = Pattern.compile("^c=([\\S]+)[ \\t]+([\\S]+)[ \\t]+([^\\s/]+)(/([0-9]+)(/([0-9]+))?)?$");
    
    public static final String NETWORK_TYPE_INTERNET = "IN";
    
    private String networkType;
    private InetAddress address;
    private int ttl;
    private int numOfAddresses;

    /**
     * Create a new connection field and set its address.
     * @param address 
     */
    public Connection(InetAddress address) {
        this.address = address;
        this.networkType = NETWORK_TYPE_INTERNET;
        this.numOfAddresses = 1;
        this.ttl = 1;
    }

    /**
     * Get connection field address.
     * 
     * @return connection field address
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Set connection field address.
     * 
     * @param address 
     */
    public void setAddress(InetAddress address) {
        this.address = address;
    }

    /**
     * Get network type. It is "IN" (Internet) by default.
     * 
     * @return network type
     */
    public String getNetworkType() {
        return networkType;
    }

    /**
     * Set network type.
     * 
     * @param networkType 
     */
    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }
    
    /**
     * Get address type. The returned value depends on the type of the 
     * used address. This method returns "IP6" in case the address is 
     * an IPv6 address otherwise this method returns "IP4".
     * 
     * @return type of the used address
     */
    public String getAddressType() {
        if (address instanceof Inet6Address)
            return "IP6";
        
        return "IP4";
    }

    /**
     * Get number of addresses. (See SDP specification.)
     * 
     * @return number of addresses
     */
    public int getNumOfAddresses() {
        return numOfAddresses;
    }

    /**
     * Set number of addresses. (See SDP specification.)
     * 
     * @param numOfAddresses 
     */
    public void setNumOfAddresses(int numOfAddresses) {
        this.numOfAddresses = numOfAddresses;
    }

    /**
     * Get packet TTL.
     * 
     * @return packet TTL
     */
    public int getTtl() {
        return ttl;
    }

    /**
     * Set packet TTL. The TTL is valid only in case the used address is
     * a multicast address.
     * 
     * @param ttl 
     */
    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    /**
     * Create text representation of this field.
     * 
     * @return text representation of this field
     */
    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder("c=");
        bldr.append(networkType).append(" ");
        bldr.append(getAddressType()).append(" ");
        bldr.append(address.getHostAddress());
        if (address.isMulticastAddress()) {
            bldr.append("/").append(ttl);
            if (numOfAddresses > 1)
                bldr.append("/").append(numOfAddresses);
        }
        bldr.append("\r\n");
        
        return bldr.toString();
    }

    /**
     * Clone this field.
     * 
     * @return cloned instance
     */
    @Override
    public Connection clone() {
        Connection result = new Connection(address);
        result.setNetworkType(networkType);
        result.setNumOfAddresses(numOfAddresses);
        result.setTtl(ttl);
        
        return result;
    }
    
    /**
     * Parse a text representation of the connection field.
     * 
     * @param line a text representation
     * @return connection field
     * @throws ParseException if the given line is not a valid connection field
     */
    public static Connection parse(String line) throws ParseException {
        Matcher m = parsePattern.matcher(line);
        if (!m.find())
            throw new ParseException("not a valid connection record", 0);
        
        Connection result = null;
        String tmp;
        
        try {
            InetAddress addr = InetAddress.getByName(m.group(3));
            result = new Connection(addr);
            result.setNetworkType(m.group(1));
            tmp = m.group(5);
            if (tmp != null && tmp.length() > 0)
                result.setTtl(Integer.parseInt(tmp));
            tmp = m.group(7);
            if (tmp != null && tmp.length() > 0)
                result.setNumOfAddresses(Integer.parseInt(tmp));
        } catch (UnknownHostException ex) {
            throw (ParseException) new ParseException("not a valid connection record", 0).initCause(ex);
        }
        
        return result;
    }
    
}

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
import java.net.UnknownHostException;
import java.text.ParseException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for the Connection class.
 * 
 * @author Ondrej Perutka
 */
public class ConnectionTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("SDP connection parse test...");
        String[] cons = new String[] {
            "c=IN IP4 127.0.0.1/10/20",
            "c=IN IP4 127.0.0.1/10",
            "c=IN IP4 127.0.0.1",
            "c=IN\tIP4\t127.0.0.1",
            " c=IN IP4 127.0.0.1",
            "c =IN IP4 127.0.0.1",
            "c = IN IP4 127.0.0.1",
            "c= IN IP4 127.0.0.1",
            "c=IN 127.0.0.1",
            "c=IN IP4 127.0.0.1/",
            "c=IN IP4 127.0.0.1/10/",
        };
        
        int i = 0;
        Connection c = Connection.parse(cons[i++]);
        assertEquals("IN", c.getNetworkType());
        assertEquals("IP4", c.getAddressType());
        assertEquals("127.0.0.1", c.getAddress().getHostAddress());
        assertEquals(10, c.getTtl());
        assertEquals(20, c.getNumOfAddresses());
        
        c = Connection.parse(cons[i++]);
        assertEquals("IN", c.getNetworkType());
        assertEquals("IP4", c.getAddressType());
        assertEquals("127.0.0.1", c.getAddress().getHostAddress());
        assertEquals(10, c.getTtl());
        assertEquals(1, c.getNumOfAddresses());
        
        c = Connection.parse(cons[i++]);
        assertEquals("IN", c.getNetworkType());
        assertEquals("IP4", c.getAddressType());
        assertEquals("127.0.0.1", c.getAddress().getHostAddress());
        assertEquals(1, c.getTtl());
        assertEquals(1, c.getNumOfAddresses());
        
        c = Connection.parse(cons[i++]);
        assertEquals("IN", c.getNetworkType());
        assertEquals("IP4", c.getAddressType());
        assertEquals("127.0.0.1", c.getAddress().getHostAddress());
        assertEquals(1, c.getTtl());
        assertEquals(1, c.getNumOfAddresses());
        
        for (; i < cons.length; i++) {
            try {
                Connection.parse(cons[i]);
                fail("connection: \"" + cons[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() throws UnknownHostException {
        System.out.println("SDP connection toString test...");
        Connection c = new Connection(InetAddress.getByName("127.0.0.1"));
        assertEquals("c=IN IP4 127.0.0.1\r\n", c.toString());
    }
    
}

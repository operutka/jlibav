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
 *
 * @author Ondrej Perutka
 */
public class OriginTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("SDP origin parse test...");
        String[] origs = new String[] {
            "o=- 10 20 IN IP4 127.0.0.1",
            "o=-\t10\t20\tIN\tIP4\t127.0.0.1",
            " o=- 0 0 IN IP4 127.0.0.1",
            "o =- 0 0 IN IP4 127.0.0.1",
            "o = - 0 0 IN IP4 127.0.0.1",
            "o= - 0 0 IN IP4 127.0.0.1",
            "o=0 0 IN IP4 127.0.0.1",
            "o=- 0 IN IP4 127.0.0.1",
            "o=- 0 0 IP4 127.0.0.1",
            "o=- 0 0 IN 127.0.0.1",
            "o=- 0 0 IN IP4",
        };
        
        int i = 0;
        Origin o;
        
        for (; i < 2; i++) {
            o = Origin.parse(origs[i]);
            assertEquals("-", o.getUsername());
            assertEquals(10l, o.getSessionId());
            assertEquals(20l, o.getVersion());
            assertEquals("IN", o.getNetworkType());
            assertEquals("IP4", o.getAddressType());
            assertEquals("127.0.0.1", o.getAddress().getHostAddress());
        }
        
        for (; i < origs.length; i++) {
            try {
                Origin.parse(origs[i]);
                fail("origin: \"" + origs[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() throws UnknownHostException {
        System.out.println("SDP origin toString test...");
        Origin o = new Origin("-", 10, 20, InetAddress.getByName("127.0.0.1"));
        assertEquals("o=- 10 20 IN IP4 127.0.0.1\r\n", o.toString());
    }
    
}

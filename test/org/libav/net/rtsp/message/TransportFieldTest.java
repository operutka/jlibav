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
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class TransportFieldTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("RTSP message Transport field parse test...");
        String[] tfs = new String[] {
            "Transport:RTP/AVP",
            "Transport: RTP/AVP",
            "transport: RTP/AVP",
            
            "Transport:RTP/AVP/TCP;multicast;destination=127.0.0.1;interleaved=1;append;ttl=5;layers=1;port=5000;client_port=6000;server_port=7000;ssrc=100;mode=\"PLAY\"",
            "Transport:RTP/AVP/TCP;;destination=127.0.0.1;interleaved=1-5;;ttl=5;layers=1;port=5000-5001;client_port=6000-6001;server_port=7000-7001;ssrc=100;mode=\"PLAY\"",
            
            "Transport:RTP",
            "Transport:RT;P/AVP",
            "Transport:RTP/AVP; multicast"
        };
        
        int i = 0;
        
        TransportField tf;
        for (; i < 3; i++) {
            tf = (TransportField)RtspHeader.Field.parse(tfs[i]);
            assertEquals("RTP", tf.getProtocol());
            assertEquals("AVP", tf.getProfile());
            assertNull(tf.getLowerTransport());
            assertFalse(tf.getAppend());
            assertFalse(tf.getMulticast());
            assertNull(tf.getClientPortFrom());
            assertNull(tf.getClientPortTo());
            assertNull(tf.getDestination());
            assertNull(tf.getInterleavedFrom());
            assertNull(tf.getInterleavedTo());
            assertNull(tf.getLayers());
            assertNull(tf.getMode());
            assertNull(tf.getPortFrom());
            assertNull(tf.getPortTo());
            assertNull(tf.getServerPortFrom());
            assertNull(tf.getServerPortTo());
            assertNull(tf.getSsrc());
            assertNull(tf.getTtl());
        }
        
        tf = (TransportField)RtspHeader.Field.parse(tfs[i++]);
        assertEquals("RTP", tf.getProtocol());
        assertEquals("AVP", tf.getProfile());
        assertEquals("TCP", tf.getLowerTransport());
        assertTrue(tf.getAppend());
        assertTrue(tf.getMulticast());
        assertEquals(6000, (int)tf.getClientPortFrom());
        assertNull(tf.getClientPortTo());
        assertEquals("127.0.0.1", tf.getDestination());
        assertEquals(1, (int)tf.getInterleavedFrom());
        assertNull(tf.getInterleavedTo());
        assertEquals(1, (int)tf.getLayers());
        assertEquals("PLAY", tf.getMode());
        assertEquals(5000, (int)tf.getPortFrom());
        assertNull(tf.getPortTo());
        assertEquals(7000, (int)tf.getServerPortFrom());
        assertNull(tf.getServerPortTo());
        assertEquals("100", tf.getSsrc());
        assertEquals(5, (int)tf.getTtl());
        
        tf = (TransportField)RtspHeader.Field.parse(tfs[i++]);
        assertEquals("RTP", tf.getProtocol());
        assertEquals("AVP", tf.getProfile());
        assertEquals("TCP", tf.getLowerTransport());
        assertFalse(tf.getAppend());
        assertFalse(tf.getMulticast());
        assertEquals(6000, (int)tf.getClientPortFrom());
        assertEquals(6001, (int)tf.getClientPortTo());
        assertEquals("127.0.0.1", tf.getDestination());
        assertEquals(1, (int)tf.getInterleavedFrom());
        assertEquals(5, (int)tf.getInterleavedTo());
        assertEquals(1, (int)tf.getLayers());
        assertEquals("PLAY", tf.getMode());
        assertEquals(5000, (int)tf.getPortFrom());
        assertEquals(5001, (int)tf.getPortTo());
        assertEquals(7000, (int)tf.getServerPortFrom());
        assertEquals(7001, (int)tf.getServerPortTo());
        assertEquals("100", tf.getSsrc());
        assertEquals(5, (int)tf.getTtl());
        
        for (; i < tfs.length; i++) {
            try {
                RtspHeader.Field.parse(tfs[i]);
                fail("Transport field: \"" + tfs[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message Transport field toString test...");
        String tmp[] = new String[] { 
            "Transport:RTP/AVP/TCP;multicast;destination=127.0.0.1;interleaved=1;append;ttl=5;layers=1;port=5000;client_port=6000;server_port=7000;mode=\"PLAY\"",
            "Transport:RTP/AVP/TCP;unicast;destination=127.0.0.1;interleaved=1-5;client_port=6000-6001;server_port=7000-7001;ssrc=100;mode=\"PLAY\"",
            "Transport:RTP/AVP;unicast",
        };
        
        TransportField tf = new TransportField();
        TransportField[] tfs = new TransportField[] { tf, null, tf.clone() };
        
        tf.setProtocol("RTP");
        tf.setProfile("AVP");
        tf.setLowerTransport("TCP");
        tf.setAppend(true);
        tf.setMulticast(true);
        tf.setClientPortFrom(6000);
        tf.setClientPortTo(null);
        tf.setDestination("127.0.0.1");
        tf.setInterleavedFrom(1);
        tf.setInterleavedTo(null);
        tf.setLayers(1);
        tf.setMode("PLAY");
        tf.setPortFrom(5000);
        tf.setPortTo(null);
        tf.setServerPortFrom(7000);
        tf.setServerPortTo(null);
        tf.setSsrc("100");
        tf.setTtl(5);
        
        tf = tf.clone();
        tf.setAppend(false);
        tf.setMulticast(false);
        tf.setClientPortTo(6001);
        tf.setInterleavedTo(5);
        tf.setPortTo(5001);
        tf.setServerPortTo(7001);
        tfs[1] = tf;
        
        String tmp2;
        for (int i = 0; i < tmp.length; i++) {
            tmp2 = tfs[i].toString();
            assertEquals(tmp[i], tmp2);
        }
    }
    
}

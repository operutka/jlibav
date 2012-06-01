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
public class SessionDescriptionTest {
    
    @Test
    public void testParse() throws Exception {
        System.out.println("SDP session description parse test...");
        String[] sds = new String[] {
                  "v=0\r\n"
                + "o=- 10 20 IN IP4 127.0.0.1\r\n"
                + "s=No Name\r\n"
                + "i=No Description\r\n"
                + "u=rtsp://localhost:5544/pokus.sdp\r\n"
                + "e=realm@subdomain.dom\r\n"
                + "p=123456789\r\n"
                + "c=IN IP4 127.0.0.1\r\n"
                + "b=vs:800\r\n"
                + "b=as:120\r\n"
                + "t=1 2\r\n"
                + "r=3 4 5\r\n"
                + "z=1 2\r\n"
                + "k=m:key\r\n"
                + "a=attr1\r\n"
                + "a=attr2:val\r\n"
                + "m=video 8000 RTP/AVP 96\r\n"
                + "i=No Title\r\n"
                + "c=IN IP4 127.0.0.1\r\n"
                + "b=b1:800\r\n"
                + "b=b2:100\r\n"
                + "k=m:key\r\n"
                + "a=attr1\r\n"
                + "a=attr2:val\r\n"
                + "m=audio 8002 RTP/AVP 96\r\n"
                + "i=No Title\r\n"
                + "c=IN IP4 127.0.0.1\r\n"
                + "b=b1:120\r\n"
                + "b=b2:10\r\n"
                + "k=m:key\r\n"
                + "a=attr1\r\n"
                + "a=attr2:val\r\n",
                
                "c=this-should-cause-an-exception",
        };
        
        int i = 0;
        
        SessionDescription sd = SessionDescription.parse(sds[i++]);
        assertEquals("0", sd.getProtocolVersion());
        assertEquals("-", sd.getOrigin().getUsername());
        assertEquals(10l, sd.getOrigin().getSessionId());
        assertEquals(20l, sd.getOrigin().getVersion());
        assertEquals("IN", sd.getOrigin().getNetworkType());
        assertEquals("IP4", sd.getOrigin().getAddressType());
        assertEquals("127.0.0.1", sd.getOrigin().getAddress().getHostAddress());
        assertEquals("No Name", sd.getSessionName());
        assertEquals("No Description", sd.getSessionDescription());
        assertEquals("rtsp://localhost:5544/pokus.sdp", sd.getUri());
        assertArrayEquals(new String[] { "realm@subdomain.dom" }, sd.getEmails().toArray(new String[1]));
        assertArrayEquals(new String[] { "123456789" }, sd.getPhones().toArray(new String[1]));
        assertEquals("IN", sd.getConnectionData().getNetworkType());
        assertEquals("IP4", sd.getConnectionData().getAddressType());
        assertEquals("127.0.0.1", sd.getConnectionData().getAddress().getHostAddress());
        assertEquals(800l, (long)sd.getBandwidth("vs"));
        assertEquals(120l, (long)sd.getBandwidth("as"));
        assertEquals(1, sd.getTimes().size());
        TimeDescription td = sd.getTimes().get(0);
        assertEquals(1l, td.getStartTime());
        assertEquals(2l, td.getStopTime());
        assertTrue(td.isPeriodic());
        assertEquals(3l, td.getRepeatInterval());
        assertEquals(4l, td.getActiveDuration());
        assertArrayEquals(new Long[] { 5l }, td.getOffsets().toArray(new Long[1]));
        assertEquals(1, sd.getTimeZoneAdjustment().getAdjustments().size());
        TimeZoneAdjustment.Adjustment tza = sd.getTimeZoneAdjustment().getAdjustments().get(0);
        assertEquals(1l, tza.getAdjustmentTime());
        assertEquals(2l, tza.getOffest());
        assertEquals("m", sd.getKey().getMethod());
        assertEquals("key", sd.getKey().getKey());
        assertNull(sd.getAttribute("attr1").getValue());
        assertEquals("val", sd.getAttribute("attr2").getValue());
        assertEquals(2, sd.getMediaDescriptions().size());
        MediaDescription md = sd.getMediaDescriptions().get(0);
        assertEquals("video", md.getMedia());
        assertEquals(8000, md.getPort());
        assertEquals("RTP/AVP", md.getTransport());
        assertArrayEquals(new String[] { "96" }, md.getFormats().toArray(new String[1]));
        assertEquals("No Title", md.getMediaTitle());
        assertEquals("IN", md.getConnection().getNetworkType());
        assertEquals("IP4", md.getConnection().getAddressType());
        assertEquals("127.0.0.1", md.getConnection().getAddress().getHostAddress());
        assertEquals(800l, (long)md.getBandwidth("b1"));
        assertEquals(100l, (long)md.getBandwidth("b2"));
        assertEquals("m", md.getKey().getMethod());
        assertEquals("key", md.getKey().getKey());
        assertNull(md.getAttribute("attr1").getValue());
        assertEquals("val", md.getAttribute("attr2").getValue());
        md = sd.getMediaDescriptions().get(1);
        assertEquals("audio", md.getMedia());
        assertEquals(8002, md.getPort());
        assertEquals("RTP/AVP", md.getTransport());
        assertArrayEquals(new String[] { "96" }, md.getFormats().toArray(new String[1]));
        assertEquals("No Title", md.getMediaTitle());
        assertEquals("IN", md.getConnection().getNetworkType());
        assertEquals("IP4", md.getConnection().getAddressType());
        assertEquals("127.0.0.1", md.getConnection().getAddress().getHostAddress());
        assertEquals(120l, (long)md.getBandwidth("b1"));
        assertEquals(10l, (long)md.getBandwidth("b2"));
        assertEquals("m", md.getKey().getMethod());
        assertEquals("key", md.getKey().getKey());
        assertNull(md.getAttribute("attr1").getValue());
        assertEquals("val", md.getAttribute("attr2").getValue());
        
        for (; i < sds.length; i++) {
            try {
                SessionDescription.parse(sds[i]);
                fail("session description: \"" + sds[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() throws UnknownHostException {
        System.out.println("SDP session description toString test...");
        String exp = "v=0\r\n"
                + "o=- 10 20 IN IP4 127.0.0.1\r\n"
                + "s=No Name\r\n"
                + "i=No Description\r\n"
                + "u=rtsp://localhost:5544/pokus.sdp\r\n"
                + "p=123456789\r\n"
                + "e=realm@subdomain.dom\r\n"
                + "c=IN IP4 127.0.0.1\r\n"
                + "t=1 2\r\n"
                + "r=3 4 5\r\n"
                + "z=1 2\r\n"
                + "k=m:key\r\n"
                + "a=attr2:val\r\n"
                + "m=video 8000 RTP/AVP 96\r\n"
                + "i=No Title\r\n"
                + "c=IN IP4 127.0.0.1\r\n"
                + "b=b1:800\r\n"
                + "k=m:key\r\n"
                + "a=attr1\r\n"
                + "m=audio 8002 RTP/AVP 96\r\n"
                + "i=No Title\r\n"
                + "c=IN IP4 127.0.0.1\r\n"
                + "b=b1:120\r\n"
                + "k=m:key\r\n"
                + "a=attr2:val\r\n";
        
        SessionDescription sd = new SessionDescription(InetAddress.getByName("127.0.0.1"));
        sd.getOrigin().setSessionId(10);
        sd.getOrigin().setVersion(20);
        sd.setSessionName("No Name");
        sd.setSessionDescription("No Description");
        sd.setUri("rtsp://localhost:5544/pokus.sdp");
        sd.getPhones().add("123456789");
        sd.getEmails().add("realm@subdomain.dom");
        sd.setConnectionData(new Connection(InetAddress.getByName("127.0.0.1")));
        TimeDescription td = new TimeDescription(1, 2);
        td.setPeriodic(true);
        td.setRepeatInterval(3);
        td.setActiveDuration(4);
        td.getOffsets().add(5l);
        sd.getTimes().add(td);
        sd.setTimeZoneAdjustment(new TimeZoneAdjustment());
        sd.getTimeZoneAdjustment().getAdjustments().add(new TimeZoneAdjustment.Adjustment(1, 2));
        sd.setKey(new Key("m", "key"));
        sd.addAttribute(new Attribute("attr2", "val"));
        MediaDescription md = new MediaDescription("video", 8000, "RTP/AVP", "96");
        md.setMediaTitle("No Title");
        md.setConnection(new Connection(InetAddress.getByName("127.0.0.1")));
        md.addBandwidth("b1", 800);
        md.setKey(new Key("m", "key"));
        md.addAttribute(new Attribute("attr1"));
        sd.getMediaDescriptions().add(md);
        md = md.clone();
        md.setMedia("audio");
        md.setPort(8002);
        md.addBandwidth("b1", 120);
        md.removeAttribute("attr1");
        md.addAttribute(new Attribute("attr2", "val"));
        sd.getMediaDescriptions().add(md);
        
        assertEquals(exp, sd.toString());
    }
    
}

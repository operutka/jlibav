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
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class MediaDescriptionTest {
    
    @Test
    public void testParse() throws Exception {
        System.out.println("SDP media description parse test...");
        String[][] mds = new String[][] {
            { "a=param",
              "a=param",
              "a=param",
              "m=video 8000/5 RTP/AVP 96",
              "i=No Title",
              "b=sb:800",
              "m=audio 0 RTP/AVP 96",
              "i=No Title" },
            { "m=video 0 RTP/AVP 96" },
            { "m=video 0 RTP/AVP 96 98" },
            
            { "m=audio 0 RTP/AVP 96",
              "b= :0" },
            { " m=audio 0 RTP/AVP 96" },
            { "m =audio 0 RTP/AVP 96" },
            { "m = audio 0 RTP/AVP 96" },
            { "m= audio 0 RTP/AVP 96" },
            { "m=0 RTP/AVP 96" },
            { "m=audio RTP/AVP 96" },
            { "m=audio 0/ RTP/AVP 96" },
            { "m=audio /0 RTP/AVP 96" },
        };
        
        int i = 0;
        SessionDescription sd = new SessionDescription(InetAddress.getByName("127.0.0.1"));
        List<MediaDescription> lmd = sd.getMediaDescriptions();
        int ul = MediaDescription.parse(sd, mds[i++], 3);
        MediaDescription md = lmd.get(lmd.size() - 1);
        
        assertEquals(3, ul);
        assertEquals("video", md.getMedia());
        assertEquals(8000, md.getPort());
        assertEquals(5, md.getNumOfPorts());
        assertEquals("RTP/AVP", md.getTransport());
        assertArrayEquals(new String[] { "96" }, md.getFormats().toArray(new String[1]));
        assertEquals("No Title", md.getMediaTitle());
        assertEquals(800l, (long)md.getBandwidth("sb"));
        
        for (; i < 3; i++)
            MediaDescription.parse(sd, mds[i], 0);
        
        for (; i < mds.length; i++) {
            try {
                MediaDescription.parse(sd, mds[i], 0);
                fail("media description: \"" + Arrays.toString(mds[i]) + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() throws UnknownHostException {
        System.out.println("SDP media description toString test...");
        MediaDescription md = new MediaDescription(MediaDescription.MEDIA_VIDEO, 8000, "RTP/AVP", "96");
        md.setMediaTitle("No Title");
        md.setNumOfPorts(5);
        md.getFormats().add("98");
        
        Connection c = new Connection(InetAddress.getByName("127.0.0.1"));
        md.setConnection(c);
        
        md.addBandwidth("sb", 800);
        
        Key k = new Key("method", "key");
        md.setKey(k);
        
        Attribute a1 = new Attribute("attr1", "val");
        md.addAttribute(a1);
        
        String ref = "m=video 8000/5 RTP/AVP 96 98\r\n"
                + "i=No Title\r\n"
                + c.toString()
                + "b=sb:800\r\n"
                + k.toString()
                + a1.toString();
        
        String tmp = md.toString();
        
        assertEquals(ref, tmp);
    }
    
}

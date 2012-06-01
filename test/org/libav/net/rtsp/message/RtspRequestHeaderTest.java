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

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;
import org.libav.net.rtsp.RtspException;

/**
 *
 * @author Ondrej Perutka
 */
public class RtspRequestHeaderTest {
    
    @Test
    public void testParse() throws Exception {
        System.out.println("RTSP message request header parse test...");
        String[][] lines = new String[][] {
            { "PLAY /test.sdp RTSP/1.0",
              "CSeq: 1",
              "Transport:RTP/AVP;",
              " unicast;client_port=5000" },
            
            { "test" },
            { "OPTIONS * RTP/1.0" },
            { "OPTIONS * RTSP/2.0" },
        };
        
        int i = 0;
        
        RtspRequestHeader rh = RtspRequestHeader.parse(lines[i++]);
        assertEquals("PLAY", rh.getMethod());
        assertEquals("/test.sdp", rh.getUri());
        assertEquals(1, rh.getCSeq());
        TransportField tf = (TransportField)rh.getField("transport");
        assertEquals(5000, (int)tf.getClientPortFrom());
        
        for (; i < lines.length; i++) {
            try {
                RtspRequestHeader.parse(lines[i]);
                fail("request header: \"" + Arrays.toString(lines[i]) + "\" parsed without an exception");
            } catch (ParseException ex) {
            } catch (RtspException ex) { }
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message request header toString test...");
        RtspRequestHeader rh = new RtspRequestHeader("OPTIONS", "*", 1);
        assertEquals("OPTIONS * RTSP/1.0\r\nCSeq:1\r\n\r\n", rh.toString());
    }
    
    @Test
    public void testGetBytes() {
        System.out.println("RTSP message request header getBytes test...");
        RtspRequestHeader rh = new RtspRequestHeader("OPTIONS", "*", 1);
        byte[] tmp = "OPTIONS * RTSP/1.0\r\nCSeq:1\r\n\r\n".getBytes(Charset.forName("UTF-8"));
        assertArrayEquals(tmp, rh.getBytes());
    }
    
}

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
public class RtspResponseHeaderTest {
    
    @Test
    public void testParse() throws Exception {
        System.out.println("RTSP message response header parse test...");
        String[][] lines = new String[][] {
            { "RTSP/1.0 200 OK",
              "CSeq: 1",
              "Transport:RTP/AVP;",
              " unicast;client_port=5000" },
            
            { "test" },
            { "RTP/1.0 200 OK" },
            { "RTSP/2.0 200 OK" },
        };
        
        int i = 0;
        
        RtspResponseHeader rh = RtspResponseHeader.parse(lines[i++]);
        assertEquals(200, rh.getCode());
        assertEquals("OK", rh.getMessage());
        assertEquals(1, rh.getCSeq());
        TransportField tf = (TransportField)rh.getField("transport");
        assertEquals(5000, (int)tf.getClientPortFrom());
        
        for (; i < lines.length; i++) {
            try {
                RtspResponseHeader.parse(lines[i]);
                fail("response header: \"" + Arrays.toString(lines[i]) + "\" parsed without an exception");
            } catch (ParseException ex) {
            } catch (RtspException ex) { }
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message response header toString test...");
        RtspResponseHeader rh = new RtspResponseHeader(200, "OK", 1);
        assertEquals("RTSP/1.0 200 OK\r\nCSeq:1\r\n\r\n", rh.toString());
    }
    
    @Test
    public void testGetBytes() {
        System.out.println("RTSP message response header getBytes test...");
        RtspResponseHeader rh = new RtspResponseHeader(200, "OK", 1);
        byte[] tmp = "RTSP/1.0 200 OK\r\nCSeq:1\r\n\r\n".getBytes(Charset.forName("UTF-8"));
        assertArrayEquals(tmp, rh.getBytes());
    }
    
}

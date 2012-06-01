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
import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class RtspMessageTest {
    
    @Test
    public void testGetRawMessage() {
        RtspMessage m = new RtspMessage("OPTIONS", "*", 1);
        m.setBodyText("body", Charset.forName("UTF-8"));
        
        byte[][] exp = new byte[][] {
            "OPTIONS * RTSP/1.0\r\nCSeq:1\r\nContent-Length:4\r\n\r\nbody".getBytes(Charset.forName("UTF-8")),
            "OPTIONS * RTSP/1.0\r\nContent-Length:4\r\nCSeq:1\r\n\r\nbody".getBytes(Charset.forName("UTF-8")),
        };
        
        byte[] tmp = m.getRawMessage();
        if (!Arrays.equals(exp[0], tmp) && !Arrays.equals(exp[1], tmp))
            fail("raw message is not equal to the expected one");
        
        m = new RtspMessage(200, "OK", 1);
        assertArrayEquals("RTSP/1.0 200 OK\r\nCSeq:1\r\n\r\n".getBytes(Charset.forName("UTF-8")), m.getRawMessage());
    }
    
}

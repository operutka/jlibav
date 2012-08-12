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
public class SessionFieldTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("RTSP message Session field parse test...");
        String[] sfs = new String[] {
            "Session:abcd123;timeout=100",
            "Session: abcd123;timeout=100",
            "session: abcd123;timeout=100",
            "Session: abcd123",
            "Session: abcd123,timeout=100",
            
            "Session: abcd123;tout=100",
            "Session:"
        };
        
        int i = 0;
        
        SessionField sf;
        for (; i < 3; i++) {
            sf = (SessionField)RtspHeader.Field.parse(sfs[i]);
            assertEquals("abcd123", sf.getId());
            assertNotNull(sf.getTimeout());
            assertEquals(100l, (long)sf.getTimeout());
        }
        
        sf = (SessionField)RtspHeader.Field.parse(sfs[i++]);
        assertNull(sf.getTimeout());
        
        sf = (SessionField)RtspHeader.Field.parse(sfs[i++]);
        assertEquals("abcd123,timeout=100", sf.getId());
        assertNull(sf.getTimeout());
        
        for (; i < sfs.length; i++) {
            try {
                RtspHeader.Field.parse(sfs[i]);
                fail("Session field: \"" + sfs[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message Session field toString test...");
        SessionField sf = new SessionField("abcd123");
        assertEquals("Session:abcd123", sf.toString());
        
        sf.clone();
        sf.setTimeout(10l);
        assertEquals("Session:abcd123;timeout=10", sf.toString());
    }
    
}

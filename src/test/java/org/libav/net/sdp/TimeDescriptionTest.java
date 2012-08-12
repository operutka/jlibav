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
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class TimeDescriptionTest {
    
    @Test
    public void testParse() throws Exception {
        System.out.println("SDP time description parse test...");
        String[][] tds = new String[][] {
            { "t=1 2" },
            { "a=attr",
              "t=1 2",
              "r=3 4 5",
              "z=1 2" },
            { "t=1 2",
              "r=3 4 5 6" },
            
            { "z=1 2" },
            { "t=1" },
            { " t=1 2" },
            { "t =1 2" },
            { "t= 1 2" },
            { "t = 1 2" },
            
            { "t=1 2",
              "r=3 4" },
            { "t=1 2",
              " r=3 4 5" },
            { "t=1 2",
              "r =3 4 5" },
            { "t=1 2",
              "r= 3 4 5" },
            { "t=1 2",
              "r = 3 4 5" },
        };
        
        int i = 0;
        
        SessionDescription sd = new SessionDescription(InetAddress.getByName("127.0.0.1"));
        List<TimeDescription> ltd = sd.getTimes();
        
        int cl = TimeDescription.parse(sd, tds[i++], 0);
        TimeDescription td = ltd.get(ltd.size() - 1);
        assertEquals(1, cl);
        assertEquals(1l, td.getStartTime());
        assertEquals(2l, td.getStopTime());
        assertFalse(td.isPeriodic());
        
        cl = TimeDescription.parse(sd, tds[i++], 1);
        td = ltd.get(ltd.size() - 1);
        assertEquals(2, cl);
        assertEquals(1l, td.getStartTime());
        assertEquals(2l, td.getStopTime());
        assertTrue(td.isPeriodic());
        assertEquals(3l, td.getRepeatInterval());
        assertEquals(4l, td.getActiveDuration());
        assertArrayEquals(new Long[] { 5l }, td.getOffsets().toArray(new Long[1]));
        
        TimeDescription.parse(sd, tds[i++], 0);
        td = ltd.get(ltd.size() - 1);
        assertArrayEquals(new Long[] { 5l, 6l }, td.getOffsets().toArray(new Long[2]));
        
        for (; i < 9; i++) {
            try {
                TimeDescription.parse(sd, tds[i], 0);
                fail("time description: \"" + Arrays.toString(tds[i]) + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
        
        for (; i < tds.length; i++) {
            cl = TimeDescription.parse(sd, tds[i], 0);
            td = ltd.get(ltd.size() - 1);
            assertEquals(1, cl);
            assertFalse(td.isPeriodic());
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("SDP time description toString test...");
        TimeDescription td = new TimeDescription(1, 2);
        assertEquals("t=1 2\r\n", td.toString());
        
        td.setPeriodic(true);
        td.setRepeatInterval(3);
        td.setActiveDuration(4);
        assertEquals("t=1 2\r\nr=3 4 0\r\n", td.toString());
        
        td.getOffsets().add(5l);
        assertEquals("t=1 2\r\nr=3 4 5\r\n", td.toString());
        
        td.getOffsets().add(6l);
        assertEquals("t=1 2\r\nr=3 4 5 6\r\n", td.toString());
    }
    
}

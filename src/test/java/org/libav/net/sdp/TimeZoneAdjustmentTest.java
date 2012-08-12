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

import java.text.ParseException;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class TimeZoneAdjustmentTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("SDP time zone adjustment parse test...");
        String[] tzas = new String[] {
            "z=1 2",
            "z=1 2 3 4",
            "z=1\t2\t3\t4",
            " z=1 2",
            "z =1 2",
            "z = 1 2",
            "z= 1 2",
        };
        
        int i = 0;
        
        TimeZoneAdjustment tza = TimeZoneAdjustment.parse(tzas[i++]);
        List<TimeZoneAdjustment.Adjustment> adjs = tza.getAdjustments();
        assertEquals(1, adjs.size());
        assertEquals(1l, adjs.get(0).getAdjustmentTime());
        assertEquals(2l, adjs.get(0).getOffest());
        
        for (; i < 3; i++) {
            tza = TimeZoneAdjustment.parse(tzas[i]);
            adjs = tza.getAdjustments();
            assertEquals(2, adjs.size());
            assertEquals(1l, adjs.get(0).getAdjustmentTime());
            assertEquals(2l, adjs.get(0).getOffest());
            assertEquals(3l, adjs.get(1).getAdjustmentTime());
            assertEquals(4l, adjs.get(1).getOffest());
        }
        
        for (; i < tzas.length; i++) {
            try {
                TimeZoneAdjustment.parse(tzas[i]);
                fail("time zone adjustment: \"" + tzas[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("SDP time zone adjustment toString test...");
        TimeZoneAdjustment tza = new TimeZoneAdjustment();
        tza.getAdjustments().add(new TimeZoneAdjustment.Adjustment(1, 2));
        assertEquals("z=1 2\r\n", tza.toString());
        
        tza.getAdjustments().add(new TimeZoneAdjustment.Adjustment(3, 4));
        assertEquals("z=1 2 3 4\r\n", tza.toString());
    }
    
}

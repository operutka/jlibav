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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class DateFieldTest {
    
    private Date refDate;
    
    @Before
    public void init() {
        Calendar c = new GregorianCalendar(2012, 1, 25, 16, 24, 25);
        refDate = c.getTime();
    }
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("RTSP message Date field parse test...");
        String[] dfs = new String[] {
            "Date:Sat, 25 Feb 2012 16:24:25 GMT",
            "Date: Sat, 25 Feb 2012 16:24:25 GMT",
            "Date: Sat, 25 Feb 2012 16:24:25 GMT",
            
            "Date: test",
        };
        
        int i = 0;
        DateField df;
        for (; i < 3; i++) {
            df = (DateField)RtspHeader.Field.parse(dfs[i]);
            assertEquals(refDate, df.getDate());
        }
        
        for (; i < dfs.length; i++) {
            try {
                RtspHeader.Field.parse(dfs[i]);
                fail("Date field: \"" + dfs[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message Date field toString test...");
        DateField df = new DateField(refDate);
        assertEquals("Date:Sat, 25 Feb 2012 16:24:25 GMT", df.toString());
    }
    
}

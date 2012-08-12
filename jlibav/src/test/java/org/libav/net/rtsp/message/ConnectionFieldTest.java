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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class ConnectionFieldTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("RTSP message Connection field parse test...");
        String[] cfs = new String[] {
            "Connection:p1,p2",
            "Connection: p1,p2",
            "connection:p1,p2",
        };
        
        int i = 0;
        ConnectionField cf;
        for (; i < cfs.length; i++) {
            cf = (ConnectionField)RtspHeader.Field.parse(cfs[i]);
            assertTrue(cf.containsParam("p1"));
            assertTrue(cf.containsParam("p2"));
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message Connection field toString test...");
        ConnectionField cf = new ConnectionField();
        cf.addParam("p1");
        cf.addParam("p2");
        
        String tmp = cf.toString();
        if (!tmp.equals("Connection:p1,p2") && !tmp.equals("Connection:p2,p1"))
            fail("reult = \"" + tmp + "\", expected = \"Connection:p1,p2\" or \"Connection:p2,p1\"");
    }
    
}

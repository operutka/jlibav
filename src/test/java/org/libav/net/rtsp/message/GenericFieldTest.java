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
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class GenericFieldTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("RTSP message generic field parse test...");
        String[] gfs = new String[] {
            "SomeField:value",
            "SomeField: value",
        };
        
        int i = 0;
        
        GenericField gf;
        for (; i < gfs.length; i++) {
            gf = (GenericField)RtspHeader.Field.parse(gfs[i]);
            assertEquals(gf.getName(), "SomeField");
            assertEquals(gf.getValue(), "value");
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message generic field toString test...");
        GenericField gf = new GenericField("SomeField", "value");
        assertEquals("SomeField:value", gf.toString());
    }
    
}

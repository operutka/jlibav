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
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class RequireFieldTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("RTSP message Require field parse test...");
        String[] rfs = new String[] {
            "Require:f1,f2",
            "Require: f1,f2",
            "require: f1,f2",
        };
        
        int i = 0;
        
        RequireField rf;
        for (; i < rfs.length; i++) {
            rf = (RequireField)RtspHeader.Field.parse(rfs[i]);
            assertEquals(2, rf.getFeatureCount());
            assertTrue(rf.containsFeature("f1"));
            assertTrue(rf.containsFeature("f2"));
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message Require field toString test...");
        RequireField rf = new RequireField();
        rf.addFeatures(new String[] { "f1", "f2" });
        rf.removeFeature("f2");
        
        RequireField rf2 = rf.clone();
        rf2.addFeatures(Arrays.asList(new String[] { "f2" }));
        
        assertEquals("Require:f1", rf.toString());
        String tmp = rf2.toString();
        if (!tmp.equals("Require:f1,f2") && !tmp.equals("Require:f2,f1"))
            fail("reult = \"" + tmp + "\", expected = \"Require:f1,f2\" or \"Require:f2,f1\"");
    }
    
}

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
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Ondrej Perutka
 */
public class ProxyRequireFieldTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("RTSP message Proxy-Require field parse test...");
        String[] rfs = new String[] {
            "Proxy-Require:f1,f2",
            "Proxy-Require: f1,f2",
            "proxy-require: f1,f2",
        };
        
        int i = 0;
        
        ProxyRequireField rf;
        for (; i < rfs.length; i++) {
            rf = (ProxyRequireField)RtspHeader.Field.parse(rfs[i]);
            assertEquals(2, rf.getFeatureCount());
            assertTrue(rf.containsFeature("f1"));
            assertTrue(rf.containsFeature("f2"));
        }
    }
    
}

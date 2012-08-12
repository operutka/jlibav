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
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for the Attribute class.
 * 
 * @author Ondrej Perutka
 */
public class AttributeTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("SDP attribute parse test...");
        String[] attributes = new String[] {
            "a=key1:value",
            "a=key2",
            " a=key",
            "a =key",
            "a = key",
            "a=key :value",
            "a=:value",
        };
        
        int i = 0;
        
        Attribute a = Attribute.parse(attributes[i++]);
        assertEquals("key1", a.getName());
        assertEquals("value", a.getValue());
        
        a = Attribute.parse(attributes[i++]);
        assertEquals("key2", a.getName());
        assertNull(a.getValue());
        
        for (; i < attributes.length; i++) {
            try {
                Attribute.parse(attributes[i]);
                fail("attribute: \"" + attributes[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }

    @Test
    public void testToString() {
        System.out.println("SDP attribute toString test...");
        Attribute a = new Attribute("key");
        assertEquals("a=key\r\n", a.toString());
        
        a = new Attribute("key", "value");
        assertEquals("a=key:value\r\n", a.toString());
    }
    
}

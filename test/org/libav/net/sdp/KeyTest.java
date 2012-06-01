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
 *
 * @author Ondrej Perutka
 */
public class KeyTest {

    @Test
    public void testParse() throws ParseException {
        System.out.println("SDP key parse test...");
        String[] keys = new String[] {
            "k=tm:tk",
            "k=prompt",
            " k=tm",
            "k =tm",
            "k = tm",
            "k=tm :tk",
            "k=:tk",
        };
        
        int i = 0;
        
        Key k = Key.parse(keys[i++]);
        assertEquals("tm", k.getMethod());
        assertEquals("tk", k.getKey());
        
        k = Key.parse(keys[i++]);
        assertEquals("prompt", k.getMethod());
        assertNull(k.getKey());
        
        for (; i < keys.length; i++) {
            try {
                Key.parse(keys[i]);
                fail("key: \"" + keys[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("SDP key toString test...");
        Key k = new Key("tm", "tk");
        assertEquals("k=tm:tk\r\n", k.toString());
        
        k = new Key("prompt", null);
        assertEquals("k=prompt\r\n", k.toString());
    }
    
}

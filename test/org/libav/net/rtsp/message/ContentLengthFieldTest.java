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
public class ContentLengthFieldTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("RTSP message Content-Length field parse test...");
        String[] clfs = new String[] {
            "Content-Length:100",
            "Content-Length: 100",
            "content-length: 100",
            
            "Content-Length: test",
        };
        
        int i = 0;
        
        ContentLengthField clf;
        for (; i < 3; i++) {
            clf = (ContentLengthField)RtspHeader.Field.parse(clfs[i]);
            assertEquals(clf.getLength(), 100l);
        }
        
        for (; i < clfs.length; i++) {
            try {
                RtspHeader.Field.parse(clfs[i]);
                fail("Content-Length field: \"" + clfs[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message Content-Length field toString test...");
        ContentLengthField clf = new ContentLengthField(100);
        assertEquals("Content-Length:100", clf.toString());
    }
    
}

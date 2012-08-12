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
public class CSeqFieldTest {
    
    @Test
    public void testParse() throws ParseException {
        System.out.println("RTSP message CSeq field parse test...");
        String[] cseqs = new String[] {
            "CSeq:100",
            "CSeq: 100",
            "cseq: 100",
            
            "CSeq: test",
        };
        
        int i = 0;
        
        CSeqField cseq;
        for (; i < 3; i++) {
            cseq = (CSeqField)RtspHeader.Field.parse(cseqs[i]);
            assertEquals(100, cseq.getCseq());
        }
        
        for (; i < cseqs.length; i++) {
            try {
                RtspHeader.Field.parse(cseqs[i]);
                fail("CSeq field: \"" + cseqs[i] + "\" parsed without an exception");
            } catch (ParseException ex) { }
        }
    }
    
    @Test
    public void testToString() {
        System.out.println("RTSP message CSeq field toString test...");
        CSeqField csf = new CSeqField(100);
        assertEquals("CSeq:100", csf.toString());
    }
    
}

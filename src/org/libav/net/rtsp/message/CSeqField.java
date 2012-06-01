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

/**
 * CSeq RTSP header field builder.
 * 
 * @author Ondrej Perutka
 */
public class CSeqField extends RtspHeader.Field {

    private int cseq;

    /**
     * Create a new CSeq header field and set the sequence number.
     * 
     * @param cseq sequence number
     */
    public CSeqField(int cseq) {
        super("CSeq");
        this.cseq = cseq;
    }

    /**
     * Get sequence number.
     * 
     * @return sequence number
     */
    public int getCseq() {
        return cseq;
    }

    /**
     * Set sequence number.
     * 
     * @param cseq sequence number
     */
    public void setCseq(int cseq) {
        this.cseq = cseq;
    }
    
    @Override
    public String getValueText() {
        return "" + cseq;
    }

    @Override
    public CSeqField clone() {
        return new CSeqField(cseq);
    }
    
    /**
     * Parse CSeq RTSP header field.
     * 
     * @param fieldValue
     * @return a CSeq RTSP header field
     * @throws ParseException if the given fieldValue is not an integral number
     */
    public static CSeqField parse(String fieldValue) throws ParseException {
        int cseq;
        
        try {
            cseq = Integer.parseInt(fieldValue);
        } catch (Exception ex) {
            throw (ParseException) new ParseException("not a valid content-length field", 0).initCause(ex);
        }
        
        return new CSeqField(cseq);
    }
    
}

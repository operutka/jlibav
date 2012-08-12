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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Time zone adjustment field parser/builder.
 * 
 * @author Ondrej Perutka
 */
public class TimeZoneAdjustment implements Cloneable {
    
    private static final Pattern parsePattern = Pattern.compile("^z=([0-9]+[ \\t]+[0-9]+[ \\t]*)+$");
    private static final Pattern numberParsePattern = Pattern.compile("[0-9]+");
    
    private List<Adjustment> adjustments;

    /**
     * Create a new time zone adjustment field.
     */
    public TimeZoneAdjustment() {
        this.adjustments = new ArrayList<Adjustment>();
    }

    /**
     * Get list of adjustments. The list is empty by default.
     * 
     * @return list of adjustments
     */
    public List<Adjustment> getAdjustments() {
        return adjustments;
    }

    /**
     * Create text representation of this field.
     * 
     * @return text representation of this field
     */
    @Override
    public String toString() {
        if (adjustments.isEmpty())
            return "";
        
        StringBuilder bldr = new StringBuilder("z=");
        for (Adjustment adj : adjustments)
            bldr.append(adj).append(" ");
        bldr.delete(bldr.length() - 1, bldr.length());
        bldr.append("\r\n");
        
        return bldr.toString();
    }

    /**
     * Clone this field.
     * 
     * @return cloned instance
     */
    @Override
    public TimeZoneAdjustment clone() {
        TimeZoneAdjustment result = new TimeZoneAdjustment();
        result.getAdjustments().addAll(adjustments);
        
        return result;
    }
    
    /**
     * Parse a text representation of the time zone adjustment field.
     * 
     * @param line a text representation
     * @return time zone adjustment field
     * @throws ParseException if the given line is not a valid time zone 
     * adjustment field
     */
    public static TimeZoneAdjustment parse(String line) throws ParseException {
        TimeZoneAdjustment result = new TimeZoneAdjustment();
        Matcher m = parsePattern.matcher(line);
        if (!m.matches())
            throw new ParseException("not a valid time zone adjustment record", 0);
        m = numberParsePattern.matcher(line);
        
        long adj, offset;
        while (m.find()) {
            adj = Long.parseLong(m.group());
            m.find();
            offset = Long.parseLong(m.group());
            result.getAdjustments().add(new Adjustment(adj, offset));
        }
        
        return result;
    }
    
    /**
     * Single time zone adjustment. See SDP specification for more detailed
     * description.
     */
    public static class Adjustment {
        private long adjustmentTime;
        private long offest;

        public Adjustment(long adjustmentTime, long offest) {
            this.adjustmentTime = adjustmentTime;
            this.offest = offest;
        }

        public long getAdjustmentTime() {
            return adjustmentTime;
        }

        public long getOffest() {
            return offest;
        }

        @Override
        public String toString() {
            return adjustmentTime + " " + offest;
        }
    }
    
}

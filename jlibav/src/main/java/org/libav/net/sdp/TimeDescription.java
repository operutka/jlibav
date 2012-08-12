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
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Time description parser/builder.
 * 
 * @author Ondrej Perutka
 */
public class TimeDescription implements Cloneable {
    
    private static final Pattern tParsePattern = Pattern.compile("^t=([0-9]+)[ \\t]+([0-9]+)$");
    private static final Pattern rParsePattern = Pattern.compile("^r=([0-9]+)[ \\t]+([0-9]+)([ \\t]+([0-9]+))+$");
    private static final Pattern numberParsePattern = Pattern.compile("[0-9]+");
    
    private long startTime;
    private long stopTime;
    private boolean periodic;
    private long repeatInterval;
    private long activeDuration;
    private List<Long> offsets;

    /**
     * Create a new time description. Set start and stop time.
     * 
     * @param startTime
     * @param stopTime 
     */
    public TimeDescription(long startTime, long stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.periodic = false;
        this.repeatInterval = 0;
        this.activeDuration = 0;
        this.offsets = new ArrayList<Long>();
    }

    /**
     * Create a new time description. Set start and stop time to 0.
     */
    public TimeDescription() {
        this(0, 0);
    }

    /**
     * Get start time.
     * 
     * @return start time
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Set start time.
     * 
     * @param startTime 
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Get stop time.
     * 
     * @return stop time
     */
    public long getStopTime() {
        return stopTime;
    }

    /**
     * Set stop time.
     * 
     * @param stopTime 
     */
    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * Get active duration. (See SDP specification.)
     * 
     * @return active duration
     */
    public long getActiveDuration() {
        return activeDuration;
    }

    /**
     * Set active duration. (See SDP specification.)
     * 
     * @param activeDuration 
     */
    public void setActiveDuration(long activeDuration) {
        this.activeDuration = activeDuration;
    }

    /**
     * Check whether the broadcast is periodic.
     * 
     * @return true if the brodcast is periodic, false otherwise
     */
    public boolean isPeriodic() {
        return periodic;
    }

    /**
     * (Un)set the broadcast as periodic.
     * 
     * @param periodic 
     */
    public void setPeriodic(boolean periodic) {
        this.periodic = periodic;
    }

    /**
     * Get repeat interval. (Used for periodic broadcast.)
     * 
     * @return repeat interval
     */
    public long getRepeatInterval() {
        return repeatInterval;
    }

    /**
     * Set repeat interval. (Used for periodic broadcast.)
     * 
     * @param repeatInterval 
     */
    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    /**
     * Get list of offsets. (See SDP specification.) The list is empty by
     * default.
     * 
     * @return list of offsets
     */
    public List<Long> getOffsets() {
        return offsets;
    }

    /**
     * Create text representation of this time description.
     * 
     * @return text representation of this time description
     */
    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder();
        bldr.append("t=").append(startTime).append(" ").append(stopTime).append("\r\n");
        if (periodic) {
            bldr.append("r=").append(repeatInterval).append(" ").append(activeDuration).append(" ");
            if (offsets.isEmpty())
                bldr.append("0");
            else {
                for (Long offset : offsets)
                    bldr.append(offset).append(" ");
                bldr.delete(bldr.length() - 1, bldr.length());
            }
            bldr.append("\r\n");
        }
        
        return bldr.toString();
    }

    /**
     * Clone this time description.
     * 
     * @return cloned instance
     */
    @Override
    public TimeDescription clone() {
        TimeDescription result = new TimeDescription(startTime, stopTime);
        result.setPeriodic(periodic);
        result.setRepeatInterval(repeatInterval);
        result.setActiveDuration(activeDuration);
        result.getOffsets().addAll(offsets);
        
        return result;
    }
    
    /**
     * Get current time in the NTP timestamp format.
     * 
     * @return current time in the NTP timestamp format
     */
    public static long getNtpTimestamp() {
        long time = Calendar.getInstance().getTimeInMillis();
        return ((time % 1000) << 32) | (((time / 1000) + 2208988800l) & 0xffffffffl);
    }
    
    /**
     * Get truncated NTP timestamp.
     * 
     * @return truncated timestamp
     */
    public static long getNtpSeconds() {
        return getNtpTimestamp() & 0xffffffffl;
    }
    
    /**
     * Parse time description from its text representation.
     * 
     * @param out session description where to put the result
     * @param lines lines containing the session description
     * @param offset offset in the lines array
     * @return number of used lines
     * @throws ParseException if the lines array does not contain a valid time
     * description at the given offset
     */
    public static int parse(SessionDescription out, String[] lines, int offset) throws ParseException {
        Matcher m = tParsePattern.matcher(lines[offset]);
        if (!m.find())
            throw new ParseException("not a valid time record", offset);
        
        TimeDescription td;
        try {
            td = new TimeDescription(Long.parseLong(m.group(1)), Long.parseLong(m.group(2)));
            out.getTimes().add(td);
            if (++offset == lines.length)
                return 1;
            m = rParsePattern.matcher(lines[offset]);
            if (m.matches()) {
                td.setPeriodic(true);
                m = numberParsePattern.matcher(lines[offset]);
                m.find();
                td.setRepeatInterval(Long.parseLong(m.group()));
                m.find();
                td.setActiveDuration(Long.parseLong(m.group()));
                while (m.find())
                    td.getOffsets().add(Long.parseLong(m.group()));
            } else
                return 1;
        } catch (Exception ex) {
            throw (ParseException) new ParseException("not a valid time record", offset).initCause(ex);
        }
        
        return 2;
    }
    
}

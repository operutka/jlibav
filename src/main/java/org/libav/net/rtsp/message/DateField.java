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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Date RTSP header field builder.
 * 
 * @author Ondrej Perutka
 */
public class DateField extends RtspHeader.Field {

    private static final DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
    
    private Date date;
    
    /**
     * Create a new Date header field and set the date.
     * @param date 
     */
    public DateField(Date date) {
        super("Date");
        this.date = date;
    }

    /**
     * Get date.
     * 
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set date.
     * 
     * @param date 
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String getValueText() {
        return dateFormat.format(date);
    }

    @Override
    public DateField clone() {
        return new DateField(new Date(date.getTime()));
    }
    
    /**
     * Parse Date RTSP header field.
     * 
     * @param fieldValue
     * @return a Date RTSP header field
     * @throws ParseException if the given fieldValue does not contain a date
     * in valid format
     */
    public static DateField parse(String fieldValue) throws ParseException {
        return new DateField(dateFormat.parse(fieldValue));
    }
    
}

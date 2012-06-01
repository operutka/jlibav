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

/**
 * Generic RTSP header field builder.
 * 
 * @author Ondrej Perutka
 */
public class GenericField extends RtspHeader.Field {
    
    private String value;

    /**
     * Create a new header field and set its name and value.
     * @param name
     * @param value 
     */
    public GenericField(String name, String value) {
        super(name);
        this.value = value;
    }

    /**
     * Get headder field value.
     * 
     * @return header field value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set header field value.
     * 
     * @param value header field value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValueText() {
        return value;
    }

    @Override
    public GenericField clone() {
        return new GenericField(getName(), value);
    }
    
}

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
 * Proxy-Require RTSP header field builder.
 * 
 * @author Ondrej Perutka
 */
public class ProxyRequireField extends RequireField {

    /**
     * Create a new empty Proxy-Require field.
     */
    public ProxyRequireField() {
        super("Proxy-Require");
    }

    @Override
    public ProxyRequireField clone() {
        ProxyRequireField result = new ProxyRequireField();
        result.features.addAll(features);
        return result;
    }
    
    /**
     * Parse Proxy-Require RTSP header field.
     * 
     * @param fieldValue
     * @return a Proxy-Require RTSP header field
     */
    public static ProxyRequireField parse(String fieldValue) {
        ProxyRequireField result = new ProxyRequireField();
        result.addFeatures(RequireField.parseFeatures(fieldValue));
        return result;
    }
    
}

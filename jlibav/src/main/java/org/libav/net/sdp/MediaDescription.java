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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Media description parser/builder.
 * 
 * @author Ondrej Perutka
 */
public class MediaDescription implements Cloneable {
    
    private static final Pattern parsePattern = Pattern.compile("^m=([\\S]+)[ \\t]+([0-9]+)(/([0-9]+))?[ \\t]+([\\S]+)(([ \\t]+[\\S]+)*)$");
    private static final Pattern tokenParsePattern = Pattern.compile("[\\S]+");
    
    public static final String MEDIA_AUDIO = "audio";
    public static final String MEDIA_VIDEO = "video";
    public static final String MEDIA_APPLICATION = "application";
    public static final String MEDIA_DATA = "data";
    public static final String MEDIA_CONTROL = "control";
    
    private String media;
    private int port;
    private int numOfPorts;
    private String transport;
    private final List<String> formats;
    
    private String mediaTitle;
    private Connection connection;
    private final Map<String, Long> bandwidth;
    private Key key;
    private final Map<String, Attribute> attributes;

    /**
     * Create a new media description.
     * 
     * @param media a media type (audio, video, ...)
     * @param port media transport port
     * @param transport transport type (e.g. RTP/AVP)
     * @param defaultFormat a transport specific format
     */
    public MediaDescription(String media, int port, String transport, String defaultFormat) {
        this.media = media;
        this.port = port;
        this.numOfPorts = 1;
        this.transport = transport;
        this.formats = new ArrayList<String>();
        this.formats.add(defaultFormat);
        
        this.mediaTitle = null;
        this.connection = null;
        this.bandwidth = new HashMap<String, Long>();
        this.key = null;
        this.attributes = new HashMap<String, Attribute>();
    }

    /**
     * Get media type.
     * 
     * @return media type
     */
    public String getMedia() {
        return media;
    }

    /**
     * Set media type.
     * 
     * @param media 
     */
    public void setMedia(String media) {
        this.media = media;
    }

    /**
     * Get number of ports used for transport.
     * 
     * @return number of ports
     */
    public int getNumOfPorts() {
        return numOfPorts;
    }

    /**
     * Set number of ports used for transport.
     * 
     * @param numOfPorts 
     */
    public void setNumOfPorts(int numOfPorts) {
        this.numOfPorts = numOfPorts;
    }

    /**
     * Get media transport port.
     * 
     * @return media transport port
     */
    public int getPort() {
        return port;
    }

    /**
     * Set media transport port.
     * 
     * @param port 
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get transport type.
     * 
     * @return transport type
     */
    public String getTransport() {
        return transport;
    }

    /**
     * Set transport type.
     * 
     * @param transport 
     */
    public void setTransport(String transport) {
        this.transport = transport;
    }

    /**
     * Get list of transport specific formats.
     * 
     * @return list of transport specific formats
     */
    public List<String> getFormats() {
        return formats;
    }

    /**
     * Get connection description. It is null by default.
     * 
     * @return connection description
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Set connection description.
     * 
     * @param connection 
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Get key field. It is null by default.
     * 
     * @return key field
     */
    public Key getKey() {
        return key;
    }

    /**
     * Set key field.
     * 
     * @param key 
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Get media title. It is null by default.
     * 
     * @return media title
     */
    public String getMediaTitle() {
        return mediaTitle;
    }

    /**
     * Set media title.
     * 
     * @param mediaTitle 
     */
    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }
    
    /**
     * Add bandwidth field.
     * 
     * @param name
     * @param value 
     */
    public void addBandwidth(String name, long value) {
        bandwidth.put(name, value);
    }
    
    /**
     * Remove bandwidth field.
     * 
     * @param name 
     */
    public void removeBandwidth(String name) {
        bandwidth.remove(name);
    }
    
    /**
     * Get value of the specified bandwidth field.
     * 
     * @param name
     * @return value of the specified bandwidth field or null if there is no
     * such field
     */
    public Long getBandwidth(String name) {
        return bandwidth.get(name);
    }
    
    /**
     * Add attribute field.
     * 
     * @param attribute attribute field
     */
    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getName(), attribute);
    }
    
    /**
     * Remove attribute field.
     * 
     * @param name 
     */
    public void removeAttribute(String name) {
        attributes.remove(name);
    }
    
    /**
     * Get the specified attribute field.
     * 
     * @param name
     * @return the specified attribute field or null if there is no such field
     */
    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Create text representation of this media description.
     * 
     * @return text representation of this media description
     */
    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder("m=");
        bldr.append(media).append(" ");
        bldr.append(port);
        if (numOfPorts > 1)
            bldr.append("/").append(numOfPorts);
        bldr.append(" ").append(transport).append(" ");
        for (String fmt : formats)
            bldr.append(fmt).append(" ");
        bldr.delete(bldr.length() - 1, bldr.length());
        bldr.append("\r\n");
        
        if (mediaTitle != null)
            bldr.append("i=").append(mediaTitle).append("\r\n");
        if (connection != null)
            bldr.append(connection);
        for (String bw : bandwidth.keySet())
            bldr.append("b=").append(bw).append(":").append(bandwidth.get(bw)).append("\r\n");
        if (key != null)
            bldr.append(key);
        for (Attribute attr : attributes.values())
            bldr.append(attr);
        
        return bldr.toString();
    }

    /**
     * Clone this media description.
     * 
     * @return cloned instance
     */
    @Override
    public MediaDescription clone() {
        MediaDescription result = new MediaDescription(media, port, transport, "");
        result.setNumOfPorts(numOfPorts);
        result.getFormats().clear();
        result.getFormats().addAll(formats);
        
        result.setMediaTitle(mediaTitle);
        if (connection != null)
            result.setConnection(connection.clone());
        result.bandwidth.putAll(bandwidth);
        if (key != null)
            result.setKey(key.clone());
        for (Attribute attr : attributes.values())
            result.addAttribute(attr.clone());
        
        return result;
    }
    
    /**
     * Parse media description from its text representation.
     * 
     * @param out session description where to put the result
     * @param lines lines containing the media description
     * @param offset offset in the lines array
     * @return number of used lines
     * @throws ParseException if the lines array does not contain a valid media
     * description at the given offset
     */
    public static int parse(SessionDescription out, String[] lines, int offset) throws ParseException {
        int oldOffset = offset;
        Matcher m = parsePattern.matcher(lines[offset]);
        if (!m.find())
            throw new ParseException("not a valid media record", offset);
        
        MediaDescription md = null;
        String tmp;
        
        try {
            md = new MediaDescription(m.group(1), Integer.parseInt(m.group(2)), m.group(5), "");
            tmp = m.group(4);
            if (tmp != null)
                md.setNumOfPorts(Integer.parseInt(tmp));
            out.getMediaDescriptions().add(md);
            md.getFormats().clear();
            tmp = m.group(6);
            if (tmp != null) {
                m = tokenParsePattern.matcher(tmp);
                while (m.find())
                    md.getFormats().add(m.group());
            }
        } catch (Exception ex) {
            throw (ParseException) new ParseException("not a valid media record", offset).initCause(ex);
        }
        
        while (++offset < lines.length) {
            if (lines[offset].startsWith("m="))
                return offset - oldOffset;
            else if (lines[offset].startsWith("i="))
                md.setMediaTitle(lines[offset].substring(2));
            else if (lines[offset].startsWith("c="))
                md.setConnection(Connection.parse(lines[offset]));
            else if (lines[offset].startsWith("b=")) {
                if (!lines[offset].matches("b=[^:\\s]+:.*"))
                    throw new ParseException("not a valid bandwidth record", offset);
                String[] bw = lines[offset].substring(2).split(":");
                md.addBandwidth(bw[0], Long.parseLong(bw[1]));
            } else if (lines[offset].startsWith("k="))
                md.setKey(Key.parse(lines[offset]));
            else if (lines[offset].startsWith("a="))
                md.addAttribute(Attribute.parse(lines[offset]));
        }
        
        return offset - oldOffset;
    }
    
}

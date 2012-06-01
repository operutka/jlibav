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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Require RTSP header field builder.
 * 
 * @author Ondrej Perutka
 */
public class RequireField extends RtspHeader.Field {

    private static final Pattern parsePattern = Pattern.compile("[^,]+");
    
    protected Set<String> features;
    
    protected RequireField(String name) {
        super(name);
        features = new HashSet<String>();
    }

    /**
     * Create a new empty Require field.
     */
    public RequireField() {
        this("Require");
    }
    
    /**
     * Add a new feature.
     * 
     * @param feature 
     */
    public void addFeature(String feature) {
        features.add(feature.toLowerCase());
    }
    
    /**
     * Add an array of features.
     * 
     * @param features 
     */
    public void addFeatures(String[] features) {
        for (String feature : features)
            addFeature(feature);
    }
    
    /**
     * Add a collection of features.
     * 
     * @param features 
     */
    public void addFeatures(Collection<String> features) {
        for (String feature : features)
            addFeature(feature);
    }
    
    /**
     * Check whether this field contains the given feature or not.
     * 
     * @param feature
     * @return true if the feature is contained in this field, false otherwise
     */
    public boolean containsFeature(String feature) {
        return features.contains(feature.toLowerCase());
    }
    
    /**
     * Remove the given feature from this field.
     * 
     * @param feature 
     */
    public void removeFeature(String feature) {
        features.remove(feature.toLowerCase());
    }
    
    /**
     * Get an array of features contained in this field.
     * 
     * @return an array of features
     */
    public String[] getFeatures() {
        return features.toArray(new String[features.size()]);
    }
    
    /**
     * Get number of features contained in this field.
     * @return 
     */
    public int getFeatureCount() {
        return features.size();
    }
    
    @Override
    public String getValueText() {
        StringBuilder bldr = new StringBuilder();
        for (String f : features)
            bldr.append(f).append(",");
        bldr.delete(bldr.length() - 1, bldr.length());
        return bldr.toString();
    }

    @Override
    public RequireField clone() {
        RequireField result = new RequireField();
        result.features.addAll(features);
        return result;
    }
    
    /**
     * Parse Require RTSP header field.
     * 
     * @param fieldValue
     * @return a Require RTSP header field
     */
    public static RequireField parse(String fieldValue) {
        RequireField result = new RequireField();
        result.addFeatures(parseFeatures(fieldValue));
        return result;
    }
    
    /**
     * Parse comma separated list of features and return them as a List.
     * 
     * @param fieldValue
     * @return a List of features
     */
    protected static List<String> parseFeatures(String fieldValue) {
        List<String> features = new ArrayList<String>();
        Matcher m  = parsePattern.matcher(fieldValue);
        
        while (m.find())
            features.add(m.group());
        
        return features;
    }
    
}

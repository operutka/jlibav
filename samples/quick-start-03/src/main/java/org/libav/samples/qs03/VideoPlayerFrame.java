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
package org.libav.samples.qs03;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.libav.util.swing.VideoPane;

/**
 * Simple SWING frame used to show video frames on it.
 * 
 * @author Ondrej Perutka
 */
public class VideoPlayerFrame extends JFrame {

    private VideoPane videoPane;
    
    /**
     * Create a new video player frame, set the window title and dimension
     * of the video pane.
     * 
     * @param windowTitle window title
     * @param width video pane width
     * @param height video pane hight
     */
    public VideoPlayerFrame(String windowTitle, int width, int height) {
        super(windowTitle);
        
        videoPane = new VideoPane();
        videoPane.setPreferredSize(new Dimension(width, height));
        
        setLayout(new BorderLayout());
        
        add(videoPane, BorderLayout.CENTER);
        
        pack();
    }

    /**
     * Get video pane.
     * 
     * @return video pane
     */
    public VideoPane getVideoPane() {
        return videoPane;
    }

    @Override
    public void dispose() {
        videoPane.dispose();
        super.dispose();
    }
    
}

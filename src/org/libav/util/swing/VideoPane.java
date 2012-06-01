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
package org.libav.util.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import org.libav.video.IVideoFrameConsumer;
import org.libav.video.VideoFrame;

/**
 * SWING component for video rendering.
 * 
 * @author Ondrej Perutka
 */
public class VideoPane extends JComponent implements IVideoFrameConsumer {

    private BufferedImage img;
    private int[] imageData;

    private int x;
    private int y;
    private int width;
    private int height;
    
    private Insets insts;
    
    private Painter painter;
    
    /**
     * Create a new video pane.
     */
    public VideoPane() {
        setDoubleBuffered(false);
        setBackground(Color.black);
        img = null;
        imageData = null;
        
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        
        insts = null;
        
        painter = new Painter();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                calculateImgRect();
                repaint();
            }
        });
    }
    
    /**
     * Get the X position of the last frame inside this component.
     * 
     * @return X position of the last frame
     */
    public int getFrameX() {
        return x;
    }
    
    /**
     * Get the Y position of the last frame inside this component.
     * 
     * @return Y position of the last frame
     */
    public int getFrameY() {
        return y;
    }
    
    /**
     * Get width of the last frame inside this component.
     * 
     * @return width of the last frame
     */
    public int getFrameWidth() {
        return width > 0 ? width : getWidth();
    }
    
    /**
     * Get height of the last frame inside this component.
     * 
     * @return height of the last frame
     */
    public int getFrameHeight() {
        return height > 0 ? height : getHeight();
    }

    @Override
    public void paintComponent(Graphics grphcs) {
        Color prev = grphcs.getColor();
        
        grphcs.setColor(getBackground());
        grphcs.fillRect(0, 0, getWidth(), getHeight());
        grphcs.setColor(prev);
        
        if (img != null)
            grphcs.drawImage(img, x, y, width, height, this);
    }

    private void calculateImgRect() {
        if (img == null)
            return;
        
        insts = getInsets(insts);
        int w = getWidth() - insts.left - insts.right;
        int h = getHeight() - insts.top - insts.bottom;
        double r = Math.min((double)w / img.getWidth(), (double)h / img.getHeight());
        
        x = insts.left + (int)((w - img.getWidth() * r) / 2);
        y = insts.top + (int)((h - img.getHeight() * r) / 2);
        width = (int)(img.getWidth() * r);
        height = (int)(img.getHeight() * r);
    }

    /**
     * Clear the component with the background color.
     */
    public void clear() {
        img = null;
        repaint();
    }
    
    @Override
    public void processFrame(Object producer, VideoFrame frame) {
        int[] data = frame.getData();

        if (img == null || img.getWidth() != frame.getWidth() || img.getHeight() != frame.getHeight()) {
            imageData = new int[data.length];
            System.arraycopy(data, 0, imageData, 0, data.length);
            DataBuffer db = new DataBufferInt(imageData, imageData.length);
            int[] masks = new int[] { 0x00ff0000, 0x0000ff00, 0x000000ff };
            SampleModel sm = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, frame.getWidth(), frame.getHeight(), masks);
            WritableRaster wr = Raster.createWritableRaster(sm, db, new Point());
            img = new BufferedImage(new DirectColorModel(24, 0x00ff0000, 0x0000ff00, 0x000000ff), wr, false, null);
            calculateImgRect();
        } else
            System.arraycopy(data, 0, imageData, 0, data.length);
        
        SwingUtilities.invokeLater(painter);
    }
    
    private class Painter implements Runnable {
        @Override
        public void run() {
            JRootPane rp = getRootPane();
            if (rp == null || !isShowing())
                return;
            Point rpLocation = rp.getLocationOnScreen();
            Point myLocation = getLocationOnScreen();

            // Nimbus L&F somtimes causes ClassCastException
            try {
                rp.paintImmediately(myLocation.x - rpLocation.x, myLocation.y - rpLocation.y, getWidth(), getHeight());
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "unable to repaint video pane", ex);
            }
        }
    }
    
}

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
package org.libav.net.rtsp;

/**
 * Aggregate media stream interface.
 * 
 * @author Ondrej Perutka
 */
public interface IAggregateMediaStream extends IMediaStream {
    
    /**
     * Get number of single media streams managed by this aggregate media 
     * stream.
     * 
     * @return number of streams
     */
    int count();
    
    /**
     * Get single media stream at the given index.
     * 
     * @param streamIndex
     * @return single media stream
     */
    ISingleMediaStream get(int streamIndex);
    
    /**
     * Get single media stream by its ID.
     * 
     * @param id stream ID
     * @return single media stream
     */
    ISingleMediaStream getById(long id);
    
    /**
     * Add a new stream change listener. The listener should be notified if
     * a substream is added or removed.
     * 
     * @param l a stream change listener
     */
    void addStreamChangeListener(IStreamChangeListener l);
    
    /**
     * Remove the given stream change listener.
     * 
     * @param l a stream change listener
     */
    void removeStreamChangeListener(IStreamChangeListener l);
    
    /**
     * Stream change listener interface.
     */
    public static interface IStreamChangeListener {
        
        /**
         * A stream has been added.
         * 
         * @param sender event sender
         * @param stream the added stream
         */
        void streamAdded(IAggregateMediaStream sender, ISingleMediaStream stream);
        
        /**
         * A stream has been removed.
         * 
         * @param sender event sender
         * @param stream the removed stream
         */
        void streamRemoved(IAggregateMediaStream sender, ISingleMediaStream stream);
        
    }
    
}

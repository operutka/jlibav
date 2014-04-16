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
package org.libav;

/**
 * Media encoder interface.
 * 
 * @author Ondrej Perutka
 */
public interface IMediaEncoder {
    
    /**
     * Get factory for video stream encoders.
     * 
     * @return video stream encoder factory
     */
    IEncoderFactory getVideoEncoderFactory();
    
    /**
     * Set factory for video stream encoders.
     * 
     * @param factory video stream encoder factory
     */
    void setVideoEncoderFactory(IEncoderFactory factory);
    
    /**
     * Get factory for audio stream encoders.
     * 
     * @return audio stream encoder factory
     */
    IEncoderFactory getAudioEncoderFactory();
    
    /**
     * Set factory for audio stream encoders.
     * 
     * @param factory audio stream encoder factory
     */
    void setAudioEncoderFactory(IEncoderFactory factory);
    
    /**
     * Get underlaying media writer.
     * 
     * @return media writer
     */
    IMediaWriter getMediaWriter();
    
    /**
     * Get encoder for the audio stream at the given index.
     * 
     * @param audioStreamIndex an audio stream index
     * @return encoder
     * @throws LibavException if an error occurs
     */
    IEncoder getAudioStreamEncoder(int audioStreamIndex) throws LibavException;
    
    /**
     * Get encoder for the video stream at the given index.
     * 
     * @param videoStreamIndex a video stream index
     * @return encoder
     * @throws LibavException if an error occurs
     */
    IEncoder getVideoStreamEncoder(int videoStreamIndex) throws LibavException;
    
    /**
     * Close the media encoder, all associated stream encoders and the
     * underlaying media writer.
     * 
     * @throws LibavException if an error occurs while closing the media encoder
     */
    void close() throws LibavException;
    
    /**
     * Check whether the media encoder is closed or not.
     * 
     * @return true if the media encoder is closed, false otherwise
     */
    boolean isClosed();
    
    /**
     * Flush all underlaying encoders.
     * 
     * @throws LibavException if an error occurs
     */
    void flush() throws LibavException;
    
}

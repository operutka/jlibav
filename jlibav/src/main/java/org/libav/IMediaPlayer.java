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
 * Media player interface.
 * 
 * @author Ondrej Perutka
 */
public interface IMediaPlayer {
    
    /**
     * Get underlaying media reader.
     * 
     * @return media reader
     */
    IMediaReader getMediaReader();
    
    /**
     * Check whether decoding of an audio stream is enabled.
     * 
     * @param audioStreamIndex an audio stream index
     * @return true if it is enabled, false otherwise
     * @throws LibavException if the decoding state cannot be checked
     */
    boolean isAudioStreamDecodingEnabled(int audioStreamIndex) throws LibavException;
    
    /**
     * Enable or disable decoding of an audio stream. The decoding is disabled
     * by default for all audio streams. You have to enable it at first if you
     * want to get audio frames.
     * 
     * @param audioStreamIndex an audio stream index
     * @param enabled true to enable decoding, false to disable it
     * @throws LibavException if the decoding cannot be enabled
     */
    void setAudioStreamDecodingEnabled(int audioStreamIndex, boolean enabled) throws LibavException;
    
    /**
     * Get decoder for the audio stream at the given index.
     * 
     * @param audioStreamIndex an audio stream index
     * @return decoder
     * @throws LibavException if an error occurs
     */
    IDecoder getAudioStreamDecoder(int audioStreamIndex) throws LibavException;
    
    /**
     * Check whether decoding of a video stream is enabled.
     * 
     * @param videoStreamIndex a video stream index
     * @return true if it is enabled, false otherwise
     * @throws LibavException if the decoding state cannot be checked
     */
    boolean isVideoStreamDecodingEnabled(int videoStreamIndex) throws LibavException;
    
    /**
     * Enable or disable decoding of a video stream. The decoding is disabled
     * by default for all video streams. You have to enable it at first if you
     * want to get video frames.
     * 
     * @param videoStreamIndex a video stream index
     * @param enabled true to enable decoding, false to disable it
     * @throws LibavException if the decoding cannot be enabled
     */
    void setVideoStreamDecodingEnabled(int videoStreamIndex, boolean enabled) throws LibavException;
    
    /**
     * Get decoder for the video stream at the given index.
     * 
     * @param videoStreamIndex a video stream index
     * @return decoder
     * @throws LibavException if an error occurs
     */
    IDecoder getVideoStreamDecoder(int videoStreamIndex) throws LibavException;
    
    /**
     * Start playback.
     * 
     * NOTE:
     * There must at least one stream with enabled decoding before starting
     * playback unless there will be no time synchronization.
     * 
     * If there is at least one video stream with enabled decoding, the playback
     * is synchronized according to video frame timestamps.
     * 
     * @throws LibavException if the playback cannot be started
     */
    void play() throws LibavException;
    
    /**
     * Stop the playback.
     */
    void stop();
    
    /**
     * Wait until the playback stops.
     * 
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    void join() throws InterruptedException;
    
    /**
     * Close the media player and release all its resources.
     * 
     * @throws LibavException if an error occurs while closing
     */
    void close() throws LibavException;
    
}

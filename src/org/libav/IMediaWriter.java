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

import java.io.FileNotFoundException;
import org.libav.avformat.IFormatContextWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.data.IPacketConsumer;

/**
 * Media writer interface.
 * 
 * @author Ondrej Perutka
 */
public interface IMediaWriter extends IPacketConsumer {
    
    /**
     * Get format context. DO NOT use this method unless you know what you are 
     * doing.
     * 
     * @return format context
     */
    IFormatContextWrapper getFormatContext();
    
    /**
     * Check whether the packet interleaving is enabled.
     * 
     * @return true if it is enabled, false otherwise
     */
    boolean getInterleave();

    /**
     * Enable or disable the packet interleaving.
     * 
     * @param interleave true to enable it, false to disable it
     */
    void setInterleave(boolean interleave);
    
    /**
     * Get number of streams.
     * 
     * @return number of streams
     */
    int getStreamCount();
    
    /**
     * Get stream wrapper for the stream at the given index.
     * 
     * @param streamIndex a strem index
     * @return stream wrapper
     */
    IStreamWrapper getStream(int streamIndex);
    
    /**
     * Get number of video streams.
     * 
     * @return number of video streams
     */
    int getVideoStreamCount();
    
    /**
     * Create a new video stream.
     * 
     * @param codecId a codec ID
     * @param width a frame width
     * @param height a frame height
     * @return index of the created video stream
     * @throws LibavException if an error occurs while creating a new stream
     */
    int addVideoStream(int codecId, int width, int height) throws LibavException;
    
    /**
     * Get stream wrapper for the video stream at the given index.
     * 
     * @param videoStreamIndex a video strem index
     * @return stream wrapper
     */
    IStreamWrapper getVideoStream(int videoStreamIndex);
    
    /**
     * Get number of audio streams.
     * 
     * @return number of audio streams
     */
    int getAudioStreamCount();
    
    /**
     * Create a new audio stream.
     * 
     * @param codecId a codec ID
     * @param sampleRate a sample rate
     * @param sampleFormat a sample format
     * @param channelCount a number of channels
     * @return index of the created audio stream
     * @throws LibavException if an error occurs while creating a new stream
     */
    int addAudioStream(int codecId, int sampleRate, int sampleFormat, int channelCount) throws LibavException;
    
    /**
     * Get stream wrapper for the audio stream at the given index.
     * 
     * @param audioStreamIndex an audio strem index
     * @return stream wrapper
     */
    IStreamWrapper getAudioStream(int audioStreamIndex);
    
    /**
     * Write format header. This method should be called after creating of all
     * streams and before writing any packet.
     * 
     * @throws LibavException if an error occurs while writing the header
     */
    void writeHeader() throws LibavException;
    
    /**
     * Write format trailer. This method should be called after writing all
     * packets.
     * 
     * @throws LibavException if an error occurs while writing the trailer
     */
    void writeTrailer() throws LibavException;
    
    /**
     * Get the SDP. DO NOT call this method before the writeHeader is called.
     * 
     * @return SDP text representation
     * @throws LibavException if an error occurs while creating the SDP
     */
    String getSdp() throws LibavException;
    
    /**
     * Generate the SDP and write it to the file. DO NOT call this method before
     * the writeHeader is called.
     * 
     * @param fileName a file name
     * @throws LibavException if an error occurs while creating the SDP
     * @throws FileNotFoundException if the given file name does not represent
     * a writeable file
     */
    void createSdpFile(String fileName) throws LibavException, FileNotFoundException;
    
    /**
     * Close the writer and release all its resources.
     * 
     * @throws LibavException if an error occurs while closing
     */
    void close() throws LibavException;
    
    /**
     * Check whether the writer is closed or not.
     * 
     * @return true if the writer is closed, false otherwise
     */
    boolean isClosed();
    
}

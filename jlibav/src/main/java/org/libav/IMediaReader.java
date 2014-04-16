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

import org.libav.avformat.IFormatContextWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.data.IPacketConsumer;

/**
 * Media reader interface.
 * 
 * @author Ondrej Perutka
 */
public interface IMediaReader {
    
    /**
     * Get format context. DO NOT use this method unless you know what you are 
     * doing.
     * 
     * @return format context
     */
    IFormatContextWrapper getFormatContext();
    
    /**
     * Get number of streams inside the underlaying format context.
     * 
     * @return number of streams
     */
    int getStreamCount();
    
    /**
     * Add a new packet consumer.
     * 
     * @param streamIndex 
     * @param consumer a packet consumer
     */
    void addPacketConsumer(int streamIndex, IPacketConsumer consumer);
    
    /**
     * Remove the given packet consumer.
     * 
     * @param streamIndex 
     * @param consumer a packet consumer
     */
    void removePacketConsumer(int streamIndex, IPacketConsumer consumer);
    
    /**
     * Check whether there is the given packet consumer for the stream at the
     * given position or not.
     * 
     * @param streamIndex a stream index
     * @param consumer a packet consumer
     * @return true if there is the given consumer, false otherwise
     */
    boolean containsPacketConsumer(int streamIndex, IPacketConsumer consumer);
    
    /**
     * Get stream wrapper for the stream at the given index.
     * 
     * @param streamIndex a stream index
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
     * Get stream wrapper for the video stream at the given index.
     * 
     * @param videoStreamIndex a video stream index
     * @return stream wrapper
     */
    IStreamWrapper getVideoStream(int videoStreamIndex);
    
    /**
     * Add a new video packet consumer.
     * 
     * @param videoStreamIndex  
     * @param consumer a packet consumer
     */
    void addVideoPacketConsumer(int videoStreamIndex, IPacketConsumer consumer);
    
    /**
     * Remove the given video packet consumer.
     * 
     * @param videoStreamIndex  
     * @param consumer a packet consumer
     */
    void removeVideoPacketConsumer(int videoStreamIndex, IPacketConsumer consumer);
    
    /**
     * Check whether there is the given packet consumer for the video stream at 
     * the given position or not.
     * 
     * @param videoStreamIndex a video stream index
     * @param consumer a packet consumer
     * @return true if there is the given consumer, false otherwise
     */
    boolean containsVideoPacketConsumer(int videoStreamIndex, IPacketConsumer consumer);
    
    /**
     * Get number of audio streams.
     * 
     * @return number of audio streams
     */
    int getAudioStreamCount();
    
    /**
     * Get stream wrapper for the audio stream at the given index.
     * 
     * @param audioStremIndex  an audio stream index
     * @return stream wrapper
     */
    IStreamWrapper getAudioStream(int audioStremIndex);
    
    /**
     * Add a new audio packet consumer.
     * 
     * @param audioStreamIndex  
     * @param consumer a packet consumer
     */
    void addAudioPacketConsumer(int audioStreamIndex, IPacketConsumer consumer);
    
    /**
     * Remove the given audio packet consumer.
     * 
     * @param audioStreamIndex  
     * @param consumer a packet consumer
     */
    void removeAudioPacketConsumer(int audioStreamIndex, IPacketConsumer consumer);
    
    /**
     * Check whether there is the given packet consumer for the audio stream at 
     * the given position or not.
     * 
     * @param audioStreamIndex an audio stream index
     * @param consumer a packet consumer
     * @return true if there is the given consumer, false otherwise
     */
    boolean containsAudioPacketConsumer(int audioStreamIndex, IPacketConsumer consumer);
    
    /**
     * Get media duration in miliseconds.
     * 
     * @return media duration
     */
    long getDuration();
    
    /**
     * Get stream duration in miliseconds.
     * 
     * @param streamIndex a stream index
     * @return stream duration
     */
    long getStreamDuration(int streamIndex);
    
    /**
     * Get video stream duration in miliseconds.
     * 
     * @param videoStreamIndex a video stream index
     * @return stream duration
     */
    long getVideoStreamDuration(int videoStreamIndex);
    
    /**
     * Get audio stream duration in miliseconds.
     * 
     * @param audioStreamIndex  an audio stream index
     * @return stream duration
     */
    long getAudioStreamDuration(int audioStreamIndex);
    
    /**
     * Get current position in the stream.
     * 
     * @return position in miliseconds
     */
    long getPosition();
    
    /**
     * Check whether the media is seekable.
     * 
     * @return true if it is seekable, false otherwise
     */
    boolean isSeekable();
    
    /**
     * Seek media at the given position.
     * 
     * @param time a seek position in miliseconds
     * @throws LibavException if the media cannot be seeked
     */
    void seek(long time) throws LibavException;
    
    /**
     * Drop all read buffers (useful for media streams or seeking).
     */
    void dropAllBuffers();
    
    /**
     * Read a next packet from the media stream and send it immediately to all
     * packet consumers for the corresponding stream.
     * 
     * @return true if there was a packet to process, false in case of EOF
     * @throws LibavException if an error occurs
     */
    boolean readNextPacket() throws LibavException;
    
    /**
     * Check whether there is a packet with the given stream index in the 
     * buffer and send it to all its consumers. If there is no such packet in
     * the buffer read a next packet and check its stream index. If it is
     * same as the given stream index send it, otherwise put it into the
     * corresponding buffer.
     * 
     * @param streamIndex a stream index
     * @return true if there was a packet to process, false in case of EOF
     * @throws LibavException if an error occurs
     */
    boolean readNextPacket(int streamIndex) throws LibavException;
    
    /**
     * Translate the given audio stream index to stream index and call 
     * readNextPacket(int).
     * 
     * @param audioStreamIndex an audio stream index
     * @return true if there was a packet to process, false in case of EOF
     * @throws LibavException if an error occurs
     */
    boolean readNextAudioPacket(int audioStreamIndex) throws LibavException;
    
    /**
     * Translate the given video stream index to stream index and call 
     * readNextPacket(int).
     * 
     * @param videoStreamIndex a video stream index
     * @return true if there was a packet to process, false in case of EOF
     * @throws LibavException if an error occurs
     */
    boolean readNextVideoPacket(int videoStreamIndex) throws LibavException;
    
    /**
     * Enable or disable buffering for the stream at the given position.
     * The buffering is disabled by default.
     * 
     * @param streamIndex a stream index
     * @param enabled true to enable it, false to disable it
     */
    void setStreamBufferingEnabled(int streamIndex, boolean enabled);
    
    /**
     * Check whether the buffering is enabled for the stream at the given 
     * position.
     * 
     * @param streamIndex a stream index
     * @return true if the buffering is enabled, false otherwise
     */
    boolean isStreamBufferingEnabled(int streamIndex);
    
    /**
     * Enable or disable buffering for the video stream at the given position.
     * The buffering is disabled by default.
     * 
     * @param videoStreamIndex a video stream index
     * @param enabled true to enable it, false to disable it
     */
    void setVideoStreamBufferingEnabled(int videoStreamIndex, boolean enabled);
    
    /**
     * Check whether the buffering is enabled for the video stream at the given 
     * position.
     * 
     * @param videoStreamIndex a video stream index
     * @return true if the buffering is enabled, false otherwise
     */
    boolean isVideoStreamBufferingEnabled(int videoStreamIndex);
    
    /**
     * Enable or disable buffering for the audio stream at the given position.
     * The buffering is disabled by default.
     * 
     * @param audioStreamIndex an audio stream index
     * @param enabled true to enable it, false to disable it
     */
    void setAudioStreamBufferingEnabled(int audioStreamIndex, boolean enabled);
    
    /**
     * Check whether the buffering is enabled for the audio stream at the given 
     * position.
     * 
     * @param audioStreamIndex an audio stream index
     * @return true if the buffering is enabled, false otherwise
     */
    boolean isAudioStreamBufferingEnabled(int audioStreamIndex);
    
    /**
     * Close the media reader and release all associated resources.
     * 
     * @throws LibavException if an error occurs while closing the media reader
     */
    void close() throws LibavException;
    
    /**
     * Check whether the media reader is closed or not.
     * 
     * @return true if the media reader is closed, false otherwise
     */
    boolean isClosed();
    
}

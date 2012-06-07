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
package org.libav.avformat;

import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.IPacketWrapper;
import org.libav.bridge.IWrapper;

/**
 * Wrapper for the AVFormatContext. It provides access to some of the
 * AVFormatContext properties and also it allows to work with the format
 * context using the Libav functions.
 * 
 * @author Ondrej Perutka
 */
public interface IFormatContextWrapper extends IWrapper {
    
    /**
     * Close the format context.
     * 
     * @throws LibavException exception caused by the Libav
     */
    void close();
    
    /**
     * Check whether the context has been closed or not.
     * 
     * @return true if the context has been closed, false otherwise
     */
    boolean isClosed();
    
    /**
     * Probe the input stream and find some stream info.
     * 
     * @throws LibavException if the stream info cannot be get (caused by the
     * Libav)
     */
    void findStreamInfo() throws LibavException;
    
    /**
     * Get streams associated with this context. If it is an input context
     * you have to call the findStreamInfo() at first.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the composition of the streams have been changed.
     * 
     * @return array of streams associated with this context
     */
    IStreamWrapper[] getStreams();
    
    /**
     * Get number of streams. If it is an input context you have to call 
     * the findStreamInfo() at first.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the composition of the streams have been changed.
     * 
     * @return number of streams
     */
    int getStreamCount();
    
    /**
     * Set stream at the given position.
     * 
     * @param streamIndex a stream position
     * @param stream a stream
     */
    void setStream(int streamIndex, IStreamWrapper stream);
    
    /**
     * Create a new stream and clear cached array of streams.
     * 
     * @return created stream
     * @throws LibavException if the stream cannot be created (caused by the
     * Libav)
     */
    IStreamWrapper newStream() throws LibavException;

    /**
     * Get the filename property from the AVFormatContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return file name
     */
    String getFileName();

    /**
     * Set the filename property of the AVFormatContext. The value may be 
     * cached.
     * 
     * @param fileName a file name
     */
    void setFileName(String fileName);

    /**
     * Get the pb property from the AVFormatContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return IO context
     */
    IIOContextWrapper getIOContext();

    /**
     * Set the pb property of the AVFormatContext. The value may be 
     * cached.
     * 
     * @param ioContext an IO context
     */
    void setIOContext(IIOContextWrapper ioContext);

    /**
     * Get the oformat property from the AVFormatContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return output format
     */
    IOutputFormatWrapper getOutputFormat();

    /**
     * Set the oformat property of the AVFormatContext. The value may be 
     * cached.
     * 
     * @param outputFormat an output format
     */
    void setOutputFormat(IOutputFormatWrapper outputFormat);
    
    /**
     * Get the iformat property from the AVFormatContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return input format
     */
    IInputFormatWrapper getInputFormat();
    
    /**
     * Get file/stream duration.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return file/stream duration
     */
    long getDuration();
    
    /**
     * Get the priv_data property from the AVFormatContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return format context private data
     */
    Pointer<?> getPrivateData();
    
    /**
     * Notify the stream server the application is able to receive data.
     */
    void play();
    
    /**
     * Notify the stream server the application wants to stop receiving of
     * data for a while.
     */
    void pause();
    
    /**
     * Read next packet from the container.
     * 
     * @param packet a structure redy for storing packet data
     * @return true on success, false on EOF or read error
     */
    boolean readNextPacket(IPacketWrapper packet);
    
    /**
     * Write container header.
     * 
     * @throws LibavException if the header cannot be written (caused by the
     * Libav)
     */
    void writeHeader() throws LibavException;
    
    /**
     * Write container trailer.
     * 
     * @throws LibavException if the trailer cannot be written (caused by the
     * Libav)
     */
    void writeTrailer() throws LibavException;
    
    /**
     * Write the given packet into the container.
     * 
     * @param packet a packet
     * @throws LibavException if the packet cannot be written (caused by the
     * Libav)
     */
    void writePacket(IPacketWrapper packet) throws LibavException;
    
    /**
     * Write the given packet into the container (this method keeps all written
     * packets in interleaved order).
     * 
     * @param packet a packet
     * @throws LibavException if the packet cannot be written (caused by the
     * Libav)
     */
    void interleavedWritePacket(IPacketWrapper packet) throws LibavException;
    
    /**
     * Seek the file/stream to the given absolute time (in miliseconds from
     * the begining)
     * 
     * @param minTime a minimal tolerable position
     * @param time a time to seek at
     * @param maxTime a miximal tolerable position
     * @throws LibavException if seeking fails for some reason
     */
    void seekFile(long minTime, long time, long maxTime) throws LibavException;
    
}

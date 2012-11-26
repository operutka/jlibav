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
package org.libav.avcodec;

import org.bridj.Pointer;
import org.libav.bridge.IWrapper;

/**
 * Wrapper for the AVPacket. It provides access to some of the AVPacket 
 * properties.
 * 
 * @author Ondrej Perutka
 */
public interface IPacketWrapper extends IWrapper, Cloneable {
    
    /**
     * Initializes the packet with the default values and clear the wrapper
     * cache.
     */
    void init();
    
    /**
     * Release data held by the packet.
     */
    void free();
    
    /**
     * Grow packet size by the given constant.
     * 
     * @param growBy number of bytes to grow by
     */
    void grow(int growBy);
    
    /**
     * Shrink packet to the given size. (This method does nothing if the packet
     * is smaller than the given size.)
     * 
     * @param size a new size
     */
    void shrink(int size);

    /**
     * Get the stream_index property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return stream index
     */
    int getStreamIndex();

    /**
     * Set the stream_index property of the AVPacket. The value may be 
     * cached.
     * 
     * @param streamIndex a stream index
     */
    void setStreamIndex(int streamIndex);
    
    /**
     * Get the size property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return packet size
     */
    int getSize();
    
    /**
     * Set the size property of the AVPacket. The value may be 
     * cached.
     * 
     * @param size a packet size
     */
    void setSize(int size);
    
    /**
     * Get the data property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return data
     */
    Pointer<Byte> getData();
    
    /**
     * Set the data property of the AVPacket. The value may be 
     * cached.
     * 
     * @param data 
     */
    void setData(Pointer<Byte> data);
    
    /**
     * Get the flags property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return flags
     */
    int getFlags();
    
    /**
     * Set the flags property of the AVPacket. The value may be 
     * cached.
     * 
     * @param flags 
     */
    void setFlags(int flags);

    /**
     * Get the pts property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return presentation timestamp
     */
    long getPts();

    /**
     * Set the pts property of the AVPacket. The value may be 
     * cached.
     * 
     * @param pts a presentation timestamp
     */
    void setPts(long pts);

    /**
     * Get the dts property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return decoding timestamp
     */
    long getDts();

    /**
     * Set the dts property of the AVPacket. The value may be 
     * cached.
     * 
     * @param dts a decoding timestamp
     */
    void setDts(long dts);

    /**
     * Get the duration property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return duration
     */
    int getDuration();

    /**
     * Set the duration property of the AVPacket. The value may be 
     * cached.
     * 
     * @param duration 
     */
    void setDuration(int duration);

    /**
     * Get the convergence_duration property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return convergence duration
     */
    long getConvergenceDuration();

    /**
     * Set the convergence_duration property of the AVPacket. The value may be 
     * cached.
     * 
     * @param convergenceDuration 
     */
    void setConvergenceDuration(long convergenceDuration);

    /**
     * Get the pos property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return position
     */
    long getPosition();

    /**
     * Set the pos property of the AVPacket. The value may be 
     * cached.
     * 
     * @param position 
     */
    void setPosition(long position);
    
    /**
     * Get the side_data property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return packet side data pointer
     */
    Pointer<?> getSideData();

    /**
     * Set the side_data property of the AVPacket. The value may be 
     * cached.
     * 
     * @param sideData side data pointer
     */
    void setSideData(Pointer<?> sideData);

    /**
     * Get the side_data_elems property from the AVPacket.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return number of side data elements
     */
    int getSideDataElems();

    /**
     * Set the side_data_elems property of the AVPacket. The value may be 
     * cached.
     * 
     * @param sideDataElems number of side data elements
     */
    void setSideDataElems(int sideDataElems);

    /**
     * Make a clonned instance of this packet. (It also duplicates packet data.)
     * 
     * @return clonned packet
     */
    IPacketWrapper clone();
    
}

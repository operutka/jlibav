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
import org.libav.LibavException;
import org.libav.avutil.SampleFormat;
import org.libav.bridge.IWrapper;

/**
 * Wrapper for the AVFrame. It provides access to some of the AVFrame 
 * properties.
 * 
 * @author Ondrej Perutka
 */
public interface IFrameWrapper extends IWrapper {
    
    /**
     * Free the native memory.
     */
    void free();
    
    /**
     * Set the fields of the underlaying AVFrame to default values.
     */
    void getDefaults();
    
    /**
     * Fill audio frame data and linesize. AVFrame extended_data channel 
     * pointers are allocated if necessary for planar audio.
     * 
     * @param sampleCount number of samples to be filled into the frame
     * @param channelCount number of channels
     * @param sampleFormat sample format
     * @param buffer audio data buffer
     * @param bufferSize size of the buffer
     * @throws LibavException on Libav error
     */
    void fillAudioFrame(int sampleCount, int channelCount, SampleFormat sampleFormat, Pointer<Byte> buffer, int bufferSize) throws LibavException;
    
    /**
     * Fill audio frame data and linesize. AVFrame extended_data channel 
     * pointers are allocated if necessary for planar audio.
     * 
     * This method allows to fill planar audio frames using data buffer with 
     * longer line size than is needed for the audio frame.
     * 
     * @param sampleCount number of samples to be filled into the frame
     * @param channelCount number of channels
     * @param sampleFormat sample format
     * @param buffer audio data buffer
     * @param bufferSize size of the buffer
     * @param bufferSampleCapacity buffer capacity
     * @throws LibavException on Libav error
     */
    void fillAudioFrame(int sampleCount, int channelCount, SampleFormat sampleFormat, Pointer<Byte> buffer, int bufferSize, int bufferSampleCapacity) throws LibavException;
    
    /**
     * Get the data property from the AVFrame.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return picture planes
     */
    Pointer<Pointer<Byte>> getData();
    
    /**
     * Get length of the array returned by the getData() method.
     * 
     * @return length of the data array
     */
    int getDataLength();
    
    /**
     * Get the extended_data property from the AVFrame.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return extended data
     */
    Pointer<Pointer<Byte>> getExtendedData();
    
    /**
     * Set the extended_data property of the AVFrame. The value may be cached.
     * 
     * @param extendedData 
     */
    void setExtendedData(Pointer<Pointer<Byte>> extendedData);
    
    /**
     * Get the linesize property from the AVFrame.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return size in bytes for each plane
     */
    Pointer<Integer> getLineSize();
    
    /**
     * Get length of the array returned by the getLineSize() method.
     * 
     * @return length of the linesize array
     */
    int getLineSizeLength();
    
    /**
     * Get the key_frame property from the AVFrame.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return true if the frame is a key frame, false otherwise
     */
    boolean isKeyFrame();
    
    /**
     * Set the key_frame property of the AVFrame. The value may be cached.
     * 
     * @param keyFrame true if the frame is a key frame, false otherwise
     */
    void setKeyFrame(boolean keyFrame);
    
    /**
     * Get the pts property from the AVFrame.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return presentation time stamp
     */
    long getPts();
    
    /**
     * Set the pts property of the AVFrame. The value may be cached.
     * 
     * @param pts presentation time stamp
     */
    void setPts(long pts);
    
    /**
     * Get the repeat_pict property from the AVFrame.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return how much the picture must be delayed
     */
    int getRepeatPicture();
    
    /**
     * Set the repeat_pict property of the AVFrame. The value may be cached.
     * 
     * @param repeatPicture how much the picture must be delayed
     */
    void setRepeatPicture(int repeatPicture);
    
    /**
     * Get the pkt_dts property from the AVFrame.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return decoding timestamp of the last packet passed to the decoder
     */
    long getPacketDts();
    
    /**
     * Set the pkt_dts property of the AVFrame. The value may be cached.
     * 
     * @param packetDts decoding timestamp of the last packet passed to the 
     * decoder
     */
    void setPacketDts(long packetDts);
    
    /**
     * Get the pkt_pts property from the AVFrame.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return presentation timestamp of the last packet passed to the decoder
     */
    long getPacketPts();
    
    /**
     * Set the pkt_pts property of the AVFrame. The value may be cached.
     * 
     * @param packetPts presentation timestamp of the last packet passed to the 
     * decoder
     */
    void setPacketPts(long packetPts);

    /**
     * Get the nb_samples property from the AVFrame.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * WARNING:
     * This property is supported since the libavcodec v53.25.0
     * 
     * @return number of audio samples in this frame
     * @throws UnsatisfiedLinkError if the property is not supported
     */
    int getNbSamples();

    /**
     * Set the pkt_pts property of the AVFrame. The value may be cached.
     * 
     * WARNING:
     * This property is supported since the libavcodec v53.25.0
     * 
     * @param nbSamples number of audio samples in this frame
     * @throws UnsatisfiedLinkError if the property is not supported
     */
    void setNbSamples(int nbSamples);
    
}

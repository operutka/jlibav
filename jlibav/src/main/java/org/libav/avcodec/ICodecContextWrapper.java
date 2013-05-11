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

import org.libav.LibavException;
import org.libav.bridge.IWrapper;
import org.libav.util.Rational;

/**
 * Wrapper for the AVCodecContext. It provides access to some of the
 * AVCodecContext properties and also it allows to work with the codec
 * context using the Libav functions.
 * 
 * @author Ondrej Perutka
 */
public interface ICodecContextWrapper extends IWrapper {
    
    /**
     * Set the fields of this AVCodecContext to default values 
     * corresponding to the given codec.
     * 
     * @param codec a codec
     */
    void getDefaults(ICodecWrapper codec) throws LibavException;
    
    /**
     * Initialize the codec context to use the given codec. The given codec 
     * must be the same codec as passed to the getDefaults() method.
     * 
     * @param codec a codec
     * @throws LibavException if the codec context cannot be initialized using
     * the given codec (caused by the Libav)
     */
    void open(ICodecWrapper codec) throws LibavException;
    
    /**
     * Release all resources associated with the codec.
     */
    void close();
    
    /**
     * Release resources associated with this context.
     * 
     * WARNING:
     * DO NOT call this method if you have not created its corresponding stream
     * using the FormatContextWrapper newStream() method.
     */
    void free();
    
    /**
     * Check whether the codec context is initialized to use some codec or not.
     * 
     * @return true if the context is initialized, false otherwise
     */
    boolean isClosed();
    
    /**
     * Get the coded_frame property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return coded frame
     */
    IFrameWrapper getCodedFrame();
    
    /**
     * Get the codec_type property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return media type
     */
    int getCodecType();
    
    /**
     * Set the codec_type property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param codecType a media type
     */
    void setCodecType(int codecType);
    
    /**
     * Get the codec_id property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return codec ID
     */
    int getCodecId();
    
    /**
     * Set the codec_id property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param codecId a codec ID
     */
    void setCodecId(int codecId);
    
    /**
     * Get the flags property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return flags
     */
    int getFlags();
    
    /**
     * Set the flags property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param flags 
     */
    void setFlags(int flags);
    
    /**
     * Get the width property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return width
     */
    int getWidth();
    
    /**
     * Set the width property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param width 
     */
    void setWidth(int width);
    
    /**
     * Get the height property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return height
     */
    int getHeight();
    
    /**
     * Set the height property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param height 
     */
    void setHeight(int height);
    
    /**
     * Get the sample_aspect_ratio property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return aspect ratio
     */
    Rational getSampleAspectRatio();
    
    /**
     * Set the sample_aspect_ratio property of the AVCodecContext. The value 
     * may be cached.
     * 
     * @param aspectRatio 
     */
    void setSampleAspectRatio(Rational aspectRatio);
    
    /**
     * Get the chroma_sample_location property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return chroma sample location
     */
    int getChromaSampleLocation();
    
    /**
     * Set the chroma_sample_location property of the AVCodecContext. The value 
     * may be cached.
     * 
     * @param chromaSampleLocation 
     */
    void setChromaSampleLocation(int chromaSampleLocation);
    
    /**
     * Get the pix_fmt property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return pixel format
     */
    int getPixelFormat();
    
    /**
     * Set the pix_fmt property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param pixelFormat a pixel format
     */
    void setPixelFormat(int pixelFormat);

    /**
     * Get the bit_rate property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return bit rate
     */
    int getBitRate();

    /**
     * Set the bit_rate property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param bitRate a bit rate
     */
    void setBitRate(int bitRate);

    /**
     * Get the time_base property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return time base
     */
    Rational getTimeBase();

    /**
     * Set the time_base property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param timeBase a time base
     */
    void setTimeBase(Rational timeBase);

    /**
     * Get the gop_size property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return number of pictures in a group of pictures
     */
    int getGopSize();

    /**
     * Set the gop_size property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param gopSize a number of pictures in a group of pictures
     */
    void setGopSize(int gopSize);

    /**
     * Get the max_b_frames property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return maximum number of B frames between non B frames
     */
    int getMaxBFrames();

    /**
     * Set the gop_size property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param maxBFrames a maximum number of B frames between non B frames
     */
    void setMaxBFrames(int maxBFrames);

    /**
     * Get the mb_decision property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return macroblock decision mode
     */
    int getMbDecision();

    /**
     * Set the gop_size property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param mbDecision a macroblock decision mode
     */
    void setMbDecision(int mbDecision);

    /**
     * Get the channels property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return number of audio channels
     */
    int getChannels();

    /**
     * Set the channels property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param channels a number of audio channels
     */
    void setChannels(int channels);
    
    /**
     * Get the channel_layout property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return channel layout
     */
    long getChannelLayout();
    
    /**
     * Set the channel_layout property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param channels a channel layout
     */
    void setChannelLayout(long channelLayout);

    /**
     * Get the sample_fmt property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return audio sample format
     */
    int getSampleFormat();

    /**
     * Set the channels property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param sampleFormat an audio sample format
     */
    void setSampleFormat(int sampleFormat);

    /**
     * Get the sample_rate property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return audio sample rate
     */
    int getSampleRate();

    /**
     * Set the channels property of the AVCodecContext. The value may be 
     * cached.
     * 
     * @param sampleRate an audio sample rate
     */
    void setSampleRate(int sampleRate);
    
    /**
     * Get the frame_size property from the AVCodecContext.
     * 
     * WARNING:
     * The returned value may be cached. Call the clearWrapperCahce() if you
     * think the value have been changed.
     * 
     * @return audio frame size
     */
    int getFrameSize();
    
    /**
     * Pass the given video packet to the decoder.
     * 
     * @param packet a video packet (in)
     * @param frame a video frame (out)
     * @return true if a video frame has been decoded, false otherwise
     * @throws LibavException if an error occurs while decoding
     */
    boolean decodeVideoFrame(IPacketWrapper packet, IFrameWrapper frame) throws LibavException;
    
    /**
     * Pass the given video frame to the encoder.
     * 
     * @param frame a video frame (in)
     * @param packet a video packet (out)
     * @return true if a packet has been encoded, false otherwise
     * @throws LibavException if an error occurs while encoding
     */
    boolean encodeVideoFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException;
    
    /**
     * Pass the given audio packet to the decoder.
     * 
     * @param packet an audio packet (in)
     * @param frame an audio frame (out)
     * @return true if an audio frame has been decoded, false otherwise
     * @throws LibavException if an error occurs while decoding
     */
    boolean decodeAudioFrame(IPacketWrapper packet, IFrameWrapper frame) throws LibavException;
    
    /**
     * Pass the given audio frame to the encoder.
     * 
     * @param frame an audio frame (in)
     * @param packet an audio packet (out)
     * @return true if a packet has been encoded, false otherwise
     * @throws LibavException if an error occurs while encoding
     */
    boolean encodeAudioFrame(IFrameWrapper frame, IPacketWrapper packet) throws LibavException;
    
}

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

import org.libav.audio.AudioFrameDecoder;
import org.libav.avformat.IStreamWrapper;
import org.libav.video.VideoFrameDecoder;

/**
 * Default implementation of the media decoder interface.
 * 
 * @author Ondrej Perutka
 */
public class DefaultMediaDecoder implements IMediaDecoder {
    
    private IMediaReader mr;
    
    private IDecoderFactory adf;
    private IDecoderFactory vdf;
    
    private IDecoder[] aDecoders;
    private IDecoder[] vDecoders;

    /**
     * Create a new media decoder and open the media stream using the default 
     * media reader.
     * 
     * @param url a media URL
     * @throws LibavException if an error occurs
     */
    public DefaultMediaDecoder(String url) throws LibavException {
        this(new DefaultMediaReader(url));
    }
    
    protected DefaultMediaDecoder(IMediaReader mr) {
        this.mr = mr;
        
        adf = new DefaultAudioDecoderFactory();
        vdf = new DefaultVideoDecoderFactory();
        
        aDecoders = new IDecoder[mr.getAudioStreamCount()];
        vDecoders = new IDecoder[mr.getVideoStreamCount()];
    }

    @Override
    public IDecoderFactory getAudioDecoderFactory() {
        return adf;
    }

    @Override
    public void setAudioDecoderFactory(IDecoderFactory factory) {
        this.adf = factory;
    }

    @Override
    public IDecoderFactory getVideoDecoderFactory() {
        return vdf;
    }

    @Override
    public void setVideoDecoderFactory(IDecoderFactory factory) {
        this.vdf = factory;
    }
    
    @Override
    public IMediaReader getMediaReader() {
        return mr;
    }

    @Override
    public void setVideoStreamDecodingEnabled(int videoStreamIndex, boolean enabled) throws LibavException {
        if (enabled)
            mr.addVideoPacketConsumer(videoStreamIndex, getVideoStreamDecoder(videoStreamIndex));
        else
            mr.removeVideoPacketConsumer(videoStreamIndex, getVideoStreamDecoder(videoStreamIndex));
    }

    @Override
    public boolean isVideoStreamDecodingEnabled(int videoStreamIndex) throws LibavException {
        return mr.containsVideoPacketConsumer(videoStreamIndex, getVideoStreamDecoder(videoStreamIndex));
    }

    @Override
    public IDecoder getVideoStreamDecoder(int videoStreamIndex) throws LibavException {
        if (vDecoders[videoStreamIndex] == null)
            vDecoders[videoStreamIndex] = vdf.createDecoder(mr.getVideoStream(videoStreamIndex));
        
        return vDecoders[videoStreamIndex];
    }
    
    @Override
    public void setAudioStreamDecodingEnabled(int audioStreamIndex, boolean enabled) throws LibavException {
        if (enabled)
            mr.addAudioPacketConsumer(audioStreamIndex, getAudioStreamDecoder(audioStreamIndex));
        else
            mr.removeAudioPacketConsumer(audioStreamIndex, getAudioStreamDecoder(audioStreamIndex));
    }

    @Override
    public boolean isAudioStreamDecodingEnabled(int audioStreamIndex) throws LibavException {
        return mr.containsAudioPacketConsumer(audioStreamIndex, getAudioStreamDecoder(audioStreamIndex));
    }

    @Override
    public IDecoder getAudioStreamDecoder(int audioStreamIndex) throws LibavException {
        if (aDecoders[audioStreamIndex] == null)
            aDecoders[audioStreamIndex] = adf.createDecoder(mr.getAudioStream(audioStreamIndex));
        
        return aDecoders[audioStreamIndex];
    }

    @Override
    public synchronized void close() throws LibavException {
        for (IDecoder afd : aDecoders) {
            if (afd != null)
                afd.close();
        }
        
        for (IDecoder vfd : vDecoders) {
            if (vfd != null)
                vfd.close();
        }
        
        mr.close();
    }

    @Override
    public boolean isClosed() {
        return mr.isClosed();
    }
    
    @Override
    public synchronized void flush() throws LibavException {
        if (isClosed())
            return;
        
        for (IDecoder afd : aDecoders) {
            if (afd != null)
                afd.flush();
        }
        
        for (IDecoder vfd : vDecoders) {
            if (vfd != null)
                vfd.flush();
        }
    }
    
    private static class DefaultVideoDecoderFactory implements IDecoderFactory {
        @Override
        public IDecoder createDecoder(IStreamWrapper stream) throws LibavException {
            return new VideoFrameDecoder(stream);
        }
    }
    
    private static class DefaultAudioDecoderFactory implements IDecoderFactory {
        @Override
        public IDecoder createDecoder(IStreamWrapper stream) throws LibavException {
            return new AudioFrameDecoder(stream);
        }
    }
    
}

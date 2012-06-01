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

import java.util.HashMap;
import java.util.Map;
import org.libav.audio.AudioFrameEncoder;
import org.libav.avformat.IStreamWrapper;
import org.libav.video.VideoFrameEncoder;

/**
 * Default implementation of the media encoder interface.
 * 
 * @author Ondrej Perutka
 */
public class DefaultMediaEncoder implements IMediaEncoder {

    private IMediaWriter mw;
    
    private IEncoderFactory aef;
    private IEncoderFactory vef;
    
    private Map<Integer, IEncoder> aEncoders;
    private Map<Integer, IEncoder> vEncoders;

    /**
     * Create a new media encoder using the default media writer.
     * 
     * @param url a media URL
     * @throws LibavException if an error occurs
     */
    public DefaultMediaEncoder(String url, String outputFormatName) throws LibavException {
        this(new DefaultMediaWriter(url, outputFormatName));
    }
    
    protected DefaultMediaEncoder(IMediaWriter mr) {
        this.mw = mr;
        
        aef = new DefaultAudioEncoderFactory();
        vef = new DefaultVideoEncoderFactory();
        
        aEncoders = new HashMap<Integer, IEncoder>();
        vEncoders = new HashMap<Integer, IEncoder>();
    }

    @Override
    public IEncoderFactory getVideoEncoderFactory() {
        return vef;
    }

    @Override
    public void setVideoEncoderFactory(IEncoderFactory factory) {
        vef = factory;
    }

    @Override
    public IEncoderFactory getAudioEncoderFactory() {
        return aef;
    }

    @Override
    public void setAudioEncoderFactory(IEncoderFactory factory) {
        aef = factory;
    }

    @Override
    public IMediaWriter getMediaWriter() {
        return mw;
    }

    @Override
    public synchronized IEncoder getAudioStreamEncoder(int audioStreamIndex) throws LibavException {
        if (!aEncoders.containsKey(audioStreamIndex)) {
            IEncoder enc = aef.createEncoder(mw.getAudioStream(audioStreamIndex));
            enc.addPacketConsumer(mw);
            aEncoders.put(audioStreamIndex, enc);
        }
        
        return aEncoders.get(audioStreamIndex);
    }

    @Override
    public synchronized IEncoder getVideoStreamEncoder(int videoStreamIndex) throws LibavException {
        if (!vEncoders.containsKey(videoStreamIndex)) {
            IEncoder enc = vef.createEncoder(mw.getVideoStream(videoStreamIndex));
            enc.addPacketConsumer(mw);
            vEncoders.put(videoStreamIndex, enc);
        }
        
        return vEncoders.get(videoStreamIndex);
    }

    @Override
    public synchronized void close() throws LibavException {
        for (IEncoder enc : aEncoders.values())
            enc.close();
        for (IEncoder enc : vEncoders.values())
            enc.close();
        
        mw.close();
        aEncoders.clear();
        vEncoders.clear();
    }

    @Override
    public boolean isClosed() {
        return mw.isClosed();
    }

    @Override
    public synchronized void flush() throws LibavException {
        if (isClosed())
            return;
        
        for (IEncoder enc : aEncoders.values())
            enc.flush();
        for (IEncoder enc : vEncoders.values())
            enc.flush();
    }
    
    private class DefaultAudioEncoderFactory implements IEncoderFactory {
        @Override
        public IEncoder createEncoder(IStreamWrapper stream) throws LibavException {
            return new AudioFrameEncoder(stream);
        }
    }
    
    private class DefaultVideoEncoderFactory implements IEncoderFactory {
        @Override
        public IEncoder createEncoder(IStreamWrapper stream) throws LibavException {
            return new VideoFrameEncoder(mw.getFormatContext(), stream);
        }
    }
    
}

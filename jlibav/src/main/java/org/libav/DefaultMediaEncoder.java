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
import java.util.HashMap;
import java.util.Map;
import org.libav.audio.AudioFrameEncoder;
import org.libav.avcodec.CodecWrapperFactory;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avcodec.ICodecWrapper;
import org.libav.avcodec.IPacketWrapper;
import org.libav.avformat.IFormatContextWrapper;
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
    
    protected DefaultMediaEncoder(IMediaWriter mw) {
        this.mw = new MediaWriterAdapter(mw);
        
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
    
    private class MediaWriterAdapter implements IMediaWriter {
        private IMediaWriter mw;

        public MediaWriterAdapter(IMediaWriter mw) {
            this.mw = mw;
        }

        @Override
        public IFormatContextWrapper getFormatContext() {
            return mw.getFormatContext();
        }

        @Override
        public boolean getInterleave() {
            return mw.getInterleave();
        }

        @Override
        public void setInterleave(boolean interleave) {
            mw.setInterleave(interleave);
        }

        @Override
        public int getStreamCount() {
            return mw.getStreamCount();
        }

        @Override
        public IStreamWrapper getStream(int streamIndex) {
            return mw.getStream(streamIndex);
        }

        @Override
        public int getVideoStreamCount() {
            return mw.getVideoStreamCount();
        }

        @Override
        public int addVideoStream(int codecId, int width, int height) throws LibavException {
            return mw.addVideoStream(codecId, width, height);
        }

        @Override
        public IStreamWrapper getVideoStream(int videoStreamIndex) {
            return mw.getVideoStream(videoStreamIndex);
        }

        @Override
        public int getAudioStreamCount() {
            return mw.getAudioStreamCount();
        }

        @Override
        public int addAudioStream(int codecId, int sampleRate, int sampleFormat, int channelCount) throws LibavException {
            return mw.addAudioStream(codecId, sampleRate, sampleFormat, channelCount);
        }

        @Override
        public IStreamWrapper getAudioStream(int audioStreamIndex) {
            return mw.getAudioStream(audioStreamIndex);
        }

        @Override
        public void writeHeader() throws LibavException {
            CodecWrapperFactory cwf = CodecWrapperFactory.getInstance();
            IStreamWrapper stream;
            ICodecContextWrapper cc;
            ICodecWrapper codec;
            
            for (int i = 0; i < mw.getStreamCount(); i++) {
                stream = mw.getStream(i);
                cc = stream.getCodecContext();
                cc.clearWrapperCache();
                codec = cwf.findEncoder(cc.getCodecId());
                cc.open(codec);
            }
            
            mw.writeHeader();
        }

        @Override
        public void writeTrailer() throws LibavException {
            mw.writeTrailer();
        }

        @Override
        public String getSdp() throws LibavException {
            return mw.getSdp();
        }

        @Override
        public void createSdpFile(String fileName) throws LibavException, FileNotFoundException {
            mw.createSdpFile(fileName);
        }

        @Override
        public void close() throws LibavException {
            mw.close();
        }

        @Override
        public boolean isClosed() {
            return mw.isClosed();
        }

        @Override
        public void processPacket(Object producer, IPacketWrapper packet) throws LibavException {
            mw.processPacket(producer, packet);
        }
    }
    
}

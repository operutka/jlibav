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
package org.libav.samples;

import java.net.ServerSocket;
import org.libav.*;
import org.libav.avcodec.CodecWrapperFactory;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.net.Server;
import org.libav.net.rtsp.*;
import org.libav.video.FrameScaler;

/**
 * Sample RTSP streaming server.
 * 
 * @author Ondrej Perutka
 */
public class RtspSample {
    
    public static void main(String[] args) throws Exception {
        // create a new RTSP server
        RtspServer rtspServer = new RtspServer();
        Server s = new Server(rtspServer);
        
        // open some multimedia file/stream
        DefaultMediaPlayer mp = new DefaultMediaPlayer("/media/D/tucnaci.avi");
        IMediaReader mr = mp.getMediaReader();
        
        IDecoder dec;
        FrameScaler scaler;
        
        // publish a new multimedia stream at the RTSP server
        SimpleAggregateMediaStream ams = new SimpleAggregateMediaStream();
        if (mr.getVideoStreamCount() > 0) {
            mp.setVideoStreamDecodingEnabled(0, true);
            dec = mp.getVideoStreamDecoder(0);
            ICodecContextWrapper cc = dec.getCodecContext();
            VideoTranscodeStream vts = new VideoTranscodeStream(new VideoStreamWriterFactory(cc));
            scaler = new FrameScaler(cc.getWidth(), cc.getHeight(), cc.getPixelFormat(), cc.getWidth(), cc.getHeight(), cc.getPixelFormat());
            dec.addFrameConsumer(scaler);
            scaler.addFrameConsumer(vts);
            ams.add(vts);
        }
        if (mr.getAudioStreamCount() > 0) {
            mp.setAudioStreamDecodingEnabled(0, true);
            dec = mp.getAudioStreamDecoder(0);
            AudioTranscodeStream ats = new AudioTranscodeStream(new AudioStreamWriterFactory(dec.getCodecContext()));
            dec.addFrameConsumer(ats);
            ams.add(ats);
        }
        rtspServer.addMediaStream("/pokus.sdp", ams);
        
        // create a socket for the server
        ServerSocket ss = new ServerSocket(5555);
        s.startListening(ss); // start listening at our socket
        mp.play(); // start playback of our multimedia file/stream
        mp.join(); // wait until the playback stops
        s.stopListening(); // stop server
    }
    
    /**
     * Factory for client video streams.
     */
    private static class VideoStreamWriterFactory implements IStreamWriterFactory {
        private int width;
        private int height;

        public VideoStreamWriterFactory(ICodecContextWrapper decoderContext) {
            width = decoderContext.getWidth();
            height = decoderContext.getHeight();
        }
        
        @Override
        public int createWriter(IMediaWriter mediaWriter) throws LibavException {
            int index = mediaWriter.addVideoStream(CodecWrapperFactory.CODEC_ID_MPEG4, width, height);
            ICodecContextWrapper cc = mediaWriter.getVideoStream(index).getCodecContext();
            cc.setBitRate(1500000);
            return index;
        }
    }
    
    /**
     * Factory for client audio streams.
     */
    private static class AudioStreamWriterFactory implements IStreamWriterFactory {
        private int channels;
        private int sampleRate;
        private int sampleFormat;

        public AudioStreamWriterFactory(ICodecContextWrapper decoderContext) {
            this.channels = decoderContext.getChannels();
            this.sampleFormat = decoderContext.getSampleFormat();
            this.sampleRate = decoderContext.getSampleRate();
        }
        
        @Override
        public int createWriter(IMediaWriter mediaWriter) throws LibavException {
            return mediaWriter.addAudioStream(CodecWrapperFactory.CODEC_ID_MP2, sampleRate, sampleFormat, channels);
        }
    }
    
}

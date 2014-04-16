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
import org.libav.audio.AudioFrameResampler;
import org.libav.avcodec.CodecID;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avutil.PixelFormat;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVChannelLayout;
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
        ICodecContextWrapper cc;
        FrameScaler scaler = null;
        AudioFrameResampler resampler = null;
        
        // publish a new multimedia stream at the RTSP server
        SimpleAggregateMediaStream ams = new SimpleAggregateMediaStream();
        if (mr.getVideoStreamCount() > 0) {
            mp.setVideoStreamDecodingEnabled(0, true);
            dec = mp.getVideoStreamDecoder(0);
            cc = dec.getCodecContext();
            VideoTranscodeStream vts = new VideoTranscodeStream(new VideoStreamWriterFactory(cc));
            scaler = new FrameScaler(cc.getWidth(), cc.getHeight(), cc.getPixelFormat(), cc.getWidth(), cc.getHeight(), cc.getPixelFormat());
            dec.addFrameConsumer(scaler);
            scaler.addFrameConsumer(vts);
            ams.add(vts);
        }
        if (mr.getAudioStreamCount() > 0) {
            mp.setAudioStreamDecodingEnabled(0, true);
            dec = mp.getAudioStreamDecoder(0);
            cc = dec.getCodecContext();
            AudioTranscodeStream ats = new AudioTranscodeStream(new AudioStreamWriterFactory(CodecID.MP2, cc.getChannels(), 48000, SampleFormat.S16));
            long channelLayout = cc.getChannelLayout();
            if (channelLayout == 0)
                channelLayout = AVChannelLayout.getDefaultChannelLayout(cc.getChannels());
            resampler = new AudioFrameResampler(channelLayout, channelLayout, cc.getSampleRate(), 48000, cc.getSampleFormat(), SampleFormat.S16);
            dec.addFrameConsumer(resampler);
            resampler.addFrameConsumer(ats);
            ams.add(ats);
        }
        rtspServer.addMediaStream("/pokus.sdp", ams);
        
        // create a socket for the server
        ServerSocket ss = new ServerSocket(5555);
        s.startListening(ss); // start listening at our socket
        mp.play(); // start playback of our multimedia file/stream
        mp.join(); // wait until the playback stops
        s.stopListening(); // stop server
        
        if (resampler != null)
            resampler.dispose();
        if (scaler != null)
            scaler.dispose();
    }
    
    /**
     * Factory for client video streams.
     */
    private static class VideoStreamWriterFactory implements IStreamWriterFactory {
        private final int width;
        private final int height;
        private final PixelFormat pixelFormat;

        public VideoStreamWriterFactory(ICodecContextWrapper decoderContext) {
            width = decoderContext.getWidth();
            height = decoderContext.getHeight();
            pixelFormat = decoderContext.getPixelFormat();
        }
        
        @Override
        public int createWriter(IMediaWriter mediaWriter) throws LibavException {
            int index = mediaWriter.addVideoStream(CodecID.MPEG4, width, height);
            ICodecContextWrapper cc = mediaWriter.getVideoStream(index).getCodecContext();
            cc.setBitRate(1500000);
            cc.setPixelFormat(pixelFormat);
            return index;
        }
    }
    
    /**
     * Factory for client audio streams.
     */
    private static class AudioStreamWriterFactory implements IStreamWriterFactory {
        private final CodecID codecId;
        private final int channels;
        private final int sampleRate;
        private final SampleFormat sampleFormat;

        public AudioStreamWriterFactory(CodecID codecId, int channels, int sampleRate, SampleFormat sampleFormat) {
            this.codecId = codecId;
            this.channels = channels;
            this.sampleRate = sampleRate;
            this.sampleFormat = sampleFormat;
        }
        
        @Override
        public int createWriter(IMediaWriter mediaWriter) throws LibavException {
            return mediaWriter.addAudioStream(codecId, sampleRate, sampleFormat, channels);
        }
    }
    
}

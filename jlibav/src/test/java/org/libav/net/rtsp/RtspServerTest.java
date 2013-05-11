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
package org.libav.net.rtsp;

import java.io.File;
import java.net.ServerSocket;
import org.junit.Test;
import org.libav.*;
import org.libav.audio.AudioFrameResampler;
import org.libav.avcodec.CodecID;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avutil.bridge.AVChannelLayout;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.net.Server;
import org.libav.video.FrameScaler;

/**
 *
 * @author Ondrej Perutka
 */
public class RtspServerTest {
    
    @Test
    public void testServerTranscode() throws Exception {
        System.out.println("testing RTSP server in transcode mode...");
        File tmp = DefaultMediaPlayerTest.makeTempFromResource(getClass().getResourceAsStream("/org/libav/resources/test.avi"), "avi");
        RtspServer rtspServer = new RtspServer();
        Server s = new Server(rtspServer);
        
        DefaultMediaPlayer mp = new DefaultMediaPlayer(tmp.getAbsolutePath());
        IMediaReader mr = mp.getMediaReader();
        
        IDecoder dec;
        ICodecContextWrapper cc;
        FrameScaler scaler = null;
        AudioFrameResampler resampler = null;
        
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
            AudioTranscodeStream ats = new AudioTranscodeStream(new AudioStreamWriterFactory(CodecID.MP2, cc.getChannels(), 48000, AVSampleFormat.AV_SAMPLE_FMT_S16));
            long channelLayout = cc.getChannelLayout();
            if (channelLayout == 0)
                channelLayout = AVChannelLayout.getDefaultChannelLayout(cc.getChannels());
            resampler = new AudioFrameResampler(channelLayout, channelLayout, cc.getSampleRate(), 48000, cc.getSampleFormat(), AVSampleFormat.AV_SAMPLE_FMT_S16);
            dec.addFrameConsumer(resampler);
            resampler.addFrameConsumer(ats);
            ams.add(ats);
        }
        rtspServer.addMediaStream("/test.sdp", ams);
        
        int port = 5000 + (int)(Math.random() * 60000);
        ServerSocket ss = new ServerSocket(port);
        s.startListening(ss);
        mp.play();
        
        DefaultMediaPlayer client = new DefaultMediaPlayer("rtsp://localhost:" + port + "/test.sdp", true);
        client.play();
        
        mp.join();
        client.stop();
        client.close();
        s.stopListening();
        mp.close();
        if (resampler != null)
            resampler.dispose();
        if (scaler != null)
            scaler.dispose();
    }
    
    private static class VideoStreamWriterFactory implements IStreamWriterFactory {
        private int width;
        private int height;
        private int pixelFormat;

        public VideoStreamWriterFactory(ICodecContextWrapper decoderContext) {
            width = decoderContext.getWidth();
            height = decoderContext.getHeight();
            pixelFormat = decoderContext.getPixelFormat();
        }
        
        @Override
        public int createWriter(IMediaWriter mediaWriter) throws LibavException {
            int index = mediaWriter.addVideoStream(CodecID.MPEG4, width, height);
            ICodecContextWrapper cc = mediaWriter.getVideoStream(index).getCodecContext();
            cc.setPixelFormat(pixelFormat);
            cc.setBitRate(1500000);
            return index;
        }
    }
    
    private static class AudioStreamWriterFactory implements IStreamWriterFactory {
        private CodecID codecId;
        private int channels;
        private int sampleRate;
        private int sampleFormat;

        public AudioStreamWriterFactory(CodecID codecId, int channels, int sampleRate, int sampleFormat) {
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

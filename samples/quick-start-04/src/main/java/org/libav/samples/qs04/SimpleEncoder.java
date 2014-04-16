/*
 * Copyright (C) 2013 Ondrej Perutka
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
package org.libav.samples.qs04;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.libav.*;
import org.libav.avcodec.CodecID;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avutil.PixelFormat;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVChannelLayout;

/**
 * This example shows you how to create and encode simple video.
 * 
 * @author Ondrej Perutka
 */
public class SimpleEncoder {
    
    private static final int AUDIO_FRAME_DURATION = 100;
    private static final int VIDEO_FRAME_DURATION = 40;
    
    private IMediaEncoder encoder;
    private final IMediaWriter writer;
    
    private IEncoder audioEncoder;
    private AudioFrameGenerator audioFrameGenerator;
    private long nextAudioFrameTime;
    
    private IEncoder videoEncoder;
    private VideoFrameGenerator videoFrameGenerator;
    private long nextVideoFrameTime;
    
    /**
     * Create a new simple encoder.
     * 
     * @param options command line options
     * @throws LibavException on Libav error
     */
    public SimpleEncoder(CommandLineOptions options) throws LibavException {
        // create a new media encoder
        encoder = new DefaultMediaEncoder(options.getFileName(), null);
        // get encoder's media writer
        writer = encoder.getMediaWriter();
        
        // initialize time stamps for interleaving
        nextAudioFrameTime = 0;
        nextVideoFrameTime = 0;
        
        // create audio and video streams
        initAudioStream(options);
        initVideoStream(options);
        
        // write output format's header
        writer.writeHeader();
    }
    
    /**
     * Create audio stream.
     * 
     * @param options command line options
     * @throws LibavException on Libav error
     */
    private void initAudioStream(CommandLineOptions options) throws LibavException {
        CodecID audioCodec = options.getAudioCodec();
        SampleFormat sampleFormat = options.getAudioSampleFormat();
        int sampleRate = options.getAudioSampleRate();
        long channelLayout = options.getAudioChannelLayout();
        int channelCount = AVChannelLayout.getChannelCount(channelLayout);
        
        // add a new audio stream into the container
        int index = writer.addAudioStream(audioCodec, sampleRate, sampleFormat, channelCount);
        // get encoder for the audio stream
        audioEncoder = encoder.getAudioStreamEncoder(index);
        
        // create a new audio frame generator
        audioFrameGenerator = new AudioFrameGenerator(sampleRate, sampleFormat, channelLayout);
        // connect the audio frame generator with the encoder
        audioFrameGenerator.addFrameConsumer(audioEncoder);
    }
    
    /**
     * Create video stream.
     * 
     * @param options command line options
     * @throws LibavException on Libav error
     */
    private void initVideoStream(CommandLineOptions options) throws LibavException {
        CodecID videoCodec = options.getVideoCodec();
        PixelFormat pixelFormat = options.getVideoPixelFormat();
        int width = options.getVideoWidth();
        int height = options.getVideoHeight();
        int bitRate = options.getVideoBitRate();
        
        // add a new video stream into the container
        int index = writer.addVideoStream(videoCodec, width, height);
        // get encoder for the video stream
        videoEncoder = encoder.getVideoStreamEncoder(index);
        
        // create a new video frame generator
        videoFrameGenerator = new VideoFrameGenerator(VIDEO_FRAME_DURATION, width, height, pixelFormat);
        // connect the video frame generator with the encoder
        videoFrameGenerator.addFrameConsumer(videoEncoder);
        
        // get encoder's codec context
        ICodecContextWrapper codecContext = videoEncoder.getCodecContext();
        // set video frame pixel format
        codecContext.setPixelFormat(pixelFormat);
        // set output video bit rate
        codecContext.setBitRate(bitRate);
    }
    
    /**
     * Finalize video output and close the media encoder.
     * 
     * @throws LibavException on Libav error
     */
    public void close() throws LibavException {
        // do nothing if the encoder has been already closed
        if (encoder == null)
            return;
        
        // add last audio frame (to keep same length of audio and video stream)
        int lastFrameDuration = (int)(nextVideoFrameTime - nextAudioFrameTime);
        if (lastFrameDuration > 0)
            audioFrameGenerator.nextFrame(lastFrameDuration);
        
        // dispose the audio and video frame generators
        audioFrameGenerator.dispose();
        videoFrameGenerator.dispose();
        
        // flush media encoder and all its streams
        encoder.flush();
        // write output format's trailer
        writer.writeTrailer();
        
        // close the media encoder
        encoder.close();
        encoder = null;
    }
    
    /**
     * Encode video of the given duration in seconds.
     * 
     * @param duration duration in seconds
     * @throws LibavException on Libav error
     */
    public void encode(double duration) throws LibavException {
        // compute target time in miliseconds
        long target = nextVideoFrameTime + (long)(duration * 1000);
        
        // keep encoding until the next video frame time stamp exceeds the target time
        while (nextVideoFrameTime < target) {
            // interleave audio and video frames:
            //  - encode next video frame if the next video frame time stamp is 
            // lower than the next audio frame time stamp
            //  - encode next audio frame otherwise
            if (nextVideoFrameTime < nextAudioFrameTime) {
                // generate next video frame
                videoFrameGenerator.nextFrame();
                // update next video frame time stamp
                nextVideoFrameTime = videoFrameGenerator.getNextFrameTime();
            } else {
                // generate next audio frame
                audioFrameGenerator.nextFrame(AUDIO_FRAME_DURATION);
                // update next audio frame time stamp
                nextAudioFrameTime = audioFrameGenerator.getNextFrameTime();
            }
        }
        
        // keep the difference in lengths of audio and video streams lower
        // than audio frame duration
        while ((nextVideoFrameTime - nextAudioFrameTime) > AUDIO_FRAME_DURATION) {
            audioFrameGenerator.nextFrame(AUDIO_FRAME_DURATION);
            nextAudioFrameTime = audioFrameGenerator.getNextFrameTime();
        }
    }
    
    public static void main(String[] args) {
        CommandLineOptions options;
        SimpleEncoder main = null;
        
        try {
            // parse command line options
            options = CommandLineOptions.parse(args);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
            return;
        }
        
        try {
            // create a new simple encoder
            main = new SimpleEncoder(options);
            // encode video of the given length
            main.encode(options.getVideoLength());
        } catch (Exception ex) {
            Logger.getLogger(SimpleEncoder.class.getName()).log(Level.SEVERE, "something's wrong", ex);
        } finally {
            try {
                // we should always release kept resources if we don't need them
                if (main != null)
                    main.close();
            } catch (Exception ex) {
                Logger.getLogger(SimpleEncoder.class.getName()).log(Level.SEVERE, "unable to release system resources", ex);
            }
        }
    }
    
}

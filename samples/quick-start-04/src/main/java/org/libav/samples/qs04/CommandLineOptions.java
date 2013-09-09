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

import java.nio.ByteOrder;
import org.libav.LibavException;
import org.libav.avcodec.CodecID;
import org.libav.avcodec.CodecWrapperFactory;
import org.libav.avcodec.ICodecWrapper;
import org.libav.avutil.MediaType;
import org.libav.avutil.PixelFormat;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVChannelLayout;

/**
 * Command line options.
 * 
 * @author Ondrej Perutka
 */
public class CommandLineOptions {
    
    private String fileName;
    private double videoLength;

    private CodecID audioCodec;
    private SampleFormat audioSampleFormat;
    private int audioSampleRate;
    private long audioChannelLayout;

    private CodecID videoCodec;
    private PixelFormat videoPixelFormat;
    private int videoBitRate;
    private int videoWidth;
    private int videoHeight;

    /**
     * Create a new command line options object.
     */
    private CommandLineOptions() {
        fileName = null;
        videoLength = 10;
        
        ByteOrder byteOrder = ByteOrder.nativeOrder();
        if (ByteOrder.BIG_ENDIAN.equals(byteOrder))
            audioCodec = CodecID.PCM_S16BE;
        else
            audioCodec = CodecID.PCM_S16LE;
        
        audioSampleFormat = SampleFormat.S16;
        audioSampleRate = 44100;
        audioChannelLayout = AVChannelLayout.AV_CH_LAYOUT_MONO;
        
        videoCodec = CodecID.MPEG4;
        videoPixelFormat = PixelFormat.YUV420P;
        videoBitRate = 800000;
        videoWidth = 640;
        videoHeight = 360;
    }

    /**
     * Get output file name.
     * 
     * @return output file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get output video length in seconds.
     * 
     * @return output video length
     */
    public double getVideoLength() {
        return videoLength;
    }

    /**
     * Get audio codec.
     * 
     * @return audio codec
     */
    public CodecID getAudioCodec() {
        return audioCodec;
    }

    /**
     * Get audio sample format.
     * 
     * @return audio sample format
     */
    public SampleFormat getAudioSampleFormat() {
        return audioSampleFormat;
    }

    /**
     * Get audio sample rate.
     * 
     * @return audio sample rate
     */
    public int getAudioSampleRate() {
        return audioSampleRate;
    }

    /**
     * Get audio channel layout.
     * 
     * @return audio channel layout
     */
    public long getAudioChannelLayout() {
        return audioChannelLayout;
    }

    /**
     * Get video codec.
     * 
     * @return video codec
     */
    public CodecID getVideoCodec() {
        return videoCodec;
    }

    /**
     * Get video pixel format.
     * 
     * @return video pixel format
     */
    public PixelFormat getVideoPixelFormat() {
        return videoPixelFormat;
    }

    /**
     * Get video bit rate.
     * 
     * @return video bit rate
     */
    public int getVideoBitRate() {
        return videoBitRate;
    }

    /**
     * Get video width.
     * 
     * @return video width
     */
    public int getVideoWidth() {
        return videoWidth;
    }

    /**
     * Get video height.
     * 
     * @return video height
     */
    public int getVideoHeight() {
        return videoHeight;
    }
    
    /**
     * Parse command line options.
     * 
     * @param args command line arguments
     * @return command line options
     */
    public static CommandLineOptions parse(String[] args) {
        if (args.length < 2)
            throw new IllegalArgumentException("USAGE: java -jar quick-start-04.jar file-name video-length OPTIONS\n"
                    + "\tfile-name           output file name\n"
                    + "\tvideo-length        output video length in seconds\n\n"
                    + "OPTIONS:\n"
                    + "\t-ac codec           audio codec [PCM_S16LE/PCM_S16BE]\n"
                    + "\t-asf format         audio sample format [S16]\n"
                    + "\t-asr sample-rate    audio sample rate [44100]\n"
                    + "\t-acl channel-layout audio channel layout [mono]\n\n"
                    + "\t-vc codec           video codec [MPEG4]\n"
                    + "\t-vpf pixel-format   video frame pixel format [YUV420P]\n"
                    + "\t-vbr bit-rate       video bit rate [800000]\n"
                    + "\t-vw width           video width [640]\n"
                    + "\t-vh height          video height [360]\n");
        
        CommandLineOptions result = new CommandLineOptions();
        
        result.fileName = args[0];
        result.videoLength = Double.parseDouble(args[1]);
        
        CodecWrapperFactory codecFactory = CodecWrapperFactory.getInstance();
        
        for (int i = 2; i < args.length; i++) {
            if ((i + 1) == args.length)
                throw new IllegalArgumentException("invalid option: " + args[i]);
            
            if ("-ac".equals(args[i])) {
                try {
                    CodecID codecId = CodecID.valueOf(args[++i].toUpperCase());
                    ICodecWrapper audioCodec = codecFactory.findEncoder(codecId);
                    if (audioCodec.getType() != MediaType.AUDIO)
                        throw new IllegalArgumentException(args[i] + " is not audio encoder (type = " + audioCodec.getType() + ")");
                    result.audioCodec = audioCodec.getId();
                } catch (LibavException ex) {
                    throw new IllegalArgumentException("unknown audio encoder: " + args[i], ex);
                }
            } else if ("-asf".equals(args[i]))
                result.audioSampleFormat = SampleFormat.valueOf(args[++i].toUpperCase());
            else if ("-asr".equals(args[i]))
                result.audioSampleRate = Integer.parseInt(args[++i]);
            else if ("-acl".equals(args[i]))
                result.audioChannelLayout = AVChannelLayout.getChannelLayout(args[++i]);
            else if ("-vc".equals(args[i])) {
                try {
                    CodecID codecId = CodecID.valueOf(args[++i].toUpperCase());
                    ICodecWrapper videoCodec = codecFactory.findEncoder(codecId);
                    if (videoCodec.getType() != MediaType.VIDEO)
                        throw new IllegalArgumentException(args[i] + " is not video encoder (type = " + videoCodec.getType() + ")");
                    result.videoCodec = videoCodec.getId();
                } catch (LibavException ex) {
                    throw new IllegalArgumentException("unknown video encoder: " + args[i], ex);
                }
            } else if ("-vpf".equals(args[i]))
                result.videoPixelFormat = PixelFormat.valueOf(args[++i].toUpperCase());
            else if ("-vbr".equals(args[i]))
                result.videoBitRate = Integer.parseInt(args[++i]);
            else if ("-vw".equals(args[i]))
                result.videoWidth = Integer.parseInt(args[++i]);
            else if ("-vh".equals(args[i]))
                result.videoHeight = Integer.parseInt(args[++i]);
        }
        
        return result;
    }
    
}

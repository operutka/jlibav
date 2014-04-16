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
package org.libav.samples.qs02;

import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import org.libav.*;
import org.libav.audio.Frame2AudioFrameAdapter;
import org.libav.audio.PlaybackMixer;
import org.libav.audio.SampleInputStream;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVChannelLayout;

/**
 * Enother usage example. It shows how to open an audio file/network stream and 
 * play its content using Java Sound API.
 * 
 * @author Ondrej Perutka
 */
public class SimpleAudioPlayer {
    
    private final IMediaPlayer player;
    private SampleInputStream sampleInputStream;
    private AudioInputStream audioInputStream;
    private Frame2AudioFrameAdapter audioFrameAdapter;
    private PlaybackMixer mixer;

    public SimpleAudioPlayer(String url) throws LibavException {
        // Open the given file/stream using IMediaPlayer with its default
        // implementation. DefaultMediaPlayer simply wraps around 
        // IMediaReader shown in the previous example.
        player = new DefaultMediaPlayer(url);
    }
    
    /**
     * Start audio playback.
     * 
     * @throws Exception if something goes wrong
     */
    public void play() throws Exception {
        // Get media player's reader.
        IMediaReader reader = player.getMediaReader();
        
        // Check if there is an audio stream.
        if (reader.getAudioStreamCount() == 0)
            throw new Exception("there is no audio stream");
        
        // Prepare the first audio stream to be played. The audio stream will be 
        // converted and played as a 16 bit stereo.
        // NOTE: upmixing/downmixing (i.e. audio channel conversion) is not
        // supported without avresample library!!!
        openAudioStream(0, AVChannelLayout.AV_CH_LAYOUT_STEREO, SampleFormat.S16);
        
        // Start playback and wait until it stops.
        player.play();
        player.join();
        
        // Flush the rest of audio data.
        sampleInputStream.flushBuffer();
    }
    
    /**
     * Release all resources.
     * 
     * @throws Exception if something goes wrong
     */
    public void close() throws Exception {
        player.close();
        
        if (audioFrameAdapter != null)
            audioFrameAdapter.dispose();
        if (sampleInputStream != null)
            sampleInputStream.close();
        if (mixer != null)
            PlaybackMixer.closeMixer(mixer);
        
        mixer = null;
    }
    
    /**
     * Prepare audio stream for playback.
     * 
     * @param audioStreamIndex audio stream index
     * @param outputChannelLayout output channel layout
     * @param outputSampleFormat output sample format
     * @throws Exception if something goes wrong
     */
    private void openAudioStream(int audioStreamIndex, long outputChannelLayout, SampleFormat outputSampleFormat) throws Exception {
        // Get decoder of the given audio stream and its codec context.
        IDecoder decoder = player.getAudioStreamDecoder(audioStreamIndex);
        ICodecContextWrapper codecContext = decoder.getCodecContext();
        
        // Get some audio stream properties.
        int outputChannelCount = AVChannelLayout.getChannelCount(outputChannelLayout);
        int inputSampleRate = codecContext.getSampleRate();
        long inputChannelLayout = codecContext.getChannelLayout();
        
        if (inputChannelLayout == 0)
            inputChannelLayout = AVChannelLayout.getDefaultChannelLayout(codecContext.getChannels());
        
        // Get Java Sound API AudioFormat descriptor for the output audio 
        // format.
        AudioFormat audioFormat = getAudioFormat(outputChannelCount, inputSampleRate, outputSampleFormat);
        
        // Create adapter between Libav audio frames and intermediary 
        // objects suitable for the SampleInputStream.
        audioFrameAdapter = new Frame2AudioFrameAdapter(inputChannelLayout, 
                outputChannelLayout, inputSampleRate, inputSampleRate, 
                codecContext.getSampleFormat(), outputSampleFormat);
        
        // Create a sample input stream. (It cunsumes audio frames and acts
        // like ordinary Java InputStream.)
        sampleInputStream = new SampleInputStream(inputSampleRate * outputChannelCount * 
                outputSampleFormat.getBytesPerSample(), true);
        
        // Create Java Sound API AudioInputStream for the SampleInputStream.
        audioInputStream = new AudioInputStream(sampleInputStream, audioFormat, -1);
        
        // Get a playback mixer for the specified audio format. Playback
        // mixer is an object responsible for sending audio data into the
        // hardware layer. It encapsulates Java Sound API data lines and
        // allows to play multiple audio streams via single data line.
        mixer = PlaybackMixer.getMixer(audioFormat);

        // Create links between the decoder, sample input stream and mixer.
        decoder.addFrameConsumer(audioFrameAdapter);
        audioFrameAdapter.addAudioFrameConsumer(sampleInputStream);
        mixer.addInputStream(audioInputStream);

        // Enable decoding of the given audio stream.
        player.setAudioStreamDecodingEnabled(audioStreamIndex, true);
        
        // Start sending audio data to the hardware layer.
        mixer.play();
    }
    
    /**
     * Create Java Sound API audio format descriptor for the given audio stream
     * params.
     * 
     * @param channelCount number of channels
     * @param sampleRate sample rate
     * @param sampleFormat sample format
     * @return audio format descriptor
     */
    private AudioFormat getAudioFormat(int channelCount, int sampleRate, SampleFormat sampleFormat) {
        // Get sample width for the given sample format.
        int bytesPerSample = sampleFormat.getBytesPerSample();
        
        AudioFormat.Encoding encoding;
        
        if (sampleFormat.isPlanar() || sampleFormat.isReal())
            throw new IllegalArgumentException("unsupported output sample format");
        else if (sampleFormat.isSigned())
            encoding = AudioFormat.Encoding.PCM_SIGNED;
        else
            encoding = AudioFormat.Encoding.PCM_UNSIGNED;
        
        return new AudioFormat(encoding, sampleRate, bytesPerSample * 8, 
                channelCount, bytesPerSample * channelCount, sampleRate, 
                ByteOrder.BIG_ENDIAN.equals(ByteOrder.nativeOrder()));
    }
    
    public static void main(String[] args) {
        SimpleAudioPlayer main = null;
        
        try {
            // Get an audio file name or stream URL from command line args.
            String url = parseArgs(args);
            // Create a new instance of this simple audio player.
            main = new SimpleAudioPlayer(url);
            // Start audio playback.
            main.play();
        } catch (Exception ex) {
            Logger.getLogger(SimpleAudioPlayer.class.getName()).log(Level.SEVERE, "something's wrong", ex);
        } finally {
            try {
                // We should always release kept resources if we don't need them.
                if (main != null)
                    main.close();
            } catch (Exception ex) {
                Logger.getLogger(SimpleAudioPlayer.class.getName()).log(Level.SEVERE, "unable to release system resources", ex);
            }
        }
    }
    
    /**
     * Check command line arguments and return the first one.
     * 
     * @param args command line arguments
     * @return first argument
     * @throws Exception if there is not exactly one argument
     */
    private static String parseArgs(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("USAGE: java -jar quick-start-02.jar file-name");
        
        return args[0];
    }
    
}

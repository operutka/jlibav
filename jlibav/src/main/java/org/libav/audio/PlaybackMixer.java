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
package org.libav.audio;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;

/**
 * Playback mixer. It is an audio stream player which allows you to play 
 * multiple audio streams via one data line.
 * 
 * @author Ondrej Perutka
 */
public class PlaybackMixer {
    
    private static final Map<AudioFormat, PlaybackMixer> instances;
    private static AudioFormat defaultFormat;
    
    static {
        instances = new HashMap<AudioFormat, PlaybackMixer>();
        defaultFormat = new AudioFormat(44100, 16, 2, true, ByteOrder.BIG_ENDIAN.equals(ByteOrder.nativeOrder()));
    }
    
    private final MixingSampleInputStream mixingStream;
    private final AudioInputStream as;
    private final AudioStreamPlayer asp;
    
    private PlaybackMixer(AudioFormat af) throws LineUnavailableException {
        mixingStream = new MixingSampleInputStream(af);
        as = new AudioInputStream(mixingStream, af, -1);
        
        asp = new AudioStreamPlayer(as, af);
    }
    
    /**
     * Start mixer playback.
     */
    public void play() {
        asp.play();
    }
    
    /**
     * Stop mixer playback.
     */
    public void stop() {
        asp.stop();
    }
    
    /**
     * Wait until the playback stops.
     * 
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void join() throws InterruptedException {
        asp.join();
    }
    
    /**
     * Flush the data line internal buffer.
     */
    public void flushDataLine() {
        asp.flushDataLine();
    }
    
    private void close() {
        asp.close();
    }
    
    /**
     * Get output audio format of this mixer.
     * 
     * @return audio format
     */
    public AudioFormat getAudioFormat() {
        return as.getFormat();
    }
    
    /**
     * Set mixer master volume.
     * 
     * @param volume a volume
     */
    public void setMasterVolume(float volume) {
        asp.setVolume(volume);
    }
    
    /**
     * Get mixer master volume.
     * 
     * @return volume
     */
    public float getMasterVolume() {
        return asp.getVolume();
    }
    
    /**
     * Check whether the conversion between the given audio format and the 
     * mixer target audio format is supported.
     * 
     * @param af an audio format
     * @return true if the conversion is supported, false otherwise
     */
    public boolean isFormatSupported(AudioFormat af) {
        return mixingStream.isFormatSupported(af);
    }
    
    /**
     * Add an audio stream to the mixer.
     * 
     * @param ais an audio input stream
     * @throws IllegalArgumentException if the conversion between the audio 
     * format of the given audio input stream and the target audio format is
     * not supported
     */
    public void addInputStream(AudioInputStream ais) {
        mixingStream.addAudioInputStream(ais);
    }
    
    /**
     * Remove an audio stream from the mixer.
     * 
     * @param ais an audio input stream
     */
    public void removeInputStream(AudioInputStream ais) {
        mixingStream.removeAudioInputStream(ais);
    }
    
    /**
     * Get the volume of the given audio input stream.
     * 
     * @param ais an audio input stream
     * @return the volume or -1 if the stream is not known
     */
    public float getStreamVolume(AudioInputStream ais) {
        return mixingStream.getStreamVolume(ais);
    }
    
    /**
     * Set the volume of the given stream.
     * 
     * @param ais an audio input stream
     * @param volume a volume
     */
    public void setStreamVolume(AudioInputStream ais, float volume) {
        mixingStream.setStreamVolume(ais, volume);
    }
    
    /**
     * Get default target audio format.
     * 
     * @return default target audio format
     */
    public static AudioFormat getDefaultFormat() {
        return defaultFormat;
    }
    
    /**
     * Set default target audio format.
     * 
     * @param format an audio format
     */
    public static void setDefaultFormat(AudioFormat format) {
        if (format == null)
            throw new NullPointerException("default audio format cannot be null");
        
        defaultFormat = format;
    }
    
    /**
     * Get default mixer (the mixer using the default audio format).
     * 
     * @return default audio mixer
     * @throws LineUnavailableException if the data line for the default mixer
     * is not available
     */
    public static PlaybackMixer getDefaultMixer() throws LineUnavailableException {
        return getMixer(defaultFormat);
    }
    
    /**
     * Get mixer for the given target audio format.
     * 
     * @param outputFormat an audio format
     * @return mixer
     * @throws LineUnavailableException if the data line of the given type is
     * not available
     */
    public static PlaybackMixer getMixer(AudioFormat outputFormat) throws LineUnavailableException {
        synchronized (instances) {
            if (!instances.containsKey(outputFormat))
                instances.put(outputFormat, new PlaybackMixer(outputFormat));
            
            return instances.get(outputFormat);
        }
    }
    
    /**
     * Close audio mixer for the given audio format and release its data line.
     * 
     * @param format an audio format
     */
    public static void closeMixer(AudioFormat format) {
        synchronized (instances) {
            PlaybackMixer pm = instances.get(format);
            if (pm == null)
                return;
            
            pm.close();
            instances.remove(format);
        }
    }
    
    public static void closeMixer(PlaybackMixer mixer) {
        synchronized (instances) {
            mixer.close();
            mixer = instances.get(mixer.getAudioFormat());
            if (mixer == null)
                return;
            
            mixer.close();
            instances.remove(mixer.getAudioFormat());
        }
    }
    
    /**
     * Close all mixers and release their data lines.
     */
    public static void closeAllMixers() {
        synchronized (instances) {
            for (PlaybackMixer pm : instances.values())
                pm.close();
            instances.clear();
        }
    }
    
}

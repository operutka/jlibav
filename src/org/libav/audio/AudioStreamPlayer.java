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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 * Audio stream player. It provides audio playback via Java Sound API. It does
 * not support audio mixing.
 * 
 * @author Ondrej Perutka
 */
public class AudioStreamPlayer {
    
    private SourceDataLine dataLine;
    private AudioInputStream inputStream;
    
    private boolean running;
    private boolean stop;
    
    private PlayerThread player;
    private Thread thread;
    
    private float volume;

    /**
     * Create a new audio stream player and set the input stream and the data
     * line. The input stream and the data line do not have to use the same
     * audio format, but the conversion must be supported if the audio formats
     * are not same.
     * 
     * @param inputStream an input stream
     * @param dataLine a data line
     * @throws IllegalArgumentException if the necessary conversion is not
     * supported
     */
    public AudioStreamPlayer(AudioInputStream inputStream, SourceDataLine dataLine) {
        this.dataLine = dataLine;
        if (dataLine.getFormat().matches(inputStream.getFormat()))
            this.inputStream = inputStream;
        else
            this.inputStream = AudioSystem.getAudioInputStream(dataLine.getFormat(), inputStream);
        
        this.running = false;
        this.stop = false;
        
        this.player = new PlayerThread();
        this.thread = null;
        
        this.volume = 1f;
    }
    
    /**
     * Create a new audio stream player, set the input stream and use a data 
     * line of the given format. The input stream format and the data line 
     * format do not have to be the same, but the conversion must be supported 
     * if they are not.
     * 
     * @param inputStream an input stream
     * @param outputFormat a data line format
     * @throws LineUnavailableException if there is no data line for the given
     * data line format
     * @throws IllegalArgumentException if the necessary conversion is not
     * supported
     */
    public AudioStreamPlayer(AudioInputStream inputStream, AudioFormat outputFormat) throws LineUnavailableException {
        this(inputStream, openDataLine(outputFormat));
    }
    
    /**
     * Start audio playback.
     */
    public synchronized void play() {
        if (running)
            return;
        
        stop = false;
        running = true;
        thread = new Thread(player, "AudioPlayer thread");
        thread.setDaemon(true);
        thread.start();
    }
    
    /**
     * Stop playback.
     */
    public synchronized void stop() {
        stop = true;
        running = false;
        
        if (thread == null)
            return;
        
        try {
            thread.interrupt();
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "interrupted while waiting for the audio player to be stopped");
        }
    }
    
    private void setGain(FloatControl gainControl, float volume) {
        double min = Math.pow(10, gainControl.getMinimum() / 20.0);
        double g = 20 * Math.log10(min + volume * (1 - min));
        if (g > gainControl.getMaximum())
            g = gainControl.getMaximum();
        
        gainControl.setValue((float)g);
    }
    
    private void setVolume(FloatControl volumeControl, float volume) {
        double range = volumeControl.getMaximum() - volumeControl.getMinimum();
        double v = volumeControl.getMinimum() + volume * range;
        if (v > volumeControl.getMaximum())
            v = volumeControl.getMaximum();
        
        volumeControl.setValue((float)v);
    }
    
    /**
     * Set the output volume.
     * 
     * @param volume a volume
     */
    public void setVolume(float volume) {
        // The Oracle's JDK uses MASTER_GAIN but the OpenJDK uses VOLUME
        if (dataLine.isControlSupported(FloatControl.Type.MASTER_GAIN))
            setGain((FloatControl)dataLine.getControl(FloatControl.Type.MASTER_GAIN), volume);
        else if (dataLine.isControlSupported(FloatControl.Type.VOLUME))
            setVolume((FloatControl)dataLine.getControl(FloatControl.Type.VOLUME), volume);
        else
            throw new RuntimeException("volume is not supported");
        
        this.volume = volume;
    }
    
    /**
     * Get the currently set volume.
     * 
     * @return currently set volume
     */
    public float getVolume() {
        return volume;
    }
    
    /**
     * Close the audio stream player and its data line.
     */
    public synchronized void close() {
        stop();
        dataLine.close();
    }
    
    /**
     * Wait until the playback stops.
     * 
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void join() throws InterruptedException {
        if (thread != null)
            thread.join();
    }
    
    /**
     * Flush the data line internal buffer.
     */
    public void flushDataLine() {
        dataLine.flush();
    }
    
    private class PlayerThread implements Runnable {
        @Override
        public void run() {
            AudioFormat af = dataLine.getFormat();
            byte[] frame = new byte[(int)af.getSampleRate() * af.getSampleSizeInBits() * af.getChannels() / 800];
            int len;
            
            dataLine.start();
            while (!stop) {
                try {
                    len = inputStream.read(frame);
                    if (len == -1)
                        break;
                    dataLine.write(frame, 0, len);
                } catch (IOException ex) {
                    if (ex.getCause() instanceof InterruptedException)
                        stop = true;
                    else
                        Logger.getLogger(AudioStreamPlayer.class.getName()).log(Level.SEVERE, "audio input stream became unreadable", ex);
                }
            }
            if (!stop)
                dataLine.drain();
            dataLine.stop();
        }
    }
    
    private static SourceDataLine openDataLine(AudioFormat format) throws LineUnavailableException {
        SourceDataLine result = AudioSystem.getSourceDataLine(format);
        result.open(format);
        return result;
    }
    
}

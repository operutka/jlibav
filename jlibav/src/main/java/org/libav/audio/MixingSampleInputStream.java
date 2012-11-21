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
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * Mixing sample input stream. It allows to mix audio samples from several
 * audio input streams. It is usefull if you need to play multiple audio
 * streams via one data line.
 * 
 * Currently it supports following target sample formats:
 * - 16bit PCM signed
 * - 16bit PCM unsigned
 * - 24bit PCM signed
 * - 24bit PCM unsigned
 * 
 * @author Ondrej Perutka
 */
public class MixingSampleInputStream extends InputStream {

    private final Map<AudioInputStream, InputStreamInfo> streams;
    private AudioFormat targetFormat;
    private SampleConverter sc;
    
    private int sampleCount;
    private int sampleSize;
    private int frameSize;
    private int[] samples;

    /**
     * Create a new mixing stream and set the target audio format.
     * 
     * @param targetFormat an audio format
     * @throws IllegalArgumentException if the audio format is not supported
     */
    public MixingSampleInputStream(AudioFormat targetFormat) {
        this.streams = Collections.synchronizedMap(new HashMap<AudioInputStream, InputStreamInfo>());
        this.targetFormat = targetFormat;
        
        frameSize = targetFormat.getFrameSize();
        sampleSize = targetFormat.getSampleSizeInBits() / 8;
        sampleCount = frameSize / sampleSize;
        samples = null;
        
        boolean le = ByteOrder.LITTLE_ENDIAN.equals(ByteOrder.nativeOrder());
        if (targetFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
            switch (targetFormat.getSampleSizeInBits()) {
                case 16: sc = le ? new Int16LEConverter() : new Int16BEConverter(); break;
                case 24: sc = le ? new Int24LEConverter() : new Int24BEConverter(); break;
                default: throw new IllegalArgumentException("unsupported sample size");
            }
        } else if (targetFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
            switch (targetFormat.getSampleSizeInBits()) {
                case 16: sc = le ? new UInt16LEConverter() : new UInt16BEConverter(); break;
                case 24: sc = le ? new UInt24LEConverter() : new UInt24BEConverter(); break;
                default: throw new IllegalArgumentException("unsupported sample size");
            }
        } else
            throw new IllegalArgumentException("unsupported sample encoding");
    }

    /**
     * Get target audio format.
     * 
     * @return target audio format
     */
    public AudioFormat getTargetAudioFormat() {
        return targetFormat;
    }
    
    /**
     * Check whether the conversion between the given audio format and the 
     * target audio format is supported.
     * 
     * @param af an audio format
     * @return true if the conversion is supported, false otherwise
     */
    public boolean isFormatSupported(AudioFormat af) {
        return AudioSystem.isConversionSupported(targetFormat, af);
    }
    
    /**
     * Add an audio stream to the mixer.
     * 
     * @param ais an audio input stream
     * @throws IllegalArgumentException if the conversion between the audio 
     * format of the given audio input stream and the target audio format is
     * not supported
     */
    public void addAudioInputStream(AudioInputStream ais) {
        AudioInputStream key = ais;
        if (!targetFormat.matches(ais.getFormat()))
            ais = AudioSystem.getAudioInputStream(targetFormat, ais);
        
        streams.put(key, new InputStreamInfo(ais));
    }
    
    /**
     * Remove an audio stream from the mixer.
     * 
     * @param ais an audio input stream
     */
    public void removeAudioInputStream(AudioInputStream ais) {
        streams.remove(ais);
    }
    
    /**
     * Set the volume of the given stream.
     * 
     * @param ais an audio input stream
     * @param volume a volume
     */
    public void setStreamVolume(AudioInputStream ais, float volume) {
        InputStreamInfo isi = streams.get(ais);
        if (isi == null)
            return;
        
        isi.setVolume(volume);
    }
    
    /**
     * Get the volume of the given audio input stream.
     * 
     * @param ais an audio input stream
     * @return the volume or -1 if the stream is not known
     */
    public float getStreamVolume(AudioInputStream ais) {
        InputStreamInfo isi = streams.get(ais);
        if (isi == null)
            return -1;
        
        return isi.getVolume();
    }
    
    @Override
    public int available() {
        return streams.isEmpty() ? 0 : frameSize;
    }

    @Override
    public void close() throws IOException {
        synchronized (streams) {
            for (InputStreamInfo isi : streams.values())
                isi.getStream().close();
            streams.clear();
        }
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public void mark(int i) {
    }

    @Override
    public void reset() throws IOException {
    }

    @Override
    public int read() throws IOException {
        if (frameSize != 1)
            throw new IOException("the audio frame size is greather than one byte, cannot read integral number of frames");
        
        byte[] result = new byte[1];
        int len = read(result);
        
        if (len == -1)
            return -1;
        
        return result[0];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int fcount = len / frameSize;
        int result, vol;
        
        if (samples == null || samples.length != (fcount * sampleCount))
            samples = new int[fcount * sampleCount];
        Arrays.fill(samples, 0);
        
        len = fcount * frameSize;
        for (InputStreamInfo isi : streams.values()) {
            result = isi.getStream().read(b, off, len);
            if (result == -1)
                continue;
            result /= sampleSize;
            vol = (int)(isi.getVolume() * 100);
            sc.getSamples(b, off, samples, result, vol);
        }
        
        sc.getBytes(samples, b, off);
        
        return len;
    }
    
    private static class InputStreamInfo {
        private AudioInputStream ais;
        private float volume;

        public InputStreamInfo(AudioInputStream ais) {
            this.ais = ais;
            this.volume = 1f;
        }

        public AudioInputStream getStream() {
            return ais;
        }

        public float getVolume() {
            return volume;
        }

        public void setVolume(float volume) {
            this.volume = volume;
        }
    }
    
    private static interface SampleConverter {
        void getSamples(byte[] data, int offset, int[] samples, int count, int volume);
        void getBytes(int[] samples, byte[] data, int dataOffset);
    }
    
    private static class Int16LEConverter implements SampleConverter {
        protected int min;
        protected int max;
        
        public Int16LEConverter() {
            min = -1 << 15;
            max = (1 << 15) - 1;
        }
        
        @Override
        public void getSamples(byte[] data, int offset, int[] samples, int count, int volume) {
            for (int i = 0; i < count; i++) {
                samples[i] = data[offset++] & 0xff;
                samples[i] |= data[offset++] << 8;
                samples[i] *= volume;
                samples[i] /= 100;
            }
        }
        
        @Override
        public void getBytes(int[] samples, byte[] data, int dataOffset) {
            int sample;
            
            for (int i = 0; i < samples.length; i++) {
                sample = samples[i];
                if (sample > max) sample = max;
                else if (sample < min) sample = min;
                data[dataOffset++] = (byte)sample;
                data[dataOffset++] = (byte)(sample >> 8);
            }
        }
    }
    
    private static class Int16BEConverter implements SampleConverter {
        protected int min;
        protected int max;
        
        public Int16BEConverter() {
            min = -1 << 15;
            max = (1 << 15) - 1;
        }

        @Override
        public void getSamples(byte[] data, int offset, int[] samples, int count, int volume) {
            for (int i = 0; i < count; i++) {
                samples[i] = data[offset++] << 8;
                samples[i] |= data[offset++] & 0xff;
                samples[i] *= volume;
                samples[i] /= 100;
            }
        }

        @Override
        public void getBytes(int[] samples, byte[] data, int dataOffset) {
            int sample;
            
            for (int i = 0; i < samples.length; i++) {
                sample = samples[i];
                if (sample > max) sample = max;
                else if (sample < min) sample = min;
                data[dataOffset++] = (byte)(sample >> 8);
                data[dataOffset++] = (byte)sample;
            }
        }
    }
    
    private static class UInt16LEConverter extends Int16LEConverter {
        public UInt16LEConverter() {
            min = 0;
            max = (1 << 16) - 1;
        }
        
        @Override
        public void getSamples(byte[] data, int offset, int[] samples, int count, int volume) {
            for (int i = 0; i < count; i++) {
                samples[i] = data[offset++] & 0xff;
                samples[i] |= (data[offset++] & 0xff) << 8;
                samples[i] *= volume;
                samples[i] /= 100;
            }
        }
    }
    
    private static class UInt16BEConverter extends Int16BEConverter {
        public UInt16BEConverter() {
            min = 0;
            max = (1 << 16) - 1;
        }

        @Override
        public void getSamples(byte[] data, int offset, int[] samples, int count, int volume) {
            for (int i = 0; i < count; i++) {
                samples[i] = (data[offset++] & 0xff) << 8;
                samples[i] |= data[offset++] & 0xff;
                samples[i] *= volume;
                samples[i] /= 100;
            }
        }
    }
    
    private static class Int24LEConverter implements SampleConverter {
        protected int min;
        protected int max;
        
        public Int24LEConverter() {
            min = -1 << 23;
            max = (1 << 23) - 1;
        }

        @Override
        public void getSamples(byte[] data, int offset, int[] samples, int count, int volume) {
            for (int i = 0; i < count; i++) {
                samples[i] = data[offset++] & 0xff;
                samples[i] |= (data[offset++] & 0xff) << 8;
                samples[i] |= data[offset++] << 16;
                samples[i] *= volume;
                samples[i] /= 100;
            }
        }

        @Override
        public void getBytes(int[] samples, byte[] data, int dataOffset) {
            int sample;
            
            for (int i = 0; i < samples.length; i++) {
                sample = samples[i];
                if (sample > max) sample = max;
                else if (sample < min) sample = min;
                data[dataOffset++] = (byte)sample;
                data[dataOffset++] = (byte)(sample >> 8);
                data[dataOffset++] = (byte)(sample >> 16);
            }
        }
    }
    
    private static class Int24BEConverter implements SampleConverter {
        protected int min;
        protected int max;
        
        public Int24BEConverter() {
            min = -1 << 23;
            max = (1 << 23) - 1;
        }

        @Override
        public void getSamples(byte[] data, int offset, int[] samples, int count, int volume) {
            for (int i = 0; i < count; i++) {
                samples[i] = data[offset++] << 16;
                samples[i] |= (data[offset++] & 0xff) << 8;
                samples[i] |= data[offset++] & 0xff;
                samples[i] *= volume;
                samples[i] /= 100;
            }
        }

        @Override
        public void getBytes(int[] samples, byte[] data, int dataOffset) {
            int sample;
            
            for (int i = 0; i < samples.length; i++) {
                sample = samples[i];
                if (sample > max) sample = max;
                else if (sample < min) sample = min;
                data[dataOffset++] = (byte)(sample >> 16);
                data[dataOffset++] = (byte)(sample >> 8);
                data[dataOffset++] = (byte)sample;
            }
        }
    }
    
    private static class UInt24LEConverter extends Int24LEConverter {
        public UInt24LEConverter() {
            min = 0;
            max = (1 << 24) - 1;
        }

        @Override
        public void getSamples(byte[] data, int offset, int[] samples, int count, int volume) {
            for (int i = 0; i < count; i++) {
                samples[i] = data[offset++] & 0xff;
                samples[i] |= (data[offset++] & 0xff) << 8;
                samples[i] |= (data[offset++] & 0xff) << 16;
                samples[i] *= volume;
                samples[i] /= 100;
            }
        }
    }
    
    private static class UInt24BEConverter extends Int24BEConverter {
        public UInt24BEConverter() {
            min = 0;
            max = (1 << 24) - 1;
        }

        @Override
        public void getSamples(byte[] data, int offset, int[] samples, int count, int volume) {
            for (int i = 0; i < count; i++) {
                samples[i] = (data[offset++] & 0xff) << 16;
                samples[i] |= (data[offset++] & 0xff) << 8;
                samples[i] |= data[offset++] & 0xff;
                samples[i] *= volume;
                samples[i] /= 100;
            }
        }
    }

}

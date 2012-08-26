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
package org.libav.avresample;

import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avutil.bridge.AVChannelLayout;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVResampleContext.
 * 
 * @author Ondrej Perutka
 */
public class AudioResampleContextWrapperLAVC implements IAudioResampleContextWrapper {
    
    private static final AVCodecLibrary codecLib = LibraryManager.getInstance().getAVCodecLibrary();
    
    private long inChannelLayout;
    private int inSampleFormat;
    private int inSampleRate;
    private long outChannelLayout;
    private int outSampleFormat;
    private int outSampleRate;
    
    private Pointer<?> rc;

    public AudioResampleContextWrapperLAVC(Pointer<?> resampleContext) {
        this.rc = null;
        
        inChannelLayout = AVChannelLayout.AV_CH_LAYOUT_STEREO;
        inSampleFormat = AVSampleFormat.AV_SAMPLE_FMT_S16;
        inSampleRate = 44100;
        outChannelLayout = AVChannelLayout.AV_CH_LAYOUT_STEREO;
        outSampleFormat = AVSampleFormat.AV_SAMPLE_FMT_S16;
        outSampleRate = 44100;
    }
    
    @Override
    public void clearWrapperCache() {
    }

    @Override
    public Pointer<?> getPointer() {
        return rc;
    }
    
    @Override
    public void open() throws LibavException {
        if (rc != null)
            close();
        
        int inChannelCount = AVChannelLayout.getChannelCount(inChannelLayout);
        int outChannelCount = AVChannelLayout.getChannelCount(outChannelLayout);
        rc = codecLib.av_audio_resample_init(outChannelCount, inChannelCount, outSampleRate, inSampleRate, outSampleFormat, inSampleFormat, 16, 10, 0, 0.8);
        if (rc == null)
            throw new LibavException("unable to create audio resample context");
    }
    
    @Override
    public void close() {
        if (rc == null)
            return;
        
        codecLib.audio_resample_close(rc);
        rc = null;
    }

    @Override
    public void free() {
        close();
    }
    
    @Override
    public long getInputChannelLayout() {
        return inChannelLayout;
    }

    @Override
    public void setInputChannelLayout(long channelLayout) {
        inChannelLayout = channelLayout;
    }

    @Override
    public int getInputSampleFormat() {
        return inSampleFormat;
    }

    @Override
    public void setInputSampleFormat(int sampleFormat) {
        inSampleFormat = sampleFormat;
    }

    @Override
    public int getInputSampleRate() {
        return inSampleRate;
    }

    @Override
    public void setInputSampleRate(int sampleRate) {
        inSampleRate = sampleRate;
    }

    @Override
    public long getOutputChannelLayout() {
        return outChannelLayout;
    }

    @Override
    public void setOutputChannelLayout(long channelLayout) {
        outChannelLayout = channelLayout;
    }

    @Override
    public int getOutputSampleFormat() {
        return outSampleFormat;
    }

    @Override
    public void setOutputSampleFormat(int sampleFormat) {
        outSampleFormat = sampleFormat;
    }

    @Override
    public int getOutputSampleRate() {
        return outSampleRate;
    }

    @Override
    public void setOutputSampleRate(int sampleRate) {
        outSampleRate = sampleRate;
    }
    
    @Override
    public double[] getMatrix(int stride) {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public void setMatrix(double[] matrix, int stride) {
        throw new UnsupportedOperationException("not supported");
    }
    
    @Override
    public int convert(Pointer<Pointer<?>> output, int outPlaneSize, int outSampleCount, Pointer<Pointer<?>> input, int inPlaneSize, int inSampleCount) throws LibavException {
        if (rc == null || input == null)
            return 0;
        
        Pointer<Byte> inputBuffer = input.get().as(Byte.class);
        Pointer<Byte> outputBuffer = output.get().as(Byte.class);
        int len = codecLib.audio_resample(rc, outputBuffer, inputBuffer, inSampleCount);
        if (len == 0)
            throw new LibavException("audio resample error");
        
        return len;
    }
    
    public static IAudioResampleContextWrapper allocate() {
        return new AudioResampleContextWrapperLAVC(null);
    }
    
}

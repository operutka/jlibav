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
import org.libav.avresample.bridge.AVResampleLibrary;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVChannelLayout;
import org.libav.avutil.bridge.AVOptionHandler;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVAudioResampleContext.
 * 
 * @author Ondrej Perutka
 */
public class AudioResampleContextWrapperLAVR implements IAudioResampleContextWrapper {

    private static final AVResampleLibrary resLib = LibraryManager.getInstance().getAVResampleLibrary();
    
    private Long inChannelLayout;
    private SampleFormat inSampleFormat;
    private Integer inSampleRate;
    private Long outChannelLayout;
    private SampleFormat outSampleFormat;
    private Integer outSampleRate;
    
    private Pointer<?> context;

    public AudioResampleContextWrapperLAVR(Pointer<?> context) {
        if (resLib == null)
            throw new UnsatisfiedLinkError("the avresample library is missing");
        
        this.context = context;
        
        inChannelLayout = null;
        inSampleFormat = null;
        inSampleRate = null;
        outChannelLayout = null;
        outSampleFormat = null;
        outSampleRate = null;
    }
    
    @Override
    public void clearWrapperCache() {
        inChannelLayout = null;
        inSampleFormat = null;
        inSampleRate = null;
        outChannelLayout = null;
        outSampleFormat = null;
        outSampleRate = null;
    }

    @Override
    public Pointer<?> getPointer() {
        return context;
    }

    @Override
    public void rebind(Pointer<?> pointer) {
        context = pointer;
    }
    
    @Override
    public void open() throws LibavException {
        if (context == null)
            return;
        
        int res = resLib.avresample_open(context);
        if (res != 0)
            throw new LibavException(res);
    }

    @Override
    public void close() {
        if (context == null)
            return;
        
        resLib.avresample_close(context);
    }

    @Override
    public void free() {
        if (context == null)
            return;
        
        Pointer<Pointer<?>> tmp = Pointer.allocatePointer();
        tmp.set(context);
        
        resLib.avresample_free(tmp);
        
        context = null;
    }

    @Override
    public long getInputChannelLayout() {
        if (context == null)
            return 0;
        
        try {
            if (inChannelLayout == null)
                inChannelLayout = AVOptionHandler.getLong(context, "in_channel_layout");
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        return inChannelLayout;
    }

    @Override
    public void setInputChannelLayout(long channelLayout) {
        if (context == null)
            return;
        
        try {
            AVOptionHandler.setLong(context, "in_channel_layout", channelLayout);
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        inChannelLayout = channelLayout;
    }

    @Override
    public SampleFormat getInputSampleFormat() {
        if (context == null)
            return null;
        
        try {
            if (inSampleFormat == null)
                inSampleFormat = SampleFormat.valueOf(AVOptionHandler.getInt(context, "in_sample_fmt"));
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        return inSampleFormat;
    }

    @Override
    public void setInputSampleFormat(SampleFormat sampleFormat) {
        if (context == null)
            return;
        
        try {
            AVOptionHandler.setInt(context, "in_sample_fmt", sampleFormat.value());
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        inSampleFormat = sampleFormat;
    }

    @Override
    public int getInputSampleRate() {
        if (context == null)
            return 0;
        
        try {
            if (inSampleRate == null)
                inSampleRate = AVOptionHandler.getInt(context, "in_sample_rate");
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        return inSampleRate;
    }

    @Override
    public void setInputSampleRate(int sampleRate) {
        if (context == null)
            return;
        
        try {
            AVOptionHandler.setInt(context, "in_sample_rate", sampleRate);
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        inSampleRate = sampleRate;
    }

    @Override
    public long getOutputChannelLayout() {
        if (context == null)
            return 0;
        
        try {
            if (outChannelLayout == null)
                outChannelLayout = AVOptionHandler.getLong(context, "out_channel_layout");
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        return outChannelLayout;
    }

    @Override
    public void setOutputChannelLayout(long channelLayout) {
        if (context == null)
            return;
        
        try {
            AVOptionHandler.setLong(context, "out_channel_layout", channelLayout);
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        outChannelLayout = channelLayout;
    }

    @Override
    public SampleFormat getOutputSampleFormat() {
        if (context == null)
            return null;
        
        try {
            if (outSampleFormat == null)
                outSampleFormat = SampleFormat.valueOf(AVOptionHandler.getInt(context, "out_sample_fmt"));
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        return outSampleFormat;
    }

    @Override
    public void setOutputSampleFormat(SampleFormat sampleFormat) {
        if (context == null)
            return;
        
        try {
            AVOptionHandler.setInt(context, "out_sample_fmt", sampleFormat.value());
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        outSampleFormat = sampleFormat;
    }

    @Override
    public int getOutputSampleRate() {
        if (context == null)
            return 0;
        
        try {
            if (outSampleRate == null)
                outSampleRate = AVOptionHandler.getInt(context, "out_sample_rate");
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        return outSampleRate;
    }

    @Override
    public void setOutputSampleRate(int sampleRate) {
        if (context == null)
            return;
        
        try {
            AVOptionHandler.setInt(context, "out_sample_rate", sampleRate);
        } catch (LibavException ex) {
            throw new RuntimeException(ex);
        }
        
        outSampleRate = sampleRate;
    }

    @Override
    public double[] getMatrix(int stride) throws LibavException {
        if (context == null)
            return null;
        
        inChannelLayout = null;
        outChannelLayout = null;
        int inChannelCount = AVChannelLayout.getChannelCount(getInputChannelLayout());
        int outChannelCount = AVChannelLayout.getChannelCount(getOutputChannelLayout());
        Pointer<Double> matrix = Pointer.allocateDoubles(stride * (outChannelCount - 1) + inChannelCount);
        
        int res = resLib.avresample_get_matrix(context, matrix, stride);
        if (res != 0)
            throw new LibavException(res);
        
        return matrix.getDoubles();
    }

    @Override
    public void setMatrix(double[] matrix, int stride) throws LibavException {
        if (context == null)
            return;
        
        Pointer<Double> tmp = Pointer.allocateDoubles(matrix.length);
        int res = resLib.avresample_set_matrix(context, tmp, stride);
        if (res != 0)
            throw new LibavException(res);
    }

    @Override
    public int convert(Pointer<Pointer<?>> output, int outPlaneSize, int outSampleCount, Pointer<Pointer<?>> input, int inPlaneSize, int inSampleCount) {
        if (context == null)
            return 0;
        
        return resLib.avresample_convert(context, output, outPlaneSize, outSampleCount, input, inPlaneSize, inSampleCount);
    }
    
    /**
     * Allocate a new audio resample context.
     * 
     * @return audio resample context wrapper
     */
    public static IAudioResampleContextWrapper allocate() {
        Pointer<?> tmp = resLib.avresample_alloc_context();
        if (tmp == null)
            throw new OutOfMemoryError();
        
        return new AudioResampleContextWrapperLAVR(tmp);
    }
    
}

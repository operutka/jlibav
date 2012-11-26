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
package org.libav;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import org.bridj.Pointer;
import org.libav.avcodec.CodecWrapperFactory;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avcodec.IPacketWrapper;
import org.libav.avcodec.bridge.AVCodecLibrary;
import org.libav.avformat.FormatContextWrapperFactory;
import org.libav.avformat.IFormatContextWrapper;
import org.libav.avformat.IOutputFormatWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avutil.bridge.AVChannelLayout;
import org.libav.avutil.bridge.AVMediaType;
import org.libav.avutil.bridge.PixelFormat;
import org.libav.bridge.LibraryManager;
import org.libav.util.Rational;

/**
 * Default implementation of the media writer interface.
 * 
 * @author Ondrej Perutka
 */
public class DefaultMediaWriter implements IMediaWriter {
    
    private IFormatContextWrapper formatContext;
    
    private IStreamWrapper[] streams;
    private int[] aStreams;
    private int[] vStreams;
    
    private boolean interleave;
    
    /**
     * Create a new media writer.
     * 
     * @param url a destination URL
     * @param outputFormatName a name of the output format (if it is null, the 
     * format is guessed from the given URL)
     * @throws LibavException if an error occurs while opening the output
     */
    public DefaultMediaWriter(String url, String outputFormatName) throws LibavException {
        formatContext = FormatContextWrapperFactory.getInstance().createMedia(url, outputFormatName);
        
        streams = new IStreamWrapper[0];
        aStreams = new int[0];
        vStreams = new int[0];
        
        interleave = true;
    }

    @Override
    public boolean getInterleave() {
        return interleave;
    }

    @Override
    public void setInterleave(boolean interleave) {
        this.interleave = interleave;
    }

    @Override
    public IFormatContextWrapper getFormatContext() {
        return formatContext;
    }

    private synchronized void reloadStreams() {
        if (isClosed())
            return;
        
        formatContext.clearWrapperCache();
        streams = formatContext.getStreams();
        if (streams == null)
            streams = new IStreamWrapper[0];
        
        ICodecContextWrapper[] ccs = new ICodecContextWrapper[streams.length];
        int v = 0, a = 0;
        
        for (int i = 0; i < streams.length; i++) {
            ccs[i] = streams[i].getCodecContext();
            switch (ccs[i].getCodecType()) {
                case AVMediaType.AVMEDIA_TYPE_VIDEO: v++; break;
                case AVMediaType.AVMEDIA_TYPE_AUDIO: a++; break;
                default: break;
            }
        }
        
        vStreams = new int[v];
        aStreams = new int[a];
        
        v = a = 0;
        for (int i = 0; i < streams.length; i++) {
            switch (ccs[i].getCodecType()) {
                case AVMediaType.AVMEDIA_TYPE_VIDEO: vStreams[v++] = i; break;
                case AVMediaType.AVMEDIA_TYPE_AUDIO: aStreams[a++] = i; break;
                default: break;
            }
        }
    }
    
    @Override
    public int getStreamCount() {
        return streams.length;
    }

    @Override
    public IStreamWrapper getStream(int streamIndex) {
        return streams[streamIndex];
    }
    
    @Override
    public int getVideoStreamCount() {
        return vStreams.length;
    }

    @Override
    public synchronized int addVideoStream(int codecId, int width, int height) throws LibavException {
        if (isClosed())
            throw new IllegalStateException("the media stream has been closed");
        
        IStreamWrapper stream = formatContext.newStream();
        stream.setTimeBase(new Rational(1, 1000));
        
        ICodecContextWrapper cc = stream.getCodecContext();
        cc.setCodecId(codecId);
        cc.setCodecType(AVMediaType.AVMEDIA_TYPE_VIDEO);
        cc.setBitRate(1000000);
        cc.setWidth(width);
        cc.setHeight(height);
        cc.setTimeBase(new Rational(1, 25));
        cc.setGopSize(12);
        cc.setPixelFormat(PixelFormat.PIX_FMT_YUV420P);
        
        if (codecId == CodecWrapperFactory.CODEC_ID_MPEG2VIDEO)
            cc.setMaxBFrames(2);
        
        IOutputFormatWrapper ofw = formatContext.getOutputFormat();
        if ((ofw.getFlags() & AVFormatLibrary.AVFMT_GLOBALHEADER) != 0)
            cc.setFlags(cc.getFlags() | AVCodecLibrary.CODEC_FLAG_GLOBAL_HEADER);
        
        stream.setTimeBase(new Rational(1, 1000));
        
        reloadStreams();
        return vStreams.length - 1;
    }

    @Override
    public IStreamWrapper getVideoStream(int videoStreamIndex) {
        return getStream(vStreams[videoStreamIndex]);
    }

    @Override
    public int getAudioStreamCount() {
        return aStreams.length;
    }

    @Override
    public synchronized int addAudioStream(int codecId, int sampleRate, int sampleFormat, int channelCount) throws LibavException {
        if (isClosed())
            throw new IllegalStateException("the media stream has been closed");
        
        IStreamWrapper stream = formatContext.newStream();
        ICodecContextWrapper cc = stream.getCodecContext();
        
        cc.setCodecType(AVMediaType.AVMEDIA_TYPE_AUDIO);
        cc.setCodecId(codecId);
        cc.setBitRate(192000);
        cc.setSampleRate(sampleRate);
        cc.setSampleFormat(sampleFormat);
        cc.setChannels(channelCount);
        cc.setChannelLayout(AVChannelLayout.getDefaultChannelLayout(channelCount));
        
        IOutputFormatWrapper ofw = formatContext.getOutputFormat();
        if ((ofw.getFlags() & AVFormatLibrary.AVFMT_GLOBALHEADER) != 0)
            cc.setFlags(cc.getFlags() | AVCodecLibrary.CODEC_FLAG_GLOBAL_HEADER);
        
        stream.setTimeBase(new Rational(1, 1000));
        
        reloadStreams();
        return aStreams.length - 1;
    }

    @Override
    public IStreamWrapper getAudioStream(int audioStreamIndex) {
        return getStream(aStreams[audioStreamIndex]);
    }

    @Override
    public synchronized void writeHeader() throws LibavException {
        if (isClosed())
            return;
        
        formatContext.writeHeader();
    }

    @Override
    public synchronized void writeTrailer() throws LibavException {
        if (isClosed())
            return;
        
        formatContext.writeTrailer();
    }

    @Override
    public synchronized void processPacket(Object producer, IPacketWrapper packet) throws LibavException {
        if (interleave)
            formatContext.interleavedWritePacket(packet);
        else
            formatContext.writePacket(packet);
    }

    @Override
    public synchronized String getSdp() throws LibavException {
        if (isClosed())
            throw new IllegalStateException("the media stream has been closed");
        
        return getSdp(new IFormatContextWrapper[] { formatContext });
    }
    
    @Override
    public synchronized void createSdpFile(String fileName) throws LibavException, FileNotFoundException {
        if (isClosed())
            throw new IllegalStateException("the media stream has been closed");
        
        createSdpFile(fileName, new IFormatContextWrapper[] { formatContext });
    }

    @Override
    public synchronized void close() throws LibavException {
        reloadStreams();
        for (int i = 0; i < streams.length; i++) {
            streams[i].getCodecContext().free();
            streams[i].setCodecContext(null);
            streams[i].free();
            formatContext.setStream(i, null);
        }
        formatContext.close();
        
        formatContext = null;
        streams = new IStreamWrapper[0];
        aStreams = new int[0];
        vStreams = new int[0];
    }

    @Override
    public boolean isClosed() {
        return formatContext == null;
    }
    
    public static String getSdp(DefaultMediaWriter[] mediaWriter) throws LibavException {
        IFormatContextWrapper[] contexts = new IFormatContextWrapper[mediaWriter.length];
        for (int i = 0; i < mediaWriter.length; i++)
            contexts[i] = mediaWriter[i].getFormatContext();
        
        return getSdp(contexts);
    }
    
    public static String getSdp(IFormatContextWrapper[] contexts) throws LibavException {
        AVFormatLibrary formatLib = LibraryManager.getInstance().getAVFormatLibrary();
        Pointer<Byte> data;
        
        Pointer<Pointer<?>> fcs = Pointer.allocatePointers(contexts.length);
        for (int i = 0; i < contexts.length; i++) {
            contexts[i].clearWrapperCache();
            if (contexts[i].getPrivateData() == null)
                throw new LibavException("all headers must be written before creating an SDP");
            fcs.set(i, contexts[i].getPointer());
        }
        
        data = Pointer.allocateBytes(4096);
        if (data == null)
            throw new LibavException("unable to allocate memory to create the SDP");
        if (formatLib.av_sdp_create(fcs, contexts.length, data, 4096) != 0)
            throw new LibavException("unable to generate the SDP");

        return data.getStringAtOffset(0, Pointer.StringType.C, Charset.forName("UTF-8"));
    }
    
    public static void createSdpFile(String fileName, DefaultMediaWriter[] mediaWriter) throws LibavException, FileNotFoundException {
        IFormatContextWrapper[] contexts = new IFormatContextWrapper[mediaWriter.length];
        for (int i = 0; i < mediaWriter.length; i++)
            contexts[i] = mediaWriter[i].getFormatContext();
        
        createSdpFile(fileName, contexts);
    }
    
    public static void createSdpFile(String fileName, IFormatContextWrapper[] contexts) throws LibavException, FileNotFoundException {
        PrintWriter pw = null;
        
        try {
            pw = new PrintWriter(fileName);
            pw.printf("SDP:\n%s\n", getSdp(contexts));
            pw.flush();
        } finally {
            if (pw != null)
                pw.close();
        }
    }
    
}

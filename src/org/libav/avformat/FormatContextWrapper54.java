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
package org.libav.avformat;

import java.nio.charset.Charset;
import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.IPacketWrapper;
import org.libav.avformat.bridge.AVFormatContext54;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVFormatContext54.
 * 
 * @author Ondrej Perutka
 */
public class FormatContextWrapper54 extends AbstractFormatContextWrapper {
    
    private static final AVFormatLibrary formatLib;
    
    static {
        formatLib = LibraryManager.getInstance().getAVFormatLibrary();
    }
    
    private AVFormatContext54 context;
    private boolean outputContext;
    
    /**
     * Create a new wrapper for the given format context.
     * 
     * @param context a format context structure
     */
    public FormatContextWrapper54(AVFormatContext54 context) {
        this.context = context;
        this.outputContext = false;
    }
    
    @Override
    public Pointer<?> getPointer() {
        if (isClosed())
            return null;
        
        return Pointer.pointerTo(context);
    }
    
    @Override
    public void close() {
        if (isClosed())
            return;
        
        if (outputContext) {
            if (getIOContext() != null && (getOutputFormat().getFlags() & AVFormatLibrary.AVFMT_NOFILE) == 0)
                formatLib.avio_close(getIOContext().getPointer());
            LibraryManager.getInstance().getAVUtilLibrary().av_free(getPointer());
        } else {
            Pointer<Pointer<?>> ps = Pointer.allocatePointer();
            ps.set(getPointer());
            formatLib.avformat_close_input(ps);
        }
        
        context = null;
        streams = null;
    }
    
    @Override
    public boolean isClosed() {
        return context == null;
    }
    
    @Override
    public void findStreamInfo() throws LibavException {
        if (isClosed())
            return;
        
        int result = formatLib.avformat_find_stream_info(getPointer(), null);
        if (result < 0)
            throw new LibavException(result);
    }
    
    @Override
    public IStreamWrapper[] getStreams() {
        if (isClosed())
            return null;
        
        if (streams != null)
            return streams;
        
        getStreamCount();
        Pointer<Pointer<?>> pStrms = context.streams();
        if (pStrms == null)
            return null;
        
        Pointer<?> pStrm;
        streams = new IStreamWrapper[streamCount];
        for (int i = 0; i < streamCount; i++) {
            pStrm = pStrms.get(i);
            streams[i] = pStrm == null ? null : StreamWrapperFactory.getInstance().wrap(pStrm);
        }
        
        return streams;
    }

    @Override
    public int getStreamCount() {
        if (isClosed())
            return 0;
        
        if (streamCount == null)
            streamCount = context.nb_streams();
        
        return streamCount;
    }
    
    @Override
    public void setStream(int streamIndex, IStreamWrapper stream) {
        if (isClosed())
            return;
        
        getStreams();
        streams[streamIndex] = stream;
        context.streams().set(streamIndex, stream == null ? null : stream.getPointer());
    }
    
    @Override
    public IStreamWrapper newStream() throws LibavException {
        if (isClosed())
            return null;
        
        Pointer pStream = formatLib.avformat_new_stream(getPointer(), null);
        if (pStream == null)
            throw new LibavException("unable to create a new stream");
        
        streams = null;
        
        return StreamWrapperFactory.getInstance().wrap(pStream);
    }

    @Override
    public String getFileName() {
        if (isClosed())
            return null;
        
        if (fileName == null)
            fileName = context.filename().getStringAtOffset(0, Pointer.StringType.C, Charset.forName("UTF-8"));
        
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        if (isClosed())
            return;
        
        this.fileName = fileName;
        context.filename().setStringAtOffset(0, fileName, Pointer.StringType.C, Charset.forName("UTF-8"));
    }

    @Override
    public IIOContextWrapper getIOContext() {
        if (isClosed())
            return null;
        
        if (ioContext == null) {
            Pointer<?> p = context.pb();
            ioContext = p == null ? null : IOContextWrapperFactory.getInstance().wrap(p);
        }
        
        return ioContext;
    }

    @Override
    public void setIOContext(IIOContextWrapper ioContext) {
        if (isClosed())
            return;
        
        this.ioContext = ioContext;
        context.pb(ioContext == null ? null : ioContext.getPointer());
    }

    @Override
    public IOutputFormatWrapper getOutputFormat() {
        if (isClosed())
            return null;
        
        if (outputFormat == null) {
            Pointer<?> p = context.oformat();
            outputFormat = p == null ? null : OutputFormatWrapperFactory.getInstance().wrap(p);
        }
        
        return outputFormat;
    }

    @Override
    public void setOutputFormat(IOutputFormatWrapper outputFormat) {
        if (isClosed())
            return;
        
        this.outputFormat = outputFormat;
        context.oformat(outputFormat == null ? null : outputFormat.getPointer());
    }
    
    @Override
    public IInputFormatWrapper getInputFormat() {
        if (isClosed())
            return null;
        
        if (inputFormat == null) {
            Pointer<?> p = context.iformat();
            inputFormat = p == null ? null : InputFormatWrapperFactory.getInstance().wrap(p);
        }
        
        return inputFormat;
    }
    
    @Override
    public long getDuration() {
        if (isClosed())
            return 0;
        
        if (duration == null)
            duration = context.duration() * 1000 / AVUtilLibrary.AV_TIME_BASE;
        
        return duration;
    }
    
    @Override
    public Pointer<?> getPrivateData() {
        if (isClosed())
            return null;
        
        if (privateData == null)
            privateData = context.priv_data();
        
        return privateData;
    }
    
    @Override
    public void play() {
        if (isClosed())
            return;
        
        formatLib.av_read_play(getPointer());
    }
    
    @Override
    public void pause() {
        if (isClosed())
            return;
        
        formatLib.av_read_pause(getPointer());
    }
    
    @Override
    public boolean readNextPacket(IPacketWrapper packet) {
        if (isClosed())
            return false;
        
        int result = formatLib.av_read_frame(getPointer(), packet.getPointer());
        packet.clearWrapperCache();
        
        return result >= 0;
    }
    
    @Override
    public void writeHeader() throws LibavException {
        if (isClosed())
            return;
        
        int result = formatLib.avformat_write_header(getPointer(), null);
        if (result < 0)
            throw new LibavException(result);
    }
    
    @Override
    public void writeTrailer() throws LibavException {
        if (isClosed())
            return;
        
        int res = formatLib.av_write_trailer(getPointer());
        if (res != 0)
            throw new LibavException(res);
    }
    
    @Override
    public void writePacket(IPacketWrapper packet) throws LibavException {
        if (isClosed())
            return;
        
        int res = formatLib.av_write_frame(getPointer(), packet.getPointer());
        if (res != 0)
            throw new LibavException(res);
    }
    
    @Override
    public void interleavedWritePacket(IPacketWrapper packet) throws LibavException {
        if (isClosed())
            return;
        
        int res = formatLib.av_interleaved_write_frame(getPointer(), packet.getPointer());
        if (res != 0)
            throw new LibavException(res);
    }
    
    @Override
    public void seekFile(long minTime, long time, long maxTime) throws LibavException {
        if (isClosed())
            return;
        
        long tb = AVUtilLibrary.AV_TIME_BASE / 1000;
        formatLib.avformat_seek_file(getPointer(), -1, minTime * tb, time * tb, maxTime * tb, 0);
    }
    
    private static FormatContextWrapper54 allocateContext() throws LibavException {
        Pointer ptr = formatLib.avformat_alloc_context();
        if (ptr == null)
            throw new LibavException("unable to allocate a format context");
        
        return new FormatContextWrapper54(new AVFormatContext54(ptr));
    }
    
    public static FormatContextWrapper54 openMedia(String url) throws LibavException {
        Pointer<Byte> purl = Pointer.pointerToString(url, Pointer.StringType.C, Charset.forName("UTF-8")).as(Byte.class);
        
        Pointer<Pointer<?>> avfcByRef = Pointer.allocatePointer();
        int result = formatLib.avformat_open_input(avfcByRef, purl, null, null);
        if (result < 0)
            throw new LibavException(result);
        
        return new FormatContextWrapper54(new AVFormatContext54(avfcByRef.get()));
    }
    
    public static FormatContextWrapper54 createMedia(String url, String outputFormatName) throws LibavException {
        Pointer<Byte> purl = Pointer.pointerToString(url, Pointer.StringType.C, Charset.forName("UTF-8")).as(Byte.class);
        FormatContextWrapper54 result = allocateContext();
        result.outputContext = true;
        
        Pointer<Byte> ofn = outputFormatName == null ? null : Pointer.pointerToString(outputFormatName, Pointer.StringType.C, Charset.forName("UTF-8")).as(Byte.class);
        Pointer of = formatLib.av_guess_format(ofn, purl, ofn);
        if (of == null)
            throw new LibavException("unknown format: " + outputFormatName);
        result.setOutputFormat(OutputFormatWrapperFactory.getInstance().wrap(of));
        result.setFileName(url);
        
        Pointer<Pointer<?>> avioc = Pointer.allocatePointer();
        avioc.set(null);
        if ((result.getOutputFormat().getFlags() & AVFormatLibrary.AVFMT_NOFILE) == 0) {
            result.setIOContext(null);
            int res = formatLib.avio_open(avioc, purl, AVFormatLibrary.AVIO_FLAG_WRITE);
            if (res < 0) {
                result.close();
                throw new LibavException(res);
            }
            result.setIOContext(avioc.get() == null ? null : IOContextWrapperFactory.getInstance().wrap(avioc.get()));
        }
        
        return result;
    }
    
}

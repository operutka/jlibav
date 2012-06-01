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

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.nio.charset.Charset;
import org.libav.LibavException;
import org.libav.avcodec.IPacketWrapper;
import org.libav.avformat.bridge.AVFormatContext53;
import org.libav.avformat.bridge.IAVFormatLibrary;
import org.libav.avutil.bridge.IAVUtilLibrary;
import org.libav.bridge.CustomNativeString;
import org.libav.bridge.LibavLibraryWrapper;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVFormatContext53.
 * 
 * @author Ondrej Perutka
 */
public class FormatContextWrapper53 extends AbstractFormatContextWrapper {
    
    private static final IAVFormatLibrary formatLib;
    
    private static final boolean avfOpenInput;
    private static final boolean avfWriteHeader;
    private static final boolean avfFindStreamInfo;
    private static final boolean avfNewStream;
    private static final boolean avfCloseInput;
    
    static {
        LibavLibraryWrapper<IAVFormatLibrary> flw = LibraryManager.getInstance().getAVFormatLibraryWrapper();
        formatLib = flw.getLibrary();
        
        avfOpenInput = flw.functionExists("avformat_open_input");
        avfWriteHeader = flw.functionExists("avformat_write_header");
        avfFindStreamInfo = flw.functionExists("avformat_find_stream_info");
        avfNewStream = flw.functionExists("avformat_new_stream");
        avfCloseInput = flw.functionExists("avformat_close_input");
    }
    
    private AVFormatContext53 context;
    private boolean outputContext;
    
    /**
     * Create a new wrapper for the given format context.
     * 
     * @param context a format context structure
     */
    public FormatContextWrapper53(AVFormatContext53 context) {
        this.context = context;
        this.outputContext = false;
    }
    
    @Override
    public Pointer getPointer() {
        if (isClosed())
            return null;
        
        return context.getPointer();
    }
    
    @Override
    public void close() {
        if (isClosed())
            return;
        
        if (outputContext) {
            if (getIOContext() != null && (getOutputFormat().getFlags() & IAVFormatLibrary.AVFMT_NOFILE) == 0)
                formatLib.avio_close(getIOContext().getPointer());
            LibraryManager.getInstance().getAVUtilLibraryWrapper().getLibrary().av_free(context.getPointer());
        } else if (avfCloseInput) {
            PointerByReference ps = new PointerByReference(context.getPointer());
            formatLib.avformat_close_input(ps);
        } else
            formatLib.av_close_input_file(context.getPointer());
        
        context = null;
        streams = null;
        outputFormat = null;
    }
    
    @Override
    public boolean isClosed() {
        return context == null;
    }
    
    @Override
    public void findStreamInfo() throws LibavException {
        if (isClosed())
            return;
        
        int result;
        if (avfFindStreamInfo)
            result = formatLib.avformat_find_stream_info(context.getPointer(), null);
        else
            result = formatLib.av_find_stream_info(context.getPointer());
        
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
        Pointer pStrms = (Pointer)context.readField("streams");
        if (pStrms == null)
            return null;
        
        Pointer[] strms = pStrms.getPointerArray(0, streamCount);
        streams = new IStreamWrapper[streamCount];
        for (int i = 0; i < streamCount; i++)
            streams[i] = strms[i] == null ? null : StreamWrapperFactory.getInstance().wrap(strms[i]);
        
        return streams;
    }

    @Override
    public int getStreamCount() {
        if (isClosed())
            return 0;
        
        if (streamCount == null)
            streamCount = (Integer)context.readField("nb_streams");
        
        return streamCount;
    }
    
    @Override
    public void setStream(int streamIndex, IStreamWrapper stream) {
        if (isClosed())
            return;
        
        getStreams();
        streams[streamIndex] = stream;
        Pointer pStrms = (Pointer)context.readField("streams");
        pStrms.write(Native.POINTER_SIZE * streamIndex, new Pointer[] { stream == null ? null : stream.getPointer() }, 0, 1);
    }
    
    @Override
    public IStreamWrapper newStream() throws LibavException {
        if (isClosed())
            return null;
        
        Pointer pStream;
        if (avfNewStream)
            pStream = formatLib.avformat_new_stream(context.getPointer(), null);
        else
            pStream = formatLib.av_new_stream(context.getPointer(), 0);
        
        if (pStream == null)
            throw new LibavException("unable to create a new stream");
        
        streams = null;
        
        return StreamWrapperFactory.getInstance().wrap(pStream);
    }

    @Override
    public String getFileName() {
        if (isClosed())
            return null;
        
        if (fileName == null) {
            byte[] data = (byte[])context.readField("filename");
            fileName = new String(data, Charset.forName("UTF-8"));
        }
        
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        if (isClosed())
            return;
        
        this.fileName = fileName;
        byte[] tmp = fileName.getBytes(Charset.forName("UTF-8"));
        byte[] data = new byte[AVFormatContext53.MAX_FILENAME_LENGTH];
        for (int i = 0; i < data.length; i++) {
            if (i < tmp.length)
                data[i] = tmp[i];
            else
                data[i] = 0;
        }
        data[data.length - 1] = 0;
        context.writeField("filename", data);
    }

    @Override
    public IIOContextWrapper getIOContext() {
        if (isClosed())
            return null;
        
        if (ioContext == null) {
            Pointer p = (Pointer)context.readField("pb");
            ioContext = p == null ? null : IOContextWrapperFactory.getInstance().wrap(p);
        }
        
        return ioContext;
    }

    @Override
    public void setIOContext(IIOContextWrapper ioContext) {
        if (isClosed())
            return;
        
        this.ioContext = ioContext;
        context.writeField("pb", ioContext == null ? null : ioContext.getPointer());
    }

    @Override
    public IOutputFormatWrapper getOutputFormat() {
        if (isClosed())
            return null;
        
        if (outputFormat == null) {
            Pointer p = (Pointer)context.readField("oformat");
            outputFormat = p == null ? null : OutputFormatWrapperFactory.getInstance().wrap(p);
        }
        
        return outputFormat;
    }

    @Override
    public void setOutputFormat(IOutputFormatWrapper outputFormat) {
        if (isClosed())
            return;
        
        this.outputFormat = outputFormat;
        context.writeField("oformat", outputFormat == null ? null : outputFormat.getPointer());
    }
    
    @Override
    public IInputFormatWrapper getInputFormat() {
        if (isClosed())
            return null;
        
        if (inputFormat == null) {
            Pointer p = (Pointer)context.readField("iformat");
            inputFormat = p == null ? null : InputFormatWrapperFactory.getInstance().wrap(p);
        }
        
        return inputFormat;
    }
    
    @Override
    public long getDuration() {
        if (isClosed())
            return 0;
        
        if (duration == null)
            duration = (Long)context.readField("duration") * 1000 / IAVUtilLibrary.AV_TIME_BASE;
        
        return duration;
    }
    
    @Override
    public Pointer getPrivateData() {
        if (isClosed())
            return null;
        
        if (privateData == null)
            privateData = (Pointer)context.readField("priv_data");
        
        return privateData;
    }
    
    @Override
    public void play() {
        if (isClosed())
            return;
        
        formatLib.av_read_play(context.getPointer());
    }
    
    @Override
    public void pause() {
        if (isClosed())
            return;
        
        formatLib.av_read_pause(context.getPointer());
    }
    
    @Override
    public boolean readNextPacket(IPacketWrapper packet) {
        if (isClosed())
            return false;
        
        int result = formatLib.av_read_frame(context.getPointer(), packet.getPointer());
        packet.clearWrapperCache();
        
        return result >= 0;
    }
    
    @Override
    public void writeHeader() throws LibavException {
        if (isClosed())
            return;
        
        int result;
        if (avfWriteHeader)
            result = formatLib.avformat_write_header(context.getPointer(), null);
        else
            result = formatLib.av_write_header(context.getPointer());
        
        if (result < 0)
            throw new LibavException(result);
    }
    
    @Override
    public void writeTrailer() throws LibavException {
        if (isClosed())
            return;
        
        int res = formatLib.av_write_trailer(context.getPointer());
        if (res != 0)
            throw new LibavException(res);
    }
    
    @Override
    public void writePacket(IPacketWrapper packet) throws LibavException {
        if (isClosed())
            return;
        
        int res = formatLib.av_write_frame(context.getPointer(), packet.getPointer());
        if (res != 0)
            throw new LibavException(res);
    }
    
    @Override
    public void interleavedWritePacket(IPacketWrapper packet) throws LibavException {
        if (isClosed())
            return;
        
        int res = formatLib.av_interleaved_write_frame(context.getPointer(), packet.getPointer());
        if (res != 0)
            throw new LibavException(res);
    }
    
    @Override
    public void seekFile(long minTime, long time, long maxTime) throws LibavException {
        if (isClosed())
            return;
        
        long tb = IAVUtilLibrary.AV_TIME_BASE / 1000;
        formatLib.avformat_seek_file(context.getPointer(), -1, minTime * tb, time * tb, maxTime * tb, 0);
    }
    
    private static FormatContextWrapper53 allocateContext() throws LibavException {
        Pointer ptr = formatLib.avformat_alloc_context();
        if (ptr == null)
            throw new LibavException("unable to allocate a format context");
        
        return new FormatContextWrapper53(new AVFormatContext53(ptr));
    }
    
    public static FormatContextWrapper53 openMedia(String url) throws LibavException {
        CustomNativeString cnsUrl = new CustomNativeString(url, "UTF-8", 1);
        
        int result;
        PointerByReference avfcByRef = new PointerByReference();
        if (avfOpenInput)
            result = formatLib.avformat_open_input(avfcByRef, cnsUrl.getPointer(), null, null);
        else
            result = formatLib.av_open_input_file(avfcByRef, cnsUrl.getPointer(), null, 0, null);
        
        if (result < 0)
            throw new LibavException(result);
        
        return new FormatContextWrapper53(new AVFormatContext53(avfcByRef.getValue()));
    }
    
    public static FormatContextWrapper53 createMedia(String url, String outputFormatName) throws LibavException {
        CustomNativeString cnsUrl = new CustomNativeString(url, "UTF-8", 1);
        FormatContextWrapper53 result = allocateContext();
        result.outputContext = true;
        
        CustomNativeString ofn = outputFormatName == null ? null : new CustomNativeString(outputFormatName, "UTF-8", 1);
        Pointer pOfn = ofn == null ? null : ofn.getPointer();
        Pointer of = formatLib.av_guess_format(pOfn, cnsUrl.getPointer(), pOfn);
        if (of == null)
            throw new LibavException("unknown format: " + outputFormatName);
        result.setOutputFormat(OutputFormatWrapperFactory.getInstance().wrap(of));
        result.setFileName(url);
        
        PointerByReference avioc = new PointerByReference(null);
        if ((result.getOutputFormat().getFlags() & IAVFormatLibrary.AVFMT_NOFILE) == 0) {
            result.setIOContext(null);
            int res = formatLib.avio_open(avioc, cnsUrl.getPointer(), IAVFormatLibrary.AVIO_FLAG_WRITE);
            if (res < 0) {
                result.close();
                throw new LibavException(res);
            }
            result.setIOContext(avioc.getValue() == null ? null : IOContextWrapperFactory.getInstance().wrap(avioc.getValue()));
        }
        
        return result;
    }
    
}

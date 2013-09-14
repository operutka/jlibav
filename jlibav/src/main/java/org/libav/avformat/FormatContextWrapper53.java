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
import java.util.Date;
import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avcodec.IPacketWrapper;
import org.libav.avformat.bridge.AVFormatContext53;
import org.libav.avformat.bridge.AVFormatLibrary;
import org.libav.avutil.DictionaryWrapperFactory;
import org.libav.avutil.IDictionaryWrapper;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVFormatContext53.
 * 
 * @author Ondrej Perutka
 */
public class FormatContextWrapper53 extends AbstractFormatContextWrapper {
    
    private static final AVFormatLibrary formatLib;
    private static final AVUtilLibrary utilLib;
    
    private static final boolean avfOpenInput;
    private static final boolean avfWriteHeader;
    private static final boolean avfFindStreamInfo;
    private static final boolean avfNewStream;
    private static final boolean avfCloseInput;
    
    static {
        formatLib = LibraryManager.getInstance().getAVFormatLibrary();
        utilLib = LibraryManager.getInstance().getAVUtilLibrary();
        
        avfOpenInput = formatLib.functionExists("avformat_open_input");
        avfWriteHeader = formatLib.functionExists("avformat_write_header");
        avfFindStreamInfo = formatLib.functionExists("avformat_find_stream_info");
        avfNewStream = formatLib.functionExists("avformat_new_stream");
        avfCloseInput = formatLib.functionExists("avformat_close_input");
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
    public void clearWrapperCache() {
        super.clearWrapperCache();
        
        rebindStreams();
        rebindChapters();
        rebindIOContext();
        rebindInputFormat();
        rebindOutputFormat();
        rebindMetadata();
    }
    
    private void rebindStreams() {
        if (context == null || streams == null)
            return;
        
        Pointer<Pointer<?>> pPtr = context.streams();
        Pointer ptr;
        
        streamCount = context.nb_streams();
        
        if (pPtr == null)
            streams = null;
        else {
            IStreamWrapper[] newStreams = new IStreamWrapper[streamCount];
            for (int i = 0; i < newStreams.length; i++) {
                ptr = pPtr.get(i);
                if (ptr == null)
                    newStreams[i] = null;
                else if  (i < streams.length && ptr.equals(streams[i].getPointer()))
                    newStreams[i] = streams[i];
                else
                    newStreams[i] = StreamWrapperFactory.getInstance().wrap(ptr);
            }
            streams = newStreams;
        }
    }
    
    private void rebindChapters() {
        if (context == null || chapters == null)
            return;
        
        Pointer<Pointer<?>> pPtr = context.chapters();
        Pointer ptr;
        
        chapterCount = context.nb_chapters();
        
        if (pPtr == null)
            chapters = null;
        else {
            IChapterWrapper[] newChapters = new IChapterWrapper[chapterCount];
            for (int i = 0; i < newChapters.length; i++) {
                ptr = pPtr.get(i);
                if (ptr == null)
                    newChapters[i] = null;
                else if  (i < chapters.length && ptr.equals(chapters[i].getPointer()))
                    newChapters[i] = chapters[i];
                else
                    newChapters[i] = ChapterWrapperFactory.getInstance().wrap(ptr);
            }
            chapters = newChapters;
        }
    }
    
    private void rebindIOContext() {
        if (context == null || ioContext == null)
            return;
        
        Pointer<?> ptr = context.pb();
        if (ptr == null || !ptr.equals(ioContext.getPointer()))
            ioContext = null;
    }
    
    private void rebindInputFormat() {
        if (context == null || inputFormat == null)
            return;
        
        Pointer<?> ptr = context.iformat();
        if (ptr == null || !ptr.equals(inputFormat.getPointer()))
            inputFormat = null;
    }
    
    private void rebindOutputFormat() {
        if (context == null || outputFormat == null)
            return;
        
        Pointer<?> ptr = context.oformat();
        if (ptr == null || !ptr.equals(outputFormat.getPointer()))
            outputFormat = null;
    }
    
    private void rebindMetadata() {
        if (context == null || metadata == null)
            return;
        
        Pointer<?> ptr = context.metadata();
        if (ptr == null || !ptr.equals(metadata.getPointer()))
            metadata = null;
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
        } else if (avfCloseInput) {
            Pointer<Pointer<?>> ps = Pointer.allocatePointer();
            ps.set(getPointer());
            formatLib.avformat_close_input(ps);
        } else
            formatLib.av_close_input_file(getPointer());
        
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
            result = formatLib.avformat_find_stream_info(getPointer(), null);
        else
            result = formatLib.av_find_stream_info(getPointer());
        
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
        
        rebindStreams();
        getStreams();
        streams[streamIndex] = stream;
        Pointer<Pointer<?>> pStrms = context.streams();
        pStrms.set(streamIndex, stream == null ? null : stream.getPointer());
    }
    
    @Override
    public IStreamWrapper newStream() throws LibavException {
        if (isClosed())
            return null;
        
        Pointer pStream;
        if (avfNewStream)
            pStream = formatLib.avformat_new_stream(getPointer(), null);
        else
            pStream = formatLib.av_new_stream(getPointer(), 0);
        
        if (pStream == null)
            throw new LibavException("unable to create a new stream");
        
        rebindStreams();
        getStreams();
        for (IStreamWrapper stream : streams) {
            if (pStream.equals(stream.getPointer()))
                return stream;
        }
        
        return null;
    }

    @Override
    public IChapterWrapper[] getChapters() {
        if (isClosed())
            return null;
        
        if (chapters != null)
            return chapters;
        
        getChapterCount();
        Pointer<Pointer<?>> pChapters = context.chapters();
        if (pChapters == null && chapterCount != 0)
            return null;
        
        Pointer<?> pChapter;
        chapters = new IChapterWrapper[chapterCount];
        for (int i = 0; i < chapterCount; i++) {
            pChapter = pChapters.get(i);
            chapters[i] = pChapter == null ? null : ChapterWrapperFactory.getInstance().wrap(pChapter);
        }
        
        return chapters;
    }

    @Override
    public int getChapterCount() {
        if (isClosed())
            return 0;
        
        if (chapterCount == null)
            chapterCount = context.nb_chapters();
        
        return chapterCount;
    }

    @Override
    public boolean addChapter(IChapterWrapper chapter) {
        if (isClosed())
            return false;
        
        Pointer<?> pChapter = chapter.getPointer();
        
        IChapterWrapper[] tmp = getChapters();
        if (tmp == null)
            return false;
        
        for (int i = 0; i < tmp.length; i++) {
            if (pChapter.equals(tmp[i].getPointer()))
                return false;
            else if (chapter.getId() == tmp[i].getId())
                return false;
        }
        
        Pointer<Pointer<?>> pNewChapters = (Pointer)utilLib.av_malloc(Pointer.SIZE * (tmp.length + 1)).as(Pointer.class);
        for (int i = 0; i < tmp.length; i++)
            pNewChapters.set(i, tmp[i].getPointer());
        pNewChapters.set(tmp.length, pChapter);
        
        Pointer<Pointer<?>> pChapters = context.chapters();
        context.chapters(pNewChapters);
        utilLib.av_free(pChapters);
        
        rebindChapters();
        
        return true;
    }

    @Override
    public IChapterWrapper removeChapter(int id) {
        if (isClosed())
            return null;
        
        IChapterWrapper[] tmp = getChapters();
        if (tmp == null)
            return null;
        
        int i = 0;
        for (; i < tmp.length; i++) {
            if (tmp[i].getId() == id)
                break;
        }
        
        if (i == tmp.length)
            return null;
        
        IChapterWrapper result = tmp[i];
        Pointer<Pointer<?>> pChapters = context.chapters();
        for (; i < (tmp.length - 1); i++)
            pChapters.set(i, pChapters.get(i + 1));
        
        rebindChapters();
        
        return result;
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
            Pointer p = context.pb();
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
    public Date getRealStartTime() {
        if (isClosed())
            return null;
        
        if (realStartTime == null) {
            long rst = context.start_time_realtime() >>> 1;
            realStartTime = new Date(rst / 500);
        }
        
        return realStartTime;
    }

    @Override
    public void setRealStartTime(Date realStartTime) {
        if (isClosed())
            return;
        
        context.start_time_realtime(realStartTime.getTime() * 1000);
        this.realStartTime = realStartTime;
    }

    @Override
    public IDictionaryWrapper getMetadata() {
        if (isClosed())
            return null;
        
        if (metadata == null) {
            DictionaryWrapperFactory dwf = DictionaryWrapperFactory.getInstance();
            if (context.metadata() == null) {
                metadata = dwf.allocate();
                context.metadata(metadata.getPointer());
            } else
                metadata = dwf.wrap(context.metadata());
        }
        
        return metadata;
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
        
        int result;
        if (avfWriteHeader)
            result = formatLib.avformat_write_header(getPointer(), null);
        else
            result = formatLib.av_write_header(getPointer());
        
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
    
    private static FormatContextWrapper53 allocateContext() throws LibavException {
        Pointer ptr = formatLib.avformat_alloc_context();
        if (ptr == null)
            throw new LibavException("unable to allocate a format context");
        
        return new FormatContextWrapper53(new AVFormatContext53(ptr));
    }
    
    public static FormatContextWrapper53 openMedia(String url) throws LibavException {
        Pointer<Byte> purl = Pointer.pointerToString(url, Pointer.StringType.C, Charset.forName("UTF-8")).as(Byte.class);
        
        int result;
        Pointer<Pointer<?>> avfcByRef = Pointer.allocatePointer();
        if (avfOpenInput)
            result = formatLib.avformat_open_input(avfcByRef, purl, null, null);
        else
            result = formatLib.av_open_input_file(avfcByRef, purl, null, 0, null);
        
        if (result < 0)
            throw new LibavException(result);
        
        return new FormatContextWrapper53(new AVFormatContext53(avfcByRef.get()));
    }
    
    public static FormatContextWrapper53 createMedia(String url, String outputFormatName) throws LibavException {
        Pointer<Byte> purl = Pointer.pointerToString(url, Pointer.StringType.C, Charset.forName("UTF-8")).as(Byte.class);
        FormatContextWrapper53 result = allocateContext();
        result.outputContext = true;
        
        Pointer<Byte> ofn = outputFormatName == null ? null : Pointer.pointerToString(outputFormatName, Pointer.StringType.C, Charset.forName("UTF-8")).as(Byte.class);
        Pointer<?> of = formatLib.av_guess_format(ofn, purl, ofn);
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

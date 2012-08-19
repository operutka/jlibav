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

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.libav.avformat.bridge.AVChapter;
import org.libav.avutil.DictionaryWrapperFactory;
import org.libav.avutil.IDictionaryWrapper;
import org.libav.avutil.bridge.AVRational;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVChapter.
 * 
 * @author Ondrej Perutka
 */
public class ChapterWrapper implements IChapterWrapper {
    
    private static final AVUtilLibrary utilLib = LibraryManager.getInstance().getAVUtilLibrary();

    private AVChapter chapter;
    
    private Integer id;
    private Long start;
    private Long end;
    private IDictionaryWrapper metadata;

    public ChapterWrapper(Pointer<?> pointer) {
        this(new AVChapter(pointer));
    }
    
    public ChapterWrapper(AVChapter chapter) {
        this.chapter = chapter;
        
        id = null;
        start = null;
        end = null;
        metadata = null;
    }
    
    @Override
    public void clearWrapperCache() {
        id = null;
        start = null;
        end = null;
        metadata = null;
    }

    @Override
    public Pointer<?> getPointer() {
        return Pointer.pointerTo(chapter);
    }

    @Override
    public void free() {
        if (chapter == null)
            return;
        
        getMetadata().free();
        utilLib.av_free(getPointer());
        chapter = null;
    }
    
    @Override
    public int getId() {
        if (chapter == null)
            return 0;
        
        if (id == null)
            id = chapter.id();
        
        return id;
    }

    @Override
    public void setId(int id) {
        if (chapter == null)
            return;
        
        chapter.id(id);
        this.id = id;
    }

    @Override
    public long getStart() {
        if (chapter == null)
            return 0;
        
        if (start == null) {
            AVRational tb = chapter.time_base();
            start = chapter.start() * 1000 * tb.num() / tb.den();
        }
        
        return start;
    }

    @Override
    public void setStart(long start) {
        if (chapter == null)
            return;
        
        AVRational tb = chapter.time_base();
        chapter.start(start * tb.den() / (tb.num() * 1000));
        this.start = start;
    }

    @Override
    public long getEnd() {
        if (chapter == null)
            return 0;
        
        if (end == null) {
            AVRational tb = chapter.time_base();
            end = chapter.end() * 1000 * tb.num() / tb.den();
        }
        
        return end;
    }

    @Override
    public void setEnd(long end) {
        if (chapter == null)
            return;
        
        AVRational tb = chapter.time_base();
        chapter.end(end * tb.den() / (tb.num() * 1000));
        this.end = end;
    }

    @Override
    public IDictionaryWrapper getMetadata() {
        if (chapter == null)
            return null;
        
        if (metadata == null) {
            DictionaryWrapperFactory dwf = DictionaryWrapperFactory.getInstance();
            if (chapter.metadata() == null) {
                metadata = dwf.allocate();
                chapter.metadata(metadata.getPointer());
            } else
                metadata = dwf.wrap(chapter.metadata());
        }
        
        return metadata;
    }
    
    /**
     * Allocate a new chapter.
     * 
     * @return chapter wrapper
     */
    public static IChapterWrapper allocate() {
        Pointer<?> tmp = utilLib.av_malloc(BridJ.sizeOf(AVChapter.class));
        if (tmp == null)
            throw new OutOfMemoryError("unable to allocate chapter native structure");
        
        ChapterWrapper result = new ChapterWrapper(tmp);
        AVChapter c = result.chapter;
        c.time_base().num(1);
        c.time_base().den(1000);
        
        return result;
    }
    
}

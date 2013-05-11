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
package org.libav.avutil;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.avutil.bridge.AVDictionaryEntry;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 * Wrapper class for the AVDictionary.
 * 
 * @author Ondrej Perutka
 */
public class DictionaryWrapper implements IDictionaryWrapper {

    private static final Charset UTF8;
    private static final Pointer<Byte> emptyString;
    
    static {
        UTF8 = Charset.forName("UTF-8");
        emptyString = Pointer.pointerToString("", Pointer.StringType.C, UTF8).as(Byte.class);
    }
    
    private static final AVUtilLibrary lib = LibraryManager.getInstance().getAVUtilLibrary();
    
    private Pointer<Pointer<?>> dictionary;

    public DictionaryWrapper(Pointer<?> dictionary) {
        this.dictionary = Pointer.allocatePointer();
        this.dictionary.set(dictionary);
    }
    
    @Override
    public void clearWrapperCache() {
    }

    @Override
    public Pointer<?> getPointer() {
        return dictionary == null ? null : dictionary.get();
    }

    @Override
    public void rebind(Pointer<?> pointer) {
        dictionary.set(pointer);
    }
    
    @Override
    public void free() {
        if (dictionary == null)
            return;
        
        lib.av_dict_free(dictionary);
        dictionary = null;
    }
    
    @Override
    public void copyTo(IDictionaryWrapper dictionary, int flags) {
        if (this.dictionary == null || dictionary.getPointer() == null)
            return;
        
        Pointer<Pointer<?>> tmp = Pointer.allocatePointer();
        tmp.set(dictionary.getPointer());
        lib.av_dict_copy(tmp, this.dictionary.get(), flags);
    }
    
    @Override
    public String get(String key, int flags) {
        if (dictionary == null)
            return null;
        
        Pointer<Byte> tmp = Pointer.pointerToString(key, Pointer.StringType.C, UTF8).as(Byte.class);
        Pointer<?> pEntry = lib.av_dict_get(dictionary.get(), tmp, null, flags);
        if (pEntry == null)
            return null;
            
        AVDictionaryEntry entry = new AVDictionaryEntry(pEntry);
        return entry.value().getString(Pointer.StringType.C, UTF8);
    }

    @Override
    public void set(String key, String value, int flags) throws LibavException {
        if (dictionary == null)
            return;
        
        Pointer<Byte> pKey = Pointer.pointerToString(key, Pointer.StringType.C, UTF8).as(Byte.class);
        Pointer<Byte> pValue = Pointer.pointerToString(value, Pointer.StringType.C, UTF8).as(Byte.class);
        int res = lib.av_dict_set(dictionary, pKey, pValue, flags);
        if (res < 0)
            throw new LibavException(res);
    }

    @Override
    public Iterator<Pair> iterator() {
        return new DictionaryIterator();
    }
    
    @Override
    public List<Pair> toList() {
        List<Pair> result = new ArrayList<Pair>();
        
        Iterator<Pair> it = iterator();
        while (it.hasNext())
            result.add(it.next());
        
        return result;
    }
    
    /**
     * Allocate a new dictionary.
     * 
     * @return dictionary wrapper
     */
    public static IDictionaryWrapper allocate() {
        Pointer<Pointer<?>> pDict = Pointer.allocatePointer();
        pDict.set(null);
        
        Pointer<Byte> dummyKey = Pointer.pointerToString("key", Pointer.StringType.C, UTF8).as(Byte.class);
        lib.av_dict_set(pDict, dummyKey, dummyKey, 0);
        lib.av_dict_set(pDict, dummyKey, null, 0);
        
        return new DictionaryWrapper(pDict.get());
    }
    
    private class DictionaryIterator implements Iterator<Pair> {
        private Pointer<?> lastEntry;

        public DictionaryIterator() {
            lastEntry = null;
        }
        
        @Override
        public boolean hasNext() {
            return dictionary != null && lib.av_dict_get(dictionary.get(), emptyString, lastEntry, AVUtilLibrary.AV_DICT_IGNORE_SUFFIX) != null;
        }

        @Override
        public Pair next() {
            if (dictionary == null)
                return null;
            
            lastEntry = lib.av_dict_get(dictionary.get(), emptyString, lastEntry, AVUtilLibrary.AV_DICT_IGNORE_SUFFIX);
            if (lastEntry == null)
                return null;
            
            AVDictionaryEntry entry = new AVDictionaryEntry(lastEntry);
            String key = entry.key().getString(Pointer.StringType.C, UTF8);
            String value = entry.value().getString(Pointer.StringType.C, UTF8);
            
            return new Pair(key, value);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation not supported.");
        }
    }
    
}

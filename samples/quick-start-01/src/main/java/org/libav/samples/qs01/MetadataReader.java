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
package org.libav.samples.qs01;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.libav.DefaultMediaReader;
import org.libav.IMediaReader;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avformat.IChapterWrapper;
import org.libav.avformat.IFormatContextWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.avutil.IDictionaryWrapper;
import org.libav.avutil.bridge.AVMediaType;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.avutil.bridge.PixelFormat;

/**
 * This is the first quick start usage example. It shows how to open 
 * a single multimedia file/network stream and how to get some metadata.
 * 
 * @author Ondrej Perutka
 */
public class MetadataReader {
    
    public static void main(String[] args) {
        // First of all we need to ensure our Libav libraries will be properly
        // loaded. BridJ searches for them in standard system locations and
        // in a folder specified by the "org.libav.libpath" system property.
        // By default the value of this property is "libav". It means 
        // the libraries will be searched for in "libav" subdirectory of 
        // the application working directory. You can simply change this
        // behaviour by changing the system property but be sure you change
        // it before ANY access to jlibav.
        
        IMediaReader reader = null;
        
        try {
            // Let's pretend the first command line argument is either a file name
            // or a stream URL. We need to do some necessary checkings so we will
            // make a special method.
            String url = parseArgs(args);
        
            // Now we need to open the file/stream. We use a IMediaReader with its
            // default implementation.
            reader = new DefaultMediaReader(url);
            
            // There are several types of metadata. Global metadata common for
            // the whole file/stream, local metadata for any particular audio
            // or video stream within the multimedia file/network stream and 
            // metadata for chapters (if there are any).
            
            // Global metadata are held by a format context. It's Libv structure 
            // which stores all information about opened file/stream.
            IFormatContextWrapper formatContext = reader.getFormatContext();
            
            // To print the metadata we use a special method.
            System.out.println("Global metadata:");
            printMetadata("    ", formatContext.getMetadata());
            
            // Now we print chapters similarly.
            System.out.println("\nChapters:");
            printChapters("    ", formatContext.getChapters());
            
            // OK, we have global metadata and chapters. Now we print info
            // about each stream.
            System.out.println("\nStreams:");
            for (int i = 0; i < reader.getStreamCount(); i++)
                printStreamInfo("    ", reader.getStream(i));
        } catch (Exception ex) {
            Logger.getLogger(MetadataReader.class.getName()).log(Level.SEVERE, "something's wrong", ex);
        } finally {
            try {
                // We should always release kept resources if we don't need them.
                if (reader != null)
                    reader.close();
            } catch (Exception ex) {
                Logger.getLogger(MetadataReader.class.getName()).log(Level.SEVERE, "unable to release system resources", ex);
            }
        }
    }
    
    /**
     * Check command line arguments and return the first one.
     * 
     * @param args command line arguments
     * @return first argument
     * @throws Exception if there is not exactly one argument
     */
    private static String parseArgs(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("USAGE: java -jar quick-start-01.jar file-name");
        
        return args[0];
    }
    
    /**
     * Print all metadata in the given dictionary and put the given prefix in
     * front of each line.
     * 
     * @param prefix line prefix
     * @param dictionaryWrapper metadata dictionary
     */
    private static void printMetadata(String prefix, IDictionaryWrapper dictionaryWrapper) {
        for (IDictionaryWrapper.Pair pair : dictionaryWrapper)
            System.out.printf("%s%-15s %s\n", prefix, pair.getKey() + ":", pair.getValue());
    }
    
    /**
     * Print the given chapters and their metadata and put the given prefix in
     * front of each line.
     * 
     * @param prefix line prefix
     * @param chapters chapters
     */
    private static void printChapters(String prefix, IChapterWrapper[] chapters) {
        for (IChapterWrapper chapter : chapters) {
            System.out.printf("%schapter 0x%08x [%8d, %8d]:\n", prefix, chapter.getId(), chapter.getStart(), chapter.getEnd());
            printMetadata(prefix + "    ", chapter.getMetadata());
        }
    }
    
    /**
     * Print some info about the given stream and put the given prefix in
     * front of each line.
     * 
     * @param prefix line prefix
     * @param stream stream
     */
    private static void printStreamInfo(String prefix, IStreamWrapper stream) {
        System.out.printf("%sstream %d:\n", prefix, stream.getIndex());
        // We should also print some info about used codec.
        printCodecInfo(prefix + "    ", stream.getCodecContext());
        printMetadata(prefix + "    ", stream.getMetadata());
    }
    
    /**
     * Print some info about the given codec context and put the given prefix in
     * front of each line.
     * 
     * @param prefix line prefix
     * @param codecContext codec context
     */
    private static void printCodecInfo(String prefix, ICodecContextWrapper codecContext) {
        // Print codec info according to the codec type.
        switch (codecContext.getCodecType()) {
            case AVMediaType.AVMEDIA_TYPE_AUDIO:
                printAudioCodecInfo(prefix, codecContext);
                break;
            case AVMediaType.AVMEDIA_TYPE_VIDEO:
                printVideoCodecInfo(prefix, codecContext);
                break;
            case AVMediaType.AVMEDIA_TYPE_SUBTITLE:
                printSubtitleCodecInfo(prefix, codecContext);
                break;
            default:
                printMiscCodecInfo(prefix, codecContext);
                break;
        }
    }
    
    /**
     * Print some info about the given audio codec context.
     * 
     * @param prefix line prefix
     * @param codecContext codec context
     */
    private static void printAudioCodecInfo(String prefix, ICodecContextWrapper codecContext) {
        String codecName = codecContext.getCodecId().name();
        System.out.printf("%saudio codec %s:\n", prefix, codecName);
        System.out.printf("%s    sample rate:        %d\n", prefix, codecContext.getSampleRate());
        System.out.printf("%s    bit rate:           %d\n", prefix, codecContext.getBitRate());
        System.out.printf("%s    number of channels: %d\n", prefix, codecContext.getChannels());
        
        String sampleFormat = "unknown";
        switch (codecContext.getSampleFormat()) {
            case AVSampleFormat.AV_SAMPLE_FMT_U8:
            case AVSampleFormat.AV_SAMPLE_FMT_U8P: sampleFormat = "unsigned, 8 bps"; break;
            case AVSampleFormat.AV_SAMPLE_FMT_S16:
            case AVSampleFormat.AV_SAMPLE_FMT_S16P: sampleFormat = "signed, 16 bps"; break;
            case AVSampleFormat.AV_SAMPLE_FMT_S32:
            case AVSampleFormat.AV_SAMPLE_FMT_S32P: sampleFormat = "signed, 32 bps"; break;
            case AVSampleFormat.AV_SAMPLE_FMT_FLT:
            case AVSampleFormat.AV_SAMPLE_FMT_FLTP: sampleFormat = "floating-point, 32 bps"; break;
            case AVSampleFormat.AV_SAMPLE_FMT_DBL:
            case AVSampleFormat.AV_SAMPLE_FMT_DBLP: sampleFormat = "floating-point, 64 bps"; break;
            default: break;
        }
        
        System.out.printf("%s    sample format:      %s\n", prefix, sampleFormat);
    }
    
    /**
     * Print some info about the given video codec context.
     * 
     * @param prefix line prefix
     * @param codecContext codec context
     */
    private static void printVideoCodecInfo(String prefix, ICodecContextWrapper codecContext) {
        String codecName = codecContext.getCodecId().name();
        System.out.printf("%svideo codec %s:\n", prefix, codecName);
        System.out.printf("%s    frame width:        %d\n", prefix, codecContext.getWidth());
        System.out.printf("%s    frame height:       %d\n", prefix, codecContext.getHeight());
        System.out.printf("%s    bit rate:           %d\n", prefix, codecContext.getBitRate());
        
        String pixelFormat = "unknown";
        
        // This is kind of a hack to get a name of the pixel format. You don't
        // need to understand it, it's just for fun :-D ...
        Class<?> pixFmtClass = PixelFormat.class;
        Field[] pixFmtFields = pixFmtClass.getDeclaredFields();
        for (Field field : pixFmtFields) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;
            if (!field.getName().startsWith("PIX_FMT_"))
                continue;
            
            try {
                if (codecContext.getPixelFormat() != field.getInt(null))
                    continue;
            } catch (Exception ex) {
                continue;
            }
            
            pixelFormat = field.getName().substring(8);
            break;
        }
        
        System.out.printf("%s    pixel format:       %s\n", prefix, pixelFormat, codecContext.getPixelFormat());
    }
    
    /**
     * Print some info about the given subtitle codec context.
     * 
     * @param prefix line prefix
     * @param codecContext codec context
     */
    private static void printSubtitleCodecInfo(String prefix, ICodecContextWrapper codecContext) {
        String codecName = codecContext.getCodecId().name();
        System.out.printf("%ssubtitle codec %s\n", prefix, codecName);
    }
    
    /**
     * Print just it's a misc codec and try to get its name.
     * 
     * @param prefix line prefix
     * @param codecContext codec context
     */
    private static void printMiscCodecInfo(String prefix, ICodecContextWrapper codecContext) {
        String codecName = codecContext.getCodecId().name();
        System.out.printf("%smiscellaneous codec %s\n", prefix, codecName);
    }
    
}

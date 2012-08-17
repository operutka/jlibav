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
package org.libav.samples;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.libav.*;
import org.libav.avcodec.CodecWrapperFactory;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.video.FrameScaler;

/**
 * Transcoding sample.
 * 
 * @author Ondrej Perutka
 */
public class TranscodeSample {
    
    public static void main(String[] args) {
        String srcUrl = "/media/D/foo.avi"; // some source multimedia file/stream
        String dstUrl = "/media/D/bar.mkv"; // destination file name
        int videoCodecId = CodecWrapperFactory.CODEC_ID_MPEG4; // output video codec
        int audioCodecId = CodecWrapperFactory.CODEC_ID_MP2; // output audio codec
        
        IMediaDecoder md = null;
        IMediaEncoder me = null;
        IMediaReader mr;
        IMediaWriter mw;
        FrameScaler scaler = null;
        
        try {
            md = new DefaultMediaDecoder(srcUrl); // open input file/stream
            me = new DefaultMediaEncoder(dstUrl, null); // open output file
            mr = md.getMediaReader();
            mw = me.getMediaWriter();
            
            IDecoder dec;
            IEncoder enc;
            ICodecContextWrapper cc1, cc2;
            int si;
            
            // init video transcoding of the first video stream if there is at
            // least one video stream
            if (mr.getVideoStreamCount() > 0) {
                md.setVideoStreamDecodingEnabled(0, true);
                dec = md.getVideoStreamDecoder(0);
                cc1 = dec.getCodecContext();
                si = mw.addVideoStream(videoCodecId, cc1.getWidth(), cc1.getHeight());
                enc = me.getVideoStreamEncoder(si);
                cc2 = enc.getCodecContext();
                cc2.setPixelFormat(cc1.getPixelFormat());
                scaler = new FrameScaler(cc1.getWidth(), cc1.getHeight(), cc1.getPixelFormat(), cc2.getWidth(), cc2.getHeight(), cc2.getPixelFormat());
                scaler.addFrameConsumer(enc);
                dec.addFrameConsumer(scaler);
            }
            
            // init audio transcoding of the first audio stream if there is at
            // least one audio stream
            if (mr.getAudioStreamCount() > 0) {
                md.setAudioStreamDecodingEnabled(0, true);
                dec = md.getAudioStreamDecoder(0);
                cc1 = dec.getCodecContext();
                si = mw.addAudioStream(audioCodecId, cc1.getSampleRate(), cc1.getSampleFormat(), cc1.getChannels());
                dec.addFrameConsumer(me.getAudioStreamEncoder(si));
            }
            
            mw.writeHeader(); // write file header
            boolean hasNext = true;
            int count = 10000;
            while (hasNext && count-- > 0) {
                try {
                    hasNext = mr.readNextPacket();
                } catch (LibavException ex) {
                    Logger.getLogger(TranscodeSample.class.getName()).log(Level.WARNING, "wrong packet", ex);
                }
            }
            md.flush();
            me.flush();
            mw.writeTrailer(); // write file trailer
        } catch (Exception ex) {
            Logger.getLogger(TranscodeSample.class.getName()).log(Level.SEVERE, "oooops", ex);
        } finally {
            try {
                if (md != null)
                    md.close();
                if (me != null)
                    me.close();
                if (scaler != null)
                    scaler.dispose();
            } catch (Exception ex) {
                Logger.getLogger(TranscodeSample.class.getName()).log(Level.SEVERE, "cannot close that", ex);
            }
        }
    }
    
}

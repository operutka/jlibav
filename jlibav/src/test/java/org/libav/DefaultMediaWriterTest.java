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

import java.io.File;
import java.util.UUID;
import org.junit.Test;
import org.libav.avcodec.CodecID;
import org.libav.avcodec.FrameWrapperFactory;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avcodec.IFrameWrapper;
import org.libav.avutil.PixelFormat;
import org.libav.avutil.SampleFormat;
import org.libav.avutil.bridge.AVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 *
 * @author Ondrej Perutka
 */
public class DefaultMediaWriterTest {
    
    private static final AVUtilLibrary lib = LibraryManager.getInstance().getAVUtilLibrary();
    
    @Test
    public void testFileIO() throws Exception {
        System.out.println("testing media writer file IO...");
        File tmpFile = File.createTempFile(UUID.randomUUID().toString(), ".mp4");
        
        DefaultMediaEncoder me = new DefaultMediaEncoder(tmpFile.getAbsolutePath(), null);
        IMediaWriter mw = me.getMediaWriter();
        if (!mw.getInterleave())
            mw.setInterleave(true);
        
        int vsIndex = mw.addVideoStream(CodecID.MPEG4, 320, 240);
        int asIndex = mw.addAudioStream(CodecID.MP2, 48000, SampleFormat.S16, 2);
        IEncoder ve = me.getVideoStreamEncoder(vsIndex);
        IEncoder ae = me.getAudioStreamEncoder(asIndex);
        
        ICodecContextWrapper cc = ve.getCodecContext();
        cc.setPixelFormat(PixelFormat.YUV420P);
        IFrameWrapper picture = FrameWrapperFactory.getInstance().allocPicture(cc.getPixelFormat(), cc.getWidth(), cc.getHeight());
        IFrameWrapper af = FrameWrapperFactory.getInstance().allocFrame();
        picture.setPts(0);
        af.getData().set(0, lib.av_malloc(10000).as(Byte.class));
        af.getLineSize().set(0, 7680);
        
        mw.writeHeader();
        for (int i = 0; i < 125; i++) {
            ve.processFrame(null, picture);
            ae.processFrame(null, af);
            picture.setPts(picture.getPts() + 40);
        }
        me.flush();
        mw.writeTrailer();
        me.close();
        
        picture.free();
        lib.av_free(af.getData().get(0));
        af.free();
    }
    
    @Test
    public void testNetworkStreamIO() throws Exception {
        System.out.println("testing media writer network stream IO...");
        
        int port = 5000 + (int)(Math.random() * 60000);
        DefaultMediaEncoder me = new DefaultMediaEncoder("rtp://localhost:" + port, "rtp");
        IMediaWriter mw = me.getMediaWriter();
        if (mw.getInterleave())
            mw.setInterleave(false);
        
        int vsIndex = mw.addVideoStream(CodecID.MPEG4, 320, 240);
        IEncoder ve = me.getVideoStreamEncoder(vsIndex);
        
        ICodecContextWrapper cc = ve.getCodecContext();
        cc.setPixelFormat(PixelFormat.YUV420P);
        IFrameWrapper picture = FrameWrapperFactory.getInstance().allocPicture(cc.getPixelFormat(), cc.getWidth(), cc.getHeight());
        picture.setPts(0);
        
        mw.writeHeader();
        for (int i = 0; i < 125; i++) {
            ve.processFrame(null, picture);
            picture.setPts(picture.getPts() + 40);
        }
        me.flush();
        mw.writeTrailer();
        me.close();
    }
    
}

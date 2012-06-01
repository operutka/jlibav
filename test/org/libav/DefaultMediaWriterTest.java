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
import org.libav.avcodec.CodecWrapperFactory;
import org.libav.avcodec.FrameWrapperFactory;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avcodec.IFrameWrapper;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.avutil.bridge.IAVUtilLibrary;
import org.libav.bridge.LibraryManager;

/**
 *
 * @author Ondrej Perutka
 */
public class DefaultMediaWriterTest {
    
    private static final IAVUtilLibrary lib = LibraryManager.getInstance().getAVUtilLibraryWrapper().getLibrary();
    
    @Test
    public void testFileIO() throws Exception {
        System.out.println("testing media writer file IO...");
        File tmpFile = File.createTempFile(UUID.randomUUID().toString(), ".avi");
        
        DefaultMediaEncoder me = new DefaultMediaEncoder(tmpFile.getAbsolutePath(), "avi");
        IMediaWriter mw = me.getMediaWriter();
        if (!mw.getInterleave())
            mw.setInterleave(true);
        
        int vsIndex = mw.addVideoStream(CodecWrapperFactory.CODEC_ID_MPEG4, 320, 240);
        int asIndex = mw.addAudioStream(CodecWrapperFactory.CODEC_ID_MP2, 48000, AVSampleFormat.AV_SAMPLE_FMT_S16, 2);
        IEncoder ve = me.getVideoStreamEncoder(vsIndex);
        IEncoder ae = me.getAudioStreamEncoder(asIndex);
        
        ICodecContextWrapper cc = ve.getCodecContext();
        IFrameWrapper picture = FrameWrapperFactory.getInstance().allocPicture(cc.getPixelFormat(), cc.getWidth(), cc.getHeight());
        IFrameWrapper af = FrameWrapperFactory.getInstance().allocFrame();
        af.setData(0, lib.av_malloc(10000));
        af.setLineSize(0, 7680);
        
        mw.writeHeader();
        for (int i = 0; i < 125; i++) {
            ve.processFrame(null, picture);
            ae.processFrame(null, af);
        }
        me.flush();
        mw.writeTrailer();
        me.close();
        
        picture.free();
        lib.av_free(af.getData()[0]);
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
        
        int vsIndex = mw.addVideoStream(CodecWrapperFactory.CODEC_ID_MPEG4, 320, 240);
        IEncoder ve = me.getVideoStreamEncoder(vsIndex);
        
        ICodecContextWrapper cc = ve.getCodecContext();
        IFrameWrapper picture = FrameWrapperFactory.getInstance().allocPicture(cc.getPixelFormat(), cc.getWidth(), cc.getHeight());
        
        mw.writeHeader();
        for (int i = 0; i < 125; i++)
            ve.processFrame(null, picture);
        me.flush();
        mw.writeTrailer();
        me.close();
    }
    
}

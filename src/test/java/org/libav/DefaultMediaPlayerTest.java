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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.junit.Test;
import org.libav.avcodec.IFrameWrapper;
import org.libav.data.IFrameConsumer;

/**
 *
 * @author Ondrej Perutka
 */
public class DefaultMediaPlayerTest {
    
    @Test
    public void test() throws Exception {
        System.out.println("testing media player...");
        File tmp = DefaultMediaPlayerTest.makeTempFromResource(getClass().getResourceAsStream("/org/libav/resources/test.avi"), "avi");
        
        DefaultMediaPlayer mp = new DefaultMediaPlayer(tmp.getAbsolutePath());
        if (mp.getMediaReader().getVideoStreamCount() > 0) {
            mp.setVideoStreamDecodingEnabled(0, true);
            mp.getVideoStreamDecoder(0).addFrameConsumer(new DummyFrameConsumer());
        }
        if (mp.getMediaReader().getAudioStreamCount() > 0) {
            mp.setAudioStreamDecodingEnabled(0, true);
            mp.getAudioStreamDecoder(0).addFrameConsumer(new DummyFrameConsumer());
        }
        
        mp.play();
        Thread.sleep(500);
        mp.stop();
        mp.play();
        mp.join();
        
        IMediaReader mr = mp.getMediaReader();
        if (mr.isSeekable()) {
            System.out.println("media file is seekable, testing seek...");
            mr.seek(0);
            mp.play();
            Thread.sleep(500);
            mp.stop();
        }
        
        mp.close();
    }
    
    public static File makeTempFromResource(InputStream resource, String suffix) throws IOException {
        File tmp = File.createTempFile(UUID.randomUUID().toString(), "." + suffix);
        FileOutputStream fos = null;
        byte[] buffer = new byte[4096];
        int len;
        
        try {
            fos = new FileOutputStream(tmp);
            while ((len = resource.read(buffer)) != -1)
                fos.write(buffer, 0, len);
            fos.flush();
        } finally {
            if (fos != null)
                fos.close();
        }
        
        return tmp;
    }
    
    private static class DummyFrameConsumer implements IFrameConsumer {
        @Override
        public void processFrame(Object producer, IFrameWrapper frame) {
            // System.out.println("processing frame, pts = " + frame.getPts());
        }
    }
    
}

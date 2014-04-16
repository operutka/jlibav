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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.libav.audio.AudioFrameDecoder;
import org.libav.avcodec.IFrameWrapper;
import org.libav.avformat.IFormatContextWrapper;
import org.libav.avformat.IInputFormatWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.data.IPacketConsumer;
import org.libav.video.VideoFrameDecoder;

/**
 * Default implementation of the media player interface.
 * 
 * @author Ondrej Perutka
 */
public class DefaultMediaPlayer implements IMediaPlayer {

    private IMediaReader mr;
    private boolean liveStream;
    
    private IDecoder[] aDecoders;
    private IDecoder[] vDecoders;
    
    private ThreadedStreamPlayer[] streamPlayers;
    private Thread[] playerThreads;
    private long stopPosition;
    private long startTime;

    /**
     * Create a new media player and open the given media URL using the default
     * media decoder.
     * 
     * @param url a media URL
     * @throws LibavException if an error occurs while opening or player 
     * initialization
     */
    public DefaultMediaPlayer(String url) throws LibavException {
        this(url, isUrlLiveStream(url));
    }
    
    /**
     * Create a new media player, open the given media URL using the default
     * media decoder and set the live stream flag.
     * 
     * @param url a media URL
     * @param liveStream live stream flag; it changes time synchronization 
     * settings to satisfy live stream needs
     * @throws LibavException if an error occurs while opening or player 
     * initialization
     */
    public DefaultMediaPlayer(String url, boolean liveStream) throws LibavException {
        this(new DefaultMediaReader(url), liveStream);
    }
    
    /**
     * Create a new media player and open the given media URL using the default
     * media decoder and forcing the input format.
     * 
     * @param url a media URL
     * @param inputFormat input format short name
     * @throws LibavException if an error occurs while opening or player 
     * initialization
     */
    public DefaultMediaPlayer(String url, String inputFormat) throws LibavException {
        this(url, inputFormat, isUrlLiveStream(url));
    }
    
    /**
     * Create a new media player, open the given media URL using the default
     * media decoder and forcing the input format and set the live stream flag.
     * 
     * @param url a media URL
     * @param inputFormat input format short name
     * @param liveStream live stream flag; it changes time synchronization 
     * settings to satisfy live stream needs
     * @throws LibavException if an error occurs while opening or player 
     * initialization
     */
    public DefaultMediaPlayer(String url, String inputFormat, boolean liveStream) throws LibavException {
        this(new DefaultMediaReader(url, inputFormat), liveStream);
    }
    
    /**
     * Create a new media player and open the given media URL using the default
     * media decoder and forcing the input format.
     * 
     * @param url a media URL
     * @param inputFormat input format
     * @throws LibavException if an error occurs while opening or player 
     * initialization
     */
    public DefaultMediaPlayer(String url, IInputFormatWrapper inputFormat) throws LibavException {
        this(url, inputFormat, isUrlLiveStream(url));
    }
    
    /**
     * Create a new media player, open the given media URL using the default
     * media decoder and forcing the input format and set the live stream flag.
     * 
     * @param url a media URL
     * @param inputFormat input format
     * @param liveStream live stream flag; it changes time synchronization 
     * settings to satisfy live stream needs
     * @throws LibavException if an error occurs while opening or player 
     * initialization
     */
    public DefaultMediaPlayer(String url, IInputFormatWrapper inputFormat, boolean liveStream) throws LibavException {
        this(new DefaultMediaReader(url, inputFormat), liveStream);
    }
    
    protected DefaultMediaPlayer(IMediaReader mr, boolean liveStream) {
        this.mr = new MediaReaderAdapter(mr);
        this.liveStream = liveStream;
        
        aDecoders = new IDecoder[mr.getAudioStreamCount()];
        vDecoders = new IDecoder[mr.getVideoStreamCount()];
        
        playerThreads = null;
        streamPlayers = null;
        stopPosition = 0;
        startTime = 0;
    }
    
    @Override
    public IMediaReader getMediaReader() {
        return mr;
    }

    @Override
    public synchronized void setVideoStreamDecodingEnabled(int videoStreamIndex, boolean enabled) throws LibavException {
        if (enabled) {
            mr.addVideoPacketConsumer(videoStreamIndex, getVideoStreamDecoder(videoStreamIndex));
            mr.setVideoStreamBufferingEnabled(videoStreamIndex, true);
            startVideoStreamPlayback(videoStreamIndex);
        } else {
            mr.removeVideoPacketConsumer(videoStreamIndex, getVideoStreamDecoder(videoStreamIndex));
            mr.setVideoStreamBufferingEnabled(videoStreamIndex, false);
            stopStreamPlayback(mr.getVideoStream(videoStreamIndex).getIndex());
        }
    }

    @Override
    public boolean isVideoStreamDecodingEnabled(int videoStreamIndex) throws LibavException {
        return mr.containsVideoPacketConsumer(videoStreamIndex, getVideoStreamDecoder(videoStreamIndex));
    }

    @Override
    public IDecoder getVideoStreamDecoder(int videoStreamIndex) throws LibavException {
        if (vDecoders[videoStreamIndex] == null)
            vDecoders[videoStreamIndex] = new SynchronizedVideoFrameDecoder(mr.getVideoStream(videoStreamIndex));
        
        return vDecoders[videoStreamIndex];
    }
    
    @Override
    public synchronized void setAudioStreamDecodingEnabled(int audioStreamIndex, boolean enabled) throws LibavException {
        if (enabled) {
            mr.addAudioPacketConsumer(audioStreamIndex, getAudioStreamDecoder(audioStreamIndex));
            mr.setAudioStreamBufferingEnabled(audioStreamIndex, true);
            startAudioStreamPlayback(audioStreamIndex);
        } else {
            mr.removeAudioPacketConsumer(audioStreamIndex, getAudioStreamDecoder(audioStreamIndex));
            mr.setAudioStreamBufferingEnabled(audioStreamIndex, false);
            stopStreamPlayback(mr.getAudioStream(audioStreamIndex).getIndex());
        }
    }

    @Override
    public boolean isAudioStreamDecodingEnabled(int audioStreamIndex) throws LibavException {
        return mr.containsAudioPacketConsumer(audioStreamIndex, getAudioStreamDecoder(audioStreamIndex));
    }

    @Override
    public IDecoder getAudioStreamDecoder(int audioStreamIndex) throws LibavException {
        if (aDecoders[audioStreamIndex] == null)
            aDecoders[audioStreamIndex] = new SynchronizedAudioFrameDecoder(mr.getAudioStream(audioStreamIndex));
        
        return aDecoders[audioStreamIndex];
    }
    
    private synchronized void startVideoStreamPlayback(int videoStreamIndex) {
        IStreamWrapper sw = mr.getVideoStream(videoStreamIndex);
        int si = sw.getIndex();
        
        if (streamPlayers == null || streamPlayers[si] != null)
            return;
        
        if (liveStream)
            stopPosition = System.currentTimeMillis() - startTime - 500;
        
        streamPlayers[si] = new ThreadedVideoStreamPlayer(mr, videoStreamIndex, stopPosition);
        playerThreads[si] = new Thread(streamPlayers[si], "StreamPlayer");
        playerThreads[si].setDaemon(true);
        playerThreads[si].start();
    }
    
    private synchronized void startAudioStreamPlayback(int audioStreamIndex) {
        IStreamWrapper sw = mr.getAudioStream(audioStreamIndex);
        int si = sw.getIndex();
        
        if (streamPlayers == null || streamPlayers[si] != null)
            return;
        
        if (liveStream)
            stopPosition = System.currentTimeMillis() - startTime - 500;
        
        streamPlayers[si] = new ThreadedAudioStreamPlayer(mr, audioStreamIndex, stopPosition);
        playerThreads[si] = new Thread(streamPlayers[si], "StreamPlayer");
        playerThreads[si].setDaemon(true);
        playerThreads[si].start();
    }
    
    private synchronized void stopStreamPlayback(int streamIndex) {
        streamPlayers[streamIndex].stop();
        try {
            playerThreads[streamIndex].join();
        } catch (InterruptedException ex) {
            Logger.getLogger(DefaultMediaPlayer.class.getName()).log(Level.WARNING, "interrupted while waiting for stream player to stop", ex);
        }
        streamPlayers[streamIndex] = null;
        playerThreads[streamIndex] = null;
    }

    @Override
    public synchronized void play() throws LibavException {
        if (streamPlayers != null)
            return;
        
        streamPlayers = new ThreadedStreamPlayer[mr.getStreamCount()];
        playerThreads = new Thread[streamPlayers.length];
        
        if (startTime == 0 || !liveStream)
            startTime = System.currentTimeMillis();
        if (liveStream) {
            stopPosition = System.currentTimeMillis() - startTime - 500; // give it a time to work with network delay
            mr.dropAllBuffers();
        }
        
        IStreamWrapper sw;
        for (int i = 0; i < mr.getVideoStreamCount(); i++) {
            if (!isVideoStreamDecodingEnabled(i))
                continue;
            mr.setVideoStreamBufferingEnabled(i, true);
            sw = mr.getVideoStream(i);
            streamPlayers[sw.getIndex()] = new ThreadedVideoStreamPlayer(mr, i, stopPosition);
            playerThreads[sw.getIndex()] = new Thread(streamPlayers[sw.getIndex()], "StreamPlayer");
            playerThreads[sw.getIndex()].setDaemon(true);
        }
        
        for (int i = 0; i < mr.getAudioStreamCount(); i++) {
            if (!isAudioStreamDecodingEnabled(i))
                continue;
            mr.setAudioStreamBufferingEnabled(i, true);
            sw = mr.getAudioStream(i);
            streamPlayers[sw.getIndex()] = new ThreadedAudioStreamPlayer(mr, i, stopPosition);
            playerThreads[sw.getIndex()] = new Thread(streamPlayers[sw.getIndex()], "StreamPlayer");
            playerThreads[sw.getIndex()].setDaemon(true);
        }
        
        for (Thread playerThread : playerThreads) {
            if (playerThread != null)
                playerThread.start();
        }
    }

    @Override
    public synchronized void stop() {
        if (streamPlayers == null)
            return;
        
        for (ThreadedStreamPlayer sp : streamPlayers) {
            if (sp != null)
                sp.stop();
        }
        
        try {
            join();
        } catch (InterruptedException ex) {
            Logger.getLogger(DefaultMediaPlayer.class.getName()).log(Level.WARNING, "interrupted while waiting for playback to stop", ex);
        }
        
        stopPosition += System.currentTimeMillis() - startTime;
        
        streamPlayers = null;
    }

    @Override
    public void join() throws InterruptedException {
        Thread[] pts = playerThreads;
        if (pts == null)
            return;
        
        for (Thread pt : pts) {
            if (pt != null)
                pt.join();
        }
    }

    @Override
    public synchronized void close() throws LibavException {
        stop();
        
        for (IDecoder afd : aDecoders) {
            if (afd != null)
                afd.close();
        }
        
        for (IDecoder vfd : vDecoders) {
            if (vfd != null)
                vfd.close();
        }
        
        mr.close();
    }
    
    private static boolean isUrlLiveStream(String url) {
        url = url.toLowerCase();
        if (url.startsWith("rtp:"))
            return true;
        if (url.startsWith("rtsp:"))
            return true;
        
        return url.endsWith(".sdp");
    }
    
    private class ThreadedVideoStreamPlayer extends ThreadedStreamPlayer {
        private final long startPoint;
        private final int videoStreamIndex;
        
        public ThreadedVideoStreamPlayer(IMediaReader mr, int videoStreamIndex, long startPoint) {
            super(mr, mr.getVideoStream(videoStreamIndex).getIndex());
            
            this.videoStreamIndex = videoStreamIndex;
            this.startPoint = startPoint;
        }

        @Override
        public void run() {
            IDecoder decoder = null;
            
            try {
                decoder = getVideoStreamDecoder(videoStreamIndex);
                if (decoder instanceof SynchronizedVideoFrameDecoder)
                    ((SynchronizedVideoFrameDecoder)decoder).setStartPoint(System.currentTimeMillis() - startPoint);
            } catch (LibavException ex) {
                Logger.getLogger(DefaultMediaPlayer.class.getName()).log(Level.SEVERE, "unable to get video frame decoder", ex);
            }
            
            super.run();
            
            try {
                if (decoder != null)
                    decoder.flush();
            } catch (LibavException ex) {
                Logger.getLogger(DefaultMediaPlayer.class.getName()).log(Level.SEVERE, "unable to flush video frame decoder", ex);
            }
        }
    }
    
    private class ThreadedAudioStreamPlayer extends ThreadedStreamPlayer {
        private final long startPoint;
        private final int audioStreamIndex;
        
        public ThreadedAudioStreamPlayer(IMediaReader mr, int audioStreamIndex, long startPoint) {
            super(mr, mr.getAudioStream(audioStreamIndex).getIndex());
            
            this.audioStreamIndex = audioStreamIndex;
            this.startPoint = startPoint;
        }

        @Override
        public void run() {
            IDecoder decoder = null;
            
            try {
                decoder = getAudioStreamDecoder(audioStreamIndex);
                if (decoder instanceof SynchronizedAudioFrameDecoder)
                    ((SynchronizedAudioFrameDecoder)decoder).setStartPoint(System.currentTimeMillis() - startPoint);
            } catch (LibavException ex) {
                Logger.getLogger(DefaultMediaPlayer.class.getName()).log(Level.SEVERE, "unable to get audio frame decoder", ex);
            }
            
            super.run();
            
            try {
                if (decoder != null)
                    decoder.flush();
            } catch (LibavException ex) {
                Logger.getLogger(DefaultMediaPlayer.class.getName()).log(Level.SEVERE, "unable to flush audio frame decoder", ex);
            }
        }
    }
    
    private static class ThreadedStreamPlayer implements Runnable {
        protected IMediaReader mr;
        private final int streamIndex;
        private boolean stop;

        public ThreadedStreamPlayer(IMediaReader mr, int streamIndex) {
            this.mr = mr;
            this.streamIndex = streamIndex;
            
            stop = false;
        }
        
        @Override
        public void run() {
            while (!stop) {
                try {
                    if (!mr.readNextPacket(streamIndex))
                        stop = true;
                } catch (LibavException ex) {
                    Logger.getLogger(DefaultMediaPlayer.class.getName()).log(Level.SEVERE, "error while playing media", ex);
                }
            }
        }

        public void stop() {
            stop = true;
        }
    }
    
    private static class SynchronizedVideoFrameDecoder extends VideoFrameDecoder {
        private long startPoint;
        
        public SynchronizedVideoFrameDecoder(IStreamWrapper stream) throws LibavException {
            super(stream);
            
            startPoint = 0;
        }
        
        public void setStartPoint(long startPoint) {
            this.startPoint = startPoint;
        }

        @Override
        protected void sendFrame(IFrameWrapper frame) throws LibavException {
            long tmp = frame.getPts() + startPoint - System.currentTimeMillis();
            //System.out.printf("VF: pts = %d, tmp = %d (expected_pts = %d, start_point = %d)\n", frame.getPts(), tmp, System.currentTimeMillis() - startPoint, startPoint);
            if (tmp > 0) {
                try {
                    Thread.sleep(tmp);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DefaultMediaPlayer.class.getName()).log(Level.WARNING, "interrupted while waiting for a sync point");
                }
            } else if (tmp < -10) // drop frame if it is too late
                return;
            
            super.sendFrame(frame);
        }
    }
    
    private static class SynchronizedAudioFrameDecoder extends AudioFrameDecoder {
        private long startPoint;
        
        public SynchronizedAudioFrameDecoder(IStreamWrapper stream) throws LibavException {
            super(stream);
            
            startPoint = 0;
        }
        
        public void setStartPoint(long startPoint) {
            this.startPoint = startPoint;
        }

        @Override
        protected void sendFrame(IFrameWrapper frame) throws LibavException {
            long tmp = frame.getPts() + startPoint - System.currentTimeMillis() - 500;
            //System.out.printf("AF: pts = %d, tmp = %d (expected_pts = %d, start_point = %d)\n", frame.getPts(), tmp, System.currentTimeMillis() - startPoint, startPoint);
            if (tmp > 0) {
                try {
                    Thread.sleep(tmp);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DefaultMediaPlayer.class.getName()).log(Level.WARNING, "interrupted while waiting for a sync point");
                }
            } else if (tmp < -100) // drop frame if it is too late
                return;
            
            super.sendFrame(frame);
        }
    }
    
    private class MediaReaderAdapter implements IMediaReader {
        private final IMediaReader mr;

        public MediaReaderAdapter(IMediaReader mr) {
            this.mr = mr;
        }
        
        @Override
        public IFormatContextWrapper getFormatContext() {
            return mr.getFormatContext();
        }

        @Override
        public int getStreamCount() {
            return mr.getStreamCount();
        }

        @Override
        public void addPacketConsumer(int streamIndex, IPacketConsumer consumer) {
            mr.addPacketConsumer(streamIndex, consumer);
        }

        @Override
        public void removePacketConsumer(int streamIndex, IPacketConsumer consumer) {
            mr.removePacketConsumer(streamIndex, consumer);
        }

        @Override
        public boolean containsPacketConsumer(int streamIndex, IPacketConsumer consumer) {
            return mr.containsPacketConsumer(streamIndex, consumer);
        }

        @Override
        public IStreamWrapper getStream(int streamIndex) {
            return mr.getStream(streamIndex);
        }

        @Override
        public int getVideoStreamCount() {
            return mr.getVideoStreamCount();
        }

        @Override
        public IStreamWrapper getVideoStream(int videoStreamIndex) {
            return mr.getVideoStream(videoStreamIndex);
        }

        @Override
        public void addVideoPacketConsumer(int videoStreamIndex, IPacketConsumer consumer) {
            mr.addVideoPacketConsumer(videoStreamIndex, consumer);
        }

        @Override
        public void removeVideoPacketConsumer(int videoStreamIndex, IPacketConsumer consumer) {
            mr.removeVideoPacketConsumer(videoStreamIndex, consumer);
        }

        @Override
        public boolean containsVideoPacketConsumer(int videoStreamIndex, IPacketConsumer consumer) {
            return mr.containsVideoPacketConsumer(videoStreamIndex, consumer);
        }

        @Override
        public int getAudioStreamCount() {
            return mr.getAudioStreamCount();
        }

        @Override
        public IStreamWrapper getAudioStream(int audioStremIndex) {
            return mr.getAudioStream(audioStremIndex);
        }

        @Override
        public void addAudioPacketConsumer(int audioStreamIndex, IPacketConsumer consumer) {
            mr.addAudioPacketConsumer(audioStreamIndex, consumer);
        }

        @Override
        public void removeAudioPacketConsumer(int audioStreamIndex, IPacketConsumer consumer) {
            mr.removeAudioPacketConsumer(audioStreamIndex, consumer);
        }

        @Override
        public boolean containsAudioPacketConsumer(int audioStreamIndex, IPacketConsumer consumer) {
            return mr.containsAudioPacketConsumer(audioStreamIndex, consumer);
        }

        @Override
        public long getDuration() {
            return mr.getDuration();
        }

        @Override
        public long getStreamDuration(int streamIndex) {
            return mr.getStreamDuration(streamIndex);
        }

        @Override
        public long getVideoStreamDuration(int videoStreamIndex) {
            return mr.getVideoStreamDuration(videoStreamIndex);
        }

        @Override
        public long getAudioStreamDuration(int audioStreamIndex) {
            return mr.getAudioStreamDuration(audioStreamIndex);
        }

        @Override
        public long getPosition() {
            return mr.getPosition();
        }

        @Override
        public boolean isSeekable() {
            return mr.isSeekable();
        }

        @Override
        public void seek(long time) throws LibavException {
            synchronized (DefaultMediaPlayer.this) {
                if (!isSeekable())
                    return;

                boolean replay = streamPlayers != null;
                stop();
                mr.seek(time);
                stopPosition = time;
                if (replay)
                    play();
            }
        }

        @Override
        public void dropAllBuffers() {
            mr.dropAllBuffers();
        }

        @Override
        public boolean readNextPacket() throws LibavException {
            return mr.readNextPacket();
        }

        @Override
        public boolean readNextPacket(int streamIndex) throws LibavException {
            return mr.readNextPacket(streamIndex);
        }

        @Override
        public boolean readNextAudioPacket(int audioStreamIndex) throws LibavException {
            return mr.readNextAudioPacket(audioStreamIndex);
        }

        @Override
        public boolean readNextVideoPacket(int videoStreamIndex) throws LibavException {
            return mr.readNextVideoPacket(videoStreamIndex);
        }

        @Override
        public void setStreamBufferingEnabled(int streamIndex, boolean enabled) {
            mr.setStreamBufferingEnabled(streamIndex, enabled);
        }

        @Override
        public boolean isStreamBufferingEnabled(int streamIndex) {
            return mr.isStreamBufferingEnabled(streamIndex);
        }

        @Override
        public void setVideoStreamBufferingEnabled(int videoStreamIndex, boolean enabled) {
            mr.setVideoStreamBufferingEnabled(videoStreamIndex, enabled);
        }

        @Override
        public boolean isVideoStreamBufferingEnabled(int videoStreamIndex) {
            return mr.isVideoStreamBufferingEnabled(videoStreamIndex);
        }

        @Override
        public void setAudioStreamBufferingEnabled(int audioStreamIndex, boolean enabled) {
            mr.setAudioStreamBufferingEnabled(audioStreamIndex, enabled);
        }

        @Override
        public boolean isAudioStreamBufferingEnabled(int audioStreamIndex) {
            return mr.isAudioStreamBufferingEnabled(audioStreamIndex);
        }

        @Override
        public void close() throws LibavException {
            mr.close();
        }

        @Override
        public boolean isClosed() {
            return mr.isClosed();
        }
    }
    
}

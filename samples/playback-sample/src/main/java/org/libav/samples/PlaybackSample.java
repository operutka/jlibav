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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.nio.ByteOrder;
import java.text.DateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.libav.DefaultMediaPlayer;
import org.libav.IMediaPlayer;
import org.libav.IMediaReader;
import org.libav.LibavException;
import org.libav.audio.Frame2AudioFrameAdapter;
import org.libav.audio.PlaybackMixer;
import org.libav.audio.SampleInputStream;
import org.libav.avcodec.ICodecContextWrapper;
import org.libav.avformat.IChapterWrapper;
import org.libav.avformat.IFormatContextWrapper;
import org.libav.avformat.IStreamWrapper;
import org.libav.avutil.IDictionaryWrapper;
import org.libav.avutil.bridge.AVSampleFormat;
import org.libav.data.IFrameConsumer;
import org.libav.util.swing.VideoPane;

/**
 * Sample multimedia player.
 * 
 * @author Ondrej Perutka
 */
public class PlaybackSample extends javax.swing.JFrame {

    private DefaultMediaPlayer player;
    private VideoPane videoPane;
    private SampleInputStream sis;
    private AudioInputStream audioStream;
    private PlaybackMixer audioMixer;
    private Frame2AudioFrameAdapter resampler;
    
    private boolean liveStream;
    
    private SeekListener seekListener;
    private Timer timer;
    
    /**
     * Create a new player.
     */
    public PlaybackSample() {
        initComponents();
        
        videoPane = new VideoPane();
        add(videoPane, BorderLayout.CENTER);
        
        player = null;
        audioMixer = null;
        sis = null;
        audioStream = null;
        resampler = null;
        
        liveStream = false;
        
        timer = new Timer(true);
        
        buttonPlay.setEnabled(false);
        buttonStop.setEnabled(false);
        
        buttonOpen.addActionListener(new OpenListener());
        buttonOpenUrl.addActionListener(new OpenUrlListener());
        buttonPlay.addActionListener(new PlayListener());
        sliderVolume.addChangeListener(new VolumeChangeListener());
        sliderSeek.addChangeListener(seekListener = new SeekListener());
        videoPane.addComponentListener(new VideoPaneResizeListener());
        
        // hide unused components
        buttonStop.setVisible(false);
        
        setPreferredSize(new Dimension(800, 600));
        pack();
    }
    
    private float getVolume() {
        return sliderVolume.getValue() / 100.0f;
    }
    
    /**
     * Open the given URL and start playback. It will close current multimedia
     * file/stream if the null is given.
     * 
     * @param url a URL
     */
    private void open(String url) {
        try {
            if (player != null) // close the player
                player.close();
            if (audioMixer != null) { // remove audio stream from the mixer
                audioMixer.removeInputStream(audioStream);
                PlaybackMixer.closeMixer(audioStream.getFormat());
                audioMixer = null;
                audioStream = null;
            }
            sliderSeek.setValue(0);
            if (url == null)
                return;
            player = new DefaultMediaPlayer(url); // create a new media player
            IMediaReader mr = player.getMediaReader();
            dumpMedia(mr);
            
            // set the video pane as a video frame consumer for the first video
            // stream if there is at least one video stream
            if (mr.getVideoStreamCount() > 0) {
                videoPane.setVisible(true);
                videoPane.clear();
                player.setVideoStreamDecodingEnabled(0, true);
                player.getVideoStreamDecoder(0).addFrameConsumer(scale());
            } else // hide the video pane if there is no video stream
                videoPane.setVisible(false);
            
            // add the first audio stream to the mixer if there is at least one
            // audio stream
            if (mr.getAudioStreamCount() > 0) {
                ICodecContextWrapper cc = player.getAudioStreamDecoder(0).getCodecContext();
                sis = new SampleInputStream(cc.getSampleRate() * AVSampleFormat.getBytesPerSample(cc.getSampleFormat()) * cc.getChannels(), true);
                AudioFormat.Encoding senc = null;
                if (AVSampleFormat.isSigned(cc.getSampleFormat()))
                    senc = AudioFormat.Encoding.PCM_SIGNED;
                else if (AVSampleFormat.isUnsigned(cc.getSampleFormat()))
                    senc = AudioFormat.Encoding.PCM_UNSIGNED;
                audioStream = new AudioInputStream(sis, new AudioFormat(senc, 
                        cc.getSampleRate(), AVSampleFormat.getBitsPerSample(cc.getSampleFormat()), cc.getChannels(), 
                        AVSampleFormat.getBytesPerSample(cc.getSampleFormat()) * cc.getChannels(), cc.getSampleRate(), 
                        ByteOrder.BIG_ENDIAN.equals(ByteOrder.nativeOrder())), -1);
                if (resampler != null)
                    resampler.dispose();
                
                try {
                    resampler = new Frame2AudioFrameAdapter(cc.getChannels(), cc.getChannels(), cc.getSampleRate(), cc.getSampleRate(), cc.getSampleFormat(), cc.getSampleFormat());
                    player.getAudioStreamDecoder(0).addFrameConsumer(resampler);
                    player.setAudioStreamDecodingEnabled(0, true);
                    audioMixer = PlaybackMixer.getMixer(audioStream.getFormat());
                    resampler.addAudioFrameConsumer(sis);
                    audioMixer.addInputStream(audioStream);
                    audioMixer.setStreamVolume(audioStream, getVolume());
                    audioMixer.play();
                } catch (Exception ex) {
                    Logger.getLogger(PlaybackSample.class.getName()).log(Level.WARNING, "unable to play audio", ex);
                }
            }
            
            sliderSeek.setEnabled(mr.isSeekable());
            if (mr.isSeekable()) {
                sliderSeek.setMaximum((int)mr.getDuration());
                if (sliderSeek.getMaximum() <= 0) {
                    if (mr.getVideoStreamCount() > 0)
                        sliderSeek.setMaximum((int)mr.getVideoStreamDuration(0));
                    else if (mr.getAudioStreamCount() > 0)
                        sliderSeek.setMaximum((int)mr.getAudioStreamDuration(0));
                }
            }
            
            buttonPlay.setEnabled(true);
            buttonStop.setEnabled(true);
            buttonPlay.setText("Pause");
            player.play(); // start playback
            timer.schedule(new SeekUpdateEvent(), 500, 500);
        } catch (LibavException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "unable to open media", ex);
            buttonPlay.setEnabled(false);
            buttonStop.setEnabled(false);
        }
    }
    
    private void dumpMedia(IMediaReader mr) {
        IFormatContextWrapper fc = mr.getFormatContext();
        System.out.println("Media info:");
        System.out.printf("capture timestamp = %s\n", DateFormat.getDateTimeInstance().format(fc.getRealStartTime()));
        System.out.println("metadata:");
        List<IDictionaryWrapper.Pair> metadata = fc.getMetadata().toList();
        for (IDictionaryWrapper.Pair pair : metadata)
            System.out.printf("\t%-15s = \"%s\"\n", pair.getKey(), pair.getValue());
        IChapterWrapper[] chapters = fc.getChapters();
        System.out.println("chapters:");
        for (IChapterWrapper chapter : chapters) {
            System.out.printf("\tchapter[%d]:\n", chapter.getId());
            System.out.printf("\t\t.start = %d\n", chapter.getStart());
            System.out.printf("\t\t.end = %d\n", chapter.getEnd());
            System.out.println("\t\t.metadata:");
            metadata = chapter.getMetadata().toList();
            for (IDictionaryWrapper.Pair pair : metadata)
                System.out.printf("\t\t\t%-15s = \"%s\"\n", pair.getKey(), pair.getValue());
        }
        dumpStreams(mr);
    }
    
    private void dumpStreams(IMediaReader mr) {
        IStreamWrapper stream;
        ICodecContextWrapper cc;
        for (int i = 0; i < mr.getStreamCount(); i++) {
            stream = mr.getStream(i);
            cc = stream.getCodecContext();
            List<IDictionaryWrapper.Pair> metadata = stream.getMetadata().toList();
            System.out.printf("stream[%02d]:\n", i);
            System.out.printf("\t.codec.codec_type = 0x%08x\n", cc.getCodecType());
            System.out.printf("\t.disposition = 0x%08x\n", stream.getDisposition());
            System.out.println("\t.metadata:");
            for (IDictionaryWrapper.Pair pair : metadata)
                System.out.printf("\t\t%-15s = \"%s\"\n", pair.getKey(), pair.getValue());
        }
    }
    
    private IFrameConsumer scale() {
        if (player == null)
            return null;

        if (player.getMediaReader().getVideoStreamCount() == 0)
            return null;

        try {
            ICodecContextWrapper cc = player.getVideoStreamDecoder(0).getCodecContext();
            videoPane.setSourceImageFormat(cc.getWidth(), cc.getHeight(), cc.getPixelFormat());
        } catch (LibavException ex) { }
        
        return videoPane;
    }
    
    private class OpenUrlListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            String url = JOptionPane.showInputDialog(PlaybackSample.this, "URL:", "Open URL ...", JOptionPane.PLAIN_MESSAGE);
            liveStream = true;
            open(url);
        }
    }
    
    private class OpenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setMultiSelectionEnabled(false);
            if (fc.showOpenDialog(PlaybackSample.this) == JFileChooser.APPROVE_OPTION) {
                liveStream = false;
                open(fc.getSelectedFile().getAbsolutePath());
            }
        }
    }
    
    private class PlayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (player == null)
                return;
            
            try {
                if (buttonPlay.getText().equalsIgnoreCase("play")) { // play
                    if (audioMixer != null) {
                        if (liveStream) {
                            sis.flushBuffer();
                            audioMixer.flushDataLine();
                        }
                        audioMixer.addInputStream(audioStream);
                        audioMixer.setStreamVolume(audioStream, getVolume());
                    }
                    player.play();
                    buttonPlay.setText("Pause");
                } else { // pause
                    player.stop();
                    if (audioMixer != null)
                        audioMixer.removeInputStream(audioStream);
                    buttonPlay.setText("Play");
                }
            } catch (LibavException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "unable to play media", ex);
            }
        }
    }
    
    private class VolumeChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent ce) {
            if (audioMixer != null)
                audioMixer.setStreamVolume(audioStream, getVolume());
        }
    }
    
    private class VideoPaneResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            scale();
        }
    }
    
    private class SeekListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (sliderSeek.getValueIsAdjusting())
                return;
            
            if (player != null) {
                try {
                    player.getMediaReader().seek(sliderSeek.getValue());
                } catch (LibavException ex) {
                    Logger.getLogger(PlaybackSample.class.getName()).log(Level.WARNING, "unable to seek", ex);
                }
            }
        }
    }
    
    private class SeekUpdateEvent extends TimerTask {
        @Override
        public void run() {
            IMediaPlayer mp = player;
            if (mp == null)
                cancel();
            else {
                sliderSeek.removeChangeListener(seekListener);
                sliderSeek.setValue((int)mp.getMediaReader().getPosition());
                sliderSeek.addChangeListener(seekListener);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        buttonOpen = new javax.swing.JButton();
        buttonPlay = new javax.swing.JButton();
        buttonStop = new javax.swing.JButton();
        buttonOpenUrl = new javax.swing.JButton();
        sliderSeek = new javax.swing.JSlider();
        sliderVolume = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        buttonOpen.setText("Open file");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(buttonOpen, gridBagConstraints);

        buttonPlay.setText("Play");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel1.add(buttonPlay, gridBagConstraints);

        buttonStop.setText("Stop");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel1.add(buttonStop, gridBagConstraints);

        buttonOpenUrl.setText("Open URL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel1.add(buttonOpenUrl, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        sliderSeek.setMaximum(1000);
        sliderSeek.setValue(0);
        sliderSeek.setEnabled(false);
        getContentPane().add(sliderSeek, java.awt.BorderLayout.PAGE_END);

        sliderVolume.setMajorTickSpacing(20);
        sliderVolume.setMaximum(200);
        sliderVolume.setMinorTickSpacing(10);
        sliderVolume.setOrientation(javax.swing.JSlider.VERTICAL);
        sliderVolume.setPaintLabels(true);
        sliderVolume.setPaintTicks(true);
        sliderVolume.setValue(100);
        getContentPane().add(sliderVolume, java.awt.BorderLayout.LINE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlaybackSample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlaybackSample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlaybackSample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlaybackSample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        PlaybackSample vt = new PlaybackSample();
        vt.setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonOpen;
    private javax.swing.JButton buttonOpenUrl;
    private javax.swing.JButton buttonPlay;
    private javax.swing.JButton buttonStop;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider sliderSeek;
    private javax.swing.JSlider sliderVolume;
    // End of variables declaration//GEN-END:variables
}

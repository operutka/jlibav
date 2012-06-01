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
package org.libav.audio;

import org.libav.LibavException;

/**
 * Audio frame consumer interface. You may need to implement this interface
 * if you want to process audio.
 * 
 * @author Ondrej Perutka
 */
public interface IAudioFrameConsumer {
    
    /**
     * This method will be called by the audio frame decoder. It delivers
     * audio frames.
     * 
     * @param producer an audio frame decoder responsible for calling this
     * method and producing the audio frame
     * @param frame an audio frame
     * @throws LibavException you may want to throw this type of exception
     * if you call some Libav function inside this method
     */
    void processFrame(Object producer, AudioFrame frame) throws LibavException;
    
}

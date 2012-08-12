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
package org.libav.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Network stream reader.
 * 
 * @author Ondrej Perutka
 */
public class NetworkStreamReader implements Closeable {

    private InputStream in;
    private Charset charset;
    private byte[] line;    
    private boolean crFlag;

    /**
     * Creates a new NetworkStreamReader.
     * 
     * @param in an input network stream
     * @param maxLineLength a maximum length of the line (in bytes)
     * @param charset a charset which will be used to convert bytes to strings
     */
    public NetworkStreamReader(InputStream in, int maxLineLength, Charset charset) {
        this.in = in;
        this.charset = charset;
        this.line = new byte[maxLineLength];
        this.crFlag = false;
    }

    /**
     * Reads until the first occurrence of CR, LF or CRLF sequence. The line
     * terminator is not contained in returned string.
     * 
     * NOTE:
     * If the line is longer than the maximum line length, only first maximum
     * line length bytes are used.
     * 
     * @return a line from the input or null, if the end of stream has been
     * reached
     * @throws IOException an exception thrown by the underlaying stream
     */
    public String readLine() throws IOException {
        int tmp, i = 0;
        
        while ((tmp = in.read()) != -1) {
            if (tmp == '\r')
                break;
            if (tmp == '\n') {
                if (i != 0 || !crFlag)
                    break;
                else
                    continue;
            }
            
            if (i < line.length)
                line[i++] = (byte)tmp;
        }
        crFlag = tmp == '\r';
        
        if (i == 0)
            return null;
        
        return new String(line, 0, i, charset);
    }
    
    /**
     * Call read() on the underlaying stream.
     * 
     * @return a byte from the stream or -1 if the EOF has been reached
     * @throws IOException an exception thrown by the underlaying stream
     */
    public int read() throws IOException {
        return in.read();
    }
    
    /**
     * Call read(byte[]) on the underlaying stream.
     * 
     * @param bytes a buffer to be filled
     * @return a number of bytes read from the stream or -1 if the EOF has been 
     * reached
     * @throws IOException an exception thrown by the underlaying stream
     */
    public int read(byte[] bytes) throws IOException {
        return in.read(bytes);
    }
    
    /**
     * Call read(byte[], int, int) on the underlaying stream.
     * 
     * @param bytes a buffer to be filled
     * @param offset an offset from the begining in the given buffer
     * @param length a maximum number of bytes to be read
     * @return a number of bytes read from the stream or -1 if the EOF has been 
     * reached
     * @throws IOException an exception thrown by the underlaying stream
     */
    public int read(byte[] bytes, int offset, int length) throws IOException {
        return in.read(bytes, offset, length);
    }

    /**
     * Close the underlaying stream.
     * 
     * @throws IOException an exception thrown by the underlaying stream
     */
    @Override
    public void close() throws IOException {
        in.close();
    }
}

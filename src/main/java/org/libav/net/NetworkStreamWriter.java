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
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Network stream writer.
 * 
 * @author Ondrej Perutka
 */
public class NetworkStreamWriter implements Closeable, Flushable {

    private OutputStream os;
    private Charset cs;
    
    /**
     * Create a new NetworkStreamWriter.
     * 
     * @param out an output network stream
     * @param cs a charset which will be used to encode strings
     */
    public NetworkStreamWriter(OutputStream out, Charset cs) {
        this.os = out;
        this.cs = cs;
    }
    
    /**
     * Get charset used to encode strings.
     * 
     * @return charset used to encode strings
     */
    public Charset getCharset() {
        return cs;
    }
    
    /**
     * Write a single line into the underlaying stream.
     * 
     * NOTE:
     * There will be added CRLF characters after the given line.
     * 
     * @param line a line to be written
     * @throws IOException an exception thrown by the underlaying stream
     */
    public void writeLine(String line) throws IOException {
        ByteBuffer bb = cs.encode(line + "\r\n");
        os.write(bb.array());
    }
    
    /**
     * Call write(byte[]) on the underlaying stream.
     * 
     * @param data bytes to be written
     * @throws IOException an exception thrown by the underlaying stream
     */
    public void write(byte[] data) throws IOException {
        os.write(data);
    }
    
    /**
     * Call write(byte[], int, int) on the underlaying stream.
     * 
     * @param data bytes to be written
     * @param off an offset from the begining in the given byte array
     * @param len a number of bytes to be written
     * @throws IOException an exception thrown by the underlaying stream
     */
    public void write(byte[] data, int off, int len) throws IOException {
        os.write(data, off, len);
    }

    /**
     * Close the underlaying stream.
     * 
     * @throws IOException an exception thrown by the underlaying stream
     */
    @Override
    public void close() throws IOException {
        os.close();
    }

    /**
     * Flush the underlaying stream.
     * 
     * @throws IOException an exception thrown by the underlaying stream
     */
    @Override
    public void flush() throws IOException {
        os.flush();
    }
    
}

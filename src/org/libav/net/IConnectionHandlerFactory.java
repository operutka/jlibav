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

import java.net.Socket;

/**
 * Interface for connection handler factories. Connection handler is Runnable
 * object which serves client request using given socket.
 * 
 * @author Ondrej Perutka
 */
public interface IConnectionHandlerFactory {
    
    /**
     * Create a new connection handler.
     * 
     * @param socket a socket used to communicate with client
     * @return connection handler
     */
    Runnable createConnectionHandler(Socket socket);
    
}

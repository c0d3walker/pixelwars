package de.pixelwars.server.internal;

import java.io.InputStream;
import java.io.OutputStream;

public class Connection {

	private InputStream _in;
	private OutputStream _os;

	public Connection(OutputStream os, InputStream is) {
		_os = os;
		_in = is;
	}

	public InputStream getInputStream() {
		return _in;
	}
	
	public OutputStream getOutputStream() {
		return _os;
	}

}

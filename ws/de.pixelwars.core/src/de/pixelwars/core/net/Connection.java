package de.pixelwars.core.net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Connection {

	private ObjectInputStream _in;
	private ObjectOutputStream _os;

	public Connection(ObjectOutputStream os, ObjectInputStream is) {
		_os = os;
		_in = is;
	}

	public ObjectInputStream getInputStream() {
		return _in;
	}
	
	public ObjectOutputStream getOutputStream() {
		return _os;
	}

}

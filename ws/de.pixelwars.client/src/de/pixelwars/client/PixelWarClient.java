package de.pixelwars.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.net.Connection;

public class PixelWarClient implements Runnable {

	@Override
	public void run() {
		try {
			var connection = setupConnection("localhost", 8888);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Connection setupConnection(String host, int port) throws IOException, ClassNotFoundException {
		Socket sock = new Socket(host, port);
		var oos = new ObjectOutputStream(sock.getOutputStream());
		var ois = new ObjectInputStream(sock.getInputStream());
		var confirmation = (StringTransportObject) ois.readObject();
		if (!(confirmation.getActionType() == EActionType.CONNECTION_CONFIRMED)) {
			throw new IllegalStateException("The client is in a wrong state");
		}
		var sto = new StringTransportObject(EActionType.CONNECTION_CONFIRMED);
		oos.writeObject(sto);
		return new Connection(oos, ois);
	}

}

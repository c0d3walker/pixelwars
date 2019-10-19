package de.pixelwars.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.net.Connection;

public class PixelWarClient implements Runnable {

	private Connection _connection;

	@Override
	public void run() {
		try {
			_connection = setupConnection("localhost", 8888);
			sendTransportObject(new StringTransportObject(EActionType.CREATE_PLAYER, "Player"));
			Object o;
			while ((o = _connection.getInputStream().readObject()) != null) {
				System.out.println(o);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				_connection.getInputStream().close();
				_connection.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void sendTransportObject(Serializable object) throws IOException {
		_connection.getOutputStream().writeObject(object);
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

package de.pixelwars.server.internal;

import java.io.IOException;
import java.io.ObjectInputStream;

import de.pixelwars.core.actions.DataTransportObject;

public class ClientReceiver implements Runnable {

	private Connection _connection;

	public ClientReceiver(Connection connection) {
		_connection = connection;
	}

	@Override
	public void run() {
		var is = _connection.getInputStream();
		try (var ois = new ObjectInputStream(is)) {
			Object o = null;
			while ((o = ois.readObject()) != null) {
				var dto = (DataTransportObject) o;
				handleRequest(dto);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private void handleRequest(DataTransportObject dto) {
		System.out.println(dto.getActionType() + "" + dto.getObjectID());
	}

}

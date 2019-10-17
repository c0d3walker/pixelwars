package de.pixelwars.server.internal;

import java.io.IOException;
import java.io.ObjectInputStream;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.net.Connection;

public class ClientReceiver implements Runnable {

	private Connection _connection;
	private IGameEnvironment _environment;
	private ClientState _state;

	public ClientReceiver(Connection connection, IGameEnvironment environment) {
		_connection = connection;
		_environment = environment;
		setupClientStateManagement();
	}

	private void setupClientStateManagement() {
		var gameRunning=new ClientState(null,EActionType.CREATE_BY_IDS);
		var established = new ClientState(gameRunning, EActionType.CREATE_PLAYER);
		var initial = new ClientState(established, EActionType.CONNECTION_CONFIRMED);
		_state=initial;
	}

	@Override
	public void run() {
		var ois = _connection.getInputStream();
		try {
			Object o = null;
			while ((o = ois.readObject()) != null) {
				if (o instanceof StringTransportObject) {
					handleStringTransportObjectRequest((StringTransportObject) o);
				} else {
					System.err.println("Received an unknown dto");
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleStringTransportObjectRequest(StringTransportObject sto) {
		System.out.println(sto.getActionType() + "" + sto.getValues()[0]);
	}

}

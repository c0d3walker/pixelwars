package de.pixelwars.server.internal;

import java.io.IOException;
import java.io.ObjectInputStream;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.exchange.ClientState;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.net.Connection;
import de.pixelwars.server.actions.CreatePlayerAction;

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
		var gameRunning = new ClientState();
		var established = new ClientState();
		var initial = new ClientState();
		initial.addTransmision(EActionType.CONNECTION_CONFIRMED, established);
		established.addTransmision(EActionType.CREATE_PLAYER, gameRunning);
		gameRunning.addTransmision(EActionType.CREATE_BY_IDS, gameRunning);
		_state = initial;
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
		EActionType actionType = sto.getActionType();
		var nextState = _state.process(actionType);
		if (nextState != null) {
			switch (actionType) {
			case CREATE_PLAYER:
				var action = new CreatePlayerAction(_connection, sto.getValues()[0]);
				_environment.scheduleAction(action);
				break;
			case CONNECTION_CONFIRMED:
				// do nothing
				break;
			default:
				break;
			}
			_state = nextState;
//			System.out.println(sto.getActionType() + "" + sto.getValues()[0]);
		} else {
			System.err.println("Wrong type received: " + sto.getActionType());
		}
	}

}

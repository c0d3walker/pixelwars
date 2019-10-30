package de.pixelwars.server.internal;

import java.io.IOException;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.exchange.ClientState;
import de.pixelwars.core.exchange.DoubleTransportObject;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.impl.Location;
import de.pixelwars.core.net.Connection;
import de.pixelwars.server.actions.CreatePlayerAction;
import de.pixelwars.server.actions.SetTargetForElementAction;

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
		gameRunning.addTransmision(EActionType.SET_TARGET_FOR_ELEMENT, gameRunning);
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
				} else if (o instanceof DoubleTransportObject) {
					handleDoubleTransportObjectRequest((DoubleTransportObject) o);
				} else {
					System.err.println("Received an unknown dto");
				}
			}
		} catch (ClassNotFoundException |

				IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleDoubleTransportObjectRequest(DoubleTransportObject dto) {
		EActionType actionType = dto.getActionType();
		var nextState = _state.process(actionType);
		if (nextState != null) {
			switch (actionType) {
			case SET_TARGET_FOR_ELEMENT:
				var values=dto.getValues();
				var id=(int)values[0];
				var location=new Location((int)values[1],(int)values[2]);
				var action=new SetTargetForElementAction(id,location);
				_environment.scheduleAction(action);
				break;
			default:
				break;
			}
		} else {
			System.err.println("Wrong type received in client receiver: " + dto.getActionType());
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
			System.err.println("Wrong type received in client receiver: " + sto.getActionType());
		}
	}

}

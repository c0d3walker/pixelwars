package de.pixelwars.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import de.pixelwars.client.actions.CreateElementAction;
import de.pixelwars.client.actions.CreatePlayerAction;
import de.pixelwars.client.actions.UpdateUnitLocationAction;
import de.pixelwars.core.EActionType;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.exchange.ClientState;
import de.pixelwars.core.exchange.DoubleTransportObject;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.impl.Location;
import de.pixelwars.core.net.Connection;

public class PixelWarClient implements Runnable, IClient {

	private Connection _connection;
	private Socket _sock;
	private ClientState _state;
	private IGameEnvironment _environment;

	public PixelWarClient() {
		setupClientStateManagement();
		_environment = new ClientGameEnvironment(20, 20);
	}

	private void setupClientStateManagement() {
		var init = new ClientState();
		var receiveGameData = new ClientState();
		var gameRunning = new ClientState();

		init.addTransmision(EActionType.CONNECTION_CONFIRMED, receiveGameData);
		_state = init;

		receiveGameData.addTransmision(EActionType.CREATE_PLAYER, receiveGameData);
		receiveGameData.addTransmision(EActionType.CREATE_BY_IDS, receiveGameData);
		receiveGameData.addTransmision(EActionType.INITIALATION_FINISHED, gameRunning);

		gameRunning.addTransmision(EActionType.UPDATE_UNIT_LOCATION, gameRunning);
	}

	@Override
	public void run() {
		try {
			_connection = setupConnection("localhost", 8888);

			Object o;
			while ((o = _connection.getInputStream().readObject()) != null) {
				if (o instanceof StringTransportObject) {
					handleStringTransportObjectRequest((StringTransportObject) o);
				} else {
					handleDoubleTransportObjectRequest((DoubleTransportObject) o);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (_connection != null) {
					_connection.getInputStream().close();
					_connection.getOutputStream().close();
					_sock.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void handleDoubleTransportObjectRequest(DoubleTransportObject dto) {
		EActionType actionType = dto.getActionType();
		var nextState = _state.process(actionType);
		if (nextState != null) {
			double[] values = dto.getValues();
			var id = (int)values[0];
			switch (actionType) {
			case CREATE_BY_IDS:
				var elementType = values[1];// (unit / building)
				var ownerID = values[2];
				var subType = values[3];// (which unit / building)
				var action = new CreateElementAction(elementType, id, ownerID, subType);
				_environment.scheduleAction(action);
				break;
			case UPDATE_UNIT_LOCATION:
				var newX = (int) values[4];
				var newY = (int) values[5];
				var location = new Location(newX, newY);
				var updateUnitLocationAction = new UpdateUnitLocationAction(id, location);
				_environment.scheduleAction(updateUnitLocationAction);
				break;
			default:
				System.err.println("Unexpected action type: " + actionType);
				break;
			}
		}
	}

	private void handleStringTransportObjectRequest(StringTransportObject sto) throws IOException {
		EActionType actionType = sto.getActionType();
		var nextState = _state.process(actionType);
		if (nextState != null) {
			switch (actionType) {
			case CREATE_PLAYER:
				var values = sto.getValues();
				var id = Integer.parseInt(values[0]);
				var action = new CreatePlayerAction(id, values[1]);
				_environment.scheduleAction(action);
				break;
			case CONNECTION_CONFIRMED:
				var stoConfirmation = new StringTransportObject(EActionType.CONNECTION_CONFIRMED);
				sendTransportObject(stoConfirmation);
				sendTransportObject(new StringTransportObject(EActionType.CREATE_PLAYER, "Player"));
				var environmentThread = new Thread(_environment);
				environmentThread.setName("Client environment");
				environmentThread.start();
				break;
			case INITIALATION_FINISHED:
				break;
			default:
				System.err.println("Unexpected action type: " + actionType);
				break;
			}
			_state = nextState;
//			System.out.println(sto.getActionType() + "" + sto.getValues()[0]);
		} else {
			System.err.println("Wrong type received in client: " + sto.getActionType());
		}
	}

	private void sendTransportObjectInternal(Serializable object) throws IOException {
		_connection.getOutputStream().writeObject(object);
	}

	private Connection setupConnection(String host, int port) throws IOException, ClassNotFoundException {
		_sock = new Socket(host, port);
		var oos = new ObjectOutputStream(_sock.getOutputStream());
		var ois = new ObjectInputStream(_sock.getInputStream());

		return new Connection(oos, ois);
	}

	public IGameEnvironment getEnvironment() {
		return _environment;
	}

	@Override
	public void sendTransportObject(StringTransportObject sto) throws IOException {
		sendTransportObjectInternal(sto);
	}

	@Override
	public void sendTransportObject(DoubleTransportObject dto) throws IOException {
		sendTransportObjectInternal(dto);
	}

}

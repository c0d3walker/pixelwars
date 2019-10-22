package de.pixelwars.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import de.pixelwars.client.actions.CreateElementAction;
import de.pixelwars.client.actions.CreatePlayerAction;
import de.pixelwars.core.EActionType;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.exchange.ClientState;
import de.pixelwars.core.exchange.DoubleTransportObject;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.net.Connection;

public class PixelWarClient implements Runnable {

	private Connection _connection;
	private Socket _sock;
	private ClientState _state;
	private IGameEnvironment _environment;

	public PixelWarClient() {
		setupClientStateManagement();
		_environment=new ClientGameEnvironment();
	}

	private void setupClientStateManagement() {
		var init = new ClientState();
		var receiveGameData = new ClientState();
		
		init.addTransmision(EActionType.CONNECTION_CONFIRMED, receiveGameData);
		_state=init;
		
		receiveGameData.addTransmision(EActionType.CREATE_PLAYER, receiveGameData);
		receiveGameData.addTransmision(EActionType.CREATE_BY_IDS, receiveGameData);
	}

	@Override
	public void run() {
		try {
			_connection = setupConnection("localhost", 8888);
			sendTransportObject(new StringTransportObject(EActionType.CREATE_PLAYER, "Player"));
			Object o;
			while ((o = _connection.getInputStream().readObject()) != null) {
				if (o instanceof StringTransportObject) {
					handleStringTransportObjectRequest((StringTransportObject) o);
				} else {
					handleDoubleTransportObjectRequest((DoubleTransportObject)o);
				}			}
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
		if(nextState!=null) {
			switch (actionType) {
			case CREATE_BY_IDS:
				var values=dto.getValues();
				var elementType=values[0];//(unit / building)
				var ownerID=values[1];
				var subType=values[2];//(which unit / building)
				var action=new CreateElementAction(elementType,ownerID,subType);
				_environment.scheduleAction(action);
				break;
			default:
				System.err.println("Unexpected action type: "+actionType);
				break;
			}
		}
	}

	private void handleStringTransportObjectRequest(StringTransportObject sto) {
		EActionType actionType = sto.getActionType();
		var nextState = _state.process(actionType);
		if (nextState != null) {
			switch (actionType) {
			case CREATE_PLAYER:
				var action = new CreatePlayerAction(sto.getValues()[0]);
				_environment.scheduleAction(action);
				break;
			case CONNECTION_CONFIRMED:
				// do nothing
				break;
			default:
				System.err.println("Unexpected action type: " + actionType);
				break;
			}
			_state = nextState;
//			System.out.println(sto.getActionType() + "" + sto.getValues()[0]);
		} else {
			System.err.println("Wrong type received: " + sto.getActionType());
		}		
	}

	public void sendTransportObject(Serializable object) throws IOException {
		_connection.getOutputStream().writeObject(object);
	}

	private Connection setupConnection(String host, int port) throws IOException, ClassNotFoundException {
		_sock = new Socket(host, port);
		var oos = new ObjectOutputStream(_sock.getOutputStream());
		var ois = new ObjectInputStream(_sock.getInputStream());
		var confirmation = (StringTransportObject) ois.readObject();
		var sto = new StringTransportObject(EActionType.CONNECTION_CONFIRMED);
		oos.writeObject(sto);
		return new Connection(oos, ois);
	}

}

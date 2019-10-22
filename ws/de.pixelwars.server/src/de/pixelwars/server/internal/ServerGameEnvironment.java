package de.pixelwars.server.internal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.IBuilding;
import de.pixelwars.core.IPlayer;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.game.environment.AbstractGameEnvironment;
import de.pixelwars.core.net.Connection;

public class ServerGameEnvironment extends AbstractGameEnvironment {
	private Map<IPlayer, Connection> _playerLookup;

	public ServerGameEnvironment() {
		_playerLookup = new HashMap<>();
	}

	@Override
	public IPlayer createPlayer(String name, Connection connection) {
		IPlayer player = null;
		try {
			sendCurrentStateToNewPlayer(connection);
			player = super.createPlayer(name, null);
			_playerLookup.put(player, connection);
			for (IPlayer p : _playerList) {
				var c = _playerLookup.get(p);
				sendPlayerToConnection(connection, player);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return player;
	}

	private void sendCurrentStateToNewPlayer(Connection connection) throws IOException {

		for (IPlayer player : _playerList) {
			sendPlayerToConnection(connection, player);
		}
		for (IBuilding building : _buildingList) {
			var values = new int[] { IBuilding.BUILDING_CONSTANT, building.getOwnerID(),
					building.getBuildingType().ordinal() };
		}

	}

	public void sendPlayerToConnection(Connection connection, IPlayer player) throws IOException {
		var ownedBuildings = new StringBuilder();
		for (int buildingID : player.getBuildings()) {
			ownedBuildings.append(buildingID).append(',');
		}
		var values = new String[] { player.getID() + "", player.getName(), ownedBuildings.toString() };
		var sto = new StringTransportObject(EActionType.CREATE_PLAYER, values);
		connection.getOutputStream().writeObject(sto);
	}

}

package de.pixelwars.server.internal;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.EUnitConstants;
import de.pixelwars.core.IBuilding;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPlayer;
import de.pixelwars.core.IUnit;
import de.pixelwars.core.exchange.DoubleTransportObject;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.game.environment.AbstractGameEnvironment;
import de.pixelwars.core.impl.Building;
import de.pixelwars.core.impl.Player;
import de.pixelwars.core.impl.Unit;
import de.pixelwars.core.net.Connection;

public class ServerGameEnvironment extends AbstractGameEnvironment {
	private Map<IPlayer, Connection> _playerLookup;

	public ServerGameEnvironment() {
		super(20, 20);
		_playerLookup = new HashMap<>();
	}

	@Override
	public Building createBuilding(int ownerID, EBuildingConstants buildingType, boolean isBuild) {
		var building = super.createBuilding(ownerID, buildingType, isBuild);
		var dto = createBuildingTransportObject(building, EActionType.CREATE_BY_IDS);
		sendToAll(dto);
		return building;
	}

	@Override
	public Unit createUnit(int ownerID, EUnitConstants unitType) {
		var unit = super.createUnit(ownerID, unitType);
		var dto = createUnitTransportObject(unit, EActionType.CREATE_BY_IDS);
		sendToAll(dto);
		return unit;
	}

	private DoubleTransportObject createUnitTransportObject(IUnit unit, EActionType type) {
		var values = new double[] { unit.getID(), IUnit.UNIT_CONSTANT, unit.getOwnerID(), unit.getUnitType().ordinal(),
				unit.getLocation().getX(), unit.getLocation().getY() };
		return new DoubleTransportObject(type, values);
	}

	@Override
	public Player createPlayer(String name, Connection connection) {
		Player player = null;
		try {
			sendCurrentStateToNewPlayer(connection);
			player = super.createPlayer(name, null);
			_playerLookup.put(player, connection);
			for (IPlayer p : _playerList) {
				var c = _playerLookup.get(p);
				sendPlayerToConnection(c, player);
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
			createBuildingTransportObject(building, EActionType.CREATE_BY_IDS);
		}
	}

	public DoubleTransportObject createBuildingTransportObject(IBuilding building, EActionType type) {
		var values = new double[] { building.getID(), IBuilding.BUILDING_CONSTANT, building.getOwnerID(),
				building.getBuildingType().ordinal() };
		return new DoubleTransportObject(type, values);
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

	private void sendToAll(Serializable element) {
		var players = _playerLookup.keySet();
		for (IPlayer player : players) {
			var connection = _playerLookup.get(player);
			try {
				connection.getOutputStream().writeObject(element);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setElementTo(int id, ILocation location) {
		super.setElementTo(id, location);
		var proxy = getElement(location.getX(), location.getY());

		if (proxy.isPresent() && proxy.getValue() instanceof IUnit) {
			var unit = (IUnit) proxy.getValue();
			var dto = createUnitTransportObject(unit, EActionType.UPDATE_UNIT_LOCATION);
			sendToAll(dto);
		}
	}
}

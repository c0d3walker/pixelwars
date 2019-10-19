package de.pixelwars.server.internal;

import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.EUnitConstants;
import de.pixelwars.core.ILocation;
import de.pixelwars.server.impl.ServerBuildingImpl;
import de.pixelwars.server.impl.ServerPlayerImpl;
import de.pixelwars.server.impl.ServerUnitImpl;

public class ServerCoreElementFactory {
	private static int ID;

	public ServerCoreElementFactory() {
		ID = 0;
	}

	private int getID() {
		return ID++;
	}

	public ServerPlayerImpl createPlayer(String name) {
		var player = new ServerPlayerImpl(getID());
		player.setName(name);
		return player;
	}

	public ServerBuildingImpl createBuilding(ServerPlayerImpl player, EBuildingConstants buildingType,
			ILocation location, boolean isBuild) {
		var building = new ServerBuildingImpl(getID());
		building.setType(buildingType);
		building.setLocation(location);
		building.setIsBuilt(isBuild);
		building.setOwnerID(player.getID());
		player.addBuilding(building.getID());
		return building;
	}

	public ServerUnitImpl createUnit(ServerPlayerImpl owner, EUnitConstants unitType, ILocation location) {
		var unit = new ServerUnitImpl(getID());
		unit.setLocation(location);
		unit.setOwnerID(owner.getID());
		unit.setLifePoints(30);
		return unit;
	}

}

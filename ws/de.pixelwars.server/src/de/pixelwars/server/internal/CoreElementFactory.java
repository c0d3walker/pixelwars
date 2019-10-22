package de.pixelwars.server.internal;

import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.EUnitConstants;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.impl.Building;
import de.pixelwars.core.impl.Player;
import de.pixelwars.core.impl.Unit;

public class CoreElementFactory {
	private static int ID;

	public CoreElementFactory() {
		ID = 0;
	}

	private int getID() {
		return ID++;
	}

	public Player createPlayer(String name) {
		int id = getID();
		return createPlayerWithID(name, id);
	}

	public Player createPlayerWithID(String name, int id) {
		var player = new Player(id);
		player.setName(name);
		return player;
	}

	public Building createBuilding(Player player, EBuildingConstants buildingType,
			ILocation location, boolean isBuild) {
		int id = getID();
		return createPlayerWithID(player, buildingType, location, isBuild, id);
	}

	public Building createPlayerWithID(Player player, EBuildingConstants buildingType, ILocation location,
			boolean isBuild, int id) {
		var building = new Building(id);
		building.setType(buildingType);
		building.setLocation(location);
		building.setIsBuilt(isBuild);
		building.setOwnerID(player.getID());
		player.addBuilding(building.getID());
		return building;
	}

	public Unit createUnit(Player owner, EUnitConstants unitType, ILocation location) {
		int id = getID();
		return createUnitWithID(owner, location, id);
	}

	public Unit createUnitWithID(Player owner, ILocation location, int id) {
		var unit = new Unit(id);
		unit.setLocation(location);
		unit.setOwnerID(owner.getID());
		unit.setLifePoints(30);
		return unit;
	}

}

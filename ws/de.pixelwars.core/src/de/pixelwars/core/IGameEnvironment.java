package de.pixelwars.core;

public interface IGameEnvironment extends Runnable {

	/**
	 * executes a given action in the environment
	 * @param action
	 */
	void scheduleAction(IAction action);

	/**
	 * creates a new player in the game
	 * @param name
	 * @return the created player
	 */
	IPlayer createPlayer(String name);

	/**
	 * creates a new building to a certain place
	 * TODO take coordinates for placing the building
	 * @param ownerID id of the player who owns the building
	 * @param buildingType 
	 * @param isBuild iff true, the building is built and has all life points 
	 * @return the created building
	 */
	IBuilding buildBuilding(int ownerID, EBuildingConstants buildingType, boolean isBuild);

	/**
	 * creates a new unit of the specific type
	 * @param ownerID of the player who owns the unit
	 * @param unitType
	 * @return the created unit
	 */
	IUnit createUnit(int ownerID, EUnitConstants unitType);

}

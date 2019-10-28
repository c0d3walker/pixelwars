package de.pixelwars.core;

import java.util.Collection;

import de.pixelwars.core.net.Connection;

public interface IGameEnvironment extends Runnable {

	/**
	 * executes a given action in the environment
	 * 
	 * @param action
	 */
	void scheduleAction(IAction action);

	/**
	 * executes a given action out of order
	 * 
	 * @param action
	 */
	void scheduleActionAsynchron(IAction action);

	/**
	 * creates a new player in the game
	 * 
	 * @param name
	 * @param connection
	 * @return the created player
	 */
	IPlayer createPlayer(String name, Connection connection);

	/**
	 * reconstructs an already created player
	 * 
	 * @param id
	 * @param name
	 * @param connection
	 * @return
	 */
	IPlayer reconstructPlayer(int id, String name, Connection connection);

	/**
	 * creates a new building to a certain place TODO take coordinates for placing
	 * the building
	 * 
	 * @param ownerID      id of the player who owns the building
	 * @param buildingType
	 * @param isBuild      iff true, the building is built and has all life points
	 * @return the created building
	 */
	IBuilding createBuilding(int ownerID, EBuildingConstants buildingType, boolean isBuild);

	/**
	 * reconstructs a building
	 * 
	 * @param id
	 * @param ownerID
	 * @param buildingType
	 * @param isBuild
	 * @return
	 */
	IBuilding reconstructBuilding(int id, int ownerID, EBuildingConstants buildingType, boolean isBuild);

	/**
	 * creates a new unit of the specific type
	 * 
	 * @param ownerID  of the player who owns the unit
	 * @param unitType
	 * @return the created unit
	 */
	IUnit createUnit(int ownerID, EUnitConstants unitType);

	/**
	 * reconstructs an already created unit
	 * 
	 * @param id
	 * @param ownerID
	 * @param unitType
	 * @return
	 */
	IUnit reconstructUnit(int id, int ownerID, EUnitConstants unitType);

	/**
	 * translates a given player id into the corresponding object
	 * 
	 * @param playerID to address the correct object
	 * @return the found object or null otherwise
	 */
	IPlayer playerIdToPlayer(int playerID);

	/**
	 * calculates which elements are in a certain area
	 * 
	 * @param topLeft     point coordinates
	 * @param bottomRight point coordinates
	 * @return a list including all elements which are in this area
	 */
	Collection<IPositionedElement> getElementsInArea(ILocation topLeft, ILocation bottomRight);

	/**
	 * looks up the element on a certain position or null
	 * 
	 * @param x
	 * @param y
	 * @return the element which is placed on the given coordinates or null
	 */
	IPositionedElement getElement(int x, int y);

	/**
	 * transforms an id into the corresponding object or null if there isn't any element with the id
	 * @param id
	 * @return the corresponding object or null
	 */
	IAdressableElement getElementById(int id);

}

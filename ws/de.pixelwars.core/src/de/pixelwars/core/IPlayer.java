package de.pixelwars.core;

import java.util.List;

public interface IPlayer {

	/**
	 * @return the name of the player
	 */
	String getName();

	/**
	 * @return the id of the player
	 */
	int getID();
	
	/**
	 * @return a list with all buildings which belong to the player
	 */
	List<Integer> getBuildings();
}

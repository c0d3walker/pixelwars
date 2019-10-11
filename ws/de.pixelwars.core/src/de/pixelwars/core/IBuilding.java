package de.pixelwars.core;

public interface IBuilding extends IDetailedInformation {

	/**
	 * @return the name of the building
	 */
	String getName();

	/**
	 * @return the owner of the building
	 */
	IPlayer getOwner();

	/**
	 * @return the value of the life points
	 */
	double getLifePoints();

}

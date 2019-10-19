package de.pixelwars.core;

public interface IBuilding extends IDetailedInformation  {

	/**
	 * @return the name of the building
	 */
	String getName();

	/**
	 * @return the id of the owner of the building
	 */
	int getOwnerID();

	/**
	 * @return the value of the life points
	 */
	double getLifePoints();

	/**
	 * @return the type of the building
	 */
	EBuildingConstants getBuildingType();

	/**
	 * @return true iff the building was built completely once
	 */
	boolean isBuilt();

}

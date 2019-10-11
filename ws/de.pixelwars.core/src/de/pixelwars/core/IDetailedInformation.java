package de.pixelwars.core;

import java.util.Iterator;

public interface IDetailedInformation {

	/**
	 * @return the name of the object
	 */
	String getName();
	
	/**
	 * @return the owner of the object
	 */
	IPlayer getOwner();
	
	/**
	 * @return the life points
	 */
	double getLifePoints();
	
	/**
	 * @return the iterator for all registered actions
	 */
	Iterator<IAction>getActions();
	
	
}

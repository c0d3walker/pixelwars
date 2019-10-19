package de.pixelwars.core;

import java.util.Iterator;

public interface IDetailedInformation extends IOwnedElement{

	/**
	 * @return the name of the object
	 */
	String getName();

	/**
	 * @return the life points
	 */
	double getLifePoints();

	/**
	 * @return the iterator for all registered actions
	 */
	Iterator<IAction> getActions();

}

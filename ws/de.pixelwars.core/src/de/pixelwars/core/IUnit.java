package de.pixelwars.core;

public interface IUnit extends IDetailedInformation, IPositionedElement {

	static final int UNIT_CONSTANT = 0;

	/**
	 * @return the type of the unit
	 */
	EUnitConstants getUnitType();
}

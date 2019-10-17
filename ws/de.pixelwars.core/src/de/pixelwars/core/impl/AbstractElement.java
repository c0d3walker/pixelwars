package de.pixelwars.core.impl;

import de.pixelwars.core.IDetailedInformation;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPositionedElement;

public abstract class AbstractElement implements IPositionedElement,IDetailedInformation {

	private ILocation _location;

	public AbstractElement(ILocation location) {
		_location = location;
	}

	@Override
	public ILocation getLocation() {
		return _location;
	}

}

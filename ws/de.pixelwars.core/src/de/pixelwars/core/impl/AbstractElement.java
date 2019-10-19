package de.pixelwars.core.impl;

import de.pixelwars.core.IAdressableElement;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPositionedElement;

public abstract class AbstractElement implements IPositionedElement, IAdressableElement {

	private ILocation _location;
	
	private int id;

	private int _id;

	public AbstractElement(int id) {
		_id=id;
	}
	
	public void setLocation(ILocation location) {
		_location=location;
	}
	
	@Override
	public int getID() {
		return _id;
	}

	@Override
	public ILocation getLocation() {
		return _location;
	}

}

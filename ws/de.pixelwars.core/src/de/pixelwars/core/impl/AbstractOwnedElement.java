package de.pixelwars.core.impl;

import de.pixelwars.core.IOwnedElement;

public abstract class AbstractOwnedElement extends AbstractElement implements IOwnedElement {

	private int _ownerID;

	public AbstractOwnedElement(int id) {
		super(id);
		setOwnerID(-1);
	}

	public void setOwnerID(int id) {
		_ownerID = id;
	}

	@Override
	public int getOwnerID() {
		return _ownerID;
	}

}

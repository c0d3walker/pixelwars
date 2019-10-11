package de.pixelwars.core.impl;

import de.pixelwars.core.IPlayer;

public class Player implements IPlayer {

	private String _name;

	public void setName(String name) {
		_name = name;
	}

	@Override
	public String getName() {
		return _name;
	}

}

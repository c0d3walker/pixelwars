package de.pixelwars.core.impl;

import java.util.List;

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

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getBuildings() {
		// TODO Auto-generated method stub
		return null;
	}

}

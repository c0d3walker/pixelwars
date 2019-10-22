package de.pixelwars.core.impl;

import java.util.ArrayList;
import java.util.List;

import de.pixelwars.core.IPlayer;

public class Player extends AbstractOwnedElement implements IPlayer {
	private String _name;
	private List<Integer> _buildings;

	public Player(int id) {
		super(id);
		_buildings = new ArrayList<>();
	}

	public void setName(String name) {
		_name = name;
	}

	public void addBuilding(int buildingID) {
		_buildings.add(buildingID);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<Integer> getBuildings() {
		return _buildings;
	}

}

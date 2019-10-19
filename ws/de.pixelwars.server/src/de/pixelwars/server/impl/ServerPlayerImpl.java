package de.pixelwars.server.impl;

import java.util.ArrayList;
import java.util.List;

import de.pixelwars.core.IPlayer;
import de.pixelwars.core.impl.AbstractOwnedElement;

public class ServerPlayerImpl extends AbstractOwnedElement implements IPlayer {
	private String _name;
	private List<Integer> _buildings;

	public ServerPlayerImpl(int id) {
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

package de.pixelwars.server.impl;

import java.util.List;

import de.pixelwars.core.IPlayer;

public class ServerPlayerImpl implements IPlayer {
	private String _name;
	private int _id;
	private List<Integer> _buildings;

	public ServerPlayerImpl(int id) {
		_id = id;
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
	public int getID() {
		return 0;
	}

	@Override
	public List<Integer> getBuildings() {
		return _buildings;
	}

}

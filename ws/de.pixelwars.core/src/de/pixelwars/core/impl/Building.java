package de.pixelwars.core.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.IAction;
import de.pixelwars.core.IBuilding;

public class Building extends AbstractElement implements IBuilding {

	private double _lifePoints;
	private String _name;
	private EBuildingConstants _buildingType;
	private boolean _isBuilt;
	private int _ownerID;
	private List<IAction> _actions;

	public Building(int id) {
		super(id);
		_actions = new ArrayList<>();
	}

	public void setName(String name) {
		_name = name;
	}

	public void setLifePoints(double lifePoints) {
		_lifePoints = lifePoints;
	}

	public void setType(EBuildingConstants buildingType) {
		_buildingType = buildingType;
	}

	public void setIsBuilt(boolean isBuilt) {
		_isBuilt = isBuilt;
	}

	public void setOwnerID(int id) {
		_ownerID = id;
	}

	public void addAction(IAction action) {
		_actions.add(action);
	}

	@Override
	public String getName() {
		return getBuildingType().name();
//		return _name;
	}

	@Override
	public int getOwnerID() {
		return _ownerID;
	}

	@Override
	public double getLifePoints() {
		return _lifePoints;
	}

	@Override
	public EBuildingConstants getBuildingType() {
		return _buildingType;
	}

	@Override
	public boolean isBuilt() {
		return _isBuilt;
	}

	@Override
	public Iterator<IAction> getActions() {
		return _actions.iterator();
	}

}

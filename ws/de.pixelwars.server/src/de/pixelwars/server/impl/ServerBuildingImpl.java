package de.pixelwars.server.impl;

import java.util.Iterator;

import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.IAction;
import de.pixelwars.core.IBuilding;
import de.pixelwars.core.IPlayer;
import de.pixelwars.core.impl.AbstractElement;

public class ServerBuildingImpl extends AbstractElement implements IBuilding {

	private double _lifePoints;
	private String _name;
	private EBuildingConstants _buildingType;
	private boolean _isBuilt;
	private int _ownerID;

	public ServerBuildingImpl(int id) {
		super(id);
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

	@Override
	public String getName() {
		return _name;
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
		// TODO Auto-generated method stub
		return null;
	}

}

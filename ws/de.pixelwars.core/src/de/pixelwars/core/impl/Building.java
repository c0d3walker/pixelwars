package de.pixelwars.core.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.IAction;
import de.pixelwars.core.IBuilding;
import de.pixelwars.core.IDetailedInformation;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPlayer;
import de.pixelwars.core.actions.impl.CreateCitizenAction;

public class Building extends AbstractOwnedElement implements IBuilding {

	private double _lifePoints;
	private String _name;
	private List<IAction> _actions;

	public Building(ILocation location) {
		super(0);
	}

	public Building() {
		this(new Location(0, 0));
		_actions = new ArrayList<>();
	}

	public void setName(String name) {
		_name = name;
	}

	public void setLifePoints(double lifePoints) {
		_lifePoints = lifePoints;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public double getLifePoints() {
		return _lifePoints;
	}

	/**
	 * adds a certain action to the actions
	 * 
	 * @param action
	 */
	public void addAction(CreateCitizenAction action) {
		_actions.add(action);
	}

	@Override
	public Iterator<IAction> getActions() {
		return _actions.iterator();
	}

	@Override
	public EBuildingConstants getBuildingType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBuilt() {
		// TODO Auto-generated method stub
		return false;
	}

}

package de.pixelwars.core.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.pixelwars.core.IAction;
import de.pixelwars.core.IBuilding;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPlayer;
import de.pixelwars.core.actions.impl.CreateCitizenAction;

public class Building extends AbstractElement implements IBuilding {

	private double _lifePoints;
	private IPlayer _owner;
	private String _name;
	private List<IAction> _actions;

	public Building(ILocation location) {
		super(location);
	}

	public Building() {
		this(new Location(0, 0));
		_actions = new ArrayList<>();
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOwner(IPlayer player) {
		_owner = player;
	}

	public void setLifePoints(double lifePoints) {
		_lifePoints = lifePoints;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public IPlayer getOwner() {
		return _owner;
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

}

package de.pixelwars.core.impl;

import java.util.Iterator;
import java.util.List;

import de.pixelwars.core.EUnitConstants;
import de.pixelwars.core.IAction;
import de.pixelwars.core.IUnit;

public class Unit extends AbstractOwnedElement implements IUnit {

	private String _name;
	private double _lifePoints;
	private List<IAction> _actions;
	private EUnitConstants _unitType;

	public Unit(int id) {
		super(id);
	}

	public void setName(String name) {
		_name = name;
	}

	public void setLifePoints(double lifePoints) {
		_lifePoints = lifePoints;
	}

	public void setUnitType(EUnitConstants unitType) {
		_unitType = unitType;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public double getLifePoints() {
		return _lifePoints;
	}

	@Override
	public Iterator<IAction> getActions() {
		return _actions.iterator();
	}

	@Override
	public EUnitConstants getUnitType() {
		return _unitType;
	}

}

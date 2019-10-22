package de.pixelwars.client.actions;

import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.EUnitConstants;
import de.pixelwars.core.IAction;
import de.pixelwars.core.IBuilding;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.IUnit;

public class CreateElementAction implements IAction {

	private int _elementType;
	private int _ownerID;
	private int _subType;

	public CreateElementAction(double elementType, double ownerID, double subType) {
		_elementType = (int)elementType;
		_ownerID = (int) ownerID;
		_subType = (int)subType;
	}

	@Override
	public String getName() {
		return "Create element action";
	}

	@Override
	public void execute(IGameEnvironment environment) {
		if (_elementType == IBuilding.BUILDING_CONSTANT) {
			var buildingType=EBuildingConstants.values()[_subType];
			environment.buildBuilding(_ownerID, buildingType, true);
		}
		if (_elementType == IUnit.UNIT_CONSTANT) {
			var unitType=EUnitConstants.values()[_subType];
			environment.createUnit(_ownerID, unitType);
		}
	}

}

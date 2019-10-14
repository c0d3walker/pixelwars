package de.pixelwars.core.actions.impl;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.IAction;
import de.pixelwars.core.actions.DataTransportObject;

public class CreateBuildingAction implements IAction {

	@Override
	public String getName() {
		return ">main<";
	}

	@Override
	public void execute() {
		var dto = new DataTransportObject(0, EActionType.create);
        // TODO send to server
	}

}

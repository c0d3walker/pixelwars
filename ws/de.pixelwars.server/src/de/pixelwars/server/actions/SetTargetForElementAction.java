package de.pixelwars.server.actions;

import de.pixelwars.core.IAction;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.impl.Location;

public class SetTargetForElementAction implements IAction {

	private int _id;
	private Location _location;

	public SetTargetForElementAction(int id, Location location) {
		_id = id;
		_location = location;
	}

	@Override
	public String getName() {
		return "Set target for element " + _id + " action";
	}

	@Override
	public void execute(IGameEnvironment environment) {
		var element = environment.getElementById(_id);
	}

}

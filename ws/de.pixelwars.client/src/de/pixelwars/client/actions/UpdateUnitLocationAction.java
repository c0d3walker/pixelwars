package de.pixelwars.client.actions;

import de.pixelwars.core.IAction;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.ILocation;

public class UpdateUnitLocationAction implements IAction {

	private int _id;
	private ILocation _location;

	public UpdateUnitLocationAction(int id, ILocation location) {
		_id = id;
		_location = location;
	}

	@Override
	public String getName() {
		return "Update location for unit " + _id;
	}

	@Override
	public void execute(IGameEnvironment environment) {
		environment.setElementTo(_id, _location);
	}

}

package de.pixelwars.client.actions;

import de.pixelwars.core.IAction;
import de.pixelwars.core.IGameEnvironment;

public class CreatePlayerAction implements IAction {

	private String _name;

	public CreatePlayerAction(String name) {
		_name = name;
	}

	@Override
	public String getName() {
		return "Create player action";
	}

	@Override
	public void execute(IGameEnvironment environment) {
		environment.createPlayer(_name,null);
	}

}

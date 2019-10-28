package de.pixelwars.client.actions;

import de.pixelwars.core.IAction;
import de.pixelwars.core.IGameEnvironment;

public class CreatePlayerAction implements IAction {

	private String _name;
	private int _id;

	public CreatePlayerAction(int id,String name) {
		_name = name;
		_id=id;
	}

	@Override
	public String getName() {
		return "Create player action";
	}

	@Override
	public void execute(IGameEnvironment environment) {
		environment.reconstructPlayer(_id, _name, null);
	}

}

package de.pixelwars.server.actions;

import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.EUnitConstants;
import de.pixelwars.core.IAction;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.net.Connection;

public class CreatePlayerAction implements IAction {

	private Connection _connection;
	private String _name;

	public CreatePlayerAction(Connection connection, String name) {
		_connection = connection;
		_name = name;
	}

	@Override
	public String getName() {
		return "Create player " + _name;
	}

	@Override
	public void execute(IGameEnvironment environment) {
		var player=environment.createPlayer(_name);
		var building=environment.buildBuilding(player.getID(),EBuildingConstants.MAIN,true);
		var unit=environment.createUnit(player.getID(),EUnitConstants.WORKER);
	}

}

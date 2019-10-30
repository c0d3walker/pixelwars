package de.pixelwars.server.actions;

import java.io.IOException;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.EUnitConstants;
import de.pixelwars.core.IAction;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.exchange.StringTransportObject;
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
		var player = environment.createPlayer(_name,_connection);
		var building = environment.createBuilding(player.getID(), EBuildingConstants.MAIN, true);
		var unit = environment.createUnit(player.getID(), EUnitConstants.WORKER);
		try {
			_connection.getOutputStream().writeObject(new StringTransportObject(EActionType.INITIALATION_FINISHED));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

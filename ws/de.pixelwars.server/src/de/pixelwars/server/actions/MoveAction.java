package de.pixelwars.server.actions;

import java.util.List;

import de.pixelwars.core.IAction;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.IUnit;
import de.pixelwars.server.algorithms.AStar;

public class MoveAction implements IAction {

	private IUnit _unit;
	private ILocation _location;

	public MoveAction(IUnit unit, ILocation location) {
		_unit = unit;
		_location = location;
	}

	@Override
	public String getName() {
		return "Move action";
	}

	@Override
	public void execute(IGameEnvironment environment) {
		var currentLocation = _unit.getLocation();
		List<ILocation> route = calculateRoute(currentLocation, _location,environment);
		if (route.size()>=2) {
			var location = route.get(0);
			environment.setElementTo(_unit.getID(), route.get(1));
			if (!_location.equals(location)) {
				var action = new MoveAction(_unit, _location);
				environment.scheduleAction(action);
			}
		}
	}

	private List<ILocation> calculateRoute(ILocation from, ILocation to, IGameEnvironment environment) {
		var routing = new AStar();
		return routing.shortestPath(from, to, environment);
	}

}

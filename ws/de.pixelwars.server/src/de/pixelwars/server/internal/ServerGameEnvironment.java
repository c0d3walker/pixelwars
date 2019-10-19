package de.pixelwars.server.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.EUnitConstants;
import de.pixelwars.core.IAction;
import de.pixelwars.core.IBuilding;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPlayer;
import de.pixelwars.core.IUnit;
import de.pixelwars.core.impl.Location;
import de.pixelwars.server.impl.ServerPlayerImpl;

public class ServerGameEnvironment implements IGameEnvironment {

	private PriorityBlockingQueue<ScheduledTask> _tasks;
	private List<ServerPlayerImpl> _playerList;
	private ServerCoreElementFactory _coreElementFactory;

	public ServerGameEnvironment() {
		_playerList = new ArrayList<>();
		_coreElementFactory = new ServerCoreElementFactory();
		_tasks = new PriorityBlockingQueue<ScheduledTask>(64, (c0, c1) -> {
			return (int) (c0.getTimeToExecute() - c1.getTimeToExecute());
		});
	}

	@Override
	public void run() {
		// TODO replace with termination condition
		while (true) {
			var task = _tasks.peek();
			if (task != null && task.getTimeToExecute() < System.currentTimeMillis()) {
				var action = _tasks.poll().getAction();
				action.execute(this);
			} else {
				try {
					Thread.sleep(20);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void scheduleAction(IAction action) {
		var scheduledTask = new ScheduledTask(System.currentTimeMillis(), action);
		_tasks.add(scheduledTask);
	}

	@Override
	public IPlayer createPlayer(String name) {
		var player = _coreElementFactory.createPlayer(name);
		_playerList.add(player);
		return player;
	}

	@Override
	public IBuilding buildBuilding(int ownerID, EBuildingConstants buildingType, boolean isBuild) {
		// TODO refactor location as parameter
		ILocation location = new Location(3, 4);
		var owner = playerIdToPlayer(ownerID);
		return _coreElementFactory.createBuilding(owner, buildingType, location, isBuild);
	}

	private ServerPlayerImpl playerIdToPlayer(int ownerID) {
		ServerPlayerImpl owner = null;
		for (int i = 0; i < _playerList.size() && owner == null; i++) {
			var currentOwner = _playerList.get(i);
			if (currentOwner.getID() == ownerID) {
				owner = currentOwner;
			}
		}
		return owner;
	}

	@Override
	public IUnit createUnit(int ownerID, EUnitConstants unitType) {
		// TODO refactor location as parameter
		ILocation location = new Location(10, 11);
		var owner = playerIdToPlayer(ownerID);
		return _coreElementFactory.createUnit(owner, unitType, location);
	}

}

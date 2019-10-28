package de.pixelwars.core.game.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import de.pixelwars.core.CoreElementFactory;
import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.EUnitConstants;
import de.pixelwars.core.IAction;
import de.pixelwars.core.IAdressableElement;
import de.pixelwars.core.IBuilding;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPositionedElement;
import de.pixelwars.core.impl.Building;
import de.pixelwars.core.impl.Location;
import de.pixelwars.core.impl.Player;
import de.pixelwars.core.impl.Unit;
import de.pixelwars.core.net.Connection;
import de.pixelwars.core.trees.KDTree;

public abstract class AbstractGameEnvironment implements IGameEnvironment {

	protected List<Player> _playerList;
	protected List<IBuilding> _buildingList;
	protected Queue<ScheduledTask> _tasks;
	protected KDTree _elementArea;
	protected CoreElementFactory _coreElementFactory;
	private IPositionedElement[][] _gameField;
	private HashSet<IAdressableElement> _elements;

	public AbstractGameEnvironment(int width, int height) {
		_tasks = new PriorityBlockingQueue<ScheduledTask>(64, (c0, c1) -> {
			return (int) (c0.getTimeToExecute() - c1.getTimeToExecute());
		});
		_elementArea = new KDTree();
		_playerList = new ArrayList<>();
		_coreElementFactory = new CoreElementFactory();
		_buildingList = new ArrayList<>();
		_gameField = new IPositionedElement[width][height];
		_elements = new HashSet<IAdressableElement>();
	}

	protected void setElement(IPositionedElement element, int newX, int newY) {
		if (getElement(newX, newY) == null) {
			var location = (Location) element.getLocation();
			_gameField[location.getY()][location.getX()] = null;
			_gameField[newY][newX] = element;
			location.setLocation(newX, newY);
		}
	}

	protected boolean setElementInitially(IPositionedElement element, int newX, int newY) {
		var isSuccessful = getElement(newX, newY) == null;
		if (isSuccessful) {
			var location = (Location) element.getLocation();
			location.setLocation(newX, newY);
			_gameField[newY][newX] = element;
		}
		return isSuccessful;
	}

	public IPositionedElement getElement(int x, int y) {
		return _gameField[y][x];
	}

	private void addToPositionManagement(ILocation location, IPositionedElement element) {
		_elementArea.addElement(element);
		setElementInitially(element, location.getX(), location.getY());
	}

	@Override
	public void scheduleActionAsynchron(IAction action) {
		var task = new ScheduledTask(0, action);
		_tasks.add(task);
	}

	@Override
	public void scheduleAction(IAction action) {
		var scheduledTask = new ScheduledTask(System.currentTimeMillis(), action);
		_tasks.add(scheduledTask);
	}

	@Override
	public Collection<IPositionedElement> getElementsInArea(ILocation topLeft, ILocation bottomRight) {
		return _elementArea.getElementsInArea(topLeft, bottomRight);
	}

	@Override
	public Player playerIdToPlayer(int ownerID) {
		Player owner = null;
		for (int i = 0; i < _playerList.size() && owner == null; i++) {
			var currentOwner = _playerList.get(i);
			if (currentOwner.getID() == ownerID) {
				owner = currentOwner;
			}
		}
		return owner;
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
	public Player createPlayer(String name, Connection connection) {
		var player = _coreElementFactory.createPlayer(name);
		_playerList.add(player);
		return player;
	}

	@Override
	public Player reconstructPlayer(int id, String name, Connection connection) {
		var player = createPlayer(name, connection);
		player.setID(id);
		return player;
	}

	@Override
	public Building createBuilding(int ownerID, EBuildingConstants buildingType, boolean isBuild) {
		// TODO refactor location as parameter
		ILocation location = new Location(3, 4);
		var owner = playerIdToPlayer(ownerID);
		var building = _coreElementFactory.createBuilding(owner, buildingType, location, isBuild);
		addToPositionManagement(location, building);
		_buildingList.add(building);
		return building;
	}

	@Override
	public Building reconstructBuilding(int id, int ownerID, EBuildingConstants buildingType, boolean isBuild) {
		var building = createBuilding(ownerID, buildingType, isBuild);
		building.setID(id);
		return building;
	}

	@Override
	public Unit createUnit(int ownerID, EUnitConstants unitType) {
		// TODO refactor location as parameter
		ILocation location = new Location(1, 2);
		var owner = playerIdToPlayer(ownerID);
		var unit = _coreElementFactory.createUnit(owner, unitType, location);
		addToPositionManagement(location, unit);
		return unit;
	}

	@Override
	public Unit reconstructUnit(int id, int ownerID, EUnitConstants unitType) {
		var unit = createUnit(ownerID, unitType);
		unit.setID(id);
		return unit;
	}

	@Override
	public IAdressableElement getElementById(int id) {
		// TODO look up
		return null;
	}
}

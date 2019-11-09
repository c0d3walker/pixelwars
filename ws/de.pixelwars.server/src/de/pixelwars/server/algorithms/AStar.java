package de.pixelwars.server.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import de.pixelwars.core.IBuilding;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.impl.Location;

public class AStar {

	public List<ILocation> shortestPath(ILocation from, ILocation to, IGameEnvironment environment) {
		var directions = getDirections();
		var visitedLocations = new HashSet<ILocation>();
		var priorityQueue = new Heap<Step>((s0, s1) -> {
			return s0.getDistance() - s1.getDistance();
		});

		var start = to;
		var target = from;

		priorityQueue.add(new Step(start, 0));
		Step ending = null;
		do {
			var element = priorityQueue.poll();
			var location = element.getLocation();
			if (location.equals(target)) {
				ending = element;
				break;
			}
			visitedLocations.add(location);
			for (int[] direction : directions) {
				var nextLocation = new Location(location.getX() + direction[0], location.getY() + direction[1]);
				if (!visitedLocations.contains(nextLocation)) {
					var proxy = environment.getElement(nextLocation.getX(), nextLocation.getY());
					if (proxy.isPresent()) {
						var fieldContent = proxy.getValue();
						if (!(fieldContent instanceof IBuilding)) {
							var distance = element.getDistance() + l1distance(nextLocation, target);
							Step successor = new Step(nextLocation, distance);
							successor.setPredecessor(element);
							var currentIndex = priorityQueue.indexOf(successor);
							if (currentIndex > 0) {
								var queued = priorityQueue.get(currentIndex);
								if (queued.getDistance() > distance) {
									priorityQueue.updateEntry(successor, currentIndex);
								}
							} else {
								priorityQueue.add(successor);
							}
						}
					}
				} else {
					System.out.println();
				}
			}
		} while (!priorityQueue.isEmpty());

		var path = new ArrayList<ILocation>();
		while (ending != null) {
			path.add(ending.getLocation());
			ending = ending.getPredecessor();
		}
		path.add(to);
		return path;
	}

	private int l1distance(ILocation l0, ILocation l1) {
		return Math.abs(l0.getX() - l1.getX()) + Math.abs(l0.getY() - l1.getY());
	}

	private int[][] getDirections() {
		return new int[][] { { 0, -1 }, { -1, 0 }, { 1, 0 }, { 0, -1 } };
	}

	private class Step {

		private ILocation _location;
		private int _distance;
		private Step _predecessor;

		public Step(ILocation location, int distance) {
			_location = location;
			_distance = distance;
		}

		public ILocation getLocation() {
			return _location;
		}

		public int getDistance() {
			return _distance;
		}

		public void setPredecessor(Step step) {
			_predecessor = step;
		}

		public Step getPredecessor() {
			return _predecessor;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Step) {
				var step = (Step) o;
				return step.getLocation().equals(getLocation());
			}
			return false;
		}
	}

}

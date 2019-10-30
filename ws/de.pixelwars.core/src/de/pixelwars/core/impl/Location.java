package de.pixelwars.core.impl;

import de.pixelwars.core.ILocation;

public class Location implements ILocation {
	private int _x = 0;
	private int _y = 0;

	public Location(int x, int y) {
		setLocation(x, y);
	}

	public void setLocation(int x, int y) {
		_x = x;
		_y = y;
	}

	@Override
	public int getX() {
		return _x;
	}

	@Override
	public int getY() {
		return _y;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ILocation) {
			var location = (ILocation) o;
			return location.getX() == getX() && location.getY() == getY();
		}
		return false;
	}

	@Override
	public String toString() {
		return new StringBuilder().append('(').append(getX()).append('|').append(getY()).append(')').toString();
	}
}

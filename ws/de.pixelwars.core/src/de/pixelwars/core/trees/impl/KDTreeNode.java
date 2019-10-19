package de.pixelwars.core.trees.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPositionedElement;

public class KDTreeNode<T> {
	private static final int X_AXIS = 0;
	private static final int Y_AXIS = 1;
	private int _axis;
	private double _splitValue;
	private List<IPositionedElement> _elements = null;
	private int _elementsPerNode;
	private KDTreeNode<KDTreeNode<IPositionedElement>> _left, _right;

	public KDTreeNode(int elementsPerNode) {
		this(X_AXIS, elementsPerNode);
	}

	private KDTreeNode(int axis, int elementsPernode) {
		_axis = axis;
		_elementsPerNode = elementsPernode;
		_elements = new ArrayList(_elementsPerNode);
	}

	public void add(IPositionedElement element) {
		if (_elements != null) {
			if (_elements.size() <= _elementsPerNode) {
				_elements.add(element);
			} else {
				splitNode();
				addInternal(element);
			}
		} else {
			addInternal(element);
		}
	}

	private void addInternal(IPositionedElement element) {
		var loc = element.getLocation();
		if (_axis == X_AXIS) {
			var x = loc.getX();
			if (x < _splitValue) {
				_left.add(element);
			} else {
				_right.add(element);
			}
		} else {
			var y = loc.getY();
			if (y < _splitValue) {
				_left.add(element);
			} else {
				_right.add(element);
			}
		}
	}

	private void splitNode() {
		var comparator = _axis == X_AXIS ? new Comparator<IPositionedElement>() {
			@Override
			public int compare(IPositionedElement t0, IPositionedElement t1) {
				return t0.getLocation().getX() - t1.getLocation().getX();
			}
		} : new Comparator<IPositionedElement>() {
			@Override
			public int compare(IPositionedElement t0, IPositionedElement t1) {
				return t0.getLocation().getY() - t1.getLocation().getY();
			}
		};
		_left = new KDTreeNode<KDTreeNode<IPositionedElement>>(Y_AXIS - _axis, _elementsPerNode);
		_right = new KDTreeNode<KDTreeNode<IPositionedElement>>(Y_AXIS - _axis, _elementsPerNode);
		_elements.sort(comparator);
		var element = _elements.get(_elements.size() / 2);
		_splitValue = _axis == X_AXIS ? element.getLocation().getX() : element.getLocation().getY();
	}

	public Collection<IPositionedElement> collectElementsInArea(ILocation topLeft, ILocation bottomRight) {
		var elements = new ArrayList<IPositionedElement>();
		if (_elements != null) {
			for (IPositionedElement element : _elements) {
				if (isElementInArea(element, topLeft, bottomRight)) {
					elements.add(element);
				}
			}
		}
		var lower = getRelevantValue(topLeft);
		var upper = getRelevantValue(bottomRight);

		if (_splitValue < upper && _splitValue > lower) {
			elements.addAll(_left.collectElementsInArea(topLeft, bottomRight));
			elements.addAll(_right.collectElementsInArea(topLeft, bottomRight));
		}

		// TODO check for children
		return elements;
	}

	/**
	 * @node left top is (0|0)
	 * @node right bottom is (x+|y+)
	 */
	private boolean isElementInArea(IPositionedElement element, ILocation topLeft, ILocation bottomRight) {
		var loc = element.getLocation();

		var lower = getRelevantValue(topLeft);
		var upper = getRelevantValue(bottomRight);
		var value = getRelevantValue(loc);

		return valueCompare(lower, upper, value);

//		if (_axis == X_AXIS) {
//			return valueCompare(topLeft.getX(), bottomRight.getX(), loc.getX());
//		} else {
//			return valueCompare(bottomRight.getY(), topLeft.getY(), loc.getY());
//		}
	}

	private double getRelevantValue(ILocation loc) {
		if (_axis == X_AXIS) {
			return loc.getX();
		}
		return loc.getY();
	}

	private boolean valueCompare(double lower, double upper, double position) {
		return (upper >= position) && (lower <= position);
	}

}

package de.pixelwars.core.trees.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.pixelwars.core.IPositionedElement;
import de.pixelwars.core.impl.Location;

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

	public void add(Location loc, IPositionedElement element) {
		if (_elements != null) {
			if (_elements.size() <= _elementsPerNode) {
				_elements.add(element);
			} else {
				splitNode();
				addInternal(loc, element);
			}
		} else {
			addInternal(loc, element);
		}
	}

	private void addInternal(Location loc, IPositionedElement element) {
		if (_axis == X_AXIS) {
			var x = loc.getX();
			if (x < _splitValue) {
				_left.add(loc, element);
			} else {
				_right.add(loc, element);
			}
		} else {
			var y = loc.getY();
			if (y < _splitValue) {
				_left.add(loc, element);
			} else {
				_right.add(loc, element);
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

}

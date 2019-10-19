package de.pixelwars.core.trees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPositionedElement;
import de.pixelwars.core.trees.impl.KDTreeNode;

public class KDTree {

	private KDTreeNode<IPositionedElement> _root;

	public KDTree() {
		this(10);
	}

	public KDTree(int elementsPerNode) {
		_root = new KDTreeNode<IPositionedElement>(elementsPerNode);
	}

	public void addElement(IPositionedElement element) {
		_root.add(element);
	}

	public Collection<IPositionedElement> getElementsInArea(ILocation topLeft, ILocation bottomRight) {
		if (topLeft.getY() < bottomRight.getY() && topLeft.getX() < bottomRight.getX()) {
			return _root.collectElementsInArea(topLeft, bottomRight);
		} else {
			throw new IllegalArgumentException(
					"The value for 'topLeft' aren't the values which are at most at the top or at most left");
		}
	}
}

package de.pixelwars.core.trees;

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
}

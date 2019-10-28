package de.pixelwars.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import de.pixelwars.core.IPositionedElement;
import de.pixelwars.core.IUnit;

public class GraphicalWrapper extends JPanel {

	private IPositionedElement _element;

	public void setElement(IPositionedElement element) {
		_element = element;
		setSize(40,40);
		setBackground(new Color(0, 100, 0, 0));
		var loc = _element.getLocation();
		setLocation(loc.getX() * getSize().width, loc.getY() * getSize().height);
	}

	@Override
	public void paintComponent(Graphics gc) {
		var g2 = (Graphics2D) gc;
		super.paintComponent(gc);
		g2.setColor(Color.BLUE);
		if (_element instanceof IUnit) {
			g2.fillOval(5,5, 35,35);
		} else {
			g2.fillRect(2, 2, 36, 36);
		}
		g2.setColor(Color.GREEN);
		g2.drawLine(5, 5, 5, 35);

	}

}

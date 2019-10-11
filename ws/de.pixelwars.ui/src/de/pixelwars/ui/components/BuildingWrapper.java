package de.pixelwars.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.pixelwars.core.IBuilding;

public class BuildingWrapper extends JPanel {

	private IBuilding _building;

	public void setBuilding(IBuilding building) {
		_building = building;
		setSize(40, 40);
		setBackground(new Color(0,100,0,0));
	}

	@Override
	public void paintComponent(Graphics gc) {
		var g2 = (Graphics2D) gc;
		super.paintComponent(gc);
		g2.setColor(Color.GREEN);
		g2.drawLine(5, 5, 5, 35);
	}

}

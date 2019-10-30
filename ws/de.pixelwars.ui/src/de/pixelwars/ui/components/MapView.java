package de.pixelwars.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.pixelwars.core.IDetailedInformation;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.ILocation;
import de.pixelwars.core.IPositionedElement;

public class MapView extends JPanel {

	private DetailInfoView _infoView;
	private IGameEnvironment _environment;
	private Point _center;

	public MapView(IGameEnvironment environment, DetailInfoView infoView) {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(800, 800));
		setLayout(null);
		_environment = environment;
		_infoView = infoView;
		_center = new Point(0, 0);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				switch (me.getButton()) {
				case MouseEvent.BUTTON1:
					_infoView.showDetails(null);
					break;
				case MouseEvent.BUTTON3:
					var point = me.getPoint();
					var cell = new Point(point.x / 40, point.y / 40);
					_infoView.setTargetPositionForSelection(cell);
					break;
				}
			}
		});
		var refresher = new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
//						Thread.sleep(3_000);
						Thread.sleep(100);
						refreshContent();
						revalidate();
						repaint();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}

		};
		var thread = new Thread(refresher);
		thread.setName("Map refresher");
		thread.start();

//		var factory = new CoreElementFactory();
//		var player = factory.createPlayer("Lu");
//		var location = new Location(4, 6);
//		var building = factory.createBuilding(player, EBuildingConstants.MAIN, location, true);
//		var wrapped = new BuildingWrapper();
//		wrapped.setBuilding(building);
//		wrapped.setLocation(50, 50);
//		wrapped.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent me) {
//				_infoView.showDetails(building);
//			}
//		});

//		add(wrapped);

//		location = new Location(10, 3);
//		var mill = factory.createBuilding(player, EBuildingConstants.MILL, location, true);
//		wrapped = new BuildingWrapper();
//		wrapped.setBuilding(mill);
//		wrapped.setLocation(85, 105);
//		wrapped.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent me) {
//				_infoView.showDetails(mill);
//			}
//		});
//
//		add(wrapped);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		var g2 = (Graphics2D) g;
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, 800, 800);
		g2.setColor(Color.GRAY);
		for (int y = 0; y < 20; y++) {
			for (int x = 0; x < 20; x++) {
				g2.drawLine(x * 40, 0, x * 40, 800);
			}
			g2.drawLine(0, y * 40, 800, y * 40);
		}
	}

	protected void refreshContent() {
		removeAll();
		for (int y = 0; y < 20; y++) {
			for (int x = 0; x < 20; x++) {
				var element = _environment.getElement(x, y);
				if (element.isPresent() && element.getValue() != null) {
					addElementToView(element.getValue());
				}
			}
		}
		// showArea(topLeft, bottomRight);
	}

	private void showArea(ILocation topLeft, ILocation bottomRight) {
		var elements = _environment.getElementsInArea(topLeft, bottomRight);
		for (IPositionedElement element : elements) {
			addElementToView(element);
		}
	}

	public void addElementToView(IPositionedElement element) {
		var wrapped = new GraphicalWrapper();
		wrapped.setElement(element);
		wrapped.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				switch (me.getButton()) {
				case MouseEvent.BUTTON1:
					_infoView.showDetails((IDetailedInformation) element);
					break;
				default:
				}
			}
		});
		add(wrapped);
	}
}

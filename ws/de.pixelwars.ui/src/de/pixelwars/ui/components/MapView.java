package de.pixelwars.ui.components;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.pixelwars.core.CoreElementFactory;
import de.pixelwars.core.EBuildingConstants;
import de.pixelwars.core.impl.Location;

public class MapView extends JPanel {

	private DetailInfoView _infoView;

	public MapView(DetailInfoView infoView) {
		setBackground(Color.BLACK);
		setLayout(null);
		_infoView = infoView;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				_infoView.showDetails(null);
			}
		});
		var factory = new CoreElementFactory();
		var player = factory.createPlayer("Lu");
		var location = new Location(4, 6);
		var building = factory.createBuilding(player, EBuildingConstants.MAIN, location, true);
		var wrapped = new BuildingWrapper();
		wrapped.setBuilding(building);
		wrapped.setLocation(50, 50);
		wrapped.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				_infoView.showDetails(building);
			}
		});

		add(wrapped);

		location = new Location(10, 3);
		var mill = factory.createBuilding(player, EBuildingConstants.MILL, location, true);
		wrapped = new BuildingWrapper();
		wrapped.setBuilding(mill);
		wrapped.setLocation(85, 105);
		wrapped.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				_infoView.showDetails(mill);
			}
		});

		add(wrapped);

	}
}

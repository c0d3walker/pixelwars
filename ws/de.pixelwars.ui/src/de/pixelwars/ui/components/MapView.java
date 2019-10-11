package de.pixelwars.ui.components;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.pixelwars.core.BuildingFactory;
import de.pixelwars.core.CoreElementFactory;

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
		var player = CoreElementFactory.createPlayer("Lu");
		var building = BuildingFactory.createMain(player);
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

		var mill = BuildingFactory.createMill(player);
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

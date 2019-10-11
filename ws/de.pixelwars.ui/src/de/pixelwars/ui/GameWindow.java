package de.pixelwars.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.pixelwars.ui.components.DetailInfoView;
import de.pixelwars.ui.components.MapView;
import de.pixelwars.ui.components.StatusBar;

public class GameWindow extends JFrame {

	public GameWindow() {
		super("Pixel Wars");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		var defaultPane = this.getContentPane();
		defaultPane.setLayout(new BorderLayout());

		createDefaultView();
		var infoView=createDetailInfo();
		createMapView(infoView);
	}

	private void createMapView(DetailInfoView infoView) {
		var map = new MapView(infoView);
		this.getContentPane().add(map, BorderLayout.CENTER);
	}

	private DetailInfoView createDetailInfo() {
		var detailInfoView = new DetailInfoView();
		this.getContentPane().add(detailInfoView, BorderLayout.SOUTH);return detailInfoView;
	}

	private void createDefaultView() {
		var statusBar = new StatusBar();
		this.getContentPane().add(statusBar, BorderLayout.NORTH);
	}
}

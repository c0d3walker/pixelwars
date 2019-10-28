package de.pixelwars.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.pixelwars.client.IClient;
import de.pixelwars.client.PixelWarClient;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.ui.components.DetailInfoView;
import de.pixelwars.ui.components.MapView;
import de.pixelwars.ui.components.StatusBar;

public class GameWindow extends JFrame {

	public GameWindow(IClient client, IGameEnvironment environment) {
		super("Pixel Wars");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		var defaultPane = this.getContentPane();
		defaultPane.setLayout(new BorderLayout());

		createDefaultView();
		var infoView = createDetailInfo(client,environment);
		createMapView(infoView, environment);
	}

	private void createMapView(DetailInfoView infoView, IGameEnvironment environment) {
		var map = new MapView(environment,infoView);
		this.getContentPane().add(map, BorderLayout.CENTER);
	}

	private DetailInfoView createDetailInfo(IClient client, IGameEnvironment environment) {
		var detailInfoView = new DetailInfoView(client,environment);
		this.getContentPane().add(detailInfoView, BorderLayout.SOUTH);
		return detailInfoView;
	}

	private void createDefaultView() {
		var statusBar = new StatusBar();
		this.getContentPane().add(statusBar, BorderLayout.NORTH);
	}
}

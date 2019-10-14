package de.pixelwars.application;

import de.pixelwars.client.PixelWarClient;
import de.pixelwars.server.PixelWarConfigurationServer;
import de.pixelwars.ui.GameWindow;

public class PixelWars {

	public static void main(String[] args) {
		var server=new PixelWarConfigurationServer();
		new Thread(server).start();
		
		var client=new PixelWarClient();
		new Thread(client).start();
		
		GameWindow window = new GameWindow();
		window.setVisible(true);
	}
}

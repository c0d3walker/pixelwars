package de.pixelwars.application;

import de.pixelwars.client.PixelWarClient;
import de.pixelwars.server.PixelWarConfigurationServer;

public class PixelWars {

	public static void main(String[] args) {
		var server = new PixelWarConfigurationServer();
		var serverThread = new Thread(server);
		serverThread.setName("Welcome Server");
		serverThread.start();

		var client = new PixelWarClient();
		var clientThread = new Thread(client);
		clientThread.setName("Client Thread");
		clientThread.start();

//		GameWindow window = new GameWindow();
//		window.setVisible(true);
	}
}

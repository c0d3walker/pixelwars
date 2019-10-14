package de.pixelwars.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import de.pixelwars.server.internal.ClientReceiver;
import de.pixelwars.server.internal.Connection;

public class PixelWarConfigurationServer implements Runnable {
	private List<Connection> _connections;

	public PixelWarConfigurationServer() {
		_connections = new ArrayList<>();
	}

	private void setupModelExchange(int port) {
		try (ServerSocket sock = new ServerSocket(port)) {
			System.out.println("Server startet at host "+sock.getLocalSocketAddress()+" at port "+sock.getLocalPort());
			while (true) {
				var client = sock.accept();
				System.out.println("Client registered:"+client.getInetAddress());
				var os = client.getOutputStream();
				var is = client.getInputStream();
				var connection = new Connection(os, is);
				_connections.add(connection);
				new Thread(new ClientReceiver(connection)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		setupModelExchange(8888);

	}

}

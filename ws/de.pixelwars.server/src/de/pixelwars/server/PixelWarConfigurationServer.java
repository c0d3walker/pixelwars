package de.pixelwars.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import de.pixelwars.core.EActionType;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.exchange.StringTransportObject;
import de.pixelwars.core.net.Connection;
import de.pixelwars.server.internal.ClientReceiver;
import de.pixelwars.server.internal.ServerGameEnvironment;

public class PixelWarConfigurationServer implements Runnable {
	private List<Connection> _connections;
	IGameEnvironment _environment;

	public PixelWarConfigurationServer() {
		_connections = new ArrayList<>();
		_environment = new ServerGameEnvironment();
	}

	private void setupModelExchange(int port) {
		try (ServerSocket sock = new ServerSocket(port)) {
			System.out.println(
					"Server startet at host " + sock.getLocalSocketAddress() + " at port " + sock.getLocalPort());
			while (true) {
				var client = sock.accept();
				System.out.println("Client registered:" + client.getInetAddress());
				var connection = createAndConfirmConnection(client);
				new Thread(new ClientReceiver(connection, _environment)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Connection createAndConfirmConnection(Socket client) throws IOException {
		var os = client.getOutputStream();
		var oos = new ObjectOutputStream(os);
		var is = client.getInputStream();
		var ois = new ObjectInputStream(is);
		var connection = new Connection(oos, ois);
		oos.writeObject(new StringTransportObject(EActionType.CONNECTION_CONFIRMED));
		_connections.add(connection);
		return connection;
	}

	@Override
	public void run() {
		var environmentThread = new Thread(_environment);
		environmentThread.setName("Game Environment - Server");
		environmentThread.start();
		setupModelExchange(8888);

	}

}

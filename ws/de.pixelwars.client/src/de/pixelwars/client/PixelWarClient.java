package de.pixelwars.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PixelWarClient implements Runnable{

	@Override
	public void run() {
		try {
			setupConnection("localhost",8888);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void setupConnection(String host, int port) throws IOException {
		try(Socket sock=new Socket(host,port)){
			var ois=new ObjectInputStream(sock.getInputStream());
			var oos=new ObjectOutputStream(sock.getOutputStream());
		}
		
	}

}

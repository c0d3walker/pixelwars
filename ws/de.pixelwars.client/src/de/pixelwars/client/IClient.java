package de.pixelwars.client;

import java.io.IOException;

import de.pixelwars.core.exchange.DoubleTransportObject;
import de.pixelwars.core.exchange.StringTransportObject;

public interface IClient {

	/**
	 * sends a <class>StringTransportObject</class> to the server
	 * @param sto
	 * @throws IOException 
	 */
	void sendTransportObject(StringTransportObject sto) throws IOException;
	
	/**
	 * sends aa <class>DoubleTransportObject</class> to the server
	 * @param dto
	 * @throws IOException 
	 */
	void sendTransportObject(DoubleTransportObject dto) throws IOException;
}

package de.pixelwars.core;

import de.pixelwars.core.impl.Building;
import de.pixelwars.core.impl.Player;

public class CoreElementFactory {

	public static IPlayer createPlayer(String name) {
		var player = new Player();
		player.setName(name);
		return player;
	}

}

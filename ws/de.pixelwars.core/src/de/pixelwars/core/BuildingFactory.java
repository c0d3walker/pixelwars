package de.pixelwars.core;

import de.pixelwars.core.actions.impl.CreateCitizenAction;
import de.pixelwars.core.impl.Building;

public class BuildingFactory {
	/**
	 * creates a new main building
	 * @param owner
	 * @return the created building
	 */
	public static IBuilding createMain(IPlayer owner) {
		var building = new Building();
		building.setLifePoints(100);
		building.setName("Main");
		building.setOwner(owner);
		building.addAction(new CreateCitizenAction());
		return building;
	}
	
	/**
	 * creates a new mill
	 * @param owner
	 * @return the created building
	 */
	public static IBuilding createMill(IPlayer owner) {
		var building = new Building();
		building.setLifePoints(100);
		building.setName("Mill");
		building.setOwner(owner);
		return building;
	}
	
}

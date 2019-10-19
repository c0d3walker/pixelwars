package de.pixelwars.core.actions.impl;

import de.pixelwars.core.IAction;
import de.pixelwars.core.IGameEnvironment;

public class CreateCitizenAction implements IAction {

	@Override
	public String getName() {
		return "Create citizen";
	}

	@Override
	public void execute(IGameEnvironment environment) {
		System.out.println("+1 citizen");
	}

}

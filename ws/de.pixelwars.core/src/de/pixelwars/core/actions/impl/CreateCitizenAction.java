package de.pixelwars.core.actions.impl;

import de.pixelwars.core.IAction;

public class CreateCitizenAction implements IAction {

	@Override
	public String getName() {
		return "Create citizen";
	}

	@Override
	public void execute() {
		System.out.println("+1 citizen");
	}

}

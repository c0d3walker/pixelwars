package de.pixelwars.core.game.environment;

import de.pixelwars.core.IAction;

public class ScheduledTask {

	private long _tte;
	private IAction _action;

	public ScheduledTask(long timeToExecute, IAction action) {
		_tte = timeToExecute;
		_action = action;
	}

	public long getTimeToExecute() {
		return _tte;
	}

	public IAction getAction() {
		return _action;
	}
}

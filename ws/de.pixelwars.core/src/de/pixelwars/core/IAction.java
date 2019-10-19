package de.pixelwars.core;

public interface IAction {

	/**
	 * @return the name of the action
	 */
	String getName();
	
	/**
	 * @return true iff the action can be executed
	 * @param environment of the game
	 */
	default boolean isExecutable(IGameEnvironment environment) {return true;}
	
	/**
	 * executes the action
	 * @param environment of the game
	 */
	void execute(IGameEnvironment environment);
}

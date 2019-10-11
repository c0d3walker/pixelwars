package de.pixelwars.core;

public interface IAction {

	/**
	 * @return the name of the action
	 */
	String getName();
	
	/**
	 * @return true iff the action can be executed
	 */
	default boolean isExecutable() {return true;}
	
	/**
	 * executes the action
	 */
	void execute();
}

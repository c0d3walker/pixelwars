package de.pixelwars.core;

public interface IProxy<T> {
/**
 * @return true if the element is present
 */
	boolean isPresent();
	
	/**
	 * @return the value of the element
	 */
	T getValue();
}

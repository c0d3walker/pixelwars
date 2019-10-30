package de.pixelwars.core.impl;

import de.pixelwars.core.IProxy;

public class Proxy<T> implements IProxy<T> {

	private T _element;
	private boolean _isPresent;

	public Proxy(T element, boolean isPresent) {
		_element = element;
		_isPresent = isPresent;
	}

	@Override
	public boolean isPresent() {
		return _isPresent;
	}

	@Override
	public T getValue() {
		return _element;
	}

}

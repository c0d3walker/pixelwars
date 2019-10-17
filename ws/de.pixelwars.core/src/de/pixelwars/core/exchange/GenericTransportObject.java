package de.pixelwars.core.exchange;

import java.io.Serializable;

public class GenericTransportObject<T> implements Serializable {

	private T _value;

	public GenericTransportObject(T value) {
		_value = value;
	}

	public T getValues() {
		return _value;
	}

}

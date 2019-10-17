package de.pixelwars.core.exchange;

import java.io.Serializable;

import de.pixelwars.core.EActionType;

public class StringTransportObject implements Serializable {
	private EActionType _actionType;
	private String[] _values;

	public StringTransportObject(EActionType actionType, String... values) {
		_actionType = actionType;
		_values = values.length == 0 ? new String[] {} : values;
	}

	public EActionType getActionType() {
		return _actionType;
	}

	public String[] getValues() {
		return _values;
	}
}

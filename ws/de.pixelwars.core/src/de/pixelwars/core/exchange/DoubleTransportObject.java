package de.pixelwars.core.exchange;

import java.io.Serializable;

import de.pixelwars.core.EActionType;

public class DoubleTransportObject implements Serializable {

	private EActionType _actionType;
	private double[] _values;

	public DoubleTransportObject(EActionType actionType, double... values) {
		_actionType = actionType;
		_values = values;
	}

	public EActionType getActionType() {
		return _actionType;
	}

	public double[] getValues() {
		return _values;
	}
}

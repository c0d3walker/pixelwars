package de.pixelwars.core.actions;

import java.io.Serializable;

import de.pixelwars.core.EActionType;

public class DataTransportObject implements Serializable {

	private int _objectID;
	private EActionType _actionType;

	public DataTransportObject(int objectID, EActionType actionType) {
		_objectID = objectID;
		_actionType = actionType;
	}

	public int getObjectID() {
		return _objectID;
	}

	public EActionType getActionType() {
		return _actionType;
	}
}

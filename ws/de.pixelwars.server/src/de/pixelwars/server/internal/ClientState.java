package de.pixelwars.server.internal;

import de.pixelwars.core.EActionType;

public class ClientState {

	private EActionType[] _allowedActionTypes;
	private ClientState _next;

	public ClientState(ClientState next, EActionType... allowedActionTypes) {
		_next = next;
		_allowedActionTypes = allowedActionTypes;
	}

	public boolean process(EActionType current) {
		var wasFound = false;
		for (int i = 0; i < _allowedActionTypes.length && !wasFound; i++) {
			wasFound = _allowedActionTypes[i] == current;
		}
		return wasFound;
	}
	
}

package de.pixelwars.server.internal;

import java.util.ArrayList;
import java.util.List;

import de.pixelwars.core.EActionType;

public class ClientState {

	private List<Transmission> _transmissions;

	public ClientState() {
		_transmissions = new ArrayList<>();
	}

	public ClientState process(EActionType current) {
		var optinalTransmission = _transmissions.stream().filter(t -> t.getActionType() == current).findFirst();
		return optinalTransmission.isPresent() ? optinalTransmission.get().getNextState() : null;
	}

	public void addTransmision(EActionType actionType, ClientState clientState) {
		_transmissions.add(new Transmission(actionType, clientState));
	}

	private class Transmission {
		private EActionType _actionType;
		private ClientState _clientState;

		public Transmission(EActionType actionType, ClientState clientState) {
			_actionType = actionType;
			_clientState = clientState;
		}

		public EActionType getActionType() {
			return _actionType;
		}

		public ClientState getNextState() {
			return _clientState;
		}
	}

}

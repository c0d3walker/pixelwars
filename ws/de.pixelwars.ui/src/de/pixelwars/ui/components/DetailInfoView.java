package de.pixelwars.ui.components;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.pixelwars.client.IClient;
import de.pixelwars.core.EActionType;
import de.pixelwars.core.IDetailedInformation;
import de.pixelwars.core.IGameEnvironment;
import de.pixelwars.core.exchange.DoubleTransportObject;

public class DetailInfoView extends JPanel {

	private JLabel _nameStatus;
	private JLabel _lifePointStatus;
	private JLabel _ownerStatus;
	private JPanel _actionPanel;
	private int _currentSelection;
	private IGameEnvironment _environment;
	private IClient _client;

	public DetailInfoView(IClient client, IGameEnvironment environment) {
		_environment = environment;
		_client = client;
		_currentSelection = -1;
		setLayout(new GridLayout(1, 1));
		JPanel container = new JPanel();
		add(container);

		container.setLayout(new FlowLayout(FlowLayout.LEFT));
//		container.add(new JLabel("abc"));
//		container.add(new JLabel("def"));

//		Box.createHorizontalBox();

//		var boxLayout = new FlowLayout();
//		container.setLayout(boxLayout);

		JPanel commonDetailPanel = new JPanel();
		container.add(commonDetailPanel);
		commonDetailPanel.setAlignmentY(Component.LEFT_ALIGNMENT);

		var l = new GridBagLayout();
		commonDetailPanel.setLayout(l);

		var image = new JPanel();
		image.setBorder(BorderFactory.createEtchedBorder());
		commonDetailPanel.add(image);

		var imageConstraints = new GridBagConstraints();
		imageConstraints.gridx = 0;
		imageConstraints.gridy = 0;
		imageConstraints.gridheight = 3;
		imageConstraints.gridwidth = 3;
		imageConstraints.ipady = 40;
		imageConstraints.ipadx = 40;
		l.addLayoutComponent(image, imageConstraints);

		var namePanel = new JPanel();
		var nameLabel = new JLabel("Name:");
		_nameStatus = new JLabel("");
		namePanel.add(nameLabel);
		namePanel.add(_nameStatus);
		commonDetailPanel.add(namePanel);

		var nameConstraints = new GridBagConstraints();
		nameConstraints.gridx = 3;
		nameConstraints.gridy = 0;
		nameConstraints.fill = GridBagConstraints.HORIZONTAL;
		l.addLayoutComponent(namePanel, nameConstraints);

		var lifePointPanel = new JPanel();
		var lifePointLabel = new JLabel("Life:");
		_lifePointStatus = new JLabel("");
		lifePointPanel.add(lifePointLabel);
		lifePointPanel.add(_lifePointStatus);
		commonDetailPanel.add(lifePointPanel);

		var lifePointConstraints = new GridBagConstraints();
		lifePointConstraints.gridx = 3;
		lifePointConstraints.gridy = 1;
		lifePointConstraints.fill = GridBagConstraints.HORIZONTAL;
		l.addLayoutComponent(lifePointPanel, lifePointConstraints);

		var ownerPanel = new JPanel();
		var ownerLabel = new JLabel("Owner:");
		_ownerStatus = new JLabel("");
		ownerPanel.add(ownerLabel);
		ownerPanel.add(_ownerStatus);
		commonDetailPanel.add(ownerPanel);

		var ownerConstraints = new GridBagConstraints();
		ownerConstraints.gridx = 3;
		ownerConstraints.gridy = 2;
		ownerConstraints.fill = GridBagConstraints.HORIZONTAL;
		l.addLayoutComponent(ownerPanel, ownerConstraints);

		_actionPanel = new JPanel();
		commonDetailPanel.add(_actionPanel);
		_actionPanel.setLayout(new BoxLayout(_actionPanel, BoxLayout.X_AXIS));
	}

	public void showDetails(IDetailedInformation information) {
		_actionPanel.removeAll();
		if (information == null) {
			_nameStatus.setText("");
			_ownerStatus.setText("");
			_lifePointStatus.setText("");
			_currentSelection = -1;
		} else {
			_currentSelection = information.getID();
			_nameStatus.setText(information.getName());
			var owner = _environment.playerIdToPlayer(information.getOwnerID());
			var ownerName = owner.getName();
			_ownerStatus.setText(ownerName);
			_lifePointStatus.setText(information.getLifePoints() + "");
			var actionIterator = information.getActions();
			while (actionIterator.hasNext()) {
				var action = actionIterator.next();
				var button = new JButton();
				button.setText(action.getName());
				button.addActionListener(ae -> action.execute(null));
				_actionPanel.add(button);
			}
		}
	}

	public void setTargetPositionForSelection(Point point) {
		var dto = new DoubleTransportObject(EActionType.SET_TARGET_FOR_ELEMENT, _currentSelection, point.x, point.y);
		try {
			_client.sendTransportObject(dto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

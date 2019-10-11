package de.pixelwars.ui.components;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel {

	private JLabel _foodStatus;
	private JLabel _woodStatus;
	private JLabel _ironStatus;
	private JLabel _stoneStatus;

	public StatusBar() {
		var foodPanel = new JPanel();
		var foodLabel = new JLabel("Food:");
		_foodStatus = new JLabel();
		foodPanel.add(foodLabel);
		foodPanel.add(_foodStatus);

		var woodPanel = new JPanel();
		var woodLabel = new JLabel("Wood:");
		_woodStatus = new JLabel();
		woodPanel.add(woodLabel);
		woodPanel.add(_woodStatus);

		var ironPanel = new JPanel();
		var ironLabel = new JLabel("Iron:");
		_ironStatus = new JLabel();
		ironPanel.add(ironLabel);
		ironPanel.add(_ironStatus);

		var stonePanel = new JPanel();
		var stoneLabel = new JLabel("Stone:");
		_stoneStatus = new JLabel();
		stonePanel.add(stoneLabel);
		stonePanel.add(_stoneStatus);

		var boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(boxLayout);

		add(foodPanel);
		add(woodPanel);
		add(ironPanel);
		add(stonePanel);
	}

}

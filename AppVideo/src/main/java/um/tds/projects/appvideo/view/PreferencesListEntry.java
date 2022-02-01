package um.tds.projects.appvideo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import um.tds.projects.appvideo.view.Constants;

import javax.swing.JLabel;
import javax.swing.JComponent;


@SuppressWarnings("serial")
public class PreferencesListEntry extends JPanel {

	private JPanel vPanel;
	private JComponent inner;

	public PreferencesListEntry(JComponent inner) {
		this.inner = inner;

		setBackground(Constants.BUTTON_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		setMinimumSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		setPreferredSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));

		addComponents();
	}

	public void updateBackground(Color bg) {
		setBackground(bg);
		vPanel.setBackground(bg);
	}

	private void addComponents() {
		vPanel = new JPanel();
		vPanel.setBackground(getBackground());
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));

		JPanel hPanel = new JPanel();
		hPanel.setBackground(getBackground());
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));
		hPanel.setAlignmentX(LEFT_ALIGNMENT);
		hPanel.add(inner);

		vPanel.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH - 10, 5)));
		vPanel.add(hPanel);
		vPanel.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH - 10, 5)));

		add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT)));
		add(vPanel);
		add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT)));
	}

}

package um.tds.projects.appvideo.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class PreferencesListEntry extends JPanel {

	private JPanel vPanel;
	private JComponent inner;
	public PreferencesListEntry(JComponent inner, int height) {
		this.inner = inner;

		setBackground(Constants.FOREGROUND_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		setMaximumSize(new Dimension(Constants.PAGE_WIDTH, height));
		setMinimumSize(new Dimension(Constants.PAGE_WIDTH, height));
		setPreferredSize(new Dimension(Constants.PAGE_WIDTH, height));

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
		hPanel.setAlignmentX(CENTER_ALIGNMENT);
		hPanel.add(inner);

		vPanel.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH - 10, 5)));
		vPanel.add(hPanel);
		vPanel.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH - 10, 5)));
		
		
		add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT)));
		add(vPanel);
		add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT)));
	}

}

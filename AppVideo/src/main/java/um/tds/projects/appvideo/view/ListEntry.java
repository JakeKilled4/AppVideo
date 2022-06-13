package um.tds.projects.appvideo.view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class ListEntry extends JPanel {

	protected MainWindow mainWindow;
	private JPanel vPanel;

	public ListEntry(MainWindow mainWindow) {
		this.mainWindow = mainWindow;

		setBackground(Constants.BUTTON_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		setMinimumSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		setPreferredSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
	}

	protected abstract JPanel createInnerPanel();

	public void updateBackground(Color bg) {
		setBackground(bg);
		vPanel.setBackground(bg);
	}

	protected void addComponents() {
		vPanel = new JPanel();
		vPanel.setBackground(getBackground());
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));
		
		vPanel.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH - 5 , 5)));
		vPanel.add(createInnerPanel());
		vPanel.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH - 5, 5)));
		
		add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT)));
		add(vPanel);
		add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT)));
	}

}

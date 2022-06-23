package um.tds.projects.appvideo.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class CommonPanel extends JPanel {

	protected static Logger logger = Logger.getLogger("um.tds.projects.appvideo.view.CommonPanel");

	// Every mayor panel holds a reference to the main window.
	protected MainWindow mainWindow;

	private JPanel toolbarPanel; // The foldable left panel holding all mayor pages.
	private JPanel searchBar;    // The search bar at the top of the screen.
	private JPanel innerPanel;   // Holds the panel's main information.
	private JPanel rightPanel;   // Optionally display some info at the right of the screen.
	private JPanel centerPanel;  // Panel containing all the main content panels.


	public CommonPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	protected void createScreen() {
		setBackground(Constants.BACKGROUND_COLOR);
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());

		// Defines the panel's data
		toolbarPanel = new ToolbarPanel(mainWindow);
		searchBar 	 = new SearchBar   (Constants.BACKGROUND_COLOR);
		innerPanel   = createInnerPanel();
		rightPanel   = createRightPanel();

		// Creat the main content panel
		centerPanel = new JPanel();

		centerPanel.setBackground(Constants.BACKGROUND_COLOR);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		centerPanel.add(searchBar);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		centerPanel.add(innerPanel);


		add(toolbarPanel, BorderLayout.WEST);
		add(centerPanel,  BorderLayout.CENTER);
		if (rightPanel != null) {
			add(rightPanel, BorderLayout.EAST);
		}
	}

	protected abstract JPanel createInnerPanel();
	
	/**
	 * Updates the content of the centre panel.
	 */
	public void updateCenterPanel() {
		this.centerPanel.remove(innerPanel);
		this.innerPanel = createInnerPanel();
		this.centerPanel.add(innerPanel);
		validate();
	}
	
	/**
	 * Updates the contents of the right panel.
	 */
	public void updateRightPanel() {
		if (rightPanel != null) {
			remove(rightPanel);
			rightPanel = createRightPanel();
			add(rightPanel,BorderLayout.EAST);
		}
		validate();
	}

	protected JPanel createRightPanel() {
		return null;
	}

	protected void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}

}

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

	protected MainWindow mainWindow;
	private JPanel toolbarPanel;
	private JPanel searchBar;
	private JPanel innerPanel;
	private JPanel rightPanel;
	private JPanel centerPanel;


	public CommonPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	protected void createScreen() {
		setBackground(Constants.BACKGROUND_COLOR);
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());

		toolbarPanel = new ToolbarPanel(mainWindow);
		searchBar 	 = new SearchBar   (Constants.BACKGROUND_COLOR);
		innerPanel = createInnerPanel();
		rightPanel = createRightPanel();

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
	
	public void updateCenterPanel() {
		this.centerPanel.remove(innerPanel);
		this.innerPanel = createInnerPanel();
		this.centerPanel.add(innerPanel);
		validate();
	}
	
	public void updateRightPanel() {
		if(this.rightPanel != null) {
			remove(rightPanel);
			rightPanel = createRightPanel();
			add(rightPanel,BorderLayout.EAST);
		}
		validate();
	}

	protected JPanel createRightPanel() {
		return null;
	}

	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}

}

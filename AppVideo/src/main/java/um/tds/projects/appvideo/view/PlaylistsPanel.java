package um.tds.projects.appvideo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PlaylistsPanel extends JPanel {

	private MainWindow mainWindow;
	private JPanel toolbarPanel;
	private JPanel searchBar;
	private JLabel dummyLabel;
	

	public PlaylistsPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createScreen();
	}

	private void createScreen() {
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());
		setBackground(Constants.BACKGROUND_COLOR);

		toolbarPanel = new ToolbarPanel(mainWindow);
		dummyLabel   = new JLabel("Ventana con listas de reproducción");
		searchBar 	 = new SearchBar	();

		add(toolbarPanel, BorderLayout.WEST);
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		panel_1.setBackground(Constants.BACKGROUND_COLOR);
		panel_1.add(searchBar);
		panel_1.add(dummyLabel);
		add(panel_1,BorderLayout.CENTER);
	}
	
	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}
}

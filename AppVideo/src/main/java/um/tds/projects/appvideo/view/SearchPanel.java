package um.tds.projects.appvideo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class SearchPanel extends JPanel {

	private MainWindow mainWindow;
	private JPanel toolbarPanel;
	private JPanel searchBar;
	private LabelPanel labelPanel;
	
	
	public SearchPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createScreen();
	}

	private void createScreen() {
		setBackground(Constants.BACKGROUND_COLOR);
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());

		toolbarPanel = new ToolbarPanel	(mainWindow);
		searchBar 	 = new SearchBar	();
		labelPanel 	 = new LabelPanel 	("Dibujos animados","Pelicula","Serie",
										"Intriga","Terror","Clasico","Videoclip",
										"Adultos","Infantil");

		add(toolbarPanel, BorderLayout.WEST);
		add(labelPanel,BorderLayout.EAST);
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Constants.BACKGROUND_COLOR);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(Box.createRigidArea(new Dimension(0, 7)));
		centerPanel.add(searchBar);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 7)));
		JButton but = new JButton("Hola");
		but.addActionListener(e -> {
			labelPanel.addLabel("Nuevo");
		});
		centerPanel.add(but);
		add(centerPanel, BorderLayout.CENTER);
	}

	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}

}

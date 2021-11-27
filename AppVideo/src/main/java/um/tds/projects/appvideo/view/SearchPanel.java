package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Video;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;

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
	private JPanel videoList;
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

		toolbarPanel = new ToolbarPanel(mainWindow);
		searchBar 	 = new SearchBar	(Constants.BACKGROUND_COLOR);
		videoList    = new VideoList   (mainWindow,
										Arrays.asList(
												new Video("", "Smart cat solves Millenium problem", 1324),
												new Video("", "UM > UMU", 325),
												new Video("", "UMU > 0", 3250000),
												new Video("", "Lo bueno, si breve, dos veces bueno", 325),
												new Video("", "Oh, brave new world...", 3205),
												new Video("", "Arma virumque cano...", 3025),
												new Video("", "En un lugar de la mancha...", 325),
												new Video("", "Entrevista a Dijkstra", 32005),
												new Video("", "Smart cat solves Millenium problem", 1324),
												new Video("", "UM > UMU", 325),
												new Video("", "UMU > 0", 3250000),
												new Video("", "Lo bueno, si breve, dos veces bueno", 325),
												new Video("", "Oh, brave new world...", 3205),
												new Video("", "Arma virumque cano...", 3025),
												new Video("", "En un lugar de la mancha...", 325),
												new Video("", "Entrevista a Dijkstra", 32005),
												new Video("", "Smart cat solves Millenium problem", 1324),
												new Video("", "UM > UMU", 325),
												new Video("", "UMU > 0", 3250000),
												new Video("", "Lo bueno, si breve, dos veces bueno", 325),
												new Video("", "Oh, brave new world...", 3205),
												new Video("", "Arma virumque cano...", 3025),
												new Video("", "En un lugar de la mancha...", 325),
												new Video("", "Entrevista a Dijkstra", 32005)));
		toolbarPanel = new ToolbarPanel	(mainWindow);
		labelPanel 	 = new LabelPanel 	("Dibujos animados","Pelicula","Serie",
										"Intriga","Terror","Clasico","Videoclip",
										"Adultos","Infantil");

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Constants.BACKGROUND_COLOR);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		centerPanel.add(searchBar);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		centerPanel.add(videoList);

		add(toolbarPanel, BorderLayout.WEST);
		add(centerPanel,  BorderLayout.CENTER);
		add(labelPanel,   BorderLayout.EAST);
	}

	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}

}

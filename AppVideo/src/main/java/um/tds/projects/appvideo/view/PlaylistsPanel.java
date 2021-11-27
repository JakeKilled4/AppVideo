package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PlaylistsPanel extends JPanel {

	private MainWindow mainWindow;
	private JPanel toolbarPanel;
	private JPanel searchBar;
	private JPanel newListBtn;
	private JPanel rmvListBtn;
	private NewPlaylistEntry newPlaylistEntry;
	private PlaylistsList playlistsList;
	
	
	public PlaylistsPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createScreen();
	}

	private void createScreen() {
		setBackground(Constants.BACKGROUND_COLOR);
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());

		toolbarPanel      = new ToolbarPanel(mainWindow);
		searchBar 	      = new SearchBar	(Constants.BACKGROUND_COLOR);
		newPlaylistEntry  = new NewPlaylistEntry(mainWindow);
		playlistsList     = new PlaylistsList(mainWindow,
											  Arrays.asList(
													  new Playlist("TDS vibes"),
													  new Playlist("What to hear when studying Geometry"),
													  new Playlist("Videos on Algebraic Topology"),
													  new Playlist("TDS vibes"),
													  new Playlist("What to hear when studying Geometry"),
													  new Playlist("Videos on Algebraic Topology"),
													  new Playlist("TDS vibes"),
													  new Playlist("What to hear when studying Geometry"),
													  new Playlist("Videos on Algebraic Topology")
											  ));
		
		// InnerPanel guarda entradas y botones.
		JPanel innerPanel = new JPanel();
		innerPanel.setBackground(Constants.BACKGROUND_COLOR);
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		innerPanel.setAlignmentX(CENTER_ALIGNMENT);
		fixSize(innerPanel, Constants.PAGE_WIDTH, playlistsList.getLength() + 2 *(Constants.VIDEOLIST_ENTRY_HEIGHT + 10));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Constants.BUTTON_COLOR);
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		
		newListBtn = defineButton("Add");
		newListBtn.setAlignmentX(RIGHT_ALIGNMENT);
		rmvListBtn = defineButton("Remove");
		
		buttonsPanel.add(newListBtn);
		buttonsPanel.add(rmvListBtn);
		
		innerPanel.add(buttonsPanel);
		innerPanel.add(playlistsList);
		
		
		// Center panel guarda 1. un espacio en blanco y 2. innerPanel (que guarda las listas, botones, ...)
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Constants.BACKGROUND_COLOR);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		centerPanel.add(searchBar);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		centerPanel.add(innerPanel);

		add(toolbarPanel, BorderLayout.WEST);
		add(centerPanel,  BorderLayout.CENTER);
	}

	private JPanel defineButton(String text) {
		JPanel button = new JPanel();
		button.setBackground(Constants.BUTTON_COLOR);
		button.setLayout(new BoxLayout(button, BoxLayout.X_AXIS));
		fixSize(button, Constants.PAGE_WIDTH / 2, Constants.VIDEOLIST_ENTRY_HEIGHT);
		
		JLabel label = new JLabel(text);
		label.setForeground(Constants.FONT_COLOR);
		button.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH / 4, Constants.VIDEOLIST_ENTRY_HEIGHT)));
		button.add(label);
		
		button.addMouseListener(
				new MouseListener() {

					public void mouseClicked(MouseEvent e) {
						button.setBackground(Constants.BUTTON_COLOR);
					}
					public void mousePressed(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {
						button.setBackground(Constants.BUTTON_HOVER_COLOR);
					}
					public void mouseExited(MouseEvent e) {
						button.setBackground(Constants.BUTTON_COLOR);
					}
				
				}
			);
		
		
		return button;
	}

	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}

}

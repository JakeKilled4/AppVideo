package um.tds.projects.appvideo.view;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class ToolbarPanel extends JPanel {

	private MainWindow mainWindow;
	private boolean open;
	private ToolbarEntryPanel entryPlaylists;
	private ToolbarEntryPanel entryPreferences;
	private ToolbarEntryPanel entrySearch;
	private ToolbarEntryPanel entryRecents;
	private ToolbarEntryPanel entryPopularVideos;
	private List<JSeparator>  separators;

	public ToolbarPanel(MainWindow mainWindow) {
		this(mainWindow, true);
	}

	public ToolbarPanel(MainWindow mainWindow, boolean open) {
		this.mainWindow = mainWindow;
		this.open       = open;

		setBackground(Constants.FOREGROUND_COLOR);
		setLayout    (new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
		adjustSize   ();
	}

	public void setOpen(boolean open) {
		if (this.open == open) return;
	  	this.open = open;
		adjustSize();
	}

	public boolean isOpen() {
		return open;
	}

	private void adjustSize() {
		if (open == true) {
			setBackground(Constants.FOREGROUND_COLOR);
			setPreferredSize(new Dimension(Constants.TOOLBAR_OPEN_SIZE, Short.MAX_VALUE));
			entryPlaylists.    setVisible(true);
			entryPreferences.  setVisible(true);
			entrySearch.       setVisible(true);
			entryRecents.      setVisible(true);
			entryPopularVideos.setVisible(true);
			separators.stream().forEach( s -> s.setVisible(true) );
		} else {
			setBackground(Constants.BACKGROUND_COLOR);
			setPreferredSize(new Dimension(Constants.TOOLBAR_CLOSED_SIZE, Short.MIN_VALUE));
			entryPlaylists.    setVisible(false);
			entryPreferences.  setVisible(false);
			entrySearch.       setVisible(false);
			entryRecents.      setVisible(false);
			entryPopularVideos.setVisible(false);
			separators.stream().forEach( s -> s.setVisible(false) );
		}
	}

	private void addComponents() {
		entryPlaylists     = new ToolbarEntryPanel(null, "Playlists");
		entryPreferences   = new ToolbarEntryPanel(null, "Preferences");
		entrySearch        = new ToolbarEntryPanel(null, "Search");
		entryRecents       = new ToolbarEntryPanel(null, "Recents");
		entryPopularVideos = new ToolbarEntryPanel(null, "Popular Videos");
		separators         = new ArrayList<JSeparator>();
		  
		JLabel icon = new JLabel(new ImageIcon("./src/images/MenuGrey.png"));
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Constants.FOREGROUND_COLOR);
		btnPanel.setLayout  (new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		btnPanel.add(icon);
		add(Box.createRigidArea(new Dimension(0,7)));
		add(btnPanel);
		
		
		btnPanel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (isOpen())
					setOpen(false);
				else
					setOpen(true);
			}
			public void mousePressed (MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered (MouseEvent e) {}
			public void mouseExited  (MouseEvent e) {}
		});
		
		entryPlaylists.    addClickAction(() -> mainWindow.activatePlaylistsPanel());
		entryPreferences.  addClickAction(() -> mainWindow.activatePreferencesPanel());
		entrySearch.       addClickAction(() -> mainWindow.activateSearchPanel(UpdateOption.BOTH));
		entryRecents.      addClickAction(() -> mainWindow.activateRecentsPanel());
		entryPopularVideos.addClickAction(() -> mainWindow.activatePopularVideosPanel());

		add(Box.createRigidArea(new Dimension(0, 30)));
		add(entryPlaylists);
		addSeparator();
		add(entryPreferences);
		addSeparator();
		add(entrySearch);
		addSeparator();
		add(entryRecents);
		addSeparator();
		add(entryPopularVideos);
	}
	
	private void addSeparator() {
		JSeparator sep = new JSeparator();
		sep.setMaximumSize(new Dimension(Constants.TOOLBAR_OPEN_SIZE, Constants.SEPARATOR_HEIGHT));
		sep.setBackground(Constants.SEPARATOR_COLOR);
		sep.setForeground(Constants.SEPARATOR_COLOR);
		separators.add(sep);
		add(sep);
	}

}

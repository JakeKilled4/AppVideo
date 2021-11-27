package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;

import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class PlaylistsList extends JPanel {
	
	private MainWindow mainWindow;
	private List<Playlist> playlists;
	private List<PlaylistListEntry> entries;
	
	public PlaylistsList(MainWindow mainWindow, List<Playlist> playlists) {
		this.mainWindow = mainWindow;
		this.playlists = playlists;
		this.entries   = playlists.stream().map( p -> new PlaylistListEntry(mainWindow, p) ).toList();
		
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
	}

	private void addComponents() {
		for (PlaylistListEntry entry: entries) {
			add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH, 10)));
			add(entry);
		}
	}

	public int getLength() {
		return entries.size() * (Constants.VIDEOLIST_ENTRY_HEIGHT + 10) - 10;
	}
}

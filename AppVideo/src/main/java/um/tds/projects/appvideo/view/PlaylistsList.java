package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;

import java.awt.Dimension;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;


@SuppressWarnings("serial")
public class PlaylistsList extends JPanel {
	
	private List<PlaylistListEntry> entries;
	
	public PlaylistsList(MainWindow mainWindow, List<Playlist> playlists) {
		this.entries   = playlists.stream().map( p -> new PlaylistListEntry(mainWindow, p) ).collect(Collectors.toList());
		
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
	}

	private void addComponents() {
		for (PlaylistListEntry entry: entries) {
			addSeparator();
			add(entry);
		}
	}
	
	private void addSeparator() {
		JSeparator sep = new JSeparator();
		sep.setMaximumSize(new Dimension(Constants.PAGE_WIDTH, Constants.SEPARATOR_HEIGHT));
		sep.setBackground(Constants.BACKGROUND_COLOR);
		sep.setForeground(Constants.BACKGROUND_COLOR);
		add(sep);
	}

	public int getLength() {
		return entries.size() * (Constants.VIDEOLIST_ENTRY_HEIGHT + Constants.SEPARATOR_HEIGHT) - Constants.SEPARATOR_HEIGHT;
	}
}

package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;

import java.awt.Font;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SinglePlaylistPanel extends CommonPanel {

	private Playlist playlist;

	public SinglePlaylistPanel(MainWindow mainWindow, Playlist playlist) {
		super(mainWindow);
		this.playlist = playlist;
		createScreen();
	}
	
	@Override
	protected JPanel createInnerPanel() {
		// Define an entry for each video.
		List<ListEntry> entries = playlist.getVideos()
			.stream ()
			.map    (v -> new VideoListEntry(mainWindow, v))
			.collect(Collectors.toList());
		// Create an info entry and place it on top of the list.
		// This entry indicates whether we are showing all of the videos
		// or just the results of a search.
		entries.add(0, makeInfoEntry());
		
		return new CommonListPanel(mainWindow, entries);
	}

	private ListEntry makeInfoEntry() {
		return new ListEntry(mainWindow) {
			{
				addComponents();
			}
			@Override
			protected JPanel createInnerPanel() {
				JPanel p = new JPanel();
				p.setLayout    (new BoxLayout(p, BoxLayout.Y_AXIS));
				p.setAlignmentX(CENTER_ALIGNMENT);

				ComponentFactory componentFactory = ComponentFactory.getUniqueInstance();
				JLabel           titleLabel       = componentFactory.specialLabel("Playlist '" + playlist.getName() + "'");
				titleLabel.setFont      (new Font("Default", Font.ITALIC, 17));
				titleLabel.setAlignmentX(CENTER_ALIGNMENT);
				
				p.add(titleLabel);
				return p;
			}
		};
	}

}

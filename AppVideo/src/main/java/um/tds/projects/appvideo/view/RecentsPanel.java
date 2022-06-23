package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.controller.Controller;

import java.awt.Font;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RecentsPanel extends CommonPanel {

	private static final int NUM_ENTRIES = 5;
	
	public RecentsPanel(MainWindow mainWindow) {
		super(mainWindow);
		createScreen();
	}
	
	@Override
	protected JPanel createInnerPanel() {
		// Get all recent videos.
		List<ListEntry> entries = Controller
			.getUniqueInstance  ()
			.getMostRecentVideos()
			.stream             ()
			.map                (v -> new VideoListEntry(mainWindow, v))
			.collect            (Collectors.toList());
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
				p.setBackground(Constants.FOREGROUND_COLOR);

				ComponentFactory componentFactory = ComponentFactory.getUniqueInstance();
				JLabel           titleLabel       = componentFactory.specialLabel("Showing the " + NUM_ENTRIES + " most recent entries");
				titleLabel.setFont      (new Font("Default", Font.ITALIC, 17));
				titleLabel.setAlignmentX(CENTER_ALIGNMENT);
				
				p.add(titleLabel);
				return p;
			}
		};
	}
}

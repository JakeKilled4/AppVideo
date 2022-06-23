package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.controller.Controller;

import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PopularVideosPanel extends CommonPanel {

	private static final int NUM_ENTRIES = 10;

	private Controller controller;
	
	public PopularVideosPanel(MainWindow mainWindow) {
		super(mainWindow);
		controller = Controller.getUniqueInstance();
		createScreen();
	}
	
	@Override
	protected JPanel createInnerPanel() {
		List<ListEntry> entries = new LinkedList<ListEntry>();
		
		// This panel is only accessible for paid users.
		if (controller.userIsPremium()) {
			entries = controller
				.getMostPopularVideos(NUM_ENTRIES)
				.stream              ()
				.map                 (v -> new VideoListEntry(mainWindow, v))
				.collect             (Collectors.toList());
		}
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
				JLabel           titleLabel       = null;
				if (controller.userIsPremium()) {
					// If being used by a paid user, display the number of videos.
					titleLabel = componentFactory.specialLabel("Displaying the " + NUM_ENTRIES + " most popular videos");
				} else {
					// Otherwise, note it.
					titleLabel = componentFactory.specialLabel("Just for premium users!");
				}
				titleLabel.setFont      (new Font("Default", Font.ITALIC, 17));
				titleLabel.setAlignmentX(CENTER_ALIGNMENT);
				
				p.add(titleLabel);
				return p;
			}
		};
	}
}

package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.controller.Controller;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SearchPanel extends CommonPanel {

	private Controller controller;

	public SearchPanel(MainWindow mainWindow) {
		super(mainWindow);
		this.controller = Controller.getUniqueInstance();
		createScreen();
	}
	
	@Override
	protected JPanel createInnerPanel() {
		// Define an entry for each video.
		List<ListEntry> entries = controller.getSearchedVideos()
			.stream ()
			.map    (v -> new VideoListEntry(mainWindow, v))
			.collect(Collectors.toList());
		// Create an info entry and place it on top of the list.
		// This entry indicates whether we are showing all of the videos
		// or just the results of a search.
		entries.add(0, makeInfoEntry());
		
		return new CommonListPanel(mainWindow, entries);
	}
	
	@Override
	protected JPanel createRightPanel() {
		return new LabelPanel(controller.getAllLabels());
	}

	private ListEntry makeInfoEntry() {
		return new ListEntry(mainWindow) {
			{
				addComponents();
			}
			@Override
			protected JPanel createInnerPanel() {
				ComponentFactory componentFactory = ComponentFactory.getUniqueInstance();
				JPanel p = new JPanel();
				p.setLayout    (new BoxLayout(p, BoxLayout.Y_AXIS));
				p.setAlignmentX(CENTER_ALIGNMENT);
				p.setBackground(Constants.FOREGROUND_COLOR);
				
				boolean noLabels    = controller.getSelectedLabels().isEmpty();
				String  searchTitle = controller.getSearchTitle   ();
				String  titleText   = "Title: \""+searchTitle+"\"";
				if (searchTitle.isBlank() && noLabels) {
					p.add(createAllVideosLabel(componentFactory));
				} else {
					p.add(createSearchResultLabel(componentFactory));
					p.add(Box.createRigidArea(new Dimension(0,10)));
					if (!searchTitle.isBlank()) p.add(createTitleLabel (componentFactory, titleText));
					if (!noLabels)              p.add(createLabelsLabel(componentFactory));
				}
				return p;
			}

			private JLabel createLabelsLabel(ComponentFactory componentFactory) {
				JLabel labelsLabel = componentFactory.specialLabel("Labels: selected labels");
				labelsLabel.setAlignmentX(CENTER_ALIGNMENT);
				return labelsLabel;
			}

			private JLabel createTitleLabel(ComponentFactory componentFactory, String titleText) {
				JLabel titleLabel = componentFactory.specialLabel(titleText);
				titleLabel.setAlignmentX(CENTER_ALIGNMENT);
				return titleLabel;
			}

			private JLabel createSearchResultLabel(ComponentFactory componentFactory) {
				JLabel searchResultLabel = componentFactory.specialLabel("Search result");
				searchResultLabel.setFont(new Font("Default",Font.ITALIC,17));
				searchResultLabel.setAlignmentX(CENTER_ALIGNMENT);
				return searchResultLabel;
			}
			
			private JLabel createAllVideosLabel(ComponentFactory componentFactory) {
				JLabel allVideosLabel = componentFactory.specialLabel("All videos");
				allVideosLabel.setFont      (new Font("Default", Font.ITALIC, 17));
				allVideosLabel.setAlignmentX(CENTER_ALIGNMENT);
				return allVideosLabel;
			}
		};
	}

}

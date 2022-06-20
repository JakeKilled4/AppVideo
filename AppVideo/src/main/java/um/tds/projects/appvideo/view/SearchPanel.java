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

		ListEntry info = new ListEntry(mainWindow) {
			{
				addComponents();
			}
			@Override
			protected JPanel createInnerPanel() {
				ComponentFactory componentFactory = ComponentFactory.getUniqueInstance();
				JPanel p = new JPanel();
				p.setBackground(getBackground());
				p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
				p.setAlignmentX(CENTER_ALIGNMENT);
				
				boolean noLabels = controller.getSelectedLabels().isEmpty();
				String searchTitle = controller.getSearchTitle();
				String titleText = "Title: \""+searchTitle+"\"";
				if(searchTitle.isBlank() && noLabels) {
					JLabel allVideosLabel = componentFactory.specialLabel("All videos");
					allVideosLabel.setFont(new Font("Default",Font.ITALIC,17));
					allVideosLabel.setAlignmentX(CENTER_ALIGNMENT);
					p.add(allVideosLabel);
				}
				else {
					JLabel searchResultLabel = componentFactory.specialLabel("Search result");
					searchResultLabel.setFont(new Font("Default",Font.ITALIC,17));
					searchResultLabel.setAlignmentX(CENTER_ALIGNMENT);
					p.add(searchResultLabel);
					p.add(Box.createRigidArea(new Dimension(0,10)));
					if(!searchTitle.isBlank()) {
						JLabel titleLabel = componentFactory.specialLabel(titleText);
						titleLabel.setAlignmentX(CENTER_ALIGNMENT);
						p.add(titleLabel);
					}
					if(!noLabels) {
						JLabel labelsLabel = componentFactory.specialLabel("Labels: selected labels");
						labelsLabel.setAlignmentX(CENTER_ALIGNMENT);
						p.add(labelsLabel);
					}
				}
				
				return p;
			}
		};
		
		List<ListEntry> entries = controller.getSearchedVideos()
				.stream()
				.map(v -> new VideoListEntry(mainWindow, v))
				.collect(Collectors.toList());
		
		entries.add(0,info);
		return new CommonListPanel(mainWindow, entries);
	}

	@Override
	protected JPanel createRightPanel() {
		
		return new LabelPanel(controller.getAllLabels());
	}

}

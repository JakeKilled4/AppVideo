package um.tds.projects.appvideo.view;


import um.tds.projects.appvideo.backend.Video;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class VideoList extends JPanel {
	
	private MainWindow mainWindow;
	private List<Video> videos;
	private List<VideoListEntry> entries;
	
	public VideoList(MainWindow mainWindow, List<Video> videos) {
		this.mainWindow = mainWindow;
		this.videos     = videos;
		this.entries    = videos.stream().map( v -> new VideoListEntry(mainWindow, v) ).collect(Collectors.toList());
		
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout    (new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
	}
	
	private int getLength() {
		return entries.size() * (Constants.VIDEOLIST_ENTRY_HEIGHT + 10) - 10;
	}
	
	private void addComponents() {

		JPanel innerPage = new JPanel();
		innerPage.setBackground(Constants.BACKGROUND_COLOR);
		innerPage.setLayout(new BoxLayout(innerPage, BoxLayout.Y_AXIS));
		innerPage.setMaximumSize(new Dimension(Constants.PAGE_WIDTH, getLength()));
		innerPage.setMinimumSize(new Dimension(Constants.PAGE_WIDTH, getLength()));
		innerPage.setPreferredSize(new Dimension(Constants.PAGE_WIDTH, getLength()));
		innerPage.setAlignmentX(CENTER_ALIGNMENT);
		
		for (VideoListEntry entry: entries) {
			innerPage.add(entry);
			innerPage.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH, 10)));
		}
		
		JScrollPane scrollPane = new JScrollPane(innerPage);
		scrollPane.setBackground(Constants.BACKGROUND_COLOR);
		
		add(scrollPane);
	}
	
}

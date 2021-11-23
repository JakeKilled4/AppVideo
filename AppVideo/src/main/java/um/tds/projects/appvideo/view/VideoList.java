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


@SuppressWarnings("serial")
public class VideoList extends JPanel {
	
	private MainWindow mainWindow;
	private List<Video> videos;
	private List<VideoListEntry> entries;
	
	public VideoList(MainWindow mainWindow, List<Video> videos) {
		this.mainWindow = mainWindow;
		this.videos     = videos;
		this.entries    = videos.stream().map( v -> new VideoListEntry(mainWindow, v) ).collect(Collectors.toList());
		
		for (VideoListEntry entry: entries) {
			entry.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {
					entry.updateBackground(Constants.BUTTON_HOVER_COLOR);
				}

				public void mouseExited(MouseEvent e) {
					entry.updateBackground(Constants.BUTTON_COLOR);
				}
			});
		}
		
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout    (new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
	}
	
	private void addComponents() {

		JPanel innerPage = new JPanel();
		innerPage.setBackground(Constants.BACKGROUND_COLOR);
		innerPage.setLayout(new BoxLayout(innerPage, BoxLayout.Y_AXIS));
		innerPage.setMaximumSize(new Dimension(Constants.PAGE_WIDTH, Short.MAX_VALUE));
		innerPage.setMinimumSize(new Dimension(Constants.PAGE_WIDTH, Short.MAX_VALUE));
		innerPage.setPreferredSize(new Dimension(Constants.PAGE_WIDTH, Short.MAX_VALUE));
		innerPage.setAlignmentX(CENTER_ALIGNMENT);
		
		for (VideoListEntry entry: entries) {
			innerPage.add(entry);
			innerPage.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH, 10)));
		}
		
		add(innerPage);
	}
	
}

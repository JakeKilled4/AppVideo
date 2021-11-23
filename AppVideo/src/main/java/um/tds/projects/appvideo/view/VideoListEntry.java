package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Video;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class VideoListEntry extends JPanel {
	
	private MainWindow mainWindow;
	private Video video;
	private JPanel thumbnail;
	private JLabel title;
	private JLabel numViews;
	private JPanel vPanel;
	private JPanel hPanel;
	private JPanel labelPanel;
	
	public VideoListEntry(MainWindow mainWindow, Video video) {
		this.mainWindow = mainWindow;
		this.video = video;
		
		setBackground(Constants.BUTTON_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		setMinimumSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		setPreferredSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		
		title = new JLabel(video.getTitle());
		title.setForeground(Constants.FONT_COLOR);
		numViews = new JLabel(Integer.toString(video.getNumViews()) + " views");
		numViews.setForeground(Constants.FONT_COLOR);
		addComponents();

	}
	
	public void updateBackground(Color bg) {
		setBackground(bg);

		vPanel.setBackground(bg);
		hPanel.setBackground(bg);
		labelPanel.setBackground(bg);
	}
	
	private void addComponents() {
		vPanel = new JPanel();
		vPanel.setBackground(getBackground());
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));
		
		hPanel = new JPanel();
		hPanel.setBackground(getBackground());
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));
		
		labelPanel = new JPanel();
		labelPanel.setBackground(getBackground());
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.setMinimumSize(new Dimension(Constants.PAGE_WIDTH - 10 - 105, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		labelPanel.setMaximumSize(new Dimension(Constants.PAGE_WIDTH - 10 - 105, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		labelPanel.setPreferredSize(new Dimension(Constants.PAGE_WIDTH - 10 - 105, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		
		title.setAlignmentX(LEFT_ALIGNMENT);
		numViews.setAlignmentX(LEFT_ALIGNMENT);
		labelPanel.add(title);
		labelPanel.add(numViews);
		
		hPanel.add(Box.createRigidArea(new Dimension(100, Constants.VIDEOLIST_ENTRY_HEIGHT - 10))); // Eventually, this should hold the video thumbnail.
		hPanel.add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT - 10)));
		hPanel.add(labelPanel);

		vPanel.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH - 10, 5)));
		vPanel.add(hPanel);
		vPanel.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH - 10, 5)));
		
		add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT)));
		add(vPanel);
		add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT)));
	}
	
}

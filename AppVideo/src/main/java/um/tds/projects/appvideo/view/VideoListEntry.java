package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.controller.Controller;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import tds.video.VideoWeb;

import javax.swing.JLabel;


@SuppressWarnings("serial")
public class VideoListEntry extends ListEntry {

	private Video    video;
	private JLabel   title;
	private JLabel   numViews;
	private JPanel   hPanel;
	private JPanel   labelPanel;
	private JLabel   icon;
	private VideoWeb videoWeb;
	private boolean  removeMode;
	private Playlist parent;

	public VideoListEntry(MainWindow mainWindow, Video video) {
		super(mainWindow);
		this.video      = video;
		this.videoWeb   = mainWindow.getVideoWeb();
		this.removeMode = false;
		this.parent     = null;
		addMouseListener(new HoverMouseListener());
		addComponents();
	}
	
	public VideoListEntry(MainWindow mainWindow, Video video, Playlist parent) {
		this(mainWindow, video);
		this.parent = parent;
	}
	
	public void setRemoveMode(boolean removeMode) {
		this.removeMode = removeMode;
	}
	
	private final class HoverMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			if (removeMode && parent != null) {
				Controller.getUniqueInstance().removeVideoFromPlaylist(parent, video);
				mainWindow.activateSinglePlaylistPanel(parent);
			} else {
				updateBackground(Constants.BUTTON_COLOR);
				mainWindow.activateVideoViewingPanel(video);
			}
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {
			if (removeMode) {
				updateBackground(Constants.BUTTON_RED_HOVER_COLOR);
			} else{
				updateBackground(Constants.BUTTON_HOVER_COLOR);
			}
		}

		public void mouseExited(MouseEvent e) {
			updateBackground(Constants.BUTTON_COLOR);
		}
	}

	@Override
	protected JPanel createInnerPanel() {
		
		icon = new JLabel();
		icon.setIcon(videoWeb.getThumb(video.getUrl()));
		title = new JLabel(video.getTitle());
		title.setForeground(Constants.FONT_COLOR);
		numViews = new JLabel(Integer.toString(video.getNumViews()) + " views");
		numViews.setForeground(Constants.FONT_COLOR);
		numViews.setFont(Constants.ITALIC_FONT);

		hPanel = new JPanel();
		hPanel.setBackground(getBackground());
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));

		labelPanel = new JPanel();
		labelPanel.setBackground(getBackground());
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		

		title.setAlignmentX(LEFT_ALIGNMENT);
		numViews.setAlignmentX(LEFT_ALIGNMENT);
		labelPanel.add(title);
		labelPanel.add(numViews);
		
		hPanel.setAlignmentX(RIGHT_ALIGNMENT);
		hPanel.setMinimumSize(new Dimension(Constants.PAGE_WIDTH - 10, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		hPanel.setMaximumSize(new Dimension(Constants.PAGE_WIDTH - 10, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		hPanel.setPreferredSize(new Dimension(Constants.PAGE_WIDTH - 10, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		hPanel.add(icon);
		hPanel.add(Box.createRigidArea(new Dimension(15, Constants.VIDEOLIST_ENTRY_HEIGHT - 10)));
		hPanel.add(labelPanel);
		
		return hPanel;
	}

}

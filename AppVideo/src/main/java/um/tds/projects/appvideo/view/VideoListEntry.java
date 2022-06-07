package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Video;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class VideoListEntry extends ListEntry {

	private final class HoverMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			updateBackground(Constants.BUTTON_COLOR);
			mainWindow.activateVideoViewingPanel(video);
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {
			updateBackground(Constants.BUTTON_HOVER_COLOR);
		}

		public void mouseExited(MouseEvent e) {
			updateBackground(Constants.BUTTON_COLOR);
		}
	}

	private Video video;
	private JLabel title;
	private JLabel numViews;
	private JPanel hPanel;
	private JPanel labelPanel;

	public VideoListEntry(MainWindow mainWindow, Video video) {
		super(mainWindow);
		this.video      = video;
		addMouseListener(new HoverMouseListener());
		addComponents();
	}

	@Override
	protected JPanel createInnerPanel() {
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
		labelPanel.setMinimumSize(new Dimension(Constants.PAGE_WIDTH - 10 - 105, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		labelPanel.setMaximumSize(new Dimension(Constants.PAGE_WIDTH - 10 - 105, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		labelPanel.setPreferredSize(new Dimension(Constants.PAGE_WIDTH - 10 - 105, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));

		title.setAlignmentX(LEFT_ALIGNMENT);
		numViews.setAlignmentX(LEFT_ALIGNMENT);
		labelPanel.add(title);
		labelPanel.add(numViews);

		hPanel.add(Box.createRigidArea(new Dimension(100, Constants.VIDEOLIST_ENTRY_HEIGHT - 10))); // Eventually, this should hold the video img.
		hPanel.add(Box.createRigidArea(new Dimension(5, Constants.VIDEOLIST_ENTRY_HEIGHT - 10)));
		hPanel.add(labelPanel);

		return hPanel;
	}

}

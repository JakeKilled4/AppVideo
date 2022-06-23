package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.controller.Controller;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PlaylistListEntry extends ListEntry {
	
	private Playlist playlist;
	private boolean  removeMode;

	public PlaylistListEntry(MainWindow mainWindow, Playlist playlist) {
		super(mainWindow);
		this.playlist   = playlist;
		this.removeMode = false;
		addMouseListener(new HoverMouseListener());
		addComponents();
	}
	
	// Controls the behaviour when the cursor enters or leaves the button.
	private final class HoverMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			if (removeMode) {
				// If on remove mode, delete the play list when clicked.
				Controller.getUniqueInstance().removePlaylist(playlist);
				mainWindow.activatePlaylistsPanel();
			} else {
				// Otherwise, activate the play list view panel.
				updateBackground(Constants.BUTTON_COLOR);
				mainWindow.activateSinglePlaylistPanel(playlist);
			}
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {
			if (removeMode) {
				// If on remove mode, set a red accent colour.
				updateBackground(Constants.BUTTON_RED_HOVER_COLOR);
			} else {
				// Otherwise, go for the default colour.
				updateBackground(Constants.BUTTON_HOVER_COLOR);
			}
		}

		public void mouseExited(MouseEvent e) {
			updateBackground(Constants.BUTTON_COLOR);
		}
	}

	@Override
	protected JPanel createInnerPanel() {
		// Panel content.
		JLabel title = new JLabel(playlist.getName());
		title.setForeground(Constants.FONT_COLOR);
		JLabel numViews = new JLabel(Integer.toString(playlist.getNumVideos()) + " videos");
		numViews.setForeground(Constants.FONT_COLOR);
		numViews.setFont(Constants.ITALIC_FONT);

		// Main content pane.
		JPanel hPanel = new JPanel();
		hPanel.setBackground(getBackground());
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));

		JPanel labelPanel = new JPanel();
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
		hPanel.add(Box.createRigidArea(new Dimension(15, Constants.VIDEOLIST_ENTRY_HEIGHT - 10)));
		hPanel.add(labelPanel);
		
		return hPanel;
	}
	
	public void setRemoveMode(boolean removeMode) {
		this.removeMode = removeMode;
	}

}

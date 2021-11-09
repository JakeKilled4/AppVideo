package um.tds.projects.appvideo.view;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class ToolbarPanel extends JPanel {

	private MainWindow mainWindow;
	private boolean open;

	public ToolbarPanel(MainWindow mainWindow) {
		this(mainWindow, true);
	}

	public ToolbarPanel(MainWindow mainWindow, boolean open) {
		this.mainWindow = mainWindow;
		this.open      = open;

		setBackground(Constants.FOREGROUND_COLOR);
		setLayout    (new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
		adjustSize   ();
	}

	public void setOpen(boolean open) {
		this.open = open;

		adjustSize();
	}

	public boolean isOpen() {
		return open;
	}

	private void adjustSize() {
		if (open)
			setPreferredSize(new Dimension(Constants.TOOLBAR_OPEN_SIZE, Short.MAX_VALUE));
		else
			setPreferredSize(new Dimension(Constants.TOOLBAR_CLOSED_SIZE, Short.MIN_VALUE));
	}

	private void addComponents() {
		JButton           btnToggle        = new JButton          ("Toggle view");
		ToolbarEntryPanel entryPlaylists   = new ToolbarEntryPanel(null, "Playlists");
		ToolbarEntryPanel entryPreferences = new ToolbarEntryPanel(null, "Preferences");
		ToolbarEntryPanel entrySearch      = new ToolbarEntryPanel(null, "Search");

		entryPlaylists.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					mainWindow.activatePlaylistsPanel();
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {
					entryPlaylists.setBackground(Constants.BUTTON_HOVER_COLOR);
				}

				public void mouseExited(MouseEvent e) {
					entryPlaylists.setBackground(Constants.BUTTON_COLOR);
				}
		});
		entryPreferences.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					mainWindow.activatePreferencesPanel();
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {
					entryPreferences.setBackground(Constants.BUTTON_HOVER_COLOR);
				}

				public void mouseExited(MouseEvent e) {
					entryPreferences.setBackground(Constants.BUTTON_COLOR);
				}
		});
		entrySearch.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					mainWindow.activateSearchPanel();
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {
					entrySearch.setBackground(Constants.BUTTON_HOVER_COLOR);
				}
				public void mouseExited(MouseEvent e) {
					entrySearch.setBackground(Constants.BUTTON_COLOR);
				}
		});

		add(btnToggle);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(entryPlaylists);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(entryPreferences);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(entrySearch);
	}

}

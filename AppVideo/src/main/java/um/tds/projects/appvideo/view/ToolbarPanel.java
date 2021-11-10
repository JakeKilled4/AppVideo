package um.tds.projects.appvideo.view;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ToolbarPanel extends JPanel {

	private MainWindow mainWindow;
	private boolean open;
	private JButton btnToggle;
	private ToolbarEntryPanel entryPlaylists;
	private ToolbarEntryPanel entryPreferences;
	private ToolbarEntryPanel entrySearch;

	public ToolbarPanel(MainWindow mainWindow) {
		this(mainWindow, true);
	}

	public ToolbarPanel(MainWindow mainWindow, boolean open) {
		this.mainWindow = mainWindow;
		this.open       = open;

		setBackground(Constants.FOREGROUND_COLOR);
		setLayout    (new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
		adjustSize   ();
	}

	public void setOpen(boolean open) {
		if (this.open == open)
			return;

	  	this.open = open;

		adjustSize();
	}

	public boolean isOpen() {
		return open;
	}

	private void adjustSize() {
		if (open == true) {
			setBackground(Constants.FOREGROUND_COLOR);
			setPreferredSize(new Dimension(Constants.TOOLBAR_OPEN_SIZE, Short.MAX_VALUE));
			entryPlaylists.setVisible(true);
			entryPreferences.setVisible(true);
			entrySearch.setVisible(true);
		} else {
			setBackground(Constants.BACKGROUND_COLOR);
			setPreferredSize(new Dimension(Constants.TOOLBAR_CLOSED_SIZE, Short.MIN_VALUE));
			entryPlaylists.setVisible(false);
			entryPreferences.setVisible(false);
			entrySearch.setVisible(false);
		}
	}

	private void addComponents() {
		btnToggle        = new JButton          ("Toggle view");
		entryPlaylists   = new ToolbarEntryPanel(null, "Playlists");
		entryPreferences = new ToolbarEntryPanel(null, "Preferences");
		entrySearch      = new ToolbarEntryPanel(null, "Search");
		
		btnToggle.addActionListener(e -> {
				if (isOpen())
					setOpen(false);
				else
					setOpen(true);
			});
		entryPlaylists.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					entryPlaylists.setBackground(Constants.BUTTON_COLOR);
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
					entryPreferences.setBackground(Constants.BUTTON_COLOR);
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
					entrySearch.setBackground(Constants.BUTTON_COLOR);
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

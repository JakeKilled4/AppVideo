package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.controller.Controller;


import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PlaylistsPanel extends CommonPanel {

	private static final String BUTTONS_LABEL         = "buttons";
	private static final String ADD_PLAYLIST_LABEL    = "add";
	private static final String REMOVE_PLAYLIST_LABEL = "rm";
	
	private Controller              controller;
	private List<PlaylistListEntry> playlistEntries;
	private ComponentFactory componentFactory;

	public PlaylistsPanel(MainWindow mainWindow) {
		super(mainWindow);
		this.controller = Controller.getUniqueInstance();
		this.componentFactory = ComponentFactory.getUniqueInstance();
		createScreen();
	}
	
	@Override
	protected JPanel createInnerPanel() {
		// Retrieve all the playlist entries and store them appart.
		playlistEntries = controller.getPlaylists()
			.stream()
			.map   (p -> new PlaylistListEntry(mainWindow, p))
			.collect(Collectors.toList());
		// Join normal entries with the top entry.
		List<ListEntry> entries = new LinkedList<ListEntry>();
		entries.add(makeControlsPanel());
		for (ListEntry le: playlistEntries) {
			entries.add(le);
		}
		
		return new CommonListPanel(mainWindow, entries);
	}
	
	private ListEntry makeControlsPanel() {
		return new ListEntry(mainWindow) {
			{
				addComponents();
			}
			@Override
			protected JPanel createInnerPanel() {
				// The panel consists of three panels stacked on top of each
				// other by means of a card layout.
				JPanel     controlPanel = new JPanel();
				CardLayout layout       = new CardLayout();
				
				// Configure the layout.
				controlPanel.setLayout(layout);
				controlPanel.setAlignmentX(CENTER_ALIGNMENT);
				fixSize(controlPanel,400,60);

				// Define and add the three panels.
				controlPanel.add(
					makeButtonsPanel(controlPanel, layout),
					BUTTONS_LABEL
				);
				controlPanel.add(
					makeAddPlaylistPanel(controlPanel, layout),
					ADD_PLAYLIST_LABEL
				);
				controlPanel.add(
					makeRemovePlaylistPanel(controlPanel, layout),
					REMOVE_PLAYLIST_LABEL
				);

				return controlPanel;
			}
			
		};
	}
	
	private JComponent makeButtonsPanel(JPanel controlPanel, CardLayout layout) {
		// The panel consists of an 'add' and of a 'remove' button.
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Constants.BUTTON_COLOR);
		buttonsPanel.setLayout(new GridLayout(0,2));
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		JPanel newListBtn = defineButton("Add");
		JPanel rmvListBtn = defineButton("Remove");
		
		// Define the action for each button.
		addClicker(
			newListBtn,
			() -> {
				layout.show(controlPanel, ADD_PLAYLIST_LABEL);
			}
		);
		addClicker(
			rmvListBtn,
			() -> {
				layout.show(controlPanel, REMOVE_PLAYLIST_LABEL);
				// The user wishes to delete a playlist, so we set the remove mode
				// on every playlist.
				for (PlaylistListEntry ple: playlistEntries) {
					ple.setRemoveMode(true);
				}
			}
		);

		buttonsPanel.add(newListBtn);
		buttonsPanel.add(rmvListBtn);
		
		return buttonsPanel;
	}
	
	private JComponent makeAddPlaylistPanel(JPanel controlPanel, CardLayout layout) {
		// The panel contains a text field for introducing the playlist name.
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		fixSize(p, 400, 30);
		p.setBackground(Constants.FOREGROUND_COLOR);
		JTextField res = componentFactory.specialTextField("Playlist name...",true);
		
		// When clicked, try to create the playlist.
		res.addActionListener(
			(ActionEvent e) -> {
				layout.show(controlPanel, BUTTONS_LABEL);

				boolean created = controller.createPlaylist(res.getText());
				if (created) {
					// If successful reload the screen, since there will be a new playlist
					// to show.
					mainWindow.activatePlaylistsPanel();
				} else {
					// Else, communicate the user the playlist could not be created.
					mainWindow.showPopUp(
						"Playlist not created",
						"There is already a playlist of that name",
						JOptionPane.ERROR_MESSAGE
					);
				}

				res.setText("Playlist name...");
			}
		);
		fixSize(res, 400, 30);
		p.add(res);
		return p;
	}
	
	private JComponent makeRemovePlaylistPanel(JPanel controlPanel, CardLayout layout) {
		JPanel cancelBtn = defineButton("Cancel");
		
		addClicker(
			cancelBtn,
			() -> {
				layout.show(controlPanel, BUTTONS_LABEL);
				// Restore the remove mode for every playlist.
				for (PlaylistListEntry ple: playlistEntries) {
					ple.setRemoveMode(false);
				}
			}
		);

		return cancelBtn;
	}
	

	private JPanel defineButton(String text) {
		JPanel button = new JPanel();
		button.setBackground(Constants.BUTTON_COLOR);
		button.setLayout(new GridLayout());
		
		JLabel label = new JLabel(text,SwingConstants.CENTER);
		label.setForeground(Constants.FONT_COLOR);
		button.add(label);

		button.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				button.setBackground(Constants.BUTTON_COLOR);
			}

			public void mouseEntered(MouseEvent e) {
				button.setBackground(Constants.BUTTON_HOVER_COLOR);
				label.setFont(Constants.BOLD_FONT);
			}

			public void mouseExited(MouseEvent e) {
				button.setBackground(Constants.BUTTON_COLOR);
				label.setFont(Constants.DEFAULT_FONT);
			}

			public void mousePressed (MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});

		return button;
	}
	
	private void addClicker(JPanel button, Runnable func) {
		button.addMouseListener(
			new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					func.run();
				}

				@Override public void mousePressed (MouseEvent e) { }
				@Override public void mouseReleased(MouseEvent e) { }
				@Override public void mouseEntered (MouseEvent e) { }
				@Override public void mouseExited  (MouseEvent e) { }
			}
		);
	}
}

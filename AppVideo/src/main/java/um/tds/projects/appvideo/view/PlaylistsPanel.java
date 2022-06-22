package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.controller.Controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PlaylistsPanel extends CommonPanel {

	private static final String BUTTONS_LABEL         = "buttons";
	private static final String ADD_PLAYLIST_LABEL    = "add";
	private static final String REMOVE_PLAYLIST_LABEL = "rm";
	
	private Controller              controller;
	private List<PlaylistListEntry> playlistEntries;

	public PlaylistsPanel(MainWindow mainWindow) {
		super(mainWindow);
		this.controller = Controller.getUniqueInstance();
		createScreen();
	}
	
	@Override
	protected JPanel createInnerPanel() {
		playlistEntries = controller.getPlaylists()
			.stream()
			.map   (p -> new PlaylistListEntry(mainWindow, p))
			.collect(Collectors.toList());
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
				JPanel     controlPanel = new JPanel();
				CardLayout layout       = new CardLayout();
				
				controlPanel.setLayout(layout);

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
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Constants.BUTTON_COLOR);
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

		JPanel newListBtn = defineButton("Add");
		newListBtn.setAlignmentX(RIGHT_ALIGNMENT);
		JPanel rmvListBtn = defineButton("Remove");
		
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
		JTextField res = new JTextField("Playlist name");
		
		res.addActionListener(
			(ActionEvent e) -> {
				layout.show(controlPanel, BUTTONS_LABEL);

				boolean created = controller.createPlaylist(res.getText());
				if (created) {
					mainWindow.activatePlaylistsPanel();
				} else {
					mainWindow.showPopUp(
						"Playlist not created",
						"There is already a playlist of that name",
						JOptionPane.ERROR_MESSAGE
					);
				}

				res.setText("Playlist name");
			}
		);

		return res;
	}
	
	private JComponent makeRemovePlaylistPanel(JPanel controlPanel, CardLayout layout) {
		JPanel cancelBtn = defineButton("Cancel");
		
		addClicker(
			cancelBtn,
			() -> {
				layout.show(controlPanel, BUTTONS_LABEL);
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
		button.setLayout    (new BoxLayout(button, BoxLayout.X_AXIS));
		fixSize(button, Constants.PAGE_WIDTH / 2, Constants.VIDEOLIST_ENTRY_HEIGHT);

		JLabel label = new JLabel(text);
		label.setForeground(Constants.FONT_COLOR);
		button.add(
			Box.createRigidArea(
				new Dimension(
					Constants.PAGE_WIDTH / 4,
					Constants.VIDEOLIST_ENTRY_HEIGHT
				)
			)
		);
		button.add(label);

		button.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				button.setBackground(Constants.BUTTON_COLOR);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				button.setBackground(Constants.BUTTON_HOVER_COLOR);
				label.setFont(Constants.BOLD_FONT);
			}

			public void mouseExited(MouseEvent e) {
				button.setBackground(Constants.BUTTON_COLOR);
				label.setFont(Constants.DEFAULT_FONT);
			}

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

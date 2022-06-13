package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.controller.Controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class PlaylistsPanel extends CommonPanel {

	private static String BUTTONS_LABEL         = "buttons";
	private static String ADD_PLAYLIST_LABEL    = "add";
	private static String REMOVE_PLAYLIST_LABEL = "rm";
	
	private Controller controller;
	private List<Playlist> playlists;

	public PlaylistsPanel(MainWindow mainWindow) {
		super(mainWindow);
		this.controller = Controller.getUniqueInstance();
		this.playlists  = new LinkedList<Playlist>();
		createScreen();
	}
	
	public void build() {
		playlists = controller.getPlaylists();
		createScreen();
	}

	protected JPanel createInnerPanel() {
		PlaylistsList playlistsList = new PlaylistsList(mainWindow, playlists);

		// InnerPanel guarda entradas y botones.
		JPanel innerPanel = new JPanel();
		innerPanel.setBackground(Constants.BACKGROUND_COLOR);
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		innerPanel.setAlignmentX(CENTER_ALIGNMENT);
		fixSize(innerPanel, Constants.PAGE_WIDTH,
				playlistsList.getLength() + 2 * (Constants.VIDEOLIST_ENTRY_HEIGHT + 10));

		innerPanel.add(makeControlPanel());
		innerPanel.add(playlistsList);

		return innerPanel;
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
					build();
				} else {
					// Popup: error
				}

				res.setText("Playlist name");
			}
		);

		return res;
	}
	
	private JComponent makeRemovePlaylistPanel(JPanel controlPanel, CardLayout layout) {
		return new JLabel("remove");
	}
	
	private JPanel makeControlPanel() {
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

	private JPanel defineButton(String text) {
		JPanel button = new JPanel();
		button.setBackground(Constants.BUTTON_COLOR);
		button.setLayout(new BoxLayout(button, BoxLayout.X_AXIS));
		fixSize(button, Constants.PAGE_WIDTH / 2, Constants.VIDEOLIST_ENTRY_HEIGHT);

		JLabel label = new JLabel(text);
		label.setForeground(Constants.FONT_COLOR);
		button.add(Box.createRigidArea(new Dimension(Constants.PAGE_WIDTH / 4, Constants.VIDEOLIST_ENTRY_HEIGHT)));
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

				@Override public void mousePressed(MouseEvent e)  { }
				@Override public void mouseReleased(MouseEvent e) { }
				@Override public void mouseEntered(MouseEvent e)  { }
				@Override public void mouseExited(MouseEvent e)   { }
			}
		);
	}

	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize(new Dimension(x, y));
		component.setMaximumSize(new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}

}

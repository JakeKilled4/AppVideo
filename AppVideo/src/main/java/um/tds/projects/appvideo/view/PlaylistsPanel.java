package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.controller.Controller;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PlaylistsPanel extends CommonPanel {

	private Controller controller;

	public PlaylistsPanel(MainWindow mainWindow) {
		super(mainWindow);
		this.controller = Controller.getUniqueInstance();
		createScreen();
	}

	protected JPanel createInnerPanel() {
		PlaylistsList playlistsList = new PlaylistsList(mainWindow, controller.getPlaylists());

		// InnerPanel guarda entradas y botones.
		JPanel innerPanel = new JPanel();
		innerPanel.setBackground(Constants.BACKGROUND_COLOR);
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		innerPanel.setAlignmentX(CENTER_ALIGNMENT);
		fixSize(innerPanel, Constants.PAGE_WIDTH,
				playlistsList.getLength() + 2 * (Constants.VIDEOLIST_ENTRY_HEIGHT + 10));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Constants.BUTTON_COLOR);
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

		JPanel newListBtn = defineButton("Add");
		newListBtn.setAlignmentX(RIGHT_ALIGNMENT);
		JPanel rmvListBtn = defineButton("Remove");

		buttonsPanel.add(newListBtn);
		buttonsPanel.add(rmvListBtn);

		innerPanel.add(buttonsPanel);
		innerPanel.add(playlistsList);

		return innerPanel;
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

	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize(new Dimension(x, y));
		component.setMaximumSize(new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}

}

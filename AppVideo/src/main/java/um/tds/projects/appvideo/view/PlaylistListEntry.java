package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class PlaylistListEntry extends JPanel {
	
	private final class HoverMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {
			updateBackground(Constants.BUTTON_HOVER_COLOR);
		}

		public void mouseExited(MouseEvent e) {
			updateBackground(Constants.BUTTON_COLOR);
		}
	}

	private MainWindow mainWindow;
	private Playlist playlist;
	private JLabel name;
	private JLabel numVideos;
	private JPanel vPanel;
	private JPanel hPanel;
	private JPanel labelPanel;
	
	public PlaylistListEntry(MainWindow mainWindow, Playlist playlist) {
		this.mainWindow = mainWindow;
		this.playlist   = playlist;
		
		
		setBackground(Constants.BUTTON_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		setMinimumSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		setPreferredSize(new Dimension(Constants.PAGE_WIDTH, Constants.VIDEOLIST_ENTRY_HEIGHT));
		addMouseListener(new HoverMouseListener());

		addComponents();

	}
	
	public void updateBackground(Color bg) {
		setBackground(bg);

		vPanel.setBackground(bg);
		hPanel.setBackground(bg);
		labelPanel.setBackground(bg);
	}
	
	private void addComponents() {
		name = new JLabel(playlist.getName());
		name.setForeground(Constants.FONT_COLOR);
		name.setFont(Constants.DEFAULT_FONT);
		numVideos = new JLabel(Integer.toString(playlist.getNumVideos()) + " videos");
		numVideos.setForeground(Constants.LIGHT_FONT_COLOR);
		numVideos.setFont(Constants.ITALIC_FONT);
		
		vPanel = new JPanel();
		vPanel.setBackground(getBackground());
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));
		
		hPanel = new JPanel();
		hPanel.setBackground(getBackground());
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));
		
		labelPanel = new JPanel();
		labelPanel.setBackground(getBackground());
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.setMinimumSize(new Dimension(Constants.PAGE_WIDTH - 10 - 35, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		labelPanel.setMaximumSize(new Dimension(Constants.PAGE_WIDTH - 10 - 35, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		labelPanel.setPreferredSize(new Dimension(Constants.PAGE_WIDTH - 10 - 35, Constants.VIDEOLIST_ENTRY_HEIGHT - 10));
		
		name.setAlignmentX(LEFT_ALIGNMENT);
		numVideos.setAlignmentX(LEFT_ALIGNMENT);
		labelPanel.add(name);
		labelPanel.add(numVideos);
		
		hPanel.add(Box.createRigidArea(new Dimension(30, Constants.VIDEOLIST_ENTRY_HEIGHT - 10)));
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

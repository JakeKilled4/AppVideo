package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SinglePlaylistPanel extends CommonPanel {

	private static final String DELETE_VIDEOS_ENTRY = "delete_videos";
	private static final String CANCEL_ENTRY        = "cancel";
	
	private Playlist             playlist;
	private List<VideoListEntry> videoEntries;

	public SinglePlaylistPanel(MainWindow mainWindow, Playlist playlist) {
		super(mainWindow);
		this.playlist = playlist;
		createScreen();
	}
	
	@Override
	protected JPanel createInnerPanel() {
		videoEntries = playlist.getVideos()
			.stream ()
			.map    (v -> new VideoListEntry(mainWindow, v, playlist))
			.collect(Collectors.toList());
		List<ListEntry> entries = new LinkedList<ListEntry>();
		entries.add(makeInfoEntry   ());
		entries.add(makeControlEntry());
		for (ListEntry le: videoEntries) {
			entries.add(le);
		}
		
		return new CommonListPanel(mainWindow, entries);
	}

	private ListEntry makeInfoEntry() {
		return new ListEntry(mainWindow) {
			{
				addComponents();
			}
			@Override
			protected JPanel createInnerPanel() {
				JPanel p = new JPanel();
				p.setLayout    (new BoxLayout(p, BoxLayout.Y_AXIS));
				p.setAlignmentX(CENTER_ALIGNMENT);
				p.setBackground(Constants.FOREGROUND_COLOR);

				ComponentFactory componentFactory = ComponentFactory.getUniqueInstance();
				JLabel           titleLabel       = componentFactory.specialLabel("Playlist '" + playlist.getName() + "'");
				titleLabel.setFont      (new Font("Default", Font.ITALIC, 17));
				titleLabel.setAlignmentX(CENTER_ALIGNMENT);
				
				p.add(titleLabel);
				return p;
			}
		};
	}
	
	private ListEntry makeControlEntry() {
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
					makeDeleteVideosPanel(controlPanel, layout),
					DELETE_VIDEOS_ENTRY
				);
				controlPanel.add(
					makeCancelPanel(controlPanel, layout),
					CANCEL_ENTRY
				);
				
				return controlPanel;
			}
		};
	}
	
	private JComponent makeDeleteVideosPanel(JPanel controlPanel, CardLayout layout) {
		JPanel deleteBtn = defineButton("Delete video");
		
		addClicker(
			deleteBtn,
			() -> {
				layout.show(controlPanel, CANCEL_ENTRY);
				for (VideoListEntry vle: videoEntries) {
					vle.setRemoveMode(true);
				}
			}
		);
		
		return deleteBtn;
	}
	
	private JComponent makeCancelPanel(JPanel controlPanel, CardLayout layout) {
		JPanel cancelBtn = defineButton("Cancel");
		
		addClicker(
			cancelBtn,
			() -> {
				layout.show(controlPanel, DELETE_VIDEOS_ENTRY);
				for (VideoListEntry vle: videoEntries) {
					vle.setRemoveMode(false);
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

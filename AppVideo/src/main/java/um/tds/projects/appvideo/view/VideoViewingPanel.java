package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Label;
import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.Video;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JSeparator;
import javax.swing.JTextField;

import um.tds.projects.appvideo.controller.Controller;
import tds.video.VideoWeb;


@SuppressWarnings("serial")
public class VideoViewingPanel extends CommonPanel  {

	private Video      video;
	private VideoWeb   videoWeb;
	private Controller controller;

	public VideoViewingPanel(MainWindow mainWindow, Video video) {
		super(mainWindow);
		this.video      = video;
		this.videoWeb   = mainWindow.getVideoWeb();
		this.controller = Controller.getUniqueInstance();
		this.controller.addViewToVideo(video);
		createScreen();
	}
	
	@Override
	protected JPanel createInnerPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Constants.BACKGROUND_COLOR);
		panel.setAlignmentX(CENTER_ALIGNMENT);
		fixSize(panel, Constants.PAGE_WIDTH, 310);
		
		panel.add(    makeTitleLabel         ());
		panel.add(    makeSeparator          (Color.WHITE));
		panel.add(Box.createRigidArea        (new Dimension(0, 10)));
		panel.add(    makeVideoAndLabelsPanel());
		panel.add(    makeNumViewsLabel      ());
		panel.add(Box.createRigidArea        (new Dimension(10,10)));
		panel.add(    makeBottomComponent    ());

		// Start playing the video
		videoWeb.playVideo(video.getUrl());
		return panel;
	}

	private JLabel makeTitleLabel() {
		JLabel title = new JLabel(video.getTitle());
		title.setFont      (Constants.TITLE_FONT);
		title.setBackground(Constants.FONT_COLOR);
		title.setForeground(Constants.FONT_COLOR);
		title.setAlignmentX(LEFT_ALIGNMENT);
		return title;
	}

	private JPanel makeVideoAndLabelsPanel() {
		JPanel hPanel = new JPanel();
		hPanel.setBackground(Constants.BACKGROUND_COLOR);
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));
		hPanel.setAlignmentX(LEFT_ALIGNMENT);

		JPanel rightPanel = new JPanel();
		FlowLayout layout = new FlowLayout();
	    layout.setHgap(10);
	    layout.setVgap(10);
	     
		rightPanel.setLayout(layout);
		rightPanel.setBackground(Constants.BACKGROUND_COLOR);
		fixSize(rightPanel,400,200);
		
		for (Label label : video.getLabels()) 
			addLabelToVideo(label);

		hPanel.add(videoWeb);
		hPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		hPanel.add(rightPanel);
		return hPanel;
	}

	private JLabel makeNumViewsLabel() {
		String numViews    = Integer.toString(video.getNumViews());
		JLabel numViewsLbl = new JLabel(numViews + ((numViews.equals("1")) ? " view" : " views"));
		numViewsLbl.setFont      (Constants.ITALIC_FONT);	
		numViewsLbl.setBackground(Constants.LIGHT_FONT_COLOR);
		numViewsLbl.setForeground(Constants.LIGHT_FONT_COLOR);
		numViewsLbl.setAlignmentX(LEFT_ALIGNMENT);
		return numViewsLbl;
	}

	private JPanel makeBottomComponent() {
		JPanel hPanel = new JPanel();
		hPanel.setBackground(Constants.BACKGROUND_COLOR);
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));
		hPanel.setAlignmentX(LEFT_ALIGNMENT);

		hPanel.add(    makeAddLabelsField());
		hPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		hPanel.add(    makePlaylistButton());
		
		return hPanel;
	}

	private JButton makePlaylistButton() {
		JButton playlistButton = new JButton("Add video to playlist");
		playlistButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<Playlist> playlists     = controller.getPlaylists();
					if (playlists.isEmpty()) {
						JOptionPane.showMessageDialog(playlistButton, "There are no playlists!");
					} else {
						Object[] playlistNames = playlists.stream().map(Playlist::getName).toList().toArray();
						String choosenPlaylistName = (String) JOptionPane.showInputDialog(
							playlistButton,
							"To which playlist would you like ot add the video?",
							"Playlist selection",
							JOptionPane.PLAIN_MESSAGE,
							null,
							playlistNames,
							playlistNames[0]
						);
						if (choosenPlaylistName != null) {
							Playlist choosenPlaylist = null;
							for (Playlist playlist: playlists) {
								if (choosenPlaylistName.equals(playlist.getName())) {
									choosenPlaylist = playlist;
									break;
								}
							}
							
							if (choosenPlaylist == null) {
								logger.severe("The playlist chooser returned a string not corresponding to any playlist");
								return;
							} else {
								controller.addVideoToPlaylist(choosenPlaylist, video);
							}
						}
					}
				}
			}
		);
		return playlistButton;
	}

	private JTextField makeAddLabelsField() {
		JTextField textField = new JTextField();
		textField.setColumns       (10);
		textField.setBackground    (Constants    .SEARCH_COLOR);
		textField.setForeground    (Constants    .FONT_COLOR);
		textField.setFont          (Constants    .DEFAULT_FONT);
		textField.setCaretColor    (Constants    .FONT_COLOR);
		textField.setBorder        (BorderFactory.createEmptyBorder());
		textField.setText          ("Add label...");
		textField.setMinimumSize   (new Dimension(textField.getWidth(),25));
		textField.setPreferredSize (new Dimension(textField.getWidth(),25));
		textField.addFocusListener (makeTextFieldFocusListener(textField));
		textField.addActionListener(makeTextFieldActionListener(textField));
		return textField;
	}
	
	private FocusListener makeTextFieldFocusListener(JTextField textField) {
		return new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(textField.getText().trim().equals("")) textField.setText("Add label...");
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				  if(textField.getText().trim().equals("Add label...")) textField.setText("");
			}
		};
	}
	
	private ActionListener makeTextFieldActionListener(JTextField textField) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Label l = controller.addLabelToVideo(video, textField.getText());
				if (l == null) {
					mainWindow.showPopUp(
						"Information",
						"The label is alredy in the video" ,
						JOptionPane.INFORMATION_MESSAGE
					);
				} else {
					addLabelToVideo(l);	
				}
				textField.setText("");
				validate();
			}
		};
	}

	static private JSeparator makeSeparator(Color color) {
		JSeparator sep = new JSeparator();
		sep.setMaximumSize(new Dimension(Short.MAX_VALUE, Constants.SEPARATOR_HEIGHT));
		sep.setBackground(color);
		sep.setForeground(color);
		return sep;
	}

	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}
	
	private void addLabelToVideo(Label label) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBackground(Constants.BUTTON_HOVER_COLOR);
		JLabel l = new JLabel(label.getName());
		l.setForeground(Constants.FONT_COLOR);
		l.setFont(new Font("Default", Font.ITALIC, 15));
		p.add(Box.createRigidArea(new Dimension(10, 10)));
		p.add(l);
		p.add(Box.createRigidArea(new Dimension(10, 10)));
		rightPanel.add(p);
	}
}

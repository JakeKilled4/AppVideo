package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Video;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private LoginPanel        loginPanel;
	private PlaylistsPanel    playlistsPanel;
	private PreferencesPanel  preferencesPanel;
	private SearchPanel       searchPanel;
	private RegisterPanel     registerPanel;
	private VideoViewingPanel videoViewingPanel;

	public void showWindow() {
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public MainWindow() {
		loginPanel        = new LoginPanel      (this);
		playlistsPanel    = new PlaylistsPanel  (this);
		preferencesPanel  = new PreferencesPanel(this);
		searchPanel       = new SearchPanel     (this);
		registerPanel 	  = new RegisterPanel   (this);
		videoViewingPanel = null; // Es redefinido al visualizar cada video.

		setContentPane(loginPanel);
		setMinimumSize(new Dimension(1000, 600));
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void activateLoginPanel() {
		setContentPane(loginPanel);
		validate();
	}

	public void activatePlaylistsPanel() {
		setContentPane(playlistsPanel);
		validate();
	}

	public void activatePreferencesPanel() {
		setContentPane(preferencesPanel);
		validate();
	}

	public void activateSearchPanel() {
		setContentPane(searchPanel);
		validate();
	}
	
	public void activateRegisterPanel() {
		setContentPane(registerPanel);
		validate();
	}
	
	public void activateVideoViewingPanel(Video video) {
		videoViewingPanel = new VideoViewingPanel(this, video);
		setContentPane(videoViewingPanel);
		validate();
	}

}

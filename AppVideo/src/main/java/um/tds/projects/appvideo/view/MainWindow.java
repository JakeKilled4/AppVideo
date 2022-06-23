package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.controller.Controller;

import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import tds.video.VideoWeb;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private static Logger logger = Logger.getLogger("um.tds.projects.appvideo.controller.controller");

	private LoginPanel          loginPanel;
	private PlaylistsPanel      playlistsPanel;
	private PreferencesPanel    preferencesPanel;
	private SearchPanel         searchPanel;
	private RegisterPanel       registerPanel;
	private VideoViewingPanel   videoViewingPanel;
	private SinglePlaylistPanel singlePlaylistPanel;
	private RecentsPanel        recentsPanel;
	private PopularVideosPanel  popularVideosPanel;
	private VideoWeb videoWeb;
	private Controller controller;

	public void showWindow() {
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public MainWindow(VideoWeb videoWeb) {
		logger.info("Starting MainWindow");
		this.videoWeb = videoWeb;
		this.controller = Controller.getUniqueInstance();
		this.controller.setMainWindow(this);
		this.loginPanel        = new LoginPanel   (this);
		this.registerPanel 	   = new RegisterPanel(this);
		
	
		UIManager.put("OptionPane.background",        Constants.BACKGROUND_COLOR);
		UIManager.put("Panel.background",             Constants.BACKGROUND_COLOR);
		UIManager.put("OptionPane.messageForeground", Constants.FONT_COLOR);
		 
		videoViewingPanel   = null; // Es redefinido al visualizar cada video.
		singlePlaylistPanel = null; // Redefinido al entrar a cada playlsit;

		logger.info("Entering login panel");
		setContentPane(loginPanel);
		
		setMinimumSize(new Dimension(1000, 600));
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public VideoWeb getVideoWeb() {
		return videoWeb;
	}

	public void enterApp() {
		this.preferencesPanel   = new PreferencesPanel  (this);
		this.playlistsPanel     = new PlaylistsPanel    (this);
		this.searchPanel        = new SearchPanel       (this);
		this.recentsPanel       = new RecentsPanel      (this);
		this.popularVideosPanel = new PopularVideosPanel(this);
		activateSearchPanel(UpdateOption.NONE);
	}
	public void activateLoginPanel() {
		setContentPane(loginPanel);
		validate();
	}
	
	public void activateRegisterPanel() {
		setContentPane(registerPanel);
		validate();
	}

	public void activatePlaylistsPanel() {
		setContentPane(playlistsPanel);
		playlistsPanel.updateCenterPanel();
		videoWeb.cancel();
		validate();
	}

	public void activateSearchPanel(UpdateOption option) {
		setContentPane(searchPanel);
		if(option == UpdateOption.BOTH || option == UpdateOption.RIGHT)
			searchPanel.updateRightPanel();
		if(option == UpdateOption.BOTH || option == UpdateOption.CENTER) 
			searchPanel.updateCenterPanel();
		videoWeb.cancel();
		validate();
	}
	
	public void activatePreferencesPanel() {
		setContentPane(preferencesPanel);
		preferencesPanel.updateCenterPanel();
		videoWeb.cancel();
		validate();
	}

	public void activateVideoViewingPanel(Video video) {
		videoWeb.cancel();
		videoViewingPanel = new VideoViewingPanel(this, video);
		setContentPane(videoViewingPanel);
		validate();
	}
	
	public void activateSinglePlaylistPanel(Playlist playlist) {
		singlePlaylistPanel = new SinglePlaylistPanel(this, playlist);
		videoWeb.cancel();
		setContentPane(singlePlaylistPanel);
		validate();
	}
	
	public void showPopUp(String title,String message, int type) {
		 JOptionPane.showMessageDialog(null, message, title, type);
	}
	
	public void activateRecentsPanel() {
		setContentPane(recentsPanel);
		videoWeb.cancel();
		recentsPanel.updateCenterPanel();
		validate();
	}
	
	public void activatePopularVideosPanel() {
		setContentPane(popularVideosPanel);
		videoWeb.cancel();
		popularVideosPanel.updateCenterPanel();
		validate();
	}

}

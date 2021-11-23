package um.tds.projects.appvideo.view;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private JPanel loginPanel;
	private JPanel playlistsPanel;
	private JPanel preferencesPanel;
	private JPanel searchPanel;
	private JPanel registerPanel;
	private JPanel manageListPanel;

	public void showWindow() {
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public MainWindow() {
		loginPanel       = new LoginPanel      (this);
		playlistsPanel   = new PlaylistsPanel  (this);
		preferencesPanel = new PreferencesPanel(this);
		searchPanel      = new SearchPanel     (this);
		registerPanel 	 = new RegisterPanel   (this);
		manageListPanel  = new ManageListPanel (this);

		setContentPane(loginPanel);
		setMinimumSize(new Dimension(250, 150));
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
	public void activateManageListPanel() {
		setContentPane(manageListPanel);
		validate();
	}

}

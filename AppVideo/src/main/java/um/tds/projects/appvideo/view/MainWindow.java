package um.tds.projects.appvideo.view;


import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow {

	private JFrame frame;
	private JPanel loginPanel;
	private JPanel playlistsPanel;
	private JPanel preferencesPanel;
	private JPanel searchPanel;
	private JPanel registerPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		loginPanel       = new LoginPanel      (this);
		playlistsPanel   = new PlaylistsPanel  (this);
		preferencesPanel = new PreferencesPanel(this);
		searchPanel      = new SearchPanel     (this);
		registerPanel 	 = new RegisterPanel   (this);

		frame.setContentPane(loginPanel);
		frame.setMinimumSize(new Dimension(250, 150));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void activateLoginPanel() {
		frame.setContentPane(loginPanel);
		frame.validate();
	}

	public void activatePlaylistsPanel() {
		frame.setContentPane(playlistsPanel);
		frame.validate();
	}

	public void activatePreferencesPanel() {
		frame.setContentPane(preferencesPanel);
		frame.validate();
	}

	public void activateSearchPanel() {
		frame.setContentPane(searchPanel);
		frame.validate();
	}
	public void activateRegisterPanel() {
		frame.setContentPane(registerPanel);
		frame.validate();
	}

}

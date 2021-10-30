package um.tds.projects.appvideo.view;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
	private LoginPanel       loginPanel;
	private SearchPanel      searchPanel;
	private PreferencesPanel preferencesPanel;
	private PlaylistsPanel   playlistsPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		MainWindow mainWindow = new MainWindow();
    }

	/**
	 * Create the application.
	 */
	public MainWindow()
	{
		setSize (Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setTitle("AppVideo");
		
		loginPanel       = new LoginPanel      (this);
		searchPanel      = new SearchPanel     (this);
		preferencesPanel = new PreferencesPanel(this);
		playlistsPanel   = new PlaylistsPanel  (this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable            (false);
		setContentPane          (loginPanel);
		pack                    ();
		setVisible              (true);
	}
	
	public void setSearchPanel()
	{
		setContentPane(searchPanel);
	}
	
	public void setPreferencesPanel()
	{
		setContentPane(preferencesPanel);
	}
	
	public void setPlaylistsPanel()
	{
		setContentPane(playlistsPanel);
	}
}

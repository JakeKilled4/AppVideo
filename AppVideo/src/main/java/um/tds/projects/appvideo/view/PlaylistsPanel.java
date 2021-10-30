package um.tds.projects.appvideo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PlaylistsPanel extends JPanel
{
	private MainWindow mainWindow;
	private JLabel     dummyLabel;
	
	public PlaylistsPanel(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		createScreen();
	}
	
	private void createScreen()
	{
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());
		
		dummyLabel = new JLabel("Ventana de búsqueda");
		add(dummyLabel, BorderLayout.CENTER);
	}
	
	private void fixSize(JComponent component, int x, int y)
	{
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}
}

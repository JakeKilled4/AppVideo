package um.tds.projects.appvideo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PreferencesPanel extends JPanel {

	private MainWindow mainWindow;
	private JPanel toolbarPanel;
	private JLabel dummyLabel;
	private JButton btnLogOut;

	public PreferencesPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createScreen();
	}

	private void createScreen() {
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());
		setBackground(Constants.BACKGROUND_COLOR);

		toolbarPanel = new ToolbarPanel(mainWindow);
		dummyLabel = new JLabel("Panel de preferencias");
		btnLogOut = new JButton("Log Out");
		
		btnLogOut.addActionListener(e -> {
			mainWindow.activateLoginPanel();
		});
		
		add(toolbarPanel, BorderLayout.WEST);
		add(dummyLabel,   BorderLayout.CENTER);
		add(btnLogOut, BorderLayout.CENTER);
	}

	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}
}

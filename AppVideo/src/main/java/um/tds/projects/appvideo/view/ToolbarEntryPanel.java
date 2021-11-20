package um.tds.projects.appvideo.view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ToolbarEntryPanel extends JPanel {

	private MainWindow mainWindow;
	private String text;

	public ToolbarEntryPanel(MainWindow mainWindow) {
		this(mainWindow, "");
	}

	public ToolbarEntryPanel(MainWindow mainWindow, String text) {
		this.mainWindow = mainWindow;
		this.text       = text;

		setBackground   (Constants.BUTTON_COLOR);
		setMinimumSize  (new Dimension(Constants.TOOLBAR_OPEN_SIZE + 10, Constants.TOOLBAR_ENTRY_HEIGHT));
		setPreferredSize(new Dimension(Constants.TOOLBAR_OPEN_SIZE + 10, Constants.TOOLBAR_ENTRY_HEIGHT));
		setMaximumSize  (new Dimension(Constants.TOOLBAR_OPEN_SIZE + 10, Constants.TOOLBAR_ENTRY_HEIGHT));
		setLayout       (new BoxLayout(this, BoxLayout.X_AXIS));
		addComponents   ();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void addComponents() {
		add(Box.createRigidArea(new Dimension(10, 0)));
		JLabel label = new JLabel(text);
		label.setForeground(Constants.FONT_COLOR);
		add(label);
	}

}
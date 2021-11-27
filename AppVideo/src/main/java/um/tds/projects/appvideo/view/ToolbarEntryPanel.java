package um.tds.projects.appvideo.view;

import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ToolbarEntryPanel extends JPanel {

	private final class HoverMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			setBackground(Constants.BUTTON_COLOR);
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {
			setBackground(Constants.BUTTON_HOVER_COLOR);
		}

		public void mouseExited(MouseEvent e) {
			setBackground(Constants.BUTTON_COLOR);
		}
	}
	
	@FunctionalInterface
	public interface ClickAction {
		void execute();
	}

	private MainWindow mainWindow;
	private String text;

	public ToolbarEntryPanel(MainWindow mainWindow) {
		this(mainWindow, "");
	}

	public ToolbarEntryPanel(MainWindow mainWindow, String text) {
		this.mainWindow = mainWindow;
		this.text       = text;

		setBackground   (Constants.BUTTON_COLOR);
		setMinimumSize  (new Dimension(Constants.TOOLBAR_OPEN_SIZE, Constants.TOOLBAR_ENTRY_HEIGHT));
		setPreferredSize(new Dimension(Constants.TOOLBAR_OPEN_SIZE, Constants.TOOLBAR_ENTRY_HEIGHT));
		setMaximumSize  (new Dimension(Constants.TOOLBAR_OPEN_SIZE, Constants.TOOLBAR_ENTRY_HEIGHT));
		setLayout       (new BoxLayout(this, BoxLayout.X_AXIS));
		addMouseListener(new HoverMouseListener());
		
		addComponents   ();
	}
	
	public void addClickAction(ClickAction action) {
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				action.execute();
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
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
		label.setFont      (Constants.STRONG_FONT);
		label.setForeground(Constants.FONT_COLOR);
		add(label);
	}

}

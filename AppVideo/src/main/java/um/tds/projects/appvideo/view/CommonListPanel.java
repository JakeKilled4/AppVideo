package um.tds.projects.appvideo.view;

import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;


@SuppressWarnings("serial")
public class CommonListPanel extends JPanel {

	private List<ListEntry> entries;

	public CommonListPanel(MainWindow mainWindow, List<ListEntry> entries) {
		// Set properties.
		this.entries = entries;

		// Init panel.
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout    (new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
	}

	private int getLength() {
		// There are "entries.size()" entries, and "entries.size()-1" separators.
		return entries.size() * (Constants.VIDEOLIST_ENTRY_HEIGHT + Constants.SEPARATOR_HEIGHT) - Constants.SEPARATOR_HEIGHT;
	}

	private void addComponents() {
		// innerPage holds the list's content.
		JPanel innerPage = new JPanel();
		innerPage.setBackground(Constants.BACKGROUND_COLOR);
		innerPage.setLayout(new BoxLayout(innerPage, BoxLayout.Y_AXIS));
		innerPage.setMaximumSize(new Dimension(Constants.PAGE_WIDTH, getLength()));
		innerPage.setMinimumSize(new Dimension(Constants.PAGE_WIDTH, getLength()));
		innerPage.setPreferredSize(new Dimension(Constants.PAGE_WIDTH, getLength()));
		innerPage.setAlignmentX(CENTER_ALIGNMENT);

		// There is a separator between each adjacent pair of entries.
		boolean first = true;
		for (ListEntry entry: entries) {
			if (first) first = false;
			else       addSeparator(innerPage);
			innerPage.add(entry);
		}

		// By putting innerPage inside a scroll pane, we allow it to grow indefinitely.
		JScrollPane scrollPane = new JScrollPane(innerPage);
		scrollPane.setBackground(Constants.BACKGROUND_COLOR);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane);
	}

	static private void addSeparator(JPanel panel) {
		JSeparator sep = new JSeparator();
		sep.setMaximumSize(new Dimension(Constants.PAGE_WIDTH, Constants.SEPARATOR_HEIGHT));
		sep.setBackground(Constants.BACKGROUND_COLOR);
		sep.setForeground(Constants.BACKGROUND_COLOR);
		panel.add(sep);
	}

}

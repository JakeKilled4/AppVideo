package um.tds.projects.appvideo.view;


import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class PreferencesList extends JPanel {

	private MainWindow mainWindow;

	public PreferencesList(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout    (new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
	}


	private void addComponents() {

		JPanel innerPage = new JPanel();
		innerPage.setBackground(Constants.BACKGROUND_COLOR);
		innerPage.setLayout(new BoxLayout(innerPage, BoxLayout.Y_AXIS));
		innerPage.setMaximumSize(new Dimension(Constants.PAGE_WIDTH, 30));
		innerPage.setMinimumSize(new Dimension(Constants.PAGE_WIDTH, 30));
		innerPage.setPreferredSize(new Dimension(Constants.PAGE_WIDTH, 30));

		JLabel pageTitle = new JLabel("Preferences");
		pageTitle.setFont(Constants.TITLE_FONT);
		pageTitle.setForeground(Constants.LIGHT_FONT_COLOR);
		JButton logoutBtn = new JButton("Log out");

		logoutBtn.addActionListener(e -> {
			mainWindow.activateLoginPanel();
		});

		innerPage.add(new PreferencesListEntry(pageTitle));
		innerPage.add(new PreferencesListEntry(logoutBtn));

		JScrollPane scrollPane = new JScrollPane(innerPage);
		scrollPane.setBackground(Constants.BACKGROUND_COLOR);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane);
	}

}

package um.tds.projects.appvideo.view;


import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.controller.Controller;


@SuppressWarnings("serial")
public class PreferencesList extends JPanel {

	private MainWindow mainWindow;
	private Controller controller;
	List<JLabel> etiq;
	JPanel innerPage;

	public PreferencesList(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout    (new BoxLayout(this, BoxLayout.Y_AXIS));
		this.controller = Controller.getUniqueInstance();
		etiq = new LinkedList<JLabel>();
		addComponents();
	}


	private void addComponents() {

		innerPage = new JPanel();
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
		
		update();
	}
	void update() {
		for(JLabel l : etiq) innerPage.remove(l);
		etiq.clear();
		for(Video v : controller.getAllVideos()) {
			JLabel label = new JLabel(String.valueOf(v.getNumViews()));
			label.setFont(Constants.TITLE_FONT);
			innerPage.add(label);
			etiq.add(label);
		}
		validate();
	}

}

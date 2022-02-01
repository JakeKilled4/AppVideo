package um.tds.projects.appvideo.view;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PreferencesPanel extends CommonPanel {

	public PreferencesPanel(MainWindow mainWindow) {
		super(mainWindow);
		createScreen();
	}

	@Override
	protected JPanel createInnerPanel() {
		return new PreferencesList(mainWindow);
	}

}

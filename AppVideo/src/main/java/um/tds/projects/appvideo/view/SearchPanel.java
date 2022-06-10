package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.controller.Controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SearchPanel extends CommonPanel {

	private Controller controller;

	public SearchPanel(MainWindow mainWindow) {
		super(mainWindow);
		this.controller = Controller.getUniqueInstance();
		createScreen();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JPanel createInnerPanel() {
		Object entries = controller.searchVideos(null, null)
			.stream()
			.map( v -> new VideoListEntry(mainWindow, v))
			.collect(Collectors.toList());
		return new CommonListPanel(mainWindow, (List<ListEntry>) entries);
	}

	@Override
	protected JPanel createRightPanel() {
		return new LabelPanel("Dibujos animados", "Pelicula", "Serie", "Intriga", "Terror", "Clasico", "Videoclip",
				"Adultos", "Infantil");
	}

}

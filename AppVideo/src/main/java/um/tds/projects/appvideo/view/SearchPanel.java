package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Video;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SearchPanel extends CommonPanel {

	public SearchPanel(MainWindow mainWindow) {
		super(mainWindow);
		createScreen();
	}

	@Override
	protected JPanel createInnerPanel() {
		return new CommonListPanel(mainWindow, Arrays.asList(
									   new VideoListEntry(mainWindow, new Video("", "Smart cat solves Millenium problem", 1324)),
									   new VideoListEntry(mainWindow, new Video("", "UM > UMU", 325)),
									   new VideoListEntry(mainWindow, new Video("", "Lo bueno, si breve, dos veces bueno", 325)),
									   new VideoListEntry(mainWindow, new Video("", "Oh, brave new world...", 3205)),
									   new VideoListEntry(mainWindow, new Video("", "En un lugar de la mancha...", 325)),
									   new VideoListEntry(mainWindow, new Video("", "Smart cat solves Millenium problem", 1324)),
									   new VideoListEntry(mainWindow, new Video("", "UMU > 0", 3250000)),
									   new VideoListEntry(mainWindow, new Video("", "Oh, brave new world...", 3205)),
									   new VideoListEntry(mainWindow, new Video("", "En un lugar de la mancha...", 325)),
									   new VideoListEntry(mainWindow, new Video("", "Smart cat solves Millenium problem", 1324)),
									   new VideoListEntry(mainWindow, new Video("", "UMU > 0", 3250000)),
									   new VideoListEntry(mainWindow, new Video("", "Oh, brave new world...", 3205)),
				new VideoListEntry(mainWindow, new Video("", "En un lugar de la mancha...", 325))));
	}

	@Override
	protected JPanel createRightPanel() {
		return new LabelPanel("Dibujos animados", "Pelicula", "Serie", "Intriga", "Terror", "Clasico", "Videoclip",
				"Adultos", "Infantil");
	}

}

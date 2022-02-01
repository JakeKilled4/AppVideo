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
		return new VideoList(mainWindow, Arrays.asList(new Video("", "Smart cat solves Millenium problem", 1324),
				new Video("", "UM > UMU", 325), new Video("", "UMU > 0", 3250000),
				new Video("", "Lo bueno, si breve, dos veces bueno", 325),
				new Video("", "Oh, brave new world...", 3205), new Video("", "Arma virumque cano...", 3025),
				new Video("", "En un lugar de la mancha...", 325), new Video("", "Entrevista a Dijkstra", 32005),
				new Video("", "Smart cat solves Millenium problem", 1324), new Video("", "UM > UMU", 325),
				new Video("", "UMU > 0", 3250000), new Video("", "Lo bueno, si breve, dos veces bueno", 325),
				new Video("", "Oh, brave new world...", 3205), new Video("", "Arma virumque cano...", 3025),
				new Video("", "En un lugar de la mancha...", 325), new Video("", "Entrevista a Dijkstra", 32005),
				new Video("", "Smart cat solves Millenium problem", 1324), new Video("", "UM > UMU", 325),
				new Video("", "UMU > 0", 3250000), new Video("", "Lo bueno, si breve, dos veces bueno", 325),
				new Video("", "Oh, brave new world...", 3205), new Video("", "Arma virumque cano...", 3025),
				new Video("", "En un lugar de la mancha...", 325), new Video("", "Entrevista a Dijkstra", 32005)));
	}

	@Override
	protected JPanel createRightPanel() {
		return new LabelPanel("Dibujos animados", "Pelicula", "Serie", "Intriga", "Terror", "Clasico", "Videoclip",
				"Adultos", "Infantil");
	}

}

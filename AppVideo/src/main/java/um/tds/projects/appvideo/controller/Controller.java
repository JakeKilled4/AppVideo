package um.tds.projects.appvideo.controller;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.IVideoFilter;
import um.tds.projects.appvideo.backend.Video;

import java.util.Arrays;
import java.util.List;


public class Controller {

	private static Controller instance;

	private Controller() { }

	public static Controller getUniqueInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public List<Playlist> getPlaylists() {
		return Arrays.asList(new Playlist("TDS vibes"),
							 new Playlist("What to hear when studying Geometry"),
							 new Playlist("Videos on Algebraic Topology"),
							 new Playlist("TDS vibes"),
							 new Playlist("What to hear when studying Geometry"),
							 new Playlist("Videos on Algebraic Topology"),
							 new Playlist("TDS vibes"),
							 new Playlist("What to hear when studying Geometry"),
							 new Playlist("Videos on Algebraic Topology"));
	}

	public List<Video> searchVideos(String str, List<IVideoFilter> filter) {
		return Arrays.asList(new Video("", "Smart cat solves Millenium problem", 1324),
							 new Video("", "UM > UMU", 325),
							 new Video("", "Lo bueno, si breve, dos veces bueno", 325),
							 new Video("", "Oh, brave new world...", 3205),
							 new Video("", "En un lugar de la mancha...", 325),
							 new Video("", "Smart cat solves Millenium problem", 1324),
							 new Video("", "UM > UMU", 325),
							 new Video("", "Lo bueno, si breve, dos veces bueno", 325),
							 new Video("", "Oh, brave new world...", 3205),
							 new Video("", "En un lugar de la mancha...", 325),
							 new Video("", "Smart cat solves Millenium problem", 1324),
							 new Video("", "UM > UMU", 325),
							 new Video("", "Lo bueno, si breve, dos veces bueno", 325),
							 new Video("", "Oh, brave new world...", 3205),
							 new Video("", "En un lugar de la mancha...", 325));
	}

}

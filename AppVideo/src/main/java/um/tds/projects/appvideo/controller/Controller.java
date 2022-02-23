package um.tds.projects.appvideo.controller;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.backend.filters.IVideoFilter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Controller {

	private static Controller instance;

	private User currentUser;

	private Controller() { }

	public static Controller getUniqueInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	/**
	 * Returns true if the user is already registered.
	 */
	public boolean userIsRegistered(String username) {
		return true;
	}

	/**
	 * Returns true iff the login was successful (i.e. if username was in the user db and the password is correct)
	 */
	public boolean login(String username, String password) {
		return false;
	}

	public void logout() { }

	/**
	 * Returns true iff the register process was successful (i.e. If the username was not already taken)
	 */
	public boolean register(String name, String surname, Date dateOfBirth, String email, String username, String password) {
		return true;
	}

	public void createPlaylist(String name) { }

	public void removePlaylist(Playlist p) { }

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

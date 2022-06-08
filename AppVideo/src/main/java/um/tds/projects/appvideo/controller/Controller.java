package um.tds.projects.appvideo.controller;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.backend.UserRepository;
import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.backend.VideoRepository;
import um.tds.projects.appvideo.backend.filters.IVideoFilter;
import um.tds.projects.appvideo.persistence.ILabelAdapter;
import um.tds.projects.appvideo.persistence.IPlaylistAdapter;
import um.tds.projects.appvideo.persistence.IUserAdapter;
import um.tds.projects.appvideo.persistence.IVideoAdapter;
import um.tds.projects.appvideo.persistence.TdsLabelAdapter;
import um.tds.projects.appvideo.persistence.TdsPlaylistAdapter;
import um.tds.projects.appvideo.persistence.TdsUserAdapter;
import um.tds.projects.appvideo.persistence.TdsVideoAdapter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Controller {

	private static Controller instance;
	private ILabelAdapter    labelAdapter;
	private IPlaylistAdapter playlistAdapter;
	private IUserAdapter     userAdapter;
	private IVideoAdapter    videoAdapter;
	
	private UserRepository  userRepository;
	private VideoRepository videoRepository;

	private Controller() {
		initializeAdapters();
		initializeRepositories();
	}

	public static Controller getUniqueInstance() {
		if (instance == null)
			instance = new Controller();
		return instance;
	}

	/**
	 * Returns true if the user is already registered.
	 */
	public boolean userIsRegistered(String username) {
		return true;
	}

	/**
	 * Returns true iff the login was successful (If username was in the user db and the password is correct)
	 */
	public boolean login(String username, String password) {
		User u = userRepository.getUser(username);
		if (u != null && u.checkPassword(password))
			return true;
		return false;
	}

	public void logout() { }

	/**
	 * Returns true iff the register process was successful (If the username was not already taken)
	 */
	public boolean register(String name, String surname, Date dateOfBirth, String email, String username, String password) {
		if (userRepository.containsUser(username))
			return false; // Other user with same username
		User u = new User(name, surname, dateOfBirth, email, username, password);
		userAdapter.registerUser(u);
		userRepository.addUser(u);
		return true;
	}

	public void createPlaylist(String name) { }

	public void removePlaylist(Playlist p) { }

	//public void addVideoToPlaylist(Playlist p, Video video) { }

	public void removeVideoFromPlaylist(Playlist p, Video video) { }

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

	public void initializeAdapters(){
		labelAdapter    = TdsLabelAdapter.getUniqueInstance();
		playlistAdapter = TdsPlaylistAdapter.getUniqueInstance();
		userAdapter     = TdsUserAdapter.getUniqueInstance();
		videoAdapter    = TdsVideoAdapter.getUniqueInstance();
	}

	public void initializeRepositories(){
		userRepository  = UserRepository.getUniqueInstance();
		videoRepository = VideoRepository.getUniqueInstance(); 
	}
}

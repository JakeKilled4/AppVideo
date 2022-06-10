package um.tds.projects.appvideo.controller;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.backend.UserRepository;
import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.backend.VideoRepository;
import um.tds.projects.appvideo.backend.filters.IVideoFilter;
import um.tds.projects.appvideo.persistence.DaoFactory;
import um.tds.projects.appvideo.persistence.IPlaylistAdapter;
import um.tds.projects.appvideo.persistence.IUserAdapter;
import um.tds.projects.appvideo.persistence.IVideoAdapter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


public class Controller {

	// Controller's unique instance
	private static Controller instance;
	
	private static Logger logger = Logger.getLogger("um.tds.projects.appvideo.controller.controller");

	// Adapters and repositories
	private IPlaylistAdapter playlistAdapter;
	private IUserAdapter     userAdapter;
	private IVideoAdapter    videoAdapter;
	private UserRepository   userRepository;
	private VideoRepository  videoRepository;
	
	// Current user
	private User currentUser;

	private Controller() {
		initializeAdapters();
		initializeRepositories();
		this.currentUser = null;
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
		return userRepository.containsUser(username);
	}

	/**
	 * Returns true iff the login was successful (If username was in the user db and the password is correct)
	 */
	public boolean login(String username, String password) {
		User u = userRepository.getUser(username);
		if (u != null && u.checkPassword(password)) {
			logger.info("User correctly logged in");
			currentUser = u;
			return true;
		} else {
			logger.info("User login unsuccessful");
			return false;
		}
	}

	public void logout() {
		logger.info("User logged out");
		currentUser = null;
	}

	/**
	 * Returns true iff the register process was successful (If the username was not already taken)
	 */
	public boolean register(String name, String surname, Date dateOfBirth, String email, String username, String password) {
		if (userRepository.containsUser(username))
			return false;
		
		// If the username is not already taken, create the new user.
		currentUser = new User(name, surname, dateOfBirth, email, username, password);
		userAdapter.registerUser(currentUser);
		userRepository.addUser(currentUser);
		return true;
	}

	public Playlist createPlaylist(String name) {
		Playlist playlist = new Playlist(name);
		playlistAdapter.registerPlaylist(playlist);
		return playlist;
	}

	public void removePlaylist(Playlist playlist) {
		playlistAdapter.removeLPlaylist(playlist);
	}

	public void addVideoToPlaylist(Playlist playlist, Video video) {
		playlist.addVideo(video);
		playlistAdapter.modifyPlaylist(playlist);
	}

	public void removeVideoFromPlaylist(Playlist playlist, Video video) {
		playlist.removeVideo(video);
		playlistAdapter.modifyPlaylist(playlist);
	}

	public List<Playlist> getPlaylists() {
		return playlistAdapter.loadAllPlaylists();
	}
	
	public List<Video> getAllVideos() {
		return videoRepository.getAllVideos();
	}

	public List<Video> searchVideos(String str, List<IVideoFilter> filter) {
		return videoRepository.findVideo(str, filter);
	}

	private void initializeAdapters(){
		logger.info("Initialising adapters");
		
		DaoFactory factory = null;
		try {
			factory = DaoFactory.getUniqueInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		userAdapter     = factory.getUserAdapter();
		playlistAdapter = factory.getPlaylistAdapter();
		videoAdapter    = factory.getVideoAdapter();
		populateUserAdapter();
		populatePlaylistAdapter();
		populateVideoAdapter();

		logger.info("Finished initialising the adapters");
	}

	private void initializeRepositories(){
		logger.info("Initialising repositories");
		userRepository  = UserRepository.getUniqueInstance();
		videoRepository = VideoRepository.getUniqueInstance(); 
	}
	
	private void populatePlaylistAdapter() {
		logger.info("Populating playlists");
		List<Playlist> playlists = Arrays.asList(
			new Playlist("TDS vibes"),
			new Playlist("What to hear when studying Geometry"),
			new Playlist("Videos on Algebraic Topology"),
			new Playlist("TDS vibes"),
			new Playlist("What to hear when studying Geometry"),
			new Playlist("Videos on Algebraic Topology"),
			new Playlist("TDS vibes"),
			new Playlist("What to hear when studying Geometry"),
			new Playlist("Videos on Algebraic Topology")
		);
		for (Playlist p: playlists)
			playlistAdapter.registerPlaylist(p);
	}
	
	private void populateUserAdapter() {
		logger.info("Populating users");
		userAdapter.registerUser(
			new User(
				"admin", "", new Date(), "", "admin", "admin"
			)
		);
		logger.info("Ended populating users");
	}
	
	private void populateVideoAdapter() {
		logger.info("Populating videos");
		List<Video> videos = Arrays.asList(
			new Video("", "Smart cat solves Millenium problem", 1324),
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
			new Video("", "En un lugar de la mancha...", 325)
		);
		for (Video v: videos)
			videoAdapter.registerVideo(v);
	}
}

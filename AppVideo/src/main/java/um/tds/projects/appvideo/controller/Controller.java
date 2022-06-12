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

	public boolean createPlaylist(String name) {
		boolean created = currentUser.createPlaylist(name);
		
		if (created) {
			userAdapter.modifyUser(currentUser);
		}
		
		return created;
	}

	public void removePlaylist(Playlist playlist) {
		playlistAdapter.removePlaylist(playlist);
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
		return currentUser.getPlaylists();
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
			logger.severe("Could not instantiate the DAO factory");;
			System.exit(-1);
		}
		userAdapter     = factory.getUserAdapter();
		playlistAdapter = factory.getPlaylistAdapter();
		videoAdapter    = factory.getVideoAdapter();
		
		logger.info("Finished initialising the adapters");
	}

	private void initializeRepositories(){
		logger.info("Initialising repositories");
		userRepository  = UserRepository.getUniqueInstance();
		videoRepository = VideoRepository.getUniqueInstance(); 
	}
}

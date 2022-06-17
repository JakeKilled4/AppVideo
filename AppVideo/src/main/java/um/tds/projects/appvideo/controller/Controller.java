package um.tds.projects.appvideo.controller;

import um.tds.projects.appvideo.backend.Label;
import um.tds.projects.appvideo.backend.LabelRepository;
import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.backend.UserRepository;
import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.backend.VideoRepository;
import um.tds.projects.appvideo.backend.filters.IVideoFilter;
import um.tds.projects.appvideo.persistence.DaoFactory;
import um.tds.projects.appvideo.persistence.ILabelAdapter;
import um.tds.projects.appvideo.persistence.IPlaylistAdapter;
import um.tds.projects.appvideo.persistence.IUserAdapter;
import um.tds.projects.appvideo.persistence.IVideoAdapter;

import java.util.Date;
import java.util.LinkedList;
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
	private ILabelAdapter	 labelAdapter;
	
	private UserRepository   userRepository;
	private VideoRepository  videoRepository;
	private LabelRepository  labelRepository;
	
	// Current user
	private User currentUser;

	private Controller() {
		initializeAdapters();
		initializeRepositories();
		//cargarVideosPrueba();
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
	
	public User getCurrentUser() {
		return this.currentUser;
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
	 * Returns true iff the change process was successful (If the username was not already taken)
	 */
	public boolean changeUserData(String name, String surname, Date dateOfBirth, String email, String username, String password) {
		if(this.currentUser == null) return false;
		if(!this.currentUser.getUsername().equals(username) && userRepository.containsUser(username)) return false;
		userRepository.removeUser(this.currentUser);
		this.currentUser.setName(name);
		this.currentUser.setSurname(surname);
		this.currentUser.setDateOfBirth(dateOfBirth);
		this.currentUser.setEmail(email);
		this.currentUser.setUsername(username);
		this.currentUser.setPassword(password);
		userRepository.addUser(this.currentUser);
		userAdapter.modifyUser(this.currentUser);
		return true;
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
	
	public List<Label> getAllLabels(){
		return labelRepository.getAllLabels();
	}

	public List<Video> searchVideos(String str, List<IVideoFilter> filter) {
		return videoRepository.findVideo(str, filter);
	}
	
	public void addViewToVideo(Video v) {
		v.addView();
		videoAdapter.modifyVideo(v);
	}
	
	public Label addLabelToVideo(Video v,String name) {
		Label l = labelRepository.getLabel(name);
		if(l == null) {
			l = new Label(name);
			labelAdapter.registerLabel(l);
			labelRepository.addLabel(l);
		}
		v.addLabel(l);
		videoAdapter.modifyVideo(v);
		return l;
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
		labelAdapter	= factory.getLabelAdapter();
		
		logger.info("Finished initialising the adapters");
	}

	private void initializeRepositories(){
		logger.info("Initialising repositories");
		userRepository  = UserRepository.getUniqueInstance();
		videoRepository = VideoRepository.getUniqueInstance(); 
		labelRepository = LabelRepository.getUniqueInstance();
	}
	
	private void cargarVideosPrueba() {
		for(Video v : videoAdapter.loadAllVideos()) {
			videoAdapter.removeVideo(v);
		}
		
		List<Video> videoList = new LinkedList<Video>();
		List<Label> l = new LinkedList<Label>();
		for(int i = 0;i<0;i++) 
			l.add(new Label("Label"+i));
		videoList.add(new Video(
				"https://www.youtube.com/watch?v=3095_w_666w",
				"Top 40 Cello Covers of Popular Songs 2021 - Best Instrumental Cello Covers Songs All Time",
				100,l));
		videoList.add(new Video(
				"https://www.youtube.com/watch?v=p_di4Zn4wz4",
				"Vista general de ecuaciones diferenciales I Capítulo 1",
				3,l));
		for(Video v: videoList) {
		if(!videoRepository.containsVideo(v.getUrl()))
			videoRepository.addVideo(v);
			videoAdapter.registerVideo(v);
		}		
	}
}

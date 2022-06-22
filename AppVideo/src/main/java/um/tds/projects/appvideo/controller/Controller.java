package um.tds.projects.appvideo.controller;

import um.tds.projects.appvideo.backend.Label;
import um.tds.projects.appvideo.backend.LabelRepository;
import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.backend.UserRepository;
import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.backend.VideoRepository;
import um.tds.projects.appvideo.persistence.DaoFactory;
import um.tds.projects.appvideo.persistence.IFilterAdapter;
import um.tds.projects.appvideo.persistence.ILabelAdapter;
import um.tds.projects.appvideo.persistence.IPlaylistAdapter;
import um.tds.projects.appvideo.persistence.IUserAdapter;
import um.tds.projects.appvideo.persistence.IVideoAdapter;
import um.tds.projects.appvideo.view.MainWindow;
import um.tds.projects.appvideo.view.UpdateOption;
import umu.tds.componente.CargadorVideos;
import umu.tds.componente.FicheroIncorrectoException;
import umu.tds.componente.Videos;
import umu.tds.componente.VideosEvent;
import umu.tds.componente.VideosListener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



public class Controller implements VideosListener{

	// Controller's unique instance
	private static Controller instance;
	
	private static Logger logger = Logger.getLogger("um.tds.projects.appvideo.controller.controller");

	// Adapters and repositories
	private IPlaylistAdapter playlistAdapter;
	private IUserAdapter     userAdapter;
	private IVideoAdapter    videoAdapter;
	private ILabelAdapter	 labelAdapter;
	private IFilterAdapter 	 filterAdapter;
	
	private UserRepository   userRepository;
	private VideoRepository  videoRepository;
	private LabelRepository  labelRepository;
	
	// Text searched by user
	String searchedTitle;
	
	// List of labels selected to search
	List<Label> selectedLabels;
	
	// Current user
	private User currentUser;
	
	// Main window
	private MainWindow mainWindow;
	
	private Controller() {
		initializeAdapters();
		initializeRepositories();
		this.currentUser = null;
		this.searchedTitle = "";
		this.selectedLabels = new LinkedList<Label>();
	}

	public boolean underEighteen() {
		Calendar a = Calendar.getInstance();
		a.setTime(currentUser.getDateOfBirth());
		Calendar b = Calendar.getInstance();
	    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
	    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
	        (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff < 18;
	}
	
	public static Controller getUniqueInstance() {
		if (instance == null)
			instance = new Controller();
		return instance;
	}
	
	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	public List<Label> getSelectedLabels(){
		return selectedLabels;
	}
	
	public void setSelectedLabel(List<String> l) {
		selectedLabels = l.stream().map(s -> new Label(s)).collect(Collectors.toList());
	}
	
	public String getSearchTitle(){
		return searchedTitle;
	}
	
	public void searchVideos(String text) {
		searchedTitle = text;
		mainWindow.activateSearchPanel(UpdateOption.CENTER);
	}
	
	public List<Video> getSearchedVideos() {
		return videoRepository.findVideo(searchedTitle, currentUser.getFilters(), selectedLabels);
	}
	@Override
	public void hayNuevosVideos(EventObject arg) {
		VideosEvent e = (VideosEvent)arg;
		Videos videos =  e.getVideos();
		for(umu.tds.componente.Video v : videos.getVideo()) {
			Video video;
			boolean videoExits = false;
			if(videoRepository.containsVideo(v.getURL())) {
				video = videoRepository.getVideo(v.getURL());
				videoRepository.removeVideo(video);
				videoExits = true;
			}
			else video = new Video(v.getURL(), v.getTitulo(), 0);
			
			for(String l : v.getEtiqueta()) {
				Label label;
				if(labelRepository.containsLabel(l)) 
					label = labelRepository.getLabel(l);
				else {
					label = new Label(l);
					labelRepository.addLabel(label);
					labelAdapter.registerLabel(label);
				}
				video.addLabel(label);
			}
			videoRepository.addVideo(video);
			if(videoExits) videoAdapter.modifyVideo(video);
			else videoAdapter.registerVideo(video);
		}
	}
	
	public void selectFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			CargadorVideos cargadorVideos = new CargadorVideos();
			cargadorVideos.addVideosListener(this);
			try {
				cargadorVideos.cargarVideos(selectedFile);
			} catch (FicheroIncorrectoException e) {
				 mainWindow.showPopUp("Error","Invalid file",JOptionPane.ERROR_MESSAGE);
			}
		}
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
	public boolean changeUserData(String name, String surname, Date dateOfBirth, String email, String username, String password, boolean isPremium) {
		if(this.currentUser == null) return false;
		if(!this.currentUser.getUsername().equals(username) && userRepository.containsUser(username)) return false;
		userRepository.removeUser(this.currentUser);
		this.currentUser.setName(name);
		this.currentUser.setSurname(surname);
		this.currentUser.setDateOfBirth(dateOfBirth);
		this.currentUser.setEmail(email);
		this.currentUser.setUsername(username);
		this.currentUser.setPassword(password);
		this.currentUser.setPremium(isPremium);
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
		logger.info("Removing the playlist '" + playlist.getName() + "'");
		currentUser.removePlaylist(playlist);
		userAdapter.modifyUser(currentUser);
	}

	public boolean addVideoToPlaylist(Playlist playlist, Video video) {
		if (playlist.addVideo(video)) {
			playlistAdapter.modifyPlaylist(playlist);
			return true;
		} else {
			return false;
		}
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
		// If the label isn't in the video
		if(v.addLabel(l)) {
			videoAdapter.modifyVideo(v);
			return l;
		}
		return null;
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
		filterAdapter 	= factory.getFilterAdapter();
		
		logger.info("Finished initialising the adapters");
	}

	private void initializeRepositories(){
		logger.info("Initialising repositories");
		userRepository  = UserRepository.getUniqueInstance();
		videoRepository = VideoRepository.getUniqueInstance(); 
		labelRepository = LabelRepository.getUniqueInstance();
	}

}

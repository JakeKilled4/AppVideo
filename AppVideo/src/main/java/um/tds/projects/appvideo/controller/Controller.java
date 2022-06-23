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
import um.tds.projects.appvideo.view.MainWindow;
import um.tds.projects.appvideo.view.UpdateOption;
import umu.tds.componente.CargadorVideos;
import umu.tds.componente.FicheroIncorrectoException;
import umu.tds.componente.Videos;
import umu.tds.componente.VideosEvent;
import umu.tds.componente.VideosListener;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class Controller implements VideosListener{

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
	
	// Text searched by user
	private String searchedTitle;
	
	// List of labels selected by the user to search
	private List<Label> selectedLabels;
	
	private User currentUser;
	private MainWindow mainWindow;
	
	private Controller() {
		initializeAdapters();
		initializeRepositories();
		this.currentUser    = null;
		this.searchedTitle  = "";
		this.selectedLabels = new LinkedList<Label>();
	}
	
	public static Controller getUniqueInstance() {
		if (instance == null)
			instance = new Controller();
		return instance;
	}
	
	/* Return true if the current user ages is less than 18 and false in other case */
	public boolean underEighteen() {
		
		// Get day of birth of the user
		Calendar a = Calendar.getInstance();
		a.setTime(currentUser.getDateOfBirth());
		
		// Get actual day
		Calendar b = Calendar.getInstance();
		
		// Calculate the difference between two dates
	    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
	    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
	        (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff < 18;
	}
	
	/* Return true if the video is in some playlist of the actual user */
	public boolean videoInSomePlaylist(Video v) {
		for(Playlist p : currentUser.getPlaylists()) {
			if(p.containsVideo(v)) return true;
		}
		return false;
	}
	
	public boolean userIsPremium() {
		return currentUser.isPremium();
	}
	
	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public List<Label> getSelectedLabels(){
		return selectedLabels;
	}
	
	public void setSelectedLabel(List<String> l) {
		
		// Transform the list of strings in a list of labels
		selectedLabels = l.stream().map(s -> new Label(s)).collect(Collectors.toList());
	}
	
	public String getSearchTitle(){
		return searchedTitle;
	}
	
	/* Load SearchPanel and filter videos with title: text */ 
	public void searchVideos(String text) {
		searchedTitle = text;
		mainWindow.activateSearchPanel(UpdateOption.CENTER);
	}
	
	public List<Video> getMostRecentVideos() {
		return this.currentUser.getHistory();
	}
	
	public List<Video> getMostPopularVideos(int n) {
		return videoRepository.getMostPopularVideos(n);
	}
	
	public List<Video> getSearchedVideos() {
		return videoRepository.findVideo(searchedTitle, currentUser.getFilter(), selectedLabels);
	}
	
	/* Function that runs when the user load the videos from a file*/
	@Override
	public void hayNuevosVideos(EventObject arg) {
		
		// Get the list of videos that the component return
		VideosEvent e = (VideosEvent)arg;
		Videos videos =  e.getVideos();
		
		// Iterate over the list of videos
		for(umu.tds.componente.Video v : videos.getVideo()) {
			Video video;
			boolean videoExits = false;
			
			// If the video is in the repository update it (remove and add again) else create new one
			if(videoRepository.containsVideo(v.getURL())) {
				video = videoRepository.getVideo(v.getURL());
				videoRepository.removeVideo(video);
				videoExits = true;
			}
			else video = new Video(v.getURL(), v.getTitulo(), 0);
			
			// Iterate over the labels of the actual video we want to add
			for(String l : v.getEtiqueta()) {
				Label label;
				
				// If the label exists in the repository get from it else add to it
				if(labelRepository.containsLabel(l)) 
					label = labelRepository.getLabel(l);
				else {
					label = new Label(l);
					labelRepository.addLabel(label);
					labelAdapter.registerLabel(label);
				}
				
				// Add the label to the video
				video.addLabel(label);
			}
			videoRepository.addVideo(video);
			
			// If the video was in the repository, just modify else register the new video
			if(videoExits) videoAdapter.modifyVideo(video);
			else videoAdapter.registerVideo(video);
		}
	}
	
	/* Function called by graphical interface to select a xml file to load videos */
	public void selectFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			CargadorVideos cargadorVideos = new CargadorVideos();
			
			// Add the controller as a listener
			cargadorVideos.addVideosListener(this);
			try {
				
				// Call the listeners (only the controller) to execute function hayNuevosVideos
				cargadorVideos.cargarVideos(selectedFile);
			} catch (FicheroIncorrectoException e) {
				 mainWindow.showPopUp("Error","Invalid file",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public boolean userIsRegistered(String username) {
		return userRepository.containsUser(username);
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}

	/* Return true iff username was in the user db and the password is correct */
	public boolean login(String username, String password) {
		User u = userRepository.getUser(username);
		if (u != null && u.checkPassword(password)) {
			currentUser = u;
			logger.info("User login successful");
			return true;
		} 
		logger.info("User login unsuccessful");
		return false;
	}

	
	public void logout() {
		currentUser    = null;
		searchedTitle  = "";
		selectedLabels = new LinkedList<Label>();
		logger.info("User logged out");
	}
	
	/* Returns true iff the username was not already taken */
	public boolean changeUserData(String name, String surname, Date dateOfBirth, String email, String username, String password, boolean isPremium, String filter) {
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
		this.currentUser.setFilter(IVideoFilter.makeFilter(filter));
	
		userRepository.addUser(this.currentUser);
		userAdapter.modifyUser(this.currentUser);
		return true;
	}
	
	/* Returns true iff the username was not already taken */
	public boolean register(String name, String surname, Date dateOfBirth, String email, String username, String password) {
		if (userRepository.containsUser(username))
			return false;
		
		currentUser = new User(name, surname, dateOfBirth, email, username, password);
		userAdapter.registerUser(currentUser);
		userRepository.addUser(currentUser);
		return true;
	}

	/* Returns true iff the playlist name was not already taken*/
	public boolean createPlaylist(String name) {
		boolean created = currentUser.createPlaylist(name);
		if (created) { 
			logger.info("Creating the playlist '" + name + "'");
			userAdapter.modifyUser(currentUser);
		}
		return created;
	}

	public void removePlaylist(Playlist playlist) {
		logger.info("Removing the playlist '" + playlist.getName() + "'");
		currentUser.removePlaylist(playlist);
		userAdapter.modifyUser(currentUser);
	}

	/* Returns true iff the videos was not already in the playlist */
	public boolean addVideoToPlaylist(Playlist playlist, Video video) {
		if (playlist.addVideo(video)) {
			logger.info("Adding video with title '" + video.getTitle() + "' to playlist '"+ playlist.getName() +"'" );
			playlistAdapter.modifyPlaylist(playlist);
			return true;
		}
		return false;
	}

	public void removeVideoFromPlaylist(Playlist playlist, Video video) {
		logger.info("Removing video with title '" + video.getTitle() + "' of the playlist '"+ playlist.getName() +"'" );
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

	/* Add a view to the video and add the video to the history of the user */
	public void viewVideo(Video v) {
		v.addView();
		videoAdapter.modifyVideo(v);
		currentUser.addBeginningHistory(v);
		userAdapter.modifyUser(currentUser);
	}
	
	/* Return null if the label was already in the video */
	public Label addLabelToVideo(Video v,String name) {
		
		// Try to get the label from the repository, else create new one
		Label l = labelRepository.getLabel(name);
		if(l == null) {
			l = new Label(name);
			labelAdapter.registerLabel(l);
			labelRepository.addLabel(l);
		}
		
		// If the label is not in the video
		if(v.addLabel(l)) {
			videoAdapter.modifyVideo(v);
			return l;
		}
		return null;
	}
	
	/* Generate a PDF with the personal data of the current user and the data of his playlists */
	public void generatePdf() {      
		Document document = new Document();
        try{
		    PdfWriter.getInstance(document, new FileOutputStream("Playlists" + currentUser.getUsername() +".pdf"));
		    document.open();
		    
		    // Personal data information to export
		    Paragraph title1 = new Paragraph("Personal data", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 18, Font.BOLD, BaseColor.BLACK));
		    Chapter chapter1 = new Chapter(title1, 1);
		    currentUser.dataTopPdf(chapter1);
		    
		    // Playlist data to export
		    Paragraph title2 = new Paragraph("Playlists", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 18, Font.BOLD, BaseColor.BLACK));
		    Chapter chapter2 = new Chapter(title2, 2);
		    currentUser.playListsToPdf(chapter2);
		
		    document.add(chapter1);
		    document.add(chapter2);
		    document.close();
		    
			mainWindow.showPopUp("Information", "Pdf generated correctly with name: "+"Playlists" + currentUser.getUsername() +".pdf", JOptionPane.INFORMATION_MESSAGE);
		    
        }
        catch (Exception e){
        	mainWindow.showPopUp("Error", "Error creating pdf", JOptionPane.ERROR_MESSAGE);
        }
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

}

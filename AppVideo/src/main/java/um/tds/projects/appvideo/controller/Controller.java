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
import java.text.SimpleDateFormat;
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
	
	// List of labels selected to search
	private List<Label> selectedLabels;
	
	// Current user
	private User currentUser;

	// Main window
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
		selectedLabels = l.stream().map(s -> new Label(s)).collect(Collectors.toList());
	}
	
	public String getSearchTitle(){
		return searchedTitle;
	}
	
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
		currentUser    = null;
		searchedTitle  = "";
		selectedLabels = new LinkedList<Label>();
	}
	
	/**
	 * Returns true iff the change process was successful (If the username was not already taken)
	 */
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
		if (created) 
			userAdapter.modifyUser(currentUser);
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

	public void viewVideo(Video v) {
		v.addView();
		videoAdapter.modifyVideo(v);
		currentUser.addBeginningHistory(v);
		userAdapter.modifyUser(currentUser);
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
	
	public void generatePdf() {      
		Document document = new Document();
        try{
		    PdfWriter.getInstance(document, new FileOutputStream("Playlists" + currentUser.getUsername() +".pdf"));
		    document.open();
		    
		    Paragraph title1 = new Paragraph("Personal data", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 18, Font.BOLD, BaseColor.BLACK));
		    Chapter chapter1 = new Chapter(title1, 1);
		    chapter1.add(new Paragraph("Name: "+currentUser.getName()));
		    if(currentUser.getSurname() != null && !currentUser.getSurname().isBlank()) 
		    	chapter1.add(new Paragraph("Surname: "+currentUser.getSurname()));
		    chapter1.add(new Paragraph("Username: "+currentUser.getUsername()));
		    chapter1.add(new Paragraph("Date of birth: " + new SimpleDateFormat("dd/MM/yyyy").format(currentUser.getDateOfBirth())));
		    if(currentUser.getEmail() != null && !currentUser.getEmail().isBlank()) 
		    	chapter1.add(new Paragraph("Email: "+currentUser.getEmail()));
		    
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

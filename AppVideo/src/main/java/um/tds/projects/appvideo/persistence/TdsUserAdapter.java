package um.tds.projects.appvideo.persistence;

import java.util.Date;
import java.util.HashSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.backend.filters.IVideoFilter;

public class TdsUserAdapter implements IUserAdapter {

	private static TdsUserAdapter       instance;
	private static Logger               logger = Logger.getLogger("um.tds.projects.appvideo.persistence.tdsuseradapter");
	private static ServicioPersistencia servPersistencia;
	
	private SimpleDateFormat   dateFormat;
	private TdsPlaylistAdapter playlistAdapter;
	private TdsVideoAdapter videoAdapter;

	public static TdsUserAdapter getUniqueInstance() {
		if (instance == null)
			instance = new TdsUserAdapter();
		return instance;
	}

	private TdsUserAdapter() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		playlistAdapter  = TdsPlaylistAdapter.getUniqueInstance();
		
		// To store the date
		dateFormat       = new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	public void registerUser(User u) {
		logger.info("Registering a new user");
		Entidad eUser = null;
		try {
			eUser = servPersistencia.recuperarEntidad(u.getCode());
		} catch (Error e) { }
		logger.info("... Correctly checked whether the user was already registered");
		if (eUser != null)
			return;

		// Register playlist
		for (Playlist playlist : u.getPlaylists())
			playlistAdapter.registerPlaylist(playlist);
		
		// Register videos of the history
		for(Video v : u.getHistory())
			videoAdapter.registerVideo(v);

		// Create user entity
		eUser = new Entidad();
		eUser.setNombre("user");
		
		// Register all properties
		eUser.setPropiedades(
			new ArrayList<Propiedad>(
				Arrays.asList(
					new Propiedad("name",                          u.getName()),
					new Propiedad("surname",                       u.getSurname()),
					new Propiedad("dateOfBirth", dateFormat.format(u.getDateOfBirth())),
					new Propiedad("email",                         u.getEmail()),
					new Propiedad("username",                      u.getUsername()),
					new Propiedad("password",                      u.getPassword()),
					new Propiedad("isPremium",      String.valueOf(u.isPremium())),
					new Propiedad("playlists",    getCodesPlaylist(u.getPlaylists())),
					new Propiedad("filter", IVideoFilter.getFilterName(u.getFilter())),
					new Propiedad("history", 	   getCodesHistory(u.getHistory()))
				)
			)
		);

		// Register user entity
		eUser = servPersistencia.registrarEntidad(eUser);
		
		// Unique identifier
		u.setCode(eUser.getId());

		logger.info("... User correctly registered");
	}

	@Override
	public void removeUser(User u) {
		
		// Remove the user's playlists
		for (Playlist playlist : u.getPlaylists())
			playlistAdapter.removePlaylist(playlist);
		
		Entidad eUser = servPersistencia.recuperarEntidad(u.getCode());
		servPersistencia.borrarEntidad(eUser);
	}

	@Override
	public void modifyUser(User u) {
		logger.info("Modifying an user");
		Entidad eUser;
		
		// Get the actual user data
		User oldUser = loadUser(u.getCode());
		
		// Modify playlist of the user
		modifyPlaylists(oldUser, u);

		eUser = servPersistencia.recuperarEntidad(u.getCode());
		
		// Modify all properties
		for (Propiedad prop: eUser.getPropiedades()) {
			modifyField(prop, "name",                          u.getName());
			modifyField(prop, "surname",                       u.getSurname());
			modifyField(prop, "dateOfBirth", dateFormat.format(u.getDateOfBirth()));
			modifyField(prop, "email",                         u.getEmail());
			modifyField(prop, "username",                      u.getUsername());
			modifyField(prop, "password",                      u.getPassword());
			modifyField(prop, "isPremium",   String.valueOf(   u.isPremium()));
			modifyField(prop, "playlists",   getCodesPlaylist( u.getPlaylists()));
			modifyField(prop, "filter",     				   IVideoFilter.getFilterName(u.getFilter()));
			modifyField(prop, "history", 	   				   getCodesHistory(u.getHistory()));
			servPersistencia.modificarPropiedad(prop);
		}
	}
	
	@Override
	public User loadUser(int code) {
		Entidad eUser = servPersistencia.recuperarEntidad(code);

		// Try to load the date stored
		Date dateOfBirth = null;
		try {
			dateOfBirth = (Date) dateFormat.parse(getFieldValue(eUser, "dateOfBirth"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Create new user with all properties
		User user = new User(
			getFieldValue(eUser, "name"),
			getFieldValue(eUser, "surname"),
			dateOfBirth,
			getFieldValue(eUser, "email"),
			getFieldValue(eUser, "username"),
			getFieldValue(eUser, "password")
		);
	
		user.setPremium(
			Boolean.parseBoolean(getFieldValue(eUser, "isPremium"))
		);
		user.setCode(code);

		// Load playlists and add playlist to the user
		List<Playlist> playlists = getPlaylistsFromCodes(getFieldValue(eUser, "playlists"));
		
		for (Playlist playlist : playlists)
			user.addPlaylist(playlist);

		// Load history and add to the user
		List<Video> history = getHistoryFromCodes(getFieldValue(eUser, "history"));
		user.setHistory(history);
		
		// Set filter to the user
		String filterName = getFieldValue(eUser, "filter");
		IVideoFilter filter = IVideoFilter.makeFilter(filterName);
		user.setFilter(filter);
		
		return user;
	}

	@Override
	public List<User> loadAllUsers() {
		List<User>    users    = new LinkedList<User>();
		List<Entidad> entities = servPersistencia.recuperarEntidades("user");

		for (Entidad entity : entities) {
			User u = loadUser(entity.getId());
			for (Playlist pl: u.getPlaylists())
				logger.info(pl.getName());
			users.add(u);
		}

		return users;
	}

	/* Private functions */
	
	/* Removes the deleted playlists and registers the new ones. */
	private void modifyPlaylists(User oldUser, User newUser) {
		// We will store the playlists in two hash sets for rapidly
		// computing whether some playlist belongs to both users.
		Set<Integer> oldPl = new HashSet<Integer>();
		Set<Integer> newPl = new HashSet<Integer>();

		// Populate the sets with each users's playlists.
		for (Playlist pl: oldUser.getPlaylists())
			oldPl.add(pl.getCode());
		for (Playlist pl: newUser.getPlaylists())
			newPl.add(pl.getCode());
		
		// Register the added playlists
		for (Playlist pl: newUser.getPlaylists()) {
			if (!oldPl.contains(pl.getCode())) {
				playlistAdapter.registerPlaylist(pl);
			}
		}
		// Remove the deleted playlists
		for (Playlist pl: oldUser.getPlaylists())
			if (!newPl.contains(pl.getCode()))
				playlistAdapter.removePlaylist(pl);
	}

	private String getFieldValue(Entidad entity, String field) {
		return servPersistencia.recuperarPropiedadEntidad(entity, field);
	}
	
	private void modifyField(Propiedad prop, String fieldName, String newValue) {
		if (prop.getNombre().equals(fieldName))
			prop.setValor(newValue);
	}
	
	private String getCodesPlaylist(List<Playlist> playlist) {
		String plays = "";
		for (Playlist list : playlist)
			plays += String.valueOf(list.getCode()) + " ";
		return plays.trim();
	}
	
	private String getCodesHistory(List<Video> history) {
		String videos = "";
		for (Video list : history)
			videos += String.valueOf(list.getCode()) + " ";
		return videos.trim();
	}

	private List<Playlist> getPlaylistsFromCodes(String playlists) {
		List<Playlist>     playlistList    = new LinkedList<Playlist>();
		StringTokenizer    strTok          = new StringTokenizer(playlists, " ");
		TdsPlaylistAdapter playlistAdapter = TdsPlaylistAdapter.getUniqueInstance();
		while (strTok.hasMoreTokens()) {
			playlistList.add(
				playlistAdapter.loadPlaylist(
					Integer.valueOf(
						(String) strTok.nextElement()
					)
				)
			);
		}
		return playlistList;
	}
	
	private List<Video> getHistoryFromCodes(String history) {
		List<Video>     historyList    = new LinkedList<Video>();
		StringTokenizer    strTok          = new StringTokenizer(history, " ");
		TdsVideoAdapter videoAdapter = TdsVideoAdapter.getUniqueInstance();
		while (strTok.hasMoreTokens()) {
			historyList.add(
				videoAdapter.loadVideo(
					Integer.valueOf(
						(String) strTok.nextElement()
					)
				)
			);
		}
		return historyList;
	}
}

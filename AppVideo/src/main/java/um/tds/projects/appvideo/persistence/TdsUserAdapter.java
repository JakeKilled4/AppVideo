package um.tds.projects.appvideo.persistence;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.backend.filters.IVideoFilter;

public class TdsUserAdapter implements IUserAdapter {

	private static TdsUserAdapter       instance;
	private static Logger               logger = Logger.getLogger("um.tds.projects.appvideo.persistence.tdsuseradapter");
	private static ServicioPersistencia servPersistencia;
	
	private SimpleDateFormat   dateFormat; // Format of the date in the DB
	private TdsPlaylistAdapter playlistAdapter;
	private TdsFilterAdapter   filterAdapter;

	public static TdsUserAdapter getUniqueInstance() {
		if (instance == null)
			instance = new TdsUserAdapter();
		return instance;
	}

	private TdsUserAdapter() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		playlistAdapter  = TdsPlaylistAdapter.getUniqueInstance();
		filterAdapter    = TdsFilterAdapter.getUniqueInstance();
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

		// Register videoFilter
		for (IVideoFilter filter : u.getFilters())
			filterAdapter.registerFilter(filter);

		// Create user entity
		eUser = new Entidad();
		eUser.setNombre("user");
		eUser.setPropiedades(
			new ArrayList<Propiedad>(
				Arrays.asList(
					new Propiedad("name",                          u.getName()),
					new Propiedad("surname",                       u.getSurname()),
					new Propiedad("dateOfBirth", dateFormat.format(u.getDateOfBirth())),
					new Propiedad("email",                         u.getEmail()),
					new Propiedad("username",                      u.getUsername()),
					new Propiedad("password",                      u.getPassword()),
					new Propiedad("isPremium",   String.valueOf(   u.isPremium())),
					new Propiedad("playlists",   getCodesPlaylist( u.getPlaylists())),
					new Propiedad("filters",     getCodesFilters(  u.getFilters()))
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
			playlistAdapter.removeLPlaylist(playlist);
		
		// Remove the user's filters
		for (IVideoFilter filter : u.getFilters())
			filterAdapter.removeFilter(filter);

		Entidad eUser = servPersistencia.recuperarEntidad(u.getCode());
		servPersistencia.borrarEntidad(eUser);
	}

	@Override
	public void modifyUser(User u) {
		Entidad eUser;
		eUser = servPersistencia.recuperarEntidad(u.getCode());
		modifyField(eUser, "name",                          u.getName());
		modifyField(eUser, "surname",                       u.getSurname());
		modifyField(eUser, "dateOfBirth", dateFormat.format(u.getDateOfBirth()));
		modifyField(eUser, "email",                         u.getEmail());
		modifyField(eUser, "username",                      u.getUsername());
		modifyField(eUser, "password",                      u.getPassword());
		modifyField(eUser, "isPremium",   String.valueOf(   u.isPremium()));
		modifyField(eUser, "playlists",   getCodesPlaylist( u.getPlaylists()));
		modifyField(eUser, "filters",     getCodesFilters(  u.getFilters()));
	}
	
	private void modifyField(Entidad entity, String fieldName, String newValue) {
		servPersistencia.eliminarPropiedadEntidad(entity, fieldName);
		servPersistencia.anadirPropiedadEntidad  (entity, fieldName, newValue);
	}

	@Override
	public User loadUser(int code) {
		Entidad eUser = servPersistencia.recuperarEntidad(code);

		Date dateOfBirth = null;
		try {
			dateOfBirth = (Date) dateFormat.parse(getFieldValue(eUser, "dateOfBirth"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

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

		// Load playlists
		List<Playlist> playlists = getPlaylistsFromCodes(getFieldValue(eUser, "playlists"));
		for (Playlist playlist : playlists)
			user.addPlaylist(playlist);

		// Load filters
		List<IVideoFilter>filters = getFiltersFromCodes(getFieldValue(eUser, "filters"));
		for (IVideoFilter filter : filters)
			user.addFilter(filter);

		return user;
	}
	
	private String getFieldValue(Entidad entity, String field) {
		return servPersistencia.recuperarPropiedadEntidad(entity, field);
	}

	@Override
	public List<User> loadAllUsers() {
		List<User>    users    = new LinkedList<User>();
		List<Entidad> entities = servPersistencia.recuperarEntidades("user");

		for (Entidad entity : entities)
			users.add(loadUser(entity.getId()));

		return users;
	}

	/* Auxiliar functions */
	private String getCodesPlaylist(List<Playlist> playlist) {
		String plays = "";
		for (Playlist list : playlist)
			plays += String.valueOf(list.getCode()) + " ";
		return plays.trim();
	}

	private String getCodesFilters(List<IVideoFilter> playlist) {
		String out = "";
		for (IVideoFilter filt : playlist)
			out += String.valueOf(filt.getCode()) + " ";
		return out.trim();
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

	private List<IVideoFilter> getFiltersFromCodes(String filters) {
		List<IVideoFilter> filterList = new LinkedList<IVideoFilter>();
		StringTokenizer    strTok     = new StringTokenizer(filters, " ");
		while (strTok.hasMoreTokens()) {
			filterList.add(
				filterAdapter.loadFilter(
					Integer.valueOf(
						(String) strTok.nextElement()
					)
				)
			);
		}
		return filterList;
	}
}

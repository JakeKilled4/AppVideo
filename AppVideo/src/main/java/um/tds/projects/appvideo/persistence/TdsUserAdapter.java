package um.tds.projects.appvideo.persistence;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.backend.filters.IVideoFilter;

public class TdsUserAdapter implements IUserAdapter {

	private static TdsUserAdapter instance;
	private static ServicioPersistencia servPersistencia;
	private SimpleDateFormat dateFormat; // Format of the date in the DB

	public static TdsUserAdapter getUniqueInstance() {
		if (instance == null) instance = new TdsUserAdapter();
		return instance;
	}
	

	private TdsUserAdapter() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@Override
	public void registerUser(User u) {
		Entidad eUser = null;
		try {
			eUser = servPersistencia.recuperarEntidad(u.getCode());
		} catch (NullPointerException e) {}
		if (eUser != null)	return;
		
		// Register playlist
		TdsPlaylistAdapter playlistAdapter = TdsPlaylistAdapter.getUniqueInstance();
		for(Playlist playlist : u.getPlaylists())
			playlistAdapter.registerPlaylist(playlist);
		
		// Register videoFilter
		TdsFilterAdapter filterAdpater = TdsFilterAdapter.getUniqueInstance();
		for(IVideoFilter filter : u.getFilters())
			filterAdpater.registerFilter(filter);
		
		
		// Create user entity
		eUser = new Entidad();
				
		eUser.setNombre("user");
		eUser.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("name", u.getName()),
						new Propiedad("surname", u.getSurname()),
						new Propiedad("dateOfBirth", dateFormat.format(u.getDateOfBirth())),
						new Propiedad("email", u.getEmail()),
						new Propiedad("username", u.getUsername()),
						new Propiedad("password", u.getPassword()),
						new Propiedad("isPremium", String.valueOf(u.isPremium())),
						new Propiedad("playlists",getCodesPlaylist(u.getPlaylists())),
						new Propiedad("filters",getCodesFilters(u.getFilters())))));
		
		// Register user entity
		eUser = servPersistencia.registrarEntidad(eUser);

		// Unique identifier
		u.setCode(eUser.getId());
	}
	
	@Override
	public void removeUser(User u) {
		Entidad eUser;
		TdsPlaylistAdapter playlistAdapter = TdsPlaylistAdapter.getUniqueInstance();
		
		// Remove playlist in the user
		for(Playlist playlist : u.getPlaylists())
			playlistAdapter.removeLPlaylist(playlist);

		TdsFilterAdapter filterAdapter = TdsFilterAdapter.getUniqueInstance();
		
		// Remove filters in the user
		for(IVideoFilter filter : u.getFilters())
			filterAdapter.removeFilter(filter);
		
		eUser = servPersistencia.recuperarEntidad(u.getCode());
		servPersistencia.borrarEntidad(eUser);
	}
	
	@Override
	public void modifyUser(User u) {
		Entidad eUser;
		eUser = servPersistencia.recuperarEntidad(u.getCode());
		servPersistencia.eliminarPropiedadEntidad(eUser, "name");
		servPersistencia.anadirPropiedadEntidad(eUser, "name", u.getName());
		servPersistencia.eliminarPropiedadEntidad(eUser, "surname");
		servPersistencia.anadirPropiedadEntidad(eUser, "surname", u.getSurname());
		servPersistencia.eliminarPropiedadEntidad(eUser, "dateOfBirth");
		servPersistencia.anadirPropiedadEntidad(eUser, "dateOfBirth", dateFormat.format(u.getDateOfBirth()));
		servPersistencia.eliminarPropiedadEntidad(eUser, "email");
		servPersistencia.anadirPropiedadEntidad(eUser, "email", u.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eUser, "username");
		servPersistencia.anadirPropiedadEntidad(eUser, "username", u.getUsername());
		servPersistencia.eliminarPropiedadEntidad(eUser, "password");
		servPersistencia.anadirPropiedadEntidad(eUser, "password", u.getPassword());
		servPersistencia.eliminarPropiedadEntidad(eUser, "isPremium");
		servPersistencia.anadirPropiedadEntidad(eUser, "isPremium", String.valueOf(u.isPremium()));
		
		String playlists = getCodesPlaylist(u.getPlaylists());
		servPersistencia.eliminarPropiedadEntidad(eUser, "playlists");
		servPersistencia.anadirPropiedadEntidad(eUser, "playlists", playlists);
		
		String filters = getCodesFilters(u.getFilters());
		servPersistencia.eliminarPropiedadEntidad(eUser, "filters");
		servPersistencia.anadirPropiedadEntidad(eUser, "filters", filters);
	}
	
	@Override
	public User loadUser(int code) {
		
		Entidad eUser;
		String name, surname, email, username, password;
		boolean isPremium;
		Date dateOfBirth = null;
		
		List<Playlist> playlists = new LinkedList<Playlist>();
		List<IVideoFilter> filters = new LinkedList<IVideoFilter>();
		
		eUser = servPersistencia.recuperarEntidad(code);
		name = servPersistencia.recuperarPropiedadEntidad(eUser, "name");
		surname = servPersistencia.recuperarPropiedadEntidad(eUser, "surname");
		email = servPersistencia.recuperarPropiedadEntidad(eUser, "email");
		username = servPersistencia.recuperarPropiedadEntidad(eUser, "username");
		password = servPersistencia.recuperarPropiedadEntidad(eUser, "password");
		isPremium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUser, "isPremium"));
		try {
			dateOfBirth = (Date) dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUser, "dateOfBirth"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		User user = new User(name,surname, dateOfBirth, email, username, password);
		user.setPremium(isPremium);
		user.setCode(code);

		// Load playlists
		playlists = getPlaylistsFromCodes(servPersistencia.recuperarPropiedadEntidad(eUser, "playlists"));

		for (Playlist playlist : playlists)
			user.addPlaylist(playlist);
		
		// Load filters
		filters = getFiltersFromCodes(servPersistencia.recuperarPropiedadEntidad(eUser, "filters"));

		for (IVideoFilter filter : filters)
			user.addFilter(filter);
		
		return user;
	}
	
	@Override
	public List<User> loadAllUsers() {
		List<User> users = new LinkedList<User>();
		List<Entidad> eUsers = servPersistencia.recuperarEntidades("user");
		for (Entidad eUser : eUsers) users.add(loadUser(eUser.getId()));
		return users;
	}
	
	/* Auxiliar functions */
	private String getCodesPlaylist(List<Playlist> playlist) {
		String plays = "";
		for (Playlist list : playlist) plays += String.valueOf(list.getCode()) + " ";
		return plays.trim();
	}
	
	private String getCodesFilters(List<IVideoFilter> playlist) {
		String out = "";
		for (IVideoFilter filt : playlist) out += String.valueOf(filt.getCode()) + " ";
		return out.trim();
	}
	
	private List<Playlist> getPlaylistsFromCodes(String playlists) {

		List<Playlist> playlistList = new LinkedList<Playlist>();
		StringTokenizer strTok = new StringTokenizer(playlists, " ");
		TdsPlaylistAdapter playlistAdapter = TdsPlaylistAdapter.getUniqueInstance();
		while (strTok.hasMoreTokens()) {
			playlistList.add(playlistAdapter.loadPlaylist(Integer.valueOf((String) strTok.nextElement())));
		}
		return playlistList;
	}

	private List<IVideoFilter> getFiltersFromCodes(String filters) {

		List<IVideoFilter> filterList = new LinkedList<IVideoFilter>();
		StringTokenizer strTok = new StringTokenizer(filters, " ");
		TdsFilterAdapter filterAdapter = TdsFilterAdapter.getUniqueInstance();
		while (strTok.hasMoreTokens()) {
			filterList.add(filterAdapter.loadFilter(Integer.valueOf((String) strTok.nextElement())));
		}
		return filterList;
	}
}

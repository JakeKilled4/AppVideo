package um.tds.projects.appvideo.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


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
						new Propiedad("playlist",getCodesPlaylist(u.getPlaylists())),
						new Propiedad("filter",getCodesFilters(u.getFilters())))));
		
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
		
	}
	
	@Override
	public User loadUser(int id) {
		return null;
	}
	
	@Override
	public List<User> loadAllUsers() {
		return new LinkedList<User>();
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

}

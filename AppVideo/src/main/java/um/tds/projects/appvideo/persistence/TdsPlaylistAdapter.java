package um.tds.projects.appvideo.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
import um.tds.projects.appvideo.backend.Video;

public class TdsPlaylistAdapter implements IPlaylistAdapter {

	private static TdsPlaylistAdapter instance;
	private static ServicioPersistencia servPersistencia;
	private static Logger logger = Logger.getLogger("um.tds.projects.appvideo.persistence.tdsplaylistadapter");
	
	private TdsVideoAdapter videoAdapter;

	public static TdsPlaylistAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsPlaylistAdapter();
		}
		return instance;
	}
	
	private TdsPlaylistAdapter() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		videoAdapter     = TdsVideoAdapter.getUniqueInstance();
	}
	
	@Override
	public void registerPlaylist(Playlist p) {
		logger.info("Registering a new playlist");
		Entidad ePlaylist = null;
		try {
			ePlaylist = servPersistencia.recuperarEntidad(p.getCode());
		} catch (NullPointerException e) {}
		logger.info("... Correctly checked whether the playlist was already registered");
		if (ePlaylist != null)
			return;
		
		// Register Videos
		for (Video video : p.getVideos())
			videoAdapter.registerVideo(video);
		
		// Create video entity
		ePlaylist = new Entidad();
				
		ePlaylist.setNombre("playlist");
		ePlaylist.setPropiedades(
			new ArrayList<Propiedad>(
				Arrays.asList(
					new Propiedad("name",                  p.getName()),
					new Propiedad("videos", getCodesVideos(p.getVideos()))
				)
			)
		);
		
		// Register video entity
		ePlaylist = servPersistencia.registrarEntidad(ePlaylist);

		// Unique identifier
		p.setCode(ePlaylist.getId());
		
		logger.info("... Playlist correctly registered");
	}
	
	@Override
	public void removePlaylist(Playlist p) {
		servPersistencia.borrarEntidad(
			servPersistencia.recuperarEntidad(p.getCode())
		);
	}
	
	@Override
	public void modifyPlaylist(Playlist p) {
		Entidad ePlaylist = servPersistencia.recuperarEntidad(p.getCode());
		
		modifyVideos(loadPlaylist(p.getCode()), p);

		for (Propiedad prop: ePlaylist.getPropiedades()) {
			modifyField(prop, "name",                  p.getName());
			modifyField(prop, "videos", getCodesVideos(p.getVideos()));
			servPersistencia.modificarPropiedad(prop);
		}
	}
	
	@Override
	public Playlist loadPlaylist(int code) {
		Entidad ePlaylist = servPersistencia.recuperarEntidad(code);

		Playlist playlist = new Playlist(
			getFieldValue(ePlaylist, "name")
		);
		logger.info(playlist.getName());
		playlist.setCode(code);

		// Load all videos
		String videoCodes = getFieldValue(ePlaylist, "videos");
		if (videoCodes != null)
			for (Video video: getVideosFromCodes(videoCodes))
				playlist.addVideo(video);
		
		return playlist;
	}
	
	@Override
	public List<Playlist> loadAllPlaylists(){
		List<Playlist> playlists  = new LinkedList<Playlist>();
		List<Entidad>  ePlaylists = servPersistencia.recuperarEntidades("playlist");
		
		for (Entidad ePlaylist : ePlaylists)
			playlists.add(loadPlaylist(ePlaylist.getId()));
		
		return playlists;
	}
	
	/* Auxiliar functions */
	/* Removes the deleted videos and registers the new ones */
	private void modifyVideos(Playlist oldPl, Playlist newPl) {
		// We will store the videos in two hash sets for rapidly
		// computing whether some videos belongs to both playlists.
		Set<Integer> oldVideos = new HashSet<Integer>();

		// Populate the sets with each playlist's videos.
		for (Video v: oldPl.getVideos())
			oldVideos.add(v.getCode());
		
		// Register the added videos, remove the deleted ones.
		for (Video v: newPl.getVideos())
			if (!oldVideos.contains(v.getCode()))
				videoAdapter.registerVideo(v);
	}
	
	private String getCodesVideos(List<Video> videos) {
		String out = "";
		for (Video video : videos)
			out += String.valueOf(video.getCode()) + " ";
		return out.trim();
	}
	
	private List<Video> getVideosFromCodes(String videos) {
		logger.info(videos);
		List<Video> videoList = new LinkedList<Video>();
		StringTokenizer strTok = new StringTokenizer(videos, " ");
		while (strTok.hasMoreTokens()) {
			videoList.add(
				videoAdapter.loadVideo(
					Integer.valueOf(
						(String) strTok.nextElement()
					)
				)
			);
		}
		return videoList;
	}
	

	private void modifyField(Propiedad prop, String fieldName, String newValue) {
		if (prop.getNombre().equals(fieldName))
			prop.setValor(newValue);
	}
	

	private String getFieldValue(Entidad entity, String field) {
		return servPersistencia.recuperarPropiedadEntidad(entity, field);
	}
}

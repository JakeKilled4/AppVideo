package um.tds.projects.appvideo.persistence;

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
import um.tds.projects.appvideo.backend.Video;

public class TdsPlaylistAdapter implements IPlaylistAdapter {

	private static TdsPlaylistAdapter instance;
	private static ServicioPersistencia servPersistencia;
	
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
		
		Entidad ePlaylist = null;
		try {
			ePlaylist = servPersistencia.recuperarEntidad(p.getCode());
		} catch (NullPointerException e) {}
		if (ePlaylist != null)	return;
		
		// Register Videos
		for(Video video : p.getVideos())
			videoAdapter.registerVideo(video);
		
		// Create video entity
		ePlaylist = new Entidad();
				
		ePlaylist.setNombre("playlist");
		ePlaylist.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("name", p.getName()),
						new Propiedad("videos",getCodesVideos(p.getVideos())))));
		
		// Register video entity
		ePlaylist = servPersistencia.registrarEntidad(ePlaylist);

		// Unique identifier
		p.setCode(ePlaylist.getId());
	}
	
	@Override
	public void removeLPlaylist(Playlist p) {
		Entidad ePlaylist;
		
		// Remove videos in the playlist
		for(Video video : p.getVideos())
			videoAdapter.removeVideo(video);

		servPersistencia.borrarEntidad(
			servPersistencia.recuperarEntidad(p.getCode())
		);
	}
	
	@Override
	public void modifyPlaylist(Playlist p) {
		Entidad ePlaylist = servPersistencia.recuperarEntidad(p.getCode());
		modifyField(ePlaylist, "name",                  p.getName());
		modifyField(ePlaylist, "videos", getCodesVideos(p.getVideos()));
	}
	
	@Override
	public Playlist loadPlaylist(int code) {
		Entidad ePlaylist = servPersistencia.recuperarEntidad(code);

		Playlist playlist = new Playlist(
			getFieldValue(ePlaylist, "name")
		);
		playlist.setCode(code);

		// Load all videos
		List<Video> videos = getVideosFromCodes(servPersistencia.recuperarPropiedadEntidad(ePlaylist, "videos"));
		for (Video video : videos)
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
	private String getCodesVideos(List<Video> videos) {
		String out = "";
		for (Video video : videos)
			out += String.valueOf(video.getCode()) + " ";
		return out.trim();
	}
	
	private List<Video> getVideosFromCodes(String videos) {

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
	

	private void modifyField(Entidad entity, String fieldName, String newValue) {
		servPersistencia.eliminarPropiedadEntidad(entity, fieldName);
		servPersistencia.anadirPropiedadEntidad  (entity, fieldName, newValue);
	}
	

	private String getFieldValue(Entidad entity, String field) {
		return servPersistencia.recuperarPropiedadEntidad(entity, field);
	}
}

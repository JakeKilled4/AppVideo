package um.tds.projects.appvideo.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.Video;

public class TdsPlaylistAdapter implements IPlaylistAdapter {

	private static TdsPlaylistAdapter instance;
	private static ServicioPersistencia servPersistencia;

	public static TdsPlaylistAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsPlaylistAdapter();
		}
		return instance;
	}
	
	private TdsPlaylistAdapter() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void registerPlaylist(Playlist p) {
		
		Entidad ePlaylist = null;
		try {
			ePlaylist = servPersistencia.recuperarEntidad(p.getCode());
		} catch (NullPointerException e) {}
		if (ePlaylist != null)	return;
		
		// Register Videos
		TdsVideoAdapter videoAdapter = TdsVideoAdapter.getUniqueInstance();
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
		TdsVideoAdapter videoAdapter = TdsVideoAdapter.getUniqueInstance();
		
		// Remove videos in the playlist
		for(Video video : p.getVideos())
			videoAdapter.removeVideo(video);

		ePlaylist = servPersistencia.recuperarEntidad(p.getCode());
		servPersistencia.borrarEntidad(ePlaylist);
	}
	
	@Override
	public void modifyPlaylist(Playlist p) {
		
	}
	
	@Override
	public Playlist loadPlaylist(int id) {
		return null;
	}
	
	@Override
	public List<Playlist> loadAllPlaylists(){
		return null;
	}
	
	/* Auxiliar functions */
	private String getCodesVideos(List<Video> videos) {
		String out = "";
		for (Video video : videos) out += String.valueOf(video.getCode()) + " ";
		return out.trim();
	}
}

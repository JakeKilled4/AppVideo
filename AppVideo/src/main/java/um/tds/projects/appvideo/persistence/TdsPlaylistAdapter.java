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
		Entidad ePlaylist;
		ePlaylist = servPersistencia.recuperarEntidad(p.getCode());
		servPersistencia.eliminarPropiedadEntidad(ePlaylist, "name");
		servPersistencia.anadirPropiedadEntidad(ePlaylist, "name", p.getName());
		String videos = getCodesVideos(p.getVideos());
		servPersistencia.eliminarPropiedadEntidad(ePlaylist, "videos");
		servPersistencia.anadirPropiedadEntidad(ePlaylist, "videos", videos);
	}
	
	@Override
	public Playlist loadPlaylist(int code) {
		Entidad ePlaylist;
		String name;
		List<Video> videos = new LinkedList<Video>();

		ePlaylist = servPersistencia.recuperarEntidad(code);
		name = servPersistencia.recuperarPropiedadEntidad(ePlaylist, "name");

		Playlist playlist = new Playlist(name);
		playlist.setCode(code);

		// Load all videos
		videos = getVideosFromCodes(servPersistencia.recuperarPropiedadEntidad(ePlaylist, "videos"));

		for (Video video : videos)
			playlist.addVideo(video);
		
		return playlist;
	}
	
	@Override
	public List<Playlist> loadAllPlaylists(){
		List<Playlist> playlists = new LinkedList<Playlist>();
		List<Entidad> ePlaylists = servPersistencia.recuperarEntidades("playlist");
		for (Entidad ePlaylist : ePlaylists) playlists.add(loadPlaylist(ePlaylist.getId()));
		return playlists;
	}
	
	/* Auxiliar functions */
	private String getCodesVideos(List<Video> videos) {
		String out = "";
		for (Video video : videos) out += String.valueOf(video.getCode()) + " ";
		return out.trim();
	}
	
	private List<Video> getVideosFromCodes(String videos) {

		List<Video> videoList = new LinkedList<Video>();
		StringTokenizer strTok = new StringTokenizer(videos, " ");
		TdsVideoAdapter videoAdapter = TdsVideoAdapter.getUniqueInstance();
		while (strTok.hasMoreTokens()) {
			videoList.add(videoAdapter.loadVideo(Integer.valueOf((String) strTok.nextElement())));
		}
		return videoList;
	}
}

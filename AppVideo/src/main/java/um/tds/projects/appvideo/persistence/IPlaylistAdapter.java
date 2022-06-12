package um.tds.projects.appvideo.persistence;

import java.util.List;

import um.tds.projects.appvideo.backend.Playlist;

public interface IPlaylistAdapter { 
	public void registerPlaylist(Playlist p);
	public void removePlaylist(Playlist p);
	public void modifyPlaylist(Playlist p);
	public Playlist loadPlaylist(int code);
	public List<Playlist> loadAllPlaylists();
	
}

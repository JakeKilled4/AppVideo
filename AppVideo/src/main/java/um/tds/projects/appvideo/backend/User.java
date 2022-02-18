package um.tds.projects.appvideo.backend;

import um.tds.projects.appvideo.backend.filters.IVideoFilter;

import java.util.ArrayList;
import java.util.List;

public class User extends Identifiable {

	private String name;
	private String email;
	private boolean isPremium;
	private List<Playlist> playlists;
	private List<IVideoFilter> filters;

	public User(String name, String email, boolean isPremium) {
		this.name = name;
		this.email = email;
		this.isPremium = isPremium;
		this.playlists = new ArrayList<Playlist>();
		this.filters = new ArrayList<IVideoFilter>();
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void addPlaylist(Playlist pl) {
		playlists.add(pl);
	}

	public void removePlaylist(Playlist pl) {
		playlists.remove(pl);
	}

	public void addFilter(IVideoFilter fl) {
		filters.add(fl);
	}

	public void removeFilter(IVideoFilter fl) {
		filters.remove(fl);
	}

}

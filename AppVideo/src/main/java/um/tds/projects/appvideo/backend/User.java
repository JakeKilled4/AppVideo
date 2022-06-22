package um.tds.projects.appvideo.backend;

import um.tds.projects.appvideo.backend.filters.IVideoFilter;
import um.tds.projects.appvideo.backend.filters.NoFilter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User extends Identifiable {
	private String name;
	private String surname;
	private Date dateOfBirth;
	private String email;
	private String username;
	private String password;
	private boolean isPremium;
	private List<Playlist> playlists;
	private IVideoFilter filter;

	public User(String name, String surname, Date dateOfBirth, String email, String username, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.username = username;
		this.password = password;
		this.isPremium = false;
		this.playlists = new LinkedList<Playlist>();
		this.filter = new NoFilter();
	}
	
	public IVideoFilter getFilter() {
		return this.filter;
	}

	public void setFilter(IVideoFilter filter) {
		this.filter = filter;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean createPlaylist(String name) {
		for (Playlist p: playlists)
			if (name.equals(p.getName()))
				return false;

		playlists.add(
			new Playlist(name)
		);

		return true;
	}

	public void addPlaylist(Playlist pl) {
		playlists.add(pl);
	}

	public void removePlaylist(Playlist pl) {
		playlists.remove(pl);
	}

	public boolean checkPassword(String _password) {
		return password.equals(_password);
	}

}
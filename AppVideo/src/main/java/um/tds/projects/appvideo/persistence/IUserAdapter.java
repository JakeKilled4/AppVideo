package um.tds.projects.appvideo.persistence;

import java.util.List;

import um.tds.projects.appvideo.backend.User;

public interface IUserAdapter {
	public void registerUser(User u);
	public void removeUser(User u);
	public void modifyUser(User u);
	public User loadUser(int id);
	public List<User> loadAllUsers();
}

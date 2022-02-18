package um.tds.projects.appvideo.persistence;

import java.util.List;

import um.tds.projects.appvideo.backend.User;

public interface IUserAdapter {
	public List<User> loadAllUsers();
}

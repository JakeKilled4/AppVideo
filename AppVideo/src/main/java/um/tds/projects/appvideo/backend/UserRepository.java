package um.tds.projects.appvideo.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import um.tds.projects.appvideo.persistence.DaoException;
import um.tds.projects.appvideo.persistence.DaoFactory;
import um.tds.projects.appvideo.persistence.IUserAdapter;

public class UserRepository {

	private static UserRepository instance;

	private Map<String, User> cache;
	private DaoFactory dao;
	private IUserAdapter userAdapter;

	private UserRepository() {
		try {
			dao = DaoFactory.getUniqueInstance();
			userAdapter = dao.getUserAdapter();
			cache = new HashMap<String, User>();
			loadRepository();
	   	} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void loadRepository() throws DaoException {
		List<User> users = userAdapter.loadAllUsers();
		for (User u: users) {
			cache.put(u.getUsername(), u);
		}
	}

	public static UserRepository getUniqueInstance() {
		if (instance == null) instance = new UserRepository();
		return instance;
	}
	
	public void addUser(User u) {
		if(!cache.containsKey(u.getUsername())) cache.put(u.getUsername(), u);
	}
	
	public void removeUser(User u) {
		if(cache.containsKey(u.getUsername())) cache.remove(u.getUsername());
	}
	
	public User getUser(String username) {
		if(cache.containsKey(username)) return cache.get(username);
		return null;
	}
	
	public boolean containsUser(String username) {
		return cache.containsKey(username);
	}

	public void findUser() {
		return;
	}
}

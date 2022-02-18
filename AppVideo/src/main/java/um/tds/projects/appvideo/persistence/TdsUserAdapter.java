package um.tds.projects.appvideo.persistence;

import java.util.LinkedList;
import java.util.List;

import um.tds.projects.appvideo.backend.User;

public class TdsUserAdapter implements IUserAdapter {

	private static TdsUserAdapter instance;

	private TdsUserAdapter() { }

	public static TdsUserAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsUserAdapter();
		}
		return instance;
	}

	public List<User> loadAllUsers() {
		return new LinkedList<User>();
	}

}

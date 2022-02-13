package um.tds.projects.appvideo.backend;


public class UserRepository {

	private UserRepository instance;

	private UserRepository() { }

	public UserRepository getUniqueInstance() {
		if (instance == null) {
			instance = new UserRepository();
		}
		return instance;
	}

	public void addUser() {
		return;
	}

	public void removeUser() {
		return;
	}

	public void findUser() {
		return;
	}
}

package um.tds.projects.appvideo.backend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class UserRepositoryTest {

	User           user;
	UserRepository repo;
/*
	@Before
	public void loadRepository() {
		repo = UserRepository.getUniqueInstance();
		user = new User(
			"name",
			"surname",
			new Date(),
			"email",
			"username",
			"password"
		);
	}

	@Test
	public void canRemoveUser() {
		assertFalse(repo.containsUser(user.getUsername()));
		repo.addUser   (user);
		assertTrue (repo.containsUser(user.getUsername()));
		repo.removeUser(user);
		assertFalse(repo.containsUser(user.getUsername()));
	}*/
}

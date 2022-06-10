package um.tds.projects.appvideo.backend;

//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

import org.junit.Before;
//import org.junit.Test;

public class UserRepositoryTest {

	UserRepository repo;

	@Before
	public void loadRepository() {
		repo = UserRepository.getUniqueInstance();
	}
/*
	@Test
	public void canStoreAndRetrieveUser() {
		User u1 = new User(null, null, false);
		repo.addUser(u1);
		User u2 = repo.getUser(u1.getId());
		assertTrue(u1 == u2);
	}

	@Test
	public void canRemoveUser() {
		User u = new User(null, null, false);
		assertFalse(repo.containsUser(u.getId()));
		repo.addUser(u);
		assertTrue(repo.containsUser(u.getId()));
		repo.removeUser(u);
		assertFalse(repo.containsUser(u.getId()));
	}*/
}

package um.tds.projects.appvideo.persistence;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.User;

public class TdsUserAdapterTest {
	
	private static TdsUserAdapter userAdpt;
	private static User           user;
	
	@BeforeClass
	public static void setup() {
		userAdpt = TdsUserAdapter.getUniqueInstance();
	}
	
	@Before
	public void before() {
		user = new User("a", "b", new Date(), "c", "d", "e");
		userAdpt.registerUser(user);
	}
	
	@After
	public void after() {
		userAdpt.removeUser(user);
	}

	@Test
	public void canStoreAndDeleteUser() {
		User u2 = userAdpt.loadUser(user.getCode());
		assertEquals(user.getCode(), u2.getCode());
	}
	
	@Test
	public void canStoreName() {
		User u2 = userAdpt.loadUser(user.getCode());
		assertEquals(user.getName(), u2.getName());
	}
	
	@Test
	public void canModifyName() {
		user.setName("aaaa");
		userAdpt.modifyUser(user);
		User u2 = userAdpt.loadUser(user.getCode());
		assertEquals("aaaa", u2.getName());
	}
	
	@Test
	public void canStorePlaylist() {
		User     u1 = new User("aa", "bb", new Date(), "cc", "dd", "ee");
		Playlist p1 = new Playlist("p1");
		u1.addPlaylist(p1);
		
		userAdpt.registerUser(u1);
		User     u2 = userAdpt.loadUser(u1.getCode());
		assertEquals(1, u2.getPlaylists().size());
		Playlist p2 = u2.getPlaylists().get(0);
		assertEquals(p1.getCode(), p2.getCode());;
		
		userAdpt.removeUser(u1);
	}
	
	@Test
	public void canStorePlaylistName() {
		User     u1 = new User("aa", "bb", new Date(), "cc", "dd", "ee");
		Playlist p1 = new Playlist("p1");
		u1.addPlaylist(p1);
		
		userAdpt.registerUser(u1);
		User     u2 = userAdpt.loadUser(u1.getCode());
		assertEquals(1, u2.getPlaylists().size());
		Playlist p2 = u2.getPlaylists().get(0);
		assertEquals("p1", p2.getName());;
		
		userAdpt.removeUser(u1);
	}
	
	@Test
	public void canStoreMultiplePlaylists() {
		User     u1 = new User("aa", "bb", new Date(), "cc", "dd", "ee");
		Playlist p1 = new Playlist("p1");
		Playlist p2 = new Playlist("p2");
		Playlist p3 = new Playlist("p3");
		u1.addPlaylist(p1);
		u1.addPlaylist(p2);
		u1.addPlaylist(p3);
		
		userAdpt.registerUser(u1);
		User u2 = userAdpt.loadUser(u1.getCode());
		
		assertEquals(3,    u2.getPlaylists().size());
		assertEquals("p1", u2.getPlaylists().get(0).getName());
		assertEquals("p2", u2.getPlaylists().get(1).getName());
		assertEquals("p3", u2.getPlaylists().get(2).getName());
		
		userAdpt.removeUser(u1);
	}
	
	@Test
	public void canAddMultiplePlaylists() {
		user.addPlaylist(new Playlist("p1"));
		user.addPlaylist(new Playlist("p2"));
		userAdpt.modifyUser(user);
		
		User u2 = userAdpt.loadUser(user.getCode());
		
		assertEquals(2,    u2.getPlaylists().size());
		assertEquals("p1", u2.getPlaylists().get(0).getName());
		assertEquals("p2", u2.getPlaylists().get(1).getName());
	}
}
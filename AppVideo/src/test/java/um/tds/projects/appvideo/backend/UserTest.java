package um.tds.projects.appvideo.backend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserTest {
	
	private User user;
	
	@Before
	public void before() {
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
	public void canCreateMultiplePlaylists() {
		final int NUM_PLAYLISTS = 100;
		for (int i = 0; i < NUM_PLAYLISTS; i++) {
			user.createPlaylist(String.valueOf(i));
		}
		List<Playlist> playlists = user.getPlaylists();
		for (int i = 0; i < NUM_PLAYLISTS; i++) {
			boolean found = false;
			for (Playlist pl: playlists) {
				if (pl.getName().equals(String.valueOf(i))) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		}
	}
	
	@Test
	public void canCreateASinglePlaylist() {
		user.createPlaylist("a");
		String name = user.getPlaylists().get(0).getName();
		assertTrue(name.equals("a"));
	}
	
	@Test
	public void correctlyChecksThePassword() {
		assertTrue (user.checkPassword("password"));
		assertFalse(user.checkPassword("asdflj"));
	}
	
	@Test
	public void cannotCreateRepeatedPlaylists() {
		assertTrue (user.createPlaylist("a"));
		assertFalse(user.createPlaylist("a"));
		assertTrue (user.getPlaylists().size() == 1);
	}
	
	@Test
	public void historyIsNeverMoreThanFiveVideosLong() {
		for (int i = 0; i < 200; i++) {
			user.addBeginningHistory(
				new Video(String.valueOf(i), "a", 2)
			);
		}
		assertTrue(user.getHistory().size() == 5);
	}
	
	@Test
	public void cannotImmediatelyAddARepeatedVideo() {
		for (int i = 0; i < 50; i++) {
			user.addBeginningHistory(new Video("a", "b", 1));
		}
		assertTrue(user.getHistory().size() == 1);
	}
	
	@Test
	public void cannotSparselyAddARepeatedVideo() {
		user.addBeginningHistory(new Video("a", "x", 1));
		user.addBeginningHistory(new Video("b", "x", 1));
		user.addBeginningHistory(new Video("c", "x", 1));
		user.addBeginningHistory(new Video("a", "x", 1));
		assertTrue(user.getHistory().size() == 3);
	}
	
	@Test
	public void mostRecentVideoComesFirst() {
		user.addBeginningHistory(new Video("a", "x", 1));
		user.addBeginningHistory(new Video("b", "x", 1));
		user.addBeginningHistory(new Video("c", "x", 1));
		user.addBeginningHistory(new Video("d", "x", 1));
		user.addBeginningHistory(new Video("e", "x", 1));
		assertTrue(user.getHistory().size() == 5);
		assertTrue(user.getHistory().get(0).getUrl().equals("e"));
	}
}

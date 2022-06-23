package um.tds.projects.appvideo.backend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PlaylistTest {
	
	Playlist playlist;
	
	@Before
	public void before() {
		playlist = new Playlist("name");
	}
	
	@Test
	public void initiallyContainsNoVideos() {
		assertTrue(playlist.getNumVideos() == 0);
	}
	
	@Test
	public void canStoreSingleVideo() {
		playlist.addVideo(new Video("a", "b", 3));
		assertTrue(playlist.containsVideo(new Video("a", "b", 3)));
	}
	
	@Test
	public void canStoreMultipleVideos() {
		final int NUM_VIDEOS = 100;
		for (int i = 0; i < NUM_VIDEOS; i++) {
			playlist.addVideo(new Video(String.valueOf(i), "x", 1));
		}
		for (int i = 0; i < NUM_VIDEOS; i++) {
			assertTrue(playlist.containsVideo(new Video(String.valueOf(i), "x", 1)));
		}
	}
	
	@Test
	public void cannotAddRepeatedVideos() {
		final int NUM_VIDEOS = 100;
		for (int i = 0; i < NUM_VIDEOS; i++) {
			playlist.addVideo(new Video(String.valueOf(i), "x", 1));
		}
		for (int i = 0; i < NUM_VIDEOS; i++) {
			playlist.addVideo(new Video(String.valueOf(i), "x", 1));
		}
		assertTrue(playlist.getNumVideos() == NUM_VIDEOS);
	}
	
	@Test
	public void canRemoveVideo() {
		Video video = new Video("a", "x", 1);
		playlist.addVideo   (video);
		playlist.removeVideo(video);
		assertTrue (playlist.getNumVideos() == 0);
		assertFalse(playlist.containsVideo(video));
	}

}

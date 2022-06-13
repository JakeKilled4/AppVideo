package um.tds.projects.appvideo.backend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class VideoRepositoryTest {

	VideoRepository repo;

	@Before
	public void loadRepository() {
		repo = VideoRepository.getUniqueInstance();
	}

	@Test
	public void canStoreAndRetrieveVideo() {
		Video v1 = new Video("url", "title", 1234);
		repo.addVideo(v1);
		Video v2 = repo.getVideo(v1.getUrl());
		assertTrue(v1 == v2);
	}

	@Test
	public void canRemoveVideo() {
		Video v = new Video("a", "b", 45);
		assertFalse(repo.containsVideo(v.getUrl()));
		repo.addVideo(v);
		assertTrue(repo.containsVideo(v.getUrl()));
		repo.removeVideo(v);
		assertFalse(repo.containsVideo(v.getUrl()));
	}
}

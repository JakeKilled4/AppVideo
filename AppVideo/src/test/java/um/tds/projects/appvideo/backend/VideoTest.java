package um.tds.projects.appvideo.backend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import java.util.Set;

public class VideoTest {
	
	Video video;
	
	@Before
	public void before() {
		video = new Video("url", "title", 123);
	}
	
	@Test
	public void canStoreAndRetrieveLabel() {
		Label label = new Label("a");
		video.addLabel(label);
		assertTrue(video.containsLabel(label.getName()));
	}
	
	@Test
	public void canStoreAndRetrieveMultipleLabels() {
		final int NUM_LABELS = 100;
		for (int i = 0; i < NUM_LABELS; i++) {
			video.addLabel(new Label(String.valueOf(i)));
		}
		for (int i = 0; i < NUM_LABELS; i++) {
			assertTrue(video.containsLabel(String.valueOf(i)));
		}
	}
	
	@Test
	public void canStoreAndRetrieveAllLabelsAtOnce() {
		final int NUM_LABELS = 100;
		for (int i = 0; i < NUM_LABELS; i++) {
			video.addLabel(new Label(String.valueOf(i)));
		}
		Set<Label> labels = video.getLabels();
		for (int i = 0; i < NUM_LABELS; i++) {
			assertTrue(labels.contains(new Label(String.valueOf(i))));
		}
	}
	
	@Test
	public void equalityIsEquivalentToEqualityOfUrls() {
		Video video1 = new Video("a", "x", 13);
		Video video2 = new Video("a", "y", 31);
		
		assertTrue(video1.equals(video2));
		
		video1 = new Video("a", "x", 13);
		video2 = new Video("b", "x", 12);
		
		assertFalse(video1.equals(video2));
	}
}

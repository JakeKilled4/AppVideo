package um.tds.projects.appvideo.persistence;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import um.tds.projects.appvideo.backend.Label;
import um.tds.projects.appvideo.backend.Video;

public class TdsVideoAdapterTest {
	
	private static TdsVideoAdapter videoAdpt;
	private static Video           video;
	
	@BeforeClass
	public static void setup() {
		videoAdpt = TdsVideoAdapter.getUniqueInstance();
	}
	
	@Before
	public void before() {
		video = new Video("url", "title", 200);
		videoAdpt.registerVideo(video);
	}
	
	@After
	public void after() {
		videoAdpt.removeVideo(video);
	}

	@Test
	public void canStoreAndDeleteVideo() {
		Video v2 = videoAdpt.loadVideo(video.getCode());
		assertEquals(video.getCode(), v2.getCode());
	}
	
	@Test
	public void canStoreTitle() {
		Video v2 = videoAdpt.loadVideo(video.getCode());
		assertEquals(video.getTitle(), v2.getTitle());
	}
	
	@Test
	public void canModifyTitle() {
		video.setTitle("newtitle");
		videoAdpt.modifyVideo(video);
		Video v2 = videoAdpt.loadVideo(video.getCode());
		assertEquals("newtitle", v2.getTitle());
	}
	
	@Test
	public void canStoreLabels() {
		Video v1 = new Video("a", "b", 1);
		Label l1 = new Label("l");
		v1.addLabel(l1);
		
		videoAdpt.registerVideo(v1);
		Video v2 = videoAdpt.loadVideo(v1.getCode());
		assertEquals(1, v2.getLabels().size());
		assertTrue  (v2.containsLabel(l1.getName()));
		
		videoAdpt.removeVideo(v1);
	}

	@Test
	public void canStoreMultipleLabels() {
		Video v1 = new Video("a", "b", 1);
		Label l1 = new Label("l1");
		Label l2 = new Label("l2");
		Label l3 = new Label("l3");
		v1.addLabel(l1);
		v1.addLabel(l2);
		v1.addLabel(l3);
		
		videoAdpt.registerVideo(v1);
		Video v2 = videoAdpt.loadVideo(v1.getCode());

		assertEquals(3,    v2.getLabels().size());
		assertTrue  (v2.containsLabel("l1"));
		assertTrue  (v2.containsLabel("l2"));
		assertTrue  (v2.containsLabel("l3"));
		
		videoAdpt.removeVideo(v1);
	}	
	
	@Test
	public void canAddMultipleLabels() {
		video.addLabel(new Label("l1"));
		video.addLabel(new Label("l2"));
		videoAdpt.modifyVideo(video);
		
		Video v2 = videoAdpt.loadVideo(video.getCode());
		
		assertEquals(2,    v2.getLabels().size());
		assertTrue  (v2.containsLabel("l1"));
		assertTrue  (v2.containsLabel("l2"));
	}
}
package um.tds.projects.appvideo.backend;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LabelTest {
	
	private Label label;
	
	@Before
	public void before() {
		label = new Label("name");
	}
	
	@Test
	public void canInstantiate() {
		assertTrue(label.getName().equals("name"));
	}
	
	@Test
	public void canSetName() {
		label.setName("new name");
		assertTrue(label.getName().equals("new name"));
	}
}

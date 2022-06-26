package um.tds.projects.appvideo.persistence;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import um.tds.projects.appvideo.backend.Label;

public class TdsLabelAdapterTest {
	
	private static TdsLabelAdapter labelAdpt;
	private static Label           label;
	
	@BeforeClass
	public static void setup() {
		labelAdpt = TdsLabelAdapter.getUniqueInstance();
	}
	
	@Before
	public void before() {
		label = new Label("aaaa");
		labelAdpt.registerLabel(label);
	}
	
	@After
	public void after() {
		labelAdpt.removeLabel(label);
	}

	@Test
	public void canStoreAndDeleteLabel() {
		Label l2 = labelAdpt.loadLabel(label.getCode());
		assertEquals(label.getCode(), l2.getCode());
	}
	
	@Test
	public void canStoreName() {
		Label l2 = labelAdpt.loadLabel(label.getCode());
		assertEquals(label.getName(), l2.getName());
	}
	
	@Test
	public void canModifyName() {
		label.setName("bbbb");
		labelAdpt.modifyLabel(label);
		Label l2 = labelAdpt.loadLabel(label.getCode());
		assertEquals("bbbb", l2.getName());
	}
}
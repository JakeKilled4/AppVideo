package um.tds.projects.appvideo.backend;

import java.util.Random;

public class Identifiable {

	private int id;

	public Identifiable() {
		this.id = (new Random()).nextInt();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

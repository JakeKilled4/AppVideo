package um.tds.projects.appvideo.backend;

import java.util.Random;

public class Identifiable {

	private int code;

	public Identifiable() {
		this.code = (new Random()).nextInt();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}

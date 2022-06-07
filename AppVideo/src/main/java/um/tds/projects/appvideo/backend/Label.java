package um.tds.projects.appvideo.backend;

public class Label {
	private int code;
	private String name;
	public Label(String name) {
		this.name = name;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

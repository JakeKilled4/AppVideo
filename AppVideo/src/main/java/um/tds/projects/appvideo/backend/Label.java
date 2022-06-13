package um.tds.projects.appvideo.backend;

public class Label extends Identifiable {
	private String name;
	public Label(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

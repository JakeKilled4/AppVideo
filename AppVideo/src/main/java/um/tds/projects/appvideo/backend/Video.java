package um.tds.projects.appvideo.backend;

public class Video {

	private String url;
	private String title;
	private int numViews;
	
	public Video(String url, String title, int numViews) {
		this.url = url;
		this.title = title;
		this.numViews = numViews;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getTitle() {
		return title;
	}

	public int getNumViews() {
		return numViews;
	}

}

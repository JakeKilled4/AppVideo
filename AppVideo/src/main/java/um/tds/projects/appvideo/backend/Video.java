package um.tds.projects.appvideo.backend;

import java.util.ArrayList;
import java.util.List;

public class Video extends Identifiable {
	
	private String url;
	private String title;
	private int numViews;
	private List<Label> labels;

	public Video(String url, String title, int numViews) {
		super();
		this.url = url;
		this.title = title;
		this.numViews = numViews;
		this.labels = new ArrayList<Label>();
	}

	public Video(String url, String title, int numViews, List<Label> labels) {
		this(url, title, numViews);
		this.labels = labels;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumViews() {
		return numViews;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void addLabel(Label label) {
		labels.add(label);
	}

}

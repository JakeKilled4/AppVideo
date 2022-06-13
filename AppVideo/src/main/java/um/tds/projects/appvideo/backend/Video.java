package um.tds.projects.appvideo.backend;

import java.util.ArrayList;
import java.util.List;

public class Video extends Identifiable {
	
	private static int MAX_LABELS = 6;
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
	
	public void addView(){
		this.numViews++;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public boolean addLabel(Label label) {
		if(labels.size() < MAX_LABELS) {
			labels.add(label);
			return true;
		}
		return false;
	}

}

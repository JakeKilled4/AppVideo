package um.tds.projects.appvideo.backend;

import java.util.Set;
import java.util.TreeSet;

public class Video extends Identifiable {
	
	private String url;
	private String title;
	private int numViews;
	//private List<Label> labels;
	private Set<Label> labels;

	public Video(String url, String title, int numViews) {
		super();
		this.url = url;
		this.title = title;
		this.numViews = numViews;
		this.labels = new TreeSet<Label>((l1, l2) -> l1.getName().compareTo(l2.getName()));
	}

	public Video(String url, String title, int numViews,Set<Label> labels) {
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

	public Set<Label> getLabels() {
		return labels;
	}
	
	public boolean addLabel(Label l) {
		if(labels.contains(l)) return false;
		labels.add(l);
		return true;
	}

}

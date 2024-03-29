package um.tds.projects.appvideo.backend;

import java.util.Set;
import java.util.TreeSet;

public class Video extends Identifiable {
	
	private String url;
	private String title;
	private int numViews;
	private Set<Label> labels;

	public Video(String url, String title, int numViews) {
		super();
		this.url = url;
		this.title = title;
		this.numViews = numViews;
		
		// Compare labels using the name
		this.labels = new TreeSet<Label>((l1, l2) -> l1.getName().compareTo(l2.getName()));
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
	
	public boolean containsLabel(String l) {
		if (labels.contains(new Label(l))) return true;
		return false;
	}
	
	/* Returns true if the label was not already in the video */
	public boolean addLabel(Label l) {
		if (labels.contains(l)) return false;
		labels.add(l);
		return true;
	}
	
	/* Compare videos, comparing the Url */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		return url.equals(((Video) obj).getUrl());
	}

}

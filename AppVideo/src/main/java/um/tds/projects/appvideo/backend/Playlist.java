package um.tds.projects.appvideo.backend;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;

public class Playlist extends Identifiable {

	private String name;
	private List<Video> videos;

	public Playlist(String name) {
		super();
		this.name = name;
		this.videos = new ArrayList<Video>();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Video> getVideos(){
		return this.videos;
	}
	
	public int getNumVideos() {
		return videos.size();
	}
	
	public boolean containsVideo(Video video) {
		for (Video v: videos) {
			if (v.equals(video)) return true;
		}
		return false;
	}
	
	/* Return true if the video was not in the playlist */
	public boolean addVideo(Video video) {
		if (containsVideo(video)) return false;
		videos.add(video);
		return true;
	}

	public void removeVideo(Video video) {
		videos.remove(video);
	}	
	
	/* Add the information of all the videos to the PDF */
	public void playListToPdf(Section s) {
		for (Video video : videos) {
			Paragraph p = new Paragraph(video.getTitle() + " (" + video.getNumViews() + " views)");
			s.add(p);
		} 
	}
}

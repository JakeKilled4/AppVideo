package um.tds.projects.appvideo.backend;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

	private String name;
	private List<Video> videos;

	public Playlist(String name) {
		this.name = name;
		this.videos = new ArrayList();
	}

	public String getName() {
		return name;
	}

	public void addVideo(Video video) {
		videos.add(video);
	}

	public void removeVideo(Video video) {
		videos.remove(video);
	}

	public int getNumVideos() {
		return videos.size();
	}

}

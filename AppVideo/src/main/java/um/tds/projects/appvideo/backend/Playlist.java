package um.tds.projects.appvideo.backend;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

	private int code;
	private String name;
	private List<Video> videos;

	public Playlist(String name) {
		this.code = 0;
		this.name = name;
		this.videos = new ArrayList<Video>();
	}
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
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

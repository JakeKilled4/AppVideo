package um.tds.projects.appvideo.backend;

import um.tds.projects.appvideo.backend.filters.IVideoFilter;

import java.util.List;


public class VideoRepository {

	private VideoRepository instance;

	private VideoRepository() { }

	public VideoRepository getUniqueInstance() {
		if (instance == null) {
			instance = new VideoRepository();
		}
		return instance;
	}

	public void addVideo(Video v) {
		return;
	}

	public void removeVideo(Video v) {
		return;
	}

	public List<Video> findVideo(String str, List<IVideoFilter> filters) {
		return null;
	}

}

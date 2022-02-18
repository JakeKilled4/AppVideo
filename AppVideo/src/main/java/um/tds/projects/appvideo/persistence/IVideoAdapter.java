package um.tds.projects.appvideo.persistence;

import java.util.List;

import um.tds.projects.appvideo.backend.Video;

public interface IVideoAdapter {
	public List<Video> loadAllVideos();
}

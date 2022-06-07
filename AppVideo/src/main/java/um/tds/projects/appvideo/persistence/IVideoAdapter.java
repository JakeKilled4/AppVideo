package um.tds.projects.appvideo.persistence;

import java.util.List;

import um.tds.projects.appvideo.backend.Video;

public interface IVideoAdapter {
	public void registerVideo(Video v);
	public void removeVideo(Video v);
	public void modifyVideo(Video v);
	public Video loadVideo(int code);
	public List<Video> loadAllVideos();
}

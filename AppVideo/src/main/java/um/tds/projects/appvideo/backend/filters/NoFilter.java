package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;

public class NoFilter implements IVideoFilter  {
	public boolean isVideoOk(Video v) {
		return true;
	}
}

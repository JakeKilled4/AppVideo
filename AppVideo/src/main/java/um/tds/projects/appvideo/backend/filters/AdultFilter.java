package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;

public class AdultFilter implements IVideoFilter  {
	public boolean isVideoOk(Video v) {
		return false;
	}
}

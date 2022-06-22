package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;

public class UnpopularFilter implements IVideoFilter {
	public boolean isVideoOk(Video v) {
		return v.getNumViews() >= 5;
	}
}

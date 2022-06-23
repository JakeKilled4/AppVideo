package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;

public class UnpopularFilter implements IVideoFilter {
	
	/* Returns true if the number of views of the video is equal or greater than 5 */
	public boolean isVideoOk(Video v) {
		return v.getNumViews() >= 5;
	}
}

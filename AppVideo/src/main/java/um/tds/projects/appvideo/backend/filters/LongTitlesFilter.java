package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;

public class LongTitlesFilter implements IVideoFilter {
	
	/* Returns true if the title's length of the video is less than 16 characters */
	public boolean isVideoOk(Video v) {
		return v.getTitle().length() < 16;
	}
}

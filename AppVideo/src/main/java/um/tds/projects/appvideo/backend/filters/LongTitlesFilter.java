package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;

public class LongTitlesFilter implements IVideoFilter {
	public boolean isVideoOk(Video v) {
		return v.getTitle().length() < 16;
	}
}

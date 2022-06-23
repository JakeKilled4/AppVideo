package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.controller.Controller;

public class ListFilter implements IVideoFilter {
	
	/* Returns true if the video is not contained in any playlist */
	public boolean isVideoOk(Video v) {
		return !Controller.getUniqueInstance().videoInSomePlaylist(v);
	}
}

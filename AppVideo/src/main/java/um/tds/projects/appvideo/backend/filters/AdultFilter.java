package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;

public class AdultFilter implements IVideoFilter  {
	private int code;
	
	public AdultFilter() {
		this.code = 0;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCode() {
		return this.code;
	}
	public boolean isVideoOk(Video v) {
		return false;
	}
}

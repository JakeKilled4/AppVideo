package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;

public class ListFilter implements IVideoFilter {
	private int code;
	private final String type = this.getClass().getSimpleName();
	
	public ListFilter(){ this.code = 0; }
	
	public String getType() {
		return this.type;
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

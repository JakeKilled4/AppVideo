package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.controller.Controller;

public class AdultFilter implements IVideoFilter  {
	private int code;
	private final String type = this.getClass().getName();
	
	public AdultFilter() {
		this.code = 0; 
	}
	
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
		if(Controller.getUniqueInstance().underEighteen() && v.containsLabel("Adultos"))
			return false;
		return true;
	}
}

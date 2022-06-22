package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.controller.Controller;

public class AdultFilter implements IVideoFilter  {	
	public boolean isVideoOk(Video v) {
		if(Controller.getUniqueInstance().underEighteen() && v.containsLabel("Adultos"))
			return false;
		return true;
	}
}

package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;
import um.tds.projects.appvideo.controller.Controller;

public class AdultFilter implements IVideoFilter  {	

	/* Returns false if the user is less than 18 years old and if the video contains the label 'Adultos'*/
	public boolean isVideoOk(Video v) {
		if(Controller.getUniqueInstance().underEighteen() && v.containsLabel("Adultos"))
			return false;
		return true;
	}
}

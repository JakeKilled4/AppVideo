package um.tds.projects.appvideo.backend.filters;

public class NoFilter implements IVideoFilter  {
	public boolean isVideoOk() {
		return false;
	}
}

package um.tds.projects.appvideo.backend.filters;

import um.tds.projects.appvideo.backend.Video;

public interface IVideoFilter {
	public int getCode();
	public void setCode(int code);
	public boolean isVideoOk(Video v);
}

package um.tds.projects.appvideo.persistence;

import java.util.LinkedList;
import java.util.List;

import um.tds.projects.appvideo.backend.Video;

public class TdsVideoAdapter implements IVideoAdapter {

	private static TdsVideoAdapter instance;

	private TdsVideoAdapter() { }

	public static TdsVideoAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsVideoAdapter();
		}
		return instance;
	}

	public List<Video> loadAllVideos() {
		return new LinkedList<Video>();
	}

}

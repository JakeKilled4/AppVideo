package um.tds.projects.appvideo.backend;

import um.tds.projects.appvideo.backend.filters.IVideoFilter;
import um.tds.projects.appvideo.persistence.DaoException;
import um.tds.projects.appvideo.persistence.DaoFactory;
import um.tds.projects.appvideo.persistence.IVideoAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class VideoRepository {


	private Map<Integer, Video> cache;
	private static VideoRepository instance;

	private DaoFactory dao;
	private IVideoAdapter videoAdapter;

	private VideoRepository() {
		try {
			dao = DaoFactory.getUniqueInstance();
			videoAdapter = dao.getVideoAdapter();
			cache = new HashMap<Integer, Video>();
			loadRepository();
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void loadRepository() throws DaoException {
		List<Video> videos = videoAdapter.loadAllVideos();
		for (Video v: videos) {
			cache.put(v.getCode(), v);
		}
	}

	public static VideoRepository getUniqueInstance() {
		if (instance == null) {
			instance = new VideoRepository();
		}
		return instance;
	}

	public void addVideo(Video v) {
		cache.put(v.getCode(), v);
	}

	public void removeVideo(Video v) {
		cache.remove(v.getCode());
	}

	public Video getVideo(int id) {
		return cache.get(id);
	}

	public boolean containsVideo(int id) {
		return cache.containsKey(id);
	}

	public List<Video> findVideo(String str, List<IVideoFilter> filters) {
		LinkedList<Video> res = new LinkedList<Video>();
		for (Video v: cache.values()) {
			if (v.getTitle().contains(str)) {
				boolean valid = true;
				for (IVideoFilter f: filters)
					valid &= f.isVideoOk(v);
				if (valid)
					res.add(v);
			}
		}
		return res;
	}

}

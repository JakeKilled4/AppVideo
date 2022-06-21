package um.tds.projects.appvideo.backend;

import um.tds.projects.appvideo.backend.filters.IVideoFilter;
import um.tds.projects.appvideo.persistence.DaoException;
import um.tds.projects.appvideo.persistence.DaoFactory;
import um.tds.projects.appvideo.persistence.IVideoAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class VideoRepository {

	private Map<String, Video> cache;
	private static VideoRepository instance;

	private DaoFactory dao;
	private IVideoAdapter videoAdapter;

	private VideoRepository() {
		try {
			dao = DaoFactory.getUniqueInstance();
			videoAdapter = dao.getVideoAdapter();
			cache = new HashMap<String, Video>();
			loadRepository();
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void loadRepository() throws DaoException {
		List<Video> videos = videoAdapter.loadAllVideos();
		for (Video v: videos) {
			cache.put(v.getUrl(), v);
		}
	}

	public static VideoRepository getUniqueInstance() {
		if (instance == null) {
			instance = new VideoRepository();
		}
		return instance;
	}

	public void addVideo(Video v) {
		if (!cache.containsKey(v.getUrl()))
			cache.put(v.getUrl(), v);
	}

	public void removeVideo(Video v) {
		if (cache.containsKey(v.getUrl()))
			cache.remove(v.getUrl());
	}

	public Video getVideo(String url) {
		if (cache.containsKey(url))
			return cache.get(url);
		return null;
	}

	public boolean containsVideo(String url) {
		return cache.containsKey(url);
	}
	
	public List<Video> getAllVideos() {
		return cache.values().stream().collect(Collectors.toList());
	}

	public List<Video> findVideo(String str, List<IVideoFilter> filters, List<Label> labels) {
		LinkedList<Video> res = new LinkedList<Video>();
		for (Video v: cache.values()) {
			boolean valid = true;
			for (IVideoFilter f: filters)
				valid &= f.isVideoOk(v);
			if (valid && v.getTitle().contains(str)) {
				boolean containLabel = false;
				for(Label l : labels) {
					if(v.containsLabel(l.getName())) { 
						containLabel = true;
						break;
					}
				}
				if(labels.isEmpty() || containLabel) res.add(v);
			}
		}
		return res;
	}


}

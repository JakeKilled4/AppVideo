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
	
	/* Just one repository */
	public static VideoRepository getUniqueInstance() {
		if (instance == null) {
			instance = new VideoRepository();
		}
		return instance;
	}
	
	/* Returns null iff there is not a video with url 'url' */
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

	public void addVideo(Video v) {
		if (!cache.containsKey(v.getUrl()))
			cache.put(v.getUrl(), v);
	}

	public void removeVideo(Video v) {
		if (cache.containsKey(v.getUrl()))
			cache.remove(v.getUrl());
	}
	
	/* Return a list with less or equals videos than 'n' 
	 * ordered by the number of views of each video */
	public List<Video> getMostPopularVideos(int n) {
		return cache
			.values()
			.stream()
			.distinct()
			.sorted((v1, v2) -> v2.getNumViews() - v1.getNumViews())
			.limit(n)
			.collect(Collectors.toList());
	}
	
	/* Filter videos in the repository using the title of the video, 
	 * the filter and the labels that a video contains */
	public List<Video> findVideo(String str, IVideoFilter filter, List<Label> labels) {
		LinkedList<Video> res = new LinkedList<Video>();
		
		// Iterate over all the videos in the repository
		for (Video v: cache.values()) {
			
			// If the video pass the filter and 'str' is contained in the title of the video
			if (filter.isVideoOk(v) && v.getTitle().contains(str)) {
				boolean containLabel = false;
				
				// Check if the video contains at least one of the labels of the selected
				for(Label l : labels) {
					if(v.containsLabel(l.getName())) { 
						containLabel = true;
						break;
					}
				}
				// If the video contains at least one of the labels of the selected (or no labels selected)
				// and all the others conditions add the video to the list to return
				if(labels.isEmpty() || containLabel) res.add(v);
			}
		}
		return res;
	}

	/* Private functions */
	private void loadRepository() throws DaoException {
		List<Video> videos = videoAdapter.loadAllVideos();
		for (Video v: videos) {
			cache.put(v.getUrl(), v);
		}
	}
}

package um.tds.projects.appvideo.backend.filters;

import java.util.ArrayList;
import java.util.List;

import um.tds.projects.appvideo.backend.Video;

public interface IVideoFilter {
	public boolean isVideoOk(Video v);
	public static List<String> getAllFiltersNames(){
		List<String> list = new ArrayList<String>();
		list.add("No filter");
		list.add("Adult filter");
		list.add("List filter");
		list.add("Unpopular filter");
		list.add("Long titles filter");
		return list;
	}
	
	public static IVideoFilter makeFilter(String filterName) {
		if(filterName.equals("No filter")) return new NoFilter();
		else if(filterName.equals("Adult filter")) return new AdultFilter();
		else if(filterName.equals("List filter")) return new ListFilter();
		else if(filterName.equals("Long titles filter")) return new LongTitlesFilter();
		else if(filterName.equals("Unpopular filter")) return new UnpopularFilter();
		else return new NoFilter();
	}
	
	public static String getFilterName(IVideoFilter filter) {
		if(filter instanceof NoFilter) return "No filter";
		else if(filter instanceof AdultFilter) return "Adult filter";
		else if(filter instanceof ListFilter) return "List filter";
		else if(filter instanceof LongTitlesFilter) return "Long titles filter";
		else if(filter instanceof UnpopularFilter) return "Unpopular filter";
		return "No filter";
	}
}

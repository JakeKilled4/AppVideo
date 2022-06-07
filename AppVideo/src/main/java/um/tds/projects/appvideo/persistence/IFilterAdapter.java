package um.tds.projects.appvideo.persistence;

import java.util.List;

import um.tds.projects.appvideo.backend.filters.IVideoFilter;

public interface IFilterAdapter {
	public void registerFilter(IVideoFilter f);
	public void removeFilter(IVideoFilter f);
	public void modifyFilter(IVideoFilter f);
	public IVideoFilter loadFilter(int code);
	public List<IVideoFilter> loadAllFilters();
}

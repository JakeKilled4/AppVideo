package um.tds.projects.appvideo.persistence;


public class TdsDaoFactory extends DaoFactory {

	public TdsDaoFactory() { }

	@Override
	public IFilterAdapter getFilterAdapter() {
		return TdsFilterAdapter.getUniqueInstance();
	}

	@Override
	public IPlaylistAdapter getPlaylistAdapter() {
		return TdsPlaylistAdapter.getUniqueInstance();
	}

	@Override
	public IUserAdapter getUserAdapter() {
		return TdsUserAdapter.getUniqueInstance();
	}

	@Override
	public IVideoAdapter getVideoAdapter() {
		return TdsVideoAdapter.getUniqueInstance();
	}
	
	@Override
	public ILabelAdapter getLabelAdapter() {
		return TdsLabelAdapter.getUniqueInstance();
	}

}

package um.tds.projects.appvideo.persistence;


public abstract class DaoFactory {

	private static DaoFactory instance;
	private static String TDS_FACTORY = "um.tds.projects.appvideo.persistence.TdsDaoFactory";

	protected DaoFactory() { }

	public static DaoFactory getUniqueInstance() throws DaoException {
		return getUniqueInstance(TDS_FACTORY);
	}

	@SuppressWarnings("deprecation")
	public static DaoFactory getUniqueInstance(String type) throws DaoException {
		if (instance == null) {
			try {
				instance = (DaoFactory) Class.forName(type).newInstance();
			} catch (Exception e) {
				throw new DaoException(e.getMessage());
			}
		}
		return instance;
	}
	
	public abstract ILabelAdapter getLabelAdapter();
	public abstract IPlaylistAdapter getPlaylistAdapter();
	public abstract IUserAdapter getUserAdapter();
	public abstract IVideoAdapter getVideoAdapter();
}

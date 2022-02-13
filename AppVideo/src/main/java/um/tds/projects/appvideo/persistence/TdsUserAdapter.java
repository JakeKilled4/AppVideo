package um.tds.projects.appvideo.persistence;


public class TdsUserAdapter implements IUserAdapter {

	private TdsUserAdapter instance;

	private TdsUserAdapter() { }

	public static TdsUserAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsUserAdapter();
		}
		return instance;
	}

}

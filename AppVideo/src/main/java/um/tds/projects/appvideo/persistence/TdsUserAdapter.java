package um.tds.projects.appvideo.persistence;


public class TdsUserAdapter implements IUserAdapter {

	private static TdsUserAdapter instance;

	private TdsUserAdapter() { }

	public static TdsUserAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsUserAdapter();
		}
		return instance;
	}

}

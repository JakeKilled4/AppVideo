package um.tds.projects.appvideo.persistence;


public class TdsVideoAdapter implements IVideoAdapter {

	private static TdsVideoAdapter instance;

	private TdsVideoAdapter() { }

	public static TdsVideoAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsVideoAdapter();
		}
		return instance;
	}

}

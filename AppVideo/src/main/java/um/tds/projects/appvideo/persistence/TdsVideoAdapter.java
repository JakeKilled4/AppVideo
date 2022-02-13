package um.tds.projects.appvideo.persistence;


public class TdsVideoAdapter implements IVideoAdapter {

	private TdsVideoAdapter instance;

	private TdsVideoAdapter() { }

	public static TdsVideoAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsVideoAdapter();
		}
		return instance;
	}

}

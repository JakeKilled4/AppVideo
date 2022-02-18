package um.tds.projects.appvideo.persistence;


public class TdsPlaylistAdapter implements IPlaylistAdapter {

	private static TdsPlaylistAdapter instance;

	private TdsPlaylistAdapter() { }

	public static TdsPlaylistAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsPlaylistAdapter();
		}
		return instance;
	}

}

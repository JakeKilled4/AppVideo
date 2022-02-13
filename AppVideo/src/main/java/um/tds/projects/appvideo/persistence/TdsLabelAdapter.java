package um.tds.projects.appvideo.persistence;


public class TdsLabelAdapter implements ILabelAdapter {

	TdsLabelAdapter instance;

	private TdsLabelAdapter() { }

	public static TdsLabelAdapter getUniqueInstance() {
		if (instance == null) {
			instance = new TdsLabelAdapter();
		}
		return instance;
	}

}

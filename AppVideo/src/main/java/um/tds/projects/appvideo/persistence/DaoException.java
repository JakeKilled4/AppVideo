package um.tds.projects.appvideo.persistence;


public class DaoException extends Exception {

	private String msg;

	public DaoException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

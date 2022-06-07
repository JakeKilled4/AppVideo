package um.tds.projects.appvideo.persistence;

import java.util.List;

import um.tds.projects.appvideo.backend.Label;

public interface ILabelAdapter {
	public void registerLabel(Label l);
	public void removeLabel(Label l);
	public void modifyLabel(Label l);
	public Label loadLabel(int code);
	public List<Label> loadAllLabels();
}

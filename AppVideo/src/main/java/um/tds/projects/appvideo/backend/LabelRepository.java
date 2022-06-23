package um.tds.projects.appvideo.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import um.tds.projects.appvideo.persistence.DaoException;
import um.tds.projects.appvideo.persistence.DaoFactory;
import um.tds.projects.appvideo.persistence.ILabelAdapter;

public class LabelRepository {
	
	private Map<String, Label> cache;
	private static LabelRepository instance;

	private DaoFactory dao;
	private ILabelAdapter labelAdapter;

	private LabelRepository() {
		try {
			dao = DaoFactory.getUniqueInstance();
			labelAdapter = dao.getLabelAdapter();
			cache = new HashMap<String, Label>();
			loadRepository();
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	/* Just one repository */
	public static LabelRepository getUniqueInstance() {
		if (instance == null) {
			instance = new LabelRepository();
		}
		return instance;
	}
	
	/* Returns null iff there is not a label with name 'name' */
	public Label getLabel(String name) {
		if (cache.containsKey(name))
			return cache.get(name);
		return null;
	}
	
	public List<Label> getAllLabels() {
		return cache.values().stream().collect(Collectors.toList());
	}
	
	public boolean containsLabel(String name) {
		return cache.containsKey(name);
	}
	
	public void addLabel(Label l) {
		if (!cache.containsKey(l.getName()))
			cache.put(l.getName(), l);
	}

	public void removeLabel(Label l) {
		if (cache.containsKey(l.getName()))
			cache.remove(l.getName());
	}
	
	/* Private functions */
	private void loadRepository() throws DaoException {
		List<Label> labels = labelAdapter.loadAllLabels();
		for (Label l: labels) {
			cache.put(l.getName(), l);
		}
	}
}

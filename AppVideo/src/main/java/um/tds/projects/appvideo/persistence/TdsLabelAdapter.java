package um.tds.projects.appvideo.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.projects.appvideo.backend.Label;

public class TdsLabelAdapter implements ILabelAdapter {

	private static TdsLabelAdapter      instance;
	private static ServicioPersistencia servPersistencia;

	public static TdsLabelAdapter getUniqueInstance() {
		if (instance == null)
			instance = new TdsLabelAdapter();

		return instance;
	}

	private TdsLabelAdapter() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registerLabel(Label l) {
		Entidad eLabel = null;
		try {
			eLabel = servPersistencia.recuperarEntidad(l.getCode());
		} catch (NullPointerException e) {
		}
		if (eLabel != null) return;

		// Create label entity
		eLabel = new Entidad();
		eLabel.setNombre("label");
		eLabel.setPropiedades(
			new ArrayList<Propiedad>(
				Arrays.asList(
					new Propiedad("name", l.getName())
				)
			)
		);
		// Register label entity
		eLabel = servPersistencia.registrarEntidad(eLabel);

		// Unique identifier
		l.setCode(eLabel.getId());
	}

	@Override
	public void removeLabel(Label l) {
		Entidad eLabel = servPersistencia.recuperarEntidad(l.getCode());
		servPersistencia.borrarEntidad(eLabel);
	}

	@Override
	public void modifyLabel(Label l) {
		Entidad eLabel = servPersistencia.recuperarEntidad(l.getCode());
		modifyField(eLabel, "name", l.getName());
	}

	@Override
	public Label loadLabel(int code) {
		Entidad eLabel = servPersistencia.recuperarEntidad(code);
		
		Label label = new Label(
			getFieldValue(eLabel, "name")
		);
		label.setCode(code);
		return label;
	}

	@Override
	public List<Label> loadAllLabels() {
		List<Label> labels = new LinkedList<Label>();
		List<Entidad> eLabels = servPersistencia.recuperarEntidades("label");
		for (Entidad eLabel : eLabels)
			labels.add(loadLabel(eLabel.getId()));
		return labels;
	}

	/* Auxiliar functions */
	private void modifyField(Entidad entity, String fieldName, String newValue) {
		servPersistencia.eliminarPropiedadEntidad(entity, fieldName);
		servPersistencia.anadirPropiedadEntidad  (entity, fieldName, newValue);
	}
	

	private String getFieldValue(Entidad entity, String field) {
		return servPersistencia.recuperarPropiedadEntidad(entity, field);
	}
}

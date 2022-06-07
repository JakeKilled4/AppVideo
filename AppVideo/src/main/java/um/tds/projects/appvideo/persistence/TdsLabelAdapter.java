package um.tds.projects.appvideo.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.projects.appvideo.backend.Label;

public class TdsLabelAdapter implements ILabelAdapter {


	private static TdsLabelAdapter instance;
	private static ServicioPersistencia servPersistencia;


	public static TdsLabelAdapter getUniqueInstance() {
		if (instance == null) instance = new TdsLabelAdapter();
		
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
		} catch (NullPointerException e) {}
		if (eLabel != null)	return;
		
		// Create label entity
		eLabel = new Entidad();
				
		eLabel.setNombre("label");
		eLabel.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("name", l.getName()))));
		
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
		
	}
	
	@Override
	public Label loadLabel(int id) {
		return null;
	}
	
	@Override
	public List<Label> loadAllLabels(){
		return null;
	}

}

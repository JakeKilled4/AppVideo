package um.tds.projects.appvideo.persistence;


import java.util.List;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.projects.appvideo.backend.filters.IVideoFilter;

public class TdsFilterAdapter implements IFilterAdapter {
	
	private static TdsFilterAdapter instance;
	private static ServicioPersistencia servPersistencia;
	
	public static TdsFilterAdapter getUniqueInstance() {
		if (instance == null) instance = new TdsFilterAdapter();
		return instance;
	}
	
	private TdsFilterAdapter() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void registerFilter(IVideoFilter f) {
		Entidad eFilter = null;
		try {
			eFilter = servPersistencia.recuperarEntidad(f.getCode());
		} catch (NullPointerException e) {}
		if (eFilter != null)	return;
		
		// Create filter entity
		eFilter = new Entidad();
				
		eFilter.setNombre("filter");
		
		// Register filter entity
		eFilter = servPersistencia.registrarEntidad(eFilter);

		// Unique identifier
		f.setCode(eFilter.getId());
	}
	
	@Override
	public void removeFilter(IVideoFilter f) {
		Entidad eFilter;
		
		eFilter = servPersistencia.recuperarEntidad(f.getCode());
		servPersistencia.borrarEntidad(eFilter);
	}
	
	@Override
	public void modifyFilter(IVideoFilter f) {
		
	}
	
	@Override
	public IVideoFilter loadFilter(int id) {
		return null;
	}
	
	@Override
	public List<IVideoFilter> loadAllFilters(){
		return null;
	}
}

package um.tds.projects.appvideo.persistence;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
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
		eFilter.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("type", f.getType()))));
		
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
		// No properties modifiable
	}
	
	@Override
	public IVideoFilter loadFilter(int code) {
		Entidad eFilter;
		String type;
		IVideoFilter filter = null;
		
		eFilter = servPersistencia.recuperarEntidad(code);
		type = servPersistencia.recuperarPropiedadEntidad(eFilter, "type");
		try {
			filter = (IVideoFilter) Class.forName(type).newInstance();
			filter.setCode(code);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return filter;
	}
	
	@Override
	public List<IVideoFilter> loadAllFilters(){
		List<IVideoFilter> filters = new LinkedList<IVideoFilter>();
		List<Entidad> eFilters = servPersistencia.recuperarEntidades("filter");
		for (Entidad eFilter : eFilters) filters.add(loadFilter(eFilter.getId()));
		return filters;
	}
}

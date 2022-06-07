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
import um.tds.projects.appvideo.backend.Video;


public class TdsVideoAdapter implements IVideoAdapter {

	private static TdsVideoAdapter instance;
	private static ServicioPersistencia servPersistencia;


	public static TdsVideoAdapter getUniqueInstance() {
		if (instance == null) instance = new TdsVideoAdapter();
		
		return instance;
	}
	private TdsVideoAdapter() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void registerVideo(Video v) {
		Entidad eVideo = null;
		try {
			eVideo = servPersistencia.recuperarEntidad(v.getCode());
		} catch (NullPointerException e) {}
		if (eVideo != null)	return;
		
		// Register labels
		TdsLabelAdapter labelAdapter = TdsLabelAdapter.getUniqueInstance();
		for(Label label : v.getLabels())
			labelAdapter.registerLabel(label);
		
		// Create video entity
		eVideo = new Entidad();
				
		eVideo.setNombre("video");
		eVideo.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("url", v.getUrl()),
						new Propiedad("title", v.getTitle()),
						new Propiedad("numViews",String.valueOf(v.getNumViews())),
						new Propiedad("labels",getCodesLabels(v.getLabels())))));
		
		// Register video entity
		eVideo = servPersistencia.registrarEntidad(eVideo);

		// Unique identifier
		v.setCode(eVideo.getId());
	}
	
	@Override
	public void removeVideo(Video v) {
		Entidad eVideo;
		TdsLabelAdapter labelAdapter = TdsLabelAdapter.getUniqueInstance();
		
		// Remove labels in the video
		for(Label label : v.getLabels())
			labelAdapter.removeLabel(label);

		eVideo = servPersistencia.recuperarEntidad(v.getCode());
		servPersistencia.borrarEntidad(eVideo);
	}
	
	@Override
	public void modifyVideo(Video v) {
		
	}
	
	@Override
	public Video loadVideo(int id) {
		return null;
	}
	
	
	@Override
	public List<Video> loadAllVideos() {
		return new LinkedList<Video>();
	}
	
	/* Auxiliar functions */
	private String getCodesLabels(List<Label> labels) {
		String out = "";
		for (Label label : labels) out += String.valueOf(label.getCode()) + " ";
		return out.trim();
	}

}

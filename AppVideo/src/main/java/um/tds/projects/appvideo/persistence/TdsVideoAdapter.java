package um.tds.projects.appvideo.persistence;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

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
		Entidad eVideo;
		eVideo = servPersistencia.recuperarEntidad(v.getCode());
		servPersistencia.eliminarPropiedadEntidad(eVideo, "url");
		servPersistencia.anadirPropiedadEntidad(eVideo, "url", v.getUrl());
		servPersistencia.eliminarPropiedadEntidad(eVideo, "title");
		servPersistencia.anadirPropiedadEntidad(eVideo, "title", v.getTitle());
		servPersistencia.eliminarPropiedadEntidad(eVideo, "numViews");
		servPersistencia.anadirPropiedadEntidad(eVideo, "numViews", String.valueOf(v.getNumViews()));
		String labels = getCodesLabels(v.getLabels());
		servPersistencia.eliminarPropiedadEntidad(eVideo, "labels");
		servPersistencia.anadirPropiedadEntidad(eVideo, "labels", labels);
	}
	
	@Override
	public Video loadVideo(int code) {
		Entidad eVideo;
		String url;
		String title;
		int numViews;
		List<Label> labels = new LinkedList<Label>();

		eVideo = servPersistencia.recuperarEntidad(code);
		url = servPersistencia.recuperarPropiedadEntidad(eVideo, "url");
		title = servPersistencia.recuperarPropiedadEntidad(eVideo, "title");
		numViews = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eVideo, "numViews"));

		Video video = new Video(url,title, numViews);
		video.setCode(code);

		// Load all labels
		labels = getLabelsFromCodes(servPersistencia.recuperarPropiedadEntidad(eVideo, "labels"));

		for (Label label : labels)
			video.addLabel(label);
		
		return video;
	}
	
	
	@Override
	public List<Video> loadAllVideos() {
		List<Video> videos = new LinkedList<Video>();
		List<Entidad> eVideos = servPersistencia.recuperarEntidades("video");
		for (Entidad eVideo : eVideos) videos.add(loadVideo(eVideo.getId()));
		return videos;
	}
	
	/**** Auxiliar functions ****/
	private String getCodesLabels(List<Label> labels) {
		String out = "";
		for (Label label : labels) out += String.valueOf(label.getCode()) + " ";
		return out.trim();
	}
	
	private List<Label> getLabelsFromCodes(String labels) {

		List<Label> labelList= new LinkedList<Label>();
		StringTokenizer strTok = new StringTokenizer(labels, " ");
		TdsLabelAdapter labelAdapter = TdsLabelAdapter.getUniqueInstance();
		while (strTok.hasMoreTokens()) {
			labelList.add(labelAdapter.loadLabel(Integer.valueOf((String) strTok.nextElement())));
		}
		return labelList;
	}
	

}

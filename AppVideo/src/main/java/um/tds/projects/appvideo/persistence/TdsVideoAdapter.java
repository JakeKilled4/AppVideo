package um.tds.projects.appvideo.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.projects.appvideo.backend.Label;
import um.tds.projects.appvideo.backend.Video;

public class TdsVideoAdapter implements IVideoAdapter {

	private static TdsVideoAdapter      instance;
	private static ServicioPersistencia servPersistencia;
	
	private TdsLabelAdapter labelAdapter;

	public static TdsVideoAdapter getUniqueInstance() {
		if (instance == null)
			instance = new TdsVideoAdapter();

		return instance;
	}

	private TdsVideoAdapter() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		labelAdapter     = TdsLabelAdapter.getUniqueInstance();
	}

	@Override
	public void registerVideo(Video v) {
		Entidad eVideo = null;
		try {
			eVideo = servPersistencia.recuperarEntidad(v.getCode());
		} catch (NullPointerException e) {
		}
		if (eVideo != null)
			return;

		// Register labels
		for (Label label : v.getLabels())
			labelAdapter.registerLabel(label);

		// Create video entity
		eVideo = new Entidad();
		eVideo.setNombre("video");
		eVideo.setPropiedades(
			new ArrayList<Propiedad>(
				Arrays.asList(
					new Propiedad("url",                     v.getUrl()),
					new Propiedad("title",                   v.getTitle()),
					new Propiedad("numViews", String.valueOf(v.getNumViews())),
					new Propiedad("labels",   getCodesLabels(v.getLabels()))
				)
			)
		);

		// Register video entity
		eVideo = servPersistencia.registrarEntidad(eVideo);

		// Unique identifier
		v.setCode(eVideo.getId());
	}

	@Override
	public void removeVideo(Video v) {
		TdsLabelAdapter labelAdapter = TdsLabelAdapter.getUniqueInstance();

		// Remove labels in the video
		for (Label label : v.getLabels())
			labelAdapter.removeLabel(label);

		servPersistencia.borrarEntidad(
			servPersistencia.recuperarEntidad(v.getCode())
		);
	}

	@Override
	public void modifyVideo(Video v) {
		Entidad eVideo;
		eVideo = servPersistencia.recuperarEntidad(v.getCode());
		
		modifyLabels(loadVideo(v.getCode()), v);

		for (Propiedad prop: eVideo.getPropiedades()) {
			modifyField(prop, "url",                     v.getUrl());
			modifyField(prop, "title",                   v.getTitle());
			modifyField(prop, "numViews", String.valueOf(v.getNumViews()));
			modifyField(prop, "labels",   getCodesLabels(v.getLabels()));
			servPersistencia.modificarPropiedad(prop);
		}
	}
	
	

	@Override
	public Video loadVideo(int code) {
		Entidad eVideo   = servPersistencia.recuperarEntidad(code);
		
		Video video = new Video(
			getFieldValue(eVideo, "url"),
			getFieldValue(eVideo, "title"),
			Integer.parseInt(getFieldValue(eVideo, "numViews"))
		);
		video.setCode(code);

		// Load all labels
		List<Label> labels = getLabelsFromCodes(getFieldValue(eVideo, "labels"));
		
		for (Label label : labels)
			video.addLabel(label);

		return video;
	}

	@Override
	public List<Video> loadAllVideos() {
		List<Video>   videos  = new LinkedList<Video>();
		List<Entidad> eVideos = servPersistencia.recuperarEntidades("video");
		
		for (Entidad eVideo: eVideos)
			videos.add(loadVideo(eVideo.getId()));
		
		return videos;
	}

	/* Auxiliar functions */
	/* Removes the deleted labels and registers the new ones */
	private void modifyLabels(Video oldVideo, Video newVideo) {
		// We will store the labels in two hash sets for rapidly
		// computing whether some label belongs to both videos.
		Set<Integer> oldLabels = new HashSet<Integer>();
		Set<Integer> newLabels = new HashSet<Integer>();

		// Populate the sets with each video's labels.
		for (Label label: oldVideo.getLabels())
			oldLabels.add(label.getCode());
		
		for (Label label: newVideo.getLabels())
			newLabels.add(label.getCode());
		
		// Register the added labels, remove the deleted ones.
		for (Label label : newVideo.getLabels())
			if(!oldLabels.contains(label.getCode()))
				labelAdapter.registerLabel(label);
		
		for (Label label: oldVideo.getLabels())
			if(!newLabels.contains(label.getCode()))
				labelAdapter.removeLabel(label);
	}
	
	
	private String getCodesLabels(Set<Label> labels) {
		String out = "";
		for (Label label : labels)
			out += String.valueOf(label.getCode()) + " ";
		return out.trim();
	}

	private List<Label> getLabelsFromCodes(String labels) {
		List<Label>     labelList = new LinkedList<Label>();
		StringTokenizer strTok    = new StringTokenizer(labels, " ");
		while (strTok.hasMoreTokens()) {
			labelList.add(
				labelAdapter.loadLabel(
					Integer.valueOf(
						(String) strTok.nextElement()
					)
				)
			);
		}
		return labelList;
	}

	private void modifyField(Propiedad prop, String fieldName, String newValue) {
		if (prop.getNombre().equals(fieldName))
			prop.setValor(newValue);
	}
	

	private String getFieldValue(Entidad entity, String field) {
		return servPersistencia.recuperarPropiedadEntidad(entity, field);
	}
}

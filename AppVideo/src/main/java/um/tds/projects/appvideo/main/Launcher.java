package um.tds.projects.appvideo.main;

import java.awt.EventQueue;
import java.util.List;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.video.VideoWeb;
import um.tds.projects.appvideo.view.MainWindow;

public class Launcher {
	private static VideoWeb videoWeb;
	public static void main(String[] args) {
		/*
		ServicioPersistencia sp = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		
		try {
			sp.recuperarEntidades();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Too bad");
		}
		
		System.out.println("???");
		*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					videoWeb = new VideoWeb(); 
					MainWindow window = new MainWindow(videoWeb);
					window.showWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

package um.tds.projects.appvideo.main;

import java.awt.EventQueue;
import java.util.logging.Logger;

import tds.video.VideoWeb;
import um.tds.projects.appvideo.view.MainWindow;

public class Launcher {
	
	private static Logger   logger = Logger.getLogger("um.tds.projects.appvideo.controller.controller");
	private static VideoWeb videoWeb;
	
	public static void main(String[] args) {

		logger.info("Launching application");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					logger.info("Loading VideoWeb");
					videoWeb = new VideoWeb();
					logger.info("VideoWeb loaded");
					MainWindow window = new MainWindow(videoWeb);
					window.showWindow();
				} catch (Exception e) {
					logger.severe("The application could not be started");
					e.printStackTrace();
				}
			}
		});
		logger.info("Finished launch");
	}
}
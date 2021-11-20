package um.tds.projects.appvideo.main;

import java.awt.EventQueue;

import um.tds.projects.appvideo.view.MainWindow;

public class Launcher {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.showWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Video;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;


@SuppressWarnings("serial")
public class VideoViewingPanel extends JPanel {

	private MainWindow mainWindow;
	private Video  video;
	private JPanel toolbarPanel;
	private JPanel searchBar;
	private JPanel contentPanel;
	private LabelPanel labelPanel;
	
	
	public VideoViewingPanel(MainWindow mainWindow, Video video) {
		this.mainWindow = mainWindow;
		this.video = video;
		addComponents();
	}
	
	private void addComponents() {
		setBackground(Constants.BACKGROUND_COLOR);
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());

		toolbarPanel = new ToolbarPanel(mainWindow);
		searchBar 	 = new SearchBar   (Constants.BACKGROUND_COLOR);
		contentPanel = defineContentPanel();

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Constants.BACKGROUND_COLOR);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		centerPanel.add(searchBar);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		centerPanel.add(contentPanel);

		add(toolbarPanel, BorderLayout.WEST);
		add(centerPanel,  BorderLayout.CENTER);
	}

	private JPanel defineContentPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Constants.BACKGROUND_COLOR);
		panel.setAlignmentX(CENTER_ALIGNMENT);
		fixSize(panel, Constants.PAGE_WIDTH, 300);

		JLabel title = new JLabel(video.getTitle());
		title.setFont(Constants.TITLE_FONT);
		title.setBackground(Constants.FONT_COLOR);
		title.setForeground(Constants.FONT_COLOR);
		title.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel hPanel = new JPanel();
		hPanel.setBackground(Constants.BACKGROUND_COLOR);
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));
		hPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel image = new JPanel(); // Esto deberá ser eventualmente sustituído por el reproductor de vídeo.
		image.setBackground(new Color(0, 0, 0));
		image.add(Box.createRigidArea(new Dimension(Constants.VIDEO_WIDTH, Constants.VIDEO_HEIGHT)));
		fixSize(image, Constants.VIDEO_WIDTH, Constants.VIDEO_HEIGHT);
		image.setAlignmentY(TOP_ALIGNMENT);
		
		JPanel vPanel = new JPanel();
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));
		vPanel.setAlignmentY(TOP_ALIGNMENT);
		vPanel.setBackground(Constants.BACKGROUND_COLOR);
		
		JLabel numViews = new JLabel(Integer.toString(video.getNumViews()) + " views");
		numViews.setFont(Constants.ITALIC_FONT);
		numViews.setBackground(Constants.LIGHT_FONT_COLOR);
		numViews.setForeground(Constants.LIGHT_FONT_COLOR);
		numViews.setAlignmentX(LEFT_ALIGNMENT);
		
		vPanel.add(numViews);
		addSeparator(vPanel, Constants.BACKGROUND_COLOR);
		
		hPanel.add(image);
		hPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		hPanel.add(vPanel);
		
		panel.add(title);
		addSeparator(panel, new Color(255, 255, 255));
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(hPanel);
		
		return panel;
	}
	
	static private void addSeparator(JPanel panel, Color color) {
		JSeparator sep = new JSeparator();
		sep.setMaximumSize(new Dimension(Short.MAX_VALUE, Constants.SEPARATOR_HEIGHT));
		sep.setBackground(color);
		sep.setForeground(color);
		panel.add(sep);
	}
	
	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}

}

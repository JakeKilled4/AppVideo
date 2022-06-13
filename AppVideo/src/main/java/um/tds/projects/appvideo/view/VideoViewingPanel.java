package um.tds.projects.appvideo.view;

import um.tds.projects.appvideo.backend.Label;
import um.tds.projects.appvideo.backend.Video;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JComponent;
import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JSeparator;
import javax.swing.JTextField;

import um.tds.projects.appvideo.controller.Controller;
import tds.video.VideoWeb;


@SuppressWarnings("serial")
public class VideoViewingPanel extends CommonPanel  {

	private Video  video;
	private VideoWeb videoWeb;
	private JTextField textField;
	private JPanel rightPanel;
	private Controller controller;

	public VideoViewingPanel(MainWindow mainWindow, Video video) {
		super(mainWindow);
		this.video = video;
		this.videoWeb = mainWindow.getVideoWeb();
		this.controller = Controller.getUniqueInstance();
		this.controller.addViewToVideo(video);
		createScreen();
	}

	@Override
	protected JPanel createInnerPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Constants.BACKGROUND_COLOR);
		panel.setAlignmentX(CENTER_ALIGNMENT);
		fixSize(panel, Constants.PAGE_WIDTH, 310);

		JLabel title = new JLabel(video.getTitle());
		title.setFont(Constants.TITLE_FONT);
		title.setBackground(Constants.FONT_COLOR);
		title.setForeground(Constants.FONT_COLOR);
		title.setAlignmentX(LEFT_ALIGNMENT);

		JPanel hPanel = new JPanel();
		hPanel.setBackground(Constants.BACKGROUND_COLOR);
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));
		hPanel.setAlignmentX(LEFT_ALIGNMENT);

		rightPanel = new JPanel();
		FlowLayout layout = new FlowLayout();
	    layout.setHgap(10);              
	    layout.setVgap(10);
	     
		rightPanel.setLayout(layout);
		rightPanel.setBackground(Constants.BACKGROUND_COLOR);
		fixSize(rightPanel,400,200);
		
		for(Label label : video.getLabels()) 
			addLabelToVideo(label);

		hPanel.add(videoWeb);
		hPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		hPanel.add(rightPanel);

		
		JLabel numViews = new JLabel(Integer.toString(video.getNumViews()) + " views");
		numViews.setFont(Constants.ITALIC_FONT);
		numViews.setBackground(Constants.LIGHT_FONT_COLOR);
		numViews.setForeground(Constants.LIGHT_FONT_COLOR);
		numViews.setAlignmentX(LEFT_ALIGNMENT);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Label l = new Label(textField.getText());
				if(controller.addLabelToVideo(video, l)) {
					addLabelToVideo(l);
					textField.setText("");
					validate();
				}
			}
		});
		
		textField.setBackground(Constants.SEARCH_COLOR);
		textField.setForeground(Constants.FONT_COLOR);
		textField.setFont(Constants.DEFAULT_FONT);
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setCaretColor(Constants.FONT_COLOR);
		textField.setText("Add label...");
		textField.setMinimumSize(new Dimension(textField.getWidth(),25));
		textField.setPreferredSize(new Dimension(textField.getWidth(),25));
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(textField.getText().trim().equals("")) textField.setText("Add label...");
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				  if(textField.getText().trim().equals("Add label...")) textField.setText("");
			}
		});
		
		panel.add(title);
		addSeparator(panel, Color.WHITE);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(hPanel);
		panel.add(numViews);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		panel.add(textField);

		// Start playing the video
		videoWeb.playVideo(video.getUrl());
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
	
	private void addLabelToVideo(Label label) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBackground(Constants.BUTTON_HOVER_COLOR);
		JLabel l = new JLabel(label.getName());
		l.setForeground(Constants.FONT_COLOR);
		l.setFont(new Font("Default", Font.ITALIC, 15));
		p.add(Box.createRigidArea(new Dimension(10, 10)));
		p.add(l);
		p.add(Box.createRigidArea(new Dimension(10, 10)));
		rightPanel.add(p);
	}
}

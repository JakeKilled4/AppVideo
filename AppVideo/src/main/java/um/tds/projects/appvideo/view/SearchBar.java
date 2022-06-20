package um.tds.projects.appvideo.view;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import um.tds.projects.appvideo.controller.Controller;

@SuppressWarnings("serial")
public class SearchBar extends JPanel{
	
	private JTextField txtSearch;
	private ComponentFactory componentFactory;
	private Controller controller;
	
	public SearchBar(Color backgroundColor){
		this.componentFactory = ComponentFactory.getUniqueInstance();
		this.controller = Controller.getUniqueInstance();
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(Short.MAX_VALUE,25));
		setBackground(backgroundColor);
		
		add(Box.createRigidArea(new Dimension(10, 0)));
		
		//JButton btnSearch = new JButton("Search");
		//add(btnSearch);
	
		//add(Box.createRigidArea(new Dimension(5, 0)));
		
		txtSearch = componentFactory.specialTextField("Search...");
		txtSearch.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtSearch.getText().trim().equals("")) txtSearch.setText("Search...");
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				  if(txtSearch.getText().trim().equals("Search...")) txtSearch.setText("");
			}
		});
		txtSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = txtSearch.getText();
				txtSearch.setText("Search...");
				if(text.trim().equals("Search..."))
					controller.searchVideos("");
				else controller.searchVideos(text);
				validate();
			}
		});
		add(txtSearch);
		
		add(Box.createRigidArea(new Dimension(10, 0)));
	}
}

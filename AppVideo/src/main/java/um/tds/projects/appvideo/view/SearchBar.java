package um.tds.projects.appvideo.view;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchBar extends JPanel{
	
	private JTextField txtSearch;
	private ComponentFactory componentFactory;
	
	public SearchBar(Color backgroundColor){
		this.componentFactory = ComponentFactory.getUniqueInstance();
		
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
		add(txtSearch);
		
		add(Box.createRigidArea(new Dimension(10, 0)));
	}
}

package um.tds.projects.appvideo.view;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchBar extends JPanel{
	
	private JTextField txtSearch;
	
	public SearchBar(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(Short.MAX_VALUE,25));
		setBackground(Constants.BACKGROUND_COLOR);
		
		add(Box.createRigidArea(new Dimension(7, 0)));
		
		JButton btnSearch = new JButton("Search");
		add(btnSearch);
		
		add(Box.createRigidArea(new Dimension(5, 0)));
		
		txtSearch = new JTextField();
		txtSearch.setText("Search");
		txtSearch.setColumns(10);
		txtSearch.setBackground(Constants.SEARCH_COLOR);
		txtSearch.setForeground(Constants.FONT_COLOR);
		txtSearch.setFont(Constants.DEFAULT_FONT);
		txtSearch.setBorder(BorderFactory.createEmptyBorder());
		add(txtSearch);
		add(Box.createRigidArea(new Dimension(7, 0)));
	}
}

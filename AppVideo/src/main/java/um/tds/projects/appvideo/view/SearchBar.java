package um.tds.projects.appvideo.view;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchBar extends JPanel{
	
	private JTextField txtSearch;
	
	public SearchBar(Color backgroundColor){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(Short.MAX_VALUE,25));
		setBackground(backgroundColor);
		
		add(Box.createRigidArea(new Dimension(10, 0)));
		
		//JButton btnSearch = new JButton("Search");
		//add(btnSearch);
	
		//add(Box.createRigidArea(new Dimension(5, 0)));
		
		txtSearch = new JTextField();
		txtSearch.setColumns(10);
		txtSearch.setBackground(Constants.SEARCH_COLOR);
		txtSearch.setForeground(Constants.FONT_COLOR);
		txtSearch.setFont(Constants.DEFAULT_FONT);
		txtSearch.setBorder(BorderFactory.createEmptyBorder());
		txtSearch.setCaretColor(Constants.FONT_COLOR);
		txtSearch.setText("Search...");
		txtSearch.setMinimumSize(new Dimension(txtSearch.getWidth(),25));
		txtSearch.setPreferredSize(new Dimension(txtSearch.getWidth(),25));
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

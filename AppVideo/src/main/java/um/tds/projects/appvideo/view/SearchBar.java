package um.tds.projects.appvideo.view;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchBar extends JPanel{
	
	private JTextField textField;
	
	public SearchBar(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(Short.MAX_VALUE,25));
		JButton btnSearch = new JButton("Search");
		add(btnSearch);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
	}
}

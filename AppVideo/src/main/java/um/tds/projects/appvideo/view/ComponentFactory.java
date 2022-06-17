package um.tds.projects.appvideo.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;


public class ComponentFactory {

	private static ComponentFactory instance;
	
	private ComponentFactory() {}

	public static ComponentFactory getUniqueInstance() {
		if (instance == null)
			instance = new ComponentFactory();
		return instance;
	}

	public JLabel specialLabel(String name) {
		JLabel label = new JLabel(name);
		label.setBackground(Constants.FOREGROUND_COLOR);
		label.setFont(Constants.DEFAULT_FONT);
		label.setForeground(Constants.FONT_COLOR);
		return label;
	}
	
	public JTextField specialTextField(String name) {
		JTextField tf = new JTextField();
		tf.setColumns(10);
		tf.setBackground(Constants.SEARCH_COLOR);
		tf.setForeground(Constants.FONT_COLOR);
		tf.setFont(Constants.DEFAULT_FONT);
		tf.setBorder(BorderFactory.createEmptyBorder());
		tf.setCaretColor(Constants.FONT_COLOR);
		tf.setMinimumSize(new Dimension(tf.getWidth(),25));
		tf.setPreferredSize(new Dimension(tf.getWidth(),25));
		if(name != null) tf.setText(name);
		return tf;
	}
	
	public JPasswordField specialPasswordField(String name) {
		JPasswordField pf = new JPasswordField();
		pf.setColumns(10);
		pf.setBackground(Constants.SEARCH_COLOR);
		pf.setForeground(Constants.FONT_COLOR);
		pf.setFont(Constants.DEFAULT_FONT);
		pf.setBorder(BorderFactory.createEmptyBorder());
		pf.setCaretColor(Constants.FONT_COLOR);
		pf.setMinimumSize(new Dimension(pf.getWidth(), 25));
		pf.setPreferredSize(new Dimension(pf.getWidth(), 25));
		if(name != null) pf.setText(name);
		return pf;
	}
	
	public JDateChooser specialDateChooser(Date d) {
		JDateChooser date = new JDateChooser();
		for(Component c : date.getComponents()) {
			c.setBackground(Constants.SEARCH_COLOR);
			c.setForeground(Constants.FONT_COLOR);
		}
		JTextFieldDateEditor e =  (JTextFieldDateEditor) date.getDateEditor();
		e.setBorder(BorderFactory.createEmptyBorder());
		e.setFont(Constants.DEFAULT_FONT);
		e.setCaretColor(Constants.FONT_COLOR);
		if(d !=  null) e.setDate(d);
		
		date.setBackground(Constants.SEARCH_COLOR);
		date.setForeground(Constants.FONT_COLOR);
		date.setFont(Constants.DEFAULT_FONT);
		date.setBorder(BorderFactory.createEmptyBorder());
		date.setMinimumSize(new Dimension(date.getWidth(),25));
		date.setPreferredSize(new Dimension(date.getWidth(),25));
		return date;
	}
}

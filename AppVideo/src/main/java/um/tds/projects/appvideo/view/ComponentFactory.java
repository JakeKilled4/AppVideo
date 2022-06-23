package um.tds.projects.appvideo.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;


/*
 * Creates several custom components with an appearance tailored for AppVideo's needs.
 */
public class ComponentFactory {

	private static ComponentFactory instance;
	
	private ComponentFactory() {}

	public static ComponentFactory getUniqueInstance() {
		if (instance == null) {
			instance = new ComponentFactory();
		}

		return instance;
	}

	public JComboBox<String> specialComboBox(String options[]){
		JComboBox<String> comboBox = new JComboBox<String>(options);

		comboBox.setBackground(Constants.FOREGROUND_COLOR);
		comboBox.setFont      (Constants.DEFAULT_FONT);
		comboBox.setForeground(Constants.FONT_COLOR);

		return comboBox;
	}

	public JLabel specialLabel(String name) {
		JLabel label = new JLabel(name);

		label.setBackground(Constants.FOREGROUND_COLOR);
		label.setFont      (Constants.DEFAULT_FONT);
		label.setForeground(Constants.FONT_COLOR);

		return label;
	}
	
	public JTextField specialTextField(String defaultText, boolean deleteDefaultTextWhenSelected) {
		JTextField tf = new JTextField();

		tf.setColumns      (10);
		tf.setBackground   (Constants.    SEARCH_COLOR);
		tf.setForeground   (Constants.    FONT_COLOR);
		tf.setFont         (Constants.    DEFAULT_FONT);
		tf.setBorder       (BorderFactory.createEmptyBorder());
		tf.setCaretColor   (Constants.    FONT_COLOR);
		tf.setMinimumSize  (new Dimension(tf.getWidth(), 25));
		tf.setPreferredSize(new Dimension(tf.getWidth(), 25));

		if (defaultText != null) {

			// If there is a default text, add it.
			tf.setText(defaultText);
		
			// The following focus listener deletes the default text whenever
			// the user enters the text field, and restores it whenever the
			// text field is abandoned.
			if (deleteDefaultTextWhenSelected) {
				tf.addFocusListener(
					new FocusListener() {
						@Override
						public void focusLost(FocusEvent e) {
							if (tf.getText().trim().equals("")) {
								tf.setText(defaultText);
							}
						}
						
						@Override
						public void focusGained(FocusEvent e) {
							if(tf.getText().trim().equals(defaultText)) {
								tf.setText("");
							}
						}
					}
				);
			}
		}

		return tf;
	}
	
	public JPasswordField specialPasswordField(String defaultText) {
		JPasswordField pf = new JPasswordField();

		pf.setColumns      (10);
		pf.setBackground   (Constants.    SEARCH_COLOR);
		pf.setForeground   (Constants.    FONT_COLOR);
		pf.setFont         (Constants.    DEFAULT_FONT);
		pf.setBorder       (BorderFactory.createEmptyBorder());
		pf.setCaretColor   (Constants.    FONT_COLOR);
		pf.setMinimumSize  (new Dimension(pf.getWidth(), 25));
		pf.setPreferredSize(new Dimension(pf.getWidth(), 25));

		if (defaultText != null) {
			pf.setText(defaultText);
		}

		return pf;
	}
	
	public JDateChooser specialDateChooser(Date d) {
		JDateChooser date = new JDateChooser();

		// Change the appearance of all the JDateChooser's elements.
		for (Component c : date.getComponents()) {
			c.setBackground(Constants.SEARCH_COLOR);
			c.setForeground(Constants.FONT_COLOR);
		}

		// Config the inner date editor.
		JTextFieldDateEditor e =  (JTextFieldDateEditor) date.getDateEditor();
		e.setBorder    (BorderFactory.createEmptyBorder());
		e.setFont      (Constants.DEFAULT_FONT);
		e.setCaretColor(Constants.FONT_COLOR);
		if (d !=  null) {
			e.setDate(d);
		}
		
		date.setBackground   (Constants.SEARCH_COLOR);
		date.setForeground   (Constants.FONT_COLOR);
		date.setFont         (Constants.DEFAULT_FONT);
		date.setBorder       (BorderFactory.createEmptyBorder());
		date.setMinimumSize  (new Dimension(date.getWidth(), 25));
		date.setPreferredSize(new Dimension(date.getWidth(), 25));

		return date;
	}

	public JCheckBox specialCheckBox(String label) {
		JCheckBox cb = new JCheckBox(label);

		cb.setBackground(Constants.FOREGROUND_COLOR);
		cb.setFont      (Constants.DEFAULT_FONT);
		cb.setForeground(Constants.FONT_COLOR);

		return cb;
	}

}

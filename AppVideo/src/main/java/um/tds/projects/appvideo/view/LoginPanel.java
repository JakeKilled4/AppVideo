package um.tds.projects.appvideo.view;


import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Box.Filler;
import java.awt.Color;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class LoginPanel extends JPanel
{    
	private JTextField textField;
	private JTextField textField_1;

    LoginPanel() {
    	setBackground(Color.ORANGE);
    	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    	
		Dimension miDim = new Dimension(1,1);
		Dimension maxDim = new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
		Dimension preDim = new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
		
		
		add(new Box.Filler(miDim, preDim, maxDim));
		
		JPanel panel_0 = new JPanel();
		panel_0.setBackground(Color.ORANGE);
		panel_0.setLayout(new BoxLayout(panel_0, BoxLayout.Y_AXIS));
		
		panel_0.add(new Box.Filler(miDim, preDim, maxDim));
		
		// Panel horiontal de usuario
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.ORANGE);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JLabel lblUserame = new JLabel("Userame:");
		panel_1.add(lblUserame);
		
		textField = new JTextField();
		textField.setColumns(10);
		panel_1.add(textField);
		
		panel_0.add(panel_1);
		
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));
		
		// Panel horiontal de password
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.ORANGE);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JLabel lblPassword = new JLabel("Password:");
		panel_2.add(lblPassword);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		panel_2.add(textField_1);
		
		panel_0.add(panel_2);
		
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));
		
		// Boton de login y register
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.ORANGE);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		JButton btnLogin = new JButton("Login");
		JButton btnRegister = new JButton("Register");
		panel_3.add(btnLogin);
		panel_3.add(Box.createRigidArea(new Dimension(20,20)));
		panel_3.add(btnRegister);
		panel_0.add(panel_3);
		
		Filler filler = new Box.Filler(miDim, preDim, maxDim);
		filler.setBackground(Color.WHITE);
		panel_0.add(filler);
		
		add(panel_0);
		add(new Box.Filler(miDim, preDim, maxDim));

    }
}

package um.tds.projects.appvideo.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import um.tds.projects.appvideo.controller.Controller;


@SuppressWarnings("serial")
public class RegisterPanel extends JPanel {

	private MainWindow mainWindow;
	private Controller controller;

	private ComponentFactory componentFactory;

    public RegisterPanel(MainWindow mainWindow) {
    	this.mainWindow = mainWindow;
		this.controller = Controller.getUniqueInstance();
		this.componentFactory = ComponentFactory.getUniqueInstance();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	setBackground(Constants.BACKGROUND_COLOR);
    	
    	addComponents();
    }
    
    private void addComponents() {
    	
    	JPanel panel1 = new JPanel();
    	panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
    	panel1.setBackground(Constants.BACKGROUND_COLOR);
    	
    	JPanel panel2 = new JPanel();
    	panel2.setLayout(new GridLayout(7,2,10,10));
    	panel2.setBackground(Constants.BACKGROUND_COLOR);
    	
    	JLabel appTitle = new JLabel("AppVideo");
    	appTitle.setFont(new Font("Default", Font.ITALIC, 50));
    	appTitle.setForeground(Constants.FONT_COLOR);
    	appTitle.setBackground(Constants.BACKGROUND_COLOR);
    	panel1.add(appTitle);
    	
    	JLabel nameLbl = componentFactory.specialLabel("*Name:");
    	JTextField nameField = componentFactory.specialTextField(null);
    	
    	JLabel surnameLbl = componentFactory.specialLabel("Surname:");
    	JTextField surnameField = componentFactory.specialTextField(null);
    	
    	JLabel dateOfBirthLbl = componentFactory.specialLabel("*Day of birth:");
    	JDateChooser dateOfBirthField = componentFactory.specialDateChooser(null);
    	
    	JLabel emailLbl = componentFactory.specialLabel("Email:");
    	JTextField emailField = componentFactory.specialTextField(null);
    	
    	JLabel usernameLbl = componentFactory.specialLabel("*Username:");
    	JTextField usernameField = componentFactory.specialTextField(null);
    	
    	JLabel passwordLbl = componentFactory.specialLabel("*Password:");
    	JPasswordField passwordField = componentFactory.specialPasswordField(null);
    	
    	JLabel confirmPasswordLbl = componentFactory.specialLabel("*Confirm Password:");
    	JPasswordField confirmPasswordField = componentFactory.specialPasswordField(null);
    	
    	panel2.add(nameLbl);				panel2.add(nameField);
    	panel2.add(surnameLbl);				panel2.add(surnameField);
    	panel2.add(dateOfBirthLbl);			panel2.add(dateOfBirthField);
    	panel2.add(emailLbl);				panel2.add(emailField);
    	panel2.add(usernameLbl); 			panel2.add(usernameField);
    	panel2.add(passwordLbl);			panel2.add(passwordField);
    	panel2.add(confirmPasswordLbl); 	panel2.add(confirmPasswordField);
    	
    	panel2.setMinimumSize(new Dimension(500,200));
    	panel2.setPreferredSize(new Dimension(500,200));
    	panel2.setMaximumSize(new Dimension(500,200));
    	
    	JPanel panel3 = new JPanel();
    	panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
    	panel3.setBackground(Constants.BACKGROUND_COLOR);
    	JButton registerBtn = new JButton("Register");
    	
    	registerBtn.addActionListener(e -> {
			String name = nameField.getText();
			String surname = surnameField.getText();
			Date dateOfBirth = dateOfBirthField.getDate();
			String email = emailField.getText();
			String username = usernameField.getText();
			String password = String.valueOf(passwordField.getPassword());
			String copassword = String.valueOf(confirmPasswordField.getPassword());
			
			
			
			if(name.isBlank() || username.isBlank() || dateOfBirth == null ||  password.isBlank()) {
				mainWindow.showPopUp("Error","Complete correctly all the mandatory fields", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!password.equals(copassword)) {
				passwordField.setText("");
				confirmPasswordField.setText("");
				mainWindow.showPopUp("Error","Passwords doesn't match", JOptionPane.ERROR_MESSAGE);
				return;
			}

			boolean registerOk = controller.register(
				name, surname, dateOfBirth, email,
				username, password);
			
			if (registerOk) {
				nameField.setText("");
	    		surnameField.setText("");
	    		dateOfBirthField.setCalendar(null);
	    		emailField.setText("");
	    		usernameField.setText("");
	    		passwordField.setText("");
	    		confirmPasswordField.setText("");
				mainWindow.enterApp();
			}
			else {
				usernameField.setText("");
				mainWindow.showPopUp("Error","This username is already taken", JOptionPane.ERROR_MESSAGE);
			}
		});
    	
    	JButton cancelBtn = new JButton("Cancel");
    	cancelBtn.addActionListener(e -> {
    		nameField.setText("");
    		surnameField.setText("");
    		dateOfBirthField.setCalendar(null);
    		emailField.setText("");
    		usernameField.setText("");
    		passwordField.setText("");
    		confirmPasswordField.setText("");
			this.mainWindow.activateLoginPanel();
		});
    	
    	JPanel panel4 = new JPanel();
    	panel4.setBackground(Constants.BACKGROUND_COLOR);
    	panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
    	JLabel mandatoryFieldsLbl = componentFactory.specialLabel("*Mandatory field");
    	panel4.add(mandatoryFieldsLbl);
    	
    	panel3.add(registerBtn);
    	panel3.add(Box.createRigidArea(new Dimension(20,0)));
    	panel3.add(cancelBtn);
    	
    	this.add(Box.createRigidArea(new Dimension(0,150)));
    	this.add(panel1);
    	this.add(Box.createRigidArea(new Dimension(0,150)));
    	this.add(panel2);
    	this.add(Box.createRigidArea(new Dimension(0,40)));
    	this.add(panel3);
    	this.add(Box.createRigidArea(new Dimension(0,40)));
    	this.add(panel4);
	}
}

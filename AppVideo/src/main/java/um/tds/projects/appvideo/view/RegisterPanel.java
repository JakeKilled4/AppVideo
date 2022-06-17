package um.tds.projects.appvideo.view;

import java.awt.Color;
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
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;

import um.tds.projects.appvideo.controller.Controller;

import javax.swing.Box.Filler;


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

    	/*
		Dimension miDim = new Dimension(1,1);
		Dimension maxDim = new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
		Dimension preDim = new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);

		add(new Box.Filler(miDim, preDim, maxDim));

		JPanel panel_0 = new JPanel();
		panel_0.setBackground(Constants.BLOGIN_COLOR);
		panel_0.setLayout(new BoxLayout(panel_0, BoxLayout.Y_AXIS));
		panel_0.add(new Box.Filler(miDim, preDim, maxDim));

		// Name 
		JPanel panel_name = new JPanel();
		panel_name.setBackground(Constants.BLOGIN_COLOR);
		panel_name.setLayout(new BoxLayout(panel_name, BoxLayout.X_AXIS));

		JLabel lblName = new JLabel("*Name:");
		lblName.setForeground(Constants.FONT_COLOR);
		panel_name.add(lblName);

		nameField = new JTextField();
		nameField.setColumns(10);

		panel_name.add(nameField);
		panel_0.add(panel_name);
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));

		// Surname
		JPanel panel_surname = new JPanel();
		panel_surname.setBackground(Constants.BLOGIN_COLOR);
		panel_surname.setLayout(new BoxLayout(panel_surname, BoxLayout.X_AXIS));

		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setForeground(Constants.FONT_COLOR);
		panel_surname.add(lblSurname);

		surnameField = new JTextField();
		surnameField.setColumns(10);

		panel_surname.add(surnameField);
		panel_0.add(panel_surname);
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));

		// Day of birth 
		JPanel panel_date = new JPanel();
		panel_date.setBackground(Constants.BLOGIN_COLOR);
		panel_date.setLayout(new BoxLayout(panel_date, BoxLayout.X_AXIS));

		JLabel lblDate = new JLabel("*Day of birth:");
		lblDate.setForeground(Constants.FONT_COLOR);
		panel_date.add(lblDate);

		date = new JDateChooser();		
		panel_date.add(date);
		panel_0.add(panel_date);
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));

		// Email 
		JPanel panel_email = new JPanel();
		panel_email.setBackground(Constants.BLOGIN_COLOR);
		panel_email.setLayout(new BoxLayout(panel_email, BoxLayout.X_AXIS));

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Constants.FONT_COLOR);
		panel_email.add(lblEmail);

		emailField = new JTextField();
		emailField.setColumns(10);

		panel_email.add(emailField);
		panel_0.add(panel_email);
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));

		// Username 
		JPanel panel_user = new JPanel();
		panel_user.setBackground(Constants.BLOGIN_COLOR);
		panel_user.setLayout(new BoxLayout(panel_user, BoxLayout.X_AXIS));

		JLabel lblUser = new JLabel("*Username:");
		lblUser.setForeground(Constants.FONT_COLOR);
		panel_user.add(lblUser);

		userField = new JTextField();
		userField.setColumns(10);

		panel_user.add(userField);
		panel_0.add(panel_user);
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));

		// Password 
		JPanel panel_pass = new JPanel();
		panel_pass.setBackground(Constants.BLOGIN_COLOR);
		panel_pass.setLayout(new BoxLayout(panel_pass, BoxLayout.X_AXIS));

		JLabel lblPass = new JLabel("*Password:");
		lblPass.setForeground(Constants.FONT_COLOR);
		panel_pass.add(lblPass);

		pwdPass = new JPasswordField();
		pwdPass.setColumns(10);

		panel_pass.add(pwdPass);
		panel_0.add(panel_pass);
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));

		// Confirm passord 
		JPanel panel_co_pass = new JPanel();
		panel_co_pass.setBackground(Constants.BLOGIN_COLOR);
		panel_co_pass.setLayout(new BoxLayout(panel_co_pass, BoxLayout.X_AXIS));

		JLabel lblCoPass = new JLabel("*Confirm password:");
		lblCoPass.setForeground(Constants.FONT_COLOR);
		panel_co_pass.add(lblCoPass);

		pwdCoPass = new JPasswordField();
		pwdCoPass.setColumns(10);

		panel_co_pass.add(pwdCoPass);
		panel_0.add(panel_co_pass);
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));

		// Register and cancel buttons
		JPanel panel_btn_re = new JPanel();
		panel_btn_re.setBackground(Constants.BLOGIN_COLOR);
		panel_btn_re.setLayout(new BoxLayout(panel_btn_re, BoxLayout.X_AXIS));
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(e -> {
			String name = nameField.getText();
			String surname = surnameField.getText();
			Date dateOfBirth = date.getDate();
			String email = emailField.getText();
			String username = userField.getText();
			String password = String.valueOf(pwdPass.getPassword());
			String copassword = String.valueOf(pwdCoPass.getPassword());
			
			if(name.isBlank() || username.isBlank() || dateOfBirth == null ||  password.isBlank()) {
				error.setText("Complete correctly all the mandatory fields");
				error.setVisible(true);
				return;
			}
			
			if (!password.equals(copassword)) {
				pwdPass.setText("");
				pwdCoPass.setText("");
				error.setText("Passwords doesn't match");
				error.setVisible(true);
				return;
			}

			boolean registerOk = controller.register(
				name, surname, dateOfBirth, email,
				username, password);
			
				resetFields();
			
			if (registerOk) mainWindow.enterApp();
			else {
				error.setText("This username is already taken");
				error.setVisible(true);
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			resetFields();
			error.setVisible(false);
			this.mainWindow.activateLoginPanel();
		});
		
		panel_btn_re.add(btnRegister);
		panel_btn_re.add(Box.createRigidArea(new Dimension(20,20)));
		panel_btn_re.add(btnCancel);
		panel_0.add(panel_btn_re);

		// Label mandatory fields
		JLabel lblMandatoryField = new JLabel("*Mandatory field");
		lblMandatoryField.setForeground(Constants.FONT_COLOR);
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Constants.BLOGIN_COLOR);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		panel_1.add(lblMandatoryField);
		panel_0.add(Box.createRigidArea(new Dimension(20,20)));
		panel_0.add(panel_1);
		
		// Label error
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setVisible(false);
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Constants.BLOGIN_COLOR);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		panel_2.add(error);
		panel_0.add(panel_2);

		Filler filler = new Box.Filler(miDim, preDim, maxDim);
		filler.setBackground(Color.WHITE);
		panel_0.add(filler);

		add(panel_0);
		add(new Box.Filler(miDim, preDim, maxDim));
		
		*/
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
			
			UIManager.put("OptionPane.background", Constants.BACKGROUND_COLOR);
			UIManager.put("Panel.background", Constants.BACKGROUND_COLOR);
			UIManager.put("OptionPane.messageForeground", Constants.FONT_COLOR);
			
			if(name.isBlank() || username.isBlank() || dateOfBirth == null ||  password.isBlank()) {
				JOptionPane.showMessageDialog(null,"Complete correctly all the mandatory fields","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!password.equals(copassword)) {
				passwordField.setText("");
				confirmPasswordField.setText("");
				JOptionPane.showMessageDialog(null,"Passwords doesn't match","Error",JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null,"This username is already taken","Error",JOptionPane.ERROR_MESSAGE);
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

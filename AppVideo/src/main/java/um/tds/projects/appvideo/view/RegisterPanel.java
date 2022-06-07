package um.tds.projects.appvideo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import um.tds.projects.appvideo.controller.Controller;

import javax.swing.Box.Filler;


@SuppressWarnings("serial")
public class RegisterPanel extends JPanel {

	private MainWindow mainWindow;
	private Controller controller;

	private JTextField nameField;
	private JTextField surnameField;
	private JTextField emailField;
	private JTextField userField;
	private JPasswordField pwdPass;
	private JPasswordField pwdCoPass;
	private JDateChooser date;
	private JLabel error;

    public RegisterPanel(MainWindow mainWindow) {
    	this.mainWindow = mainWindow;
		this.controller = Controller.getUniqueInstance();

    	setBackground(Constants.BLOGIN_COLOR);
    	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

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

		/* Confirm passord */
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
			
			if (registerOk) mainWindow.activatePlaylistsPanel();
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
    }
    
    private void resetFields() {
		nameField.setText("");
		surnameField.setText("");
		date.setCalendar(null);
		emailField.setText("");
		userField.setText("");
		pwdPass.setText("");
		pwdCoPass.setText("");
    }

}

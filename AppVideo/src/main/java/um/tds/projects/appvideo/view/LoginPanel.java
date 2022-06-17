package um.tds.projects.appvideo.view;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import um.tds.projects.appvideo.controller.Controller;
import javax.swing.JPasswordField;


@SuppressWarnings("serial")
public class LoginPanel extends JPanel
{
	private MainWindow mainWindow;
	private Controller controller;
	private ComponentFactory componentFactory;

    public LoginPanel(MainWindow mainWindow) {
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
    	panel2.setLayout(new GridLayout(2,2,10,10));
    	panel2.setBackground(Constants.BACKGROUND_COLOR);
    	
    	JLabel appTitle = new JLabel("AppVideo");
    	appTitle.setFont(new Font("Default", Font.ITALIC, 50));
    	appTitle.setForeground(Constants.FONT_COLOR);
    	appTitle.setBackground(Constants.BACKGROUND_COLOR);
    	panel1.add(appTitle);
    	
    	JLabel usernameLbl = componentFactory.specialLabel("Username:");
    	JTextField usernameField = componentFactory.specialTextField(null);
    	
    	JLabel passwordLbl = componentFactory.specialLabel("Password:");
    	JPasswordField passwordField = componentFactory.specialPasswordField(null);
    	
    	panel2.add(usernameLbl); panel2.add(usernameField);
    	panel2.add(passwordLbl); panel2.add(passwordField);
    	
    	panel2.setMinimumSize(new Dimension(Constants.PAGE_WIDTH/2,50));
    	panel2.setPreferredSize(new Dimension(Constants.PAGE_WIDTH/2,50));
    	panel2.setMaximumSize(new Dimension(Constants.PAGE_WIDTH/2,50));
    	
    	JPanel panel3 = new JPanel();
    	panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
    	panel3.setBackground(Constants.BACKGROUND_COLOR);
    	JButton loginBtn = new JButton("Login");
    	
    	loginBtn.addActionListener(e -> {
			String username = usernameField.getText();
			String password = String.valueOf(passwordField.getPassword());
			boolean loginOk = controller.login(username, password);
			usernameField.setText("");
			passwordField.setText("");
			if (loginOk) mainWindow.enterApp();
			else {
				 UIManager.put("OptionPane.background", Constants.BACKGROUND_COLOR);
				 UIManager.put("Panel.background", Constants.BACKGROUND_COLOR);
				 UIManager.put("OptionPane.messageForeground", Constants.FONT_COLOR);
				 JOptionPane.showMessageDialog(null,"Incorrect username or password","Error",JOptionPane.ERROR_MESSAGE);
			}
		});
    	
    	JButton registerBtn = new JButton("Register");
    	registerBtn.addActionListener(e -> {
			this.mainWindow.activateRegisterPanel();
		});
    	
    	panel3.add(loginBtn);
    	panel3.add(Box.createRigidArea(new Dimension(20,0)));
    	panel3.add(registerBtn);
    	
    	this.add(Box.createRigidArea(new Dimension(0,200)));
    	this.add(panel1);
    	this.add(Box.createRigidArea(new Dimension(0,200)));
    	this.add(panel2);
    	this.add(Box.createRigidArea(new Dimension(0,40)));
    	this.add(panel3);
    	
    }
}

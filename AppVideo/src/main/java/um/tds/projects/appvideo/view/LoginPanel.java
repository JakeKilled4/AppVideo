package um.tds.projects.appvideo.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class LoginPanel extends JPanel
{
	private MainWindow mainWindow;
	private JLabel     loginLabel;
	private JPanel     centerPanel;
	private JButton    loginButton;
	private JButton    registerButton;
	private JLabel     usernameLabel;
	private JLabel     passwordLabel;
	private JTextField usernameText;
	private JTextField passwordText;
	
	public LoginPanel(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		createScreen();
	}
	
	private void createScreen()
	{
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());

		loginLabel = new JLabel("Inicio de sesión", JLabel.CENTER);
		fixSize(loginLabel, Constants.X_SIZE, 60);
		add    (loginLabel, BorderLayout.NORTH);

		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		usernameLabel  = new JLabel("Usuario",    JLabel.RIGHT);
		passwordLabel  = new JLabel("Contraseña", JLabel.RIGHT);
		usernameText   = new JTextField();
		passwordText   = new JTextField();
		loginButton    = new JButton("Login");
		registerButton = new JButton("Registrar");
		fixSize(usernameLabel,  170, 24);
		fixSize(passwordLabel,  170, 24);
		fixSize(usernameText,   300, 24);
		fixSize(passwordText,   300, 24);
		fixSize(loginButton,    100, 30);
		fixSize(registerButton, 100, 30);
		
		centerPanel.add(Box.createRigidArea(new Dimension(Constants.X_SIZE, 75)));
		centerPanel.add(usernameLabel); centerPanel.add(usernameText);
		centerPanel.add(passwordLabel); centerPanel.add(passwordText);
		centerPanel.add(Box.createRigidArea(new Dimension(Constants.X_SIZE, 75)));
		centerPanel.add(Box.createRigidArea(new Dimension(170, 20)));
		centerPanel.add(loginButton);
		centerPanel.add(Box.createRigidArea(new Dimension(90, 20)));
		centerPanel.add(registerButton);
		
		add(centerPanel, BorderLayout.CENTER);
		
		// Dummy implementation: Just start the application.
		// Eventually, this will be the place to check whether the user is
		// actually on the user DB.
		loginButton.addActionListener(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						mainWindow.setSearchPanel();
					}
				});
		registerButton.addActionListener(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						mainWindow.setSearchPanel();
					}
				});
	}
	
	private void fixSize(JComponent component, int x, int y)
	{
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}
}

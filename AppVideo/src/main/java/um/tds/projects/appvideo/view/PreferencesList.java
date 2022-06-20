package um.tds.projects.appvideo.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;


import com.toedter.calendar.JDateChooser;

import pulsador.EncendidoEvent;
import pulsador.Luz;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.controller.Controller;


@SuppressWarnings("serial")
public class PreferencesList extends JPanel {

	private MainWindow mainWindow;
	private JPanel innerPage;
	private Controller controller;
	private ComponentFactory componentFactory;
	private JTextField nameFl;
	private JTextField surnameFl;
	private JTextField usernameFl; 
	private JDateChooser date;
	private JTextField emailFl;
	private JPasswordField newPasswordFl;
	private JPasswordField confirmNewPasswordFl;
	private JCheckBox premium;
	private JButton generatePdf;
	private User u;
	

	public PreferencesList(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.controller = Controller.getUniqueInstance();
		this.componentFactory = ComponentFactory.getUniqueInstance();
		this.u = controller.getCurrentUser();
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout    (new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
	}


	private void addComponents() {
		innerPage = new JPanel();
		innerPage.setBackground(Constants.BACKGROUND_COLOR);
		innerPage.setLayout(new BoxLayout(innerPage, BoxLayout.Y_AXIS));
		innerPage.setMaximumSize(new Dimension(Constants.PAGE_WIDTH, 30));
		innerPage.setMinimumSize(new Dimension(Constants.PAGE_WIDTH, 30));
		innerPage.setPreferredSize(new Dimension(Constants.PAGE_WIDTH, 30));

		addTitle();
		addSeparator();
		addLoadVideo();
		addSeparator();
		addPremiumButton();
		addChangeForm();
		addSaveLogOutButtons();
		addMandatoryFields();
		
		JScrollPane scrollPane = new JScrollPane(innerPage);
		scrollPane.setBackground(Constants.BACKGROUND_COLOR);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane);
	}
	
	private void addChangeForm() {
		
		JPanel p = new JPanel();
		p.setBackground(Constants.FOREGROUND_COLOR);
		p.setLayout(new GridLayout(7, 2, 0, 10));
		
		JLabel nameLbl = this.componentFactory.specialLabel("*Name:");
		nameFl = this.componentFactory.specialTextField(u.getName());
		
		JLabel surnameLbl = this.componentFactory.specialLabel("Surname:");
		surnameFl = this.componentFactory.specialTextField(u.getSurname());
		
		JLabel usernameLbl = componentFactory.specialLabel("*Username:");
    	usernameFl = componentFactory.specialTextField(u.getUsername());
		
		JLabel dayOfBirthLbl = this.componentFactory.specialLabel("*Date Of Birth:");
		date = this.componentFactory.specialDateChooser(u.getDateOfBirth());
		
		JLabel emailLbl = this.componentFactory.specialLabel("Email");
		emailFl = this.componentFactory.specialTextField(u.getEmail());
		
		JLabel newPasswordLbl = this.componentFactory.specialLabel("*Password:");
		newPasswordFl = this.componentFactory.specialPasswordField(u.getPassword());
		
		JLabel confirmNewPasswordLbl = this.componentFactory.specialLabel("*Confirm password:");
		confirmNewPasswordFl = this.componentFactory.specialPasswordField(u.getPassword());
		
		p.add(nameLbl); p.add(nameFl);
		p.add(surnameLbl); p.add(surnameFl);
		p.add(usernameLbl); p.add(usernameFl);
		p.add(dayOfBirthLbl); p.add(date);
		p.add(emailLbl); p.add(emailFl); 
		p.add(newPasswordLbl); p.add(newPasswordFl);
		p.add(confirmNewPasswordLbl); p.add(confirmNewPasswordFl);
		innerPage.add(new PreferencesListEntry(p, 240));
		
	}
	
	private void addTitle() {
		JLabel pageTitle = new JLabel("Preferences");
		pageTitle.setFont(Constants.TITLE_FONT);
		pageTitle.setForeground(Constants.LIGHT_FONT_COLOR);
		innerPage.add(new PreferencesListEntry(pageTitle, Constants.DEFAULT_PREFERENCES_HEIGHT));
	}
	
	private void addSeparator() {
		JSeparator sep = new JSeparator();
		sep.setMaximumSize(new Dimension(Constants.PAGE_WIDTH, Constants.SEPARATOR_HEIGHT));
		sep.setBackground(Color.WHITE);
		sep.setForeground(Color.WHITE);
		innerPage.add(sep);
	}
	
	

	private void addLoadVideo() {
		JPanel loadPanel = new JPanel();
		loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.X_AXIS));
		loadPanel.setAlignmentY(CENTER_ALIGNMENT);
		loadPanel.setBackground(Constants.FOREGROUND_COLOR);
		//JLabel loaderLbl = new JLabel("Load videos:");
		//loaderLbl.setFont(Constants.DComponenteBuscadorVideos c = new ComponenteBuscadorVideos();EFAULT_FONT);
		//loaderLbl.setForeground(Constants.FONT_COLOR);
		
		Luz luz = new Luz();
		luz.setPreferredSize(new Dimension(100, 100));
		luz.setMinimumSize(new Dimension(100, 100));
		luz.setMaximumSize(new Dimension(100, 100));
		luz.setBackground(Constants.FOREGROUND_COLOR);
		luz.setColor(Color.GREEN);
		luz.addEncendidoListener(e -> {
			EncendidoEvent p = (EncendidoEvent)e;
			if(p.getNewEncendido()) {
				this.controller.selectFile();
				luz.setEncendido(false);
			}
			
		});
		
		//loadPanel.add(loaderLbl);
		//loadPanel.add(Box.createRigidArea(new Dimension(100,0)));
		loadPanel.add(luz);
		if(u.isPremium()) {
			generatePdf = new JButton("Generate Pdf");
			loadPanel.add(Box.createRigidArea(new Dimension(200,0)));
			loadPanel.add(generatePdf);
		}
		
		innerPage.add(new PreferencesListEntry(loadPanel, 2*Constants.DEFAULT_PREFERENCES_HEIGHT ));
	}
	
	private void addPremiumButton() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBackground(Constants.FOREGROUND_COLOR);
		premium = componentFactory.specialCheckBox("Premium");
		premium.setSelected(u.isPremium());
		p.add(premium);
		innerPage.add(new PreferencesListEntry(p, 2*Constants.DEFAULT_PREFERENCES_HEIGHT ));
	}
	private void addSaveLogOutButtons() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBackground(Constants.FOREGROUND_COLOR);
		JButton logoutBtn = new JButton("Log out");
		JButton saveChangesBtn = new JButton("Save");
		
		saveChangesBtn.addActionListener(e -> {
			String name = nameFl.getText();
			String surname = surnameFl.getText();
			String username = usernameFl.getText();
			Date dateOfBirth = date.getDate();
			String email = emailFl.getText();
			String password = String.valueOf(newPasswordFl.getPassword());
			String confirmPassword = String.valueOf(confirmNewPasswordFl.getPassword());
			boolean isPremium = premium.isSelected();
						
			if(name.isBlank() || username.isBlank() || dateOfBirth == null ||  password.isBlank()) {
				mainWindow.showPopUp("Error","Complete correctly all the mandatory fields", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!password.equals(confirmPassword)) {
				newPasswordFl.setText(String.valueOf(u.getPassword()));
				confirmNewPasswordFl.setText(String.valueOf(u.getPassword()));
				mainWindow.showPopUp("Error", "Passwords doesn't match", JOptionPane.ERROR_MESSAGE);
				return;
			}

			boolean changeOk = controller.changeUserData(
				name, surname, dateOfBirth, email,
				username, password,isPremium);
			
			if (changeOk) {/*
				nameFl.setText(u.getName());
	    		surnameFl.setText(u.getSurname());
	    		date.setDate(u.getDateOfBirth());
	    		emailFl.setText(u.getEmail());
	    		usernameFl.setText(u.getUsername());
	    		newPasswordFl.setText(u.getPassword());
	    		confirmNewPasswordFl.setText(u.getPassword());
				mainWindow.enterApp();*/
				mainWindow.showPopUp("Saved", "Saved correctly", JOptionPane.INFORMATION_MESSAGE);
				mainWindow.activatePreferencesPanel();
			}
			else {
				usernameFl.setText(u.getUsername());
				mainWindow.showPopUp("Error", "This username is already taken", JOptionPane.ERROR_MESSAGE);
			}
			
		});
		
		logoutBtn.addActionListener(e -> {
			this.controller.logout();
			mainWindow.activateLoginPanel();
		});
		p.add(logoutBtn);
		p.add(Box.createRigidArea(new Dimension(20,0)));
		p.add(saveChangesBtn);
		innerPage.add(new PreferencesListEntry(p, 90 ));
	}
	private void addMandatoryFields() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBackground(Constants.FOREGROUND_COLOR);
		JLabel mandatoryFields = componentFactory.specialLabel("*Mandatory fields");
		p.add(mandatoryFields);
		innerPage.add(new PreferencesListEntry(p, 40));
	}

}

package um.tds.projects.appvideo.view;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;



import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;


import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import pulsador.Luz;
import um.tds.projects.appvideo.backend.User;
import um.tds.projects.appvideo.controller.Controller;


@SuppressWarnings("serial")
public class PreferencesList extends JPanel {

	private MainWindow mainWindow;
	private JPanel innerPage;
	private JDateChooser date;
	private Controller controller;
	private ComponentFactory componentFactory;

	public PreferencesList(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.controller = Controller.getUniqueInstance();
		this.componentFactory = ComponentFactory.getUniqueInstance();
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
		addLoadVideo();
		addChangeForm();
		addButtons();
		
		JScrollPane scrollPane = new JScrollPane(innerPage);
		scrollPane.setBackground(Constants.BACKGROUND_COLOR);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane);
	}
	
	private void addChangeForm() {
		User u = controller.getCurrentUser();
		JPanel p = new JPanel();
		p.setBackground(Constants.FOREGROUND_COLOR);
		p.setLayout(new GridLayout(6, 2, 0, 10));
		
		JLabel nameLbl = this.componentFactory.specialLabel("Name:");
		JTextField nameFl = this.componentFactory.specialTextField(u.getName());
		
		JLabel surnameLbl = this.componentFactory.specialLabel("Surname:");
		JTextField surnameFl = this.componentFactory.specialTextField(u.getSurname());
		
		JLabel dayOfBirthLbl = this.componentFactory.specialLabel("Date Of Birth:");
		date = this.componentFactory.specialDateChooser(u.getDateOfBirth());
		
		JLabel emailLbl = this.componentFactory.specialLabel("Email");
		JTextField emailFl = this.componentFactory.specialTextField(u.getEmail());
		
		JLabel newPasswordLbl = this.componentFactory.specialLabel("Password:");
		JPasswordField newPasswordFl = this.componentFactory.specialPasswordField(u.getPassword());
		
		JLabel confirmNewPasswordLbl = this.componentFactory.specialLabel("Confirm password:");
		JPasswordField confirmNewPasswordFl = this.componentFactory.specialPasswordField(u.getPassword());
		
		p.add(nameLbl); p.add(nameFl);
		p.add(surnameLbl); p.add(surnameFl);
		p.add(dayOfBirthLbl); p.add(date);
		p.add(emailLbl); p.add(emailFl); 
		p.add(newPasswordLbl); p.add(newPasswordFl);
		p.add(confirmNewPasswordLbl); p.add(confirmNewPasswordFl);
		innerPage.add(new PreferencesListEntry(p, 210));
		
	}
	
	private void addTitle() {
		JLabel pageTitle = new JLabel("Preferences");
		pageTitle.setFont(Constants.TITLE_FONT);
		pageTitle.setForeground(Constants.LIGHT_FONT_COLOR);
		innerPage.add(new PreferencesListEntry(pageTitle, Constants.DEFAULT_PREFERENCES_HEIGHT));
		addSeparator();
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
		//loaderLbl.setFont(Constants.DEFAULT_FONT);
		//loaderLbl.setForeground(Constants.FONT_COLOR);
		
		Luz luz = new Luz();
		luz.setPreferredSize(new Dimension(100, 100));
		luz.setMinimumSize(new Dimension(100, 100));
		luz.setMaximumSize(new Dimension(100, 100));
		luz.setBackground(Constants.FOREGROUND_COLOR);
		luz.setColor(Color.GREEN);
		luz.addEncendidoListener(e -> {
			selectFile();
		});
		
		//loadPanel.add(loaderLbl);
		//loadPanel.add(Box.createRigidArea(new Dimension(100,0)));
		loadPanel.add(luz);
		
		innerPage.add(new PreferencesListEntry(loadPanel, 2*Constants.DEFAULT_PREFERENCES_HEIGHT ));
		addSeparator();
	}
	
	private void addButtons() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBackground(Constants.FOREGROUND_COLOR);
		JButton logoutBtn = new JButton("Log out");
		JButton saveChangesBtn = new JButton("Save");
		logoutBtn.addActionListener(e -> {
			mainWindow.activateLoginPanel();
		});
		p.add(logoutBtn);
		p.add(Box.createRigidArea(new Dimension(20,0)));
		p.add(saveChangesBtn);
		innerPage.add(new PreferencesListEntry(p, 80 ));
	}
	
	void selectFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
		}
	}

}

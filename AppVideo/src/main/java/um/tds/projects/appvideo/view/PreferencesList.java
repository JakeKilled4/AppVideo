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

	public PreferencesList(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.controller = Controller.getUniqueInstance();
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
		
		JLabel nameLbl = configLabel("Name:");
		JTextField nameFl = configTextField();
		nameFl.setText(u.getName());
		
		JLabel surnameLbl = configLabel("Surname:");
		JTextField surnameFl = configTextField();
		surnameFl.setText(u.getSurname());
		
		JLabel dayOfBirthLbl = configLabel("Date Of Birth:");
		date = new JDateChooser();
		for(Component c : date.getComponents()) {
			c.setBackground(Constants.SEARCH_COLOR);
			c.setForeground(Constants.FONT_COLOR);
		}
		JTextFieldDateEditor e =  (JTextFieldDateEditor) date.getDateEditor();
		e.setBorder(BorderFactory.createEmptyBorder());
		e.setFont(Constants.DEFAULT_FONT);
		e.setCaretColor(Constants.FONT_COLOR);
		e.setDate(u.getDateOfBirth());
		
		date.setBackground(Constants.SEARCH_COLOR);
		date.setForeground(Constants.FONT_COLOR);
		date.setFont(Constants.DEFAULT_FONT);
		date.setBorder(BorderFactory.createEmptyBorder());
		date.setMinimumSize(new Dimension(date.getWidth(),25));
		date.setPreferredSize(new Dimension(date.getWidth(),25));
		
		
		JLabel emailLbl = configLabel("Email");
		JTextField emailFl = configTextField();
		emailFl.setText(u.getEmail());
		
		JLabel newPasswordLbl = configLabel("Password:");
		JPasswordField newPasswordFl = configPasswordField();
		newPasswordFl.setText(u.getPassword());		
		
		JLabel confirmNewPasswordLbl = configLabel("Confirm password:");
		JPasswordField confirmNewPasswordFl = configPasswordField();
		confirmNewPasswordFl.setText(u.getPassword());
		
		p.add(nameLbl); p.add(nameFl);
		p.add(surnameLbl); p.add(surnameFl);
		p.add(dayOfBirthLbl); p.add(date);
		p.add(emailLbl); p.add(emailFl); 
		p.add(newPasswordLbl); p.add(newPasswordFl);
		p.add(confirmNewPasswordLbl); p.add(confirmNewPasswordFl);
		innerPage.add(new PreferencesListEntry(p, 210));
		
	}
	private JLabel configLabel(String name) {
		JLabel label = new JLabel(name);
		label.setBackground(Constants.FOREGROUND_COLOR);
		label.setFont(Constants.DEFAULT_FONT);
		label.setForeground(Constants.FONT_COLOR);
		return label;
	}
	
	private JTextField configTextField() {
		JTextField tf = new JTextField();
		tf.setColumns(10);
		tf.setBackground(Constants.SEARCH_COLOR);
		tf.setForeground(Constants.FONT_COLOR);
		tf.setFont(Constants.DEFAULT_FONT);
		tf.setBorder(BorderFactory.createEmptyBorder());
		tf.setCaretColor(Constants.FONT_COLOR);
		tf.setMinimumSize(new Dimension(tf.getWidth(),25));
		tf.setPreferredSize(new Dimension(tf.getWidth(),25));
		return tf;
	}
	
	private JPasswordField configPasswordField() {
		JPasswordField tf = new JPasswordField();
		tf.setColumns(10);
		tf.setBackground(Constants.SEARCH_COLOR);
		tf.setForeground(Constants.FONT_COLOR);
		tf.setFont(Constants.DEFAULT_FONT);
		tf.setBorder(BorderFactory.createEmptyBorder());
		tf.setCaretColor(Constants.FONT_COLOR);
		tf.setMinimumSize(new Dimension(tf.getWidth(), 25));
		tf.setPreferredSize(new Dimension(tf.getWidth(), 25));
		return tf;
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

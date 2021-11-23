package um.tds.projects.appvideo.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class ManageListPanel extends JPanel {

	private MainWindow mainWindow;
	private JPanel toolbarPanel;
	private JList<String> videoList;
	private DefaultListModel<String> videoModel;
	private JPanel searchBar;
	
	
	public ManageListPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createScreen();
	}

	private void createScreen() {
		setBackground(Constants.BACKGROUND_COLOR);
		setSize  (Constants.X_SIZE, Constants.Y_SIZE);
		fixSize  (this, Constants.WINDOW_X_SIZE, Constants.WINDOW_Y_SIZE);
		setLayout(new BorderLayout());

		toolbarPanel  = new ToolbarPanel	(mainWindow);
		
		add(toolbarPanel, BorderLayout.WEST);
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Constants.BACKGROUND_COLOR);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		searchBar = new SearchBar(Constants.BACKGROUND_COLOR);
		centerPanel.add(searchBar);
		
		centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		videoModel = new DefaultListModel<String>();
		videoModel.addElement("Uno");

		videoList = new JList<String>(videoModel);
		videoList.setLayoutOrientation(JList.VERTICAL);
		videoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		videoList.setBackground(Constants.SEARCH_COLOR);
		videoList.setForeground(Constants.FONT_COLOR);
		videoList.setFont(Constants.DEFAULT_FONT);
		((DefaultListCellRenderer)videoList.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		JPanel scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.X_AXIS));
		scrollPanel.setBackground(Constants.BACKGROUND_COLOR);
		
		scrollPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		JScrollPane availableScroll = new JScrollPane(videoList);
		availableScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		availableScroll.setBorder(BorderFactory.createEmptyBorder());
		scrollPanel.add(availableScroll);
		
		scrollPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		centerPanel.add(scrollPanel);
			
		centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel,BoxLayout.X_AXIS));
		btnPanel.setBackground(Constants.BACKGROUND_COLOR);
		btnPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		JButton addBtn = new JButton("Add");
		btnPanel.add(addBtn);
		btnPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		JButton removeBtn = new JButton("Remove");
		btnPanel.add(removeBtn);
		btnPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		centerPanel.add(btnPanel);
		
		addBtn.addActionListener(e -> {
			int res = JOptionPane.showConfirmDialog(this,
					"Create new videlist with name ...?","List not found",
					JOptionPane.YES_NO_OPTION );
		});
		
		
		centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		add(centerPanel, BorderLayout.CENTER);
	}

	private void fixSize(JComponent component, int x, int y) {
		component.setMinimumSize  (new Dimension(x, y));
		component.setMaximumSize  (new Dimension(x, y));
		component.setPreferredSize(new Dimension(x, y));
	}

}

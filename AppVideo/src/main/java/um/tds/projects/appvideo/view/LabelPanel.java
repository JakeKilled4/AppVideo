package um.tds.projects.appvideo.view;


import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JList;

@SuppressWarnings("serial")
public class LabelPanel extends JPanel{
	
	private DefaultListModel<String> availableModel;
	private DefaultListModel<String> selectedModel;
	private JList<String> availableList;
	private JList<String> selectedList;
	private ListSelectionListener listener1;
	private ListSelectionListener listener2;
	private ArrayList<String> labels;
	
	
	public LabelPanel(String...filterLabels) {
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBackground(Constants.FOREGROUND_COLOR);
		
		
		
		JLabel availableLabels = new JLabel("Available labels");
		availableLabels.setForeground(Constants.FONT_COLOR);
		availableLabels.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(availableLabels);
		
		labels = new ArrayList<String>(Arrays.asList(filterLabels));
		
		availableModel = new DefaultListModel<String>();
		availableModel.addAll(Arrays.asList(filterLabels));

		availableList = new JList<String>(availableModel);
		availableList.setLayoutOrientation(JList.VERTICAL);
		availableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		availableList.setBackground(Constants.FOREGROUND_COLOR);
		availableList.setForeground(Constants.FONT_COLOR);
		availableList.setFont(Constants.DEFAULT_FONT);
		((DefaultListCellRenderer)availableList.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		JScrollPane availableScroll = new JScrollPane(availableList);
		availableScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(availableScroll);
		
		add(Box.createRigidArea(new Dimension(0, 10)));
	
		JLabel selectedLabels = new JLabel("Selected labels");
		selectedLabels.setForeground(Constants.FONT_COLOR);
		selectedLabels.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(selectedLabels);
		
		selectedModel = new DefaultListModel<String>();
		selectedList = new JList<String>(selectedModel);
		selectedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedList.setBackground(Constants.FOREGROUND_COLOR);
		selectedList.setForeground(Constants.FONT_COLOR);
		selectedList.setFont(Constants.DEFAULT_FONT);
		selectedList.setAlignmentX(Component.CENTER_ALIGNMENT);
		((DefaultListCellRenderer)selectedList.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		JScrollPane selectedScroll = new JScrollPane(selectedList);
		selectedScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(selectedScroll);
		
		
		listener1 = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()){
					@SuppressWarnings("unchecked")
					JList<String> source = (JList<String>)event.getSource();
					String selected = source.getSelectedValue().toString();
					int index = source.getSelectedIndex();
					availableList.removeListSelectionListener(this);
					selectedList.removeListSelectionListener(listener2);
					selectedModel.addElement(selected);	
					availableList.clearSelection();
					availableModel.remove(index);
                    availableList.addListSelectionListener(this);
                    selectedList.addListSelectionListener(listener2);
				}
			}
		};
		
		listener2 = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()){
					@SuppressWarnings("unchecked")
					JList<String> source = (JList<String>)event.getSource();
					String selected = source.getSelectedValue().toString();
					int index = source.getSelectedIndex();
					selectedList.removeListSelectionListener(this);
					availableList.removeListSelectionListener(listener1);
					availableModel.addElement(selected);	
					selectedList.clearSelection();
					selectedModel.remove(index);
                    selectedList.addListSelectionListener(this);
                    availableList.addListSelectionListener(listener1);
				}
			}
		};
	
		availableList.addListSelectionListener(listener1);
		selectedList.addListSelectionListener(listener2);
	}
	public void addLabel(String label) {
		if(!labels.contains(label)) {
			availableList.removeListSelectionListener(listener1);
			availableModel.addElement(label);
			labels.add(label);
			availableList.addListSelectionListener(listener1);
		}
	}
}

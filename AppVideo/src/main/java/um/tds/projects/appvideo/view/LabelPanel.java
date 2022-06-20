package um.tds.projects.appvideo.view;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import um.tds.projects.appvideo.backend.Label;
import um.tds.projects.appvideo.controller.Controller;

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
	private Controller controller;
	
	
	public LabelPanel(List<Label> filterLabels) {
		this.controller = Controller.getUniqueInstance();
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBackground(Constants.FOREGROUND_COLOR);
		setMinimumSize  (new Dimension(Constants.TOOLBAR_OPEN_SIZE, Short.MAX_VALUE));
		setPreferredSize(new Dimension(Constants.TOOLBAR_OPEN_SIZE, Short.MAX_VALUE));
		setMaximumSize  (new Dimension(Constants.TOOLBAR_OPEN_SIZE, Short.MAX_VALUE));
		
		add(Box.createRigidArea(new Dimension(0, 10)));
		
		JLabel availableLabels = new JLabel("Available labels");
		availableLabels.setForeground(Constants.FONT_COLOR);
		availableLabels.setAlignmentX(Component.CENTER_ALIGNMENT);
		availableLabels.setFont(Constants.TITLE_FONT);
		add(availableLabels);
		
		add(Box.createRigidArea(new Dimension(0, 2)));
		
		JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
		separator1.setMaximumSize(new Dimension(Short.MAX_VALUE, 0));
		separator1.setBackground(new Color(99, 130, 191));
		add(separator1);
		
		add(Box.createRigidArea(new Dimension(0, 5)));
		
		availableModel = new DefaultListModel<String>();
		availableModel.addAll(filterLabels.stream().map(l -> l.getName()).collect(Collectors.toList()));

		availableList = new JList<String>(availableModel);
		availableList.setLayoutOrientation(JList.VERTICAL);
		availableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		availableList.setBackground(Constants.FOREGROUND_COLOR);
		availableList.setForeground(Constants.FONT_COLOR);
		availableList.setFont(Constants.DEFAULT_FONT);
		((DefaultListCellRenderer)availableList.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		JScrollPane availableScroll = new JScrollPane(availableList);
		availableScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		availableScroll.setBorder(BorderFactory.createEmptyBorder());
		add(availableScroll);
		
		add(Box.createRigidArea(new Dimension(0, 10)));
	
		JLabel selectedLabels = new JLabel("Selected labels");
		selectedLabels.setForeground(Constants.FONT_COLOR);
		selectedLabels.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectedLabels.setFont(Constants.TITLE_FONT);
		add(selectedLabels);
		
		add(Box.createRigidArea(new Dimension(0, 2)));
		
		JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
		separator2.setMaximumSize(new Dimension(Short.MAX_VALUE, 0));
		separator2.setBackground(new Color(99, 130, 191));
		add(separator2);
		
		add(Box.createRigidArea(new Dimension(0, 5)));
		
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
		selectedScroll.setBorder(BorderFactory.createEmptyBorder());
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
                    
                    List<String> selectedLabels = new LinkedList<String>();
                    for (int i = 0; i < selectedModel.getSize(); i++) {
                        String item = selectedModel.getElementAt(i);
                        selectedLabels.add(item);
                    }
                    controller.setSelectedLabel(selectedLabels);
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
    
                    List<String> selectedLabels = new LinkedList<String>();
                    for (int i = 0; i < selectedModel.getSize(); i++) {
                        String item = selectedModel.getElementAt(i);
                        selectedLabels.add(item);
                    }
                    controller.setSelectedLabel(selectedLabels);
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

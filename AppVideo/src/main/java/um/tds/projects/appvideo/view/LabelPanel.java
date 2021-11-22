package um.tds.projects.appvideo.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.glass.events.MouseEvent;


import javax.swing.JList;

@SuppressWarnings("serial")
public class LabelPanel extends JPanel{
	
	private DefaultListModel<String> availableModel;
	private DefaultListModel<String> selectedModel;
	private ListSelectionListener listener1;
	private ListSelectionListener listener2;
	
	public LabelPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Constants.FOREGROUND_COLOR);
		
		JLabel availableLabels = new JLabel("Available labels");
		availableLabels.setForeground(Constants.FONT_COLOR);
		add(availableLabels);
		
		
		String[] filters = {"Filtro1","Filtro2","Filtro3","Filtro4"};
		availableModel = new DefaultListModel<String>();
		availableModel.addAll(Arrays.asList(filters));

		JList<String> availableList = new JList<String>(availableModel);
		availableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		availableList.setBackground(Constants.FOREGROUND_COLOR);
		availableList.setForeground(Constants.FONT_COLOR);
		availableList.setFont(Constants.DEFAULT_FONT);
		//availableList.setFixedCellWidth(100);
		add(availableList);
		
	
		JLabel selectedLabels = new JLabel("Selected labels");
		selectedLabels.setForeground(Constants.FONT_COLOR);
		add(selectedLabels);
		
		selectedModel = new DefaultListModel<String>();
		JList<String> selectedList = new JList<String>(selectedModel);
		selectedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedList.setBackground(Constants.FOREGROUND_COLOR);
		selectedList.setForeground(Constants.FONT_COLOR);
		selectedList.setFont(Constants.DEFAULT_FONT);
		add(selectedList);
		
		
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
}

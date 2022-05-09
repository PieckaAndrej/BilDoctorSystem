package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Table extends JPanel {
	private JTable table;
	private JPanel panel;
	private ControllerActionIF addAction;
	private RemoveActionIF removeAction;
	private String[] columns;
	private Box verticalBox;
	private Component verticalStrut;
	
	private InputPanel inputPanel;

	/**
	 * Create the panel.
	 */
	public Table(String[] columns) {
		this(columns, new InputPanel(columns));
	}
	
	public Table(String[] columns, InputPanel inputPanel) {
		this.inputPanel = inputPanel;
		
		this.columns = columns;
		
		panel = new JPanel();
		
		
		JButton add = new JButton("Add");
		add.setAlignmentX(0.5f);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				askForInput();
			}
		});
		
		JButton remove = new JButton("Remove");
		remove.setAlignmentX(0.5f);
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSelected();
			}
		});
		
		setLayout(new BorderLayout(0, 0));
		panel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		table.setFocusable(false);

		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		
		add(panel);
		
		verticalBox = Box.createVerticalBox();
		verticalBox.add(add);
		verticalBox.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
		
		verticalStrut = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut);
		verticalBox.add(remove);
		panel.add(verticalBox, BorderLayout.EAST);

		updateTable();
	}
	
	public void setName(String name) {
		table.setName(name);
	}
	
	public String getName() {
		return table.getName();
	}
	
	public void updateTable() {
		DefaultTableModel myTableModel = new DefaultTableModel();
		
		for (int i = 0; i < columns.length; i++) {
			myTableModel.addColumn(columns[i]);
		}
		
		table.setModel(myTableModel);
	}
	
	public void removeSelected() {
		int[] selected = table.getSelectedRows();

		int length = selected.length;
		
		int[] reversed = new int[length];
		
		for (int i = 0; i < length; i++) {
			reversed[i] = selected[length - (i + 1)];
		}
		
		Arrays.stream(reversed).forEach(System.out::println);
		
		for (int i = 0; i < length; i++) {
			((DefaultTableModel)table.getModel()).removeRow(reversed[i]);
		}
		
		if(removeAction != null) {
			removeAction.action(reversed);
		}
	}
	
	public void askForInput() {
		inputPanel.setAction(l -> {
			if (addAction != null) {
				addAction.action(l);
			}
		});
		
		inputPanel.generatePanel();
		inputPanel.setVisible(true);
	}

	public void addRow(InputPanel inputPanel) {
		String[] row = new String[columns.length];
		
		String[] texts = inputPanel.getTexts();
		
		for (int i = 0; i < row.length; i++) {
			row[i] = texts[i];
		}
		
		((DefaultTableModel)table.getModel()).addRow(row);
	}
	
	public JTable getTable() {
		return table;
	}
	
	public void setActionAdd(ControllerActionIF controllerAction) {
		this.addAction = controllerAction;
	}
	
	public void setActionRemove(RemoveActionIF actionRemove) {
		this.removeAction = actionRemove;
	}
	

}

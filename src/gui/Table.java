package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
	 * Create the panel
	 * @param columns Array of column names
	 * @wbp.parser.constructor
	 */
	public Table(String[] columns) {
		this(columns, new InputPanel(columns));
	}
	
	/**
	 * Create the panel with custom input panel
	 * @param columns Array of column names
	 * @param inputPanel custom input panel
	 */
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
	
	/**
	 * Set name of the panel
	 * @param name The name as a String
	 */
	public void setName(String name) {
		table.setName(name);
	}
	
	/**
	 * Get name as String
	 * @return table name as String
	 */
	public String getName() {
		return table.getName();
	}
	
	/**
	 * Update the table with the columns
	 */
	public void updateTable() {
		DefaultTableModel myTableModel = new DefaultTableModel();
		
		for (int i = 0; i < columns.length; i++) {
			myTableModel.addColumn(columns[i]);
		}
		
		table.setModel(myTableModel);
	}
	
	/**
	 * Remove selected element from the table
	 */
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
	
	/**
	 * Show input panel
	 */
	public void askForInput() {
		inputPanel.setAction(l -> {
			if (addAction != null) {
				addAction.action(l);
			}
		});
		
		inputPanel.generatePanel();
		inputPanel.setVisible(true);
	}

	/**
	 * Add a new row
	 * @param inputPanel Input panel with user inputs
	 */
	public void addRow(InputPanel inputPanel) {
		String[] row = new String[columns.length];
		
		String[] texts = inputPanel.getTexts();
		
		for (int i = 0; i < row.length; i++) {
			row[i] = texts[i];
		}
		
		((DefaultTableModel)table.getModel()).addRow(row);
	}
	
	/**
	 * Get table as JTable
	 * @return table as JTable
	 */
	public JTable getTable() {
		return table;
	}
	
	/**
	 * Set action add as ControllerActionIF
	 * @param controllerAction ControllerActionIf as action add
	 */
	public void setActionAdd(ControllerActionIF controllerAction) {
		this.addAction = controllerAction;
	}
	
	/**
	 * Set action remove as RemoveActionIF
	 * @param controllerAction RemoveActionIF as action remove
	 */
	public void setActionRemove(RemoveActionIF actionRemove) {
		this.removeAction = actionRemove;
	}
	

}

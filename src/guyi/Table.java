package guyi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.SaleController;

public class Table extends JPanel {
	private JTable table;
	private JPanel panel;
	private SaleController saleCtrl;
	private String[] columns;
	private Box verticalBox;
	private Component verticalStrut;

	/**
	 * Create the panel.
	 */
	public Table(SaleController saleCtrl, String[] columns) {
		this.saleCtrl = saleCtrl;
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
		table.setCellEditor(null);

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
		
		for (int i = 0; i < length; i++) {
			((DefaultTableModel)table.getModel()).removeRow(selected[0]);
		}
		
	}
	
	public void askForInput() {
		
		InputPanel inputPanel = new InputPanel(columns) {

			@Override
			public void confirm() {
				addRow(this);
				dispose();
			}
			
		};
		inputPanel.generatePanel();
		inputPanel.setVisible(true);
	}

	public void addRow(InputPanel inputPanel) {
		String[] row = new String[columns.length];
		
		for (int i = 0; i < row.length; i++) {
			row[i] = inputPanel.getFields().get(i).getText();
		}
		
		((DefaultTableModel)table.getModel()).addRow(row);
	}
	
	public JTable getTable() {
		return table;
	}

}

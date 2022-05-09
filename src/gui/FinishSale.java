package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

public class FinishSale extends JPanel {

	private static final long serialVersionUID = -7893429309021174225L;
	
	private Table[] tables;
	private JTextPane textPane;
	private JButton btnFinishSale;

	/**
	 * Create the panel.
	 */
	public FinishSale(Table[] tables) {
		this.tables = tables;
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		
		btnFinishSale = new JButton("Finish Sale");
		panel_1.add(btnFinishSale);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		textPane.setFocusable(false);
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

		showTableInfo();
	}
	
	public void addFinishSaleActionListener(ActionListener listener) {
		btnFinishSale.addActionListener(listener);
	}
	
	public void showTableInfo() {
		StringBuilder text = new StringBuilder();
		
		for (int index = 0; index < tables.length; index ++) {
			JTable thisTable = tables[index].getTable();
			
			text.append(thisTable.getName() + "\n") ;
			
			for (int i = 0; i < thisTable.getRowCount(); i++) {
				for (int k = 0; k < thisTable.getColumnCount(); k++) {
					text.append(thisTable.getColumnName(k));
					text.append(": ");
					text.append(thisTable.getValueAt(i, k));
					text.append(", ");
				}
				text.append("\n");
			}

			text.append("\n");
		}
		
		textPane.setText(text.toString());
	}

}

package guyi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class InputPanel extends JFrame {
	
	private String[] names;
	private String[] values;
	private Box verticalBoxText;
	private Box verticalBoxField;
	private JPanel panel_1;
	private JButton btnCancel;
	private JButton btnConfirm;
	private Component horizontalStrut;
	
	private List<JTextField> fields;
	private JLabel lblError;

	/**
	 * Create the panel.
	 * @wbp.parser.constructor
	 */
	public InputPanel(String[] names, String[] values) {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		Box horizontalBox = Box.createHorizontalBox();
		getContentPane().add(horizontalBox, BorderLayout.NORTH);
		
		verticalBoxText = Box.createVerticalBox();
		horizontalBox.add(verticalBoxText);
		
		verticalBoxField = Box.createVerticalBox();
		horizontalBox.add(verticalBoxField);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);
		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(btnCancel, BorderLayout.WEST);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm();
			}
		});
		
		panel_1.add(btnConfirm, BorderLayout.EAST);
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblError, BorderLayout.CENTER);
		
		fields = new ArrayList<>();
		
		this.names = names;
		if (values != null) {
			this.values = values;
		}

	}
	
	public InputPanel(String[] names) {
		this(names, null);
		
		String[] values = new String[names.length];
		
		for (int i = 0; i < values.length; i++) {
			values[i] = "";
		}
		
		this.values = values;
	}
	
	public void generatePanel() {
		setBounds(100, 100, 300, 200);
		setTitle("Bil Doctor - Input");
		
		for (int name = 0; name < names.length; name++) {
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(new BorderLayout());
			JLabel label = new JLabel(names[name]);
			
			label.setVerticalAlignment(SwingConstants.CENTER);
			
			panel.add(label, BorderLayout.EAST);


			verticalBoxText.add(panel);
		}
		
		fields.clear();
		
		for (int value = 0; value < values.length; value++) {
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(new BorderLayout());
			JTextField field = new JTextField(values[value]);
			fields.add(field);
			
			panel.add(field);

			verticalBoxField.add(panel, BorderLayout.EAST);
		}
	}
	
	public void setNames(String[] names) {
		this.names = names;
	}
	
	public void setValues(String[] values) {
		this.values = values;
	}
	
	public String[] getNames() {
		return names;
	}
	
	public String[] getValues() {
		return values;
	}
	
	public List<JTextField> getFields() {
		return fields;
	}
	
	public JLabel getErrorLabel() {
		return lblError;
	}
	
	public void confirm() {
		
	}
	
	public void resetFieldColor() {
		for (JTextField field : fields) {
			field.putClientProperty( "JComponent.outline", "default" );
		}
	}

}

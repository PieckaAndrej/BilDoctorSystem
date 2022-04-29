package guyi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
	private Box horizontalBox;
	private JPanel panel_1;
	private JButton btnCancel;
	private JButton btnConfirm;
	private Component horizontalStrut;
	private ControllerActionIF action;
	
	private JTextField[] fields;
	private JLabel lblError;

	/**
	 * Create the panel.
	 * @wbp.parser.constructor
	 */
	public InputPanel(String[] names, String[] values) {
		setBounds(100, 100, 300, 200);
		setTitle("Bil Doctor - Input");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		horizontalBox = Box.createHorizontalBox();
		getContentPane().add(horizontalBox, BorderLayout.NORTH);
		
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
		
		fields = new JTextField[names.length];
		
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
	
	public void createLabels() {
		horizontalBox.removeAll();
		verticalBoxText = Box.createVerticalBox();
		horizontalBox.add(verticalBoxText);
		
		verticalBoxField = Box.createVerticalBox();
		horizontalBox.add(verticalBoxField);
		
		for (int name = 0; name < names.length; name++) {
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(new BorderLayout());
			JLabel label = new JLabel(names[name]);
			
			label.setVerticalAlignment(SwingConstants.CENTER);
			
			panel.add(label, BorderLayout.EAST);


			verticalBoxText.add(panel);
		}
	}
	
	public void generatePanel() {
		createLabels();
		
		fields = new JTextField[names.length];
		
		for (int value = 0; value < names.length; value++) {
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(new BorderLayout());
			JTextField field = new JTextField(values[value]);
			fields[value] = field;
			
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
	
	public JTextField[] getFields() {
		return fields;
	}
	
	public void addField(int index, JTextField field) {
		fields[index] = field;
	}
	
	public String[] getTexts() {
		
		return Arrays.stream(fields)
				.map(JTextField::getText)
				.toList().toArray(new String[fields.length]);
	}
	
	public JLabel getErrorLabel() {
		return lblError;
	}
	
	public void confirm() {
		if (action != null) {
			action.action(this);
		}
	}
	
	public void resetFieldColor() {
		Arrays.stream(fields)
			.filter(l -> l != null)
			.forEach(field -> {
				field.putClientProperty( "JComponent.outline", "default" );
				field.setBackground(ColorScheme.BACKGROUND);
			});
			
	}
	
	public void setAction(ControllerActionIF action) {
		this.action = action;
	}

	protected Box getVerticalBoxField() {
		return verticalBoxField;
	}

}

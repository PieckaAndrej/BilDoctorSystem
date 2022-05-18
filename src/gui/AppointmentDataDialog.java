package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.AppointmentController;
import exceptions.DatabaseAccessException;
import exceptions.LengthUnderrunException;
import model.Employee;
import model.Product;

public class AppointmentDataDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea;
	private JSpinner spinner;
	private AppointmentController appointmentController;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField textPhoneNumber;
	private JTextField textName;
	private JPanel descriptionPanel;
	private JPanel customerPanel;
	private JButton secondOkButton;
	private JButton okButton;
	private JLabel errorLabel;
	private JComboBox<Employee> comboBox;
	private String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AppointmentDataDialog dialog = new AppointmentDataDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AppointmentDataDialog(LocalDateTime time) {
		appointmentController = new AppointmentController();
		setTitle("Appointment menu");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			descriptionPanel = new JPanel();
			contentPanel.add(descriptionPanel);
			descriptionPanel.setLayout(new BorderLayout(0, 0));
			{
				Box verticalBox = Box.createVerticalBox();
				descriptionPanel.add(verticalBox);
				{
					JLabel lblNewLabel_2 = new JLabel(time+"");
					lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
					verticalBox.add(lblNewLabel_2);
				}
				{
					Box horizontalBox = Box.createHorizontalBox();
					horizontalBox.setAlignmentY(Component.CENTER_ALIGNMENT);
					verticalBox.add(horizontalBox);
					{
						lblNewLabel = new JLabel("Length");
						horizontalBox.add(lblNewLabel);
					}
					{
						SpinnerModel sm = new SpinnerNumberModel(1, 1, null, 1);
						spinner = new JSpinner(sm);
						horizontalBox.add(spinner);
					}
				}
				{
					Box horizontalBox = Box.createHorizontalBox();
					verticalBox.add(horizontalBox);
					{
						lblNewLabel_1 = new JLabel("Description");
						horizontalBox.add(lblNewLabel_1);
					}
				}
				{
					textArea = new JTextArea();
					textArea.setLineWrap(true);
					verticalBox.add(textArea);
					textArea.setPreferredSize(new Dimension(100,100));
				}
			}
		}
		{
			customerPanel = new JPanel();
			contentPanel.add(customerPanel, BorderLayout.NORTH);
			customerPanel.setVisible(false);
			{
				Box verticalBox = Box.createVerticalBox();
				customerPanel.add(verticalBox);
				{
					Box horizontalBox = Box.createHorizontalBox();
					verticalBox.add(horizontalBox);
					{
						JLabel lblNewLabel_3 = new JLabel("Phone Number:");
						horizontalBox.add(lblNewLabel_3);
					}
					{
						textPhoneNumber = new JTextField();
						horizontalBox.add(textPhoneNumber);
						textPhoneNumber.setColumns(10);
					}
					
				}
				{
					Component verticalStrut = Box.createVerticalStrut(10);
					verticalBox.add(verticalStrut);
				}
				{
					Box horizontalBox = Box.createHorizontalBox();
					verticalBox.add(horizontalBox);
					{
						JLabel lblNewLabel_4 = new JLabel("Name:");
						horizontalBox.add(lblNewLabel_4);
					}
					{
						textName = new JTextField();
						horizontalBox.add(textName);
						textName.setColumns(10);
					}
				}
				{
					Component verticalStrut = Box.createVerticalStrut(10);
					verticalBox.add(verticalStrut);
				}
				{
					Box horizontalBox = Box.createHorizontalBox();
					verticalBox.add(horizontalBox);
					{
						JLabel lblNewLabel_5 = new JLabel("Employee");
						horizontalBox.add(lblNewLabel_5);
					}
					{
						comboBox = new JComboBox<>();
						horizontalBox.add(comboBox);
						comboBox.setEditable(true);
						fillProductList(comboBox);
						comboBox.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {

							   @Override
							   public void focusGained(FocusEvent e) {
							      comboBox.showPopup();
							   }
							});
						
						comboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
							
							@Override
							public void keyReleased(KeyEvent e) {
								try {
									if(!(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_UP ||
										e.getKeyCode() == KeyEvent.VK_DOWN ||
										e.getKeyCode() == KeyEvent.VK_RIGHT) ) {
										Object o = comboBox.getEditor().getItem();
											((DefaultComboBoxModel<Employee>) comboBox.getModel())
											.removeAllElements();
											comboBox.setSelectedItem(o);
										if(o != null) {
											((DefaultComboBoxModel<Employee>) comboBox.getModel()).addAll(
													appointmentController.getAllEmployees().stream()
													.filter(p -> p.getName().toLowerCase().contains(o.toString()
													.toLowerCase())).toList());
										
											}
										}	
									} catch (DatabaseAccessException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
							}
								
						});

						comboBox.getEditor().getEditorComponent().addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseClicked(MouseEvent e) {
								if(e.getButton() == MouseEvent.BUTTON1)
								comboBox.showPopup();
							}
						});
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				secondOkButton = new JButton("Confirm");
				secondOkButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textPhoneNumber.getText().matches(pattern)) {
							if(textName.equals("")) {
								if(comboBox.getEditor().getItem() instanceof Employee) {
									addCustomerInfo();
									addEmployee();
								} else {
									errorLabel.setVisible(true);
									errorLabel.setText("The employee is incorrect");
								}
							} else {
								errorLabel.setVisible(true);
								errorLabel.setText("Input name");
							}
						} else {
							errorLabel.setVisible(true);
							errorLabel.setText("The phone number is incorrect");
						}						
					}
				});
				{
					errorLabel = new JLabel("New label");
					buttonPane.add(errorLabel);
					errorLabel.setVisible(false);
				}
				secondOkButton.setActionCommand("Confirm");
				buttonPane.add(secondOkButton);
				secondOkButton.setEnabled(false);
				secondOkButton.setVisible(false);
			}
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						createAppointment(time);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}

		}
	}
	private void createAppointment(LocalDateTime time) {
		try {
			if(appointmentController.createAppointment(time, (Integer)spinner.getValue(), textArea.getText())) {
				openCustomerInfoPanel();
				}
				else {
					errorLabel.setVisible(true);
					errorLabel.setText("The date is incorrect");
				}			
		} catch (DatabaseAccessException | LengthUnderrunException e) {
			e.printStackTrace();
		}
	}
	
	private void addCustomerInfo() {
		appointmentController.addCustomerInfo(textName.getText(), textPhoneNumber.getText());
	}
	
	private void addEmployee() {
			try {
				appointmentController.addEmployee((Employee) comboBox.getEditor().getItem());
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void openCustomerInfoPanel() {
		descriptionPanel.setVisible(false);
		okButton.setVisible(false);
		customerPanel.setVisible(true);
		secondOkButton.setVisible(true);
		secondOkButton.setEnabled(true);
		getRootPane().setDefaultButton(secondOkButton);
	}
	
	private void fillProductList(JComboBox<Employee> comboBox) {
		List<Employee> ps = null;
		try {
			ps = appointmentController.getAllEmployees();
		} catch (DatabaseAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultComboBoxModel<Employee> dfm = new DefaultComboBoxModel<>();
		dfm.addAll(ps);
		
		comboBox.setModel(dfm);
		comboBox.setRenderer(new EmployeeListCellRenderer());
	}
}

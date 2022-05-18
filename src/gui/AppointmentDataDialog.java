package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.AppointmentController;
import exceptions.DatabaseAccessException;
import exceptions.LengthUnderrunException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
						addCustomerInfo();
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
			System.out.println(time);
			if(appointmentController.createAppointment(time, (Integer)spinner.getValue(), textArea.getText())) {
				openCustomerInfoPanel();
				}
				else {
					errorLabel.setVisible(true);
					errorLabel.setText("The date is incorrect");
				};			
		} catch (DatabaseAccessException | LengthUnderrunException e) {
			e.printStackTrace();
		}
	}
	
	private void addCustomerInfo() {
		appointmentController.addCustomerInfo(textName.getText(), textPhoneNumber.getText());
		System.out.println("works");
	}
	
	private void openCustomerInfoPanel() {
		descriptionPanel.setVisible(false);
		okButton.setVisible(false);
		customerPanel.setVisible(true);
		secondOkButton.setVisible(true);
		secondOkButton.setEnabled(true);
		getRootPane().setDefaultButton(secondOkButton);
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AppointmentDataDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AppointmentDataDialog dialog = new AppointmentDataDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AppointmentDataDialog() {
		setTitle("Appointment menu");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			{
				Box verticalBox = Box.createVerticalBox();
				panel.add(verticalBox);
				{
					JLabel lblNewLabel_2 = new JLabel("Date");
					verticalBox.add(lblNewLabel_2);
				}
				{
					Box horizontalBox = Box.createHorizontalBox();
					verticalBox.add(horizontalBox);
					{
						JLabel lblNewLabel = new JLabel("Length");
						horizontalBox.add(lblNewLabel);
					}
					{
						textField = new JTextField();
						horizontalBox.add(textField);
						textField.setColumns(10);
					}
				}
				{
					Box horizontalBox = Box.createHorizontalBox();
					verticalBox.add(horizontalBox);
					{
						JLabel lblNewLabel_1 = new JLabel("Description");
						horizontalBox.add(lblNewLabel_1);
					}
					{
						textField_1 = new JTextField();
						horizontalBox.add(textField_1);
						textField_1.setColumns(10);
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}

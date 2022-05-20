package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Vehicle;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class VehiclePanel extends JPanel {
	/**
	 * Create the panel.
	 * @param hasCellFocus 
	 * @param isSelected 
	 */
	public VehiclePanel(int index, Vehicle vehicle, boolean isSelected, boolean hasCellFocus) {
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JLabel lblIndex = new JLabel("" + index);
		add(lblIndex, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);
		
		JLabel lblVehicle = new JLabel("Vehicle");
		lblVehicle.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblVehicle = new GridBagConstraints();
		gbc_lblVehicle.gridwidth = 0;
		gbc_lblVehicle.anchor = GridBagConstraints.NORTH;
		gbc_lblVehicle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblVehicle.insets = new Insets(0, 0, 5, 0);
		gbc_lblVehicle.gridx = 0;
		gbc_lblVehicle.gridy = 0;
		panel.add(lblVehicle, gbc_lblVehicle);
		
		JLabel lblVehicleBrand = new JLabel(vehicle.getBrand());
		lblVehicleBrand.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblVehicleBrand = new GridBagConstraints();
		gbc_lblVehicleBrand.anchor = GridBagConstraints.NORTH;
		gbc_lblVehicleBrand.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblVehicleBrand.insets = new Insets(0, 0, 5, 0);
		gbc_lblVehicleBrand.gridx = 1;
		gbc_lblVehicleBrand.gridy = 2;
		panel.add(lblVehicleBrand, gbc_lblVehicleBrand);
		
		JLabel lblVehicleYear = new JLabel("" + vehicle.getYear());
		lblVehicleYear.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblVehicleYear = new GridBagConstraints();
		gbc_lblVehicleYear.anchor = GridBagConstraints.NORTH;
		gbc_lblVehicleYear.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblVehicleYear.gridx = 0;
		gbc_lblVehicleYear.gridy = 2;
		panel.add(lblVehicleYear, gbc_lblVehicleYear);
		
		JLabel lblVehicleCheckUp = new JLabel("" + vehicle.getCheckUpDate());
		lblVehicleCheckUp.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblVehicleCheckUp = new GridBagConstraints();
		gbc_lblVehicleCheckUp.gridwidth = 0;
		gbc_lblVehicleCheckUp.anchor = GridBagConstraints.NORTH;
		gbc_lblVehicleCheckUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblVehicleCheckUp.gridx = 0;
		gbc_lblVehicleCheckUp.gridy = 1;
		panel.add(lblVehicleCheckUp, gbc_lblVehicleCheckUp);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblOwner = new JLabel("Owner");
		lblOwner.setHorizontalAlignment(SwingConstants.CENTER);
		
		GridBagConstraints gbc_lblOwner = new GridBagConstraints();
		gbc_lblOwner.anchor = GridBagConstraints.CENTER;
		gbc_lblOwner.insets = new Insets(0, 0, 5, 0);
		gbc_lblOwner.gridx = 0;
		gbc_lblOwner.gridy = 0;
		panel_1.add(lblOwner, gbc_lblOwner);
		
		JLabel lblOwnerName = new JLabel(String.format("name: %s %s",
				vehicle.getOwner().getName(), vehicle.getOwner().getSurname()));
		lblOwnerName.setHorizontalAlignment(SwingConstants.CENTER);
		
		GridBagConstraints gbc_lblOwnerName = new GridBagConstraints();
		gbc_lblOwnerName.anchor = GridBagConstraints.CENTER;
		gbc_lblOwnerName.insets = new Insets(0, 0, 5, 0);
		gbc_lblOwnerName.gridx = 0;
		gbc_lblOwnerName.gridy = 1;
		panel_1.add(lblOwnerName, gbc_lblOwnerName);
		
		JLabel lblOwnerPhone = new JLabel(String.format("phone: %s",
				vehicle.getOwner().getPhoneNumber()));
		lblOwnerPhone.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblOwnerPhone = new GridBagConstraints();
		gbc_lblOwnerPhone.anchor = GridBagConstraints.CENTER;
		gbc_lblOwnerPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOwnerPhone.gridx = 0;
		gbc_lblOwnerPhone.gridy = 2;
		panel_1.add(lblOwnerPhone, gbc_lblOwnerPhone);
		
		if (isSelected) {
			setBackground(ColorScheme.FOCUS);
			panel.setBackground(ColorScheme.FOCUS);
			panel_1.setBackground(ColorScheme.FOCUS);
			lblIndex.setForeground(ColorScheme.BACKGROUND);
			lblVehicleBrand.setForeground(ColorScheme.BACKGROUND);
			lblVehicleYear.setForeground(ColorScheme.BACKGROUND);
			lblOwnerName.setForeground(ColorScheme.BACKGROUND);
			lblOwnerPhone.setForeground(ColorScheme.BACKGROUND);
			lblOwner.setForeground(ColorScheme.BACKGROUND);
			lblVehicleCheckUp.setForeground(ColorScheme.BACKGROUND);
			lblVehicle.setForeground(ColorScheme.BACKGROUND);
		}
	}

}

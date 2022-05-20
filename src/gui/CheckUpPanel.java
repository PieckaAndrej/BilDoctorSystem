package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.AppointmentController;
import model.Vehicle;

public class CheckUpPanel extends JPanel {
	private AppointmentController appointmentCtrl;
	private JList<Vehicle> vehicleList;
	private JPanel checkUpPanel;
	private JPanel panel;
	private JButton btnRegister;
	private JButton btnCancel;
	private JPanel buttonPanel;
	private List<Vehicle> vehicles;
	
	private RegisterCheckUp listener;
	
	/**
	 * Create the panel.
	 */
	public CheckUpPanel() {
		Thread t = new Thread(() -> {
			appointmentCtrl = new AppointmentController();
		});
		
		t.start();
		
		initGui();
	}
	
	private void initGui() {
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		buttonPanel = new JPanel();
		FlowLayout fl_buttonPanel = (FlowLayout) buttonPanel.getLayout();
		fl_buttonPanel.setAlignment(FlowLayout.RIGHT);
		panel.add(buttonPanel, BorderLayout.NORTH);
		
		checkUpPanel = new JPanel();
		panel.add(checkUpPanel, BorderLayout.CENTER);
		checkUpPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		checkUpPanel.add(scrollPane);
		
		JLabel lblHeader = new JLabel("Closest check ups");
		scrollPane.setColumnHeaderView(lblHeader);
		
		vehicleList = new JList<Vehicle>();
		vehicleList.setCellRenderer((list, vehicle, index, isSelected, hasCellFocus) -> {
			
			VehiclePanel vehiclePanel = new VehiclePanel(
					index + 1, vehicle, isSelected, hasCellFocus);
			
			return vehiclePanel;
		});
		
		vehicleList.addListSelectionListener(l -> {
			if (vehicleList.getSelectedIndex() != -1) {
				btnRegister.setEnabled(true);
			}
			
		});
		
		scrollPane.setViewportView(vehicleList);
		vehicleList.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register(vehicleList.getSelectedValue());
			}
		});
		
		btnRegister.setEnabled(false);
		buttonPanel.add(btnRegister);
		
		showVehicleList();
	}
	
	private void showVehicleList() {
		vehicles = appointmentCtrl.getAllVehicles();
		
		vehicles.sort((a, b) -> a.getCheckUpDate().compareTo(b.getCheckUpDate()));

		vehicleList.setListData(vehicles.toArray(new Vehicle[vehicles.size()]));
		
	}
	
	private void register(Vehicle vehicle) {
		panel.remove(checkUpPanel);
		btnRegister.setEnabled(false);
		buttonPanel.remove(btnRegister);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		
		buttonPanel.add(btnCancel);
		
		AppointmentPanel appointmentPanel = new AppointmentPanel(
				vehicle.getOwner().getName(), vehicle.getOwner().getSurname()
				, vehicle.getOwner().getPhoneNumber(),
				"Check up for " + vehicle.getPlateNumber());
		
		appointmentPanel.addFinishListener(date -> {
			appointmentCtrl.makeVehicleCheckUp(vehicle.getPlateNumber(),
					date.toLocalDate().plusDays(30));
			cancel();
			
			vehicles.remove(vehicle);
			
			listener.action(getListLength());
		});
		
		panel.add(appointmentPanel, BorderLayout.CENTER);
	}
	
	private void cancel() {
		removeAll();
		initGui();
	}
	
	public void addCheckUpListener(RegisterCheckUp l) {
		listener = l;
	}
	
	public int getListLength() {
		return vehicles.size();
	}

}

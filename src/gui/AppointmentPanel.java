package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.JDatePanel;

import controller.AppointmentController;
import dal.AppointmentDB;
import exceptions.DatabaseAccessException;
import model.Appointment;

public class AppointmentPanel extends JPanel {

	

	private static final long serialVersionUID = -81954108504757498L;
	
	private JPanel saleButtonsPanel;
	private JButton btnCreateSale;
	private JDatePanel calendar;
	private JList<Double> list;
    private DefaultListModel<Double> listModel;
    private AppointmentDB appointmentDB;
    private LocalDateTime time;

	private AppointmentController appointmentController;

	
	/**
	 * Create the panel.
	 */
	public AppointmentPanel() {
		Thread controllerInit = new Thread(() -> {
				appointmentController = new AppointmentController();
		});
		
		controllerInit.start();
		initGui();
	}
	
	/**
	 * Initialise gui
	 */
	private void initGui() {
		setBorder(new EmptyBorder(new Insets(0, 3, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		
		saleButtonsPanel = new JPanel();
		saleButtonsPanel.setBackground(ColorScheme.BACKGROUND);
		add(saleButtonsPanel);
		GridBagLayout gbl_saleButtonsPanel = new GridBagLayout();
		saleButtonsPanel.setLayout(gbl_saleButtonsPanel);
		
		btnCreateSale = new JButton("Create appointment");
		btnCreateSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread createAppointment = new Thread(() -> {
					createAppointment();
				});
				createAppointment.start();
			}
		});
		
		calendar = new JDatePanel();
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.weighty = 1.0;
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.ipadx = 5;
		gbc_lblNewLabel.weightx = 1.0;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		saleButtonsPanel.add(calendar, gbc_lblNewLabel);
		
		
		GridBagConstraints gbc_btnCreateSale = new GridBagConstraints();
		gbc_btnCreateSale.gridheight = 0;
		gbc_btnCreateSale.gridx = 0;
		gbc_btnCreateSale.gridy = 1;
		saleButtonsPanel.add(btnCreateSale, gbc_btnCreateSale);
	}

	public void createAppointment() {		
		calendar.getModel().getValue();
		time = ((GregorianCalendar)calendar.getModel().getValue()).toZonedDateTime().toLocalDateTime();
		removeAll();
		
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setAlignmentX(0.5f);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm(time);
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread cancelSale = new Thread(() -> {
					cancelAppointment();
				});
				
				cancelSale.start();
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane();	
		JPanel gridPanel = new JPanel();
		
		GridBagLayout gbl_tabbedPanel = new GridBagLayout();
		gridPanel.setLayout(gbl_tabbedPanel);
		GridBagConstraints gbc_tabbedPanel = new GridBagConstraints();
		gbc_tabbedPanel.gridwidth = 0;
		gbc_tabbedPanel.gridheight = 7;
		gbc_tabbedPanel.weightx = 1;
		gbc_tabbedPanel.weighty = 1;
		gbc_tabbedPanel.fill = GridBagConstraints.BOTH;
		gbc_tabbedPanel.gridx = 0;
		gbc_tabbedPanel.gridy = 0;
		
		/**
		 * Creates the ScrollPane and adds it into the tabbed pane
		 */
		JScrollPane scrollPane = new JScrollPane();
		
	    

	    //Create the list and put it in a scroll pane.
	    list = new JList<Double>();
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setSelectedIndex(0);
	    list.setVisibleRowCount(2);
	    
	    scrollPane.setViewportView(list);

	
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		
	    tabbedPane.addTab("AvailableHours", null, panel, null);
	    
	    GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridwidth = 0;
		gbc_cancelButton.gridheight = 0;
		gbc_cancelButton.gridx = 7;
		gbc_cancelButton.gridy = 0;
		gbc_cancelButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_cancelButton.insets = new Insets(5, 5, 5, 5);
		gridPanel.add(cancelButton, gbc_cancelButton);
		
		saleButtonsPanel.add(gridPanel);
		buttonPanel.add(confirmButton);
		panel.add(buttonPanel, BorderLayout.EAST);
		panel.add(scrollPane, BorderLayout.CENTER);
		gridPanel.add(tabbedPane, gbc_tabbedPanel);
		add(gridPanel, BorderLayout.CENTER);

		ArrayList<Appointment> a;
		try {
			a = getAppointments(time);
		    fillList(list,a);
		} catch (DatabaseAccessException e1) {
			e1.printStackTrace();
		}
		
		revalidate();

	}
	
	public void confirm(LocalDateTime time) {	
		System.out.println(list.getSelectedValue());
		AppointmentDataDialog newDialog = new AppointmentDataDialog();
		newDialog.setVisible(true);
	}
	
	public void cancelAppointment() {		
		this.removeAll();
		initGui();
	}
	
	public void fillList(JList<Double> list, ArrayList<Appointment> a){
		list.setCellRenderer(new HourListCellRenderer(time,a));
		DefaultListModel<Double> dlm = new DefaultListModel<>();
		for(double i = 0.00; i < 24; i++) {
					dlm.addElement(i);
			}
		list.setModel(dlm);
	}
		
	
	public ArrayList<Appointment> getAppointments(LocalDateTime time) throws DatabaseAccessException{
		appointmentDB = new AppointmentDB();
		return appointmentDB.getAllAppointments(time);
	}	
}

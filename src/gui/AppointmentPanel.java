package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import controller.AppointmentController;
import controller.SaleController;
import exceptions.DatabaseAccessException;
import java.awt.GridLayout;

public class AppointmentPanel extends JPanel {

	

	private static final long serialVersionUID = -81954108504757498L;
	
	private JPanel saleButtonsPanel;
	private JButton btnCreateSale;
	private JTextField fieldVehicle;
	private JLabel lblNewLabel;
	private JDatePanel calendar;
	private JList list;
    private DefaultListModel<Double> listModel;
    private Box verticalBox;
    private Component verticalStrut;

	private AppointmentController appointmentController;
	private Table hourTable;

	
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
//		StringBuilder a = new StringBuilder();
//		a.append(calendar.getModel().getYear() + ".");
//		a.append(calendar.getModel().getMonth() + ".");
//		a.append(calendar.getModel().getDay());
//		System.out.println(a);
		removeAll();
		
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setAlignmentX(0.5f);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm();
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
	    listModel = new DefaultListModel<Double>();
	  
	     //Generate list
	     
		for(double i = 0.00; i < 24; i++) {
			listModel.addElement(i);
		}

	    //Create the list and put it in a scroll pane.
	    list = new JList<Double>(listModel);
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setSelectedIndex(0);
	    list.setVisibleRowCount(2);
	    scrollPane.setViewportView(list);
	    
	    JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm();
			}
		});
	
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout(0, 0));
		
	    tabbedPane.addTab("AvailableHours", null, buttonPanel, null);
	    
	    verticalBox = Box.createVerticalBox();
		verticalBox.add(confirmButton);
		verticalBox.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
		verticalStrut = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut);
		
		
		
	    GridBagConstraints gbc_confirmButton = new GridBagConstraints();
		gbc_confirmButton.anchor = GridBagConstraints.EAST;
		gbc_confirmButton.gridwidth = 0;
		gbc_confirmButton.gridheight = 0;
		gbc_confirmButton.gridx = 1;
		gbc_confirmButton.gridy = 0;
		gridPanel.add(btnConfirm, gbc_confirmButton);
		
	    
	    GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridwidth = 0;
		gbc_cancelButton.gridheight = 0;
		gbc_cancelButton.gridx = 7;
		gbc_cancelButton.gridy = 0;
		gbc_cancelButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_cancelButton.insets = new Insets(5, 5, 5, 5);
		gridPanel.add(cancelButton, gbc_cancelButton);
		
//		gridPanel.setLayout(new BorderLayout(0, 0));
		saleButtonsPanel.add(gridPanel);
		buttonPanel.add(confirmButton, BorderLayout.EAST);
		buttonPanel.add(scrollPane, BorderLayout.CENTER);
		gridPanel.add(buttonPanel, gbc_confirmButton);
		add(gridPanel, BorderLayout.CENTER);

		revalidate();

	}
	
	public void confirm() {

			System.out.println(list.getSelectedIndex());
	}
	
	public void cancelAppointment() {		
		this.removeAll();
		initGui();
	}
		
}

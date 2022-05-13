package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

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

	private SaleController saleCtrl;
	
	private Table hourTable;

	
	/**
	 * Create the panel.
	 */
	public AppointmentPanel() {
		Thread controllerInit = new Thread(() -> {
			try {
				saleCtrl = new SaleController();
			} catch (DatabaseAccessException e) {
				e.printStackTrace();
			}
		});
		
		controllerInit.start();
		initGui();
	}
	
	/**
	 * Initialise gui
	 */
	private void initGui() {
		setBorder(new EmptyBorder(new Insets(0, 3, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{447, 0};
		gridBagLayout.rowHeights = new int[]{300, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		saleButtonsPanel = new JPanel();
		saleButtonsPanel.setBackground(ColorScheme.BACKGROUND);
		GridBagConstraints gbc_saleButtonsPanel = new GridBagConstraints();
		gbc_saleButtonsPanel.weightx = 1.0;
		gbc_saleButtonsPanel.weighty = 1.0;
		gbc_saleButtonsPanel.fill = GridBagConstraints.BOTH;
		gbc_saleButtonsPanel.gridx = 0;
		gbc_saleButtonsPanel.gridy = 0;
		add(saleButtonsPanel, gbc_saleButtonsPanel);
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
		StringBuilder a = new StringBuilder();
		a.append(calendar.getModel().getYear() + ".");
		a.append(calendar.getModel().getMonth() + ".");
		a.append(calendar.getModel().getDay());
		System.out.println(a);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		hourTable = new Table(new String[] {"Description", "Cost", "Time"});
		
		tabbedPane.addTab("AvailableHours", null, hourTable, null);

		removeAll();
		
		
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
		
		saleButtonsPanel.add(gridPanel);
		gridPanel.add(tabbedPane, gbc_tabbedPanel);
		add(gridPanel);
		revalidate();

	}
}

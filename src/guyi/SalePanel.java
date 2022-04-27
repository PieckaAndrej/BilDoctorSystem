package guyi;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.SaleController;
import exceptions.DatabaseAccessException;

public class SalePanel extends JPanel {

	private static final long serialVersionUID = -81954108504757498L;
	
	private JPanel saleButtonsPanel;
	private JButton btnCreateSale;
	private JTextField fieldVehicle;
	private JLabel lblNewLabel;

	private SaleController saleCtrl;
	
	private Table serviceTable;
	private Table productTable;

	
	public SalePanel() {
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
	
	private void initGui() {
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(new Insets(0, 3, 0, 0)));
		
		saleButtonsPanel = new JPanel();
		saleButtonsPanel.setBackground(ColorScheme.BACKGROUND);
		add(saleButtonsPanel, BorderLayout.CENTER);
		GridBagLayout gbl_saleButtonsPanel = new GridBagLayout();
		gbl_saleButtonsPanel.columnWeights = new double[]{1.0};
		saleButtonsPanel.setLayout(gbl_saleButtonsPanel);
		
		btnCreateSale = new JButton("Create Sale");
		btnCreateSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread createSale = new Thread(() -> {
					createSale();
				});
				
				createSale.start();
			}
		});
		
		lblNewLabel = new JLabel("Vehicle Id");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.ipadx = 5;
		gbc_lblNewLabel.weightx = 1.0;
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		saleButtonsPanel.add(lblNewLabel, gbc_lblNewLabel);
		
	
		fieldVehicle = new JTextField(10);
		GridBagConstraints gbc_fieldVehicle = new GridBagConstraints();
		gbc_fieldVehicle.weightx = 1.0;
		gbc_fieldVehicle.anchor = GridBagConstraints.WEST;
		gbc_fieldVehicle.insets = new Insets(0, 0, 5, 0);
		gbc_fieldVehicle.gridx = 1;
		gbc_fieldVehicle.gridy = 0;
		saleButtonsPanel.add(fieldVehicle, gbc_fieldVehicle);
		
		
		GridBagConstraints gbc_btnCreateSale = new GridBagConstraints();
		gbc_btnCreateSale.gridwidth = 2;
		gbc_btnCreateSale.gridheight = 0;
		gbc_btnCreateSale.gridx = 0;
		gbc_btnCreateSale.gridy = 1;
		saleButtonsPanel.add(btnCreateSale, gbc_btnCreateSale);
	}
	
	private void createSale() {
		if (saleCtrl.createSale(fieldVehicle.getText())) {
			JTabbedPane tabbedPane = new JTabbedPane();
			serviceTable = new Table(new String[] {"Description", "Cost", "Time"});
			productTable = new Table(new String[] {"Id", "Name", "Quantity"});
			
			serviceTable.setActionAdd(new ControllerActionIF() {
				public void action(InputPanel p) {
					Thread addService = new Thread(() -> {
						addService(p);
					});
					
					addService.start();
				}
			});
			
			productTable.setActionAdd(new ControllerActionIF() {
				public void action(InputPanel p) {
					Thread addProduct = new Thread(() -> {
						addProduct(p);
					});
					
					addProduct.start();
				}
			});
			
			
			serviceTable.setName("Services");
			productTable.setName("Products");
			
			FinishSale finishSale = new FinishSale(
					new Table[] {serviceTable, productTable});
			
			// Add action listener to the finish sale button
			finishSale.addFinishSaleActionListener(a -> finishSale());
			
			tabbedPane.addTab("Service", null, serviceTable, null);
			tabbedPane.addTab("Product", null, productTable, null);
			tabbedPane.addTab("Finish", null, finishSale, null);
			
			// Update finish sale panel text when clicked on it
			tabbedPane.addChangeListener(l -> {
				if (tabbedPane.getSelectedIndex() == 2) {
					finishSale.showTableInfo();
				}
			});
			
			removeAll();
			add(tabbedPane, BorderLayout.CENTER);
		} else {
			fieldVehicle.putClientProperty( "JComponent.outline", "error" );
			//fieldVehicle.setFocusable(true);
		}
	}
	
	private void finishSale() {
		Thread finishSaleThread = new Thread(() -> {
			if (saleCtrl.finishSale()) {
				removeAll();
				initGui();
			} else {
				// TODO maybe something could go wrong
				System.out.println("Error");
			}
		});
		
		finishSaleThread.start();
	}
	
	private void addProduct(InputPanel input) {
		input.resetFieldColor();
		List<JTextField> fields = input.getFields();
		
		try { // Wrong id
			int id = Integer.parseInt(fields.get(0).getText());
			
			try { // Wrong name
				String name = fields.get(1).getText();
				
				try { // Wrong quantity
					int quantity = Integer.parseInt(fields.get(2).getText());
					saleCtrl.addProduct(id, name, quantity);
					
					productTable.addRow(input);
					
					input.dispose();
				} catch (Exception e) {
					input.getFields().get(2).putClientProperty( "JComponent.outline", "error" );
					input.getErrorLabel().setText(e.getMessage());
				}
			} catch (Exception e) {
				input.getFields().get(1).putClientProperty( "JComponent.outline", "error" );
				input.getErrorLabel().setText(e.getMessage());
			}
			
			
		} catch (Exception e) {
			input.getFields().get(0).putClientProperty( "JComponent.outline", "error" );
			input.getErrorLabel().setText(e.getMessage());
		}
		
		
	}
	
	private void addService(InputPanel input) {
		input.resetFieldColor();
		
		List<JTextField> fields = input.getFields();
		try { // Wrong time
			double time = Double.parseDouble(fields.get(2).getText());
			
			try { // Wrong cost
				double cost = Double.parseDouble(fields.get(1).getText());
				
				try { // Wrong description
					String desc = fields.get(0).getText();
					
					saleCtrl.addService(cost, time, desc);
					
					serviceTable.addRow(input);
					
					input.dispose();
				} catch (Exception e) {
					fields.get(0).putClientProperty( "JComponent.outline", "error" );
					input.getErrorLabel().setText(e.getMessage());
				}
				
			} catch (Exception e) {
				fields.get(1).putClientProperty( "JComponent.outline", "error" );
				input.getErrorLabel().setText(e.getMessage());
			}

		} catch (Exception e) {
			fields.get(2).putClientProperty( "JComponent.outline", "error" );
			input.getErrorLabel().setText(e.getMessage());
		}
	}
}

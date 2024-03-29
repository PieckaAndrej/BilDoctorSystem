package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.SaleController;
import exceptions.DatabaseAccessException;
import model.Product;

public class SalePanel extends JPanel {

	private static final long serialVersionUID = -81954108504757498L;
	
	private JPanel saleButtonsPanel;
	private JButton btnCreateSale;
	private JTextField fieldVehicle;
	private JLabel lblNewLabel;

	private SaleController saleCtrl;
	private FinishSale finishSale;
	
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
	
	/**
	 * Initialise gui
	 */
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
	
	/**
	 * Create sale button
	 */
	private void createSale() {
		if (saleCtrl.createSale(fieldVehicle.getText())) {
			
			JTabbedPane tabbedPane = new JTabbedPane();
			// Cancel button
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Thread cancelSale = new Thread(() -> {
						cancelSale();
					});
					
					cancelSale.start();
				}
			});
			
			
			// Service Table
			serviceTable = new Table(new String[] {"Description", "Cost", "Time"});
			
			// Product Table
			String[] productInputs = new String[] {"Id", "Name", "Quantity"};
			
			CustomInputPanel inputPanel = new CustomInputPanel(productInputs);
			inputPanel.setValues(new String[] {"", "", "1"});
			
			JComboBox<Product> box = new JComboBox<>();
			
			// Creates the list using the ProductListCellRenderer and fill the list
			
			box.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
				return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(),
						index, isSelected, cellHasFocus);
			});
			
			List<Product> ps = saleCtrl.getAllProducts();
			DefaultComboBoxModel<Product> dfm = new DefaultComboBoxModel<>();
			dfm.addAll(ps);
			
			box.setModel(dfm);
			
			// Adds a focus listener so when the combo box receives focus the list opens
			
			box.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {

				   @Override
				   public void focusGained(FocusEvent e) {
					   
					   // Opens the list
					   
				      box.showPopup();
				   }
				});
			
			// Adds a key listener, so after a key is pressed it can update the list
			
			box.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyReleased(KeyEvent e) {
					
					// Check if the pressed key was an arrow key and in that case it does not refresh the box, so
					// we can navigate freely in the written word and list
					
					if (!(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_UP ||
							e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT) ) {
						
						// Retrieve the string written in the text field
						
						Object o = box.getEditor().getItem();
						
						// Remove every item from the list
						
						((DefaultComboBoxModel<Product>) box.getModel()).removeAllElements();

						// Sets the selected item to the written text so it will not be removed
						
						box.setSelectedItem(o);

						// Check if object is null, because in that case we cannot call the toString() on it
						
						if (o != null) {
								
							// Refills the list with the items which contains the written text	
								
							((DefaultComboBoxModel<Product>) box.getModel()).addAll(saleCtrl.getAllProducts()
									.stream().filter(p -> p.getName().toLowerCase().contains(o.toString()
									.toLowerCase())).toList());
						}
						
						// Checks if you press enter, if you do than it selects item from list
						
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							
							// Checks if there is a product in the list, so it can select item from there
							
							if (saleCtrl.getAllProducts()
									.stream().filter(p -> p.getName().toLowerCase().contains(o.toString()
									.toLowerCase())).count() > 0 ) {
								
								// Checks if the item in the editor is Product, if not it selects the first one
								// in the list which matches is the most
								
								if (!(box.getEditor().getItem() instanceof Product)) {
									box.getEditor().setItem(saleCtrl.getAllProducts()
											.stream().filter(p -> p.getName().toLowerCase().contains(o.toString()
											.toLowerCase())).toList().get(0));
								}	
								
								// Changes the id field automatically to the id of the selected product
								
								inputPanel.getFields()[0].setText(Integer.
										toString(((Product)box.getEditor().getItem()).getId()));
							}
							
						} else {
							
							// If we did not press enter it deletes the id if we had something there and then
							// closes and opens the list so it resizes
							
							inputPanel.getFields()[0].setText("");
							box.hidePopup();
						    box.showPopup();
						    
						    
						}
					}
					
				}
			});
			
			// Adds a item listener so if we choose an item with the mouse it can set the id
			
			box.addItemListener(e -> {
				
				// Check if it was a mouse event and if there is an element in the list
				
				if (MouseEvent.BUTTON1 == e.getStateChange() && box.getSelectedIndex() != -1) {
					
					// Changes the id field automatically to the id of the selected product
					
					inputPanel.getFields()[0].setText(Integer.
							toString(((Product)box.getSelectedItem()).getId()));
				}
			});
			
			// Adds a mouse listener so if you click on the combo box it opens the list

			box.getEditor().getEditorComponent().addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
					// Checks if it was a left mouse click
					
					if(e.getButton() == MouseEvent.BUTTON1)
						
						// Opens the list
							
						box.showPopup();
				}
			});

			// Enables the  editing of the combo box so you can write in it
			
			box.setEditable(true);
			
			// Adds the box to the input panel replacing the 2nd j text field
			
			inputPanel.setComponent(1, box);
			
			productTable = new Table(productInputs, inputPanel);
			
			
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
			
			productTable.setActionRemove(new RemoveActionIF(){
				public void action(int[] array) {
					Thread remove = new Thread(() -> {
						removeProduct(array);
					});
					
					remove.start();
				}
			});
			
			serviceTable.setActionRemove(new RemoveActionIF(){
				public void action(int[] array) {
					Thread remove = new Thread(() -> {
						removeService(array);
					});
					
					remove.start();
				}
			});
			
			
			
			serviceTable.setName("Services");
			productTable.setName("Products");
			
			finishSale = new FinishSale(
					new Table[] {serviceTable, productTable});
			
			// Add action listener to the finish sale button
			finishSale.addFinishSaleActionListener(a -> finishSale());
			
			tabbedPane.addTab("Service", null, serviceTable, null);
			tabbedPane.addTab("Product", null, productTable, null);
			tabbedPane.addTab("Finish", null, finishSale, null);
			tabbedPane.addTab("Cancel", null, cancelButton, null);
			
			// Update finish sale panel text when clicked on it
			tabbedPane.addChangeListener(l -> {
				if (tabbedPane.getSelectedIndex() == 2) {
					finishSale.showTableInfo();
				}
				else if(tabbedPane.getSelectedIndex() == 3) {
					cancelSale();
				}
			});
			
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
			
			GridBagConstraints gbc_cancelButton = new GridBagConstraints();
			gbc_cancelButton.gridwidth = 0;
			gbc_cancelButton.gridheight = 0;
			gbc_cancelButton.gridx = 7;
			gbc_cancelButton.gridy = 0;
			gbc_cancelButton.anchor = GridBagConstraints.NORTHEAST;
			gbc_cancelButton.insets = new Insets(5, 5, 5, 5);
			gridPanel.add(cancelButton, gbc_cancelButton);
			
			gridPanel.add(tabbedPane, gbc_tabbedPanel);
			add(gridPanel, BorderLayout.CENTER);
		} else {
			App.showComponentError(fieldVehicle);
		}
		
	}
	
	/**
	 * Finish sale button
	 */
	private void finishSale() {
		Thread finishSaleThread = new Thread(() -> {
			saleCtrl.getSale().setDescription(finishSale.getTextPane().getText());
			
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
	
	/**
	 * Remove product from the controller
	 * @param array An array containing the indexes of the 
	 * removed products sorted from high to low
	 */
	private void removeProduct(int[] array) {
		saleCtrl.removeProduct(array);
	}
	
	/**
	 * Remove service from the controller
	 * @param array An array containing the indexes of the 
	 * removed services sorted from high to low
	 */
	private void removeService(int[] array) {
		saleCtrl.removeService(array);
	}
	
	/**
	 * Add product to the sale
	 * @param input InputPanel containing the user input
	 */
	private void addProduct(InputPanel input) {
		input.resetFieldColor();
		String[] fields = input.getTexts();
		
		try { // Wrong id
			int id = Integer.parseInt(fields[0]);
			
			try { // Wrong name
				String name = fields[1];
				
				try { // Wrong quantity
					int quantity = Integer.parseInt(fields[2]);
					saleCtrl.addProduct(id, name, quantity);
					
					productTable.addRow(input);
					
					input.dispose();
				} catch (NumberFormatException e) {
					App.showComponentError(input.getFields()[2]);
					input.getErrorLabel().setText(e.getClass().getName());
				} catch (Exception e) {
					App.showComponentError(input.getFields()[2]);
					input.getErrorLabel().setText(e.getMessage());
				}
			} catch (Exception e) {
				App.showComponentError(input.getFields()[1]);
				input.getErrorLabel().setText(e.getMessage());
			}
			
			
		} catch (NumberFormatException e) {
			App.showComponentError(input.getFields()[0]);
			input.getErrorLabel().setText("Enter valid ID");
		} catch (Exception e) {
			App.showComponentError(input.getFields()[0]);
			input.getErrorLabel().setText(e.getMessage());
		}
		
		
	}
	
	/**
	 * Add service to the sale
	 * @param input InputPanel containing the user input
	 */
	private void addService(InputPanel input) {
		input.resetFieldColor();
		
		String[] fields = input.getTexts();
		try { // Wrong time
			double time = Double.parseDouble(fields[2]);
			
			try { // Wrong cost
				double cost = Double.parseDouble(fields[1]);
				
				try { // Wrong description
					String desc = fields[0];
					
					saleCtrl.addService(cost, time, desc);
					
					serviceTable.addRow(input);
					
					input.dispose();
				} catch (Exception e) {
					App.showComponentError(input.getFields()[0]);
					input.getErrorLabel().setText(e.getMessage());
				}
				
			} catch (NumberFormatException e) {
				App.showComponentError(input.getFields()[1]);
				input.getErrorLabel().setText("Enter valid cost");
			} catch (Exception e) {
				App.showComponentError(input.getFields()[1]);
				input.getErrorLabel().setText(e.getMessage());
			}

		} catch (NumberFormatException e) {
			App.showComponentError(input.getFields()[2]);
			input.getErrorLabel().setText("Enter valid time");
		} catch (Exception e) {
			App.showComponentError(input.getFields()[2]);
			input.getErrorLabel().setText(e.getMessage());
		}
	}
	
	/**
	 * Cancel the sale
	 */
	public void cancelSale() {
		saleCtrl.cancelSale();
		
		this.removeAll();
		initGui();
	}
}

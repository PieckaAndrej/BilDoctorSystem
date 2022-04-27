package guyi;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import controller.SaleController;
import exceptions.DatabaseAccessException;

public class App extends JFrame {

	private JPanel contentPane;
	private JPanel salePanel;
	
	private SaleController saleCtrl;
	private JPanel saleButtonsPanel;
	private JButton btnCreateSale;
	private JTextField fieldVehicle;
	private JLabel lblNewLabel;
	
	private Table serviceTable;
	private Table productTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatLightLaf.setup();
					UIManager.put( "Button.arc", 10 );
					UIManager.put( "Component.arc", 10 );
					UIManager.put( "ProgressBar.arc", 10 );
					UIManager.put( "TextComponent.arc", 10 );
					UIManager.put( "TabbedPane.showTabSeparators", true );
					
					UIManager.put( "ScrollBar.thumbArc", 999 );
					UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
					//UIManager.setLookAndFeel ( new WebLookAndFeel () );
					//UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		try {
			saleCtrl = new SaleController();
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
		}
		
		initGui();
	}
	
	private void initGui() {
		setTitle("Bil Doctor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		//contentPane.setBackground(ColorScheme.BACKGROUND);
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		salePanel = new JPanel();
		tabbedPane.addTab("Sale", null, salePanel, null);
		salePanel.setLayout(new BorderLayout(0, 0));
		
		saleButtonsPanel = new JPanel();
		salePanel.add(saleButtonsPanel, BorderLayout.CENTER);
		GridBagLayout gbl_saleButtonsPanel = new GridBagLayout();
		gbl_saleButtonsPanel.columnWeights = new double[]{1.0};
		saleButtonsPanel.setLayout(gbl_saleButtonsPanel);
		
		btnCreateSale = new JButton("Create Sale");
		btnCreateSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSale();
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
			productTable = new Table(new String[] {"Id", "Quantity"});
			
			serviceTable.setActionAdd(new ControllerActionIF() {
				public void action(InputPanel p) {
					addService(p);
				}
			});
			
			productTable.setActionAdd(new ControllerActionIF() {
				public void action(InputPanel p) {
					addProduct(p);
				}
			});
			
			
			serviceTable.setName("Services");
			productTable.setName("Products");
			
			FinishSale finishSale = new FinishSale(
					new Table[] {serviceTable, productTable}, saleCtrl);
			
			tabbedPane.addTab("Service", null, serviceTable, null);
			tabbedPane.addTab("Product", null, productTable, null);
			tabbedPane.addTab("Finish", null, finishSale, null);
			
			tabbedPane.addChangeListener(l -> {
				if (tabbedPane.getSelectedIndex() == 2) {
					finishSale.showTableInfo();
				}
			});
			
			salePanel.removeAll();
			salePanel.add(tabbedPane, BorderLayout.CENTER);
		} else {
			fieldVehicle.setBackground(ColorScheme.ERROR);
		}
	}
	
	private void addProduct(InputPanel input) {
		input.resetFieldColor();
		List<JTextField> fields = input.getFields();
		
		try {
			int id = Integer.parseInt(fields.get(0).getText());
			
			try {
				int quantity = Integer.parseInt(fields.get(1).getText());
				saleCtrl.addProduct(id, quantity);
				
				productTable.addRow(input);
				
				input.dispose();
			} catch (Exception e) {
				input.getFields().get(1).setBackground(ColorScheme.ERROR);
				input.getErrorLabel().setText(e.getMessage());
			}
		} catch (Exception e) {
			input.getFields().get(0).setBackground(ColorScheme.ERROR);
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
					fields.get(0).setBackground(ColorScheme.ERROR);
					input.getErrorLabel().setText(e.getMessage());
				}
				
			} catch (Exception e) {
				fields.get(1).setBackground(ColorScheme.ERROR);
				input.getErrorLabel().setText(e.getMessage());
			}

		} catch (Exception e) {
			fields.get(2).setBackground(ColorScheme.ERROR);
			input.getErrorLabel().setText(e.getMessage());
		}
	}
}

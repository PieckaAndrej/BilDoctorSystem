package guyi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.SaleController;
import exceptions.DatabaseAccessException;

public class App extends JFrame {

	private JPanel contentPane;
	private JPanel salePanel;
	
	private SaleController saleCtrl;
	private JPanel saleButtonsPanel;
	private JButton btnCreateSale;
	private InputPanel inputPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
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
			SaleController saleCtrl = new SaleController();
		} catch (DatabaseAccessException e) {
			// TODO Auto-generated catch block
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
		contentPane.setBackground(ColorScheme.BACKGROUND);
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		salePanel = new JPanel();
		tabbedPane.addTab("Sale", null, salePanel, null);
		salePanel.setLayout(new BorderLayout(0, 0));
		
		saleButtonsPanel = new JPanel();
		salePanel.add(saleButtonsPanel, BorderLayout.CENTER);
		GridBagLayout gbl_saleButtonsPanel = new GridBagLayout();
		saleButtonsPanel.setLayout(gbl_saleButtonsPanel);
		
		btnCreateSale = new JButton("Create Sale");
		btnCreateSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSale();
			}
		});
		GridBagConstraints gbc_btnCreateSale = new GridBagConstraints();
		gbc_btnCreateSale.gridwidth = 0;
		gbc_btnCreateSale.gridheight = 0;
		gbc_btnCreateSale.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCreateSale.gridx = 0;
		gbc_btnCreateSale.gridy = 0;
		saleButtonsPanel.add(btnCreateSale, gbc_btnCreateSale);
		
		
		
	}

	private void createSale() {
		JTabbedPane tabbedPane = new JTabbedPane();
		Table serviceTable = new Table(saleCtrl, new String[] {"Description", "Cost", "Time"});
		Table productTable = new Table(saleCtrl, new String[] {"Id", "Quantity"});
		
		//inputPanel = new InputPanel(new String[] {"Description", "Cost", "Time"});
		
		serviceTable.setName("Services");
		productTable.setName("Products");
		
		FinishSale finishSale = new FinishSale(new Table[] {serviceTable, productTable});
		
		tabbedPane.addTab("Service", null, serviceTable, null);
		tabbedPane.addTab("Product", null, productTable, null);
		tabbedPane.addTab("Finish", null, finishSale, null);
		
		salePanel.removeAll();
		salePanel.add(tabbedPane, BorderLayout.CENTER);
		
	}
}

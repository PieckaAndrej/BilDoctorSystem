package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import dal.DbConnection;

public class App extends JFrame {

	private JPanel contentPane;
	private JPanel salePanel;
	private JLabel lblConnection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Set up the look and feel
					BilDoctorTheme.setup();
					
					// Make the Components have round corners
					UIManager.put("Button.arc", 10);
					UIManager.put("Component.arc", 10);
					UIManager.put("ProgressBar.arc", 10);
					UIManager.put("TextComponent.arc", 10);
					UIManager.put("TabbedPane.showTabSeparators", true);

					// Scroll bar with round corners
					UIManager.put("ScrollBar.thumbArc", 999);
					UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
					UIManager.put("TabbedPane.selectedBackground", Color.white);
					
					// Initialise the application
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
		// Start the connection checking on a new thread
		Thread connectionCheck = new Thread(() -> {
			resetConnection();
		});

		connectionCheck.start();
		
		// Initialise the GUI
		initGui();
	}

	/**
	 * Initialise the GUI
	 */
	private void initGui() {
		setTitle("Bil Doctor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		// contentPane.setBackground(ColorScheme.BACKGROUND);
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		salePanel = new SalePanel();
		tabbedPane.addTab("Sale", null, salePanel, null);
	}

	/**
	 * Ran after the connection is lost
	 * 
	 * Checks the connection again with incrementing delays
	 */
	private void resetConnection() {
		System.out.println("Connection lost");
		lblConnection = new JLabel("Lost connection - establishing connection...");
		System.out.println("ADD");
		getContentPane().add(lblConnection, BorderLayout.NORTH);
		revalidate();

		int[] times = new int[] { 2, 5, 10, 20 };

		int thisTimeIndex = 0;
		int time = 0;

		try {
			while (!DbConnection.getInstance().checkConnection()) {
				time = times[thisTimeIndex];

				for (int i = 0; i < times[thisTimeIndex]; i++) {
					lblConnection.setText("Lost connection - reseting in " + time);
					time--;
					Thread.sleep(1000);
				}

				if (thisTimeIndex < (times.length - 1)) {
					thisTimeIndex++;
				}

				lblConnection.setText("Lost connection - establishing connection...");
			}
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}

		// After the connection is back
		remove(lblConnection);
		revalidate();
		checkConnection();
	}

	/**
	 * Checks the connection every 5 seconds
	 * 
	 * If the connection is lost it will try to reset connection
	 */
	private void checkConnection() {
		boolean uninterupted = true;

		boolean con = true;
		
		// Check connection every 5 seconds
		while (uninterupted) {

			try {
				con = DbConnection.getInstance().checkConnection();
			} catch (SQLException e) {
				con = false;
			}

			if (!con) {
				uninterupted = false;
				resetConnection();
			}
//			System.out.println("Waiting 5");
			try {
				// Sleep for 5 seconds
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				uninterupted = false;
			}

		}
	}
	
	public static void showComponentError(JComponent c) {
		c.putClientProperty( "JComponent.outline", "error" );
		c.setBackground(ColorScheme.ERROR);
	}
}

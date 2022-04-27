package guyi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;
import java.sql.SQLException;

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
					BilDoctorTheme.setup();
					UIManager.put( "Button.arc", 10 );
					UIManager.put( "Component.arc", 10 );
					UIManager.put( "ProgressBar.arc", 10 );
					UIManager.put( "TextComponent.arc", 10 );
					UIManager.put( "TabbedPane.showTabSeparators", true );
					
					UIManager.put( "ScrollBar.thumbArc", 999 );
					UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
					UIManager.put( "TabbedPane.selectedBackground", Color.white );
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
		Thread connectionCheck = new Thread(() -> {
			resetConnection();
		});
		
		connectionCheck.start();
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
		
		salePanel = new SalePanel();
		tabbedPane.addTab("Sale", null, salePanel, null);
	}
	
	private void resetConnection() {
		System.out.println("Connection lost");
		lblConnection = new JLabel("Lost connection - establishing connection...");
		System.out.println("ADD");
		add(lblConnection, BorderLayout.NORTH);
		revalidate();
		
		int[] times = new int[] {
				2,
				5,
				10,
				20
		};
		
		int thisTimeIndex = 0;
		int time = 0;
		
		try {
			while (!DbConnection.getInstance().checkConnection()) {
				time = times[thisTimeIndex];
				
				for (int i = 0; i < times[thisTimeIndex]; i++) {
					lblConnection.setText("Lost connection - resseting in " + time);
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
	
	private void checkConnection() {
		boolean uninterupted = true;
		
		boolean con = true;
		while(uninterupted) {
			
			try {
				con = DbConnection.getInstance().checkConnection();
			} catch (SQLException e) {
				con = false;
			}
			
			if (!con) {
				uninterupted = false;
				resetConnection();
			}
			System.out.println("Waiting 10");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				uninterupted = false;
			}
			
		}
	}
}

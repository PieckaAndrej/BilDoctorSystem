package guyi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class App extends JFrame {

	private JPanel contentPane;
	private JPanel salePanel;


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
}

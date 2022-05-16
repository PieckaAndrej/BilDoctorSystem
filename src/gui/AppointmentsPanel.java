package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.AppointmentController;

import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class AppointmentsPanel extends JFrame {

	private JPanel contentPane;
	private AppointmentController appointmentController;
	private JList list;
    private DefaultListModel listModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppointmentsPanel frame = new AppointmentsPanel();
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
	public AppointmentsPanel() {
			Thread controllerInit = new Thread(() -> {
					appointmentController = new AppointmentController();
			});
			
			controllerInit.start();
			initGui();
	}

	public void initGui() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		//scrollPane.setViewportView(panel);
		

		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox = Box.createVerticalBox();
		panel.add(verticalBox);
		

	    listModel = new DefaultListModel();
	    
		for(double i = 0.00; i < 24; i++) {
			listModel.addElement(i);
		}

	    //Create the list and put it in a scroll pane.
	    list = new JList(listModel);
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
	    scrollPane.setColumnHeaderView(btnConfirm);
	}
	
	public void confirm() {

		System.out.println(list.getSelectedIndex());
	}

}

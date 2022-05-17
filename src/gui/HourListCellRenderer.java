package gui;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Appointment;

public class HourListCellRenderer implements ListCellRenderer<Double> {
	
	private LocalDateTime date;
	private ArrayList<Appointment> a;

	public HourListCellRenderer(LocalDateTime date, ArrayList<Appointment> a) {
		this.date = date;
		this.a = a;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Double> list, Double value, int index,
			boolean isSelected, boolean cellHasFocus) {
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		for(Appointment element: a) {
			if(Double.compare(element.getAppointmentDate().getHour(), value) == 0) {
				dlcr.setBackground(Color.RED);
			}
		}
		
		return dlcr;
	}


	public void setDate() {
		
	}
}

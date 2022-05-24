package gui;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Appointment;

public class HourListCellRenderer implements ListCellRenderer<Integer> {
	
	private List<Appointment> a;

	public HourListCellRenderer(List<Appointment> a) {
		this.a = a;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
			boolean isSelected, boolean cellHasFocus) {
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		for (Appointment element: a) {
			if (Integer.compare(element.getAppointmentDate().getHour(), value) == 0) {
				dlcr.setBackground(Color.RED);
			}
		}
		
		return dlcr;
	}


	public void setDate() {
		
	}
}

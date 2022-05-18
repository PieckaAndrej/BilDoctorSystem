package gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Employee;

public class EmployeeListCellRenderer implements ListCellRenderer<Employee>{

	@Override
	public Component getListCellRendererComponent(JList<? extends Employee> list, Employee value, int index,
			boolean isSelected, boolean cellHasFocus) {
//		  String name = (value == null) ? "" : value.getName();
		return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
	}

}

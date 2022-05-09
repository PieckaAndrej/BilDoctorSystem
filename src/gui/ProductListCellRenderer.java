package gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Product;

public class ProductListCellRenderer implements ListCellRenderer<Product>{

	@Override
	public Component getListCellRendererComponent(JList<? extends Product> list, Product value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
	}

}

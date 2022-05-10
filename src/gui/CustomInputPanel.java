package gui;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CustomInputPanel extends InputPanel {
	private enum Type {
		TEXT_FIELD,
		SPINNER,
		COMBO_BOX
	}
	
	private Type[] types;
	
	private JSpinner[] spinners;
	private JComboBox<?>[] boxes;
	
	
	public CustomInputPanel(String[] names) {
		super(names);
		
		types = new Type[names.length];
		
		for (int i = 0; i < names.length; i++) {
			types[i] = Type.TEXT_FIELD;
		}
		
		spinners = new JSpinner[names.length];
		boxes = new JComboBox[names.length];
	}
	
	@Override
	public void generatePanel() {
		super.createLabels();
		for (int i = 0; i < types.length; i++) {
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(new BorderLayout());
			

			switch (types[i]) {
			case SPINNER:
				System.out.println("Spinner");
				
				panel.add(spinners[i]);
				break;
			case TEXT_FIELD:
				System.out.println("Text field");
				
				JTextField field = new JTextField(super.getValues()[i]);
				super.addField(i, field);
				
				panel.add(field);
				break;
			case COMBO_BOX:
				System.out.println("box");
				
				boxes[i].setSelectedIndex(-1);
				panel.add(boxes[i]);
				break;
			}
			
			getVerticalBoxField().add(panel, BorderLayout.EAST);
		}
	}
	
	@Override
	public String[] getTexts() {
		String[] retVal = new String[types.length];
		
		for (int i = 0; i < retVal.length; i++) {
			switch (types[i]) {
			case SPINNER:
				System.out.println("Spinner");
				
				retVal[i] = spinners[i].getValue().toString();
				break;
			case TEXT_FIELD:
				System.out.println("Text field");
				
				retVal[i] = super.getFields()[i].getText();
				break;
			case COMBO_BOX:
				System.out.println("box");
				
				Object item = boxes[i].getSelectedItem();
				String s = "";
				
				if (item != null) {
					s = item.toString();
				}
				
				retVal[i] = s;
				
				
				break;
			}
		}
		
		return retVal;
	}
	
	public void setComponent(int index, JSpinner spinner) {
		types[index] = Type.SPINNER;
		
		spinners[index] = spinner;
	}
	
	public void setComponent(int index, JComboBox<?> box) {
		types[index] = Type.COMBO_BOX;
		
		boxes[index] = box;
	}

}

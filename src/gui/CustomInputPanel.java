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
	
	/**
	 * Constructor of the custom input panel
	 * @param names Names of the input fields
	 */
	public CustomInputPanel(String[] names) {
		super(names);
		
		types = new Type[names.length];
		
		for (int i = 0; i < names.length; i++) {
			types[i] = Type.TEXT_FIELD;
		}
		
		spinners = new JSpinner[names.length];
		boxes = new JComboBox[names.length];
	}
	
	/**
	 * Generate panel with the inputs
	 */
	@Override
	public void generatePanel() {
		super.createLabels();
		for (int i = 0; i < types.length; i++) {
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(new BorderLayout());
			

			switch (types[i]) {
			case SPINNER:
				panel.add(spinners[i]);
				
				break;
			case TEXT_FIELD:
				JTextField field = new JTextField(super.getValues()[i]);
				super.addField(i, field);
				
				panel.add(field);
				break;
			case COMBO_BOX:
				boxes[i].setSelectedIndex(-1);
				panel.add(boxes[i]);
				
				break;
			}
			
			getVerticalBoxField().add(panel, BorderLayout.EAST);
		}
	}
	
	/**
	 * Get texts from the inputs
	 */
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
	
	/**
	 * Set component with index as a JSpinner
	 * @param index The index of the component
	 * @param spinner The JSpinner object that is in the index
	 */
	public void setComponent(int index, JSpinner spinner) {
		types[index] = Type.SPINNER;
		
		spinners[index] = spinner;
	}
	
	/**
	 * Set component with index as a JComboBox
	 * @param index The index of the component
	 * @param box The JComboBox object that is in the index
	 */
	public void setComponent(int index, JComboBox<?> box) {
		types[index] = Type.COMBO_BOX;
		
		boxes[index] = box;
	}

}

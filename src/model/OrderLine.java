package model;

import exceptions.QuantityUnderrunException;

//Barnabas doing this do not touch
public class OrderLine {
	private int quantity;
	private int id;
	private Product product;
	private String plateNumber;

	
	
	/**
	 * Constructor for OrderLine
	 * @param quantity
	 * @throws QuantityUnderrunException 
	 */
	public OrderLine(int quantity, Product product) throws QuantityUnderrunException {
		setQuantity(quantity);
		this.product = product;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) throws QuantityUnderrunException {
		if (quantity <= 0) {
			throw new QuantityUnderrunException(quantity);
		}
		this.quantity = quantity;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public String getPlateNumber() {
		return plateNumber;
	}



	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	
}

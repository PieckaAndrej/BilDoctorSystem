package model;

import java.time.LocalTime;

//Barnabas doing this do not touch
public class OrderLine {
	private int quantity;
	private int id;
	private Product product;
	private String plateNumber;

	
	
	/**
	 * Constructor for OrderLine
	 * @param quantity
	 */
	public OrderLine(int quantity, Product product) {
		this.quantity = quantity;
		this.product = product;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
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

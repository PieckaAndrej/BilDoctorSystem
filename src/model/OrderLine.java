package model;

import exceptions.QuantityUnderrunException;

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

	/**
	 * Get quantity
	 * 
	 * @return quantity as int
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Set quantity
	 * 
	 * @param quantity as int
	 * @throws QuantityUnderrunException
	 */
	public void setQuantity(int quantity) throws QuantityUnderrunException {
		if (quantity <= 0) {
			throw new QuantityUnderrunException(quantity);
		}
		this.quantity = quantity;
	}

	/**
	 * Get id
	 * 
	 * @return id as int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id as int
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get product
	 * 
	 * @return product as Product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Set product
	 * 
	 * @param product as Product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Get plate number
	 * 
	 * @return plate number as String
	 */
	public String getPlateNumber() {
		return plateNumber;
	}

	/**
	 * Set plate number
	 * @param plateNumber as String
	 */
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
}

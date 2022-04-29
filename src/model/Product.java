package model;

import java.math.BigDecimal;

public class Product {
	
	private int currentStock;
	private BigDecimal price;
	private int id;
	
	private String name;
	
	
	public Product(int currentStock, BigDecimal price, int id, String name) {
		this.currentStock = currentStock;
		this.price = price;
		this.id = id;
		this.name = name;
	}
	
	public Product(int currentStock, double price, int id, String name) {
		this(currentStock, new BigDecimal(price), id, name);
	}

	/**
	 * @return the currentStock
	 */
	public int getCurrentStock() {
		return currentStock;
	}

	/**
	 * @param currentStock the currentStock to set
	 */
	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = new BigDecimal(price);
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/**
	 * @return the productId
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setId(int productId) {
		this.id = productId;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param Set the name
	 */
	public void setName(String name) {
		this.name = name;
	}
}

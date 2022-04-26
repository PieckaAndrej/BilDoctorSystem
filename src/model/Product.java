package model;

public class Product {
	
	private int currentStock;
	
	private double price;
	
	public int id;
	
	public Product(int currentStock, double price, int id)
	{
		this.currentStock = currentStock;
		this.price = price;
		this.id = id;
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
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
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
}

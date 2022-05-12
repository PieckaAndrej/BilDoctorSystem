package model;

import java.math.BigDecimal;

public class Service {
	
	private BigDecimal price;
	private double time;
	private String description;
	
	public Service(BigDecimal price, double time, String dsc) {
		this.price = price;
		this.time = time;
		this.description = dsc;
	}
	
	public Service(double price, double time, String dsc) {
		this(new BigDecimal(price), time, dsc);
	}

	/**
	 * Get price
	 *
	 * @return price as BigDecimal
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Set price
	 *
	 * @param price as BigDecimal
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/**
	 * Set price
	 *
	 * @param price as double 
	 */
	public void setPrice(double price) {
		this.price = new BigDecimal(price);
	}

	/**
	 * Get time.
	 *
	 * @return time as double.
	 */
	public double getTime() {
	    return time;
	}

	/**
	 * Set time.
	 *
	 * @param time the value to set.
	 */
	public void setTime(double time) {
	    this.time = time;
	}

	/**
	 * Get description.
	 *
	 * @return description as String.
	 */
	public String getDescription() {
	    return description;
	}

	/**
	 * Set description.
	 *
	 * @param description the value to set.
	 */
	public void setDescription(String description) {
	    this.description = description;
	}
}

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public void setPrice(double price) {
		this.price = new BigDecimal(price);
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

package model;

import java.time.LocalDateTime;

public class Discount {
	
	private String category;
	
	private int value;
	
	private LocalDateTime startingDate;
	
	public Discount(String category, int value, LocalDateTime startingDate) {
		this.setCategory(category);
		this.setValue(value);
		this.setStartingDate(startingDate);
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the startingDate
	 */
	public LocalDateTime getStartingDate() {
		return startingDate;
	}

	/**
	 * @param startingDate the startingDate to set
	 */
	public void setStartingDate(LocalDateTime startingDate) {
		this.startingDate = startingDate;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}

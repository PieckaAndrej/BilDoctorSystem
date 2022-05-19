package model;

public class Customer extends Person {
	private String discountCategory;
	
	public Customer(String name, String surname, String address, String city,
			String zipcode, String phoneNumber, String countryCode) {
		
		super(name, surname, address, city, zipcode, phoneNumber, countryCode);
	}
	
	public Customer(String name, String surname, String address, String city,
			String zipcode, String phoneNumber, String countryCode, String discountCategory) {
		
		super(name, surname, address, city, zipcode, phoneNumber, countryCode);
		this.discountCategory = discountCategory;
	}

	/**
	 * @return the discountCategory
	 */
	public String getDiscountCategory() {
		return discountCategory;
	}

	/**
	 * @param discountCategory the discountCategory to set
	 */
	public void setDiscountCategory(String discountCategory) {
		this.discountCategory = discountCategory;
	}
}
